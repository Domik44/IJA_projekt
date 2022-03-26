/**
* <h1>UMLAttribute</h1>
* This class represents attribute
* of class/operation in class diagrams.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/


package uml;

public class UMLAttribute extends Element {

	// Attributes
	private UMLClassifier type;
	private UMLClassifier visibility;
	
	// Constructors
	public UMLAttribute(String name, UMLClassifier type) {
		super(name);
		this.type = type;
	}
	
	public UMLAttribute(String name, UMLClassifier type, UMLClassifier visibility) {
		super(name);
		this.type = type;
		this.visibility = visibility;
	}
	
	// Methods
	public UMLClassifier getType() {
		return this.type;
	}
	
	public UMLClassifier getVisibility() {
		return this.visibility;
	}
	
	@Override
	public String toString(){
		UMLClassifier vis = this.getVisibility();
		String result;
		if(vis == null) {
			result = this.getName() + ": " + this.type;
		}
		else {			
			result = this.getVisibility() + " " + this.getName() + ": " + this.type;
		}
		
		return result;
	}

}
