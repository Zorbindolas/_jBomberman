package view;

import java.awt.Graphics2D;

import model.StateLabel;
/**
 * It's the state of the view when a game over occurs.
 */
public class StatePanelGameOver extends StatePanel {
	/**
	 * Constructor.
	 * It's the state of the view when a game over occurs.
	 * @param dsg DynaSlaveGame reference
	 */
	public StatePanelGameOver(DynaSlaveGame dsg) {
		super(
				dsg, 
				StateLabel.LOADING, 
				new String[] {"GAME OVER", "", ""}
				);
	}
	
	@Override
	public void handleUpdateOptionsStrings() {
		
		dsg.getMaster().setDrawDefaultBackground(false);
		dsg.getMaster().setDrawPointer(false);
		dsg.getMaster().setDrawOptions(true);
		
		dsg.changeTextOfAnOptionAtRuntime(0, stringTwins[0]);
		dsg.changeTextOfAnOptionAtRuntime(1, stringTwins[1]);
		
	}
	
	@Override
	public Graphics2D handleDrawStuff(Graphics2D bufferedGraphics) {
		
		dsg.paintItBlack(bufferedGraphics);
		
		return bufferedGraphics;
        
	}

}
