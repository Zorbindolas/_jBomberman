package view;

import java.awt.Graphics2D;

import model.StateLabel;
/**
 * Abstraction for states that display options on the screen that can be chosen by the user.
 * The following states belong to this type:
 * StatePanelContinue, StatePanelPaused.
 */
public abstract class StatePanelOptionable extends StatePanel {
	/**
	 * Current pointer value
	 */
	private int pointer;
	/**
	 * Title for this panel
	 */
	private String title;
	/**
	 * Constructor.
	 * Abstraction for states that display options on the screen that can be chosen by the user.
	 * @param dsg DynaSlaveGame reference
	 * @param stateLabel state label of this panel
	 * @param options options names
	 * @param title title of this panel
	 */
	public StatePanelOptionable(
			DynaSlaveGame dsg, 
			StateLabel stateLabel,
			String[] options,
			String title
			) {
		
		super(
				dsg, 
				stateLabel, 
				options
				);
		this.title = title;
	}
	
	@Override
	public void handleUpdateOptionsStrings() {
		
		dsg.getMaster().setDrawPointer(true);
		dsg.getMaster().setDrawOptions(true);
		
		pointer = dsg.getMaster().getPointer();
		
		handleOptionSwitcher(pointer);
		
	}
	
	@Override
	public void handleOptionSwitcher(int pointer) {
			switch(pointer) {
				case 0 -> {		
					dsg.getMaster().setPointer(0);
					dsg.changeTextOfAnOptionAtRuntime(0, stringTwins[0]);
					dsg.changeTextOfAnOptionAtRuntime(1, " "+stringTwins[1]);
				}
				case 1 -> {
					dsg.getMaster().setPointer(1);
					dsg.changeTextOfAnOptionAtRuntime(0, " "+stringTwins[0]);
					dsg.changeTextOfAnOptionAtRuntime(1, stringTwins[1]);
				}
				default -> {}
			}

	}
	
	
	@Override
	public Graphics2D handleDrawStuff(Graphics2D bufferedGraphics) {
		
		dsg.paintWindowBlack(bufferedGraphics);
		
		dsg.paintString(bufferedGraphics,title);
		
		return bufferedGraphics;
	}
}
