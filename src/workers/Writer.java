package workers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import uml.ClassDiagram;
import uml.UMLAttribute;
import uml.UMLClass;
import uml.UMLInterface;
import uml.UMLOperation;
import uml.pos.Position;
import uml.relations.RelAggregation;
import uml.relations.RelAssociation;
import uml.relations.RelGeneralization;

/**
* Writer program writes data to input files
* after diagram is saved.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class Writer {
	private String fileName;
	
	/**
	 * Method for writing newly edited information to output (input) file.
	 * @param fileName Output file.
	 */
	public Writer(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Setter for new fileName of new output file.
	 * @param fileName Input file.
	 */
	public void setNewFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Gets name of output file.
	 * @return Returns name of output file.
	 */
	public String getFileName() {
		String path = System.getProperty("user.dir").concat(fileName);
		// Editing path because of JAR location
		return path.replace("dest", "data\\");
		//return "data/"+this.fileName; // use this for testing in IDE! (same for reader)
	}
	
	public static void startWriting(ClassDiagram classDiagram) {
		Writer writer = new Writer("ClassDiagramNew.cl"); //TODO -> predelat jmeno inputu!
		
		File overWrittenFile = new File(writer.getFileName());
		try {
			FileWriter fileWriter = new FileWriter(overWrittenFile, false);
			fileWriter.write("@startuml\n\n");
			
			// Writing classes
			fileWriter.write(writer.writeClasses(classDiagram));
			// Writing interfaces
			fileWriter.write(writer.writeInterfaces(classDiagram));
			// Writing relations
			fileWriter.write(writer.writeAssociations(classDiagram));
			fileWriter.write(writer.writeAggregations(classDiagram));
			fileWriter.write(writer.writeGeneralizations(classDiagram));
			
			
			fileWriter.write("@enduml");
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Error! File for writing not found, shutting down.");
		    System.exit(-1);
		}
	}
	
	public String writeClasses(ClassDiagram classDiagram) {
		String returnString = new String();
		for(UMLClass processedClass : classDiagram.getClasses()) {
			String temporaryString = "Class " + processedClass.getName() + " {\n  position ";
			temporaryString = temporaryString + processedClass.getPosition().getX() + " " + processedClass.getPosition().getY() + "\n";
			for(UMLAttribute attribute : processedClass.getAttributes()) {
				temporaryString = temporaryString + "  attrib " + attribute.getName() + " " + attribute.getType() + " " + attribute.getVisibility() + "\n";
			}
			for(UMLOperation operation : processedClass.getOperations()) {
				temporaryString = temporaryString + "  oper " + operation.getName() + " " + operation.getType() + " " + operation.getVisibility() + " ";
				for(UMLAttribute attribute : operation.getArguments()) {
					temporaryString = temporaryString + attribute.getName() + ":" + attribute.getType() + " ";
				}
				temporaryString = temporaryString + "\n";
			}
			temporaryString = temporaryString + "}\n\n";
			returnString = returnString + temporaryString;
		}
		
		return returnString;
	}
	
	public String writeInterfaces(ClassDiagram classDiagram) {
		String returnString = new String();
		for(UMLInterface processsedInterface : classDiagram.getInterfaces()) {
			String temporaryString = "Interface " + processsedInterface.getName() + " {\n  position ";
			temporaryString = temporaryString + processsedInterface.getPosition().getX() + " " + processsedInterface.getPosition().getY() + "\n";
			for(UMLOperation operation : processsedInterface.getOperations()) {
				temporaryString = temporaryString + "  oper " + operation.getName() + " " + operation.getType() + " " + operation.getVisibility() + " ";
				for(UMLAttribute attribute : operation.getArguments()) {
					temporaryString = temporaryString + attribute.getName() + ":" + attribute.getType() + " ";
				}
				temporaryString = temporaryString + "\n";
			}
			temporaryString = temporaryString + "}\n\n";
			returnString = returnString + temporaryString;
		}
		
		return returnString;
	}
	
	// TODO
	public String writeAssociations(ClassDiagram classDiagram) {
		String returnString = new String();
		for(RelAssociation processsedRelation : classDiagram.getAssociations()) {
			String temporaryString = "Relation " + processsedRelation.getType() + " {\n";
			for(Position pos : processsedRelation.getPoints()) {
				temporaryString = temporaryString + "  position " + pos.getX() + " " + pos.getY() + "\n";
			}
			temporaryString = temporaryString + "  lClass " + processsedRelation.getLeftClass().getName() + "\n";
			temporaryString = temporaryString + "  rClass " + processsedRelation.getRightClass().getName() + "\n";
			
			if(processsedRelation.getAssociationClass() != null) {
				temporaryString = temporaryString + "  aClass " + processsedRelation.getAssociationClass().getName() + "\n";
			}
			if(!processsedRelation.getLeftCardinality().equals("")) {
				temporaryString = temporaryString + "  lCard " + processsedRelation.getLeftCardinality() + "\n";
			}
			if(!processsedRelation.getRightCardinality().equals("")) {
				temporaryString = temporaryString + "  rCard " + processsedRelation.getRightCardinality() + "\n";
			}
			if(!processsedRelation.getLabel().equals("")) {
				temporaryString = temporaryString + "  label " + processsedRelation.getLabel() + " " + processsedRelation.getLabelPosition().getX() + " " + processsedRelation.getLabelPosition().getY() + "\n";
			}
			temporaryString = temporaryString + "}\n\n";
			returnString = returnString + temporaryString;
		}
		
		return returnString;
	}
	
	public String writeAggregations(ClassDiagram classDiagram) {
		String returnString = new String();
		for(RelAggregation processsedRelation : classDiagram.getAggregations()) {
			String temporaryString = "Relation " + processsedRelation.getType() + " {\n";
			for(Position pos : processsedRelation.getPoints()) {
				temporaryString = temporaryString + "  position " + pos.getX() + " " + pos.getY() + "\n";
			}
			temporaryString = temporaryString + "  lClass " + processsedRelation.getLeftClass().getName() + "\n";
			temporaryString = temporaryString + "  rClass " + processsedRelation.getRightClass().getName() + "\n";
			
			if(!processsedRelation.getLeftCardinality().equals("")) {
				temporaryString = temporaryString + "  lCard " + processsedRelation.getLeftCardinality() + "\n";
			}
			if(!processsedRelation.getRightCardinality().equals("")) {
				temporaryString = temporaryString + "  rCard " + processsedRelation.getRightCardinality() + "\n";
			}
			if(!processsedRelation.getLabel().equals("")) {
				temporaryString = temporaryString + "  label " + processsedRelation.getLabel() + " " + processsedRelation.getLabelPosition().getX() + " " + processsedRelation.getLabelPosition().getY() + "\n";
			}
			temporaryString = temporaryString + "}\n\n";
			returnString = returnString + temporaryString;
		}
		
		return returnString;
	}
	
	public String writeGeneralizations(ClassDiagram classDiagram) {
		String returnString = new String();
		for(RelGeneralization processsedRelation : classDiagram.getGeneralizations()) {
			String temporaryString = "Relation " + processsedRelation.getType() + " {\n";
			for(Position pos : processsedRelation.getPoints()) {
				temporaryString = temporaryString + "  position " + pos.getX() + " " + pos.getY() + "\n";
			}
			temporaryString = temporaryString + "  lClass " + processsedRelation.getLeftClass().getName() + "\n";
			temporaryString = temporaryString + "  rClass " + processsedRelation.getRightClass().getName() + "\n";
			
			temporaryString = temporaryString + "}\n\n";
			returnString = returnString + temporaryString;
		}
		
		return returnString;
	}
}
