package view;

import java.awt.Graphics2D;

import model.StateLabel;
/**
 * It's the first state of the view when Controller and Model aren't ready yet.
 */
public class StatePanelLoading extends StatePanel {
	/**
	 * Constructor.
	 * It's the first state of the view when Controller and Model aren't ready yet.
	 * @param dsg DynaSlaveGame reference
	 */
	public StatePanelLoading(DynaSlaveGame dsg) {
		super(
				dsg, 
				StateLabel.LOADING, 
				new String[] {"LOADING", "", ""}
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
