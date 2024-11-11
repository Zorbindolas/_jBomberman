package view;

import model.StateLabel;
/**
 * It's the state of the view when the game is paused by the user.
 */
public class StatePanelPaused extends StatePanelOptionable {
	/**
	 * Constructor.
	 * It's the state of the view when the game is paused by the user.
	 * @param dsg DynaSlaveGame reference
	 */
	public StatePanelPaused(DynaSlaveGame dsg) {
		super(
				dsg, 
				StateLabel.PAUSED, 
				new String[] {"PLAY", "EXIT", ""},
				"PAUSE"
				);
	}

}
