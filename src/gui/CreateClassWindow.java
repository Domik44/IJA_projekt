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
 * EditClassWindowClassName
 */
public class CreateClassWindow {
    /**
     * Init window and setup values
     */
    static String retValue = null;
    static boolean checkboxOut;

    public static String display(){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Create class/interface");
        window.setMinWidth(300);

        Label classNameLabel = new Label();
        classNameLabel.setText("ClassName: ");


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5,5,5,5));

        TextField field = new TextField();

        grid.add(classNameLabel, 0, 0);
        grid.add(field, 1, 0, 2, 1);


        Button save = new Button("Save");
        save.setOnAction(e -> {
            retValue = field.getText();
            window.close();
        });
        Button discard = new Button("Discard");
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
