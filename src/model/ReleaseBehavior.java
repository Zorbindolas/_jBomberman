package model;

/**
 * Interface for the Strategy Pattern that defines 
 * the bomb release behaviors of the player characters
 */
@FunctionalInterface
public interface ReleaseBehavior {

	/**
	 * Methods to release a bomb.
	 * it is designed so that any polymorphism with bomb supertype can be released
	 * @param c Cartesian point of the released bomb
	 * @return the released bomb
	 */
	Bomb release(CartesianCoordinate c);
}
