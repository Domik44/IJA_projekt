package gui;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Rotate;

/**
 * MyNodeAnchor nocde with a small circle in Pane
 * used for ending GUI Paths on GClasses.
 * Also holds end points of Paths (like generalization arrow)
 *
 * @author  Adam Hos
 * @version 1.0
 */
public class MyNodeAnchor extends MyNode{
    //attributes
    Polygon polygon;
    Rotate rotation;

    /**
     * Constructor for MyNodeAnchor. Call super MyNode constructor, make circle smaller and allocate rotation
     * @param X value of X Position
     * @param Y value of y Position
     * @param makeDraggable if the node should be created as draggable
     */
    public MyNodeAnchor(double X, double Y, boolean makeDraggable) {
        super(X, Y, makeDraggable);
        c.setRadius(3);
        rotation = new Rotate();
    }

    /**
     * Create new triangle and give it to this.polygon
     */
    public void polygonTriangle(){
        double size = 15.0;
        polygon = new Polygon(
                0.0,0.0,
                -size, size*1.5,
                size, size*1.5);

        polygon.setStroke(Color.BLACK);
        polygon.setStrokeWidth(3);
        polygon.setFill(Color.WHITE);
        polygon.setStrokeLineCap(StrokeLineCap.ROUND);
        g.getChildren().add(polygon);
    }
    /**
     * Create new small triangle and give it to this.polygon
     */
    public void polygonTriangleSmall(){
        double size = 10.0;
        polygon = new Polygon(
                0.0,0.0,
                -size, size*1.5,
                size, size*1.5);

        polygon.setStroke(Color.BLACK);
        polygon.setStrokeWidth(3);
        polygon.setFill(Color.WHITE);
        polygon.setStrokeLineCap(StrokeLineCap.ROUND);
        g.getChildren().add(polygon);
    }

    /**
     * Create new small triangle and give it to this.polygon
     */
    public void polygonArrow(){
        double size = 10.0;
        polygon = new Polygon(
                0.0,0.0,
                -size, size*1.5,
                0.0,0.0,
                size, size*1.5);

        polygon.setStroke(Color.BLACK);
        polygon.setStrokeWidth(3);
        polygon.setFill(Color.WHITE);
        polygon.setStrokeLineCap(StrokeLineCap.ROUND);
        g.getChildren().add(polygon);
    }
    /**
     * Create new square and give it to this.polygon
     */
    public void polygonSquare(){
        double size = 15.0;
        polygon = new Polygon(
                0.0,0.0,
                -size, size,
                0.0, 2*size,
                size, size);

        polygon.setStroke(Color.BLACK);
        polygon.setStrokeWidth(3);
        polygon.setFill(Color.WHITE);
        polygon.setStrokeLineCap(StrokeLineCap.ROUND);
        g.getChildren().add(polygon);
    }

    /**
     * Setup rotation of polygon, based on this MyAnchorNodes location on GClass
     * @param gclass the class that the Anchor sits on
     */
    public void polygonSetRotatoin(Gclass gclass) {
        rotation.setPivotX(0);
        rotation.setPivotY(0);
        Platform.runLater(() ->{
            double xdiff = g.getTranslateX() - gclass.getRoot().getTranslateX();
            double ydiff = g.getTranslateY() - gclass.getRoot().getTranslateY();
            if (xdiff == 0) { // left side
                rotation.setAngle(90);
            }
            else if (xdiff == gclass.getWidth()){
                rotation.setAngle(-90);
            }
            if (ydiff == 0) {
                rotation.setAngle(180);
            }
            else if (ydiff == gclass.getHeight()){
                rotation.setAngle(0);
            }
            polygon.getTransforms().add(rotation);
        });
    }

    public void polygonSetRotatoinLR(boolean pointingLeft) {
        polygon.getTransforms().clear();
        rotation.setPivotX(0);
        rotation.setPivotY(0);
        if (pointingLeft)
            rotation.setAngle(90);
        else
            rotation.setAngle(-90);
        polygon.getTransforms().add(rotation);
    }
}
