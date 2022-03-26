/**
* <h1>RelAssociation</h1>
* This class represents association
* relation between classes.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/

package uml;

public class RelAssociation extends RelAggregation {
	private UMLInterface asociationClass;
	
	public RelAssociation(UMLInterface left, UMLInterface right, String type) {
		super(left, right, type);
	}
	
	public void setAssociationClass(UMLInterface assocClass) {
		this.asociationClass = assocClass;
	}
	
	public UMLInterface getAssociationClass() {
		return this.asociationClass;
	}
}
