package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import model.Gravedigger;
/**
 * Decorated Gravedigger related to a death NPC event.
 */
public class DecoratedGravediggerByNpc extends DecoratorGravedigger 
implements MyJBombermanFormat {
	/**
	 * Final state of this DecoratorGravedigger
	 */
	private final static int finalState = 16;
	/**
	 * State in which happens the release of a DecoratedGravediggerScore
	 */
	private final static int inversionState = 15;
	/**
	 * Last image from NPC before its death
	 */
	private BufferedImage deathImage;
	/**
	 * Whitened deathImage version for flashing effects
	 */
	private BufferedImage flashingImage;
	/**
	 * Images of explosion after deathImage flashing
	 */
	private List<BufferedImage> sublimations;
	/**
	 * True if this gravedigger has dispensed its DecoratedGravediggerScore
	 */
	private boolean givenScore;
	/**
	 * Coordinate of this decorated gravedigger
	 */
	private int hGrave,wGrave;
	/**
	 * Constructor
	 * @param component Gravedigger to decorate
	 * @param lastImage referred NPC's death Image
	 * @param hGrave related NPC's Height
	 * @param wGrave related NPC's Width
	 */
	public DecoratedGravediggerByNpc(
			Gravedigger component, BufferedImage lastImage,
			int hGrave, int wGrave) {
		super(component);
		this.deathImage = lastImage;
		this.flashingImage = ColorTwister.whitener(lastImage);
		sublimations = new ArrayList<>();
		for(int i=0; i<3; i++) {
			try {
				sublimations.add(ImageIO.read(getClass()
						.getResourceAsStream("/explosion/destruction"+i+".png"))
						);
			} catch (IOException e) {e.printStackTrace();}
		}
		this.hGrave = hGrave;
		this.wGrave = wGrave;
	}
	/**
	 * Getter of Height
	 * @return this height
	 */
	public int getHGrave() {return hGrave;}
	/**
	 * Getter of Width
	 * @return this width
	 */
	public int getWGrave() {return wGrave;}
	/**
	 * Getter of the image related to animation
	 * @return correct image for animation
	 */
	public BufferedImage getAnimatedImage() {
		if(actualState==13) return sublimations.get(0);
		if(actualState==14) return sublimations.get(1);
		if(actualState==15) return sublimations.get(2);
		if(boolState) {
			return deathImage;
		} else {
			return flashingImage;
		}
	}
	@Override
	public boolean endFootage() {
		return actualState>=finalState;
	}
	/**
	 * This method disposes the related DecoratedGravediggerScore from this
	 * into the input set.
	 * @param gravediggers set in which insert another DecoratorGravedigger
	 */
	public void createScore(Set<Gravedigger> gravediggers) {
		if(actualState>=inversionState && !givenScore) {
			givenScore = true;
			gravediggers.add(
					new DecoratedGravediggerScore(
							Gravedigger.setTomb(
									getScore(),
									getYPanel()+UNIT_SMALL,
									getXPanel()-(UNIT_SMALL/2)
									)
							));
		}
	}
	
	
	
}
