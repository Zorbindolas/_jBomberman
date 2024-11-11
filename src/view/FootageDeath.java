package view;

import java.awt.image.BufferedImage;
/**
 * FootageDeath type has methods to manage death animation.
 */
public interface FootageDeath {
	/**
	 * Set the next death sprite as the current one if present
	 */
	public void incFootageDeath();
	/**
	 * Getter of the current death image
	 * @return current death image
	 */
	BufferedImage getImageDeath();
	
}
