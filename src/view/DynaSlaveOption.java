package view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.GameModel;
/**
 * The Card in which is possible to change game settings.
 */
public class DynaSlaveOption extends DynaSlaveCard {
	/**
	 * Serialized key for Serialization
	 */
	private static final long serialVersionUID = -2396339054302790965L;
	/**
	 * Title sprite
	 */
	private BufferedImage logoSET;
	/**
	 * Constructor. 
	 * In this Card the User can change game settings.
	 * @param master CardLayout Master reference
	 */
	public DynaSlaveOption(DynaMasterCard master) {
		super(
				master, 
				6,
				new String[] {
						"GO TO FULLSCREEN",
						"POWERUPSt DESTROYABLE",
						"ACCOUNT IS VISIBLE",
						"TURN SONGS OFF",
						"TURN SOUND EFFECTS OFF",
						"BACK"},
				new int[] {3,4,5,6,7,8},
				"bgvaporwave"
				);
		
		try {
			logoSET = ImageIO.read(getClass().getResourceAsStream("/menu/logoSET.png"));
		} catch (IOException e) {e.printStackTrace();}

	}

	@Override
	public void optionSwitcher(int pointer) {
		switch(pointer) {
		case 0 -> {
			if(master.getFrame().isFullscreen()) {
				
				master.getFrame().setFullScreenOff();
				changeTextOfAnOptionAtRuntime(0,"GO TO FULLSCREEN");
				
			} else { 
				
				master.getFrame().setFullScreenOn();
				changeTextOfAnOptionAtRuntime(0,"GO TO NORMAL SCREEN");
				
			}
		}
		case 1 -> {
			if(GameModel.getInstance().isDestroyablePowerUp()) {
				
				GameModel.getInstance().setDestroyablePowerUp(false);
				changeTextOfAnOptionAtRuntime(1,"POWERUPSt INDESTRUCTIBLE");
				
			} else { 
				
				GameModel.getInstance().setDestroyablePowerUp(true);
				changeTextOfAnOptionAtRuntime(1,"POWERUPSt DESTROYABLE");
				
			}
			
		}
		case 2 -> {
			if(GameModel.getInstance().isCurrAccountVisible()) {
				
				GameModel.getInstance().setCurrAccountVisible(false);
				changeTextOfAnOptionAtRuntime(2,"ACCOUNT IS INVISIBLE");
				
			} else { 
				
				GameModel.getInstance().setCurrAccountVisible(true);
				changeTextOfAnOptionAtRuntime(2,"ACCOUNT IS VISIBLE");
				
			}
		}
		case 3 -> {
			if(DynaSound.isMuteSong()) {
				
				DynaSound.setMuteSong(false);
				changeTextOfAnOptionAtRuntime(3,"TURN SONGS OFF");
				DynaSound.playSong("option");
				
			} else { 
				
				DynaSound.setMuteSong(true);
				changeTextOfAnOptionAtRuntime(3,"TURN SONGS ON");
				
			}
		}
		case 4 -> {
			if(DynaSound.isMuteEffects()) {
				
				DynaSound.setMuteEffects(false);
				changeTextOfAnOptionAtRuntime(4,"TURN SOUND EFFECTS OFF");
				
			} else { 
				
				DynaSound.setMuteEffects(true);
				changeTextOfAnOptionAtRuntime(4,"TURN SOUND EFFECTS ON");
				
			}
		}
		case 5 -> escFunction();
		default -> {}
		}
	}
	
	@Override
	public void escFunction() {
		super.changePanel("start");
	}
	
	@Override
	public Graphics2D drawStuff(Graphics2D g2) {
		
		g2.drawImage(logoSET,
				(master.getFrame().getWStand()-300)/2,
				0,
				300,
				150,
				null
				);

		return g2;
		
	}

	@Override
	public void relocateComponents() {
		
		this.meterW = master.getFrame().getTenthW();
		this.meterH = master.getFrame().getTenthH();
		
		// posY
		redefinePosY(meterH,yOptions);
		
		// posX
		redefinePosXCentralPositioning(standardWidthChar);

		
		// Relocate buttons		
		relocateButtonsToDrawnText(standardWidthChar, buttonHeight);

	}


}
