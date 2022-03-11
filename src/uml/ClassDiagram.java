package uml;

public class ClassDiagram extends Element {
	// Attributes
	private java.util.List<UMLClassifier> classes = new java.util.ArrayList<UMLClassifier>();

	// Constructors
	public ClassDiagram(java.lang.String name) {
		super(name);
	}
	
	// Methods
	// Creates new class
	public UMLClass createClass(java.lang.String name) { // TO DO
		UMLClass newClass = new UMLClass(name);
		this.classes.add(newClass);
		
		return newClass;
		
	}
	
	// Tries to find classifier, if it doesnt exist it creates new one
	public UMLClassifier classifierForName(java.lang.String name) {	 // TO DO
		
		UMLClassifier res = this.findClassifier(name);
		if(res != null)
			return res;
		
		UMLClassifier result = UMLClassifier.forName(name);
		this.classes.add(result);
		return result;
		
	}
	
	// In case of success returns Classifier, otherwise null
	public UMLClassifier findClassifier(java.lang.String name) { // TO DO
		
		for (UMLClassifier umlClass : this.classes) {
			if(umlClass.getName() == name) {
				return umlClass;
			}
		}
		
		return null;

	}

}
