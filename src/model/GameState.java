package model;

/**
 * Class for State Design Pattern
 */
public interface GameState {

	/**
	 * Operations handle by this GameState
	 */
	public abstract void handleOperations();

	/**
	 * Getter of this GameState label
	 * @return this stateLabel
	 */
	public abstract StateLabel getStateLabel();
	
}
