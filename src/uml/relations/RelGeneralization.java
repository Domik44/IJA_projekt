package uml.relations;

import uml.classDiagram.UMLInterface;
import uml.classDiagram.UMLRelation;


/**
* RelGeneralization class represents generalization
* relation between classes.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class RelGeneralization extends UMLRelation {
	/**
	 * Constructor for generalization relation.
	 * @param parent Contains parent class/interface.
	 * @param child Contains child class/interface.
	 * @param type Contains type of relation.
	 */
	public RelGeneralization(UMLInterface parent, UMLInterface child, String type) {
		super(parent, child, type);
	}
}
