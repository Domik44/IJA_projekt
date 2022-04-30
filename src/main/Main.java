package main;

import workers.Converter;
import workers.Reader;

import java.util.UUID;

import gui.GUIMain;
import uml.classDiagram.ClassDiagram;
import uml.classDiagram.UMLAttribute;
import uml.classDiagram.UMLClass;
import uml.classDiagram.UMLInterface;
import uml.classDiagram.UMLOperation;
import uml.pos.Position;
import uml.relations.RelAggregation;
import uml.relations.RelAssociation;
import uml.relations.RelGeneralization;
import uml.sequenceDiagram.SequenceDiagram;
import uml.sequenceDiagram.UMLMessage;
import uml.sequenceDiagram.UMLParticipant;
import workers.Writer;

/**
* Main is the main body of application. It calls all 
* other parts of application. 
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/
public class Main {

	/**
	 * Main function of application.
	 *
	 * @param args Input arguments.
	 */
	public static void main(String[] args) {
//		System.out.println("Working directory = " + System.getProperty("user.dir"));
		GUIMain gui = new GUIMain();
		gui.Start();
		// TODO -> vratit spusteni gui
		// TODO -> vratit zpatky mazani v pom.xml ze zalohy (v others)
//		ClassDiagram classDiagram = new ClassDiagram("diagram");
//		Reader.startReading(classDiagram);
	
		
//		UMLClass newClass = classDiagram.createClass("Nakladni automobil");
//		newClass.addAttribute(new UMLAttribute("znakca", classDiagram.classifierForName("int"), classDiagram.classifierForName("+")));

//		classDiagram.deleteClass(newClass.getName());
		
//		RelGeneralization rel = classDiagram.createGeneralization("OperaceVozidlo", newClass.getName(), "Generalization");
//		rel.addPosition(new Position(10, 10));
//		
//		classDiagram.createAssociation("Auto", newClass.getName(), "Association");
		
//		classDiagram.deleteGeneralization(rel.getName());	
	
		
//		SequenceDiagram seq = classDiagram.getSequenceDiagram("SequenceDiagram0");
//		
//		for(UMLMessage mes : seq.getMessages()) {
//			System.out.println(mes.getStartObject().getName());
//		}
		
//		UMLParticipant par = seq.getParticipant("Motorka: Motorka:1");
//		for(UMLOperation mes : seq.getAvailableMessages(par.getName())) {
//			if(mes.getName().equals("vypisKM")) {
//				UMLMessage newMes = seq.createMessage(mes.getName(), "asddf");
//				System.out.println(newMes.getName()+"()");
//			}
////			System.out.println(mes.getName());
//		}
//		seq.createParticipant("Nakladni Automobil:1", newClass);
//		for(UMLParticipant op : seq.getAvailableParticipents("Auto: Auto:1")) {
//			System.out.println(op.getName());
//		}
		
		
//		Writer.startWriting(classDiagram);

	} 
}

