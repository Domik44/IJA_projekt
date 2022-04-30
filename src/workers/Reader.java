package workers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import uml.*;
import uml.classDiagram.ClassDiagram;
import uml.classDiagram.UMLAttribute;
import uml.classDiagram.UMLClass;
import uml.classDiagram.UMLClassifier;
import uml.classDiagram.UMLInterface;
import uml.classDiagram.UMLOperation;
import uml.relations.RelAggregation;
import uml.relations.RelAssociation;
import uml.relations.RelGeneralization;
import uml.sequenceDiagram.SequenceDiagram;
import uml.sequenceDiagram.UMLActivationBox;
import uml.sequenceDiagram.UMLMessage;
import uml.sequenceDiagram.UMLParticipant;
import uml.pos.*;
import java.util.List;
import java.util.ArrayList;

/**
* Reader class reads data from input files
* and stores them for further use.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class Reader {
	private String fileName;
	
	/**
	 * Constructor for reader.
	 * @param fileName Sets fileName of input file.
	 */
	public Reader(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Setter for new fileName of new input file.
	 * @param fileName Input file.
	 */
	public void setNewFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Gets name of input file.
	 * @return Returns name of input file.
	 */
	public String getFileName() {
		String path = System.getProperty("user.dir").concat(fileName);
		// Editing path because of JAR location
		return path.replace("dest", "data\\");
		//return "data/"+this.fileName;
		//return this.fileName;
	}
	
	/**
	 * Method starts reading informations about class diagram from input file.
	 * @param diagram Contains reference to diagram object, where all information will be stored.
	 */
	public static void startReading(ClassDiagram diagram) {
		Reader reader = new Reader("ClassDiagram.cl");
		int seqCounter = 0;
		try {
		      File inputFile = new File(reader.getFileName());
		      
		      //create the scanner object
		      Scanner readFile = new Scanner(inputFile);

		      while (readFile.hasNextLine()) {
		        String line = readFile.nextLine();
		        line = line.trim();

		        String[] lineParsed = line.split(" ", -2);
		        
		        if(lineParsed[0].equals("Class")) {
		        	String name = lineParsed[1];
		        	UMLClass newClass = diagram.createClass(name);
		        	reader.readClass(readFile, newClass);
		        }
		        else if(lineParsed[0].equals("Interface")) {
		        	String name = lineParsed[1];
		        	UMLInterface newInterface = diagram.createInterface(name);
		        	reader.readInterface(readFile ,newInterface);
		        }
		        else if(lineParsed[0].equals("Relation")) {
		        	String type = lineParsed[1];
		        	reader.readRelation(readFile, type, diagram);
		        }
		        else if(lineParsed[0].equals("@startSequence")) {
		        	String seqName = "SequenceDiagram" + Integer.toString(seqCounter);
		        	SequenceDiagram seqDiagram = diagram.createSequenceDiagram(seqName);
		        	seqCounter++;
		        	reader.readSequenceDiagram(readFile, seqDiagram, diagram);
		        	
		        }
		      }
		      readFile.close();
		 } 
		 catch (FileNotFoundException x) {
		      System.out.println("Error! File for reading not found, shutting down.");
		      System.exit(-1);
		 }
	}
	
	/**
	 * Method for reading information about class from input file.
	 * @param readFile Input file.
	 * @param newClass Object representing new class.
	 */
	public void readClass(Scanner readFile, UMLClass newClass) {
		while (readFile.hasNextLine()) {
			String line = readFile.nextLine();
			line = line.trim();
			
	        String[] lineParsed = line.split(" ", -2);
			if(lineParsed[0].equals("}")) {
				break;
			}
			else if(lineParsed[0].equals("attrib")) {
				UMLAttribute atr = new UMLAttribute(Converter.converToCamelCase(lineParsed[1]), UMLClassifier.forName(lineParsed[2]), UMLClassifier.forName(lineParsed[3]));
				newClass.addAttribute(atr);
			}
			else if(lineParsed[0].equals("oper")) {
				int numberStaticParams = 4;
				int numberArgs = lineParsed.length - numberStaticParams;
				UMLAttribute[] argsArray = new UMLAttribute[numberArgs];
				int i = numberStaticParams;
				int j = 0;
				while(i < lineParsed.length) {
					String[] splittedArg = lineParsed[i].split(":", -2);
					UMLAttribute atr = new UMLAttribute(Converter.converToCamelCase(splittedArg[0]), UMLClassifier.forName(splittedArg[1]));
					argsArray[j] = atr;
					j++;
					i++;
				}
				UMLOperation op = UMLOperation.createOperation(lineParsed[1], UMLClassifier.forName(lineParsed[2]), UMLClassifier.forName(lineParsed[3]), argsArray);
				newClass.addOperation(op);
			}
			else if(lineParsed[0].equals("position")) {
				int x = Integer.parseInt(lineParsed[1]);
				int y = Integer.parseInt(lineParsed[2]);
				newClass.setPosition(x, y);
			}
		}
	}
	
	/**
	 * Method for reading information about interface from input file.
	 * @param readFile Input file.
	 * @param newInterface Object representing new interface.
	 */
	public void readInterface(Scanner readFile, UMLInterface newInterface) {
		while (readFile.hasNextLine()) {
			String line = readFile.nextLine();
			line = line.trim();
			
	        String[] lineParsed = line.split(" ", -2);
			if(lineParsed[0].equals("}")) {
				break;
			}
			else if(lineParsed[0].equals("oper")) {
				int numberStaticParams = 4;
				int numberArgs = lineParsed.length - numberStaticParams;
				UMLAttribute[] argsArray = new UMLAttribute[numberArgs];
				int i = numberStaticParams;
				int j = 0;
				while(i < lineParsed.length) {
					String[] splittedArg = lineParsed[i].split(":", -2);
					UMLAttribute atr = new UMLAttribute(splittedArg[0], UMLClassifier.forName(splittedArg[1]));
					argsArray[j] = atr;
					j++;
					i++;
				}
				UMLOperation op = UMLOperation.createOperation(lineParsed[1], UMLClassifier.forName(lineParsed[2]), UMLClassifier.forName(lineParsed[3]), argsArray);
				newInterface.addOperation(op);
			}
			else if(lineParsed[0].equals("position")) {
				int x = Integer.parseInt(lineParsed[1]);
				int y = Integer.parseInt(lineParsed[2]);
				newInterface.setPosition(x, y);
			}
		}
	}
	
	/**
	 * Method for reading information about relation and creating it.
	 * @param readFile Input file.
	 * @param type Contains type of relation.
	 * @param diagram Object representing class diagram.
	 */
	public void readRelation(Scanner readFile, String type, ClassDiagram diagram) {
		String label = "";
		String lCard = "", rCard = "";
//		UMLInterface lClass = null, rClass = null, aClass = null;
		String lClass = ""; 
		String rClass = "";
		String aClass = "";
		List<UMLInterface> childClasses = new ArrayList<UMLInterface>();
		List<Position> listPos = new java.util.ArrayList<Position>();
		int labelX = 0; int labelY = 0;
		
		while (readFile.hasNextLine()) {
			String line = readFile.nextLine();
			line = line.trim();
			
	        String[] lineParsed = line.split(" ", -2);
			if(lineParsed[0].equals("}")) {
				break;
			}
			else if(lineParsed[0].equals("lCard")) {
				lCard = lineParsed[1];
			}
			else if(lineParsed[0].equals("rCard")) {
				rCard = lineParsed[1];
			}
			else if(lineParsed[0].equals("lClass")) {
				lClass = lineParsed[1];
			}
			else if(lineParsed[0].equals("rClass")) {
				rClass = lineParsed[1];
			}
			else if(lineParsed[0].equals("label")) {
				label = lineParsed[1];
				labelX = Integer.parseInt(lineParsed[2]);
				labelY = Integer.parseInt(lineParsed[3]);
			}
			else if(lineParsed[0].equals("aClass")) {
				aClass = lineParsed[1];
			}
			else if(lineParsed[0].equals("position")) {
				int x = Integer.parseInt(lineParsed[1]);
				int y = Integer.parseInt(lineParsed[2]);
				Position pos = new Position(x, y);
				listPos.add(pos);
			}
		}

		if (type.equals("Generalization")){
			@SuppressWarnings("unused")
			RelGeneralization rel = diagram.createGeneralization(lClass, rClass, type);
			rel.changeList(listPos);
		}
		else if (type.equals("Aggregation") || type.equals("Composition")){
			RelAggregation rel = diagram.createAggregation(lClass, rClass, type);
			rel.setCardinality(lCard, rCard);
			rel.setLabel(label);
			rel.setLabelPosition(labelX, labelY);
			rel.changeList(listPos);
		}
		else if (type.equals("Association")){
			RelAssociation rel = diagram.createAssociation(lClass, rClass, type);
			rel.setCardinality(lCard, rCard);
			rel.setLabel(label);
			rel.setLabelPosition(labelX, labelY);
			UMLInterface associationClass = diagram.getInterface(aClass) == null ? diagram.getClass(aClass) : diagram.getInterface(aClass);
			rel.setAssociationClass(associationClass);
			rel.changeList(listPos);
		}
	}
	
	public void readSequenceDiagram(Scanner readFile, SequenceDiagram seqDiagram, ClassDiagram clDiagram) {
		String name = "";
		String type = "";
		while(readFile.hasNextLine()) {
			String line = readFile.nextLine();
			line = line.trim();
			
			String[] lineParsed = line.split(" ", -2);
			if(lineParsed[0].equals("@endSequence")) {
				break;
			}
			else if(lineParsed[0].equals("Participant")) {
				name = lineParsed[1];
				type = lineParsed[2];
				this.readParticipant(readFile, name, type, seqDiagram, clDiagram);
			}
			else if(lineParsed[0].equals("Message")) {
				name = lineParsed[1];
				type = lineParsed[2];
				this.readMessage(readFile, name, type, seqDiagram);
			}
			else if(lineParsed[0].equals("ActivationBox")) {
				this.readActivationBox(readFile, seqDiagram);
			}
		}
	}
	
	public void readParticipant(Scanner readFile, String name, String instaceClass, SequenceDiagram seqDiagram, ClassDiagram clDiagram) {
		UMLInterface instanceOf = clDiagram.getInterface(instaceClass);
		if(instanceOf == null) {
			instanceOf = clDiagram.getClass(instaceClass);
		}
		name = Converter.converToCamelCase(name);
		UMLParticipant newParticipant = seqDiagram.createParticipant(name, instanceOf);

		int x = 0;
		int y = 0;
		while(readFile.hasNextLine()) {
			String line = readFile.nextLine();
			line = line.trim();
			
			String[] lineParsed = line.split(" ", -2);
			if(lineParsed[0].equals("}")) {
				break;
			}
			else if(lineParsed[0].equals("startPos")) {
				x = Integer.parseInt(lineParsed[1]);
				y = Integer.parseInt(lineParsed[2]);
				newParticipant.setStartPosition(x, y);
			}
			else if(lineParsed[0].equals("endPos")) {
				x = Integer.parseInt(lineParsed[1]);
				y = Integer.parseInt(lineParsed[2]);
				newParticipant.setEndPosition(x, y);
			}
			else if(lineParsed[0].equals("lineStart")) {
				x = Integer.parseInt(lineParsed[1]);
				y = Integer.parseInt(lineParsed[2]);
				newParticipant.setLineStartPosition(x, y);
			}
			else if(lineParsed[0].equals("lineEnd")) {
				x = Integer.parseInt(lineParsed[1]);
				y = Integer.parseInt(lineParsed[2]);
				newParticipant.setLineEndPosition(x, y);
			}
		}
	}
	
	public void readMessage(Scanner readFile, String name, String type, SequenceDiagram seqDiagram) {
		UMLMessage newMessage = seqDiagram.createMessage(name, type);
		int x = 0;
		int y = 0;
		while(readFile.hasNextLine()) {
			String line = readFile.nextLine();
			line = line.trim();
			
			String[] lineParsed = line.split(" ", -2);
			if(lineParsed[0].equals("}")) {
				break;
			}
			else if(lineParsed[0].equals("position")) {
				x = Integer.parseInt(lineParsed[1]);
				y = Integer.parseInt(lineParsed[2]);
				Position newPosition = new Position(x, y);
				newMessage.addPosition(newPosition);
			}
			else if(lineParsed[0].equals("namePos")) {
				x = Integer.parseInt(lineParsed[1]);
				y = Integer.parseInt(lineParsed[2]);
				newMessage.setNamePosition(x, y);
			}
			else if(lineParsed[0].equals("startObject")) {
				UMLParticipant startObject = seqDiagram.getParticipant(lineParsed[1]);
				newMessage.setStartObject(startObject);
			}
			else if(lineParsed[0].equals("endObject")) {
				UMLParticipant endObject = seqDiagram.getParticipant(lineParsed[1]);
				newMessage.setEndObject(endObject);
			}
		}
	}
	
	public void readActivationBox (Scanner readFile, SequenceDiagram seqDiagram) {
		UMLActivationBox newActivationBox = seqDiagram.createActivationBox();
		int x = 0;
		int y = 0;
		while(readFile.hasNextLine()) {
			String line = readFile.nextLine();
			line = line.trim();
			
			String[] lineParsed = line.split(" ", -2);
			if(lineParsed[0].equals("}")) {
				break;
			}
			else if(lineParsed[0].equals("startPos")) {
				x = Integer.parseInt(lineParsed[1]);
				y = Integer.parseInt(lineParsed[2]);
				newActivationBox.setStartPosition(x, y);
			}
			else if(lineParsed[0].equals("endPos")) {
				x = Integer.parseInt(lineParsed[1]);
				y = Integer.parseInt(lineParsed[2]);
				newActivationBox.setEndPosition(x, y);
			}
		}
	}
	
}
