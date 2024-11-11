package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;

import model.NonPlayableCharacter;
/**
 * This class is a third level of abstraction to decorate an NPC component in a more specific way.
 * Decorated Object is represented in four possible direction of movement during its animations. 
 * That is, in this way it has four type of footage.
 */
public class DecoratedNpcTetra extends DecoratedNonPlayableCharacter {
	/**
	 * Predefined footage cardinality
	 */
	private static Integer numberOfForms = 3;
	/**
	 * First footage array
	 */
	private BufferedImage[] imageUp = new BufferedImage[numberOfForms];
	/**
	 * Second footage array
	 */
	private BufferedImage[] imageDown = new BufferedImage[numberOfForms];
	/**
	 * Third footage array
	 */
	private BufferedImage[] imageLeft = new BufferedImage[numberOfForms];
	/**
	 * Forth footage array
	 */
	private BufferedImage[] imageRight = new BufferedImage[numberOfForms];
	/**
	 * Constructor
	 * @param npc component to decorate
	 * @param path path for images loading
	 */
	public DecoratedNpcTetra(NonPlayableCharacter npc, String path) {
		super(npc, path);

		try {
			
			for(int i=0 ; i<numberOfForms;i++) {
				imageUp[i] = ImageIO.read(getClass().getResourceAsStream(path+"Up"+i+sfx));
				imageDown[i] = ImageIO.read(getClass().getResourceAsStream(path+"Down"+i+sfx));
				imageLeft[i] = ImageIO.read(getClass().getResourceAsStream(path+"Left"+i+sfx));
				imageRight[i] = ImageIO.read(getClass().getResourceAsStream(path+"Right"+i+sfx));
			}
			
				
		} catch ( IOException e ) {
			e.printStackTrace();
		}
			
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {

				int[] actualForm = new int[] {0,1,0,2};
				int shoots = 3;  // final index
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
		
			case UP : return imageUp[actualPointer];
			
			case DOWN: return imageDown[actualPointer];

			case LEFT :return imageLeft[actualPointer];

			case RIGHT :return imageRight[actualPointer];

			default : return imageDown[actualPointer];
			}
	}
	
	@Override
	public BufferedImage getDeathImage() {
		return getImage();
	}

}
