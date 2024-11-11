package model;

/**
 * Bomb release behavior designed to release faster bombs
 */
public class DropBombFaster implements ReleaseBehavior {
	
	@Override
	public Bomb release(CartesianCoordinate c) {
		return new BombFaster(c.getY(),c.getX());
	}

}
