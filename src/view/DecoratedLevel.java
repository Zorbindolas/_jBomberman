package view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import model.LevelConcrete;
/**
 * Decorated version of a Level in which view elements are wrapped.
 */
public class DecoratedLevel extends DecoratorLevel implements FootageFuncs {
	/**
	 * Color of the game dock
	 */
	private Color dockColor;
	/**
	 * Standard color of original Bomberman dock
	 */
	private static final Color brownColor = new Color(144,16,0);
	/**
	 * Icon used for attempts
	 */
	private BufferedImage attemptsIcon;
	/**
	 * Icon used for lives
	 */
	private BufferedImage livesIcon;
	/**
	 * Current timer icon
	 */
	private BufferedImage actualTimerIcon;
	/**
	 * Current score icon
	 */
	private BufferedImage actualScoreIcon;
	/**
	 * Timer footage cardinality
	 */
	private static final int timerNumberForms = 8;
	/**
	 * path for timer images
	 */
	private static final String timerPath = "/iconsClassical/";
	/**
	 * iterator for timer animation
	 */
	private Iterator<BufferedImage> iterFormTimer;
	/**
	 * timer images
	 */
	private List<BufferedImage> timerImages;
	/**
	 * Space reserved for score digits
	 */
	private static final int scoreNumberForms = 11;
	/**
	 * path for score images
	 */
	private static final String scorePath = "/iconsClassical/";
	/**
	 * iterator for score animation
	 */
	private Iterator<BufferedImage> iterFormScore;
	/**
	 * score images
	 */
	private List<BufferedImage> scoreImages;
	/**
	 * Constructor to decorate a Level
	 * @param component Level component to decorate
	 * @param arches related arches for this level
	 * @param iconsPath attempts and lives images path
	 * @param dockColor chosen color for the dock
	 */
	public DecoratedLevel(
			LevelConcrete component, Arches arches, String iconsPath, Color dockColor) {
		super(component, arches);
		
		try {
			
			attemptsIcon = ImageIO.read(getClass().getResourceAsStream(iconsPath+"attemptsIcon"+".png"));
			livesIcon = ImageIO.read(getClass().getResourceAsStream(iconsPath+"livesIcon"+".png"));
			
			timerImages = new ArrayList<>();
			scoreImages = new ArrayList<>();
			
			for(int i=0 ; i<timerNumberForms;i++) {
				timerImages.add(ImageIO.read(getClass().getResourceAsStream(timerPath+"timer"+i+sfx)));
			}
			for(int i=0 ; i<scoreNumberForms;i++) {
				scoreImages.add(ImageIO.read(getClass().getResourceAsStream(scorePath+"score"+i+sfx)));
			}
			
			resetFootage();
			incFootage();
			
			resetFootageScore();
			incFootageScore();
			
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
		this.dockColor = dockColor;
		
	}
	/**
	 * Getter of attempts icon
	 * @return this attempts icon
	 */
	public BufferedImage getAttemptsIcon() {return attemptsIcon;}
	/**
	 * Getter of lives icon
	 * @return this lives icon
	 */
	public BufferedImage getLivesIcon() {return livesIcon;}
	/**
	 * Getter of dock Color
	 * @return this dock Color
	 */
	public Color getDockColor() {return dockColor;}
	/**
	 * Getter of standard dock Color
	 * @return the standard dock Color
	 */
	public Color getColorBrown() {return brownColor;}
	/**
	 * Set the next timer's sprite as the current one if present
	 */
	@Override
	public void incFootage() {
		if(!iterFormTimer.hasNext()) {
			resetFootage();
		}
		actualTimerIcon = iterFormTimer.next();
	}
	/**
	 * Reset the timer's animation footage from the beginning
	 */
	@Override
	public void resetFootage() {
		iterFormTimer = timerImages.iterator();
	}
	/**
	 * Getter of current timer's sprite image
	 */
	@Override
	public BufferedImage getImage() {
		return actualTimerIcon;
	}
	/**
	 * Set the next score's sprite as the current one if present
	 */
	public void incFootageScore() {
		if(!iterFormScore.hasNext()) {
			resetFootageScore();
		}
		actualScoreIcon = iterFormScore.next();
	}
	/**
	 * Reset the score's animation footage from the beginning
	 */
	public void resetFootageScore() {
		iterFormScore = scoreImages.iterator();
	}
	/**
	 * Getter of current score's sprite image
	 * @return its actualScoreIcon
	 */
	public BufferedImage getImageScore() {
		return actualScoreIcon;
	}
	

}
