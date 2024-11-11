package model;
/**
 * Particular bomb with fewer pre-explosion phases.
 */
public class BombFaster extends Bomb {
	
	/**
	 * Constructor for this bomb
	 * @param yGrid y grid coordinate of this bomb
	 * @param xGrid x grid coordinate of this bomb
	 */
	public BombFaster(int yGrid, int xGrid) {
		super(yGrid, xGrid);
	}

	@Override
	public int[] getForms() {
		return new int[] {0,1,2,1,0,3};
	}

}
