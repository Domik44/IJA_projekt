package uml;

import java.util.List;

public class UMLClass extends UMLClassifier {

	// Attributes
	private boolean abstraction;
	private java.util.List<UMLAttribute> attributes = new java.util.ArrayList<UMLAttribute>();
	
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
	
	public java.util.List<UMLAttribute> getAttributes(){
		java.util.List<UMLAttribute> copy = List.copyOf(this.attributes);
		
		return copy;
	}

}
