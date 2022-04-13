package gui;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.List;

/**
 * GAggregation represents GUI Aggregation
 *
 * @author  Adam Hos
 * @version 1.0
 */
public class GAggregation {
    Gclass parent;
    Gclass child;
    GConnection connection;
    Label pLabel;   //label near parent
    Label cLabel;   //label near child

    /**
     * Constructor for GAggregation. Allocate memory, set parent and child
     * @param parent parent GClass
     * @param child child GClass
     */
    public GAggregation(Gclass parent, Gclass child) {
        this.parent = parent;
        this.child = child;
        connection = new GConnection();
        pLabel = new Label();
        cLabel = new Label();
    }

    /**
     * Setup Aggregation from nodes (when reading from file)
     * @param start parent Anchor
     * @param end   child Anchor
     * @param list  list of connecting MyNodes
     */
    public void setFromList(List<MyNode> list, MyNodeAnchor start, MyNodeAnchor end){
        connection.setStart(start);
        connection.setEnd(end);
        if (list.size() > 0){
            connection.setBetween(list);
        }
        connection.setBind();
        connection.connect();

        parent.addAnchor(connection.start);
        child.addAnchor(connection.end);
    }

    /**
     * Setup labels near classes
     * @param parent string for parent label
     * @param child string for child label
     */
    public void setLabels(String parent, String child){
        pLabel.setFont(new Font("Arial", 30));
        pLabel.setText(parent);
        cLabel.setFont(new Font("Arial", 30));
        cLabel.setText(child);
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
     * @param parentNode anchor that will hold LCardinality label
     * @param childNode anchor that will hold RCardinality label
     */
    public void showLabels(MyNodeAnchor parentNode, MyNodeAnchor childNode){
        showLabelsCardinality(parent, parentNode, pLabel);
        showLabelsCardinality(child, childNode, cLabel);
    }
    /**
     * Add label to anchor with calculated position
     * @param gclass class near label
     * @param node anchor
     * @param label label o be added
     */
    public void showLabelsCardinality(Gclass gclass, MyNodeAnchor node, Label label){
        Group tmpRoot =gclass.getRoot();
        node.g.getChildren().add(label);
        Platform.runLater(() ->{
            double xdiff = node.g.getTranslateX() - tmpRoot.getTranslateX();
            double ydiff = node.g.getTranslateY() - tmpRoot.getTranslateY();
            if (xdiff == 0) {
                label.setLayoutX(-5 - label.getWidth());
                label.setLayoutY(-25);
            }
            else if (xdiff == parent.getWidth()){
                label.setLayoutX(+5 + label.getWidth()); //TODO check this
                label.setLayoutY(-25);
            }
            if (ydiff == 0) {
                label.setLayoutX(5);
                label.setLayoutY(-5 - label.getHeight());  //TODO check this
            }
            else if (ydiff == parent.getHeight()){
                label.setLayoutX(5);
                label.setLayoutY(+5 + label.getHeight()); //TODO check this
            }
        });
    }
}
