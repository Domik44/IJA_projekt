/**
* <h1>RelAggregation</h1>
* This class represents aggregation
* relation between classes.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/

package uml;

public class RelAggregation extends UMLRelation {
	private String leftCardinality = "";
	private String rightCardinality = "";
	private String label = "";
	
	public RelAggregation(UMLInterface left, UMLInterface right, String type) {
		super(left, right, type);
	}
	
	public void setCardinality(String left, String right) {
		this.leftCardinality = left;
		this.rightCardinality = right;
	}
	
	public String getLeftCardinality() {
		return this.leftCardinality;
	}
	
	public String getRightCardinality() {
		return this.rightCardinality;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
}
