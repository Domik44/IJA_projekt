/**
* <h1>RelGeneralization</h1>
* This class represents generalization
* relation between classes.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/

package uml;

public class RelGeneralization extends UMLRelation {
	public RelGeneralization(UMLInterface parent, UMLInterface child, String type) {
		super(parent, child, type);
	}
}
