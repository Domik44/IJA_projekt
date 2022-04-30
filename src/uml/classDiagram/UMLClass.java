package uml.classDiagram;

import java.util.List;

/**
* UMLClass class represents class
* in class diagrams.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class UMLClass extends UMLInterface {

	// Attributes
	private boolean abstraction;
	private List<UMLAttribute> attributes = new java.util.ArrayList<UMLAttribute>();
	
	// Constructors
	/**
	 * Constructor for class object. Also sets abstraction of class.
	 * @param name Contains name of created class.
	 */
	public UMLClass(String name) {
		super(name);
		this.abstraction = false;
	}

	// Methods
	/**
	 * Method for determining if class is abstract or not.
	 * @return Returns true if class is abstract, false if not.
	 */
	public boolean isAbstract() {
		return this.abstraction;
	}
	
	/**
	 * Method for setting abstraction of class.
	 * @param isAbstract Contains value, that sets abstraction of class.
	 */
	public void setAbstract(boolean isAbstract) {
		this.abstraction = isAbstract;
	}
	
	// Attribute methods
	/**
	 * Methods adds attribute to attributes list of class.
	 * @param attr Contains reference to attribute object.
	 * @return Returns true if attribute was added to list.
	 */
	public boolean addAttribute(UMLAttribute attr) {
		return this.attributes.add(attr);
	}
	
	/**
	 * Method for getting position of attribute in class.
	 * @param attr Contains reference to attribute object.
	 * @return Returns index representing position of attribute inside class.
	 */
	public int getAttrPosition(UMLAttribute attr) {
		return this.attributes.indexOf(attr);
	}
	
	/**
	 * Method for moving attribute to certain position inside class.
	 * @param attr Contains reference to attribute object.
	 * @param pos Contains position attribute where attribute will be moved to.
	 * @return Returns position number if successful, -1 if not.
	 */
	public int moveAttrAtPosition(UMLAttribute attr, int pos) {
		if(this.attributes.contains(attr)) {
			this.attributes.remove(attr);
			this.attributes.add(pos, attr);
			
			return pos;
		}
		
		return -1;
	}
	
	/**
	 * Getter for attributes list.
	 * @return Returns unmodifiable list of attributes of class.
	 */
	public List<UMLAttribute> getAttributes(){
		List<UMLAttribute> copy = List.copyOf(this.attributes);
		
		return copy;
	}
}
