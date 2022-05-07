package gui;

import controller.DeleteController;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uml.classDiagram.ClassDiagram;
import uml.pos.Position;
import uml.relations.RelAssociation;
import uml.relations.RelGeneralization;

import java.util.ArrayList;
import java.util.List;

/**
 * Gclass represents GUI UMLclass object
 *
 * @author  Adam Hos
 * @version 1.0
 */
public class GActivationBox {
    Group root;
    Rectangle rectangle;
    MyNode start;
    MyNode end;
    double eX;
    double eY;

    public GActivationBox(double y1, double y2) {
        final int initialClassWidth = 10;
        int initialClassHeight = 100;

        start = new MyNode(0, y1, false);
        start.c.setFill(Color.GREEN);
        start.c.setRadius(initialClassWidth);
        end   = new MyNode(0, y2, false);
        end.c.setFill(Color.GREEN);
        end.c.setRadius(initialClassWidth);


        this.root = new Group();
        rectangle = new Rectangle(initialClassWidth, initialClassHeight);
        rectangle.heightProperty().bind(end.g.translateYProperty().subtract(start.g.translateYProperty()));
        rectangle.setTranslateX(-initialClassWidth/2);
        rectangle.setFill(Color.LIGHTGREEN);
        rectangle.setOnMouseDragged(Event::consume);

        start.g.getChildren().add(rectangle);
        makeDraggableVerticalyTOP(start.g);
        makeDraggableVerticalyBOTTOM(end.g);
        root.getChildren().addAll(start.g, end.g);
        start.c.toFront();

    }

    private  double startY;
    private void makeDraggableVerticalyTOP(Group node) {

        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (GUIMain.state != 0)
                    return;
                startY = e.getSceneY() - node.getTranslateY();
                e.consume();
            }
        });

        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
//                if (GUIMain.state != 0)
//                    return;
                double Y = e.getSceneY() - startY;
                node.setTranslateY(Y);
                e.consume();
            }
        });

        node.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (start.getYprop().getValue() < 0){
                    node.setTranslateY( 20);
                }
                if (start.getYprop().getValue() > end.getYprop().getValue()){
                    node.setTranslateY(end.getYprop().getValue() - 50);
                }
                e.consume();
            }
        });
    }


    private void makeDraggableVerticalyBOTTOM(Group node) {

        node.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                VBox participantVB;
                startY = e.getSceneY() - node.getTranslateY();
                e.consume();
            }
        });

        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                VBox participantVB;
                double Y = e.getSceneY() - startY;
                node.setTranslateY(Y);
                e.consume();
            }
        });

        node.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (start.getYprop().getValue() > end.getYprop().getValue()){
                    node.setTranslateY(start.getYprop().getValue() + 50);
                }
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

}
