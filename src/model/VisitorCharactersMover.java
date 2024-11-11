package model;

import java.util.Map;

import model.GameCharacter.Degree;

/**
 * This Visitor encapsulates all the methods that determine Game Character movement.
 * In doing so, it makes the game grid communicate with the game panel.
 * Furthermore, it defines during its construction the limits of the panel 
 * in which the game grid is located and in which the game characters can move. 
 * These limits are the ones he considers to make the movements.
 * The View will actually set these values ​​when the instance of this object is created in the Controller.
 */
public class VisitorCharactersMover implements Visitor, MyGridFormat {
	/**
	 * Minimum value for the x coordinate of a point belonging to the panel.
	 * Represents the x coordinate value that the points belonging to the left edge of the panel have in common.
	 * Nb: leftX <= rightX
	 */
	private int leftX;
	/**
	 * Maximum value for the x coordinate of a point belonging to the panel.
	 * Represents the x coordinate value that the points belonging to the right edge of the panel have in common.
	 * Nb: leftX <= rightX
	 */
	private int rightX;
	/**
	 * Minimum value for the y coordinate of a point belonging to the panel.
	 * Represents the y coordinate value that the points belonging to the superior edge of the panel have in common.
	 * Nb: upperY <= lowerY
	 */
	private int upperY;
	/**
	 * Maximum value for the y coordinate of a point belonging to the panel.
	 * Represents the y coordinate value that the points belonging to the inferior edge of the panel have in common.
	 * Nb: upperY <= lowerY
	 */
	private int lowerY;
	/**
	 * Side of the square that identifies a cell of the game grid 
	 * (which is under the jurisdiction of a Tessera).
	 */
	private int tesseraeUnit;
	/**
	 * Relative offset of the panel origin point for the y coordinate
	 */
	private int offsetY;
	/**
	 * Relative offset of the panel origin point for the x coordinate
	 */
	private int offsetX;
	/**
	 * Visitor Characters Mover Constructor that also define Game Panel Border Limits.
	 * @param leftX minimum value for the x coordinate of a point belonging to the panel.
	 * @param rightX Maximum value for the x coordinate of a point belonging to the panel.
	 * @param upperY minimum value for the y coordinate of a point belonging to the panel.
	 * @param lowerY Maximum value for the y coordinate of a point belonging to the panel.
	 * @param tesseraeUnit Side of the square that identifies a cell of the game grid 
	 * @param offsetY Relative offset of the panel origin point for the y coordinate
	 * @param offsetX Relative offset of the panel origin point for the x coordinate
	 */
	public VisitorCharactersMover(
			int leftX, int rightX, int upperY, int lowerY, 
			int tesseraeUnit, int offsetY, int offsetX) {

		this.leftX = leftX;
		this.rightX = rightX;
		this.upperY = upperY;
		this.lowerY = lowerY;
		this.tesseraeUnit = tesseraeUnit;
		this.offsetY = offsetY;
		this.offsetX = offsetX;
	}

	/**
	 * Check that the projection points are inside the game panel:
	 * in this way it verifies if a collision occurs between a game character and the game borders.
	 * @param projectedPoints examined projected points
	 * @return True if the game character giving these points is within the borders of Game
	 */
	private boolean withinTheBorders(
			Map<CartesianCoordinate,Degree> projectedPoints) {

		for(CartesianCoordinate p : projectedPoints.keySet()) {
			if(!(p.getX() <= rightX && p.getX() >= leftX 
					&& p.getY() <= lowerY && p.getY() >= upperY)) {
				
//				System.out.println(p.getX() +"<="+ upperX);
//				System.out.println(p.getX() +">="+ lowerX);
//				System.out.println(p.getY() +"<="+ upperX);
//				System.out.println(p.getY() +">="+ lowerY);
				return false;
			}

		}
		return true;
		
	}
	
	/**
	 * Convert a CartesianCoordinate point's coordinates 
	 * from panel format to grid format
	 * @param c Cartesian point to convert
	 */
	public void cartesianGridConverter(CartesianCoordinate c) {
		int yS = (c.getY() - offsetY) / tesseraeUnit;
		int xS = (c.getX() - offsetX) / tesseraeUnit;
		if(yS<0) yS = 0;
		if(xS<0) xS = 0;
		if(yS>=ROWS) yS = ROWS -1;
		if(xS>=COLUMNS) xS = COLUMNS-1;
		c.setY(yS);
		c.setX(xS);
	}
	
	/**
	 * Removes points that belong to areas over which movement is possible. 
	 * Only the points that belong to solid areas will remain.
	 * @param map Map of the points to filter
	 * @return Filtered map
	 */
	public Map<CartesianCoordinate,Degree> keepOnlySolidSquares(
			Map<CartesianCoordinate,Degree> map) { // Collision Check Box
		
		map.entrySet()
				.removeIf( 
					entry -> {
							CartesianCoordinate c = entry.getKey();
							cartesianGridConverter(c);
							if( GameModel.getInstance()
									.getActualLevel()
									.getSquare(c.getY(),c.getX())
									.isSolid() ) return false;
							return true; // if the coordinate refers to a non solid square then I remove it
						});
		
		return map;

	}
	
	/**
	 * Standard algorithm for Game Character movement.
	 * @param gc game character to move
	 * @param dir direction of movement
	 * @return number of collisions with solid areas
	 */
	private int makeYourMove(GameCharacter gc, Dir dir) {
		Map<CartesianCoordinate,Degree> projectedPoints = gc.getProjectedSolidPoints(dir);
		
		if(! withinTheBorders(projectedPoints)) {
			gc.move(dir,0);
			return -1;
		}
		
		Map<CartesianCoordinate,Degree> collisionPoints 
			= keepOnlySolidSquares(projectedPoints);
		
		int size = collisionPoints.size();
	
		if(size == 0) { // no collision
			
			gc.move(dir);
			
		} else if(size == 1) { /* only one point collides then Automatic Correction.
								  This way I can get some Assisted Walking */
			if(dir==Dir.UP) {
				if(collisionPoints.containsValue(Degree.NORD_OVEST)) {
					gc.move(Dir.RIGHT);
				} else { // then collisionPoints contains NORD_EST
					gc.move(Dir.LEFT);
				}
			} else if(dir==Dir.LEFT) {
				if(collisionPoints.containsValue(Degree.NORD_OVEST)) {
					gc.move(Dir.DOWN);
				} else { // then collisionPoints contains SUD_OVEST
					gc.move(Dir.UP);
				}
			} else if(dir==Dir.DOWN) {
				if(collisionPoints.containsValue(Degree.SUD_OVEST)) {
					gc.move(Dir.RIGHT);
				} else { // then collisionPoints contains SUD_EST
					gc.move(Dir.LEFT);
				}
			} else if(dir==Dir.RIGHT) {
				if(collisionPoints.containsValue(Degree.NORD_EST)) {
					gc.move(Dir.DOWN);
				} else { // then collisionPoints contains SUD_EST
					gc.move(Dir.UP);
				}
			}
			
		} else if(size==2){ // size == 2 both two point collides then it must be stop
			
			gc.move(dir,0);
			
		}
		
		return size;
	}
	
	/**
	 * Altered version of algorithm for Game Character movement.
	 * It doesn't verify collisions because these game character 
	 * can also pass through areas of solidity.
	 * @param gc game character to move
	 * @param dir direction of movement
	 */
	private void makeYourMoveRunnerVersion(GameCharacter gc, Dir dir) {
		Map<CartesianCoordinate,Degree> projectedPoints = gc.getProjectedSolidPoints(dir);
		
		if(! withinTheBorders(projectedPoints)) {
			gc.move(dir,0);
		} else {
			gc.move(dir);
		}	

	}
	
	/**
	 * Visit method implemented to move a PlayerCharacter object.
	 * New PlayerCharacter's coordinates are transmitted to the Game Model.
	 */
	@Override
	public void visit(PlayerCharacter pc) {
		Dir playerDir = pc.getDir();
		if(playerDir != Dir.NONE) {
			makeYourMove(pc, playerDir);
		}
		
		GameModel.getInstance().transmitPcCoords();
	}
	
	/**
	 * Visit method implemented to move a NonPlayableCharacter object.
	 */
	@Override
	public void visit(NonPlayableCharacter npc) {
		int size = makeYourMove(npc,npc.getDir());
		if(size==2) npc.setFindAStop(true);
	}
	/**
	 * Visit method implemented to move a Runner object.
	 * The object must be also a Game Character because movement algorithm will be applied.
	 */
	@Override
	public void visit(Runner runner) {
		if(runner instanceof GameCharacter) {
			GameCharacter npc = (GameCharacter)runner;
			makeYourMoveRunnerVersion(npc, npc.getDir());
		}
	}
	
	
}
