package gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import uml.classDiagram.*;
import uml.pos.Position;
import uml.relations.RelAggregation;
import uml.relations.RelAssociation;
import uml.relations.RelGeneralization;
import uml.sequenceDiagram.UMLMessage;
import uml.sequenceDiagram.UMLParticipant;
import workers.Reader;

import java.io.IOException;
import java.util.*;


/**
 * GUIMain represents GUI extends Application implements Observer
 *
 * @author  Adam Hos
 * @version 1.0
 */
public class GUIMain extends Application implements Observer {

    VBox vBoxCD;
    VBox vBoxSD;
    Pane pane;
    List<Gclass> gClassList;
    // TODO -> tady tohle je pro sekvencni
    List<GParticipant> gParticipantList = new ArrayList<>();
    Scene scene;
    Stage stage;
    ClassDiagram diagram;
    Gclass selectedGclass;
    ComboBox<String> comboBoxSDSelection;
    HBox hboxCD;
    Button editClass;
    Button deleteClass;
    Button goToSD;

    /**
     * Constructor for GUIMain. Allocate memory, set scene
     */
    public GUIMain() {
        vBoxCD = new VBox();
        pane = new Pane();
        gClassList = new ArrayList<>();
        scene = new Scene(vBoxCD, 1000, 600);
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

    public void setupParticipants(List<UMLParticipant> participants) {
    	for (UMLParticipant par : participants) {
    		GParticipant tmp = new GParticipant();
            makeGParticipantDraggable(tmp, scene.getWidth(), scene.getHeight());
    		tmp.name = par.getName();
//    		makeDraggable(tmp.getRoot(), scene.getWidth(), scene.getHeight());
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
            Gclass tmp = new Gclass(false, false);
            setGClassSelectEventHandler(tmp);
            makeGClassDraggable(tmp, scene.getWidth()- 240, scene.getHeight());
            gClassList.add(tmp);
            tmp.getRoot().toBack();

            //setup class data
            tmp.classLabel.setText(c.getName());
            Position pos = c.getPosition();
            tmp.getRoot().setTranslateX(pos.getX());
            tmp.getRoot().setTranslateY(pos.getY());
            pane.getChildren().add(tmp.getRoot());

            for (UMLAttribute attr : c.getAttributes()) {
                tmp.attrListLabel.add(new Label(attr.toString()));
            }
            tmp.classVB.getChildren().addAll(tmp.attrListLabel);
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
            Gclass tmp = new Gclass(true, false);
            setGClassSelectEventHandler(tmp);
            makeGClassDraggable(tmp, scene.getWidth() - 240, scene.getHeight());
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
            first.relationList.add(a);
            Gclass second = getClassByName(a.getRightClass().getName());
            second.relationList.add(a);
            GAssociation ass = new GAssociation(first, second);
            ass.name = a.getName();
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
//            aggr.showLabels(start, end); //TODO implement search by class name
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
            first.generalizationList.add(g);
            Gclass second = getClassByName(g.getRightClass().getName());
            second.generalizationList.add(g);
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

    public void setupFromDiagram(ClassDiagram diagram){
        pane.getChildren().clear();
        gClassList.clear();
        pane.setStyle("-fx-background-color: grey; -fx-border-color: black");
        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); //TODO better size
        setupClasses(diagram.getClasses());
        setupInterfaces(diagram.getInterfaces());
        setupAssociation(diagram.getAssociations());
        setupAggregation(diagram.getAggregations());
        setupGeneralization(diagram.getGeneralizations());
    }

    /**
     * Application start
     */
    @Override
    public void start(Stage orig) throws IOException {


        //*****GUI START*****//
        //setup initial look
//        Image icon = new Image("icon.png");
//        stage.getIcons().add(icon);
//        stage.setTitle("ER DIAGRAM EDITOR FOR IJA");


        stage = new Stage();
        stage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                editClass.setDisable(true);
                deleteClass.setDisable(true);
            }
        });
        //create buttons and button bar
        ButtonBar buttonBar = new ButtonBar();
        Button addClass = new Button("Add Class");
        addClass.setOnAction(e ->{
            String name = CreateClassWindow.display();
            if (name != null && !name.equals("")) {
                diagram.createClass(name);
                setupFromDiagram(diagram);
            }
        });
        buttonBar.getButtons().add(addClass);

        editClass = new Button("Edit Class");
        editClass.setDisable(true);
        editClass.setOnAction(e -> {
            int GclassHeightdiff = 0;
            if (selectedGclass.isinterface){
                GclassHeightdiff = ECW.display(diagram.getInterface(selectedGclass.classLabel.getText()));
            }
            else {
                GclassHeightdiff = ECW.display(diagram.getClass(selectedGclass.classLabel.getText()));
            }
            System.out.println(selectedGclass.generalizationList.size());
            if (GclassHeightdiff != 0)
                repairGclassBottomBorderRelations(selectedGclass, GclassHeightdiff);
            setupFromDiagram(diagram);
            editClass.setDisable(true);
            deleteClass.setDisable(true);
        });
        buttonBar.getButtons().add(editClass);

        deleteClass = new Button("Delete Class");
        deleteClass.setDisable(false);
        GUIMain gui = this;

        deleteClass.addEventHandler(MouseEvent.MOUSE_CLICKED,  new EventHandler<>(){
            @Override public void handle(MouseEvent e) {
                selectedGclass.delete(gui,diagram);
                setupFromDiagram(diagram);
            }
        });
        buttonBar.getButtons().add(deleteClass);

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

        ///READING FROM INPUT FILE
        diagram = new ClassDiagram("ClassDiagram");
        Reader.startReading(diagram);
        setupFromDiagram(diagram);

        comboBoxSDSelection = new ComboBox<>();
        comboBoxSDSelection.setPromptText("Select Sequence diagram");
        comboBoxSDSelection.setMinWidth(200);
        comboBoxSDSelection.setOnAction(e ->{
            goToSD.setDisable(false);
        });
        for( var seq : diagram.getSequenceDiagrams()){
            comboBoxSDSelection.getItems().add(seq.getName());
        }
        goToSD = new Button("->");
        goToSD.setDisable(true);
        goToSD.setMinWidth(40);
        goToSD.setOnAction(e -> {
            if (!comboBoxSDSelection.getValue().equals("Select Sequence diagram"))
                SwitchToSDContext(comboBoxSDSelection.getValue());
        });
//        goToSD.setDisable(true);

        hboxCD = new HBox();
        hboxCD.getChildren().addAll(buttonBar, comboBoxSDSelection, goToSD);
        vBoxCD.getChildren().add(hboxCD);
        vBoxCD.getChildren().add(pane);




        vBoxCD.prefWidthProperty().bind(stage.widthProperty().multiply(0.80));

        VBox.setVgrow(pane, Priority.ALWAYS);



        stage.setScene(scene);
        stage.setTitle("Diagram");
        stage.show();
        setupFromDiagram(diagram);
        stage.setOnCloseRequest(e -> ClosingProgram());
    }

    /**
     * Switch to Sequence Diagram context
     * @param SDname name of Sequence Diagram
     */
    private void SwitchToSDContext(String SDname) {
        System.out.println(SDname);
        pane.getChildren().clear();
        vBoxSD = new VBox();
        Group root = new Group();
//        ButtonBar buttonBar = new ButtonBar();
        Button returnButton = new Button("<- Return");
        returnButton.setOnAction(e -> {
            SwitchToCDContext();
        });
        Label addParticipantL = new Label("Add Participant:");


        ComboBox<String> addParticipantCB = new ComboBox<>();
        var SD = diagram.getSequenceDiagram(SDname);
        setupParticipants(SD.getParticipants());

        for (var m : SD.getMessages()){
            System.out.println(m.getID() + " " + m.getName());
        }

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.getChildren().addAll(returnButton, addParticipantL);
        vBoxSD.getChildren().add(hBox);
        vBoxSD.getChildren().add(pane);
        Scene x = new Scene(vBoxSD, 1000, 600);

        GActivationBox act1 = new GActivationBox(50, 300);
        GActivationBox act2 = new GActivationBox(100, 200);
        gParticipantList.get(0).dashedStart.getChildren().add(act1.root);
        gParticipantList.get(1).dashedStart.getChildren().add(act2.root);
        stage.setScene(x);
    }

    private void SwitchToCDContext() {
//        comboBoxSDSelection = null;
        pane.getChildren().clear();
        vBoxCD.getChildren().add(pane);
        comboBoxSDSelection.setValue("Select Sequence diagram");
        goToSD.setDisable(true);
        setupFromDiagram(diagram);
        stage.setScene(scene);
    }

    private void repairGclassBottomBorderRelations(Gclass selectedGclass, int diff) {
        for (var g : selectedGclass.generalizationList){
            Position point;
            var Points = g.getPoints();
            if (g.getLeftClass().getName() == selectedGclass.getName())
                point = Points.get(0); //get first point
            else
                point = Points.get(Points.size() - 1); //get last
            if (selectedGclass.isPointOnBottom(point, diff))
                point.setY(point.getY() + diff);
        }
    }

    private void ClosingProgram(){
        System.out.println("Finished <- last function call\n");
    }
    public void clearView() {
        pane.getChildren().clear();
    }

    public void upadeView() {
        stage.setScene(scene);
        stage.show();
    }

    private double startX;
    private  double startY;
    /**
     * Makes the Node draggable with movement restriction
     * @param gclass Gclass to be made draggable
     * @param maxH maximum height
     * @param maxW maximum width
     */
    private void makeGClassDraggable(Gclass gclass, double maxW, double maxH) {

        gclass.root.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                startX = e.getSceneX() - gclass.root.getTranslateX();
                startY = e.getSceneY() - gclass.root.getTranslateY();

            }
        });

        gclass.root.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
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
                gclass.root.setTranslateX(X);
                gclass.root.setTranslateY(Y);
            }
        });

        //released mouse drag, need to update UMLClass
        gclass.root.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (!gclass.isinterface) {
                    diagram.getClass(gclass.classLabel.getText()).setPosition(
                            (int) gclass.root.getTranslateX(),
                            (int) gclass.root.getTranslateY());
                }
                else{
                    diagram.getInterface(gclass.classLabel.getText()).setPosition(
                            (int) gclass.root.getTranslateX(),
                            (int) gclass.root.getTranslateY());
                }
            }
        });
    }

    private void makeGParticipantDraggable(GParticipant gParticipant, double maxW, double maxH) {

        gParticipant.root.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                startX = e.getSceneX() - gParticipant.root.getTranslateX();
            }
        });

        gParticipant.root.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                double X = e.getSceneX() - startX;
                if (X > maxW)
                    X = maxW - 10;
                if (X < 0)
                    X = 0;
                gParticipant.root.setTranslateX(X);
            }
        });
    }


    private void setGClassSelectEventHandler(Gclass gclass){
        gclass.root.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selectedGclass = gclass;
                deleteClass.setDisable(false);
                editClass.setDisable(false);
                e.consume();
            }
        });
    }
}
