package model;

import java.util.List;

/**
 * Interface implemented by the entities chasing the main character
 */
public interface Hound {

	/**
	 * Redefine panel modifiers for the move direction
	 * @param dirs accepted directions
	 */
	public void updateDirModders(List<Dir> dirs);
}
