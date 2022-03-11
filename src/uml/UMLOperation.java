package uml;

import java.util.List;

public class UMLOperation extends UMLAttribute {

	// Attributes
	private List<UMLAttribute> arguments = new java.util.ArrayList<UMLAttribute>();
	
	// Constructors
	public UMLOperation(String name, UMLClassifier type) {
		super(name, type);
	}

	// Methods
	public static UMLOperation create(java.lang.String name, UMLClassifier type, UMLAttribute... args) {
		UMLOperation result = new UMLOperation(name, type);
		for (UMLAttribute umlAttribute : args) {
			result.arguments.add(umlAttribute);
		}
		
		return result;
		
	}
	
	public boolean addArgument(UMLAttribute arg) {
		return this.arguments.add(arg);
	}
	
	public List<UMLAttribute> getArguments(){
		List<UMLAttribute> copy = List.copyOf(this.arguments);
		
		return copy;
	}
}
