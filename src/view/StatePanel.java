package view;

import java.awt.Graphics2D;

import model.StateLabel;
/**
 * Abstraction of View Game State used for DynaSlaveGame states.
 * A subtype of this class emulates the state manifested 
 * by the Model that is running in the Controller.
 */
public abstract class StatePanel {
	/**
	 * Current DynaSlaveGame in this application
	 */
	protected final DynaSlaveGame dsg;
	/**
	 * State label associated with this State
	 */
	protected final StateLabel stateLabel;
	/**
	 * Names Strings for the DynaSlaveGame in this State
	 */
	protected String[] stringTwins;
	/**
	 * Constructor. 
	 * Abstraction of View Game State used for DynaSlaveGame states.
	 * A subtype of this class emulates the state manifested 
	 * by the Model that is running in the Controller.
	 * 
	 * @param dsg DynaSlaveGame reference
	 * @param stateLabel state label for this state
	 * @param stringTwins names Strings in this state 
	 */
	public StatePanel(
			DynaSlaveGame dsg,
			StateLabel stateLabel,
			String[] stringTwins) {
		
		this.dsg = dsg;
		this.stateLabel = stateLabel;
		this.stringTwins = stringTwins;

	}
	
	// to execute during transition of state
	/**
	 * Setter of drawing conditions to execute during transition of state
	 */
	public abstract void handleUpdateOptionsStrings();
	/**
	 * Determine which methods are used to draw on the defScreen during this state
	 * @param bufferedGraphics graphic context
	 * @return updated drawn graphic context
	 */
	public abstract Graphics2D handleDrawStuff(Graphics2D bufferedGraphics);
	/**
	 * Command executor of the button associated with the current pointer value
	 * @param pointer current pointer value
	 */
	public void handleOptionSwitcher(int pointer) {
			switch(pointer) {
			case 0 -> {}
			case 1 -> {}
			default -> {}
		}
	}
	

}
