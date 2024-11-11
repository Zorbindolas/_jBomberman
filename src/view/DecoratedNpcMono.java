package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import model.NonPlayableCharacter;
/**
 * This class is a third decoration step for a Decorated Object that is represented
 * in only one direction of movement during its animations. 
 * That is, in this way it has only one type of footage.
 */
public class DecoratedNpcMono extends DecoratedNonPlayableCharacter {
	/**
	 * Footage cardinality. It can be defined in the constructor.
	 */
	private Integer numberOfForms;
	/**
	 * Footage array
	 */
	private BufferedImage[] image;
	/**
	 * Constructor
	 * @param npc component to decorate
	 * @param path path for images loading
	 * @param numberOfForms footage cardinality
	 */
	public DecoratedNpcMono(
			NonPlayableCharacter npc, String path, int numberOfForms) {
		super(npc, path);
		
		this.numberOfForms = numberOfForms;
		image = new BufferedImage[numberOfForms];
		
		try {
			
			for(int i=0 ; i<numberOfForms;i++) {
				image[i] = ImageIO.read(getClass().getResourceAsStream(path+"mono"+i+sfx));
			}
			
				
		} catch ( IOException e ) {
			e.printStackTrace();
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
		return image[actualPointer];
	}
	
	@Override
	public BufferedImage getDeathImage() {
		return getImage();
	}

}