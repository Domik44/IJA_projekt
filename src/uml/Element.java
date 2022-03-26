/**
* <h1>Element</h1>
* Basic class used for inheritance of name.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/

package uml;

public class Element {

	// Attributes
	private String name;
	
	// Constructors
	public Element(String name) {
		this.name = name;
	}
	
	/** <h1>Methods</h1> */ 
	
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
