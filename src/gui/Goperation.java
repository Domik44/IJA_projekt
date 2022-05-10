package gui;

import javafx.scene.Group;
import javafx.scene.control.Label;
import uml.classDiagram.UMLOperation;

/**
 * Goperation represents operation in EditClassWindow
 *
 * @author  Adam Hos
 * @version 1.0
 */
public class Goperation extends Group {
    public UMLOperation op;
    public Label label;

    /**
     * Constructor for Goperation
     * @param operation UMLOperation that Goperation represents
     */
    public Goperation(UMLOperation operation){
        op = operation;
        label = new Label(op.toString());
        this.getChildren().add(label);
    }
}
