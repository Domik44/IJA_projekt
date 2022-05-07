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
public class AddAssociationWindow {
    /**
     * Init window and setup values
     */
    static String[] retArray;

    public static String[] display(){
        Stage window = new Stage();
        retArray = new String[3];

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add Association");
        window.setMinWidth(250);

        //Labels
        Label startL = new Label();
        startL.setText("Start Cardinality: ");
        Label nameL = new Label();
        nameL.setText("Name: ");
        Label endL = new Label();
        endL.setText("End Cardinality: ");

        TextField startTF = new TextField();
        TextField nameTF = new TextField();
        TextField endTF = new TextField();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5,5,5,5));

        grid.add(startL, 0, 0);
        grid.add(startTF, 1, 0, 2, 1);
        grid.add(nameL, 0, 1);
        grid.add(nameTF, 1, 1, 2, 1);
        grid.add(endL, 0, 2);
        grid.add(endTF, 1, 2, 2, 1);




        Button save = new Button("Save");
        save.setOnAction(e -> {
            retArray[0] = startTF.getText();
            retArray[1] = nameTF.getText();
            retArray[2] = endTF.getText();
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
                new ColumnConstraints(100),
                new ColumnConstraints(200)
        );

        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();

        return retArray;
    }
}
