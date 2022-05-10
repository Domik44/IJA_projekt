package gui;

import controller.DeleteController;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uml.classDiagram.ClassDiagram;
import uml.classDiagram.UMLRelation;
import uml.pos.Position;

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
    List<Label> attrListLabel;
    List<Label> methodList;
    VBox classVB;
    Rectangle separator1;
    Rectangle separator2;
    boolean isinterface;

    List<UMLRelation> relationList;

    double eX;
    double eY;

    /**
     * Constructor for Gclass. Set border, VBox, Labels(name, attributes, methods) and separators(rectangles)
     */
    public Gclass(boolean isinterface) {
        final int initialClassWidth = 250;
        int initialClassHeight = 80;
        this.isinterface = isinterface;
        relationList = new ArrayList<>();

        this.root = new Group();
        border = new Rectangle(initialClassWidth, initialClassHeight);
        border.setFill(Color.BLUEVIOLET);
        root.getChildren().addAll(border);


        classVB = new VBox();
        classVB.setPrefSize(initialClassWidth - 20, initialClassHeight - 20);
        classVB.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        classVB.setTranslateX(10);
        classVB.setTranslateY(10);
        classVB.setAlignment(Pos.CENTER);

        attrListLabel = new ArrayList<>();
        methodList = new ArrayList<>();
        separator1 = new Rectangle(initialClassWidth - 20, 5);
        separator1.setFill(Color.BLACK);
        separator2 = new Rectangle(initialClassWidth - 20, 5);
        separator2.setFill(Color.BLACK);

        //create label for class name
        classLabel = new Label("'Class Name'");
        classVB.getChildren().add(classLabel);

        //add separating line
        classVB.getChildren().add(separator1);

//        DoubleBinding result = ;
        border.heightProperty().bind(classVB.heightProperty().add(20));
        Platform.runLater(()->{
//            System.out.println(border.getHeight()); //TODO use this to get border height
        });


        root.getChildren().add(classVB);

        root.setOnMouseClicked( e -> {
            if (GUIMain.state == 0)
                return;
            else if (GUIMain.state == 1){
                GUIMain.state++;
                GUIMain.selectedGclass1 = this;
                GUIMain.positionList.clear();
                GUIMain.positionList.add(new Position((int)e.getX(),(int) e.getY()));
                e.consume();
            }
            else if(GUIMain.state == 2){
                GUIMain.state = 0;
                GUIMain.selectedGclass2 = this;
                GUIMain.positionList.add(new Position((int)e.getX(),(int) e.getY()));
                NotifyThatRelationPathEnded();
                GUIMain.AddRelationENDSetupButtons();
                e.consume();
            }

        });

    }
    /**
     * Setter for eX (dragg event related)
     * @param newX new X value
     */
    public void seteX(double newX){
        eX = newX;
    }

    /**
     * Setter for eY (dragg event related)
     * @param newY new Y value
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

    /**
     * Return name of Gclass
     * @return name
     */
    public String getName() { return classLabel.getText();};

    /**
     * Ask if point with changed height is lower then border
     * @param point point to be checked
     * @param diff changed border height
     * @return boolean
     */
    public boolean isPointOnBottom(Position point, int diff){
        double borderHeight = getHeight();
        if (point.getY() + diff < borderHeight)
            return false;
        return true;
    }

//    public void delete(GUIMain gui, ClassDiagram diagram) {
//        if(isinterface)
//            DeleteController.DeleteInterface(this.getName(), gui, diagram);
//        else
//            DeleteController.DeleteClass(this.getName(), gui, diagram);
//    }

    public void NotifyThatRelationPathEnded(){
        setChanged();
        notifyObservers();
    }

    public void setColorToSelected(){
        border.setFill(Color.LIGHTBLUE);
    }

    public void setColorToUNSelected(){
        border.setFill(Color.BLUEVIOLET);
    }
}