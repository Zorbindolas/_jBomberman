package controller;

import model.GameModel;
import model.StateLabel;
/**
 * This state is connected by design in these ways:
 * (1) from StatePlaying to this if StatePlaying's actualTG.isPaused = true;
 * (2) from this to StatePlaying with GFLO (GameFirstOptionListener);
 * (3) from this to StateGameOver with GSLO (GameSecondOptionListener).
 * 
 * This state should only be achieved in case the player press the input for pause (P key).
 * In this state, ThreadGame loops without changes.
 */
public class StatePaused extends StateAbstract {
	/**
	 * Concrete GameState constructor in which is automatically fixed the StateLabel of this state.
	 */
	private StatePaused() {
		super(StateLabel.PAUSED);
	}
	/**
	 * Singleton's getInstance method that also update current ThreadGame reference.
	 * Its instance is held by the super type.
	 * @param actualTG current ThreadGame
	 * @return this instance
	 */
	public static StatePaused getInstance(ThreadGame actualTG) {
		if(pausedInstance==null) pausedInstance = new StatePaused();
		pausedInstance.setActualTG(actualTG);
		return pausedInstance;
	}

	@Override
	public void handleOperations() {
		
		checkOptionSelections();
		
		listenForOption();
		
		// exit from pause
		if(actualTG.isPaused()) {
			transitionTo(StatePlaying.getInstance(actualTG));
		}
		
		if(actualTG.getTimerGeneral() == 0) {
			GameModel.getInstance().getCurrAccount().incPauseTime();
		}

	}
	
	
	
}
