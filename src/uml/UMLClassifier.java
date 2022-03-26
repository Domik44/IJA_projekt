/**
* <h1>UMLAttribute</h1>
* This class represents classifier
* in class diagrams.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/

package uml;

public class UMLClassifier extends Element {
	
	// Attributes
	private boolean userDefined;

	// Constructors
	public UMLClassifier(String name) {
		super(name);
		this.userDefined = false; // Tady zmena oproti ukolu 1
	}
	
	public UMLClassifier(String name, boolean isUserDefined) {
		super(name);
		this.userDefined = isUserDefined;
	}

	// Methods
	public static UMLClassifier forName(String name) {
		return new UMLClassifier(name, true); // Tady zmena oproti ukolu 1
	}
	
	public boolean isUserDefined() {
		return this.userDefined;
	}
	
	@Override
	public String toString(){
		// TODO changed this -> maybe change back for future use?
		//java.lang.String result = this.getName() + '(' + this.isUserDefined() +  ')';
		String result = this.getName();
		
		if(result.equals("null")) {
			result = "";
		}
		
		return result;
	}
}
