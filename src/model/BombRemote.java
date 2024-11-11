package model;

/**
 * special bomb whose explosion is determined by the 
 * player's input and not by the transitory phases. 
 * Following activation, the transitional phases are completed anyway, 
 * but they are few compared to the standard bomb.
 */
public class BombRemote extends Bomb implements TimerExplosion {
	
	/**
	 * Boolean representing the activation status of the bomb. 
	 */
	private boolean triggered;

	/**
	 * Constructor for this bomb
	 * @param yGrid y grid coordinate of this bomb
	 * @param xGrid x grid coordinate of this bomb
	 */
	public BombRemote(int yGrid, int xGrid) {
		super(yGrid, xGrid);
	}
	
	@Override
	public int[] getForms() {
		return new int[] {0,1};
	}
	
	@Override
	public boolean isTriggered() {return triggered;}
	@Override
	public void trigger() {triggered = true;}
	
	/**
	 * This overrides it's necessary in order to fix 
	 * player's remoteBombs list due to collateral explosions detonation
	 */
	@Override
	public void trespasser(LevelAbstract level) { 
		trigger(); // in order to fix player's remoteBombs list due to collateral explosions detonation
		super.trespasser(level);
	}

}
