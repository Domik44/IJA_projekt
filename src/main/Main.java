package main;

import uml.*;
import workers.Reader;
import uml.pos.Position;
import uml.relations.*;

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
	 * @param args Input arguments.
	 */
	public static void main(String[] args) {
		// Calling reader
		ClassDiagram diagram = new ClassDiagram("ClassDiagram");
		Reader.startReading(diagram);
		for(UMLClass cl : diagram.getClasses()) {
			Position pos = cl.getPosition();
			pos.setX(11);
			System.out.println("Class: "+cl + " " + cl.getPosition().getX() + " " + cl.getPosition().getY());
			for(UMLAttribute at : cl.getAttributes()) {
				String attr = at.toString(); // tohle je jen pro demostraci funkcnosti toString
				// Takhle si to budes moct ulozit do stringu a pak to jen rovnou hodit na zobrazeni
				System.out.println(attr);
				//System.out.println(at); -> stacilo by takhle
			}
			
			for(UMLOperation op : cl.getOperations()) {
				System.out.println(op);
			}
			System.out.println();
		}
		
		System.out.println();
		
		for(UMLInterface inter : diagram.getInterfaces()) {
			System.out.println("Interface: "+inter);
			for(UMLOperation op : inter.getOperations()) {
				System.out.println(op);
			}
			System.out.println();
		}
		
		System.out.println();
		
		for(RelGeneralization rl : diagram.getGeneralizations()) {
			System.out.println(rl.getType());
			System.out.println(rl.getLeftClass());
			for(UMLInterface cl : rl.getChildren()) {
				System.out.println(cl.getName());
			}
			System.out.println();
		}
		
		for(RelAggregation rl : diagram.getAggregations()) {
			System.out.println(rl.getType());
			System.out.println(rl.getLeftClass());
			System.out.println(rl.getRightClass());
			for(Position pos : rl.getPoints()) {
				System.out.println(pos.getX() + " " + pos.getY());
			}
			System.out.println(rl.getLabel());
			System.out.println(rl.getLeftCardinality());
			System.out.println(rl.getRightCardinality());
			System.out.println();
		}
		
		for(RelAssociation rl : diagram.getAssociations()) {
			System.out.println(rl.getType());
			System.out.println(rl.getLeftClass());
			System.out.println(rl.getRightClass());
			for(Position pos : rl.getPoints()) {
				System.out.println(pos.getX() + " " + pos.getY());
			}
			System.out.println(rl.getLabel());
			System.out.println(rl.getLeftCardinality());
			System.out.println(rl.getRightCardinality());
			if(rl.getAssociationClass() != null)
				System.out.println(rl.getAssociationClass().getName());
			System.out.println();
		}
	}

}
