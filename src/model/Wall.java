package model;

/**
 * These Prop are unbreakable and solid.
 */
public class Wall extends Prop{
	/**
	 * Wall Constructor
	 * @param yGrid fixed y grid
	 * @param xGrid fixed x grid
	 */
	public Wall(int yGrid, int xGrid) {
		super(yGrid, xGrid, false, true);
	}
	/**
	 * Wall Constructor without grid coordinate definitions
	 */
	public Wall() {
		super(false, true);
	}
	
	/**
	 * Wall solidity cannot be set.
	 */
	@Override
	public final void setSolid(boolean solid) {}

}
