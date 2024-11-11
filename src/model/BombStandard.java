package model;

/**
 *  Standard bomb model.
 *  Mimics the bomb from the original video game.
 */
public class BombStandard extends Bomb {

	/**
	 * Constructor for this bomb
	 * @param yGrid y grid coordinate of this bomb
	 * @param xGrid x grid coordinate of this bomb
	 */
	public BombStandard(int yGrid, int xGrid) {
		super(yGrid, xGrid);
	}

	@Override
	public int[] getForms() {
		return new int[] {1,2,1,0,1,2,1,0};
	}

}
