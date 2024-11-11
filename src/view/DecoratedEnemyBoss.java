package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import model.Hound;
import model.Boss;
import model.Dir;
import model.EnemyBoss;
/**
 * Decorated version of an EnemyBoss
 */
public class DecoratedEnemyBoss extends DecoratedNonPlayableCharacterWithDeathImage 
								implements Boss, Hound, MyJBombermanFormat {
	/**
	 * Footages cardinality
	 */
	private final Integer numberOfForms = 2;
	/**
	 * Footage for Rest Behavior
	 */
	private BufferedImage[] footageRest = new BufferedImage[numberOfForms];
	/**
	 * Footage for Walk Behavior
	 */
	private BufferedImage[] footageWalk = new BufferedImage[numberOfForms];
	/**
	 * Footage for Action Behavior
	 */
	private BufferedImage[] footageAction= new BufferedImage[numberOfForms];
	/**
	 * Constructor
	 * @param eb EnemyBoss component to decorate
	 * @param path images paths
	 */
	public DecoratedEnemyBoss(EnemyBoss eb, String path) {
		super(eb, path);

			try {
				
				for(int i=0 ; i<numberOfForms;i++) {
					footageRest[i] = ImageIO.read(getClass().getResourceAsStream(path+"rest"+i+sfx));
					footageWalk[i] = ImageIO.read(getClass().getResourceAsStream(path+"walk"+i+sfx));
					footageAction[i] = ImageIO.read(getClass().getResourceAsStream(path+"action"+i+sfx));
				}
					
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			

	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {

				int[] actualForm = new int[] {0,0,1,1,1,1};
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
		switch(this.getBehaviour()) {
			case REST -> {return footageRest[actualPointer];}
			case WALK -> {return footageWalk[actualPointer];}
			case ACTION -> {return footageAction[actualPointer];}
			default -> {return footageRest[actualPointer];}
			}
	}
	
	@Override
	public void updateDirModders(List<Dir> dirs) {
		((Hound)component).updateDirModders(dirs);
	}

}
