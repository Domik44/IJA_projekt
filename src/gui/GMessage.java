package gui;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.util.List;
import java.util.Observable;

import uml.pos.Position;

/**
 * This class represents message in GUI.
 * 
 * @author Dominik Pop
 *
 */
public class GMessage extends Observable {
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
    public GMessage(GParticipant start, GParticipant end) {
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
     * @param list  list of connecting MyNodes
     */
    public void setFromList(List<MyNode> list, MyNodeAnchor start, MyNodeAnchor end){
        this.connection.setStart(start);
        this.connection.setEnd(end);
        if (list.size() > 0){
            this.connection.setBetween(list);
        }
        this.connection.setBind();
        this.connection.connect();

        this.startParticipant.addAnchor(connection.start);
        this.endParticipant.addAnchor(connection.end);
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
        for (MyNode node: connection.between) {
            pane.getChildren().add(node.g);
            node.g.toFront();
        }
    }
	
	/**
     * Set label with Message name, text and position.
     * 
     * @param name to be named
     * @param labelPosition position to be moved
     */
    public void setLabelName(String name, Position labelPosition) {
        this.messageName.setText(name);
        this.messageName.setTranslateX(labelPosition.getX());
        this.messageName.setTranslateY(labelPosition.getY());
    }
}
