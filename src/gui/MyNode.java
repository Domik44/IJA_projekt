package gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.util.Observable;

/**
 * MyNode nocde with a circle in Pane, used in GUI Paths (connection between classes)
 *
 * @author  Adam Hos
 * @version 1.0
 */
public class MyNode extends Observable {
    //Attributes
    Group g;
    Circle c;
    ObservableDoubleValue x;
    ObservableDoubleValue y;

    /**
     * Constructor for attribute object. Class constructor of super class Element and sets type of attribute.
     * @param X value of X Position
     * @param Y value of y Position
     * @param makeDraggable if the node should be created as draggable
     */
    public MyNode(double X, double Y, boolean makeDraggable) {
        g = new Group();
        g.setTranslateX(X);
        g.setTranslateY(Y);
        if (makeDraggable) {
            makeDraggable(g);
        }
        c = new Circle(0,0, 5);
        c.toFront();
        g.getChildren().add(c);
    }
    /**
     * Getter for translateXProperty of Group node
     * @return translateXProperty of Group node
     */
    public DoubleProperty getXprop() {
        return this.g.translateXProperty();
    }

    /**
     * Getter for translateYProperty of Group node
     * @return translateYProperty of Group node
     */
    public DoubleProperty getYprop() {
        return this.g.translateYProperty();
    }


    private double startX;
    private  double startY;
    /**
     * Makes the Node draggable with no movement restriction
     * @param node Node to be made draggable
     */
    private void makeDraggable(Group node) {

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
                node.setTranslateX(X);
                node.setTranslateY(Y);
            }
        });
    }
}
