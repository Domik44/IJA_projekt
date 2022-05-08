package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import uml.classDiagram.*;
import uml.pos.Position;
import uml.relations.RelAggregation;
import uml.relations.RelAssociation;
import uml.relations.RelGeneralization;
import uml.sequenceDiagram.SequenceDiagram;
import uml.sequenceDiagram.UMLMessage;
import uml.sequenceDiagram.UMLParticipant;
import workers.Reader;

import java.io.IOException;
import java.util.*;

import static java.lang.System.exit;

import controller.DeleteController;
import controller.EditController;
import controller.AddController;
import controller.UIAction;


/**
 * GUIMain represents GUI extends Application implements Observer
 *
 * @author  Adam Hos
 * @version 1.1
 */
public class GUIMain extends Application implements Observer {


    //Fields for both diagrams
    Scene scene;
    Stage stage;
    Pane pane;
    public static int state;

    //Fields for ClassDiagram
    VBox vBoxCD;
    List<Gclass> gClassList;
    static ClassDiagram diagram;
    public static Gclass selectedGclass1;
    public static Gclass selectedGclass2;
    static Object selectedRelation;
    HBox hboxCD;
    static Button addClass;
    static Button addInterface;
    static Button editClass;
    static Button deleteClass;
    static Button addGeneralization;
    static Button addAggregation;
    static Button addAssociation;
    static Button cancelCD;;
    ComboBox<String> comboBoxSDSelection;
    Button goToSD;
    public static List<Position> positionList = new ArrayList<>();
    public static String relationType;
    public static String LCardinality;
    public static String RCardinality;
    public static String relationName;
    private Button deleteteRelation;

    //Fields for SequenceDiagram
    VBox vBoxSD;
    List<GParticipant> gParticipantList = new ArrayList<>();
    public static SequenceDiagram SD;
    private GParticipant selectedParticipant1;
    private GParticipant selectedParticipant2;
    private Button returnButton;
    private Button addParticipant;
    private Button addSynchronous;
    private Button addAsynchronous;
    private Button addCreate;
    private Button addReturn;
    private Button addDelete;
    private Button addBox;
    private Button cancelSD;
    private Button delete;
    private Object selectForDelete;
    private String messageText;
    private String messageType;
    
    ArrayDeque<UIAction> history = new ArrayDeque<>();
    DeleteController deleteControl = new DeleteController();
    AddController addControl = new AddController();
    EditController editControl = new EditController();
    

    /**
     * Constructor for GUIMain. Allocate memory, set scene
     */
    public GUIMain() {
        vBoxCD = new VBox();
        pane = new Pane();
        gClassList = new ArrayList<>();
        scene = new Scene(vBoxCD, 1600, 860);
    }

    public void createRelation() {
        if (relationType == "Generalization"){
            createGeneralization();
        }
        else if (relationType == "Aggregation"){
            createAggregation();
        }
        else if (relationType == "Association"){
            createAssociation();
        }
    }

    private void createGeneralization() {
    	var action = this.addControl.new AddGeneralization(this, diagram);
    	run(action);
    }
    private void createAggregation() {
        RelAggregation a = diagram.createAggregation(selectedGclass1.getName(), selectedGclass2.getName(), relationType);
        fixBorderPoints();
        for (var p : positionList){
            a.addPosition(p);
        }
        a.setCardinality(LCardinality, RCardinality);
        a.setLabel(relationName);
        //place label into midle of start and end nodes
        a.setLabelPosition(50,50);
        setupFromDiagram(diagram);
    }

    private void createAssociation() {
        RelAssociation a = diagram.createAssociation(selectedGclass1.getName(), selectedGclass2.getName(), relationType);
        fixBorderPoints();
        for (var p : positionList){
            a.addPosition(p);
        }
        a.setCardinality(LCardinality, RCardinality);
        a.setLabel(relationName);
        //place label into midle of start and end nodes
        a.setLabelPosition(50,50);
        setupFromDiagram(diagram);
    }

    /**
     * Calculate to witch part of Gclass border should the points snap to
     */
    public void fixBorderPoints() {
        fixBorderPoint(selectedGclass1, positionList.get(0));
        fixBorderPoint(selectedGclass2, positionList.get(positionList.size()-1));
    }

    /**
     * Calculate to witch part of Gclass border should the point snap to
     * @param gclass gclass that has point
     * @param point point to be fixed
     */
    private void fixBorderPoint(Gclass gclass, Position point) {
        int borderWidth = 10;
        Double[] arr = {
                0. + point.getX(),
                gclass.getWidth() - point.getX(),
                0. + point.getY(),
                gclass.getHeight() - point.getY()
        };

        var min = Collections.min(Arrays.asList(arr));

        if ( min == point.getX())
            point.setX(0);
        else if(min == point.getY())
            point.setY(0);
        else if(min ==  gclass.getWidth() - point.getX())
            point.setX((int)gclass.getWidth());
        else
            point.setY((int)gclass.getHeight());
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

    /**
     * Setup all GClasses from diagram input
     * @param classes list of UMLClass to be converted
     */
    public void setupClasses(List<UMLClass> classes) {
        for (UMLClass c : classes) {
            //create Gclass object
            Gclass tmp = new Gclass(false);
            addGClassSelectedHandler(tmp);
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

            //ADD OBSERVER FOR PAGE REFRESH AFTER RELATION CRETE FINISHED
            tmp.addObserver((Observable o, Object arg) -> {
                createRelation();
            });
        }
    }


    /**
     * Setup all interfaces from diagram input
     * @param interfaces list of interfaces to be converted
     */
    public void setupInterfaces(List<UMLInterface> interfaces) {//TODO create separate list
        for (UMLInterface i : interfaces) {
            //create Gclass object
            Gclass tmp = new Gclass(true);
            addGClassSelectedHandler(tmp);
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

            //ADD OBSERVER FOR PAGE REFRESH AFTER RELATION CRETE FINISHED
            tmp.addObserver((Observable o, Object arg) -> {
                createRelation();
            });
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
            //create association
            //select classes
            Gclass first = getClassByName(a.getLeftClass().getName());
            first.relationList.add(a);
            Gclass second = getClassByName(a.getRightClass().getName());
            second.relationList.add(a);
            GAssociation ass = new GAssociation(first, second, a.getName());
//            ass.name = a.getName();
            ass.setFromList(listNode, start, end);
            ass.setLabels(a.getLeftCardinality(),a.getRightCardinality(), a.getLabel());
            ass.setLabelName(a.getLabel(), a.getLabelPosition());
            pane.getChildren().add(ass.rLabel);
            ass.showLabels(start, end);

            addRelationSelectedHandler(ass);

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
            first.relationList.add(a);
            Gclass second = getClassByName(a.getRightClass().getName());
            second.relationList.add(a);
            GAggregation aggr = new GAggregation(first, second, a.getName());

            aggr.setFromList(listNode, start, end);
            aggr.setLabels(a.getLeftCardinality(),a.getRightCardinality(), a.getLabel());
            aggr.setLabelName(a.getLabel(), a.getLabelPosition());
            pane.getChildren().add(aggr.rLabel);
            aggr.showLabels(start, end);
            addRelationSelectedHandler(aggr);

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
            first.relationList.add(g);
            Gclass second = getClassByName(g.getRightClass().getName());
            second.relationList.add(g);
            GGeneralization generalization = new GGeneralization(first, second, g.getName());
            generalization.setFromList(listNode, start, end);
            start.polygonTriangle();
            start.polygonSetRotatoin(first);

            addRelationSelectedHandler(generalization);

            generalization.show(pane);
        }
    }

    private void addRelationSelectedHandler(Object o) {
        if( o instanceof GRelationAbstract){
            var casted = (GRelationAbstract)o;
            for( var node : casted.connection.between){
                node.g.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    if (state == 0) {
                        selectedRelation = o;
                        deleteteRelation.setDisable(false);
                        editClass.setDisable(true);
                        deleteClass.setDisable(true);
                        e.consume();
                    }
                });
            }
            casted.connection.start.g.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (state == 0) {
                    selectedRelation = o;
                    deleteteRelation.setDisable(false);
                    editClass.setDisable(true);
                    deleteClass.setDisable(true);
                    e.consume();
                }
            });
        }
    }



    /**
     * Setup all Gparticipants from diagram input
     * @param participants list of participants
     */
    public void setupParticipants(List<UMLParticipant> participants) {
        for (UMLParticipant par : participants) {
            GParticipant tmp = new GParticipant();
            //selected event handler
            makeGParticipantDraggableAndSelected(tmp, scene.getWidth(), scene.getHeight());
            tmp.name = par.getName();

            gParticipantList.add(tmp);
            tmp.getRoot().toBack();
            tmp.participantNameLabel.setText(par.toString());
            Position pos = par.getStartPosition();
            tmp.getRoot().setTranslateX(pos.getX());
            tmp.getRoot().setTranslateY(pos.getY());
            pane.getChildren().add(tmp.getRoot());

            //setup ActivationBoxes
            for (var box : par.getBoxes()){
                GActivationBox Gbox = new GActivationBox(box.getStartPosition().getY(),box.getEndPosition().getY(), box.getID());
                tmp.dashedStart.getChildren().add(Gbox.root);
                Gbox.root.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
                    selectForDelete = Gbox;
                    delete.setDisable(false);
                    e.consume();
                });
            }

            tmp.addObserver((Observable o, Object arg) -> {
                if (arg instanceof Position){
                    var p = (Position)arg;
                    par.setStartPosition(p.getX(), p.getY());
                    setupFromSEQDiagram(SD);
                }
            });

            setDashedEvents(tmp);
        }
    }

    /**
     * Setup all GMessages from diagram input
     * @param participants list of messages
     */
    private void setupMessages(List<UMLMessage> messages) {
        for( var m : SD.getMessages()){
            try {
                var par1 = gParticipantList.stream().filter(participant -> m.getStartObject().getName().equals(participant.name)).findAny().orElse(null);
                var par2 = gParticipantList.stream().filter(participant -> m.getEndObject().getName().equals(participant.name)).findAny().orElse(null);
                GMessage Gm = new GMessage(m.getID(),par1,par2);
                //create new Gmessage
                var Points = m.getPoints();
                MyNodeAnchor a1 = new MyNodeAnchor(0, Points.get(0).getY(), false);
                MyNodeAnchor a2 = new MyNodeAnchor(0, 0, false);
                Gm.setFromNodes(a1, a2);
                a1.c.setRadius(5);
                Gm.makeConnectionDraggableVerticaly(Gm.connection, pane.getHeight());
                Gm.setLabelName(m.getName());
                Gm.messageName.translateYProperty().bind(a1.getYprop().add(-20));
                Gm.show(pane);
                //center label
                Platform.runLater(() -> {
                    var half = a2.getXprop().subtract(a1.getXprop()).divide(2);
//                    Gm.messageName.translateXProperty().bind(a1.getXprop().add(half).subtract(Gm.messageName.getWidth() / 2));
                    double magicalCenteringConstant = 3.6;
                    Gm.messageName.translateXProperty().bind(a1.getXprop().add(half).subtract(Gm.messageName.getText().length()*magicalCenteringConstant));
                });

                Gm.setStyle(m);

                //setup DELETE handlers
                Gm.connection.start.g.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
                    selectForDelete = Gm;
                    delete.setDisable(false);
                    e.consume();
                });
                Gm.connection.end.g.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
                    selectForDelete = Gm;
                    delete.setDisable(false);
                    e.consume();
                });
                Gm.messageName.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
                    selectForDelete = Gm;
                    delete.setDisable(false);
                    e.consume();
                });

            }
            catch (Exception e){
                System.out.println("Sequence diagram inconsistent input during relation: " + m.getName());
                exit(-1);
            }
        }
    }

    /**
     * Setup pane from ClassDiagram (redraw all Gclasses (relevant to this diagram))
     * @param  diagram ClassDiagram that will be read from
     */
    public void setupFromDiagram(ClassDiagram diagram){
        pane.getChildren().clear();
        gClassList.clear();
        pane.setStyle("-fx-background-color: lightgrey; -fx-border-color: black");
        pane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); //TODO better size
        setupClasses(diagram.getClasses());
        setupInterfaces(diagram.getInterfaces());
        setupAssociation(diagram.getAssociations());
        setupAggregation(diagram.getAggregations());
        setupGeneralization(diagram.getGeneralizations());
    }

    /**
     * Setup pane from SequenceDiagram (redraw all Gclasses (relevant to this diagram))
     * @param  SD SequenceDiagram that will be read from
     */
    private void setupFromSEQDiagram(SequenceDiagram SD) {
        gParticipantList.clear();
        pane.getChildren().clear();
        setupParticipants(SD.getParticipants());
        setupMessages(SD.getMessages());
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
        //*****GUI START*****//
        //setup initial look
//        Image icon = new Image("icon.png");
//        stage.getIcons().add(icon);
//        stage.setTitle("ER DIAGRAM EDITOR FOR IJA");

        state = 0;
        stage = new Stage();
        stage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                editClass.setDisable(true);
                deleteClass.setDisable(true);
                deleteteRelation.setDisable(true);
//                if (delete != null)
//                    delete.setDisable(true);
            }
        });
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (state == 2){
                    GUIMain.positionList.add(new Position((int)e.getX(),(int) e.getY()));
                }
            }
        });

        //create buttons and button bar
        ButtonBar buttonBar = new ButtonBar();
        addClass = new Button("Add Class");
        addClass.setOnAction(e ->{
        	String name = CreateClassWindow.display();
            if (name != null && !name.equals("")) {
            	  var action = this.addControl.new AddClass(this, diagram, name);
            	  run(action);
            }
        });
        buttonBar.getButtons().add(addClass);
        
        addInterface = new Button("Add Interface");
        addInterface.setOnAction(e ->{ // TODO predelat na action
            String name = CreateClassWindow.display();
            if (name != null && !name.equals("")) {
          	  var action = this.addControl.new AddInterface(this, diagram, name);
          	  run(action);
          }
        });
        buttonBar.getButtons().add(addInterface);

        editClass = new Button("Edit Class");
        editClass.setDisable(true);
        editClass.setOnAction(e -> {
            if (selectedGclass1.isinterface){
                var action = this.editControl.new EditInterface(this, diagram, selectedGclass1.classLabel.getText());
          	  	run(action);
            }
            else {
            	var action = this.editControl.new EditClass(this, diagram, selectedGclass1.classLabel.getText());
          	  	run(action);
            }

            editClass.setDisable(true);
            deleteClass.setDisable(true);
        });
        buttonBar.getButtons().add(editClass);

        deleteClass = new Button("Delete Class");
        deleteClass.setDisable(true);
        GUIMain gui = this;

        deleteClass.setOnAction(e ->{
        	if(selectedGclass1.isinterface) {
        		var action = this.deleteControl.new DeleteInterface(this, diagram, selectedGclass1.getName());
        		run(action);
        	}
        	else {
        		var action = this.deleteControl.new DeleteClass(this, diagram, selectedGclass1.getName());
        		run(action);
        	}
        });
        buttonBar.getButtons().add(deleteClass);

        addAssociation = new Button("Add Association");
        buttonBar.getButtons().add(addAssociation);
        addAssociation.setOnAction(e -> {
            var retArr = AddRelationWithLabelsWindow.display("Add Association");
            if (retArr != null) {
                LCardinality = retArr[0];
                relationName = retArr[1];
                RCardinality = retArr[2];
                AddRelationSetupButtons();
                state = 1;
                relationType = "Association";
            }
        });

        addAggregation = new Button("Add Aggregation");
        buttonBar.getButtons().add(addAggregation);
        addAggregation.setOnAction(e -> {
            var retArr = AddRelationWithLabelsWindow.display("Add Aggregation");
            if (retArr != null) {
                LCardinality = retArr[0];
                relationName = retArr[1];
                RCardinality = retArr[2];
                AddRelationSetupButtons();
                state = 1;
                relationType = "Aggregation";
            }
        });

        addGeneralization = new Button("Add Generalization");
        buttonBar.getButtons().add(addGeneralization);
        addGeneralization.setOnAction(e -> {
            AddRelationSetupButtons();
            state = 1;
            relationType = "Generalization";
        });





        deleteteRelation = new Button("Delete Relation");
        deleteteRelation.setDisable(true);
        buttonBar.getButtons().add(deleteteRelation);
        deleteteRelation.setOnAction(e -> { // TODO -> predelat na aciton
            if(selectedRelation instanceof GGeneralization){
            	var action = this.deleteControl.new DeleteGeneralization(this, diagram, ((GGeneralization)selectedRelation).name);
                run(action);
            	deleteteRelation.setDisable(true);
            }
            else if(selectedRelation instanceof GAssociation){
                var action = this.deleteControl.new DeleteAssociation(this, diagram, ((GAssociation)selectedRelation).name);
                run(action);
                deleteteRelation.setDisable(true);
            }
            else if(selectedRelation instanceof GAggregation){
            	var action = this.deleteControl.new DeleteAggregation(this, diagram, (((GAggregation)selectedRelation).name));
                run(action);
                deleteteRelation.setDisable(true);
            }
        });

        cancelCD = new Button("Cancel");
        cancelCD.setDisable(true);
        buttonBar.getButtons().add(cancelCD);
        cancelCD.setOnAction(e -> {
            AddRelationENDSetupButtons();
            state = 0;
        });


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
        
        
        Button undoButton = new Button("Undo");
        buttonBar.getButtons().add(undoButton);

        undoButton.setOnAction(e ->{
        	undo();
        });

        hboxCD = new HBox();
        hboxCD.getChildren().addAll(buttonBar, comboBoxSDSelection, goToSD);
        vBoxCD.getChildren().add(hboxCD);
        vBoxCD.getChildren().add(pane);




        vBoxCD.prefWidthProperty().bind(stage.widthProperty().multiply(0.80));

        VBox.setVgrow(pane, Priority.ALWAYS);



        stage.setScene(scene);
        stage.setTitle("Diagram");
        stage.show();
//        setupFromDiagram(diagram);
        stage.setOnCloseRequest(e -> ClosingProgram());
    }

    private Position starPoint;
    private void setDashedEvents(GParticipant gParticipant){
        gParticipant.root.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if(state == 0)
                return;
            //Create message events
            if(state == 1){
                selectedParticipant1 = gParticipant;
                state ++;
                starPoint = new Position(0,(int) e.getY());
            }
            else if(state == 2){
                state = 0;
                AddMessageENDSetupButtons();
                if (selectedParticipant1 == gParticipant) //selected the same GParticipant twice, invalid -> return
                    return;
                selectedParticipant2 = gParticipant;
                createGMessage(starPoint, messageText, messageType);

            }
            //Create Box events
            else if(state == 5){
                selectedParticipant1 = gParticipant;
                state ++;
                starPoint = new Position(0,(int) e.getY());
            }
            else if(state == 6){
                if (selectedParticipant1 != gParticipant) //selected another GParticipant twice, invalid -> return
                    return;
                state = 0;
                var endPoint = new Position(0,(int) e.getY());
                //create new box
                var box = SD.createActivationBox(selectedParticipant1.name);
                GActivationBox Gbox = new GActivationBox(starPoint.getY(),endPoint.getY(), box.getID());
                box.setStartPosition(0, starPoint.getY());
                box.setEndPosition(0, endPoint.getY());
                selectedParticipant1.dashedStart.getChildren().add(Gbox.root);
                AddMessageENDSetupButtons();
            }
            e.consume();
        });
    }

    private void createGMessage(Position lineStart, String messageText, String messageType) {
        UMLMessage m = SD.createMessage(selectedParticipant1.name,
                selectedParticipant2.name,
                messageText,
                messageType);
        if(lineStart.getY() < 50)
            lineStart.setY(50);
        m.addPosition(lineStart);
        setupFromSEQDiagram(SD);
    }

    public static void AddRelationSetupButtons(){
        addClass.setDisable(true);
        addInterface.setDisable(true);
        editClass.setDisable(true);
        deleteClass.setDisable(true);
        addGeneralization.setDisable(true);
        addAggregation.setDisable(true);
        addAssociation.setDisable(true);
        cancelCD.setDisable(false);
    }
    public static void AddRelationENDSetupButtons(){
        addClass.setDisable(false);
        addInterface.setDisable(false);
        editClass.setDisable(true);
        deleteClass.setDisable(true);
        addGeneralization.setDisable(false);
        addAggregation.setDisable(false);
        addAssociation.setDisable(false);
        cancelCD.setDisable(true);
    }

    public void AddMessageSetupButtons(){
        returnButton.setDisable(true);
        addSynchronous.setDisable(true);
        addAsynchronous.setDisable(true);
        addCreate.setDisable(true);
        addReturn.setDisable(true);
        addDelete.setDisable(true);
        addParticipant.setDisable(true);
        addBox.setDisable(true);
        cancelSD.setDisable(false);
        delete.setDisable(true);
    }

    public void AddMessageENDSetupButtons(){
        returnButton.setDisable(false);
        addSynchronous.setDisable(false);
        addAsynchronous.setDisable(false);
        addCreate.setDisable(false);
        addReturn.setDisable(false);
        addDelete.setDisable(false);
        addParticipant.setDisable(false);
        addBox.setDisable(false);
        cancelSD.setDisable(true);
        delete.setDisable(true);
    }
    /**
     * Switch to Sequence Diagram context
     * @param SDname name of Sequence Diagram
     */
    private void SwitchToSDContext(String SDname) {
        vBoxSD = new VBox();
//        ButtonBar buttonBar = new ButtonBar();
        returnButton = new Button("<- Return");
        returnButton.setOnAction(e -> {
            SwitchToCDContext();
        });

        addParticipant = new Button("Add Participant");
        addParticipant.setOnAction(e -> {
            var retArr = CreateParticipantWindow.display(null);
            SD.createParticipant(retArr[0], new UMLInterface(retArr[1])); //TODO domluvit se na implementaci
            setupFromSEQDiagram(SD);
        });

        addBox = new Button("Add Box");
        addBox.setOnAction(e -> {
            state = 5;
            AddMessageSetupButtons();
        });

        addSynchronous = new Button("Add Synchronous");
        addSynchronous.setOnAction(e -> {
            messageType = "Synchronous";
            messageText = CreateMessageWindow.display(messageType);
            state = 1;
            AddMessageSetupButtons();
        });

        addAsynchronous = new Button("Add Asynchronous");
        addAsynchronous.setOnAction(e -> {
            messageType = "Asynchronous";
            messageText = CreateMessageWindow.display(messageType);
            state = 1;
            AddMessageSetupButtons();
        });

        addCreate = new Button("Add Create");
        addCreate.setOnAction(e -> {
            messageType = "Create";
            messageText = CreateMessageWindow.display(messageType);
            state = 1;
            AddMessageSetupButtons();
        });

        addReturn = new Button("Add Return");
        addReturn.setOnAction(e -> {
            messageType = "Return";
            messageText = CreateMessageWindow.display(messageType);
            state = 1;
            AddMessageSetupButtons();
        });

        addDelete = new Button("Add Delete");
        addDelete.setOnAction(e -> {
            messageType = "Delete";
            messageText = CreateMessageWindow.display(messageType);
            state = 1;
            AddMessageSetupButtons();
        });

        cancelSD = new Button("Cancel");
        cancelSD.setDisable(true);
        cancelSD.setOnAction(e -> {
            state = 0;
            AddMessageENDSetupButtons();
        });

        delete = new Button("Delete");
        delete.setDisable(true);
        delete.setOnAction(e -> {
            if (selectForDelete != null){
                if (selectForDelete instanceof GParticipant){
                    var casted = (GParticipant)selectForDelete;
                    SD.deleteParticipant(casted.name);
                    delete.setDisable(true);
                    setupFromSEQDiagram(SD);
                }
                else if (selectForDelete instanceof GMessage){
                    var casted = (GMessage)selectForDelete;
                    SD.deleteMessage(casted.ID);
                    delete.setDisable(true);
                    setupFromSEQDiagram(SD);
                }
                else if (selectForDelete instanceof GActivationBox){
                    var casted = (GActivationBox)selectForDelete;
                    SD.deleteActivationBox(casted.ID);
                    delete.setDisable(true);
                    setupFromSEQDiagram(SD);
                }
            }
        });

        ComboBox<String> addParticipantCB = new ComboBox<>();
        SD = diagram.getSequenceDiagram(SDname);


        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.getChildren().addAll(returnButton,addParticipant,addBox ,addSynchronous, addAsynchronous, addCreate, addReturn,addDelete , cancelSD, delete);
        vBoxSD.getChildren().add(hBox);
        vBoxSD.getChildren().add(pane);
        Scene x = new Scene(vBoxSD, 1000, 600);

        setupFromSEQDiagram(SD);

        stage.setScene(x);
    }



    private void SwitchToCDContext() {
        pane.getChildren().clear();
        vBoxCD.getChildren().add(pane);
        comboBoxSDSelection.setValue("Select Sequence diagram");
        goToSD.setDisable(true);
        setupFromDiagram(diagram);
        stage.setScene(scene);
    }

    public void repairGclassBottomBorderRelations(Gclass selectedGclass, int diff) {
        for (var g : selectedGclass.relationList){
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
                if (GUIMain.state != 0)
                    return;
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
                if (GUIMain.state != 0)
                    return;
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

    private void makeGParticipantDraggableAndSelected(GParticipant gParticipant, double maxW, double maxH) {

        gParticipant.root.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                startX = e.getSceneX() - gParticipant.root.getTranslateX();
                selectForDelete = gParticipant;
                delete.setDisable(false);
                e.consume();
            }
        });

        gParticipant.root.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (GUIMain.state != 0)
                    return;
                double X = e.getSceneX() - startX;
                if (X > maxW)
                    X = maxW - 10;
                if (X < 0)
                    X = 0;
                gParticipant.root.setTranslateX(X);
                e.consume();
            }
        });
        gParticipant.root.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (GUIMain.state != 0)
                    return;
                Position pos = new Position((int)gParticipant.root.getTranslateX(),0);
                gParticipant.movedNotifyObservers(pos);
                e.consume();
            }
        });
    }


    private void addGClassSelectedHandler(Gclass gclass){
        gclass.root.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (GUIMain.state != 0)
                    return;
                selectedGclass1 = gclass;
                deleteteRelation.setDisable(true);
                deleteClass.setDisable(false);
                editClass.setDisable(false);
                e.consume();
            }
        });
    }
    
    public void run(UIAction action) {
    	history.addLast(action);
    	
    	action.run();
    }
    
    public void undo() {
    	if (history.isEmpty())
            return;

        var lastAction = history.removeLast();
        lastAction.undo();
    }
}
