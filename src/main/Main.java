package main;

import workers.Reader;

import gui.GUIMain;
import uml.ClassDiagram;
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
		//System.out.println("Working directory = " + System.getProperty("user.dir"));
		//GUIMain gui = new GUIMain();
		//gui.Start();
		// TODO -> vratit spusteni gui
		// TODO -> vratit zpatky mazani v pom.xml ze zalohy (v others)
		ClassDiagram classDiagram = new ClassDiagram("diagram");
		Reader.startReading(classDiagram);
		Writer.startWriting(classDiagram);
	}
}

