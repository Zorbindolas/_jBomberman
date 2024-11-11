package controller;

import model.GameModel;
import model.PlayerCharacter;
import model.StateLabel;
/**
 * This state is connected by design in these ways:
 * (1) from initialization of a new ThreadGame to this;
 * (2) from this to StatePlaying when this count-down is completed.
 * 
 * This state is the first and initial state of the Conditional Controller Architecture.
 */
public class StateStarter extends StateAbstract {
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
	private StateStarter() {
		super(StateLabel.STARTER);
	}
	/**
	 * Singleton's getInstance method that also update current ThreadGame reference.
	 * Its instance is held by the super type.
	 * The call of this method reset the count down.
	 * @param actualTG current ThreadGame
	 * @return this instance
	 */
	public static StateStarter getInstance(ThreadGame actualTG) {
		if(starterInstance==null) starterInstance = new StateStarter();
		starterInstance.setActualTG(actualTG);
		countDownInSeconds = finalCountDown;
		return starterInstance;
	}
	
	//public int getCountDownInSeconds() {return countDowninSeconds;}
	
	@Override
	public void handleOperations() {
		
			
		if(actualTG.getTimerGeneral()==0) {
			countDownInSeconds--;
		}

		
		if(countDownInSeconds<1) {
			GameModel.getInstance().getPowerUpMediator().reset();
			GameModel.getInstance().getNpcMediator().load(
					GameModel.getInstance().getActualLevel().getNpcs()
					);
			StatePlaying statePlaying = StatePlaying.getInstance(actualTG);
			
			PlayerCharacter player = GameModel.getInstance().getActualPC();
			player.setInvincibility(6);
//			player.setXGrid(0);
//			player.setYGrid(0);
//			player.setXPanel(MyJBombermanFormat.START_PC_X);
//			player.setYGrid(MyJBombermanFormat.START_PC_Y);
			statePlaying.resetVariablesAndPlayingTimerWith(180);
			transitionTo(statePlaying);
		}
		
	}


}
