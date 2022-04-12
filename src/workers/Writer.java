package workers;

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
	 * @param fileName Ouput file.
	 */
	public Writer(String fileName) {
		this.fileName = fileName;
		System.out.println(this.fileName);
	}
}
