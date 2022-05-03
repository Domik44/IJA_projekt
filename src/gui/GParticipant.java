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
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GParticipant extends Observable {
	// TODO -> make it private and create setters/getters
	String name;
	Group root;
	Rectangle border;
	Label participantNameLabel;
	VBox participantVB;
	List<MyNode> anchors;
	
	double eX;
	double eY;
	
	public GParticipant() {
		final int initialParticipantWidth = 150;
		int initialParticipantHeight = 30;
		
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
        
        DoubleBinding result = this.participantVB.heightProperty().add(10);
        this.border.heightProperty().bind(result);
        Platform.runLater(()->{
        	// TODO
        });
        
        this.root.getChildren().add(this.participantVB);
        
        this.border.setOnMousePressed(Event::consume);
        this.border.setOnMouseDragged(Event::consume);
        this.border.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                seteX(e.getX() + root.getTranslateX());
                seteY(e.getY() + root.getTranslateY());

                setChanged();
                notifyObservers(e);
            }
        });
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
        double origY = node.getYprop().doubleValue();

        DoubleBinding resultX = root.translateXProperty().add(origX);
        DoubleBinding resultY = root.translateYProperty().add(origY);

        node.getXprop().bind(resultX);
        node.getYprop().bind(resultY);
    }
    
    /**
     * Getter for root
     * @return root node
     */
    public Group getRoot(){
        return this.root;
    }
    
    /**
     * Getter for value of border height Property
     * @return border height
     */
    public double getHeight(){
        return border.heightProperty().getValue();
    }
    
    /**
     * Getter for value of border width Property
     * @return border width
     */
    public double getWidth(){
        return border.widthProperty().getValue();
    }
      
}
