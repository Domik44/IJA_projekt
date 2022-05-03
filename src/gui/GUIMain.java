package gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import uml.*;
import uml.classDiagram.ClassDiagram;
import uml.classDiagram.UMLAttribute;
import uml.classDiagram.UMLClass;
import uml.classDiagram.UMLInterface;
import uml.pos.Position;
import uml.relations.RelAggregation;
import uml.relations.RelAssociation;
import uml.relations.RelGeneralization;
import uml.sequenceDiagram.UMLMessage;
import uml.sequenceDiagram.UMLParticipant;
import workers.Reader;

import java.io.IOException;
import java.util.*;

import controller.DeleteController;


/**
 * GUIMain represents GUI extends Application implements Observer
 *
 * @author  Adam Hos
 * @version 1.0
 */
public class GUIMain extends Application implements Observer {

    VBox vBox;
    Pane pane;
    List<Gclass> gClassList;
    // TODO -> tady tohle je pro sekvencni
    List<GParticipant> gParticipantList = new ArrayList<GParticipant>();
    Scene scene;
    Stage stage;

    /**
     * Constructor for GUIMain. Allocate memory, set scene
     */
    public GUIMain() {
        vBox = new VBox();
        pane = new Pane();
        gClassList = new ArrayList<>();
        scene = new Scene(vBox, 1000, 600);
    }

    /**
     * Search through gClassList for GClass with name
     * @param name class name
     * @return found GClass or null
     */
    public Gclass getClassByName(String name){
        for (Gclass gclass: gClassList){
            if (gclass.classLabel.getText() == name)
                return gclass;
        }
        return null;
    }
    
    //TODO -> tohle je pro sekvencni diagram
    public GParticipant getParticipantByName(String name){
        for (GParticipant par : this.gParticipantList){
            if (par.participantNameLabel.getText() == name)
                return par;
        }
        return null;
    }

    // TODO -> tady tohle je pro sekvencni
    public void setupParticipants(List<UMLParticipant> participants) {
    	for (UMLParticipant par : participants) {
    		GParticipant tmp = new GParticipant();
    		tmp.name = par.getName();
    		makeDraggable(tmp.getRoot(), scene.getWidth(), scene.getHeight());
    		gParticipantList.add(tmp);
    		tmp.getRoot().toBack();
    		tmp.participantNameLabel.setText(par.toString());
    		Position pos = par.getStartPosition();
    		tmp.getRoot().setTranslateX(pos.getX());
    		tmp.getRoot().setTranslateY(pos.getY());
    		pane.getChildren().add(tmp.getRoot());
    		
    	}
    }
    
    /**
     * Setup all GGeneralization from diagram input
     * @param generalizations list of generalization to be converted
     */
    public void setupMessage(List<UMLMessage> messages) {
        for (UMLMessage mes : messages) {
            List<Position> list = mes.getPoints();
            int len = list.size();

            //create anchors
            MyNodeAnchor start = new MyNodeAnchor(list.get(0).getX(), list.get(0).getY(), false);
            MyNodeAnchor end = new MyNodeAnchor(list.get(len -1).getX(), list.get(len -1).getY(), false);

            //create Gclass list
            List<MyNode> listNode = new ArrayList<>();
            for ( int i = 1; i < len - 1; i++){
                Position point = list.get(i);
                listNode.add(new MyNode(point.getX(), point.getY(), true));
            }
            
            GParticipant first = getParticipantByName(mes.getStartObject().getName());
            GParticipant second = getParticipantByName(mes.getEndObject().getName());
            GMessage message = new GMessage(first, second);
            message.setFromList(listNode, start, end);
//            start.polygonTriangle();
//            start.polygonSetRotatoin(gClassList.get(0));
            message.show(pane);
        }
    }
    
    /**
     * Setup all GClasses from diagram input
     * @param classes list of UMLClass to be converted
     */
    public void setupClasses(List<UMLClass> classes) {
        for (UMLClass c : classes) {
            //create Gclass object
            Gclass tmp = new Gclass(false);
            tmp.name = c.getName();
            makeDraggable(tmp.getRoot(), scene.getWidth(), scene.getHeight());
            gClassList.add(tmp);
            tmp.getRoot().toBack();
            //setup class data
            tmp.classLabel.setText(c.getName());
            Position pos = c.getPosition();
            tmp.getRoot().setTranslateX(pos.getX());
            tmp.getRoot().setTranslateY(pos.getY());
            pane.getChildren().add(tmp.getRoot());

            for (UMLAttribute attr : c.getAttributes()) {
                tmp.attrList.add(new Label(attr.toString()));
            }
            tmp.classVB.getChildren().addAll(tmp.attrList);
            tmp.classVB.getChildren().add(tmp.spacer);
            tmp.classVB.getChildren().add(tmp.separator2);

            for (UMLAttribute opr : c.getOperations()) {
                tmp.methodList.add(new Label(opr.toString()));
            }
            tmp.classVB.getChildren().addAll(tmp.methodList);
        }
    }

    /**
     * Setup all interfaces from diagram input
     * @param interfaces list of interfaces to be converted
     */
    public void setupInterfaces(List<UMLInterface> interfaces) {//TODO create separate list
        for (UMLInterface i : interfaces) {
            //create Gclass object
            Gclass tmp = new Gclass(false);
            makeDraggable(tmp.getRoot(), scene.getWidth(), scene.getHeight());
            gClassList.add(tmp);
            tmp.getRoot().toBack();
            tmp.classVB.getChildren().add(0,new Label("<<interface>>"));
            //setup class data
            tmp.classLabel.setText(i.getName());
            Position pos = i.getPosition();
            tmp.getRoot().setTranslateX(pos.getX());
            tmp.getRoot().setTranslateY(pos.getY());
            pane.getChildren().add(tmp.getRoot());


            for (UMLAttribute opr : i.getOperations()) {
                tmp.methodList.add(new Label(opr.toString()));
            }
            tmp.classVB.getChildren().addAll(tmp.methodList);
        }
    }

    /**
     * Setup all GAssociation from diagram input
     * @param associations list of associations to be converted
     */
    public void setupAssociation(List<RelAssociation> associations) {
        for (RelAssociation a : associations) {
            List<Position> list = a.getPoints();
            int len = list.size();

            //create anchors
            MyNodeAnchor start = new MyNodeAnchor(list.get(0).getX(), list.get(0).getY(), false);
            MyNodeAnchor end = new MyNodeAnchor(list.get(len -1).getX(), list.get(len -1).getY(), false);

            //create Gclass list
            List<MyNode> listNode = new ArrayList<>();
            for ( int i = 1; i < len - 1; i++){
                Position point = list.get(i);
                listNode.add(new MyNode(point.getX(), point.getY(), true));
            }
            Collections.reverse(listNode);
            //create association
            //select classes
            Gclass first = getClassByName(a.getLeftClass().getName());
            Gclass second = getClassByName(a.getRightClass().getName());
            GAssociation ass = new GAssociation(first, second);
            ass.setFromList(listNode, start, end);
            ass.setLabels(a.getLeftCardinality(),a.getRightCardinality(), a.getLabel());
            ass.setLabelName(a.getLabel(), a.getLabelPosition());
            pane.getChildren().add(ass.rLabel);
            ass.showLabels(start, end); //TODO implement search by class name
            ass.show(pane);
        }
    }

    /**
     * Setup all GAggregations from diagram input
     * @param aggregations list of aggregations to be converted
     */
    public void setupAggregation(List<RelAggregation> aggregations) {
        for (RelAggregation a : aggregations) {
            List<Position> list = a.getPoints();
            int len = list.size();

            //create anchors
            MyNodeAnchor start = new MyNodeAnchor(list.get(0).getX(), list.get(0).getY(), false);
            MyNodeAnchor end = new MyNodeAnchor(list.get(len -1).getX(), list.get(len -1).getY(), false);

            //create Gclass list
            List<MyNode> listNode = new ArrayList<>();
            for ( int i = 1; i < len - 1; i++){
                Position point = list.get(i);
                listNode.add(new MyNode(point.getX(), point.getY(), true));
            }
            //create association
            //select classes
            Gclass first = getClassByName(a.getLeftClass().getName());
            Gclass second = getClassByName(a.getRightClass().getName());
            GAggregation aggr = new GAggregation(first, second);
            aggr.setFromList(listNode, start, end);
            aggr.setLabels(a.getLeftCardinality(),a.getRightCardinality());
            aggr.showLabels(start, end); //TODO implement search by class name
            start.polygonSquare();
            start.polygonSetRotatoin(first);
            aggr.show(pane);
        }
    }

    /**
     * Setup all GGeneralization from diagram input
     * @param generalizations list of generalization to be converted
     */
    public void setupGeneralization(List<RelGeneralization> generalizations) {
        for (RelGeneralization g : generalizations) {
            List<Position> list = g.getPoints();
            int len = list.size();

            //create anchors
            MyNodeAnchor start = new MyNodeAnchor(list.get(0).getX(), list.get(0).getY(), false);
            MyNodeAnchor end = new MyNodeAnchor(list.get(len -1).getX(), list.get(len -1).getY(), false);

            //create Gclass list
            List<MyNode> listNode = new ArrayList<>();
            for ( int i = 1; i < len - 1; i++){
                Position point = list.get(i);
                listNode.add(new MyNode(point.getX(), point.getY(), true));
            }
            //create association
            //select classes
            Gclass first = getClassByName(g.getLeftClass().getName());
            Gclass second = getClassByName(g.getRightClass().getName());
            GGeneralization generalization = new GGeneralization(first, second);
            generalization.setFromList(listNode, start, end);
            start.polygonTriangle();
            start.polygonSetRotatoin(gClassList.get(0));
            generalization.show(pane);
        }
    }

    /**
     * Observer method
     * @param  o observable object
     * @param arg argument that was sent
     */
    @Override
    public void update(java.util.Observable o, Object arg) {
        System.out.println("Update called with Arguments: " + arg);
    }

    /**
     * Startup GUI
     */
    public void Start(){
        launch();
    }
    /**
     * Application start
     */
    @Override
    public void start(Stage orig) throws IOException {
        ClassDiagram diagram = new ClassDiagram("ClassDiagram");
        Reader.startReading(diagram);


        //*****GUI START*****//
        //setup initial look
//        Image icon = new Image("icon.png");
//        stage.getIcons().add(icon);
//        stage.setTitle("ER DIAGRAM EDITOR FOR IJA");


        stage = new Stage();


        //create buttons and button bar
        ButtonBar buttonBar = new ButtonBar();
        Button addClass = new Button("Add Class");
        buttonBar.getButtons().add(addClass);

        Button editClass = new Button("Edit Class");
//        editClass.setDisable(true);
        buttonBar.getButtons().add(editClass);

        Button addAssociation = new Button("Add AssociationClass");
        addAssociation.setDisable(true);
        buttonBar.getButtons().add(addAssociation);

        Button addGeneralization = new Button("Add Generalization");
        addGeneralization.setDisable(true);
        buttonBar.getButtons().add(addGeneralization);

        Button addAggregation = new Button("Add Aggregation");
        addAggregation.setDisable(true);
        buttonBar.getButtons().add(addAggregation);

        Button deleteteRelation = new Button("Delete Relation");
        deleteteRelation.setDisable(true);
        buttonBar.getButtons().add(deleteteRelation);
        
//        setupClasses(diagram.getClasses());
//        setupInterfaces(diagram.getInterfaces());
//        setupAssociation(diagram.getAssociations());
//        setupAggregation(diagram.getAggregations());
//        setupGeneralization(diagram.getGeneralizations());
        
        // TODO -> tady tohle je pro sekvenci
        setupParticipants(diagram.getSequenceDiagram("SequenceDiagram0").getParticipants());
        setupMessage(diagram.getSequenceDiagram("SequenceDiagram0").getMessages());

        pane.setStyle("-fx-background-color: grey; -fx-border-color: black");

        HBox hbox = new HBox();
        hbox.getChildren().addAll(buttonBar);
        vBox.getChildren().addAll(hbox, pane);


        vBox.prefWidthProperty().bind(stage.widthProperty().multiply(0.80));

        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); //TODO better size
        VBox.setVgrow(pane, Priority.ALWAYS);




        //event handler for button create class
        EventHandler<MouseEvent> eve = new EventHandler<>(){
            @Override public void handle(MouseEvent e) {
//                addClass.setEffect(null);
                System.out.println("Created new Class");
                Gclass tmpClass = new Gclass(true);
                makeDraggable(tmpClass.getRoot(), scene.getWidth(), scene.getHeight());
                gClassList.add(tmpClass);
                pane.getChildren().add(0,tmpClass.getRoot());

            }
        };
        addClass.addEventHandler(MouseEvent.MOUSE_CLICKED, eve);
        
        GUIMain gui = this;
        
        EventHandler<MouseEvent> event = new EventHandler<>(){
            @Override public void handle(MouseEvent e) {
            	DeleteController.DeleteInterface("OperaceVozidlo", gui, diagram);
            }
        };
        editClass.addEventHandler(MouseEvent.MOUSE_CLICKED, event);

        stage.setScene(scene);
        stage.show();
    }




    private double startX;
    private  double startY;
    /**
     * Makes the Node draggable with movement restriction
     * @param node Node to be made draggable
     * @param maxH maximum height
     * @param maxW maximum width
     */
    private void makeDraggable(Group node, double maxW, double maxH) {

        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                startX = e.getSceneX() - node.getTranslateX();
                startY = e.getSceneY() - node.getTranslateY();
            }
        });

        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                double X = e.getSceneX() - startX;
                double Y = e.getSceneY() - startY;
                if (X > maxW)
                    X = maxW - 10;
                if (X < 0)
                    X = 0;
                if (Y > maxH)
                    Y = maxH - 10;
                if (Y < 0)
                    Y = 0;
                node.setTranslateX(X);
                node.setTranslateY(Y);
            }
        });
    }
    
    public void clearView() {
    	pane.getChildren().clear();
    }
    
    public void upadeView() {
    	stage.setScene(scene);
        stage.show();
    }
}

//        Label lwindowSize = new Label("LABEL");
//        lwindowSize.setTranslateX(600);
//        pane.getChildren().add(lwindowSize);
//        lwindowSize.textProperty().bind(Bindings.format(" Size: %.0f x %.0f", scene.widthProperty(), scene.heightProperty()));

//        Label testingLabel = new Label("Testing LABEL");
//        testingLabel.setTranslateX(600);
//        testingLabel.setTranslateY(20);
//        pane.getChildren().add(testingLabel);
//        testingLabel.textProperty().bind(Bindings.format(" Test: %.0f x %.0f", c1.root.translateXProperty(), c1.root.translateYProperty()));
//        double X = scene.widthProperty().getValue();