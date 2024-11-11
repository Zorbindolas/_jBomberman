package model;
/**
 * Home-made exception for manage array desiderable length
 */
public class InconsistenArrayException extends Exception {

	/**
	 * Serialization number
	 */
	private static final long serialVersionUID = -628707889019505311L;
	/**
	 * Actual length of the array
	 */
	private int len;
	/**
	 * Desiderable length of this array
	 */
	private int desiderableLen;
	/**
	 * Constructor of this exception
	 * @param len actual length of this array
	 * @param desiderableLen desiderable length of this array
	 */
	public InconsistenArrayException(int len, int desiderableLen) {
		this.len = len;
		this.desiderableLen = desiderableLen;
	}
	
	@Override
	public String toString() {
		return super.toString() + ": array of length["+len+"] must be: "+desiderableLen+" !";
	}
}
