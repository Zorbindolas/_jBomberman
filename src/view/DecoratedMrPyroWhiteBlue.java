package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import model.EnemyPyro;
/**
 * Special DecoratedNonPlayableCharacter version for MrPyroWhiteBlue.
 * This enemy is produced in larger quantities so let's make sure that its footage 
 * is loaded only once as it is static.
 */
public class DecoratedMrPyroWhiteBlue extends DecoratedNonPlayableCharacter {
	/**
	 * This boolean is used to make the bufferedImage arrays come
	 * assigned only once in the constructor.
	 */
	private static boolean loaded = true;
	/**
	 * Footage cardinality
	 */
	private static Integer numberOfForms = 4;
	/**
	 * This is the same BufferedImage array for every class Object:
	 * the super type pointer results to be the only change-suitable field 
	 * in each of these.
	 */
	private static BufferedImage[] mono = new BufferedImage[numberOfForms];
	/**
	 * Constructor
	 * @param ep EnemyPyro component to decorate
	 */
	public DecoratedMrPyroWhiteBlue(EnemyPyro ep) {
		super(ep, "/mrPyroWhiteBlue/");
		
		if(loaded) {
			try {
				
				for(int i=0 ; i<numberOfForms;i++) {
					mono[i] = ImageIO.read(getClass().getResourceAsStream(path+"mono"+i+sfx));
				}
				
				loaded = false;
					
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			
		}

	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {

				int puntatore;  // initialized to 0
				
				// default constructor
				
				@Override
				public boolean hasNext() {
					return true;
				}

				@Override
				public Integer next() {
					puntatore++;
					if(puntatore>=numberOfForms) puntatore = 0;
					return puntatore;
				}
				
			};
	}
	
	@Override
	public BufferedImage getImage() {
		return mono[actualPointer];
	}
	
	@Override
	public BufferedImage getDeathImage() {
		return mono[actualPointer];
	}

}
