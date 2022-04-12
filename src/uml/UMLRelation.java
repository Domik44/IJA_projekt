/**
* <h1>UMLRelation</h1>
* This class represents relation
* in class diagrams.
* 
* It is used for generalization of basic
* relation parameters.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/


package uml;

import java.util.List;
import uml.pos.Position;

public class UMLRelation{
	private UMLInterface leftClass;
	private UMLInterface rightClass;
	private String type;
	private List<Position> listPoints = new java.util.ArrayList<Position>();
	
	public UMLRelation(UMLInterface left, UMLInterface right, String type) {
		this.type = type;
		this.leftClass = left;
		this.rightClass = right;
	}
	
	// POZOR MOMENTALNE NEVRACI REFERENCE ALE JMENA!!
	public UMLInterface getLeftClass() {
		return this.leftClass;
	}
	
	public UMLInterface getRightClass() {
		return this.rightClass;
	}
	
	// TODO  -> dodelat getType -> popremylet jestli ho nedelat pres Classifier
	public String getType() {
		return this.type;
	}
	
	public void addPosition(Position pos) {
		listPoints.add(pos);
	}
	
	public List<Position> getPoints() {
		return this.listPoints;
	}
	
	public void changeList(List<Position> newList) {
		this.listPoints = null;
		this.listPoints = newList;
	}
}
