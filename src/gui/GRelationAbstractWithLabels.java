package gui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import uml.pos.Position;

import java.util.List;

/**
 * GRelationAbstract abstract class for all Class Diagram Relations
 *
 * @author  Adam Hos
 * @version 1.0
 */
 public class GRelationAbstractWithLabels extends GRelationAbstract{
    Label pLabel;   //label near parent
    Label cLabel;   //label near child
    Label rLabel;   //label for association name

    public GRelationAbstractWithLabels(Gclass parent, Gclass child, String name) {
        super(parent, child, name);
        pLabel = new Label();
        cLabel = new Label();
        rLabel = new Label();
        pLabel.setFont(new Font("Arial", 15));
        cLabel.setFont(new Font("Arial", 15));
        rLabel.setFont(new Font("Arial", 20));
    }

    /**
     * Setup labels
     * @param parent string for parent label
     * @param child string for child label
     * @param relation name of relation
     */
    public void setLabels(String parent, String child, String relation){
        pLabel.setText(parent);
        cLabel.setText(child);
        rLabel.setText(relation);

        //make name label draggable
        makeDraggable(rLabel);
    }

    /**
     * Add path, MyNodes and MyNodeAnchors to pane
     * @param pane pane that will hold obects
     */
    public void show(Pane pane){
        pane.getChildren().add(connection.path);
        pane.getChildren().add(connection.start.g);
        pane.getChildren().add(connection.end.g);
        for (MyNode node: connection.between) {
            pane.getChildren().add(node.g);
            node.g.toFront();
        }
    }

    /**
     * Add cardinality labels
     * @param childNode anchor that will hold RCardinality label
     * @param parentNode anchor that will hold LCardinality label
     */
    public void showLabels(MyNode parentNode, MyNode childNode){
        showLabelsCardinality(parent, parentNode, pLabel);
        showLabelsCardinality(child, childNode, cLabel);
    }

    /**
     * Add label to anchor with calculated position
     * @param gclass class near label
     * @param node anchor
     * @param label label o be added
     */
    public void showLabelsCardinality(Gclass gclass, MyNode node, Label label){
        Group tmpRoot = gclass.getRoot();
        node.g.getChildren().add(label);
        Platform.runLater(() ->{
            System.out.println(label.getWidth());
            double xdiff = node.g.getTranslateX() - tmpRoot.getTranslateX();
            double ydiff = node.g.getTranslateY() - tmpRoot.getTranslateY();
            int smalloffset = 5;
            int bigoffset = 25;
            label.setLayoutX(smalloffset);
            if (xdiff == 0) {
                label.setLayoutX(-smalloffset - label.getWidth());
                label.setLayoutY(-bigoffset);
            }
            else if (xdiff == parent.getWidth()){
                label.setLayoutX(+smalloffset); //TODO check this
                label.setLayoutY(-bigoffset);
            }
            if (ydiff == 0) {
                label.setLayoutX(smalloffset);
                label.setLayoutY(-bigoffset - label.getHeight());  //TODO check this
            }
            else if (ydiff == parent.getWidth()){
                label.setLayoutX(smalloffset);
                label.setLayoutY(+bigoffset); //TODO check this
            }
        });
    }

    /**
     * Set label with Association name, text and position
     * @param name to be named
     * @param labelPosition position to be moved
     */
    public void setLabelName(String name, Position labelPosition) {
        rLabel.setText(name);
        rLabel.setTranslateX(labelPosition.getX());
        rLabel.setTranslateY(labelPosition.getY());
    }

    private double startX;
    private  double startY;
    public void makeDraggable(Label label) {

        label.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                startX = e.getSceneX() - label.getTranslateX();
                startY = e.getSceneY() - label.getTranslateY();
            }
        });

        label.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (GUIMain.state != 0)
                    return;
                double X = e.getSceneX() - startX;
                double Y = e.getSceneY() - startY;
                label.setTranslateX(X);
                label.setTranslateY(Y);
            }
        });
    }
}
