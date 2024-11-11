package controller;

import model.Dir;
import model.GameState;
import model.StateLabel;
/**
 * This abstraction holds the instances of its sub-types
 * and it supplies them with some utility methods.
 * Its actual Thread Game reference is updated on transition of state.
 * Transitions of states are defined in concrete GameState or by GameOptionListener's performance.
 */
public abstract class StateAbstract implements GameState {	
	/**
	 * Singleton Instance of StateStarter
	 */
	public static StateStarter starterInstance;
	/**
	 * Singleton Instance of StatePlaying
	 */
	public static StatePlaying playingInstance;
	/**
	 * Singleton Instance of StatePaused
	 */
	public static StatePaused pausedInstance;
	/**
	 * Singleton Instance of StateContinue
	 */
	public static StateContinue continueInstance;
	/**
	 * Singleton Instance of StateGameOver
	 */
	public static StateGameOver gameoverInstance;
	/**
	 * Singleton Instance of StateWinLevel
	 */
	public static StateWinLevel winlevelInstance;
	/**
	 * Singleton Instance of StateWinStage
	 */
	public static StateWinStage winstageInstance;
	/**
	 * Current ThreadGame instance
	 */
	protected ThreadGame actualTG;
	/**
	 * StateLabel of this particular concrete State
	 */
	private StateLabel stateLabel;
	/**
	 * Protected Constructor of a concrete State
	 * @param stateLabel stateLabel of this concrete State
	 */
	protected StateAbstract(StateLabel stateLabel) {
		this.stateLabel = stateLabel;
	}
	/**
	 * Change the current ThreadGame instance
	 * @param newTG new ThreadGame instance
	 */
	protected void setActualTG(ThreadGame newTG) {actualTG = newTG;}
	
	@Override
	public StateLabel getStateLabel(){return stateLabel;}
	/**
	 * Change current concrete GameState of this current ThreadGame
	 * @param newState new current GameState
	 */
	protected void transitionTo(StateAbstract newState) {
		actualTG.setCurrentState(newState);
	}
	/**
	 * Apply controller's inputs up or down to ThreadGame if it has an options panel as current state.
	 */
	protected void checkOptionSelections() {
		if(actualTG.getClockNRoll()>6) {
			// set up|down based on the inputs
			actualTG.setUp(actualTG.getDirection()==Dir.UP);
			actualTG.setDown(actualTG.getDirection()==Dir.DOWN);
			// apply the input (up has priority)
			actualTG.checkUpOrDown(actualTG.getPanel().getMod());
			// reset clockNRoll
			actualTG.setClockNRoll(0);
		}
		
	}
	/**
	 * Apply controller's input enter to ThreadGame if it has an options panel as current state.
	 */
	protected void listenForOption() {
		if(actualTG.isEnter()
				||
				actualTG.getPanel().getMaster().isEnteredPressedByMouse()) {
			
			int p =	actualTG.getPanel().getMaster().getPointer();
			if(p==0) {
				GameFirstOptionListener.getInstance().performance();
			} else if(p==1) {
				GameSecondOptionListener.getInstance().performance();
			}
		}
	}
	
	@Override
	public String toString() {
		return stateLabel.toString();
	}
	
}
