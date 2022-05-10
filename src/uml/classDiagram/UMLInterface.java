package uml.classDiagram;

import java.util.ArrayList;
import java.util.List;
import uml.pos.Position;
import workers.Converter;

/**
* UMLInterface class represents interface
* in class diagrams.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class UMLInterface extends UMLClassifier {
	private List<UMLOperation> methods = new ArrayList<UMLOperation>();
	private Position position = new Position(0, 0);
	private List<UMLOperation> inheritedMethods = new ArrayList<UMLOperation>(); // TODO 
	private List<UMLInterface> comunicatesWith = new ArrayList<UMLInterface>(); // TODO 
	private boolean isInconsistent;
	
	// Constructors
	/**
	 * Constructor for interface object. Calls constructor of super class UMLClassifier.
	 * @param name Contains name of created interface.
	 */
	public UMLInterface(String name) {
		super(name);
		this.isInconsistent = false;
	}
	
	public Object clone() throws CloneNotSupportedException {
	      return (UMLInterface)super.clone();
	}
		
	// Operation methods
	public void setCommunicatesWith(List<UMLInterface> com) {
		this.comunicatesWith = com;
	}
	
	public void setOperations(List<UMLOperation> operations, List<UMLOperation> inherited) {
		this.methods = operations;
		this.inheritedMethods = inherited;
	}
	
	/**
	 * Method for adding operation to operations list.
	 * @param operation Contains reference to operation object.
	 * @return Returns true if operation was added to list.
	 */
	public boolean addOperation(UMLOperation operation) {
		return this.methods.add(operation);
	}
		
	/**
	 * Getter for operation position.
	 * @param operation Contains reference to operation object.
	 * @return Returns index representing position of operation in interface/class, -1 if it didn't succeed.
	 */
	public int getOperationPosition(UMLOperation operation) {
		return this.methods.indexOf(operation);
	}
	
	/**
	 * Method for moving operation to certain position inside interface/operation.
	 * @param operation Contains reference to operation object.
	 * @param pos Contains position we want to move operation to.
	 * @return Return number of position if it was successful, -1 if not.
	 */
	public int moveOperationAtPosition(UMLOperation operation, int pos) {
		if(this.methods.contains(operation)) {
			this.methods.remove(operation);
			this.methods.add(pos, operation);
			
			return pos;
		}
		
		return -1;
	}
	
	/**
	 * Getter for operations list.
	 * @return Returns reference to unmodifiable list of operations.
	 */
	public List<UMLOperation> getOperations(){
		List<UMLOperation> copy = List.copyOf(this.methods);
		
		return copy;
	}
	
	// Position methods
	
	/**
	 * Getter for position of interface/class in class diagram.
	 * @return Returns reference to position object holding (x, y) coordinates.
	 */
	public Position getPosition() {
		return this.position;
	}
	
	/**
	 * Sets (x, y) coordinates of interface/class in class diagram.
	 * @param x Contains number representing x coordinate.
	 * @param y Contains number representing y coordinate.
	 */
	public void setPosition(int x, int y) {
		this.position.setX(x);
		this.position.setY(y);
	}
	
	public void addInheritedMethods (List<UMLOperation> inheritedMethods) {
//		this.inheritedMethods = inheritedMethods;
		this.inheritedMethods.addAll(inheritedMethods);
	}
	
	public void removeInheritedMethod() { // TODO
		
	}
	
	public List<UMLOperation> getIneritedMethods(){
		List<UMLOperation> copy = List.copyOf(this.inheritedMethods);
		
		return copy;
	}
	
	public List<UMLOperation> getAllMethods(){
		List<UMLOperation> allMethods = new ArrayList<UMLOperation>();
		allMethods.addAll(this.methods);
		if(!this.inheritedMethods.isEmpty()) {
			allMethods.addAll(this.inheritedMethods);
		}
		
		return List.copyOf(allMethods);
	}
	
	public void addCommunaction(UMLInterface newCommunication) {
		if(!this.comunicatesWith.contains(newCommunication)) {
			this.comunicatesWith.add(newCommunication);
		}
	}
	
	public void removeCommunication() { // TODO
		
	}
	
	public List<UMLInterface> getCommunications(){
		return List.copyOf(this.comunicatesWith);
	}
	
	public void removeAllOperations() {
		this.methods.clear();
	}
	
	public void removeAllInherited() {
		this.inheritedMethods.clear();
	}
	
	public void removeAllCommuniactions() {
		this.comunicatesWith.clear();
	}
	
	public void clean() {
		this.removeAllCommuniactions();
		this.removeAllInherited();
		this.removeAllOperations();
	}
	
	public void copy(UMLInterface edited) {
		this.setPosition(edited.getPosition().getX(), edited.getPosition().getY());
		this.methods.addAll(edited.methods);
		this.inheritedMethods.addAll(edited.inheritedMethods);
		this.comunicatesWith.addAll(edited.comunicatesWith);
	}
	
	public void setIsInconsistent(boolean value) {
		this.isInconsistent = value;
	}
	
	public boolean getIsInconsistent() {
		return this.isInconsistent;
	}
}
