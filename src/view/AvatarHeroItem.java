package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import model.Heroes;
/**
 * This class represents a choosable player character in DynaSlavePlayer and DynaSlavePlayerNested
 * panels.
 */
public class AvatarHeroItem implements Iterable<BufferedImage>, FootageFuncs{
	/**
	 * What type of hero it is. Also, the key.
	 */
	private Heroes hero;
	/**
	 * Short phrase about this AvatarItem hero in the DynaSlavePlayerNested panel.
	 */
	private String coolPhrase;
	
	// Footage
	/**
	 *Closeup icon of this avatar during its selection.
	 */
	private ImageIcon icon;
	/**
	 * Collection of the images of this avatar to show for its footage during its selection.
	 */
	private List<BufferedImage> footage;
	/**
	 * Detailed Avatar Image over its description when selected.
	 */
	private BufferedImage actualStatue;
	/**
	 * Footage iterator that determines actualStatue.
	 */
	private Iterator<BufferedImage> iterForms;
	/**
	 * True if the footage never stops.
	 */
	private boolean infinityLoop;
	/**
	 * Constructor of this AvatarItem
	 * @param hero hero of this avatar (the key)
	 */
	public AvatarHeroItem(Heroes hero) {
		this.hero = hero;
		this.coolPhrase = getCoolPhrase(hero);
		BufferedImage image;
		try {
			// Load icon
			image = ImageIO.read(getClass().getResourceAsStream(
					"/"+getLabelResource(hero)+ "/icon.png"));
			this.icon = new ImageIcon(image);
			
			// Load Footage labeled as "Statue" in the res package
			footage = new ArrayList<>();
			for(int i=0; i<getNumberOfStatues(hero);i++) {
				String root = "/"+getLabelResource(hero)+ "/Statue"+i+".png";
				image = ImageIO.read(getClass().getResourceAsStream(root));
				footage.add(image);
			}
			
		} catch (IOException e) {e.printStackTrace();}
		
		this.infinityLoop = getInfinityLoop(hero);
		
		resetFootage();

	}
	/**
	 * Getter of hero label
	 * @return this hero label (key)
	 */
	public Heroes getHero() {return hero;}
	/**
	 * Getter of this coolPhrase
	 * @return this coolPhrase
	 */
	public String getCoolPhrase() {return coolPhrase;}
	/**
	 * Getter of this icon
	 * @return this icon
	 */
	public ImageIcon getIcon() {return icon;}
	/**
	 * This method is used to pause the avatar's loop.
	 * @return true when it's time to stop the footage.
	 */
	public boolean isFootageDone() {
		if(infinityLoop) return false;
		return ! iterForms.hasNext();
	}
	/**
	 * It restart the footage from the first image in its Collection.
	 */
	public void restartFootageIcon() {
		if(!infinityLoop) {
			resetFootage();
		}
	}

	@Override
	public Iterator<BufferedImage> iterator() {
		return footage.iterator();
	}
	
	@Override
	public void incFootage() {
		
		if(iterForms.hasNext()) {
			actualStatue = iterForms.next();
		} else {
			if(infinityLoop) {
				resetFootage();
				if(footage.size()>1) incFootage();
			}
		}
		
	}

	@Override
	public void resetFootage() {		
		iterForms = this.iterator();		
	}

	@Override
	public BufferedImage getImage() {
		return actualStatue;
	}
	/**
	 * This method returns the value of an hero avatar's sprites defined during development.
	 * @param hero the hero for which you need to know the number of sprites
	 * @return total number for this hero sprites
	 */
	private int getNumberOfStatues(Heroes hero) {
		switch(hero) {
		case WHITE_BOMBERMAN ->{return 5;}
		case MAD_MINER ->{return 10;}
		case MISS_DINAH_MIGHT ->{return 8;}
		case BARON_BOMBAROLO ->{return 4;}
		case MECHA_BOMBERMAN ->{return 8;}
		case RETRO_LADY ->{return 4;}
		case MASKED_MAGICIAN ->{return 4;}
		default ->{return 0;}
		}
	}
	/**
	 * This method returns the value associated to an hero's avatarItem related to its infinity looping
	 * and defined during development.
	 * @param hero the hero for which you need to know the infinity loop value
	 * @return the infinity loop boolean value
	 */
	private boolean getInfinityLoop(Heroes hero) {
		switch(hero) {
		case 
				WHITE_BOMBERMAN, 
				MISS_DINAH_MIGHT,
				MASKED_MAGICIAN,
				MAD_MINER
				->{return false;}
		
		case 
				BARON_BOMBAROLO, 
				MECHA_BOMBERMAN, 
				RETRO_LADY 
				->{return true;}

				
		default ->{return false;}
		}
	}
	/**
	 * This method returns the coolPhrase String of this hero avatar defined during development.
	 * @param hero the hero for which you need to know the coolPhrase String
	 * @return coolPhrase String
	 */
	private String getCoolPhrase(Heroes hero) {
		switch(hero) {
		case WHITE_BOMBERMAN ->{return "I'm ready!";}
		case MISS_DINAH_MIGHT ->{return "Death bombs";}
		case BARON_BOMBAROLO ->{return "Lucky Boy";}
		case MECHA_BOMBERMAN ->{return "Heavy metal";}
		case RETRO_LADY ->{return "Only hearts";}
		case MAD_MINER ->{return "Remote Bomb";}
		case MASKED_MAGICIAN ->{return "Reveal Spots";}
		default ->{return "";}
		}
	}
	/**
	 * This method returns the defined root path String of this hero avatar's sprites.
	 * @param hero the hero for which you need to know the root path String
	 * @return root path String
	 */
	private String getLabelResource(Heroes hero) {
		switch(hero) {
		case WHITE_BOMBERMAN ->{return "pgWbm";}
		case MISS_DINAH_MIGHT ->{return "pgMDM";}
		case BARON_BOMBAROLO ->{return "pgBB";}
		case MECHA_BOMBERMAN ->{return "pgMB";}
		case RETRO_LADY ->{return "pgRL";}
		case MAD_MINER ->{return "pgMadMiner";}
		case MASKED_MAGICIAN ->{return "pgMKM";}
		default ->{return "";}
		}
	}
	
	
}
