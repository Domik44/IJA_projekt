package uml.sequenceDiagram;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uml.Element;
import uml.classDiagram.UMLAttribute;
import uml.pos.Position;

public class UMLMessage extends Element {
	private String type;
	private List<Position> listPoints = new java.util.ArrayList<Position>(); // TODO -> v zadani nejsou uvedeny self message, takze by teoreticky stacili jen 2 body!
	private Position namePosition = new Position(0,0);
	private UMLParticipant startObject;
	private UMLParticipant endObject;
	private String uniqueID;
	private List<UMLAttribute> attributes = new ArrayList<UMLAttribute>();
	
	public UMLMessage(String name, String type) {
		super(name);
		this.uniqueID = UUID.randomUUID().toString();
		this.type = type;
	}
	
	public String getID() {
		return this.uniqueID;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	// Position methods
	
	/**
	 * Method adds position of message point to points list.
	 * @param pos Contains position of point holding (x, y) coordinates.
	 */
	public void addPosition(Position pos) {
		listPoints.add(pos);
	}
	
	/**
	 * Getter for points list.
	 * @return Returns unmodifiable list of relation points.
	 */
	public List<Position> getPoints() {
		List<Position> copy = List.copyOf(this.listPoints);
		return copy;
	}
	
	/**
	 * Method gets rid of old list of points and takes new one.
	 * @param newList Returns reference to new points list.
	 */
	public void changeList(List<Position> newList) {
		this.listPoints = null;
		this.listPoints = newList;
	}
	
	
	/**
	 * Getter for position of name of message.
	 * @return Returns reference to position object holding (x, y) coordinates.
	 */
	public Position getPosition() {
		return this.namePosition;
	}
		
	/**
	 * Sets (x, y) coordinates of name of message in.
	 * @param x Contains number representing x coordinate.
	 * @param y Contains number representing y coordinate.
	 */
	public void setNamePosition(int x, int y) {
		this.namePosition.setX(x);
		this.namePosition.setY(y);
	}
	
	// Start and end objects
	
	public void setStartObject(UMLParticipant startObject) {
		this.startObject = startObject;
	}
	
	public UMLParticipant getStartObject() {
		return this.startObject;
	}
	
	
	public void setEndObject(UMLParticipant endObject) {
		this.endObject = endObject;
	}
	
	public UMLParticipant getEndObject() {
		return this.endObject;
	}
	
	@Override
	public String toString() {
		return "<<" + this.getName() + ">>";
	}
}
