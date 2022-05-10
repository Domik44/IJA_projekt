package gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;
import java.util.Observable;

import javafx.scene.text.FontWeight;
import uml.pos.Position;
import uml.sequenceDiagram.UMLMessage;

/**
 * This class represents message in GUI.
 * 
 * @author Dominik Pop
 *
 */
public class GMessage{
    String ID;
	GParticipant startParticipant;
	GParticipant endParticipant;
	GConnection connection;
	Label messageName;
	
	
	 /**
     * Constructor for GMessage. Allocate memory, set start and end,
     * set text format.
     * 
     * @param start Contains reference to start participant.
     * @param end Contains reference to end participant.
     */
    public GMessage(String ID, GParticipant start, GParticipant end) {
        this.ID = ID;
        this.startParticipant = start;
        this.endParticipant = end;
        this.connection = new GConnection();
        this.messageName = new Label();
        this.messageName.setFont(new Font("Arial", 15));
    }
	
	/**
     * Setup message from nodes (when reading from file).
     * 
     * @param start parent Anchor
     * @param end   child Anchor
     */
    public void setFromNodes(MyNodeAnchor start, MyNodeAnchor end){
        this.connection.setStart(start);
        this.connection.setEnd(end);
        this.connection.setBind();
        this.connection.connect();

        this.startParticipant.addAnchor(start);
        this.endParticipant.addAnchor(end);
    }
	
	 /**
     * Add path, MyNodes and MyNodeAnchors to pane.
     * 
     * @param pane pane that will hold objects
     */
	public void show(Pane pane){
        pane.getChildren().add(connection.path);
        pane.getChildren().add(connection.start.g);
        pane.getChildren().add(connection.end.g);
        pane.getChildren().add(messageName);
    }
	
	/**
     * Set label with Message name, text and position.
     * 
     * @param name to be named
     */
    public void setLabelName(String name) {
        this.messageName.setText(name);
        this.messageName.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void makeConnectionDraggableVerticaly( GConnection connection, double maxH) {
        connection.end.g.translateYProperty().bind(connection.start.g.translateYProperty());

        connection.start.g.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                double Y = e.getSceneY();
                final int magicConst = 55;
                connection.start.g.setTranslateY(Y - magicConst);
            }
        });

        connection.start.g.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (connection.start.g.getTranslateY() < 50)
                    connection.start.g.setTranslateY(100);
                GUIMain.SD.getMessage(ID).ClearList();
                GUIMain.SD.getMessage(ID).addPosition(new Position(0,(int) connection.start.g.getTranslateY()));
                GUIMain.SD.getMessage(ID).addPosition(new Position(0,(int) connection.start.g.getTranslateY()));
            }
        });
    }

    public void setEndRotation(){
        if(startParticipant.getRoot().getTranslateX() < endParticipant.getRoot().getTranslateX())
            this.connection.end.polygonSetRotatoinLR(true);
        else
            this.connection.end.polygonSetRotatoinLR(false);
    }
    /**
     * Set style based on message type
     * @param m UMLMessage
     */
    public void setStyle(UMLMessage m) {
        String type = m.getType();
        if (type.equals("Synchronous") || type.equals("Create") || type.equals("Delete")) {
            this.connection.end.polygonTriangleSmall();
            this.setEndRotation();
        }
        else if(type.equals("Asynchronous")){
            connection.path.getStrokeDashArray().addAll(20d, 10d);
            this.connection.end.polygonArrow();
            this.setEndRotation();
        }
        else if(type.equals("Return")){
            connection.path.getStrokeDashArray().addAll(20d, 10d);
            this.connection.end.polygonTriangleSmall();
            this.setEndRotation();
        }
    }

    public void setColorToInconsistent(){
        connection.path.setStroke(Color.RED);
    }
}
