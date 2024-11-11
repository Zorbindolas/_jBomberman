package controller;
/**
 * Abstraction implemented by Concrete Command (Command Pattern), 
 * provided by Java Swing Architecture.
 * Using Abstract Class I can make its method protected.
 * Its performance method implements the performed action when a DynaSlaveGame's button is clicked.
 * It encapsulates the family of algorithms of all concrete states provided by that button.
 * The contextual operations are selected by a switch and are relative to the current state of game.
 */

public abstract class GameOptionListener {
	/**
	 * Selector of the contextual operation to execute
	 */
	protected abstract void performance();
}
