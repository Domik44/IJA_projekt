package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uml.classDiagram.UMLAttribute;

/**
 * Edit Class Window for attribute
 */
public class ECWAttribute {
    /**
     * Init window and setup values
     */
    static String[] retArray;

    public static String[] display(UMLAttribute attr){
        Stage window = new Stage();
        retArray = new String[3];

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Attribute");
        window.setMinWidth(250);

        //Labels
        Label visibilityL = new Label();
        visibilityL.setText("Visibility: ");
        Label nameL = new Label();
        nameL.setText("Name: ");
        Label typeL = new Label();
        typeL.setText("Type: ");

        TextField visibilityTF = new TextField();
        TextField nameTF = new TextField();
        TextField typeTF = new TextField();

        if (attr != null) {
            visibilityTF.setText(attr.getVisibility().toString());
            nameTF.setText(attr.getName());
            typeTF.setText(attr.getType().toString());
        }

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5,5,5,5));

        grid.add(visibilityL, 0, 0);
        grid.add(visibilityTF, 1, 0, 2, 1);
        grid.add(nameL, 0, 1);
        grid.add(nameTF, 1, 1, 2, 1);
        grid.add(typeL, 0, 2);
        grid.add(typeTF, 1, 2, 2, 1);




        Button save = new Button("Save");
        save.setOnAction(e -> {
            retArray[0] = visibilityTF.getText();
            retArray[1] = nameTF.getText();
            retArray[2] = typeTF.getText();
            window.close();
        });
        Button discard = new Button("Discard");
        discard.setOnAction(e -> {
            retArray[0] = null;
            retArray[1] = null;
            retArray[2] = null;
            window.close();
        });

//        grid.add(save, 1,3);
//        grid.add(discard, 2, 3);

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(save,discard);
        grid.add(buttonBar,0,3,3,1);


        grid.getColumnConstraints().addAll(
                new ColumnConstraints(50),
                new ColumnConstraints(200)
        );

        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();

        return retArray;
    }
}
