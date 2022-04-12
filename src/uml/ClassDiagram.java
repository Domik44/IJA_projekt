/**
* <h1>ClassDiagram</h1>
* This class represents class diagram
* as one piece.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/

package uml;

import java.util.ArrayList;

import java.util.List;

import uml.relations.RelAggregation;
import uml.relations.RelAssociation;
import uml.relations.RelGeneralization;

public class ClassDiagram extends Element {
	// Attributes
	private List<UMLInterface> interfaces = new ArrayList<UMLInterface>();
	private List<UMLClass> classes = new ArrayList<UMLClass>();
	private List<RelAssociation> relAssociation = new ArrayList<RelAssociation>();
	private List<RelAggregation> relAggregation = new ArrayList<RelAggregation>();
	private List<RelGeneralization> relGeneralization = new ArrayList<RelGeneralization>();
	private List<UMLClassifier> types = new ArrayList<UMLClassifier>();

	// Constructors
	public ClassDiagram(String name) {
		super(name);
	}
	
	// Methods
	
	// Tries to find classifier, if it doesn't exist it creates new one
	public UMLClassifier classifierForName(String name) {	 // TO DO
		
		UMLClassifier res = this.findClassifier(name);
		if(res != null)
			return res;
		
		UMLClassifier result = UMLClassifier.forName(name);
		this.types.add(result);
		
		return result;
	}
	
	// In case of success returns Classifier, otherwise null
	public UMLClassifier findClassifier(String name) { // TO DO
		
		for (UMLClassifier classifier : this.types) {
			if(classifier.getName() == name) {
				return classifier;
			}
		}
		
		return null;
	}
	
	// Creates basic data types and adds them to types list
	public void basicTypes() {
		String[] basices = {"int", "str", "bool", "float", "+", "-", "#", "~"};
		for(String str : basices) {
			UMLClassifier obj = new UMLClassifier(str);
			this.types.add(obj);
		}
	}
	
	// Creates interface and adds it to interface list
	public UMLInterface createInterface(String name) { // TO DO
		UMLInterface newInterface = new UMLInterface(name);
		this.interfaces.add(newInterface);		
		
		return newInterface;	
	}
	
	
	public List<UMLInterface> getInterfaces(){
		List<UMLInterface> copyList = List.copyOf(this.interfaces);
		return copyList;
	}
	
	public UMLInterface getInterface(String name) {
		for(UMLInterface in : this.interfaces) {
			if(in.getName().equals(name)) {
				return in;
			}
		}
		
		return null;
	}
	
	// Creates class and adds it to class list
	public UMLClass createClass(String name) { // TO DO
		UMLClass newClass = new UMLClass(name);
		this.classes.add(newClass);		
		
		return newClass;	
	}
	
	
	public List<UMLClass> getClasses(){
		List<UMLClass> copyList = List.copyOf(this.classes);
		return copyList;
	}
	
	public UMLClass getClass(String name) {
		for(UMLClass cl : this.classes) {
			if(cl.getName().equals(name)) {
				return cl;
			}
		}
		
		return null;
	}
	
	// Creates relation and adds it to relation list
	public RelGeneralization createGeneralization(UMLInterface parent, List<UMLInterface> childClasses,String type) {
		RelGeneralization newRelation = new RelGeneralization(parent, childClasses, type);
		this.relGeneralization.add(newRelation);
		
		return newRelation;
	}
	
	public RelAggregation createAggregation(UMLInterface parent, UMLInterface child, String type) {
		RelAggregation newRelation = new RelAggregation(parent, child, type);
		this.relAggregation.add(newRelation);
		
		return newRelation;
	}
	
	public RelAssociation createAssociation(UMLInterface parent, UMLInterface child, String type) {
		RelAssociation newRelation = new RelAssociation(parent, child, type);
		this.relAssociation.add(newRelation);
		
		return newRelation;
	}
	
	//
	public List<RelAggregation> getAggregations(){
		List<RelAggregation> copyList = List.copyOf(this.relAggregation);
		return copyList;
	}
	
	public List<RelGeneralization> getGeneralizations(){
		List<RelGeneralization> copyList = List.copyOf(this.relGeneralization);
		return copyList;
	}
	
	public List<RelAssociation> getAssociations(){
		List<RelAssociation> copyList = List.copyOf(this.relAssociation);
		return copyList;
	}

}
