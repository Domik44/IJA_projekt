package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import uml.*;
import uml.pos.Position;
import uml.relations.RelAssociation;
import workers.Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GUIMain extends Application implements Observer {

    VBox vBox;
    Pane pane;
    List<Gclass> gclassList;
    Scene scene;
    Stage stage;

    public GUIMain() {
        vBox = new VBox();
        pane = new Pane();
        gclassList = new ArrayList<>();
        scene = new Scene(vBox, 1000, 600);
    }


    public void setupClasses(List<UMLClass> classes) {
        for (UMLClass c : classes) {
            //create Gclass object
            Gclass tmp = new Gclass(pane, false);
            makeDraggable(tmp.getRoot(), scene.getWidth(), scene.getHeight());
            gclassList.add(tmp);
            tmp.getRoot().toBack();
            //setup class data
            tmp.classLabel.setText(c.getName());
            Position pos = c.getPosition();
            tmp.getRoot().setTranslateX(pos.getX());
            tmp.getRoot().setTranslateY(pos.getY());
            pane.getChildren().add(tmp.getRoot());

            for (UMLAttribute attr : c.getAttributes()) {
                System.out.println(attr.toString());
                tmp.attrList.add(new Label(attr.toString()));
            }
            tmp.classVB.getChildren().addAll(tmp.attrList);
            tmp.classVB.getChildren().add(tmp.spacer);
            tmp.classVB.getChildren().add(tmp.separator2);

            for (UMLAttribute opr : c.getOperations()) {
                System.out.println(opr.toString() + "xx");
                tmp.methodList.add(new Label(opr.toString()));
            }
            tmp.classVB.getChildren().addAll(tmp.methodList);
        }
    }

    public void setupInterfaces(List<UMLInterface> interfaces) {
        for (UMLInterface i : interfaces) {
            //create Gclass object
            Gclass tmp = new Gclass(pane, false);
            makeDraggable(tmp.getRoot(), scene.getWidth(), scene.getHeight());
            gclassList.add(tmp);        //TODO create separate list
            tmp.getRoot().toBack();
            tmp.classVB.getChildren().add(0,new Label("<<interface>>"));
            //setup class data
            tmp.classLabel.setText(i.getName());
            Position pos = i.getPosition();
            tmp.getRoot().setTranslateX(pos.getX());
            tmp.getRoot().setTranslateY(pos.getY());
            pane.getChildren().add(tmp.getRoot());


            for (UMLAttribute opr : i.getOperations()) {
                System.out.println(opr.toString() + "xx");
                tmp.methodList.add(new Label(opr.toString()));
            }
            tmp.classVB.getChildren().addAll(tmp.methodList);
        }
    }

    public void setupAssociation(List<RelAssociation> associations) {
        for (RelAssociation a : associations) {
            //create Gclass object
            List<Position> list = a.getPoints();
            List<MyNode> listNode = new ArrayList<>();
            for ( Position point : list){
                listNode.add(new MyNode(point.getX(), point.getY(), false));
            }

            GAssociation ass = new GAssociation(gclassList.get(0), gclassList.get(2));
            ass.setFromList(listNode);
            ass.show(pane);
        }
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        System.out.println("Update called with Arguments: " + arg);
    }

    double x;
    public void Start(){

        launch();
    }
    @Override
    public void start(Stage orig) throws IOException {
        ClassDiagram diagram = new ClassDiagram("ClassDiagram");
        Reader.startReading(diagram);
        for(UMLClass cl : diagram.getClasses()) {
            Position pos = cl.getPosition();
            System.out.println("Class: "+cl + " " + cl.getPosition().getX() + " " + cl.getPosition().getY());
            for(UMLAttribute at : cl.getAttributes()) {
                String attr = at.toString(); // tohle je jen pro demostraci funkcnosti toString
                // Takhle si to budes moct ulozit do stringu a pak to jen rovnou hodit na zobrazeni
                System.out.println(attr);
                //System.out.println(at); -> stacilo by takhle
            }

            for(UMLOperation op : cl.getOperations()) {
                System.out.println(op);
            }
            System.out.println();
        }


        //*****GUI START*****//
        //setup initial look
//        Image icon = new Image("icon.png");
//        stage.getIcons().add(icon);
//        stage.setTitle("ER DIAGRAM EDITOR FOR IJA");
        stage = new Stage();
        setupClasses(diagram.getClasses());
        setupInterfaces(diagram.getInterfaces());
        setupAssociation(diagram.getAssociations());
        Button addClass = new Button("Add Class");
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().add(addClass);

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
                Gclass tmpClass = new Gclass(pane, true);
                makeDraggable(tmpClass.getRoot(), scene.getWidth(), scene.getHeight());
                gclassList.add(tmpClass);
                pane.getChildren().add(0,tmpClass.getRoot());

            }
        };
        addClass.addEventHandler(MouseEvent.MOUSE_CLICKED, eve);

//        Gclass tmpc1 = new Gclass(pane, true);
//        makeDraggable(tmpc1.getRoot(), scene.getWidth(), scene.getHeight());
//        gclassList.add(tmpc1);
//        tmpc1.getRoot().toBack();
//        pane.getChildren().add(tmpc1.getRoot());
//
//
//
//        System.out.println(tmpc1.getHeight());
//        MyNode n1 = new MyNode(200,50, false);
//        MyNode n2 = new MyNode(400,100, true);
//        MyNode n3 = new MyNode(100,0, false);
//
//        List<MyNode> init = new ArrayList<>();
//        init.add(n1);
//        init.add(n2);
//        init.add(n3);

//        GInheritence inh = new Ggeneralization(tmpc1, tmpc2);
//        inh.setFromList(init);
//        inh.show(pane);

//        GConnection test = new GConnection();
//        test.setStart(n1);
//        test.setEnd(n2);
//        test.setBind();
//        test.connect();
//
//        pane.getChildren().addAll(test.start.g, test.end.g);
//        for (MyNode node: test.between) {
//            pane.getChildren().add(node.g);
//            node.g.toFront();
//        }
//
//        tmpc1.addAnchor(test.start);
//        tmpc2.addAnchor(test.end);
//
//        pane.getChildren().add(test.path);

//        Polygon b = new Polygon(
//                0.0,12.0,
//                12.0,0.0,
//                24.0, 12.0);

//        double firstval = 20.0;
//        double X = 300;
//        double Y = 300;
//
//        Polygon b = new Polygon(
//                0.0,0.0,
//                -firstval, firstval,
//                firstval, firstval);
//
//        b.setStroke(Color.BLACK);
//        b.setStrokeWidth(3);
//        b.setFill(Color.WHITE);
//        b.setStrokeLineCap(StrokeLineCap.ROUND);
//
////        inh.connection.start.g.getChildren().add(b);
//
//        Rotate r = new Rotate();
//        r.setPivotX(0);
//        r.setPivotY(0);
//        r.setAngle(-90);
//
//        b.getTransforms().add(r);
//
//        Polygon p = new Polygon(
//                0.0,0.0,
//                -firstval, firstval,
//                0.0,0.0,
//                firstval, firstval);
//
//        p.setStroke(Color.BLACK);
//        p.setStrokeWidth(3);
//        p.setFill(Color.WHITE);
//        p.setStrokeLineCap(StrokeLineCap.ROUND);
//        p.setTranslateX(X);
//        p.setTranslateY(Y);
//        pane.getChildren().add(b);
//        pane.getChildren().add(p);



        stage.setScene(scene);
        stage.show();
    }



    private double startX;
    private  double startY;
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