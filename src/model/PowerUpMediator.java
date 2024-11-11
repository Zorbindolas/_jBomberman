package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.PowerUp.Power;

/**
 * PowerUp are regulated by their mediator. They aren't wrapped to Tesserae.
 */
public abstract class PowerUpMediator {
	/**
	 * Messages can be send to the mediator
	 */
	public static enum Message {
		/**
		 * ADDING POSITION MESSAGE
		 */
		ADD_POS, 
		/**
		 * DELETE POSITION MESSAGE
		 */
		DELETE_POS;
	}
	/**
	 * Coordinate in which there is an opportunity to drop a powerup
	 */
	protected List<CartesianCoordinate> spawnableCoords = new ArrayList<>();
	/**
	 * Powerup dropped an actually present in the session
	 */
	protected Map<CartesianCoordinate,Powerupper> droppedPowerUps = new HashMap<>();
	/**
	 * reference to npcs mediator
	 */
	protected NpcMediator npcMediator;
	/**
	 * static random field
	 */
	protected static Random random = new Random();
	/**
	 * Powerup mediator Constructor
	 * @param npcMediator reference to its npc mediator
	 */
	protected PowerUpMediator(NpcMediator npcMediator) {
		this.npcMediator = npcMediator;
	}
	/**
	 * Getter of droppedPowerUps (powerUp that can be collect by the player)
	 * @return this droppedPowerUps
	 */
	public Map<CartesianCoordinate,Powerupper> getDroppedPowerUps(){
		return droppedPowerUps;
	}
	/**
	 * Method invoked in classes that use this mediator to interact with the powerups currently present in the game session.
	 * @param p Cartesian reference point 
	 * @param m message can be: add a possible spawn or destroy a powerup 
	 */
	public void sendMessage(Prop p, Message m) {
		CartesianCoordinate c = p.getSquareCoords();
		switch(m) {
			case ADD_POS -> {possibleSpawn(c);}
			case DELETE_POS -> {destroyPowerUp(c);}
			default -> {}
		}
	}
	
	/**
	 * Adds the Cartesian point to valid spawns if random conditions are favorable.
	 * In other words this method is the context in which we check whether 
	 * a potential spawn point becomes effective or not.
	 * @param squareCoords Cartesian point to be validated
	 */
	private void possibleSpawn(CartesianCoordinate squareCoords) {
		int y = squareCoords.getY();
		int x = squareCoords.getX();
		if(
				! GameModel.getInstance().getActualLevel().getGrid()[y][x].isExit()
				&&
				random.nextInt(0,10) > GameModel.getInstance().getActualPC().getLuck()
				) {  // standard 1/10 probability to generate compatible spawn coords
			
			spawnableCoords.add(squareCoords);
			
		}
	}
	
	/**
	 * Delete the powerup present at the indicated coordinates
	 * @param squareCoords Indicated Cartesian coordinates 
	 */
	private void destroyPowerUp(CartesianCoordinate squareCoords) {
		if(GameModel.getInstance().isDestroyablePowerUp()) {
			droppedPowerUps.remove(squareCoords);
		}
	}
	
	/**
	 * Generate powerups at the coordinates collected so far 
	 * into the actual level's grid and then empty the collection of these coordinates.
	 * @param grid Actual level's grid
	 */
	public void setPowerUps(TesseraAbstract[][] grid) {
		
		spawnableCoords.stream()
					   .filter(sc -> grid[sc.getY()][sc.getX()].getBundle()==null)
					   .forEach(sc -> droppedPowerUps.put(sc, generateCasualPowerUp(sc)));
		
		spawnableCoords.clear();
		
		// Without stream is:
//		Iterator<CartesianCoordinate> it = spawnableCoords.iterator();
//		while(it.hasNext()) {
//			CartesianCoordinate c = it.next();
//			int y = c.getY();
//			int x = c.getX();
//			if(grid[y][x].getBundle()==null) {
//				droppedPowerUps.put(
//						c,
//						generateCasualPowerUp(c)
//						);
//				it.remove();
//			}
//		}
	}

	/**
	 * Create a casual power up
	 * @param c Coordinates of the newest powerup
	 * @return concrete powerup
	 */
	protected Powerupper generateCasualPowerUp(CartesianCoordinate c) {
		Power label = generateCasualLabel();
		return PowerUp.newPowerUp(label,c.getY(),c.getX(),0,0,npcMediator);
	}
	
	/**
	 * Randomly chooses a powerup label
	 * @return chosen powerup label
	 */
	protected Power generateCasualLabel() {
		Power label;
		int r = random.nextInt(0,21);
		switch(r) {
			case 0,1,2,3,4 -> {label= Power.EXPLOSION_EXPANDER;}
			case 5,6,7,8,9 -> {label= Power.EXTRA_BOMB;}
			case 10,11,12,13 -> {label= Power.EXTRA_HEART;}
			case 14,15,16,17 -> {label= Power.ACCELERATOR;}
			case 18,19,20 -> {
				r = random.nextInt(0,5);
				switch(r) {
					case 0 -> {label= Power.TABULA_RASA;}
					case 1 -> {label= Power.FAST_BOMB;}
					case 2 -> {label= Power.INDESTRUCTIBLE_ARMOR;}
					case 3 -> {label = Power.FOUR_LEAF_CLOVER;}
					case 4 -> {{label= Power.TIMER_RESTORER;}}
					default -> {label= Power.FAST_BOMB;}
					}
				}
			default -> {label = Power.EXTRA_HEART;}
		}
		return label;
	}
	
	/**
	 * Checks if there is a power up at the player's coordinates and if so, 
	 * applies it and releases its gravedigger
	 * @param pc player character
	 */
	public void collectsPower(PlayerCharacter pc) {
		Prop powerUp = (Prop) droppedPowerUps.remove(pc.getSquareCoords());
		if(powerUp!=null) {
			((Powerupper)powerUp).applyPowerUp();
			incScoreByCollecting(powerUp);
		}
	}
	
	/**
	 * Generates the corresponding gravedigger and sends it to the Game Model 
	 * @param powerUp powerup that produces the gravedigger 
	 */
	protected void incScoreByCollecting(Prop powerUp) {
		GameModel.getInstance().incScore( 
				Gravedigger.setTomb(
						((Powerupper)powerUp).getScoreValue(), 
						powerUp.getYPanel(), 
						powerUp.getXPanel())
				);
	}
	
	/**
	 * Clear Collections of this Mediator
	 */
	public void reset() {
		spawnableCoords.clear();
		droppedPowerUps.clear();
	}
	
}
