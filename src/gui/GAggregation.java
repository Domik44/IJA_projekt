package gui;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * GAggregation represents GUI Aggregation
 *
 * @author  Adam Hos
 * @version 1.0
 */
public class GAggregation extends GRelationAbstract {
    Label pLabel;   //label near parent
    Label cLabel;   //label near child

    /**
     * Constructor for GAggregation. Allocate memory, set parent and child
     * @param parent parent GClass
     * @param child child GClass
     */
    public GAggregation(Gclass parent, Gclass child) {
        super(parent, child);
        pLabel = new Label();
        cLabel = new Label();
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
