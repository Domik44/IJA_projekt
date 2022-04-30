package uml.relations;

import uml.classDiagram.UMLInterface;

/**
* RelAssociation class represents association
* relation between classes.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class RelAssociation extends RelAggregation {
	private UMLInterface asociationClass;
	
	/**
	 * Constructor for association. Calls constructor of super class RelAggregation.
	 * @param left Contains left class of association.
	 * @param right Contains right class of association.
	 * @param type Contains type of relation.
	 */
	public RelAssociation(UMLInterface left, UMLInterface right, String type) {
		super(left, right, type);
		//this.asociationClass = null;
	}
	
	/**
	 * Sets association class of relation (if there is one).
	 * @param assocClass Contains association class.
	 */
	public void setAssociationClass(UMLInterface assocClass) {
		this.asociationClass = assocClass;
	}
	
	/**
	 * Getter for association class.
	 * @return Returns reference to association class, null if there isn't one.
	 */
	public UMLInterface getAssociationClass() {
		return this.asociationClass;
	}
	
	public void deleteAssociationClass(UMLInterface delete) {
		if(this.asociationClass.equals(delete)) {
			this.asociationClass = null;
		}
	}
	
}
