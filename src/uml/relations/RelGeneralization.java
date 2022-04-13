package uml.relations;

import uml.UMLInterface;
import java.util.List;
import java.util.ArrayList;
import uml.UMLRelation;

/**
* RelGeneralization class represents generalization
* relation between classes.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class RelGeneralization extends UMLRelation {
	private List<UMLInterface> childClasses = new ArrayList<UMLInterface>();
	
	/**
	 * Constructor for generalization relation.
	 * @param parent Contains parent class/interface.
	 * @param child Contains child class/interface.
	 * @param type Contains type of relation.
	 */
	public RelGeneralization(UMLInterface parent, UMLInterface child, String type) {
		super(parent, child, type);
	}
	
	/**
	 * Getter for children list.
	 * @return Returns unmodifiable list of children classes.
	 */
	public List<UMLInterface> getChildren(){
		List<UMLInterface> copy = List.copyOf(this.childClasses);
		return copy;
	}
}
