package model;

/**
 * Bomb release behavior designed to release remote bombs
 */
public class DropBombRemote implements ReleaseBehavior {
	
	@Override
	public Bomb release(CartesianCoordinate c) {
		return new BombRemote(c.getY(),c.getX());
	}

}
