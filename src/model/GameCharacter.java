package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A Game Character is an entity that lives and moves in the game panel but in doing so interacts with the grid. 
 * The game is born from this exchange of relationships. In fact the characters, whether players or not, will all be subtypes of this class.
 */
public abstract class GameCharacter extends Entity 
			implements VisitorAcceptor, Comparable<GameCharacter> {
	
	/**
	 * The degrees represent the pairs of directions that identify 
	 * the derived and plausible diagonal directions. 
	 * These derived directions become necessary when entities acquire the concept of solidity. 
	 * This gives rise to the collision context between a character who lives in the panel 
	 * (and who has his own collision box, representable as a rectangle) 
	 * and an object who lives in the grid (whose solidity is the entire box it occupies).
	 */
	public static enum Degree {
		/**
		 * NORD_OVEST degree
		 */
		NORD_OVEST, 
		/**
		 * NORD_EST degree
		 */
		NORD_EST, 
		/**
		 * SUD_OVEST degree
		 */
		SUD_OVEST, 
		/**
		 * SUD_EST degree
		 */
		SUD_EST;
	}
	/**
	 * Static field common to all game characters which is used to uniquely identify them one by one during creation.
	 */
	private static int globalId = 0;
	/**
	 * Identifier of this Game Character.
	 * In terms of databases it represents the key that uniquely differentiates the characters
	 */
	protected final int ID;
	/**
	 * Predefine starting direction of this Game Character
	 */
	protected final Dir STARTING_DIR = Dir.RIGHT;
	/**
	 * Direction of current movement of this game character.
	 */
	private Dir direction = STARTING_DIR;
	/**
	 * Speed of the game Character.
	 * Algebraically it is the natural number that multiplies the coordinate modifiers 
	 * established by the current direction of movement. 
	 * Serves to establish the new point following a character movement operation.
	 */
	private double speed;
	/**
	 * Game Character's Height
	 */
	protected int h;
	/**
	 * Game Character's width
	 */
	protected int w;
	 
	/**
	 * Coordinate modifiers to determine the solidity area of this Game Character.
	 * They are define and fix at the creation of the character.
	 */
	protected int ySolid1, ySolid2, xSolid1, xSolid2;
	/**
	 * Cartesian points representing the vertices of the rectangle which defines the area of ​​solidity of this game character.
	 */
	private Map<CartesianCoordinate,Degree> solidPoints = new HashMap<>();
	
	/**
	 * Coordinate modifiers to determine the center of the solidity rectangle of this game character.
	 */
	protected int xOffsetGravityCenter,yOffsetGravityCenter;

	/**
	 * Number of remaining lives
	 */
	protected int lives;
	/**
	 * Seconds left of invincibility. When a character is invincible he cannot die.
	 */
	protected int invincibility;
	
	/**
	 * The Constructor of Game Character.
	 * @param yPanel starting y panel
	 * @param xPanel starting x panel
	 * @param speed starting speed
	 * @param h fixed height
	 * @param w fixed width
	 * @param lives starting number of lives
	 */
	public GameCharacter(
			int yPanel, int xPanel, 
			double speed, 
			int h, int w,
			int lives
			) {
		this.ID = globalId++;
		this.yPanel = yPanel;
		this.xPanel = xPanel;
		this.speed = speed;
		this.lives = lives;
		this.setHW(h,w);

	}
	
	// Getters
	/**
	 * Getter of game character's identifier
	 * @return game character's identifier
	 */
	public int getID() {return ID;}
	/**
	 * Getter of game character's current direction
	 * @return current direction
	 */
	public Dir getDir() {return direction;}
	/**
	 * Getter of game character's speed
	 * @return game character's speed
	 */
	public int getSpeed() {return (int)speed;}
	/**
	 * Getter of game character's height
	 * @return game character's height
	 */
	public int getHeight() {return h;}
	/**
	 * Getter of game character's width
	 * @return game character's width
	 */
	public int getWidth() {return w;}
	/**
	 * Getter of game character's offset y gravity center to determine its gravity center point
	 * @return game character's offset y gravity center
	 */
	public int getOffGravityY() {return yOffsetGravityCenter;}
	/**
	 * Getter of game character's offset x gravity center to determine its gravity center point
	 * @return game character's offset x gravity center
	 */
	public int getOffGravityX() {return xOffsetGravityCenter;}
	/**
	 * Getter of game character's lives
	 * @return game character's lives
	 */
	public int getLives() {return lives;}
	/**
	 * Getter of game character's invincibility unit (seconds)
	 * @return game character's invincibility
	 */
	public int getInvincibility() {return invincibility;}
	
	/**
	 * Calculate Cartesian point representing gravity center of game character
	 * @return gravity center Cartesian Coordinate
	 */
	public CartesianCoordinate getGravityCoords() {
		return new CartesianCoordinate(
				yPanel+yOffsetGravityCenter,
				xPanel+xOffsetGravityCenter
				);
	}
	
	// Setters
	/**
	 * Setter of speed
	 * @param speed new speed
	 */
	public void setSpeed(int speed) {this.speed = speed;}
	/**
	 * Incrementer of speed by 0.5 
	 */
	public void incSpeed() {speed += 0.5;}
	/**
	 * Setter of current Direction.
	 * @param newDir new direction of movement
	 */
	public void setDir(Dir newDir) {
		direction = newDir;
	}
	/**
	 * Setter of lives
	 * @param lives new lives total
	 */
	public void setLives(int lives) {this.lives = lives;}

	/**
	 * Setter to determine Height, Width and Cartesian modifiers
	 * of this game character 
	 * @param h Game Character's Height
	 * @param w Game Character's Width
	 */
	public void setHW(int h, int w) {
		this.h = h;
		this.w = w;
		this.ySolid1 = (int)(h*3)/4;
		this.ySolid2 = (int) h;
		this.xSolid1 = (int)(w/5);
		this.xSolid2 = (int)(w*3)/4;
		this.xOffsetGravityCenter = xSolid1 + (xSolid2 - xSolid1)/2;
		this.yOffsetGravityCenter = ySolid1 + (ySolid2 - ySolid1)/2;
	}
	
	/**
	 * Try to reduce this game character's total lives value by one
	 * @return true if the reduction occurs
	 */
	public boolean decLives() {
		if(invincibility<=0) {
			lives--;
			return true;
		}
		return false;
	}
	/**
	 * Setters of game character's invincibility
	 * @param invincibility new game character's invincibility
	 */
	public void setInvincibility(int invincibility) {
		this.invincibility = invincibility;
	}
	/**
	 * Reduce game character's invincibility by one (second)
	 */
	public void decInvincibility() {invincibility--;}
	
	// ---------------
	@Override
	public boolean equals(Object o) {
		if (this==o) return true;
		return o!=null
			&& getClass().equals(o.getClass())
			&& this.ID == ((GameCharacter)o).getID();
//				&& getSquareCoords().getY() == (((GameCharacter)o).getSquareCoords().getY()) 
//				&& getSquareCoords().getX() == (((GameCharacter)o).getSquareCoords().getX());
	}
	
	@Override
	public int hashCode() {
		return 31 * ID;
	}
	
	@Override
	public int compareTo(GameCharacter o) {
		int order = getGravityCoords().compareTo(o.getGravityCoords());
		if(order==0) order = getID() - ((GameCharacter)o).getID();
		return order;
	}
	
	
	// --------------- MANAGE MOBILITY ----------------------------------------------
	/**
	 * Move this Game Character
	 * @param dir moving direction
	 * @param yourSpeed used speed
	 */
	public void move(Dir dir, int yourSpeed) {
		setDir(dir);
		switch(direction) {
		case UP: yPanel -= yourSpeed; break;
		case DOWN: yPanel += yourSpeed; break;
		case LEFT: xPanel -= yourSpeed; break;
		case RIGHT: xPanel += yourSpeed; break;
		case NONE : break;
		}
	}
	
	// Overloading
	/**
	 * Move this game character using its speed
	 * @param dir moving direction
	 */
	public void move(Dir dir) {
		this.move(dir,(int)this.speed);
	}
	
	/**
	 * Calculate the hypothetical value that y would have once a move operation is completed
	 * @param dir moving direction
	 * @param offsetY correction factor applied to y
	 * @return new y panel value
	 */
	protected int getYProjected(Dir dir, int offsetY) { // y projected by declared move
		int yP = yPanel + offsetY;
		switch(dir) {
		case UP : return yP-(int)speed;
		case DOWN : return yP + (int)speed;
		default : return yP;
		}
	}
	
	/**
	 * Calculate the hypothetical value that x would have once a move operation is completed
	 * @param dir moving direction
	 * @param offsetY correction factor applied to x
	 * @return new x panel value
	 */
	protected int getXProjected(Dir dir, int offsetX) { // x projected by declared move
		int xP = xPanel + offsetX;
		switch(dir) {
		case LEFT :  return xP - (int)speed;
		case RIGHT :  return xP + (int)speed;
		default :  return xP;
		}
	}
	
	/**
	 * Calculate the two projected solid points determined by the input direction.
	 * If these projections belong to an area of ​​solidity of the grid then the movement should not occur.
	 * @param dir input direction
	 * @return Collection of the two projected solid points
	 */
	public Map<CartesianCoordinate,Degree> getProjectedSolidPoints(Dir dir){
		solidPoints.clear();
//		List<CartesianCoordinate> solidPoints = new ArrayList<>();
		CartesianCoordinate nordOvest = new CartesianCoordinate(
				getYProjected(dir,ySolid1),getXProjected(dir,xSolid1));
		CartesianCoordinate nordEst = new CartesianCoordinate(
				getYProjected(dir,ySolid1),getXProjected(dir,xSolid2));
		CartesianCoordinate sudOvest = new CartesianCoordinate(
				getYProjected(dir,ySolid2),getXProjected(dir,xSolid1));
		CartesianCoordinate sudEst = new CartesianCoordinate(
				getYProjected(dir,ySolid2),getXProjected(dir,xSolid2));
		switch(dir) {
			case UP  -> {
				solidPoints.put(nordEst, Degree.NORD_EST);
				solidPoints.put(nordOvest, Degree.NORD_OVEST);
	//			solidPoints.add(nordOvest); 
	//			solidPoints.add(nordEst); 		
			}
	
			case DOWN -> {
				solidPoints.put(sudOvest, Degree.SUD_OVEST);
				solidPoints.put(sudEst, Degree.SUD_EST);
	//			solidPoints.add(sudOvest); 
	//			solidPoints.add(sudEst); break;
			}
	
			case RIGHT -> {
				solidPoints.put(nordEst, Degree.NORD_EST);
				solidPoints.put(sudEst, Degree.SUD_EST);
	//			solidPoints.add(nordEst); 
	//			solidPoints.add(sudEst); break;
			}
	
			case LEFT -> {
				solidPoints.put(nordOvest, Degree.NORD_OVEST);
				solidPoints.put(sudOvest, Degree.SUD_OVEST);
	//			solidPoints.add(nordOvest); 
	//			solidPoints.add(sudOvest); break;
			}
	
			default -> {}
		}
//			solidPoints.add(nordOvest);solidPoints.add(nordEst);
//			solidPoints.add(sudOvest);solidPoints.add(sudEst); break;
//		}
		return solidPoints;
	}
	
	/**
	 * Calculates the 4 points that are the vertices of the rectangle that represents the area of ​​solidity of the character 
	 * @return Set of 4 solidity points
	 */
	public Set<CartesianCoordinate> getActualSolidPoints(){
		Set<CartesianCoordinate> returnSet = new HashSet<>();
		CartesianCoordinate c00 = getLimitNordOvestRectCoord(); // nordOvest
		CartesianCoordinate c01 = new CartesianCoordinate(yPanel+ySolid1,xPanel+xSolid2); // nordEst
		CartesianCoordinate c10 = new CartesianCoordinate(yPanel+ySolid2,xPanel+xSolid1); // sudOvest
		CartesianCoordinate c11 = getLimitSudEstRectCoord(); // sudEst
		returnSet.add(c00); returnSet.add(c01);
		returnSet.add(c10); returnSet.add(c11);
		return returnSet;
	}
	
	/**
	 * Calculates the points which are the vertices of the rectangle 
	 * and halfway along the sides of this which represents the area of ​​solidity of the character
	 * @return Set of 8 solidity points
	 */
	public Set<CartesianCoordinate> getActualSolidPointsExtremePrecision(){
		Set<CartesianCoordinate> returnSet = new HashSet<>();
		
		// first Line
		CartesianCoordinate c000 = getLimitNordOvestRectCoord(); // nordOvest
		CartesianCoordinate c001 = 
				new CartesianCoordinate(yPanel+ySolid1,
										xPanel+xSolid1+(xSolid2-xSolid1)/2); // midNord
		CartesianCoordinate c010 = 
				new CartesianCoordinate(yPanel+ySolid1,
										xPanel+xSolid2); // nordEst
		
		// second Line
		CartesianCoordinate c011 = 
				new CartesianCoordinate(yPanel + ySolid1 + (ySolid2 - ySolid1)/2,
										xPanel+xSolid1);  // midOvest
		CartesianCoordinate cxxx = getGravityCoords();  // Gravity Center
		CartesianCoordinate c100 = 
				new CartesianCoordinate(yPanel+ySolid1 + (ySolid2 - ySolid1)/2,
										xPanel+xSolid2);  // midEst
		
		// third Line
		CartesianCoordinate c101 = 
				new CartesianCoordinate(yPanel+ySolid2,
										xPanel+xSolid1); // sudOvest
		CartesianCoordinate c110 = 
				new CartesianCoordinate(yPanel+ySolid2,
										xPanel+xSolid1+(xSolid2-xSolid1)/2);  // midSud
		CartesianCoordinate c111 = getLimitSudEstRectCoord(); // sudEst
		
		returnSet.add(c000); returnSet.add(c001);returnSet.add(c010);
		returnSet.add(c011); returnSet.add(cxxx);
		returnSet.add(c100); returnSet.add(c101); returnSet.add(c110);
		returnSet.add(c111);
		return returnSet;
	}
	
	/**
	 * Calculate the nord ovest solidity point
	 * @return nord ovest solidity point
	 */
	public CartesianCoordinate getLimitNordOvestRectCoord() {
		return new CartesianCoordinate(yPanel+ySolid1,xPanel+xSolid1);
	}
	
	/**
	 * Calculate the sud est solidity point
	 * @return sud est solidity point
	 */
	public CartesianCoordinate getLimitSudEstRectCoord() {
		return new CartesianCoordinate(yPanel+ySolid2,xPanel+xSolid2);
	}
	/**
	 * calculates two opposite vertices of the solidity rectangle:
	 * the nord ovest one, the sud est one.
	 * @return Set of 2 solidity points.
	 */
	public Set<CartesianCoordinate> getActualSolidPointsLimits(){
		Set<CartesianCoordinate> returnSet = new HashSet<>();
		returnSet.add(getLimitNordOvestRectCoord());
		returnSet.add(getLimitSudEstRectCoord());
		return returnSet;
	}
	

}
