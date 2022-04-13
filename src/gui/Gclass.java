package gui;

import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Gclass represents GUI UMLclass object
 *
 * @author  Adam Hos
 * @version 1.0
 */
public class Gclass extends Observable {
    Group root;
    Rectangle border;
    Label classLabel;
    List<Label> attrList;
    List<Label> methodList;
    VBox classVB;
    List<MyNode> anchors;
    Rectangle separator1;
    Rectangle separator2;
    Rectangle spacer;

    double eX;
    double eY;

    /**
     * Constructor for Gclass. Set border, VBox, Labels(name, attributes, methods) and separators(rectangles)
     * @param dummy if dummy, fill GClass with dummy data
     */
    public Gclass( boolean dummy) {
        final int initialClassWidth = 250;
        int initialClassHeight = 80;

        this.root = new Group();
        border = new Rectangle(initialClassWidth, initialClassHeight);
        border.setFill(Color.BLUEVIOLET);
        root.getChildren().addAll(border);

        anchors = new ArrayList<>();

        classVB = new VBox();
        classVB.setPrefSize(initialClassWidth - 20, initialClassHeight - 20);
        classVB.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        classVB.setTranslateX(10);
        classVB.setTranslateY(10);
        classVB.setAlignment(Pos.CENTER);

        attrList = new ArrayList<>();
        methodList = new ArrayList<>();
        separator1 = new Rectangle(initialClassWidth - 20, 5);
        separator1.setFill(Color.BLACK);
        separator2 = new Rectangle(initialClassWidth - 20, 5);
        separator2.setFill(Color.BLACK);
        spacer = new Rectangle(initialClassWidth - 20, 5);
        spacer.setFill(Color.WHITE);

        //create label for class name
        classLabel = new Label("'Class Name'");
        classVB.getChildren().add(classLabel);


        //add separating line
        classVB.getChildren().add(separator1);


        //initial attributes if dummy
        if (dummy){
            for (int i = 1; i <= 3 ; i++) {
                attrList.add(new Label("-atr" + i));
            }
            classVB.getChildren().addAll(attrList);

            if (attrList.isEmpty()){ //add spacer if needed
                classVB.getChildren().add(spacer);
            }

            //add separating line
            classVB.getChildren().add(separator2);

            for (int i = 1; i <= 2 ; i++) {
                methodList.add(new Label("-method" + i + "()"));
            }
            classVB.getChildren().addAll(methodList);
        }

        DoubleBinding result = classVB.heightProperty().add(20);
        border.heightProperty().bind(result);
        Platform.runLater(()->{
            System.out.println(border.getHeight());
        });


        root.getChildren().add(classVB);

        border.setOnMousePressed(Event::consume);
        border.setOnMouseDragged(Event::consume);
        border.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                seteX(e.getX() + root.getTranslateX());
                seteY(e.getY() + root.getTranslateY());
//                System.out.printf("Root %f   %f\n",root.getTranslateX(), root.getTranslateY());

                setChanged();
                notifyObservers(e);
            }
        });

//        border.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent e) {
//                Line testline = new Line();
//                testline.setStartX(eX);
//                testline.setStartY(eY);
//                testline.setEndX(e.getX() + root.getTranslateX());
//                testline.setEndY(e.getY() + root.getTranslateY());
//                System.out.printf("Relesae ex %f  eY %f   %f   %f\n",eX,eY, e.getX(), e.getY());
//                pane.getChildren().add(testline);
//                testline.toBack();
//                root.toFront();
//
//                testline.setStartX(300.0);
//
//            }
//        });
    }
    /**
     * Setter for eX (dragg event related)
     * @param newX
     */
    public void seteX(double newX){
        eX = newX;
    }

    /**
     * Setter for eY (dragg event related)
     * @param newY
     */
    public void seteY(double newY){
        eY = newY;
    }


    /**
     * Add Anchor to class so it will be dragged around with it
     * @param node anchor to be added
     */
    public void addAnchor(MyNodeAnchor node){
        double origX = node.getXprop().doubleValue();
        double origY = node.getYprop().doubleValue();

        DoubleBinding resultX = root.translateXProperty().add(origX);
        DoubleBinding resultY = root.translateYProperty().add(origY);

        node.getXprop().bind(resultX);
        node.getYprop().bind(resultY);
    }

    /**
     * Getter for root
     * @return root node
     */
    public Group getRoot(){
        return this.root;
    }
    /**
     * Getter for value of border height Property
     * @return border height
     */
    public double getHeight(){
        return border.heightProperty().getValue();
    }
    /**
     * Getter for value of border width Property
     * @return border width
     */
    public double getWidth(){
        return border.widthProperty().getValue();
    }
}
