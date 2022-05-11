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

import java.sql.Array;

/**
 * EditClassWindowClassName
 */
public class CreateMessageWindow {

    static String[] retArr = null;
    /**
     * Init window and setup required elements
     */
    public static String[] display(String type, GParticipant selectedParticipant){
        Stage window = new Stage();

        boolean createORreturnORdelete = false;
        if(type.equals("Create") || type.equals("Return") || type.equals("Delete"))
            createORreturnORdelete = true;

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Create \"" + type + "\" message");
        window.setMinWidth(350);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5,5,5,5));

        grid.getColumnConstraints().addAll(
                new ColumnConstraints(100),
                new ColumnConstraints(200)
        );
        grid.getRowConstraints().addAll( new RowConstraints(50), new RowConstraints(40));
        if (!createORreturnORdelete)
            grid.getRowConstraints().addAll(new RowConstraints(20), new RowConstraints(60));


        Label classNameLabel = new Label("Create new: ");
        grid.add(classNameLabel, 0, 0);

        TextField field = new TextField();
        grid.add(field, 1, 0);

        Label orSelect = new Label("Or Select an existing one ");
        Button save = new Button("Save");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText("Select Sequence diagram");
        comboBox.setMinWidth(200);
        System.out.println(selectedParticipant.name);
        for (var m : selectedParticipant.UMLInstanceOf.getAllMethods()){
//            comboBox.getItems().add(m.toString());
        	comboBox.getItems().add(m.getName() + "()");
            System.out.println(m.toString());
        }

        save.setOnAction(e -> {
            retArr = new String[2];
            if (type.equals("Create") || type.equals("Return") || type.equals("Delete")){
                if (type.equals("Create"))
                    retArr[0] = "<<create>>";
                if (type.equals("Return"))
                    retArr[0] = "";
                if (type.equals("Delete"))
                    retArr[0] = "<<delete>>";
                retArr[0] += field.getText();
                retArr[1] = "false";
            }
            else {
                retArr[0] = field.getText();
                retArr[1] = "true";
                if (comboBox.getValue() != null) {
                    retArr[0] = comboBox.getValue();
                    retArr[1] = "false";
                }
            }
            window.close();
        });

        Button discard = new Button("Discard");
        discard.setOnAction(e -> {
            retArr = null;
            window.close();
        });

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(save,discard);
        if (!createORreturnORdelete) {
            grid.add(orSelect, 0, 1, 2, 1);
            grid.add(comboBox, 0, 2, 2, 1);
        }
        grid.add(buttonBar,0,3, 2, 1);



//        grid.getRowConstraints().addAll(new RowConstraints(50));


        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();

        return retArr;
    }
}
