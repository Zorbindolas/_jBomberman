package view;

import java.awt.image.BufferedImage;
/**
 * FootageWin type has methods to manage win animation.
 */
public interface FootageWin {
	/**
	 * Set the next win sprite as the current one if present
	 */
	public void incFootageWin();
	/**
	 * Getter of the current win image
	 * @return current win image
	 */
	BufferedImage getImageWin();
	
}
