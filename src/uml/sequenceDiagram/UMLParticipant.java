package uml.sequenceDiagram;

import java.util.ArrayList;
import java.util.List;

import uml.Element;
import uml.classDiagram.UMLInterface;
import uml.pos.Position;


public class UMLParticipant extends Element {
	private Position startPosition = new Position(0,0);
	private Position endPosition = new Position(0,0);
	private Position lineStartPosition = new Position(0,0);
	private Position lineEndPosition = new Position(0,0);
	private UMLInterface instanceOf; // TODO
	private List<UMLActivationBox> boxes = new ArrayList<UMLActivationBox>();
 
	public UMLParticipant(String name) {
		super(name);
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
	
	public Position getLineStartPosition() {
		return this.lineStartPosition;
	}
	
	public Position getLineEndPosition() {
		return this.lineEndPosition;
	}
	
	public void setLineStartPosition(int x, int y) {
		this.lineStartPosition.setX(x);
		this.lineStartPosition.setY(y);
	}
	
	public void setLineEndPosition(int x, int y) {
		this.lineEndPosition.setX(x);
		this.lineEndPosition.setY(y);
	}
	
	public void setInstanceOf(UMLInterface instanceOf) {
		this.instanceOf = instanceOf;
	}
	
	public UMLInterface getInstanceOf() {
		return this.instanceOf;
	}
	
	@Override
	public String toString() {
		return this.instanceOf.getName() + ": " + this.getName();
	}
	
	public void addBox(UMLActivationBox box) {
		this.boxes.add(box);
	}
//	
	public void deleteBox(String ID) { //TODO 
		this.boxes.removeIf(box -> (box.getID().equals(ID)));
	}
	
	public List<UMLActivationBox> getBoxes(){
		return List.copyOf(this.boxes);
	}
	
	public UMLActivationBox getBox(String ID) {
		for(UMLActivationBox box : this.boxes) {
			if(box.getID().equals(ID)) {
				return box;
			}
		}
		
		return null;
	}
	
}
