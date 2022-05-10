package uml.classDiagram;

import java.util.List;

import workers.Converter;

/**
* UMLOperation class represents operation
* of class/interface in class diagrams.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class UMLOperation extends UMLAttribute {

	// Attributes
	private List<UMLAttribute> arguments = new java.util.ArrayList<UMLAttribute>();
	
	// Constructors
	/**
	 * Constructor for operation object.
	 * @param name Contains name of operation.
	 * @param type Contains data type of operation.
	 * @param visibility Contains visibility of operation.
	 */
	public UMLOperation(String name, UMLClassifier type, UMLClassifier visibility) {
		super(name, type, visibility);
	}

	// Methods
	/**
	 * Method creates operation and and adds its attributes to it.
	 * @param name Contains name of operation.
	 * @param type Contains data type of operation.
	 * @param visibility Contains visibility of operation.
	 * @param args Contains variable number of attributes of operation.
	 * @return Returns reference to newly created operation.
	 */
	public static UMLOperation createOperation(java.lang.String name, UMLClassifier type, UMLClassifier visibility, UMLAttribute... args) {
		name = Converter.converToCamelCase(name);
		UMLOperation result = new UMLOperation(name, type, visibility);
		for (UMLAttribute umlAttribute : args) {
			result.arguments.add(umlAttribute);
		}
		
		return result;
		
	}
	
	/**
	 * Method for adding attribute to operation arguments list.
	 * @param arg Contains added attribute.
	 * @return Returns true if attribute was added to list.
	 */
	public boolean addArgument(UMLAttribute arg) {
		return this.arguments.add(arg);
	}
	
	/**
	 * Deletes argument from arguments list by name.
	 * 
	 * @param name Contains name of argument to be deleted.
	 */
	public void deleteArgument(String name) {
		this.arguments.removeIf(attr -> (attr.getName().equals(name)));
	}
	
	/**
	 * Getter for arguments list.
	 * @return Returns unmodifiable list of arguments of operation.
	 */
	public List<UMLAttribute> getArguments(){
		List<UMLAttribute> copy = List.copyOf(this.arguments);
		
		return copy;
	}
	
	/**
	 * Gets reference to attribute by its name.
	 * 
	 * @param name Contains name of attribute.
	 * @return Returns reference to attribute, if it was found.
	 */
	public UMLAttribute getArgument(String name) {
		for(UMLAttribute attr : this.arguments) {
			if(attr.getName().equals(name)) {
				return attr;
			}
		}
		
		return null;
	}
	
	/**
	 * Override of toString method. Formats informations about operation ready for output.
	 * @return Returns formated output ready to be inserted inside class/interface in class diagram.
	 */
	@Override
	public String toString(){
		//String result = this.getVisibility() + " " + this.getName() + "("+ this.getArguments() + "): " + this.getType();
		int cnt = this.getArguments().size() - 1;
		String result = this.getVisibility() + " " + this.getName() + "(";
		
		for(UMLAttribute at : this.getArguments()) {
			result = result + at;
			if(cnt != 0) {
				result = result + ", ";
			}
			cnt--;
		}
		result = result + "): " + this.getType();
		
		return result;
	}
}
