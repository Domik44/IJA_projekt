/**
* <h1>UMLInterface</h1>
* This class represents interface
* in class diagrams.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/

package uml;

import java.util.List;

public class UMLInterface extends UMLClassifier {
	private List<UMLOperation> methods = new java.util.ArrayList<UMLOperation>();
	
	// Constructors
		public UMLInterface(String name) {
			super(name);
		}
		
	// Operation methods
	public boolean addOperation(UMLOperation operation) {
		return this.methods.add(operation);
	}
		
	public int getOperationPosition(UMLOperation operation) {
		return this.methods.indexOf(operation);
	}
	
	public int moveOperationAtPosition(UMLOperation operation, int pos) {
		if(this.methods.contains(operation)) {
			this.methods.remove(operation);
			this.methods.add(pos, operation);
			
			return pos;
		}
		
		return -1;
	}
	
	public List<UMLOperation> getOperations(){
		List<UMLOperation> copy = List.copyOf(this.methods);
		
		return copy;
	}
}