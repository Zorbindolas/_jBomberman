package model;

import model.PowerUpMediator.Message;
/**
 * These Prop are breakable and solid.
 * When such an object gets destroyed,
 * it recalls a powerUpMediator method
 * before this.trespasser (inherited by Prop).
 */
public class Obstacle extends Prop {
	
	/**
	 * Reference to power up mediator
	 */
	private PowerUpMediator powerUpMediator;
	/**
	 * Obstacle Constructor
	 * @param yGrid fixed y grid
	 * @param xGrid fixed x grid
	 * @param powerUpMediator reference to powerUpMediator
	 */
	public Obstacle(int yGrid, int xGrid, PowerUpMediator powerUpMediator) {
		super(yGrid, xGrid, true, true);
		this.powerUpMediator = powerUpMediator;
	}
	/**
	 * Obstacle Constructor without grid coordinate definitions
	 */
	public Obstacle() {
		super(true, true);
	}
	
	@Override
	public void trespasser(LevelAbstract level) {
		// do stuff before destruction
		// send a message to PowerUpMediator to possibly generate a power up here
		powerUpMediator.sendMessage(this,Message.ADD_POS);
		super.trespasser(level);
	}

}
