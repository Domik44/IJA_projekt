/**
* <h1>RelGeneralization</h1>
* This class represents generalization
* relation between classes.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/

package uml.relations;

import uml.UMLInterface;
import java.util.List;
import java.util.ArrayList;
import uml.UMLRelation;

public class RelGeneralization extends UMLRelation {
	private List<UMLInterface> childClasses = new ArrayList<UMLInterface>();
	
	public RelGeneralization(UMLInterface parent, List<UMLInterface> childClasses, String type) {
		super(parent, null, type);
		this.childClasses = childClasses;
	}
	
	public List<UMLInterface> getChildren(){
		return this.childClasses;
	}
}
