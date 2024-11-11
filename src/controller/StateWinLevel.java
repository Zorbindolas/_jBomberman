package controller;

import model.GameModel;
import model.StateLabel;
/**
 * This state is connected by design in these ways:
 * (1) from StatePlaying to this if StatePlaying's gameWin = true and there's another next level;
 * (3) from this to StateStarter with GFLO automatically (GameFirstOptionListener).
 * 
 * This state should only be achieved in case the player win the current scene (level).
 */
public class StateWinLevel extends StateAbstract {
	/**
	 * Private field for count downing (in seconds)
	 */
	private static int countDownInSeconds;
	/**
	 * Total duration in this state (in seconds)
	 */
	private static final int finalCountDown = 4;
	/**
	 * Concrete GameState constructor in which is automatically fixed the StateLabel of this state.
	 */
	private StateWinLevel() {
		super(StateLabel.WINLEVEL);
	}
	/**
	 * Singleton's getInstance method that also update current ThreadGame reference.
	 * Its instance is held by the super type.
	 * The call of this method reset the count down.
	 * @param actualTG current ThreadGame
	 * @return this instance
	 */
	public static StateWinLevel getInstance(ThreadGame actualTG) {
		if(winlevelInstance==null) winlevelInstance = new StateWinLevel();
		winlevelInstance.setActualTG(actualTG);
		countDownInSeconds = finalCountDown;
		return winlevelInstance;
	}

	@Override
	public void handleOperations() {

		if(actualTG.getTimerGeneral()==0) {
			countDownInSeconds--;
		}
		
		if(countDownInSeconds<1) {
			GameModel.getInstance().getCurrAccount().incVictoriesTotal();
			GameFirstOptionListener.getInstance().performance();
			countDownInSeconds = finalCountDown;
		}
	}
}