package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * SelectFileWindow
 */
public class SelectFileWindow {

    static String retValue = null;
    /**
     * Init window and setup required elements
     */
    public static String display(){
        Stage window = new Stage();
        String WindowName = "Open file";

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(WindowName);
        window.setMinWidth(330);

        Label classNameLabel = new Label();
        classNameLabel.setText("Name: ");


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5,5,5,5));
        grid.getRowConstraints().addAll( new RowConstraints(50), new RowConstraints(40));

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText("Select Sequence diagram");
        comboBox.setMinWidth(200);
        comboBox.getItems().add("ClassDiagram");
        comboBox.getItems().add("ClassDiagram2");

        grid.add(classNameLabel, 0, 0);
        grid.add(comboBox, 1, 0, 2, 1);


        Button save = new Button("Open");
        save.setOnAction(e -> {
            retValue = comboBox.getValue();
            window.close();
        });
        Button discard = new Button("Return");
        discard.setOnAction(e -> {
            retValue = null;
            window.close();
        });

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(save,discard);
        grid.add(buttonBar,0,3,3,1);


        grid.getColumnConstraints().addAll(
                new ColumnConstraints(70),
                new ColumnConstraints(200)
        );

//        grid.getRowConstraints().addAll(new RowConstraints(50));


        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();

        return retValue;
    }
}
