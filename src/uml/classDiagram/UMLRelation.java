package uml.classDiagram;

import java.util.List;
import java.util.UUID;

import uml.Element;
import uml.pos.Position;

/**
* UMLRelation class represents relation
* in class diagrams.
* 
* It is used for generalization of basic
* relation parameters.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class UMLRelation extends Element{
	private UMLInterface leftClass;
	private UMLInterface rightClass;
	private String type;
	private List<Position> listPoints = new java.util.ArrayList<Position>();
	
	/**
	 * Constructor for relation object. Sets type, left and right class of relation.
	 * @param left Contains reference to interface/class.
	 * @param right Contains reference to interface/class.
	 * @param type Contains type of relation.
	 */
	public UMLRelation(UMLInterface left, UMLInterface right, String type) {
		super(UUID.randomUUID().toString()); // represents unique id
		this.type = type;
		this.leftClass = left;
		this.rightClass = right;
	}
	
	
	/**
	 * Getter for left class/interface of relation.
	 * @return Returns name of class/interface. (Because of override toString in UMLClassifier)
	 */
	public UMLInterface getLeftClass() {
		return this.leftClass;
	}
	
	/**
	 * Getter for right class/interface of relation.
	 * @return Returns name of class/interface.
	 */
	public UMLInterface getRightClass() {
		return this.rightClass;
	}
	
	/**
	 * Getter for type of relation.
	 * @return Returns type of relation.
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Method adds position of relation point to points list.
	 * @param pos Contains position of point holding (x, y) coordinates.
	 */
	public void addPosition(Position pos) {
		listPoints.add(pos);
	}
	
	public void deletePosition(Position pos) {
		listPoints.remove(pos);
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
}
