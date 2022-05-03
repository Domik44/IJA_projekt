package gui;

import javafx.scene.Group;
import javafx.scene.control.Label;
import uml.classDiagram.UMLAttribute;

public class Gattribute extends Group {
    public UMLAttribute attribute;
    public Label label;

    public Gattribute(UMLAttribute attr){
        attribute = attr;
        label = new Label(attribute.toString());
        this.getChildren().add(label);
    }
}
