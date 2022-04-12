package uml;

import java.util.List;
import uml.pos.Position;

/**
* UMLInterface class represents interface
* in class diagrams.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class UMLInterface extends UMLClassifier {
	private List<UMLOperation> methods = new java.util.ArrayList<UMLOperation>();
	//private int[] position = new int[]{0,0};
	private Position position = new Position(0, 0);
	
	// Constructors
	/**
	 * Constructor for interface object. Calls constructor of super class UMLClassifier.
	 * @param name Contains name of created interface.
	 */
	public UMLInterface(String name) {
		super(name);
	}
		
	// Operation methods
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
}
