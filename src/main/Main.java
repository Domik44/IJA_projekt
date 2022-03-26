/**
* <h1>Main</h1>
* This is the main body of application. It calls all 
* other parts of application. 
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-03-23 
*/

package main;

import uml.*;
import workers.Reader;

public class Main {

	public static void main(String[] args) {
		// Calling reader
		ClassDiagram diagram = new ClassDiagram("ClassDiagram");
		Reader.startReading(diagram);
		for(UMLClass cl : diagram.getClasses()) {
			System.out.println("Class: "+cl);
			for(UMLAttribute at : cl.getAttributes()) {
				String attr = at.toString(); // tohle je jen pro demostraci funkcnosti toString
				// Takhle si to budes moct ulozit do stringu a pak to jen rovnou hodit na zobrazeni
				System.out.println(attr);
				//System.out.println(at); -> stacilo by takhle
			}
			
			for(UMLOperation op : cl.getOperations()) {
				System.out.println(op);
			}
		}
		
		System.out.println();
		
		for(UMLInterface inter : diagram.getInterfaces()) {
			System.out.println("Interface: "+inter);
			for(UMLOperation op : inter.getOperations()) {
				System.out.println(op);
			}
		}
		
		System.out.println();
		
		for(UMLRelation rl : diagram.getRelations()) {
			System.out.println(rl.getType());
			System.out.println(rl.getLeftClass());
			System.out.println(rl.getRightClass());
		}
		
		
	}

}
