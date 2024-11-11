package view;

import java.awt.Graphics2D;

import model.StateLabel;
/**
 * It's the state of the view in which the player won the stage. 
 * The View is about to be returned to the DynaSlaveStart.
 */
public class StatePanelWinStage extends StatePanel {
	/**
	 * Constructor.
	 * It's the state of the view in which the player won the stage. 
	 * @param dsg DynaSlaveGame reference
	 */
	public StatePanelWinStage(DynaSlaveGame dsg) {
		super(
				dsg, 
				StateLabel.WINSTAGE, 
				new String[] {"STAGE COMPLETED", "YOU WIN", ""}
				);
	}
	
	@Override
	public void handleUpdateOptionsStrings() {
//		dsg.getAButton(0).removeActionListener(GameFirstOptionListener.getInstance());
//		dsg.getAButton(1).removeActionListener(GameSecondOptionListener.getInstance());
		
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
