package view;

import model.Gravedigger;
/**
 * Gravedigger Decoration permits to add special effects to a Gravedigger related event.
 * The state of these objects is intended to be an integer value, implemented in sub-types.
 * DecoratorGravedigger management is entrusted to DynaSlaveGame panel in Observable-Observer way
 * between Model and View.
 */
public abstract class DecoratorGravedigger extends Gravedigger {
	/**
	 * Gravedigger to decorate
	 */
	private Gravedigger component;
	/**
	 * Parameter related to special effects
	 */
	protected boolean boolState;
	/**
	 * Gravedigger special effects changes happens when it increments its state.
	 */
	protected int actualState;
	/**
	 * Constructor
	 * @param component Gravedigger to decorate
	 */
	public DecoratorGravedigger(Gravedigger component) {
		super(component.getScore(),component.getYPanel(),component.getXPanel());
		this.component = component;
	}

	@Override
	public int getTempo() {return component.getTempo();}
	@Override
	public long getScore() {return component.getScore();}
	@Override
	public String getScoreString() {return component.getScoreString();}
	@Override
	public int getYPanel() {return component.getYPanel();}
	@Override
	public int getXPanel() {return component.getXPanel();}	
	@Override
	public void setXPanel(int xPanel) {component.setXPanel(xPanel);}
	@Override
	public void setYPanel(int yPanel) {component.setYPanel(yPanel);;}
	/**
	 * Increment current state for animation
	 */
	public void incState() {
		actualState++;
		boolState = !boolState;
	}
	/**
	 * Getter of the current state
	 * @return this current state
	 */
	public int getActualState() {
		return actualState;
	}
	/**
	 * Check ending footage condition
	 * @return ending footage condition
	 */
	public abstract boolean endFootage();
	
}