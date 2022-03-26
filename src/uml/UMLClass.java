/**
* <h1>UMLClass</h1>
* This class represents class
* in class diagrams.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/

package uml;

import java.util.List;

public class UMLClass extends UMLInterface {

	// Attributes
	private boolean abstraction;
	private List<UMLAttribute> attributes = new java.util.ArrayList<UMLAttribute>();
	
	// Constructors
	public UMLClass(String name) {
		super(name);
		this.abstraction = false;
	}

	// Methods
	public boolean isAbstract() {
		return this.abstraction;
	}
	
	public void setAbstract(boolean isAbstract) {
		this.abstraction = isAbstract;
	}
	
	// Attribute methods
	public boolean addAttribute(UMLAttribute attr) {
		return this.attributes.add(attr);
	}
	
	public int getAttrPosition(UMLAttribute attr) {
		return this.attributes.indexOf(attr);
	}
	
	public int moveAttrAtPosition(UMLAttribute attr, int pos) {
		if(this.attributes.contains(attr)) {
			this.attributes.remove(attr);
			this.attributes.add(pos, attr);
			
			return pos;
		}
		
		return -1;
	}
	
	public List<UMLAttribute> getAttributes(){
		List<UMLAttribute> copy = List.copyOf(this.attributes);
		
		return copy;
	}
}
