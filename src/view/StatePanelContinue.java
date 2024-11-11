package view;

import model.StateLabel;
/**
 * It's the state of the View when the player has lost an attempt 
 * but can decide to try another game session without game over.
 */
public class StatePanelContinue extends StatePanelOptionable {
	/**
	 * Constructor.
	 * It's the state of the View when the player has lost an attempt 
	 * but can decide to try another game session without game over.
	 * @param dsg DynaSlaveGame reference
	 */
	public StatePanelContinue(DynaSlaveGame dsg) {
		super(
				dsg, 
				StateLabel.CONTINUE, 
				new String[] {"YES", "NO", ""},
				"CONTINUEq"
				);
	}
	
}
