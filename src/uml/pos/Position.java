package uml.pos;

/**
* Position class represents position
* of point in class diagram. <br>Holds (x, y) coordinates of point.
* 
*
* @author  Dominik Pop
* @version 1.0
* @since   2022-04-12 
*/
public class Position {
	private int x;
	private int y;
	
	/**
	 * Constructor for position object. Sets x and y coordinate.
	 * @param x Contains x coordinate.
	 * @param y Contains y coordinate.
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Setter for x coordinate.
	 * @param x Contains x coordinate.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Setter for y coordinate.
	 * @param y Contains y coordinate.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Getter for x coordinate.
	 * @return Returns x coordinate.
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Getter for y coordinate.
	 * @return Returns y coordinate.
	 */
	public int getY() {
		return this.y;
	}
}
