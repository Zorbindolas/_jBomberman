package view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.MyGridFormat;
/**
 * This class is composed by methods and pictures that concur to represent
 * the external non-playable borders for game-panel.
 */
public abstract class Arches implements MyJBombermanFormat, MyGridFormat {
	/**
	 * Arches is formed by each Arch combination.
	 */
	public enum Arch {
		/**
		 * NORD_OVEST Arch
		 */
		NORD_OVEST, 
		/**
		 * MID_OVEST Arch
		 */
		MID_OVEST, 
		/**
		 * SUD_OVEST Arch
		 */
		SUD_OVEST,
		/**
		 * NORD_EST Arch
		 */
		NORD_EST, 
		/**
		 * MID_EST Arch
		 */
		MID_EST, 
		/**
		 * SUD_EST Arch
		 */
		SUD_EST,
		/**
		 * MID_NORD_LEFT Arch
		 */
		MID_NORD_LEFT, 
		/**
		 * MID_NORD_RIGHT Arch
		 */
		MID_NORD_RIGHT, 
		/**
		 * MID_SUD_LEFT Arch
		 */
		MID_SUD_LEFT, 
		/**
		 * MID_SUD_RIGHT Arch
		 */
		MID_SUD_RIGHT;
		
	}
	/**
	 * The extension of image files.
	 */
	private final String sfx = ".png";
	/**
	 * Image for a specific Arch position.
	 */
	protected BufferedImage nordOvest,midOvestOne,midOvestTwo,sudOvest,nordEst,
							midEstOne,midEstTwo,sudEst,midNordLeft,midNordRight,
							midSudLeft,midSudRight;
	/**
	 * Constructor of Arches.
	 * @param path files path for images files
	 */
	public Arches(String path) {

		try {
			
			nordOvest = ImageIO.read(getClass().getResourceAsStream(path+"nordOvest"+sfx));
			midOvestOne = ImageIO.read(getClass().getResourceAsStream(path+"midOvestOne"+sfx));
			midOvestTwo = ImageIO.read(getClass().getResourceAsStream(path+"midOvestTwo"+sfx));
			sudOvest = ImageIO.read(getClass().getResourceAsStream(path+"sudOvest"+sfx));
			
			nordEst = ImageIO.read(getClass().getResourceAsStream(path+"nordEst"+sfx));
			midEstOne = ImageIO.read(getClass().getResourceAsStream(path+"midEstOne"+sfx));
			midEstTwo = ImageIO.read(getClass().getResourceAsStream(path+"midEstTwo"+sfx));
			sudEst = ImageIO.read(getClass().getResourceAsStream(path+"sudEst"+sfx));
			
			midNordLeft = ImageIO.read(getClass().getResourceAsStream(path+"midNordLeft"+sfx));
			midNordRight = ImageIO.read(getClass().getResourceAsStream(path+"midNordRight"+sfx));

			midSudLeft = ImageIO.read(getClass().getResourceAsStream(path+"midSudLeft"+sfx));
			midSudRight = ImageIO.read(getClass().getResourceAsStream(path+"midSudRight"+sfx));
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Arches drawing method to use in the DynaSlaveGame drawStuff.
	 * @param g2 Graphics Object to be drawn
	 */
	public void render(Graphics2D g2) {

		g2.drawImage(
				nordOvest,
				0,
				HEIGHT_DOCK,
				UNIT_MEGA,
				UNIT_NORMAL,
				null);
		
		g2.drawImage(
				nordEst,
				X_SPAWN_ARCH_NORD,
				HEIGHT_DOCK,
				UNIT_MEGA,
				UNIT_NORMAL,
				null);

		g2.drawImage(
				sudOvest,
				0,
				Y_SPAWN_ARCS_SUD,
				UNIT_MEGA,
				UNIT_NORMAL,
				null);
		
		g2.drawImage(
				sudEst,
				X_SPAWN_ARCH_NORD,
				Y_SPAWN_ARCS_SUD,
				UNIT_MEGA,
				UNIT_NORMAL,
				null);
	}
	
//	public BufferedImage getImage(ArchPosition position) {
//	
//	switch(position) {
//	
//	case NORD_OVEST :  return nordOvest;
//	case MID_OVEST: return midOvest;
//	case SUD_OVEST : return sudOvest; 
//	
//	case NORD_EST : return nordEst; 
//	case MID_EST : return midEst;
//	case SUD_EST : return sudEst; 
//	
//	case MID_NORD_LEFT : return midNordLeft; 
//	case MID_NORD_RIGHT : return midNordRight;
//
//	case MID_SUD_LEFT : return midSudLeft; 
//	case MID_SUD_RIGHT : return midSudRight;
//	
//	}
//	return null;
//}

}
