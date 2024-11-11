package controller;

import model.StateLabel;
/**
 * This state is connected by design in these ways:
 * (1) from StatePlaying to this if StatePlaying's gameOver = true;
 * (2) from StateContinue to this with GSLO (GameSecondOptionListener);
 * (3) from StatePaused to this with GSLO (GameSecondOptionListener);
 * (4) from this to ThreadGame's ender with gameOver=true (ThreadGame's death).
 * 
 * This state should only be achieved in case the player has lost all his lives
 * and all his attempts.
 * This state causes the return to the initial menu.
 */
public class StateGameOver extends StateAbstract {
	/**
	 * Private field for count downing (in seconds)
	 */
	private static int countDownInSeconds;
	/**
	 * Total duration in this state (in seconds)
	 */
	private static final int finalCountDown = 3;
	/**
	 * Concrete GameState constructor in which is automatically fixed the StateLabel of this state.
	 */
	private StateGameOver() {
		super(StateLabel.GAMEOVER);
	}
	/**
	 * Singleton's getInstance method that also update current ThreadGame reference.
	 * Its instance is held by the super type.
	 * The call of this method reset the count down.
	 * @param actualTG current ThreadGame
	 * @return this instance
	 */
	public static StateGameOver getInstance(ThreadGame actualTG) {
		if(gameoverInstance==null) gameoverInstance = new StateGameOver();
		gameoverInstance.setActualTG(actualTG);
		countDownInSeconds = finalCountDown;
		return gameoverInstance;
	}

	@Override
	public void handleOperations() {

		if(actualTG.getTimerGeneral()==0) {
			countDownInSeconds--;
		}
		
		if(countDownInSeconds<1) {
			GameSecondOptionListener.getInstance().performance();
			countDownInSeconds = finalCountDown;
		}
	}
}