package uml.sequenceDiagram;

import java.util.UUID;

import uml.pos.Position;

public class UMLActivationBox {
	private String uniqueID;
	private Position startPosition = new Position(0,0);
	private Position endPosition = new Position(0,0);
	
	public UMLActivationBox() {
		this.uniqueID = UUID.randomUUID().toString();
	}
	
	public String getID() {
		return this.uniqueID;
	}
	
	public Position getStartPosition() {
		return this.startPosition;
	}
	
	public Position getEndPosition() {
		return this.endPosition;
	}
	
	public void setStartPosition(int x, int y) {
		this.startPosition.setX(x);
		this.startPosition.setY(y);
	}
	
	public void setEndPosition(int x, int y) {
		this.endPosition.setX(x);
		this.endPosition.setY(y);
	}
}
