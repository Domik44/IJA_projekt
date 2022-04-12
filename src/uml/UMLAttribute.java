package uml;

/**
* UMLAttribute class represents attribute
* of class/operation in class diagrams.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class UMLAttribute extends Element {

	// Attributes
	private UMLClassifier type;
	private UMLClassifier visibility;
	
	// Constructors
	/**
	 * Constructor for attribute object. Class constructor of super class Element and sets type of attribute.
	 * @param name Contains name of created attribute.
	 * @param type Contains data type of attribute.
	 */
	public UMLAttribute(String name, UMLClassifier type) {
		super(name);
		this.type = type;
	}
	
	/**
	 * Constructor for attribute object. Class constructor of super class Element, sets type of attribute and visibility.
	 * @param name Contains name of attribute.
	 * @param type Contains data type of attribute.
	 * @param visibility Contains visibility of attribute.
	 */
	public UMLAttribute(String name, UMLClassifier type, UMLClassifier visibility) {
		super(name);
		this.type = type;
		this.visibility = visibility;
	}
	
	// Methods
	/**
	 * Getter for type of attribute.
	 * @return Method returns reference to type of attribute.
	 */
	public UMLClassifier getType() {
		return this.type;
	}
	
	/**
	 * Getter for visibility of attribute.
	 * @return Method returns reference to visibility of attribute.
	 */
	public UMLClassifier getVisibility() {
		return this.visibility;
	}
	
	@Override
	/**
	 * Override of toString method.
	 * @return Method returns formated output showing information about attribute.
	 */
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
