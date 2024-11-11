package controller;

import model.StateLabel;
/**
 * This state is connected by design in these ways:
 * (1) from StatePlaying to this if StatePlaying's gameWin = true and there's not another next level;
 * (3) from this to ThreadGame's ender with gameWin=true (ThreadGame's death).
 * 
 * This state should only be achieved in case the player win all levels of the current Stage.
 */
public class StateWinStage extends StateAbstract {
	/**
	 * Private field for count downing (in seconds)
	 */
	private static int countDownInSeconds;
	/**
	 * Total duration in this state (in seconds)
	 */
	private static final int finalCountDown = 7;
	/**
	 * Concrete GameState constructor in which is automatically fixed the StateLabel of this state.
	 */
	private StateWinStage() {
		super(StateLabel.WINSTAGE);
	}
	/**
	 * Singleton's getInstance method that also update current ThreadGame reference.
	 * Its instance is held by the super type.
	 * The call of this method reset the count down.
	 * @param actualTG current ThreadGame
	 * @return this instance
	 */
	public static StateWinStage getInstance(ThreadGame actualTG) {
		if(winstageInstance==null) winstageInstance = new StateWinStage();
		winstageInstance.setActualTG(actualTG);
		countDownInSeconds = finalCountDown;
		return winstageInstance;
	}

	@Override
	public void handleOperations() {

		if(actualTG.getTimerGeneral()==0) {
			countDownInSeconds--;
		}
		
		if(countDownInSeconds<1) {
			GameFirstOptionListener.getInstance().performance();
			countDownInSeconds = finalCountDown;
		}
	}
}
