package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import model.NonPlayableCharacter;
/**
 * This class is a forth decoration step for a Decorated Object that is represented
 * in two possible direction of movement during its animations. 
 * That is, in this way it has two type of footage.
 */
public class DecoratedNpcDuo extends DecoratedNonPlayableCharacterWithDeathImage {
	/**
	 * Footage cardinality.
	 */
	private static Integer numberOfForms = 3;
	/**
	 * First footage array
	 */
	private BufferedImage[] imageOne = new BufferedImage[numberOfForms];
	/**
	 * Second footage array
	 */
	private BufferedImage[] imageTwo = new BufferedImage[numberOfForms];
	/**
	 * Constructor
	 * @param npc component to decorate
	 * @param path path for images loading
	 */
	public DecoratedNpcDuo(NonPlayableCharacter npc, String path) {
		super(npc, path);

		try {
			
			for(int i=0 ; i<numberOfForms;i++) {
				imageOne[i] = ImageIO.read(getClass().getResourceAsStream(path+"one"+i+sfx));
				imageTwo[i] = ImageIO.read(getClass().getResourceAsStream(path+"two"+i+sfx));
			}
			
		} catch ( IOException e ) {
			e.printStackTrace();
		}
			
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {

				int[] actualForm = new int[] {0,1,2};
				int shoots = 2;  // final index
				int puntatore;  // initialized to 0
				
				// default constructor
				
				@Override
				public boolean hasNext() {
					return true;
				}

				@Override
				public Integer next() {
					if(puntatore>shoots) puntatore = 0;
					return actualForm[puntatore++];
				}
				
			};
	}
	
	@Override
	public BufferedImage getImage() {
		
		switch(getDir()) {
		
			case UP,DOWN : return imageOne[actualPointer];

			case LEFT,RIGHT :return imageTwo[actualPointer];

			default : return imageOne[actualPointer];
			}
	}

}