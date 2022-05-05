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
    static Gclass selectedGclass1;
    static Gclass selectedGclass2;
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

    //Fields for SequenceDiagram
    VBox vBoxSD;
    List<GParticipant> gParticipantList = new ArrayList<>();
    private SequenceDiagram SD;
    private GParticipant selectedParticipant1;
    private GParticipant selectedParticipant2;
    private Button addMessage;
    private Button cancelSD;
    private Button returnButton;
    private String messageText;
    private String messageType;
    private Button deleteteRelation;

    /**
     * Constructor for GUIMain. Allocate memory, set scene
     */
    public GUIMain() {
        vBoxCD = new VBox();
        pane = new Pane();
        gClassList = new ArrayList<>();
        scene = new Scene(vBoxCD, 1400, 600);
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
        RelGeneralization g = diagram.createGeneralization(selectedGclass1.getName(), selectedGclass2.getName(), relationType);
        fixBorderPoints();
        for (var p : positionList){
            g.addPosition(p);
        }
        g.rename(UUID.randomUUID().toString());
        setupFromDiagram(diagram);
    }

    private void createAggregation() {
        RelAggregation a = diagram.createAggregation(selectedGclass1.getName(), selectedGclass2.getName(), relationType);
        fixBorderPoints();
        for (var p : positionList){
            a.addPosition(p);
        }
        a.rename(UUID.randomUUID().toString());
        setupFromDiagram(diagram);
    }

    private void createAssociation() {
        RelAssociation a = diagram.createAssociation(selectedGclass1.getName(), selectedGclass2.getName(), relationType);
        fixBorderPoints();
        for (var p : positionList){
            a.addPosition(p);
        }
        a.rename(UUID.randomUUID().toString());
        a.setCardinality(LCardinality, RCardinality);
        a.setLabel(relationName);
        //place label into midle of start and end nodes
        a.setLabelPosition(50,50);
        setupFromDiagram(diagram);
    }

    /**
     * Calculate to witch part of Gclass border should the points snap to
     */
    private void fixBorderPoints() {
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
            ass.name = a.getName();
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
            Gclass second = getClassByName(a.getRightClass().getName());
            GAggregation aggr = new GAggregation(first, second, a.getName());
            aggr.setFromList(listNode, start, end);
            aggr.setLabels(a.getLeftCardinality(),a.getRightCardinality());
            aggr.showLabels(start, end); //TODO this does nothing?
            start.polygonSquare();
            start.polygonSetRotatoin(first);

            addRelationSelectedHandler(aggr);

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
                if (state == 0) {
                    node.g.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        selectedRelation = o;
                        deleteteRelation.setDisable(false);
                        editClass.setDisable(true);
                        deleteClass.setDisable(true);
                        e.consume();
                    });
                }
            }
            casted.connection.start.g.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                selectedRelation = o;
                deleteteRelation.setDisable(false);
                editClass.setDisable(true);
                deleteClass.setDisable(true);
                e.consume();
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
            makeGParticipantDraggable(tmp, scene.getWidth(), scene.getHeight());
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
                GActivationBox Gbox = new GActivationBox(box.getStartPosition().getY(),box.getEndPosition().getY());
                tmp.dashedStart.getChildren().add(Gbox.root);
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
            public void handle(MouseEvent mouseEvent) {
                editClass.setDisable(true);
                deleteClass.setDisable(true);
                deleteteRelation.setDisable(true);
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
                diagram.createClass(name);
                setupFromDiagram(diagram);
            }
        });
        buttonBar.getButtons().add(addClass);
        addInterface = new Button("Add Interface");
        addInterface.setOnAction(e ->{
            String name = CreateClassWindow.display();
            if (name != null && !name.equals("")) {
                diagram.createInterface(name);
                setupFromDiagram(diagram);
            }
        });
        buttonBar.getButtons().add(addInterface);

        editClass = new Button("Edit Class");
        editClass.setDisable(true);
        editClass.setOnAction(e -> {
            int GclassHeightdiff = 0;
            if (selectedGclass1.isinterface){
                GclassHeightdiff = ECW.display(diagram.getInterface(selectedGclass1.classLabel.getText()));
            }
            else {
                GclassHeightdiff = ECW.display(diagram.getClass(selectedGclass1.classLabel.getText()));
            }
            if (GclassHeightdiff != 0)
                repairGclassBottomBorderRelations(selectedGclass1, GclassHeightdiff);
            setupFromDiagram(diagram);
            editClass.setDisable(true);
            deleteClass.setDisable(true);
        });
        buttonBar.getButtons().add(editClass);

        deleteClass = new Button("Delete Class");
        deleteClass.setDisable(true);
        GUIMain gui = this;

        deleteClass.addEventHandler(MouseEvent.MOUSE_CLICKED,  new EventHandler<>(){
            @Override public void handle(MouseEvent e) {
                selectedGclass1.delete(gui,diagram);
                setupFromDiagram(diagram);
            }
        });
        buttonBar.getButtons().add(deleteClass);

        addAssociation = new Button("Add AssociationClass");
        buttonBar.getButtons().add(addAssociation);
        addAssociation.setOnAction(e -> {
            var retArr = AddAssociationWindow.display();
            if (retArr != null) {
                LCardinality = retArr[0];
                relationName = retArr[1];
                RCardinality = retArr[2];
                AddRelationSetupButtons();
                state = 1;
                relationType = "Association";
            }
        });

        addGeneralization = new Button("Add Generalization");
        buttonBar.getButtons().add(addGeneralization);
        addGeneralization.setOnAction(e -> {
            AddRelationSetupButtons();
            state = 1;
            relationType = "Generalization";
        });



        addAggregation = new Button("Add Aggregation");
        buttonBar.getButtons().add(addAggregation);
        addAggregation.setOnAction(e -> {
            AddRelationSetupButtons();
            state = 1;
            relationType = "Aggregation";
        });

        deleteteRelation = new Button("Delete Relation");
        deleteteRelation.setDisable(true);
        buttonBar.getButtons().add(deleteteRelation);
        deleteteRelation.setOnAction(e -> {
            if(selectedRelation instanceof GGeneralization){
                diagram.deleteGeneralization(((GGeneralization)selectedRelation).name);
                deleteteRelation.setDisable(true);
                setupFromDiagram(diagram);
            }
            else if(selectedRelation instanceof GAssociation){
                diagram.deleteAssociation(((GAssociation)selectedRelation).name);
                deleteteRelation.setDisable(true);
                setupFromDiagram(diagram);
            }
            else if(selectedRelation instanceof GAggregation){
                diagram.deleteAggregation(((GAggregation)selectedRelation).name);
                deleteteRelation.setDisable(true);
                setupFromDiagram(diagram);
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

    private Position lineStart;
    private void setDashedEvents(GParticipant gParticipant){
        gParticipant.root.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if(state == 0)
                return;
            if(state == 1){
                selectedParticipant1 = gParticipant;
                state ++;
                lineStart = new Position(0,(int) e.getY());
            }
            else if(state == 2){
                if (selectedParticipant1 == gParticipant) //selected the same GParticipant twice, invalid -> return
                    return;
                selectedParticipant2 = gParticipant;
                state = 0;
                AddMessageENDSetupButtons();
                messageText = "TestName()";
                messageType = "Synchronous";
                createGMessage(lineStart, messageText, messageType);

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
        addMessage.setDisable(true);
        cancelSD.setDisable(false);
    }

    public void AddMessageENDSetupButtons(){
        returnButton.setDisable(false);
        addMessage.setDisable(false);
        cancelSD.setDisable(true);
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
        addMessage = new Button("Add Message");
        cancelSD = new Button("Cancel");
        cancelSD.setDisable(true);

        addMessage.setOnAction(e -> {
            state = 1;
            AddMessageSetupButtons();
        });

        cancelSD.setOnAction(e -> {
            state = 0;
            AddMessageENDSetupButtons();
        });

        ComboBox<String> addParticipantCB = new ComboBox<>();
        SD = diagram.getSequenceDiagram(SDname);


        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.getChildren().addAll(returnButton, addMessage, cancelSD);
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
                if (GUIMain.state != 0)
                    return;
                double X = e.getSceneX() - startX;
                if (X > maxW)
                    X = maxW - 10;
                if (X < 0)
                    X = 0;
                gParticipant.root.setTranslateX(X);
            }
        });
        gParticipant.root.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (GUIMain.state != 0)
                    return;
                Position pos = new Position((int)gParticipant.root.getTranslateX(),0);
                gParticipant.movedNotifyObservers(pos);
            }
        });
    }


    private void setGClassSelectEventHandler(Gclass gclass){
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
}
