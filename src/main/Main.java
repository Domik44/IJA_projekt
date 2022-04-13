package main;

import gui.GUIMain;

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
		GUIMain gui = new GUIMain();
		gui.Start();
	}
}

