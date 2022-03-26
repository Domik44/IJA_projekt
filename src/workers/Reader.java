/**
* <h1>Reader</h1>
* This program reads data from input files
* and stores them for further use.
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/

package workers;

import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.Scanner;

import uml.*;

public class Reader {
	private String fileName;
	
	public Reader(String fileName) {
		this.fileName = fileName;
	}
	
	public void setNewFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return "data/"+this.fileName;
	}
	
	public static void startReading(ClassDiagram diagram) {
		Reader reader = new Reader("ClassDiagram.cl");
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
		      }
		      readFile.close();
		 } 
		 catch (FileNotFoundException x) {
		      System.out.println("Error! File not found, shutting down.");
		      System.exit(-1);
		 }
	}
	
	
	public void readClass(Scanner readFile, UMLClass newClass) {
		while (readFile.hasNextLine()) {
			String line = readFile.nextLine();
			line = line.trim();
			
	        String[] lineParsed = line.split(" ", -2);
			if(lineParsed[0].equals("}")) {
				break;
			}
			else if(lineParsed[0].equals("attrib")) {
				UMLAttribute atr = new UMLAttribute(lineParsed[1], UMLClassifier.forName(lineParsed[2]), UMLClassifier.forName(lineParsed[3]));
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
					UMLAttribute atr = new UMLAttribute(splittedArg[0], UMLClassifier.forName(splittedArg[1]));
					argsArray[j] = atr;
					j++;
					i++;
				}
				UMLOperation op = UMLOperation.createOperation(lineParsed[1], UMLClassifier.forName(lineParsed[2]), UMLClassifier.forName(lineParsed[3]), argsArray);
				newClass.addOperation(op);
			}
		}
	}
	
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
		}
	}
	
	// TODO -> dodelat
	public void readRelation(Scanner readFile, String type, ClassDiagram diagram) {
		String label = "";
		String lCard = "", rCard = "";
		UMLInterface lClass = null, rClass = null, aClass = null;
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
				lClass = diagram.getInterface(lineParsed[1]);
				if (lClass == null) {
					lClass = diagram.getClass(lineParsed[1]);
				}
			}
			else if(lineParsed[0].equals("rClass")) {
				rClass = diagram.getInterface(lineParsed[1]);
				if (rClass == null) {
					rClass = diagram.getClass(lineParsed[1]);
				}
			}
			else if(lineParsed[0].equals("label")) {
				label = lineParsed[1];
			}
			else if(lineParsed[0].equals("aClass")) {
				aClass = diagram.getInterface(lineParsed[1]);
				if (aClass == null) {
					aClass = diagram.getClass(lineParsed[1]);
				}
			}
		}

		if (type.equals("Generalization")){
			@SuppressWarnings("unused")
			RelGeneralization rel = diagram.createGeneralization(lClass, rClass, type);
		}
		else if (type.equals("Aggregation")){
			RelAggregation rel = diagram.createAggregation(lClass, rClass, type);
			rel.setCardinality(lCard, rCard);
			rel.setLabel(label);
		}
		else if (type.equals("Association")){
			RelAssociation rel = diagram.createAssociation(lClass, rClass, type);
			rel.setCardinality(lCard, rCard);
			rel.setLabel(label);
			rel.setAssociationClass(aClass);
		}
	}
	
}
