package gui;

import javafx.scene.Group;
import javafx.scene.control.Label;
import uml.classDiagram.UMLAttribute;

/**
 * Gattribute represents attribute in EditCalssWindows
 *
 * @author  Adam Hos
 * @version 1.0
 */
public class Gattribute extends Group {
    public UMLAttribute attribute;
    public Label label;

    /**
     * Constructor for Gattribute
     * @param attr UML attribute related to this
     */
    public Gattribute(UMLAttribute attr){
        attribute = attr;
        label = new Label(attribute.toString());
        this.getChildren().add(label);
    }
}
