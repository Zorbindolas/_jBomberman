package model;

import java.util.Random;

/**
 * Pre-established set of directions that give dimensionality to the entities that live in this application.
 */
public enum Dir {
	/**
	 * NORD direction
	 */
	UP("nord"),
	/**
	 * DOWN direction
	 */
	DOWN("sud"),
	/**
	 * LEFT direction
	 */
	LEFT("ovest"),
	/**
	 * RIGHT direction
	 */
	RIGHT("est"),
	/**
	 * NONE direction
	 */
	NONE("center");
	/**
	 * Name of this direction
	 */
	String stringState;
	/**
	 * static field for random calculations
	 */
	private static Random random = new Random();
	
	/**
	 * Enumeration Constructor 
	 * @param stringState name of the constructed direction
	 */
	Dir(String stringState){
		this.stringState = stringState;
	}
	
	/**
	 * Give the name of the direction
	 */
	@Override
	public String toString() {
		return stringState;
	}
	
	/**
	 * Converts the direction into a Cartesian point 
	 * whose values ​​can be added to those of another point.
	 * Given a starting point these modifiers allow you 
	 * to obtain the new coordinates of the arrival point 
	 * depending on the direction taken.
	 * @return Cartesian point with modifier values
	 */
	public CartesianCoordinate getCartesianModifier() {
		switch(this) {
		case UP : return new CartesianCoordinate(-1,0);
		case DOWN : return new CartesianCoordinate(1,0);
		case RIGHT : return new CartesianCoordinate(0,1);
		case LEFT : return new CartesianCoordinate(0,-1);
		case NONE : return new CartesianCoordinate(0,0);
		default : return new CartesianCoordinate(0,0); 
		}
	}
	
	/**
	 * Get a casual direction, including none
	 * @return a casual direction
	 */
	public static Dir getCasualDir() {
		int casualPos = random.nextInt(0,values().length);
		return Dir.values()[casualPos];
	}
	
	/**
	 * Get a casual direction, excluding none
	 * @return a casual direction
	 */
	public static Dir getCasualDirWithoutNone() {
		Dir[] dirs = new Dir[] {UP,RIGHT,LEFT,DOWN};
		int casualPos = random.nextInt(0,dirs.length);
		return dirs[casualPos];
	}
	
//	public static void main(String[] args) {
//		Dir d = Dir.UP;
//		System.out.println(d);
//	}
	
}
