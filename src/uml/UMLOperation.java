/**
* <h1>UMLOperation</h1>
* This class represents operation
* of class/interface in class diagrams.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/

package uml;

import java.util.List;

public class UMLOperation extends UMLAttribute {

	// Attributes
	private List<UMLAttribute> arguments = new java.util.ArrayList<UMLAttribute>();
	
	// Constructors
	public UMLOperation(String name, UMLClassifier type, UMLClassifier visibility) {
		super(name, type, visibility);
	}

	// Methods
	public static UMLOperation createOperation(java.lang.String name, UMLClassifier type, UMLClassifier visibility, UMLAttribute... args) {
		UMLOperation result = new UMLOperation(name, type, visibility);
		for (UMLAttribute umlAttribute : args) {
			result.arguments.add(umlAttribute);
		}
		
		return result;
		
	}
	
	public boolean addArgument(UMLAttribute arg) {
		return this.arguments.add(arg);
	}
	
	public List<UMLAttribute> getArguments(){
		List<UMLAttribute> copy = List.copyOf(this.arguments);
		
		return copy;
	}
	
	@Override
	public String toString(){
		//String result = this.getVisibility() + " " + this.getName() + "("+ this.getArguments() + "): " + this.getType();
		String result = this.getVisibility() + " " + this.getName() + "(";
		for(UMLAttribute at : this.getArguments()) {
			result = result + at;
		}
		result = result + "): " + this.getType();
		
		return result;
	}
}
