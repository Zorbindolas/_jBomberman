package model;
/**
 * Interface with public static field of this videogame parameters grid
 */
public interface MyGridFormat {

	/**
	 * Columns of the grid
	 */
	int COLUMNS = 13;
	/**
	 * Rows of the grid
	 */
	int ROWS = 11;
	/**
	 * Number of regular walls
	 */
	int REGULAR_WALLS = (ROWS / 2) * (COLUMNS / 2); // 30
	
}
