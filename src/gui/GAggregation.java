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
public class GAggregation extends GRelationAbstractWithLabels {
    /**
     * Constructor for GAggregation. Allocate memory, set parent and child
     * @param parent parent GClass
     * @param child child GClass
     */
    public GAggregation(Gclass parent, Gclass child, String name) {
        super(parent, child, name);
    }

    //need to override, need more space coz square
    public void showLabelsCardinality(Gclass gclass, MyNode node, Label label){
        Group tmpRoot = gclass.getRoot();
        node.g.getChildren().add(label);
        Platform.runLater(() ->{
            double xdiff = node.g.getTranslateX() - tmpRoot.getTranslateX();
            double ydiff = node.g.getTranslateY() - tmpRoot.getTranslateY();
            int smalloffset = 5;
            int bigoffset = 40;
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

}
