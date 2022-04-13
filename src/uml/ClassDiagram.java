package uml;

import java.util.ArrayList;
import java.util.List;
import uml.relations.RelAggregation;
import uml.relations.RelAssociation;
import uml.relations.RelGeneralization;

/**
* Class diagram class represents class diagram
* as one piece. <br>It contains Classes/Interfaces and Relations.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class ClassDiagram extends Element {
	// Attributes
	private List<UMLInterface> interfaces = new ArrayList<UMLInterface>();
	private List<UMLClass> classes = new ArrayList<UMLClass>();
	private List<RelAssociation> relAssociation = new ArrayList<RelAssociation>();
	private List<RelAggregation> relAggregation = new ArrayList<RelAggregation>();
	private List<RelGeneralization> relGeneralization = new ArrayList<RelGeneralization>();
	private List<UMLClassifier> types = new ArrayList<UMLClassifier>();

	// Constructors
	/**
	 * Constructor for ClassDiagram object. 
	 * Calls constructor of super class Element.
	 * @param name Contains name of class diagram.
	 */
	public ClassDiagram(String name) {
		super(name);
	}
	
	/**
	 * <h2>Methods</h2>
	 */

	/**
	 * Tries to find classifier, if it doesn't exist, creates new one.
	 * @param name Contains name of searched classifier.
	 * @return Returns reference to found/created classifier.
	 */
	public UMLClassifier classifierForName(String name) {	 // TO DO
		
		UMLClassifier res = this.findClassifier(name);
		if(res != null)
			return res;
		
		UMLClassifier result = UMLClassifier.forName(name);
		this.types.add(result);
		
		return result;
	}
	
	/**
	 * Method for finding classifier by a name.
	 * @param name Contains name of the classifier.
	 * @return If found returns reference to classifier, otherwise null.
	 */
	public UMLClassifier findClassifier(String name) { // TO DO
		
		for (UMLClassifier classifier : this.types) {
			if(classifier.getName() == name) {
				return classifier;
			}
		}
		
		return null;
	}
	
	/**
	 * Method for creating basic data types and adding them to types list.
	 */
	public void basicTypes() {
		String[] basices = {"int", "str", "bool", "float", "+", "-", "#", "~"};
		for(String str : basices) {
			UMLClassifier obj = new UMLClassifier(str);
			this.types.add(obj);
		}
	}
	
	/**
	 * Method for creating interface object and adding it to class list.
	 * @param name Contains name of created interface.
	 * @return Returns reference to newly created interface.
	 */
	public UMLInterface createInterface(String name) { // TO DO
		UMLInterface newInterface = new UMLInterface(name);
		this.interfaces.add(newInterface);		
		
		return newInterface;	
	}
	
	/**
	 * Getter for list of interfaces.
	 * @return Returns unmodifiable list of itnerfaces.
	 */
	public List<UMLInterface> getInterfaces(){
		List<UMLInterface> copyList = List.copyOf(this.interfaces);
		return copyList;
	}
	
	/**
	 * Method compares interfaces in list by name.
	 * @param name Contains name of interface we are searching for.
	 * @return If comparison is successful return reference to interface, otherwise null.
	 */
	public UMLInterface getInterface(String name) {
		for(UMLInterface in : this.interfaces) {
			if(in.getName().equals(name)) {
				return in;
			}
		}
		
		return null;
	}
	
	/**
	 * Method for creating class object and adding it to class list.
	 * @param name Contains name of created class.
	 * @return Returns reference to newly created class.
	 */
	public UMLClass createClass(String name) { // TO DO
		UMLClass newClass = new UMLClass(name);
		this.classes.add(newClass);		
		
		return newClass;	
	}
	
	/**
	 * Getter for list of classes.
	 * @return Returns unmodifiable list of classes.
	 */
	public List<UMLClass> getClasses(){
		List<UMLClass> copyList = List.copyOf(this.classes);
		return copyList;
	}
	
	/**
	 * Method compares classes in list by name.
	 * @param name Contains name of class we are searching for.
	 * @return If comparison is successful return reference to class, otherwise null.
	 */
	public UMLClass getClass(String name) {
		for(UMLClass cl : this.classes) {
			if(cl.getName().equals(name)) {
				return cl;
			}
		}
		
		return null;
	}
	
	/**
	 * Method for creating Generalizations and adding them to Generalizations list.
	 * @param parent Contains reference to parent Class/Interface.
	 * @param child Contains reference to child Class/Interface.
	 * @param type Contains type of Relation.
	 * @return Method returns newly created Aggregation.
	 */
	public RelGeneralization createGeneralization(UMLInterface parent, UMLInterface child, String type) {
		RelGeneralization newRelation = new RelGeneralization(parent, child, type);
		this.relGeneralization.add(newRelation);
		
		return newRelation;
	}
	
	/**
	 * Method for creating Aggregations and adding them to Aggregations list.
	 * @param parent Contains reference to Class/Interface of relation where compozition/aggregagtion is.
	 * @param child Contains reference to Class/Interface of relation.
	 * @param type Contains type of Relation.
	 * @return Method returns newly created Aggregation.
	 */
	public RelAggregation createAggregation(UMLInterface parent, UMLInterface child, String type) {
		RelAggregation newRelation = new RelAggregation(parent, child, type);
		this.relAggregation.add(newRelation);
		
		return newRelation;
	}
	
	/**
	 * Method for creating Associations and adding them to Associations list.
	 * @param parent Contains reference to left Class/Interface of relation.
	 * @param child Contains reference to right Class/Interface of relation.
	 * @param type Contains type of Relation.
	 * @return Method returns newly created Association.
	 */
	public RelAssociation createAssociation(UMLInterface parent, UMLInterface child, String type) {
		RelAssociation newRelation = new RelAssociation(parent, child, type);
		this.relAssociation.add(newRelation);
		
		return newRelation;
	}
	
	/**
	 * Getter for Aggregations list.
	 * @return Method returns unmodifiable list of Aggregations.
	 */
	public List<RelAggregation> getAggregations(){
		List<RelAggregation> copyList = List.copyOf(this.relAggregation);
		return copyList;
	}
	
	/**
	 * Getter for Generalizations list.
	 * @return Method returns unmodifiable list of Generalizations.
	 */
	public List<RelGeneralization> getGeneralizations(){
		List<RelGeneralization> copyList = List.copyOf(this.relGeneralization);
		return copyList;
	}
	
	/**
	 * Getter for Associations list.
	 * @return Method returns unmodifiable list of Associations.
	 */
	public List<RelAssociation> getAssociations(){
		List<RelAssociation> copyList = List.copyOf(this.relAssociation);
		return copyList;
	}

}
