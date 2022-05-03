package gui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.*;
import javafx.scene.*;
import uml.classDiagram.UMLAttribute;
import uml.classDiagram.UMLClass;
import uml.classDiagram.UMLClassifier;
import uml.classDiagram.UMLInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Edit Class Window
 */
public class ECW {
    /**
     * Init window and setup values
     */
    static List<Gattribute> GattributeList;
    static List<Label> methodListLabel;
    static Gattribute selectedGattr = null;
    static Button attribEdit;
    static Button attribDelete;
    static private final int labelHeight = 18;
    static int changeCnt;

    public static int display( Object arg){
        if (!(arg instanceof UMLInterface))
            return 0;

        boolean isUMLClass = false;
        if (arg instanceof UMLClass) {
            isUMLClass = true;
        }
        UMLInterface Class = (UMLInterface) arg;

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        if (isUMLClass){
            window.setWidth(800);
            window.setTitle("Edit Class");
        }
        else{
            window.setTitle("Edit Interface");
            window.setWidth(420);
        }
        window.setHeight(300);
        if (isUMLClass)
            window.addEventHandler(MouseEvent.MOUSE_CLICKED, removeselectedAttribute);
        changeCnt = 0;

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(20);
        HBox hboxClassName = new HBox();
        hboxClassName.setSpacing(10);
        hboxClassName.setAlignment(Pos.CENTER_LEFT);
        Label classNameLabel = new Label();
        Button editClassName = new Button("Edit Interface Name");
        if (isUMLClass)
            editClassName.setText("Edit Class Name");
        String stringStart = (Class instanceof UMLClass) ? "Class Name:  " : "Interface Name:  ";
        classNameLabel.setText(stringStart + Class.getName());

        editClassName.setOnAction(e -> {
            String tmp = ECWClassName.display();
            if (tmp != null && !tmp.equals("")){
                Class.rename(tmp);
                classNameLabel.setText(stringStart + Class.getName());
            }
        });



        hboxClassName.getChildren().addAll(classNameLabel, editClassName);
        grid.add(hboxClassName, 0, 0, 5, 1);

        if (isUMLClass) {
            ButtonBar attribsBB = new ButtonBar();
            Button attribAdd = new Button("Add Attribute");
            attribAdd.setOnAction(e -> {
                var retArr = ECWAttribute.display(null);
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
                    ((UMLClass) Class).addAttribute(newUMLattr);
                    ClearGattributeLabels(grid);
                    SetupGattributeLabels(((UMLClass) Class), grid);
                }
            });
            attribEdit = new Button("Edit Attribute");
            attribEdit.setOnAction(e -> {
                var tmp = ECWAttribute.display(selectedGattr.attribute);
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
                    SetupGattributeLabels(((UMLClass) Class), grid);
                }
            });
            attribEdit.setDisable(true);
            attribDelete = new Button("Delete Attribute");
            attribDelete.setDisable(true);

            attribsBB.getButtons().addAll(attribAdd, attribEdit, attribDelete);
            grid.add(attribsBB, 0, 1, 1, 3);
        }

        ButtonBar methodsBB = new ButtonBar();
        Button methodAdd = new Button("Add Method");
        Button methodEdit = new Button("Edit Method");
        methodEdit.setDisable(true);
        Button methodDelete = new Button("Delete Method");
        methodDelete.setDisable(true);

        methodsBB.getButtons().addAll(methodAdd, methodEdit, methodDelete);
        grid.add(methodsBB, 3, 1, 1, 3);

        if (isUMLClass)
            SetupGattributeLabels(((UMLClass)Class),grid);


        methodListLabel = new ArrayList<>();
        var methodList = Class.getOperations();
        for(int i = 0; i < methodList.size(); i++){
            methodListLabel.add(new Label(methodList.get(i).toString()));
            grid.add(ECW.methodListLabel.get(i), 3, 3+ i, 1, 2);
        }

        Scene scene = new Scene(grid);

        window.setScene(scene);
        window.showAndWait();
        return changeCnt * labelHeight;
    }

    static void EnableAttrButtons(){
        attribEdit.setDisable(false);
        attribDelete.setDisable(false);
    }

    public static void SetupGattributeLabels(UMLClass Class, GridPane grid){
        GattributeList = new ArrayList<>();
        //setup attrib labels
        var attrList = Class.getAttributes();
        for(int i = 0; i < attrList.size(); i++){
            GattributeList.add(new Gattribute(attrList.get(i)));
            GattributeList.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, selectedAttribute);
            grid.add(GattributeList.get(i), 0, 3+ i, 1, 2);
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
                System.out.println("Clicked " + tmp.label.getText());
                selectedGattr = tmp;
                EnableAttrButtons();
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
