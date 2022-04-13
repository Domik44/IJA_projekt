package gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.util.Observable;

/**
 * MyNode represents GUI UMLclass
 *
 * @author  Adam Hos
 * @version 1.0
 */
public class MyNode extends Observable {
    Group g;
    Circle c;
    ObservableDoubleValue x;
    ObservableDoubleValue y;


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

    public DoubleProperty getXprop() {
        return this.g.translateXProperty();
    }

    public DoubleProperty getYprop() {
        return this.g.translateYProperty();
    }


    private double startX;
    private  double startY;
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
