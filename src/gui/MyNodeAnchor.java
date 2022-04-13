package gui;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Rotate;

public class MyNodeAnchor extends MyNode{
    Polygon polygon;
    Rotate rotation;


    public MyNodeAnchor(double X, double Y, boolean makeDraggable) {
        super(X, Y, makeDraggable);
        c.setRadius(3);
        rotation = new Rotate();
    }

    public void polygonTriangle(){
        double size = 20.0;
        polygon = new Polygon(
                0.0,0.0,
                -size, size,
                size, size);

        polygon.setStroke(Color.BLACK);
        polygon.setStrokeWidth(3);
        polygon.setFill(Color.WHITE);
        polygon.setStrokeLineCap(StrokeLineCap.ROUND);
        g.getChildren().add(polygon);
    }

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
}
