package model;

import model.PowerUpMediator.Message;

/**
 * The only Prop that have lethality true.
 * It represents the fire that comes from the explosion of a bomb.
 * It can damage the player, npcs and destroy power up.
 */
public class Fire extends Prop implements Lethal {

	/**
	 * Direction of this fire
	 */
	private Dir fireDir;
	/**
	 * Type of flameSpread
	 */
	private FlameSpread fireSpread;
	
	/**
	 * Types of fires: they can be the tip of an explosion or inside an arm
	 */
	public static enum FlameSpread {
		/**
		 * FlameSpread ARM - type
		 */
		ARM("arm"),
		/**
		 * FlameSpread END - type
		 */
		END("end");
		/**
		 * Descriptor of the flameSpread
		 */
		String stringState;
		/**
		 * FlameSpread Constructor
		 * @param stringState this descriptor
		 */
		FlameSpread(String stringState){
			this.stringState = stringState;
		}
		@Override
		public String toString() {
			return stringState;
		}
		
	}
	/**
	 * Fire Constructor
	 * @param yGrid fixed y grid
	 * @param xGrid fixed x grid
	 * @param fireDir fire direction
	 * @param fireSpread type of fireSpread
	 * @param powerUpMediator fire's powerUpMediator
	 */
	public Fire(
			int yGrid, int xGrid, 
			Dir fireDir, FlameSpread fireSpread,
			PowerUpMediator powerUpMediator
			
			) {
		
		super(yGrid, xGrid, true, false);
		this.setLethality(true);
		this.fireDir = fireDir;
		this.fireSpread = fireSpread;
//		this.powerUpMediator = powerUpMediator;
		powerUpMediator.sendMessage(this, Message.DELETE_POS);
	}

	/**
	 * Getter of fire direction
	 * @return fire direction
	 */
	public Dir getFireDir() {return fireDir;}
	/**
	 * Getter of this fire spread
	 * @return this fire spread
	 */
	public FlameSpread getFireSpread() {return fireSpread;}
	
	@Override
	public void setLethality(boolean lethality) { this.lethality = lethality; }

	

}
