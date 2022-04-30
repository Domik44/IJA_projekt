package uml.classDiagram;

import java.util.ArrayList;
import java.util.List;

import uml.Element;
import uml.relations.RelAggregation;
import uml.relations.RelAssociation;
import uml.relations.RelGeneralization;
import uml.sequenceDiagram.SequenceDiagram;
import workers.Converter;

/**
 * Class diagram class represents class diagram as one piece. <br>
 * It contains Classes/Interfaces and Relations.
 *
 * @author Dominik Pop
 * @version 1.0
 * @since 2022-03-23
 */
public class ClassDiagram extends Element {
	// Attributes
	private List<UMLInterface> interfaces = new ArrayList<UMLInterface>();
	private List<UMLClass> classes = new ArrayList<UMLClass>();
	private List<RelAssociation> relAssociation = new ArrayList<RelAssociation>();
	private List<RelAggregation> relAggregation = new ArrayList<RelAggregation>();
	private List<RelGeneralization> relGeneralization = new ArrayList<RelGeneralization>();
	private List<UMLClassifier> types = new ArrayList<UMLClassifier>();
	private List<SequenceDiagram> sequenceDiagrams = new ArrayList<SequenceDiagram>();
	private boolean isSaved = true;

	// Constructors
	/**
	 * Constructor for ClassDiagram object. Calls constructor of super class
	 * Element.
	 * 
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
	 * 
	 * @param name Contains name of searched classifier.
	 * @return Returns reference to found/created classifier.
	 */
	public UMLClassifier classifierForName(String name) { // TO DO
		
		name = Converter.converToCamelCase(name);
		UMLClassifier res = this.findClassifier(name);
		if (res != null)
			return res;

		UMLClassifier result = UMLClassifier.forName(name);
		this.types.add(result);

		return result;
	}

	/**
	 * Method for finding classifier by a name.
	 * 
	 * @param name Contains name of the classifier.
	 * @return If found returns reference to classifier, otherwise null.
	 */
	public UMLClassifier findClassifier(String name) { // TO DO

		for (UMLClassifier classifier : this.types) {
			if (classifier.getName() == name) {
				return classifier;
			}
		}

		return null;
	}

	/**
	 * Method for creating basic data types and adding them to types list.
	 */
	public void basicTypes() {
		String[] basics = { "int", "str", "bool", "float", "+", "-", "#", "~" };
		for (String str : basics) {
			UMLClassifier obj = new UMLClassifier(str);
			this.types.add(obj);
		}
	}

	/**
	 * Method for creating interface object and adding it to class list.
	 * 
	 * @param name Contains name of created interface.
	 * @return Returns reference to newly created interface.
	 */
	public UMLInterface createInterface(String name) { // TO DO
		name = Converter.converToCamelCase(name);
		if(this.getInterface(name) != null) {
			// TODO thow exception a handle pres vyskakovaci okno!
			return null;
		}
		UMLInterface newInterface = new UMLInterface(name);
		this.interfaces.add(newInterface);

		return newInterface;
	}

	/**
	 * Getter for list of interfaces.
	 * 
	 * @return Returns unmodifiable list of interfaces.
	 */
	public List<UMLInterface> getInterfaces() {
		List<UMLInterface> copyList = List.copyOf(this.interfaces);
		return copyList;
	}
	
	/**
	 * Method compares interfaces in list by name.
	 * 
	 * @param name Contains name of interface we are searching for.
	 * @return If comparison is successful return reference to interface, otherwise
	 *         null.
	 */
	public UMLInterface getInterface(String name) {
		for (UMLInterface in : this.interfaces) {
			if (in.getName().equals(name)) {
				return in;
			}
		}

		return null;
	}
	
	/**
	 * Method deletes interface from Interface list.
	 * 
	 * @param deleteItem Item to be deleted.
	 */
	public void deleteInterface(String name) {
		UMLInterface toBeDeleted = this.getInterface(name);
		if(toBeDeleted != null) {
			this.deleteRelationsWith(toBeDeleted);
			this.interfaces.remove(toBeDeleted);
			toBeDeleted = null;
		}
			
	}

	/**
	 * Method for creating class object and adding it to class list.
	 * 
	 * @param name Contains name of created class.
	 * @return Returns reference to newly created class.
	 */
	public UMLClass createClass(String name) { // TO DO
		name = Converter.converToCamelCase(name);
		if(this.getClass(name) != null) {
			// TODO thow exception a handle pres vyskakovaci okno!
			return null;
		}
		UMLClass newClass = new UMLClass(name);
		this.classes.add(newClass);

		return newClass;
	}

	/**
	 * Getter for list of classes.
	 * 
	 * @return Returns unmodifiable list of classes.
	 */
	public List<UMLClass> getClasses() {
		List<UMLClass> copyList = List.copyOf(this.classes);
		return copyList;
	}

	/**
	 * Method compares classes in list by name.
	 * 
	 * @param name Contains name of class we are searching for.
	 * @return If comparison is successful return reference to class, otherwise
	 *         null.
	 */
	public UMLClass getClass(String name) {
		for (UMLClass cl : this.classes) {
			if (cl.getName().equals(name)) {
				return cl;
			}
		}

		return null;
	}

	/**
	 * Method deletes class from Classes list.
	 * 
	 * @param deleteIitem Item to be deleted.
	 */
	public void deleteClass(String name) {
		UMLClass toBeDeleted = this.getClass(name);
		if(toBeDeleted != null) {
			this.deleteRelationsWith(toBeDeleted);
			this.classes.remove(toBeDeleted);
			toBeDeleted = null;
		}
			
	}
	
	/**
	 * Method deletes all relations that are associated with class/interface that is being deleted.
	 * @param deleted Contains class/interface that was deleted from ClassDiagram.
	 */
	public void deleteRelationsWith(UMLInterface deleted) {
		this.relAggregation.removeIf(rel -> (rel.getLeftClass().equals(deleted) || rel.getRightClass().equals(deleted)));
		this.relAssociation.removeIf(rel -> (rel.getLeftClass().equals(deleted) || rel.getRightClass().equals(deleted)));
		this.relGeneralization.removeIf(rel -> (rel.getLeftClass().equals(deleted) || rel.getRightClass().equals(deleted)));
	}

	/**
	 * Method for creating Generalizations and adding them to Generalizations list.
	 * <br>
	 * Method also guarantees inheritance of methods and relations between
	 * classes/interfaces.
	 * 
	 * @param parent Contains reference to parent Class/Interface.
	 * @param child  Contains reference to child Class/Interface.
	 * @param type   Contains type of Relation.
	 * @return Method returns newly created Aggregation.
	 */
	public RelGeneralization createGeneralization(String parentName, String childName, String type) {
		UMLInterface parent = this.getInterface(parentName) == null ? this.getClass(parentName) : this.getInterface(parentName);
		UMLInterface child = this.getInterface(childName) == null ? this.getClass(childName) : this.getInterface(childName);
		
		RelGeneralization newRelation = new RelGeneralization(parent, child, type);
		this.relGeneralization.add(newRelation);
		child.addInheritedMethods(parent.getAllMethods());
		for (UMLInterface com : parent.getCommunications()) {
			child.addCommunaction(com);
		}

		return newRelation;
	}

	/**
	 * Method deletes Generalization from Generalizations list.
	 * 
	 * @param name Name of item to be removed from list.
	 */
	public void deleteGeneralization(String name) {
		RelGeneralization toBeDeleted = this.getGeneralization(name);
		if (toBeDeleted != null)
			this.relGeneralization.remove(toBeDeleted);
	}

	/**
	 * Method for creating Aggregations and adding them to Aggregations list.
	 * 
	 * @param parent Contains reference to Class/Interface of relation where
	 *               composition/aggregation is.
	 * @param child  Contains reference to Class/Interface of relation.
	 * @param type   Contains type of Relation.
	 * @return Method returns newly created Aggregation.
	 */
	public RelAggregation createAggregation(String parentName, String childName, String type) {
		UMLInterface parent = this.getInterface(parentName) == null ? this.getClass(parentName) : this.getInterface(parentName);
		UMLInterface child = this.getInterface(childName) == null ? this.getClass(childName) : this.getInterface(childName);
		
		RelAggregation newRelation = new RelAggregation(parent, child, type);
		this.relAggregation.add(newRelation);
		parent.addCommunaction(child);
		child.addCommunaction(parent);

		return newRelation;
	}
	
//	public RelAggregation createAggregation(UMLInterface parent, UMLInterface child, String type) {
//		RelAggregation newRelation = new RelAggregation(parent, child, type);
//		this.relAggregation.add(newRelation);
//		parent.addCommunaction(child);
//		child.addCommunaction(parent);
//
//		return newRelation;
//	}

	/**
	 * Method deletes Aggregation from Aggregations list.
	 * 
	 * @param name Name of item to be removed from list.s
	 */
	public void deleteAggregation(String name) {
		RelAggregation toBeDeleted = this.getAggregation(name);
		if (toBeDeleted != null)
			this.relAggregation.remove(toBeDeleted);
	}

	/**
	 * Method for creating Associations and adding them to Associations list.
	 * 
	 * @param parent Contains reference to left Class/Interface of relation.
	 * @param child  Contains reference to right Class/Interface of relation.
	 * @param type   Contains type of Relation.
	 * @return Method returns newly created Association.
	 */
	public RelAssociation createAssociation(String parentName, String childName, String type) {
		UMLInterface parent = this.getInterface(parentName) == null ? this.getClass(parentName) : this.getInterface(parentName);
		UMLInterface child = this.getInterface(childName) == null ? this.getClass(childName) : this.getInterface(childName);
		
		RelAssociation newRelation = new RelAssociation(parent, child, type);
		this.relAssociation.add(newRelation);
		parent.addCommunaction(child);
		child.addCommunaction(parent);

		return newRelation;
	}

	/**
	 * Method deletes Association from Associations list.
	 * 
	 * @param name Name of item to be removed from list.
	 */
	public void deleteAssociation(String name) {
		RelAssociation toBeDeleted = this.getAssociation(name);
		if (toBeDeleted != null)
			this.relAssociation.remove(toBeDeleted);
	}

	/**
	 * Getter for Aggregations list.
	 * 
	 * @return Method returns unmodifiable list of Aggregations.
	 */
	public List<RelAggregation> getAggregations() {
		List<RelAggregation> copyList = List.copyOf(this.relAggregation);
		return copyList;
	}
	
	public RelAggregation getAggregation(String name) {
		for(RelAggregation rel : this.getAggregations()) {
			if(rel.getName().equals(name)) {
				return rel;
			}
		}
		
		return null;
	}

	/**
	 * Getter for Generalizations list.
	 * 
	 * @return Method returns unmodifiable list of Generalizations.
	 */
	public List<RelGeneralization> getGeneralizations() {
		List<RelGeneralization> copyList = List.copyOf(this.relGeneralization);
		return copyList;
	}
	
	public RelGeneralization getGeneralization(String name) {
		for(RelGeneralization rel : this.getGeneralizations()) {
			if(rel.getName().equals(name)) {
				return rel;
			}
		}
		
		return null;
	}

	/**
	 * Getter for Associations list.
	 * 
	 * @return Method returns unmodifiable list of Associations.
	 */
	public List<RelAssociation> getAssociations() {
		List<RelAssociation> copyList = List.copyOf(this.relAssociation);
		return copyList;
	}
	
	public RelAssociation getAssociation(String name) {
		for(RelAssociation rel : this.getAssociations()) {
			if(rel.getName().equals(name)) {
				return rel;
			}
		}
		
		return null;
	}

	/**
	 * Method creates new sequence diagram and adds it to the list.
	 * 
	 * @param name Name of new sequence diagram.
	 * @return Returns created diagram.
	 */
	public SequenceDiagram createSequenceDiagram(String name) {
		SequenceDiagram newDiagram = new SequenceDiagram(name);
		this.sequenceDiagrams.add(newDiagram);

		return newDiagram;
	}

	public List<SequenceDiagram> getSequenceDiagrams() {
		List<SequenceDiagram> copy = List.copyOf(this.sequenceDiagrams);

		return copy;
	}
	
	public SequenceDiagram getSequenceDiagram(String name) {
		for(SequenceDiagram seq : this.sequenceDiagrams) {
			if(seq.getName().equals(name)) {
				return seq;
			}
		}
		return null;
	}
	
	public void setIsSaved(boolean value) {
		this.isSaved = value;
	}
	
	public boolean getIsSaved() {
		return this.isSaved;
	}

}
