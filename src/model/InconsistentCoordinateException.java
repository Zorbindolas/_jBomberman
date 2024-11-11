package model;
/**
 * Home-made exception to manage insertion of inconsistent grid coordinates
 */
public class InconsistentCoordinateException extends Exception implements MyGridFormat{

	/**
	 * Serialization number
	 */
	private static final long serialVersionUID = -7586683048390584371L;
	/**
	 * Ordinate
	 */
	private int y;
	/**
	 * Abscissa
	 */
	private int x;
	/**
	 * Exception constructor
	 * @param y ordinate
	 * @param x abscissa
	 */
	public InconsistentCoordinateException(int y, int x) {
		this.y = y;
		this.x = x;
	}
	/**
	 * Check if one or two errors
	 * @return string of errors
	 */
	private String check() {
		
		String stringCheck = "";
		
		int errors = 0;
		
		if(x<0 || x>=COLUMNS) {
			stringCheck += "x = " + x + " ";
			errors++;
		}
		if(y<0 || y>=ROWS) {
			stringCheck += "y = " + y;
			errors++;
		}
		
		if(errors==2) stringCheck +=" are ";
		else stringCheck += " is ";
		
		return stringCheck;
	}
	
	@Override
	public String toString() {
		
		return super.toString()+": "+check()+" inconsistent";
	}
}
