package view;

import java.awt.Graphics2D;

import model.StateLabel;
/**
 * It's the state of the view when a game level win occurs.
 */
public class StatePanelGameWin extends StatePanel {
	/**
	 * Constructor.
	 * It's the state of the view when a game level win occurs.
	 * @param dsg DynaSlaveGame reference
	 */
	public StatePanelGameWin(DynaSlaveGame dsg) {
		super(
				dsg, 
				StateLabel.WINLEVEL, 
				new String[] {"LEVEL COMPLETED", "", ""}
				);
	}
	
	@Override
	public void handleUpdateOptionsStrings() {
//		dsg.getAButton(0).addActionListener(GameFirstOptionListener.getInstance());
//		dsg.getAButton(1).addActionListener(GameSecondOptionListener.getInstance());
		
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
