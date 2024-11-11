package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import model.*;
/**
 * This class gives its supertype the footages array and lists 
 * and also the way to process these animations.
 */
public class DecoratedPlayerCharacter extends DecoratorPlayerCharacter  {
	/**
	 * Up animation footage as array
	 */
	private BufferedImage[] imageUp;
	/**
	 * Down animation footage as array
	 */
	private BufferedImage[] imageDown;
	/**
	 * Left animation footage as array
	 */
	private BufferedImage[] imageLeft;
	/**
	 * Right animation footage as array
	 */
	private BufferedImage[] imageRight;
	/**
	 * Death animation footage as List
	 */
	private List<BufferedImage> deathImages;
	/**
	 * Win animation footage as List
	 */
	private List<BufferedImage> winImages;
	/**
	 * Footage array cardinality
	 */
	private Integer numberOfForms;
	/**
	 * Pointer for the current array animation position 
	 */
	private int actualForm;
	/**
	 * Iteration for array animation process
	 */
	private Iterator<Integer> iterForms; // 0: stationary, other: walking forms
	/**
	 * Iteration for Lists animations process
	 */
	private Iterator<BufferedImage> iterDeath, iterWin;
	/**
	 * Current death image
	 */
	private BufferedImage actualDeath;
	/**
	 * Current win image
	 */
	private BufferedImage actualWin;
	/**
	 * Character height correction factor used for those sprites 
	 * of non-standard dimensions so that the final drawing of the character is not deformed.
	 */
	private int hCorrector;
	/**
	 * Constructor to decorate a PlayerCharacter
	 * @param component PlayerCharacter to decorate
	 * @param path images path for array footage animations
	 * @param numberOfForms footage array cardinality
	 * @param numberOfFormsOfDeath death footage cardinality
	 * @param numberOfFormsOfWin win footage cardinality
	 * @param hCorrector this player character's height corrector factor
	 */
	public DecoratedPlayerCharacter(PlayerCharacterConcrete component, 
									String path, 
									Integer numberOfForms,
									Integer numberOfFormsOfDeath,
									Integer numberOfFormsOfWin,
									int hCorrector
									) {
		super(component);
		this.numberOfForms = numberOfForms; // error if nOF < 0
		
		imageUp = new BufferedImage[numberOfForms];
		imageDown = new BufferedImage[numberOfForms];
		imageRight = new BufferedImage[numberOfForms];
		imageLeft = new BufferedImage[numberOfForms];
		this.hCorrector = hCorrector;
		resetFootage();
		
		try {
			
			for(int i=0 ; i<numberOfForms;i++) {
				this.imageUp[i] = ImageIO.read(getClass().getResourceAsStream(path+"Up"+i+sfx));
				this.imageDown[i] = ImageIO.read(getClass().getResourceAsStream(path+"Down"+i+sfx));
				this.imageLeft[i] = ImageIO.read(getClass().getResourceAsStream(path+"Left"+i+sfx));
				this.imageRight[i] = ImageIO.read(getClass().getResourceAsStream(path+"Right"+i+sfx));
			}
			
			deathImages = new ArrayList<>();
			for(int i=0 ; i<numberOfFormsOfDeath;i++) {
				deathImages.add(ImageIO.read(getClass().getResourceAsStream(path+"Death"+i+sfx)));
			}
			iterDeath = deathImages.iterator();
			
			
			winImages = new ArrayList<>();
			for(int i=0 ; i<numberOfFormsOfWin; i++) {
				winImages.add(ImageIO.read(getClass().getResourceAsStream(path+"Statue"+i+sfx)));
			}
			iterWin = winImages.iterator();
				
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Getter of number of forms (footage array)
	 * @return this PC's number-of-Forms cardinality
	 */
	public int getNumberOfForms() {return numberOfForms;}
	/**
	 * Getter of Height corrector factor
	 * @return this PC's Height corrector factor
	 */
	public int getHCorrector() {return hCorrector;}
	
	@Override
	public void incFootage() {
		actualForm = iterForms.next();
	}
	
	@Override
	public void resetFootage() {
		iterForms = this.iterator();
		incFootage();
	}
	
	@Override
	public BufferedImage getImage() {

		switch(getLastValidDir()) {
		
			case UP -> { return imageUp[actualForm]; }
			
			case DOWN -> { return imageDown[actualForm]; }

			case LEFT -> { return imageLeft[actualForm]; }

			case RIGHT -> { return imageRight[actualForm]; }

			default -> { return imageUp[actualForm]; }
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
	
	// FootageDeath
	@Override
	public void incFootageDeath() {
		if(iterDeath.hasNext()) 
			actualDeath = iterDeath.next();
	}
	
	@Override
	public BufferedImage getImageDeath() {
		return actualDeath;
	}
	
	// Footage Win
	@Override
	public void incFootageWin() {
		if(iterWin.hasNext()) 
			actualWin = iterWin.next();
	}

	@Override
	public BufferedImage getImageWin() {
		return actualWin;
	}
	
	@Override
	public void resetTemporaryStatus() {
		component.resetTemporaryStatus();
		iterDeath = deathImages.iterator();
		iterWin = winImages.iterator();
	}

}
