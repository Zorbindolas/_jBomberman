package view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.NonPlayableCharacter;
/**
 * This class is a third decoration step for Decorated Object in which death image is predefined
 */
public abstract class DecoratedNonPlayableCharacterWithDeathImage 
extends DecoratedNonPlayableCharacter {
	/**
	 * Predefined death image (for Gravedigger purposes).
	 */
	protected BufferedImage imageDeath;
	/**
	 * Constructor
	 * @param c component to decorate
	 * @param path path for images loading
	 */
	public DecoratedNonPlayableCharacterWithDeathImage(NonPlayableCharacter c, String path) {
		super(c, path);
		try {
			imageDeath = ImageIO.read(getClass().getResourceAsStream(path+"death"+sfx));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Getter of predefined death image.
	 */
	@Override
	public BufferedImage getDeathImage() {
		return imageDeath;
	}

}
