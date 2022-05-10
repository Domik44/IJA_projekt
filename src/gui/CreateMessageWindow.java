package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uml.classDiagram.UMLInterface;

/**
 * EditClassWindowClassName
 */
public class CreateMessageWindow {
    /**
     * Init window and setup values
     */
    static String retValue = null;
    static boolean checkboxOut;

    public static String display(String type, GParticipant selectedParticipant){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Create " + type + " message");
        window.setMinWidth(300);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5,5,5,5));

        grid.getColumnConstraints().addAll(
                new ColumnConstraints(100),
                new ColumnConstraints(200)
        );
        grid.getRowConstraints().addAll(
                new RowConstraints(50),
                new RowConstraints(40),
                new RowConstraints(20),
                new RowConstraints(60)
        );

        Label classNameLabel = new Label("Create new: ");
        grid.add(classNameLabel, 0, 0);

        TextField field = new TextField();
        grid.add(field, 1, 0);

        Label orSelect = new Label("Or Select an existing one ");
        grid.add(orSelect, 0, 1, 2, 1);
        Button save = new Button("Save");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText("Select Sequence diagram");
        comboBox.setMinWidth(200);
        comboBox.setOnAction(e ->{
            save.setDisable(false);
        });
        for (var m : selectedParticipant.UMLInstanceOf.getAllMethods()){
            comboBox.getItems().add(m.toString());
        }

        save.setOnAction(e -> {
            retValue = field.getText();
            if (comboBox.getValue() != null)
                retValue = comboBox.getValue();
            window.close();
        });
        Button discard = new Button("Discard");
        discard.setOnAction(e -> {
            retValue = null;
            window.close();
        });

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(save,discard);
        grid.add(comboBox,0,2, 2, 1);
        grid.add(buttonBar,0,3, 2, 1);



//        grid.getRowConstraints().addAll(new RowConstraints(50));


        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();

        return retValue;
    }
}
