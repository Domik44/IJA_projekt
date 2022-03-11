package uml;

public class Element {

	// Attributes
	private java.lang.String name;
	
	// Constructors
	public Element(java.lang.String name) {
		this.name = name;
	}
	
	// Methods
	public java.lang.String getName(){
		return this.name;
	}
	
	public void rename(java.lang.String newName) {
		this.name = newName;
	}	
	
}
