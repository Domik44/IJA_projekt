package uml.classDiagram;

import uml.Element;

/**
* UMLAttribute class represents classifier
* in class diagrams.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/

public class UMLClassifier extends Element {
	
	// Attributes
	private boolean userDefined;

	// Constructors
	/**
	 * Constructor for classifier object. Calls constructor of super class Element and sets that classifier is not user defined.
	 * @param name Contains name of classifier.
	 */
	public UMLClassifier(String name) {
		super(name);
		this.userDefined = false;
	}
	
	/**
	 * Constructor for classifier object. Calls constructor of super class Element and sets if classifier is (not) user defined.
	 * @param name Contains name of classifier.
	 * @param isUserDefined Contains values deciding if classifier is (not) user defined.
	 */
	public UMLClassifier(String name, boolean isUserDefined) {
		super(name);
		this.userDefined = isUserDefined;
	}

	// Methods
	/**
	 * Method creates new classifier by name.
	 * @param name Contains name of classifier.
	 * @return Returns reference to newly created classifier.
	 */
	public static UMLClassifier forName(String name) {
		return new UMLClassifier(name, true); // Tady zmena oproti ukolu 1
	}
	
	/**
	 * Method for determining if classifier is user defined.
	 * @return Returns true if classifier is user defined, false if not.
	 */
	public boolean isUserDefined() {
		return this.userDefined;
	}
	
	/**
	 * Override of toString method for output. 
	 * @return Returns name of classifier.
	 */
	@Override
	public String toString(){
		String result = this.getName();
		
		if(result.equals("null")) {
			result = "";
		}
		
		return result;
	}
}
