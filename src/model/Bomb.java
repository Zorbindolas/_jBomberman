package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Fire.FlameSpread;

/**
 * Class of objects thought to be the bombs of the game.
 */
public abstract class Bomb extends Prop implements MyGridFormat{
	/**
	 * Type of implemented bombs
	 */
	public static enum Type {
		/**
		 * Bomb STANDARD - Type
		 */
		STANDARD, 
		/**
		 * Bomb FAST - Type
		 */
		FAST, 
		/**
		 * Bomb REMOTE - Type
		 */
		REMOTE;
	}
	/**
	 * Length of fire arm of the bombs from the epicenter of the explosion
	 */
	private int area;
	/**
	 * necessary steps that must pass to trigger the bomb
	 */
	private int triggerTime;
	/**
	 * Register of coordinates on the game grid that need to be destroyed by bomb explosion.
	 * These are  the coordinates of the Tessera tiles that must be emptied if they envelop destructible objects.
	 */
	private Set<CartesianCoordinate> squareToBeDestroyed = new HashSet<>();
	/**
	 * Register of coordinates on the game grid in which fires spawn
	 */
	private Map<CartesianCoordinate, Fire> firesToBeCreated = new HashMap<>();
	
	/**
	 * Constructor of a bomb by the actual PC saved in the Game Model
	 * @param yGrid y coordinate of this bomb
	 * @param xGrid x coordinate of this bomb
	 */
	public Bomb(int yGrid, int xGrid) {
		super(yGrid, xGrid, true, false);
		this.area = (GameModel.getInstance().getActualPC()).getEffectArea();
		this.triggerTime = GameModel.getInstance().getActualPC().getTriggerTime();
	}
	/**
	 * Getter of the area established by this bomb
	 * @return area of thi bomb
	 */
	public int getArea() {return area;}
	/**
	 * Getter of the trigger time of this bomb
	 * @return trigger time of this bomb
	 */
	public int getTriggerTime() {return triggerTime;}
	/**
	 * Transitory forms of state that follow one another before the bomb can actually explode
	 * @return state forms before explosion
	 */
	public abstract int[] getForms();
	/**
	 * This override is necessary so that the player can leave the square
	 *  where he placed this bomb but cannot return there as long as it is present
	 */
	@Override
	public boolean isSolid() {
		
		if(GameModel.getInstance().getActualPC().getSquareCoords().equals(this.getSquareCoords()))
			return false;
		
		else return true;
	}
	
	/**
	 * Getter of coordinates to be destroyed
	 * @return coordinates to be destroyed
	 */
	public Set<CartesianCoordinate> getSquareToBeDestroyed(){
		return squareToBeDestroyed ;
	}

	/**
	 * Getters of coordinates of the tiles in which the fires must be inserted
	 * @return coordinates in which insert fires
	 */
	public Map<CartesianCoordinate, Fire> getFiresToBeCreated(){
		return firesToBeCreated;
	}
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public void setToBeFired() {
//		List<Object> returnList = ((Bomb)component).explode();
	/**
	 * The bomb objects that are reported to be destroyed alter 
	 * the normal management of the destruction of the objects 
	 * they affect with the explosion. 
	 * For this reason this override is necessary.
	 * Before being flagged as to be destroyed, 
	 * the bomb possibly adds other objects to these markers.
	 * The explosion of a bomb notifies the game model of the objects to be bundled 
	 * with certain tiles and other objects whose trespasser method must 
	 * be activated (which will trigger their destruction).
	 */
	@Override
	public void trespasser(LevelAbstract level) { 
		
		explode(level);
		
		Map<CartesianCoordinate,Fire> virginMap = getFiresToBeCreated();
//			= (HashMap<CartesianCoordinate, Fire>) returnList.get(1);
		Map<CartesianCoordinate,Prop> bundlingMap = new HashMap<CartesianCoordinate, Prop>();
		for(CartesianCoordinate c : virginMap.keySet()) {
			Fire fire = virginMap.get(c);
			bundlingMap.put(c, fire);
		}
		level.bundling(bundlingMap);

		level.setToBeTrespasser(getSquareToBeDestroyed());
//		level.setToBeTrespasser(
//				((Bomb)component).getSquareToBeDestroyed());
//		GameModel.getInstance().setToBeTrespasser(
//				((Set<CartesianCoordinate>)returnList.get(0)));
	}
	
//	public List<Object> explode() {
	/**
	 * This method determines the coordinates of the Tessera whose tiles 
	 * are to be emptied and the coordinates of the Tessera into which to insert fires objects of a particular category
	 * @param level  Level from which the game grid to be altered is extrapolated
	 */
	public void explode(LevelAbstract level) {
		// INITIALISE
		Set<CartesianCoordinate> squareToBeDestroyed = new HashSet<>();
		Map<CartesianCoordinate, Fire> firesToBeCreated = new HashMap<>();
		
		// CREATE FIRES AND DEFINE COORDS TO DESTROY
		CartesianCoordinate cE = this.getSquareCoords();
		// Center Fire
		firesToBeCreated.put(cE, 
				new Fire(
						cE.getY(),cE.getX(),
						Dir.NONE,FlameSpread.END,
						GameModel.getInstance().getPowerUpMediator()
						));
		
		// starting recursion of spreadingFlames
		spreadingFlames(level,Dir.UP,cE,area,firesToBeCreated,squareToBeDestroyed);
		spreadingFlames(level,Dir.DOWN,cE,area,firesToBeCreated,squareToBeDestroyed);
		spreadingFlames(level,Dir.RIGHT,cE,area,firesToBeCreated,squareToBeDestroyed);
		spreadingFlames(level,Dir.LEFT,cE,area,firesToBeCreated,squareToBeDestroyed);
		
		// RETURN
//		List<Object> returnList = new ArrayList<>();
		
		this.squareToBeDestroyed = squareToBeDestroyed;
		this.firesToBeCreated = firesToBeCreated;
		return;
//		returnList.add(squareToBeDestroyed); // first object
//		returnList.add(firesToBeCreated); // second object
//		return returnList;
		
	}
	
	/**
	 * Recursive search algorithm with four base cases that determines the coordinates 
	 * with Tesserae to empty and the coordinates in which the fires appear
	 * @param level level that offers the game grid and it's solidity parts
	 * @param dir various blast directions taken by the recursive calls
	 * @param c Cartesian point studied in a specific recursive call
	 * @param recArea Coordinates left to examine moving away from the epicenter of the explosion
	 * @param firesToBeCreated  Map of fires to be created to alter in the recursion
	 * @param squareToBeDestroyed Set of square to be destroyed to alter in the recursion
	 */
	private void spreadingFlames(
			LevelAbstract level,
			Dir dir, 
			CartesianCoordinate c,
			int recArea,
			Map<CartesianCoordinate, Fire> firesToBeCreated,
			Set<CartesianCoordinate> squareToBeDestroyed) {
		
		CartesianCoordinate dirSquare = dir.getCartesianModifier();
		CartesianCoordinate newSquare = new CartesianCoordinate(
				c.getY() + dirSquare.getY(),
				c.getX() + dirSquare.getX()
				);
		if(recArea<0) return; // first base case 
		
		if(newSquare.getY()<0 || newSquare.getY()>=ROWS ||
				newSquare.getX()<0 || newSquare.getX()>=COLUMNS) {
			return; // second base case
		}
		
		if(level.canYouReleaseHere(newSquare)) {
			if(recArea==0) {
				firesToBeCreated.put(newSquare, 
						new Fire(
								newSquare.getY(),newSquare.getX(),
								dir,FlameSpread.END,
								GameModel.getInstance().getPowerUpMediator()
								));
				return;  // third base case
			} else { // recArea > 0
				firesToBeCreated.put(newSquare, 
						new Fire(
								newSquare.getY(),newSquare.getX(),
								dir,FlameSpread.ARM,
								GameModel.getInstance().getPowerUpMediator()
								));
				recArea--;
				spreadingFlames(level,dir,newSquare,recArea,firesToBeCreated,squareToBeDestroyed);
			}
		} else {
			squareToBeDestroyed.add(newSquare);
			return; // fourth base case
		}
	}

}
