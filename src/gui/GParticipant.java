package gui;

import java.util.List;
import java.util.Observable;

import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import uml.pos.Position;

public class GParticipant extends Observable{
	String name;
	Group root;
	Rectangle border;
	Label participantNameLabel;
	VBox participantVB;
    Group dashedStart;
	Line dashed;

	double eX;
	double eY;
	
	public GParticipant() {
		final int initialParticipantWidth = 200; //TODO change width during runtime if needed?
		final int initialParticipantHeight = 30;
		
		this.root = new Group();
		this.border = new Rectangle(initialParticipantWidth, initialParticipantHeight);
		border.setFill(Color.BLUEVIOLET);
		root.getChildren().addAll(border);
		
		participantVB = new VBox();
        participantVB.setPrefSize(initialParticipantWidth - 10, initialParticipantHeight);
        participantVB.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        participantVB.setTranslateX(5);
        participantVB.setTranslateY(5);
        participantVB.setAlignment(Pos.CENTER);
        
        this.participantNameLabel = new Label("name");
        this.participantVB.getChildren().add(this.participantNameLabel);

        this.border.heightProperty().bind(this.participantVB.heightProperty().add(10));
        
        this.root.getChildren().add(this.participantVB);


        dashedStart = new Group();
        dashedStart.setTranslateX(initialParticipantWidth/2);
        dashedStart.setTranslateY(initialParticipantHeight);
        dashed = new Line(0, 10, 0, 1200);
        dashed.getStrokeDashArray().addAll(25d, 20d);
        dashed.setStrokeWidth(6);
        dashed.setStyle("-fx-stroke: white;");
        dashedStart.getChildren().add(dashed);
        root.getChildren().add(dashedStart);
        border.toFront();
        participantVB.toFront();
        dashed.toBack();

	}
	
	/**
     * Setter for eX (drag event related)
     * @param newX new X value
     */
    public void seteX(double newX){
        eX = newX;
    }

    /**
     * Setter for eY (drag event related)
     * @param newY new Y value
     */
    public void seteY(double newY){
        eY = newY;
    }
    
    /**
     * Add Anchor to class so it will be dragged around with it
     * @param node anchor to be added
     */
    public void addAnchor(MyNodeAnchor node){
        double origX = node.getXprop().doubleValue();

        DoubleBinding resultX = root.translateXProperty().add(origX).add(dashedStart.getTranslateX());

        node.getXprop().bind(resultX);
    }
    
    /**
     * Getter for root
     * @return root node
     */
    public Group getRoot(){
        return this.root;
    }

    public void movedNotifyObservers(Position position){
        setChanged();
        notifyObservers(position);
    }
}
