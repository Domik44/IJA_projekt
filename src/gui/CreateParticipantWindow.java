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
public class CreateParticipantWindow {
    /**
     * Init window and setup values
     */
    static String[] retArray = null;

    public static String[] display(UMLAttribute attr){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Create Participant");
        window.setMinWidth(250);

        //Labels
        Label nameL = new Label();
        nameL.setText("Name: ");
        Label typeL = new Label();
        typeL.setText("Class: ");

        TextField nameTF = new TextField();
        TextField typeTF = new TextField();

        if (attr != null) {
            nameTF.setText(attr.getName());
            typeTF.setText(attr.getType().toString());
        }

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5,5,5,5));

        grid.add(nameL, 0, 1);
        grid.add(nameTF, 1, 1, 2, 1);
        grid.add(typeL, 0, 2);
        grid.add(typeTF, 1, 2, 2, 1);




        Button save = new Button("Save");
        save.setOnAction(e -> {
            retArray = new String[2];
            retArray[0] = nameTF.getText();
            retArray[1] = typeTF.getText();
            window.close();
        });
        Button discard = new Button("Discard");
        discard.setOnAction(e -> {
            retArray = null;
            window.close();
        });


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
