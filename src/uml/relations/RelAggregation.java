package uml.relations;

import uml.classDiagram.UMLInterface;
import uml.classDiagram.UMLRelation;
import uml.pos.Position;

/**
* RelAggregation class represents aggregation
* relation between classes.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class RelAggregation extends UMLRelation {
	private String leftCardinality = "";
	private String rightCardinality = "";
	private String label = "";
	private Position labelPos = new Position(0,0);
	
	/**
	 * Constructor for aggregation object. Calls constructor of super class UMLRelation.
	 * @param left Contains left interface/class of aggregation.
	 * @param right Contains right interface/class of aggregation.
	 * @param type Contains type of relation.
	 */
	public RelAggregation(UMLInterface left, UMLInterface right, String type) {
		super(left, right, type);
	}
	
	/**
	 * Setter for cardinality of aggregation.
	 * @param left Contains cardinality of left class/interface.
	 * @param right Contains cardinality of right class/interface.
	 */
	public void setCardinality(String left, String right) {
		this.leftCardinality = left;
		this.rightCardinality = right;
	}
	
	public void setLeftCardinality(String left) {
		this.leftCardinality = left;
	}
	
	public void setRightCardinality(String right) {
		this.rightCardinality = right;
	}
	
	/**
	 * Getter for cardinality of left class/interface.
	 * @return Returns cardinality.
	 */
	public String getLeftCardinality() {
		return this.leftCardinality;
	}
	
	/**
	 * Getter for cardinality of right class/interface.
	 * @return Returns cardinality.
	 */
	public String getRightCardinality() {
		return this.rightCardinality;
	}
	
	/**
	 * Sets label of aggregation.
	 * @param label Contains label describing name of relation.
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Sets label position.
	 * @param x Contains x coordinate.
	 * @param y Contains y coordinate.
	 */
	public void setLabelPosition(int x, int y) {
		this.labelPos.setX(x);
		this.labelPos.setY(y);
	}
	
	/**
	 * Getter for position of label of relation.
	 * @return Returns reference to position object holding (x, y) coordinates.
	 */
	public Position getLabelPosition() {
		return this.labelPos;
	}
	
	/**
	 * Getter for label of aggregation.
	 * @return Return aggregation label.
	 */
	public String getLabel() {
		return this.label;
	}
	
}
