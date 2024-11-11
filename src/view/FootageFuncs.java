package view;

import java.awt.image.BufferedImage;
/**
 * FootageFuncs type has methods to manage its animation ( or footage ).
 */
public interface FootageFuncs {
	/**
	 * Standard extension for image of this application.
	 */
	String sfx = ".png"; // suffix
	/**
	 * Set the next sprite as the current one if present
	 */
	void incFootage();
	/**
	 * Reset the animation footage from the beginning
	 */
	void resetFootage();
	/**
	 * Getter of current sprite image
	 * @return the current image of the animation
	 */
	BufferedImage getImage();
	
}
