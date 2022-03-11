package uml;

public class UMLAttribute extends Element {

	// Attributes
	private UMLClassifier type;
	
	// Constructors
	public UMLAttribute(java.lang.String name, UMLClassifier type) {
		super(name);
		this.type = type;
	}
	
	// Methods
	public UMLClassifier getType() {
		return this.type;
	}
	
	@Override
	public java.lang.String toString(){
		java.lang.String result = this.getName() + ":" + this.type;
		
		return result;
	}

}
