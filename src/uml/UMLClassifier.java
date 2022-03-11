package uml;

public class UMLClassifier extends Element {
	
	// Attributes
	private boolean userDefined;

	// Constructors
	public UMLClassifier(java.lang.String name) {
		super(name);
		this.userDefined = true;
	}
	
	public UMLClassifier(java.lang.String name, boolean isUserDefined) {
		super(name);
		this.userDefined = isUserDefined;
	}

	// Methods
	public static UMLClassifier forName(java.lang.String name) {
		return new UMLClassifier(name, false);
	}
	
	public boolean isUserDefined() {
		return this.userDefined;
	}
	
	@Override
	public java.lang.String toString(){
		java.lang.String result = this.getName() + '(' + this.isUserDefined() +  ')';
		
		return result;
	}
}
