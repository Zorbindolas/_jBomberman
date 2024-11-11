package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Mediator that encapsulates dependencies of NPC in every game level.
 * It manages their correct functionality.
 * Set its visitor before use.
 */
public abstract class NpcMediator implements MyGridFormat{
	/**
	 * Current npcs in the mediator
	 */
	protected Set<NonPlayableCharacter> npcs = new TreeSet<>();
	/**
	 * Npcs to add to the mediator
	 */
	private List<NonPlayableCharacter> addedNpcs = new ArrayList<>();
	/**
	 * Set of the bomb eated by enemyBombEater. It is required for correct functionality of remote bombs.
	 */
	private Set<Bomb> bombsEated = new HashSet<>();
	/**
	 * Visitor for visiting npcs (movement functionality)
	 */
	private VisitorCharactersMover npcVisitor;
	/**
	 * Boolean to check if player collides with at least one npc.
	 */
	protected boolean collidingThePlayer;
	/**
	 * Boolean to take off one life from every current npcs
	 */
	private boolean killEveryNpcOneTime;
	
	/**
	 * Default construtor
	 */
	public NpcMediator() {}
	/**
	 * Getter of collidingThePlayer
	 * @return actual collidingThePlayer
	 */
	public boolean isCollidingThePlayer() {return collidingThePlayer;}
	/**
	 * Setter of collidingThePlayer
	 * @param collidingThePlayer new collidingThePlayer
	 */
	public void setCollidingThePlayer(boolean collidingThePlayer) {
		this.collidingThePlayer = collidingThePlayer;
	}
	
	// Methods to set the Mediator
	/**
	 * Setter of Mediator's Visitor
	 * @param npcVisitor visitor to set in this mediator
	 */
	public void setVisitor(VisitorCharactersMover npcVisitor) {
		this.npcVisitor=npcVisitor;
	}
	/**
	 * It is the only way to add a npc during game session
	 * @param npc npc to add
	 */
	public void add(NonPlayableCharacter npc) {
		addedNpcs.add(npc);
	}
	/**
	 * Add an EnemyPyro to npcs (special effect)
	 * @param cGravity spawn point
	 * @param dirs dirs for EnemyPyro
	 * @param h EnemyPyro's Height
	 * @param w EnemyPyro's Width
	 * @param speed EnemyPyro's speed
	 * @param npcMediator mediator to add to EnemyPyro
	 */
	public void addPyro(
			CartesianCoordinate cGravity, List<Dir> dirs,int h, int w,
			int speed, NpcMediator npcMediator) {
		NonPlayableCharacter pyro = getPyro(cGravity,dirs,h,w,speed,npcMediator);
		add(pyro);
	}
	/**
	 * Generate a EnemyPyro instance
	 * @param cGravity spawn point
	 * @param dirs dirs for EnemyPyro
	 * @param h EnemyPyro's Height
	 * @param w EnemyPyro's Width
	 * @param speed EnemyPyro's speed
	 * @param npcMediator mediator to add to EnemyPyro
	 * @return a concrete EnemyPyro
	 */
	protected NonPlayableCharacter getPyro(
			CartesianCoordinate cGravity, List<Dir> dirs,
			int h, int w, int speed, NpcMediator npcMediator) {
		EnemyPyro pyro = new EnemyPyro(
				0,0,speed,
				h,
				w,
				1,false,0,
				dirs,
				npcMediator
				);
		pyro.setYPanel(cGravity.getY());
		pyro.setXPanel(cGravity.getX());
		return pyro;
	}
	/**
	 * Add multiple npc to this mediator
	 * @param levelSet npcs starter set of the level
	 */
	public void add(Set<NonPlayableCharacter> levelSet) {
		npcs = new TreeSet<>(levelSet); // sets aren't bonded
	}
	/**
	 * Clear all npcs present in this mediator
	 */
	public void reset() {
		npcs.clear();
	}
	/**
	 * Load npcs from a level's starting npcs set 
	 * @param levelSet npcs starter set of the level
	 */
	public void load(Set<NonPlayableCharacter> levelSet) { // use it in the StateStarter
		npcs.clear();
		npcs = new TreeSet<>(levelSet);
	}
	
	// Receive message
	/**
	 * Apply resonance of a Npc to a player if they collide togheter
	 * @param pc the player character
	 */
	public void sendPosition(PlayerCharacter pc) {
		npcs.stream()
			.filter(x -> 
				(x.isHostile())&& 
				checkPCPreciseCollisionWithNPC(pc,x) //(x.getSquareCoords().equals(c)))
				)
			.findFirst()
			.ifPresent(x-> {
				x.resonanceOnPc(pc);
				collidingThePlayer = true;
			}); // resonance from first npc founded in the same position of the pc
	}
	/**
	 * Check if player character's solid area overlaps with solid area of npc
	 * @param pc player character
	 * @param npc npc to check
	 * @return true if collision happens
	 */
	private boolean checkPCPreciseCollisionWithNPC(
			PlayerCharacter pc, NonPlayableCharacter npc) {
		
		CartesianCoordinate nordOvest = npc.getLimitNordOvestRectCoord();
		CartesianCoordinate sudEst = npc.getLimitSudEstRectCoord();
		
		for(CartesianCoordinate c : pc.getActualSolidPoints()) {
			
			if( 
					nordOvest.getX()<=c.getX() && nordOvest.getY()<=c.getY() 
					&& sudEst.getX()>=c.getX() && sudEst.getY()>=c.getY()
					) {
				return true;
			}
			
		}
		
		return false;
	}
	
	// Methods to manage npcs
	/**
	 * Adapts the npcs to the passing of a second
	 */
	public void oneSecondOfActivityPassed() {
		npcs.forEach(x->{
			x.decActivityTimerByOne();
			x.setActionReady(true);
			if(x.getInvincibility()>0) x.decInvincibility();
		});
	}
	/**
	 * Redefine behavior of npcs which have run out their activity timer
	 * @param pc
	 */
	private void reEstablishBehaviors(PlayerCharacter pc) {
		npcs.stream()
			.filter(x-> (x.getActivityTimer()<=0))
			.forEach(x->{
				x.setFindAStop(false);
				x.setActivityTimerRandomly();
				if(x instanceof Hound) {
					((Hound)x).updateDirModders(pcHound(x,pc));
					x.setCasualBehaviour();
				} 
				else {
					x.setDir(getCoerentDir(x));
					x.setCasualBehaviour();
				}
				
			});
		for(int i=0;i<bombsEated.size();i++) {
			pc.reloadAmmunition();
			pc.remoteBombEated();
		}
		bombsEated.clear();
	}
	
	// ------------------ WALK WITH VISITOR ---------------------------
	/**
	 * Visit npc with Visitor Mover 
	 * @param npc npc to visit
	 */
	public void walksWithMe(NonPlayableCharacter npc) {
		if(npc instanceof Runner) {
			npcVisitor.visit ((Runner)npc);
		} else {
			npcVisitor.visit(npc);
		}
	}
	
	// ------------------ COERENT MOVEMENT ----------------------------
	/**
	 * decides the direction the character will take
	 * @param npc npc to move
	 * @return direction Dir to use
	 */
	public Dir getCoerentDir(NonPlayableCharacter npc) {
		Set<Dir> dirs = new HashSet<>(Arrays.asList(Dir.UP,Dir.DOWN,Dir.LEFT,Dir.RIGHT));
		TesseraAbstract[][] grid = GameModel.getInstance().getActualLevel().getGrid();
		CartesianCoordinate c = npc.getSquareCoords();
		int y = c.getY();
		int x = c.getX();
		
		boolean firstRow, lastRow, firstCol, lastCol;
		// Check no-borders coords :
		// ... y:
		lastRow = false;
		if(y==0) {
			dirs.remove(Dir.UP);
			firstRow = true;
		} else if(y==(grid.length-1)) {
			dirs.remove(Dir.DOWN);
			firstRow = false;
			lastRow = true;
		} else {
			firstRow = false;
		}
		// .. x:
		lastCol = false;
		if(x==0) {
			dirs.remove(Dir.LEFT);
			firstCol = true;
		} else if(x==(grid[0].length-1)) {
			dirs.remove(Dir.RIGHT);
			firstCol = false;
			lastCol = true;
		} else {
			firstCol = false;
		}
		
		// Check free near tesserae
		/* Have (A && B), A is used to avoid ArrayIndexOutOfBoundsException
		 * because && is short circuited*/
		if(npc.getNpcType()==NpcType.RUNNER) {
			// it can pass over obstacles
		} else if(npc.getNpcType()==NpcType.BOMB_EATER) {
			// ... up-tessera:
			if(		!firstRow 
					&& grid[y-1][x].getBundle()!=null // if null then short circuiting with false, else...
					&& !(grid[y-1][x].getBundle() instanceof Bomb) ){ // check if it's a bomb
				dirs.remove(Dir.UP);
			}
			// ... down_tessera:
			if(		!lastRow && 
					grid[y+1][x].getBundle()!=null
					&& !(grid[y+1][x].getBundle() instanceof Bomb) ){
				dirs.remove(Dir.DOWN);
			}
			// ... left-tessera:
			if(!firstCol && 
					grid[y][x-1].getBundle()!=null
					&& !(grid[y][x-1].getBundle() instanceof Bomb) ){
				dirs.remove(Dir.LEFT);
			}
			// ... right-tessera:
			if(!lastCol && 
					grid[y][x+1].getBundle()!=null
					&& !(grid[y][x+1].getBundle() instanceof Bomb) ){
				dirs.remove(Dir.RIGHT);
			}
		} else {
			// ... up-tessera:
			if(!firstRow && grid[y-1][x].getBundle()!=null) {dirs.remove(Dir.UP);}
			// ... down_tessera:
			if(!lastRow && grid[y+1][x].getBundle()!=null) {dirs.remove(Dir.DOWN);}
			// ... left-tessera:
			if(!firstCol && grid[y][x-1].getBundle()!=null) {dirs.remove(Dir.LEFT);}
			// ... right-tessera:
			if(!lastCol && grid[y][x+1].getBundle()!=null) {dirs.remove(Dir.RIGHT);}
		}

		int size = dirs.size();
		if(size<=0) return Dir.NONE;
		
	    Dir dir = dirs.stream()
	    		.skip(new Random().nextInt(size))
	    		.findFirst()
	    		.orElse(Dir.NONE);
	    
	    if(npc.getNpcType()== NpcType.BOMB_EATER) { // eat the bomb
	    	switch(dir) {
		    	case UP -> {
		    		if(grid[y-1][x].getBundle() instanceof Bomb) {
		    			bombsEated.add((Bomb) grid[y-1][x].getBundle());
		    		}
		    		grid[y-1][x].setBundle(null);
		    	}
		    	case DOWN -> {
		    		if(grid[y+1][x].getBundle() instanceof Bomb) {
		    			bombsEated.add((Bomb) grid[y+1][x].getBundle());
		    		}
		    		grid[y+1][x].setBundle(null);
		    	}
		    	case LEFT -> {
		    		if(grid[y][x-1].getBundle() instanceof Bomb) {
		    			bombsEated.add((Bomb) grid[y][x-1].getBundle());
		    		}
		    		grid[y][x-1].setBundle(null);
		    	}
		    	case RIGHT -> {
		    		if(grid[y][x+1].getBundle() instanceof Bomb) {
		    			bombsEated.add((Bomb) grid[y][x+1].getBundle());
		    		}
		    		grid[y][x+1].setBundle(null);
		    		
		    	}
		    	default -> {}
	    	}
	    }
	    
	    return dir;

	}
	
	/**
	 * Methods used by npc that chasing the player
	 * @param npc pusuer npc
	 * @param pc player character
	 * @return List with 2 Dir
	 */
	private List<Dir> pcHound(NonPlayableCharacter npc,PlayerCharacter pc) {
		List<Dir> list = new ArrayList<>();
		CartesianCoordinate cHound = npc.getGravityCoords();
		CartesianCoordinate cPc = pc.getGravityCoords();
		if(cHound.getY()>cPc.getY()) {
			list.add(Dir.UP);
		} else {
			list.add(Dir.DOWN);
		}
		if(cHound.getX()>cPc.getX()) {
			list.add(Dir.LEFT);
		} else {
			list.add(Dir.RIGHT);
		}
		return list;
	}
	
	// -----------------------------------------------------------------
	/**
	 * Every npcs do their doYourThing methods
	 */
	protected void commitments() {
		npcs.forEach(x-> x.doYourThing());
	}
	/**
	 * Try to kill npcs that meet certain conditions
	 * @param grid level's grid
	 * @param vcm movement visitor 
	 */
	private void tryToKillThem(
			TesseraAbstract[][] grid, VisitorCharactersMover vcm) {
		
		Iterator<NonPlayableCharacter> it = npcs.iterator();
		while(it.hasNext()) {
			
			NonPlayableCharacter npc = it.next();

			if(npc instanceof Boss) {
				if(isBossOnLethal(grid,npc,vcm)) {
					if(killOnce(npc)) {
						it.remove();
					}
				}
				
			} else { // Not a Boss
				CartesianCoordinate c = npc.getSquareCoords();
				int yNpc = c.getY();
				int xNpc = c.getX();
				if( (yNpc<0 || yNpc>=ROWS || xNpc<0 || xNpc>=COLUMNS)) {
					it.remove();
				} else if( onLethalSquare(grid,npc) ) {
					if(killOnce(npc)) {
						it.remove();
					}

				}
			}
		}
	}
	
	/**
	 * Check lethality of this npc's square
	 * @param grid Game Grid of squares
	 * @param npc Npc to check position
	 * @return true if npc is on a lethal square
	 */
	private boolean onLethalSquare(
			TesseraAbstract[][] grid, NonPlayableCharacter npc) {
		CartesianCoordinate c = npc.getSquareCoords();
		return grid[c.getY()][c.getX()].isLethal() 
				&& (npc.getInvincibility()<=0);
	}
	
	/**
	 * Remove a life from this npc
	 * @param npc Npc to kill
	 * @return true if npc is definitely death
	 */
	private boolean killOnce(NonPlayableCharacter npc) {
		int preLives = npc.getLives();
		if(preLives>1) {
			npc.decLives();
			npc.setInvincibility(3);
		} else {
			if(npc.isScoreGiver()) incScoreGM(npc);
			if(npc instanceof Boss) bossDefeated();
			return true;
		}
		return false;
	}
	
	/**
	 * Check lethality of this npc's square
	 * @param grid Game Grid of squares
	 * @param npc Npc to check position
	 * @return true if npc is on a lethal square
	 */
	private boolean isBossOnLethal(
			TesseraAbstract[][] grid, NonPlayableCharacter npc,
			VisitorCharactersMover vcm) {
		// I get the squares occupied by the boss
		Set<CartesianCoordinate> squares = 
				npc.getActualSolidPointsLimits();
		squares.forEach(x->vcm.cartesianGridConverter(x));
		squaresOccupiedByBoss(squares);
		if(npc.getInvincibility()<=0) { // if it's mortal
			for(CartesianCoordinate c : squares) {
				if( grid[c.getY()][c.getX()].isLethal() 
						 ) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Apply Tabula Rasa power up effect
	 */
	public void doKillEveryNpcOneTime() {killEveryNpcOneTime=true;}
	/**
	 * Try to take off a live from every npcs
	 */
	public void killEveryNpcOneTime() {
		if(killEveryNpcOneTime==true) {
			NonPlayableCharacter[] arrayNpcs = npcs.toArray(new NonPlayableCharacter[] {});
			for(int i = 0; i<arrayNpcs.length;i++) {
				if(killOnce(arrayNpcs[i])) {npcs.remove(arrayNpcs[i]);}
			}
			killEveryNpcOneTime = false;
		}
	}
	
	/**
	 * Fill the set with all points between minimum coordinates and maximum ones
	 * @param squares set to be filled
	 */
	private void squaresOccupiedByBoss(Set<CartesianCoordinate> squares) {
		int minX = squares.stream().map(x->x.getX())
				.mapToInt(x->x).min().orElse(0);
		int maxX = squares.stream().map(x->x.getX())
				.mapToInt(x->x).max().orElse(0);
		int minY = squares.stream().map(x->x.getY())
				.mapToInt(x->x).min().orElse(0);
		int maxY = squares.stream().map(x->x.getY())
				.mapToInt(x->x).max().orElse(0);
		for(int y = minY; y<=maxY; y++) {
			for(int x = minX; x<=maxX; x++) {
				squares.add(new CartesianCoordinate(y,x));
			}
		}
	}
	/**
	 * Set bossDefeated in this actual Level
	 */
	private static void bossDefeated() {
		GameModel.getInstance().getActualLevel().setBossDefeated(true);
	}
	/**
	 * Destroy an npc generate a gravedigger
	 * @param npc npc destroyed
	 */
	protected void incScoreGM(NonPlayableCharacter npc) {
		GameModel.getInstance().incScore( // this part are override with DecoratedGravedigger in the sub DecoratedPowerUpMediator
				Gravedigger.setTomb(
						npc.getScoreValue(), 
						npc.getYPanel(), 
						npc.getXPanel())
				);
	}

	/**
	 * Management core method use in Controller to apply Mediator functions 
	 * in the correct order
	 * @param pc player character
	 * @param grid level's grid
	 * @param vcm mover visitor
	 */
	public void managementCore(
			PlayerCharacter pc, 
			TesseraAbstract[][] grid,
			VisitorCharactersMover vcm
			) {
		addedNewNpcs();
		reEstablishBehaviors(pc);
		commitments(); // also update npcs' grid square
		sendPosition(pc);
		killEveryNpcOneTime();
		tryToKillThem(grid,vcm);
	}
	/**
	 * Add new npc to this mediator's npcs avoid concurrent exception
	 */
	private void addedNewNpcs() {
		for(NonPlayableCharacter npc : addedNpcs) {
			npcs.add(npc);
		}
		addedNpcs.clear();
	}
	/**
	 * Method boolean giver of collidingThePlayer.
	 * The boolean will be exhausted.
	 * @param pc player character
	 * @return collidingThePlayer
	 */
	public boolean checkCollisions(PlayerCharacter pc) {
		if(collidingThePlayer) {
			collidingThePlayer = false;
			return true;
		}
		return false;
	}

	/**
	 * Check if any hostile npc remain. Non-hostile npcs aren't counted.
	 * @return true if it possible to win the scene
	 */
	public boolean nobodyLeft() {
		return npcs.stream().noneMatch(x -> x.isHostile() == true);
	}	
	
}
