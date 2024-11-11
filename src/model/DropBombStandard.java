package model;

/**
 * Bomb release behavior designed to release standard bombs
 */
public class DropBombStandard implements ReleaseBehavior {
	
	@Override
	public Bomb release(CartesianCoordinate c) {
		return new BombStandard(c.getY(),c.getX());
	}

}
