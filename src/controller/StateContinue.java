package controller;

import model.StateLabel;
/**
 * This state is connected by design in these ways:
 * (1) from StatePlaying to this if StatePlaying's gameContinue = true;
 * (2) from this to StateStarter with GFLO (GameFirstOptionListener);
 * (3) from this to StateGameOver with GSLO (GameSecondOptionListener).
 * 
 * This state should only be achieved in case the player has lost all his lives, 
 * but still has at least one attempt left.
 * This state gives the user the possibility to choose whether to continue playing 
 * (consuming one attempt) or whether to return to the initial menu.
 * If a new attempt is consumed then the current level is regenerated.
 */
public class StateContinue extends StateAbstract {
	/**
	 * Concrete GameState constructor in which is automatically fixed the StateLabel of this state.
	 */
	private StateContinue() {
		super(StateLabel.CONTINUE);
	}
	/**
	 * Singleton's getInstance method that also update current ThreadGame reference.
	 * Its instance is held by the super type.
	 * @param actualTG current ThreadGame
	 * @return this instance
	 */
	public static StateContinue getInstance(ThreadGame actualTG) {
		if(continueInstance==null) continueInstance = new StateContinue();
		continueInstance.setActualTG(actualTG);
		return continueInstance;
	}

	@Override
	public void handleOperations() {
			
		checkOptionSelections();
		
		listenForOption();
			
	}
		
}
