package view;

import java.awt.Graphics2D;

import model.StateLabel;
/**
 * It's the state of the view in which a game session is in progress.
 */
public class StatePanelPlaying extends StatePanel {
	/**
	 * Constructor.
	 * It's the state of the view in which a game session is in progress.
	 * @param dsg DynaSlaveGame reference
	 */
	public StatePanelPlaying(DynaSlaveGame dsg) {
		super(
				dsg, 
				StateLabel.PLAYING, 
				new String[] {"", "", ""}
				);
	}
	
	@Override
	public void handleUpdateOptionsStrings() {
		
		dsg.getMaster().setDrawDefaultBackground(false);
		dsg.getMaster().setDrawPointer(false);
		dsg.getMaster().setDrawOptions(false);
		
		dsg.changeTextOfAnOptionAtRuntime(0, stringTwins[0]);
		dsg.changeTextOfAnOptionAtRuntime(1, stringTwins[1]);
		dsg.changeTextOfAnOptionAtRuntime(2, stringTwins[2]);
		
	}
	
	@Override
	public Graphics2D handleDrawStuff(Graphics2D bufferedGraphics) {
		
		dsg.renderDock(bufferedGraphics);
        
        dsg.renderArchs(bufferedGraphics);
        
        dsg.renderBackgroundGrid(bufferedGraphics);
        
        dsg.renderPowerUps(bufferedGraphics);
        
        dsg.renderNPCsWithPlayer(bufferedGraphics);
        
        dsg.renderGravediggers(bufferedGraphics);
        
        return bufferedGraphics;

	}
	
	
}
