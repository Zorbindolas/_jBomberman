package view;
import model.*;
/**
 * Decorated Bomb for BombStandard and BombFast
 */
public class DecoratedBomb extends DecoratorBomb {
	/**
	 * Paths from which load bomb images
	 */
	private static final String[] path = new String[] {
		"/bombStandard/bomb0", 
		"/bombStandard/bomb1",
		"/bombStandard/bomb2",
		"/bombStandard/bomb3"
	};
	/**
	 * Constructor
	 * @param c this bomb component
	 */
	public DecoratedBomb(Bomb c) {
		super(c, path);
	}

}
