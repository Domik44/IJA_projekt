package gui;

import javafx.scene.Group;
import javafx.scene.control.Label;
import uml.classDiagram.UMLOperation;

public class Goperation extends Group {
    public UMLOperation op;
    public Label label;

    public Goperation(UMLOperation operation){
        op = operation;
        label = new Label(op.toString());
        this.getChildren().add(label);
    }
}
