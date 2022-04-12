package uml;

/**
* Element class is used for inheritance of name attribute.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class Element {

	// Attributes
	private String name;
	
	// Constructors
	/**
	 * Constructor for Element class. Sets name of created object.
	 * @param name Contains name of object.
	 */
	public Element(String name) {
		this.name = name;
	}
	
	// Methods
	/**
	* This method is used to receive name of object.
	* 
	* @return Returns name of object.
	*/
	public String getName(){
		return this.name;
	}
	
	/**
	* This method is used to change name of object.
	* 
	* @param newName This is the new name of object.
	*/
	public void rename(String newName) {
		this.name = newName;
	}	
	
}
