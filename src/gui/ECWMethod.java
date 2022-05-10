package gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uml.classDiagram.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Edit Class Window for attribute
 */
public class ECWMethod {
    /**
     * Init window and setup values
     */
    static String[] retArray;

    static List<Gattribute> GattributeList;
    static Gattribute selectedGattr = null;
    static Button attribEdit;
    static Button attribDelete;
    static private final int labelHeight = 18;
    static int changeCnt;

    public static String[] display(UMLOperation operation){
        Stage window = new Stage();
        retArray = new String[3];

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Opperation");
        window.setWidth(800);
        window.setHeight(300);
        window.addEventHandler(MouseEvent.MOUSE_CLICKED, removeselectedAttribute);

        //Labels
        Label visibilityL = new Label();
        visibilityL.setText("Visibility: ");
        Label nameL = new Label();
        nameL.setText("Name: ");
        Label typeL = new Label();
        typeL.setText("Return Type: ");

        TextField visibilityTF = new TextField();
        TextField nameTF = new TextField();
        TextField typeTF = new TextField();

        if (operation != null) {
            visibilityTF.setText(operation.getVisibility().toString());
            nameTF.setText(operation.getName());
            typeTF.setText(operation.getType().toString());
        }

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(20);

        grid.add(visibilityL, 3, 0);
        grid.add(visibilityTF, 4, 0, 2, 1);
        grid.add(nameL, 3, 1);
        grid.add(nameTF, 4, 1, 2, 1);
        grid.add(typeL, 3, 2);
        grid.add(typeTF, 4, 2, 2, 1);


        ButtonBar attribsBB = new ButtonBar();
        Button attribAdd = new Button("Add Attribute");
        attribAdd.setOnAction(e -> {
            var retArr = ECWAttribute.display(null, false);
            UMLAttribute newUMLattr = new UMLAttribute("", new UMLClassifier("")); //TODO je toto ok?
            boolean change = false;
            if (retArr[0] != null && !retArr[0].equals("")) {
                newUMLattr.setVisibility(new UMLClassifier(retArr[0]));
                change = true;
            }
            if (retArr[1] != null && !retArr[1].equals("")) {
                newUMLattr.rename(retArr[1]);
                change = true;
            }
            if (retArr[2] != null && !retArr[2].equals("")) {
                newUMLattr.setType(new UMLClassifier(retArr[2]));
                change = true;
            }
            if (change) { //need to redraw attributes
                changeCnt++;
                operation.addArgument(newUMLattr);
                ClearGattributeLabels(grid);
                SetupGattributeLabels(operation, grid);
            }
        });
        attribEdit = new Button("Edit Attribute");
        attribEdit.setOnAction(e -> {
            var tmp = ECWAttribute.display(selectedGattr.attribute, false);
            boolean change = false;
            if (tmp[0] != null && !tmp[0].equals("")) {
                selectedGattr.attribute.setVisibility(new UMLClassifier(tmp[0]));
                change = true;
            }
            if (tmp[1] != null && !tmp[1].equals("")) {
                selectedGattr.attribute.rename(tmp[1]); //TODO what if rename fails?
                change = true;
            }
            if (tmp[2] != null && !tmp[2].equals("")) {
                selectedGattr.attribute.setType(new UMLClassifier(tmp[2]));
                change = true;
            }
            if (change) { //need to redraw attributes
                ClearGattributeLabels(grid);
                SetupGattributeLabels(operation, grid);
            }
        });
        attribEdit.setDisable(true);
        attribDelete = new Button("Delete Attribute");
        attribDelete.setDisable(true);

        if (operation != null)
            SetupGattributeLabels(operation, grid);
        attribsBB.getButtons().addAll(attribAdd, attribEdit, attribDelete);
        grid.add(attribsBB, 0, 2);


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
        save.setAlignment(Pos.BASELINE_LEFT);
        discard.setAlignment(Pos.BASELINE_LEFT);
        buttonBar.getButtons();
        grid.add(buttonBar,0,0, 1, 2);


//        grid.getColumnConstraints().addAll(
//                new ColumnConstraints(100),
//                new ColumnConstraints(200)
//        );

        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();

        return retArray;
}



    public static void SetupGattributeLabels(UMLOperation operation, GridPane grid){
        GattributeList = new ArrayList<>();
        //setup attrib labels
        var argumentList = operation.getArguments();
        for(int i = 0; i < argumentList.size(); i++){
            GattributeList.add(new Gattribute(argumentList.get(i)));
            GattributeList.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, selectedAttribute);
            grid.add(GattributeList.get(i), 0, 3 + i);
        }
    }


    public static void ClearGattributeLabels(GridPane grid){
        for (var Gattr : GattributeList){
            grid.getChildren().remove(Gattr);
        }
    }


    public static EventHandler selectedAttribute = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent ME) {
            Object obj = ME.getSource();
            if (obj instanceof Gattribute) {
                Gattribute tmp = (Gattribute) obj;
                selectedGattr = tmp;
                attribEdit.setDisable(false);
                attribDelete.setDisable(false);
                ME.consume();
            }
        }
    };

    public static EventHandler<MouseEvent> removeselectedAttribute = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent ME) {
            selectedGattr = null;
            attribEdit.setDisable(true);
            attribDelete.setDisable(true);
        }
    };

}
