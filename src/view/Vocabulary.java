package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
/**
 * This class is used to convert String objects into images 
 * to be drawn in the view in order to obtain a coherent aesthetic in the video game
 */
public class Vocabulary {
	/**
	 * singleton instance
	 */
	public static Vocabulary instance; 
	/**
	 * Map between characters and their representative images
	 */
	private Map<Character,BufferedImage> vocabulary = new HashMap<>();
	/**
	 * Singleton getInstance method
	 * @return singleton instance of this class
	 */
	public static Vocabulary getInstance() {
		if(instance==null) instance = new Vocabulary();
		return instance;
	}
	/**
	 * Private constructor for Singleton Design Pattern
	 */
	private Vocabulary() {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789bpqtv";

		for(char c : letters.toCharArray()) {
			BufferedImage bi;
			try {
				bi = ImageIO.read(getClass().getResourceAsStream("/graphy/letter"+c+".png"));
				vocabulary.put(c,bi);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	/**
	 * Given a Character, it returns the corresponding image.
	 * @param c Character to convert
	 * @return corresponding image
	 */
	public BufferedImage getIm(char c) {
		return vocabulary.get(c);
	}
	
}
