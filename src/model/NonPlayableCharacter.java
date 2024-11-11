package model;

import java.util.Random;

/**
 * This class models non-player characters.
 * The Npc Mediator manages actual NPCs ina a game session.
 */
public abstract class NonPlayableCharacter extends GameCharacter {

	/**
	 * Possible behavioral states of the character
	 */
	public static enum Behavior{
		/**
		 * REST BEHAVIOR
		 */
		REST, 
		/**
		 * WALK BEHAVIOR
		 */
		WALK, 
		/**
		 * ACTION BEHAVIOR
		 */
		ACTION;
	}
	/**
	 * List with every behavior
	 */
	private static final Behavior[] BEHAVIOR_VALUES = Behavior.values();
	/**
	 * Number of total behaviors
	 */
	private static final int SIZE_BEHAVIOUR = Behavior.values().length;
	/**
	 * Mediator of NonPlayableCharacters
	 */
	protected NpcMediator npcMediator;
	/**
	 * Type of this NonPlayableCharacter
	 */
	private NpcType npcType;
	/**
	 * random utility
	 */
	private static Random random = new Random();
	/**
	 * If true, resonanceOnPc occurs when this character collides with the player character.
	 * You cannot win the game if at least one hostile creature is still alive 
	 */
	private boolean hostility;
	/**
	 * Value of score given by the death of this character
	 */
	private int scoreValue;
	/**
	 * True if this character gives score at its death
	 */
	private boolean scoreGiver;
	/**
	 * Boolean value that gives rhythm to the completion of the doAction method
	 */
	private boolean actionReady;

	// Behaviour Management
	/**
	 * Current behavior of this character
	 */
	private Behavior behavior;
	/**
	 * Seconds remaining until the current action is completed
	 */
	protected int activityTimer;
	/**
	 * True if during a movement action the character has encountered an area of ​​solidity (this situation causes it to stop)
	 */
	private boolean findAStop;
	
	/**
	 * Constructor of NPC
	 * @param yPanel starting y panel
	 * @param xPanel starting x panel
	 * @param speed starting speed
	 * @param h Character's height
	 * @param w Character's width
	 * @param lives starting lives
	 * @param hostility hostility towards the player 
	 * @param scoreGiver fixed scoreGiver status
	 * @param scoreValue fixed ScoreValue
	 * @param npcType fixed npc type
	 * @param npcMediator fixed npc mediator
	 */
	public NonPlayableCharacter(
			int yPanel, int xPanel, 
			int speed, int h, int w, int lives, 
			boolean hostility, boolean scoreGiver, 
			int scoreValue, NpcType npcType,
			NpcMediator npcMediator
			) {
		
		super(yPanel, xPanel, speed, h, w, lives);
		this.hostility = hostility;
		this.scoreGiver = scoreGiver;
		this.scoreValue = scoreValue;
		this.behavior = Behavior.REST;
		this.npcType = npcType;
		this.npcMediator = npcMediator;
	}
	
	/**
	 * Overloading of npc constructor with panel coordinates to 0s
	 * @param speed starting speed
	 * @param h Character's height
	 * @param w Character's width
	 * @param lives starting lives
	 * @param hostility hostility towards the player 
	 * @param scoreGiver fixed scoreGiver status
	 * @param scoreValue fixed ScoreValue
	 * @param npcType fixed npc type
	 * @param npcMediator fixed npc mediator
	 */
	public NonPlayableCharacter(
			int speed, int h, int w, int lives, 
			boolean hostility, boolean scoreGiver, int scoreValue,
			NpcType npcType,NpcMediator npcMediator) {
		this(0, 0, speed, h, w, lives, hostility, 
				scoreGiver, scoreValue, npcType,npcMediator);
		this.npcType = npcType; 
	}
	
	// Getters
	/**
	 * Getter of behavior
	 * @return this current behavior
	 */
	public Behavior getBehaviour() {return behavior;}
	/**
	 * Getter of score value
	 * @return score value
	 */
	public int getScoreValue() {return scoreValue;}
	/**
	 * Getter of hostility
	 * @return true if is hostile
	 */
	public boolean isHostile() {return hostility;}
	/**
	 * Getter of remain activity timer (seconds)
	 * @return activity timer
	 */
	public int getActivityTimer() {return activityTimer;}
	/**
	 * Getter of findAStop
	 * @return findAStop
	 */
	public boolean haveFindAStop() {return findAStop;}
	/**
	 * Getter of scoreGiver
	 * @return true if give score dying
	 */
	public boolean isScoreGiver() {return scoreGiver;}
	/**
	 * Getter of actionReady
	 * @return true if it can doAction
	 */
	public boolean isActionReady() { return actionReady;}
	/**
	 * Getter of this npc type
	 * @return this npc type
	 */
	public NpcType getNpcType() {return npcType;}
	
	// Special Setters and Random Setters
	/**
	 * Setter of this behavior
	 * @param behavior the new behavior
	 */
	public void setPredefineBehaviour(Behavior behavior) {this.behavior=behavior;}
	/**
	 * Setter of activity timer (seconds)
	 * @param activityTimer new Activity Timer
	 */
	public void setActivityTimer(int activityTimer) {
		this.activityTimer = activityTimer;
	}
	/**
	 * reduce activity timer by one
	 */
	public void decActivityTimerByOne() {activityTimer--;}
	/**
	 * Get a random behavior
	 * @return a behavior casually chosen
	 */
	private static Behavior getRandomBehaviour()  {
		return BEHAVIOR_VALUES[random.nextInt(SIZE_BEHAVIOUR)];
	}
	/**
	 * Set a casual behavior
	 */
	public void setCasualBehaviour() {
		this.behavior = getRandomBehaviour();
	}
	/**
	 * Set a casual activity timer
	 */
	public void setActivityTimerRandomly() {
		this.activityTimer = random.nextInt(1,2);
	}
	/**
	 * Set findAStop
	 * @param findAWall new boolean value for findAStop
	 */
	public void setFindAStop(boolean findAWall) {this.findAStop=findAWall;}
	/**
	 * Set actionReady
	 * @param actionReady new boolean value for actionReady
	 */
	public void setActionReady(boolean actionReady) {
		this.actionReady = actionReady;
	}
	
	// Special Methods
	/**
	 * Template Methods for behavioral algorithm decisions
	 */
	public void doYourThing() {
		if(activityTimer>0) {
			switch(behavior) {
				case REST -> {
					this.setDir(Dir.NONE);
					doRest();
					}
				case WALK -> {
					doWalk();
					}
				case ACTION -> {
					this.setDir(Dir.NONE);
					doAction();
					}
				default -> {}
			}
		}
	}
	
	/**
	 * Methods call when character must rest
	 */
	public void doRest() {}
	
	/**
	 * Methods call when character must move
	 */
	public void doWalk() {
		if(!findAStop) npcMediator.walksWithMe(this);;
	}
	/**
	 * Methods call when character must do its action
	 */
	public void doAction() {}

	/**
	 * Effect triggered by this character towards the player character
	 * when the two entities share part of the solidity area
	 * @param pc player character to alter
	 */
	public abstract void resonanceOnPc(PlayerCharacter pc);
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Getter of the reference to the mediator
	 * @return the npc actual mediator
	 */
	public NpcMediator getNpcMediator() {
		return npcMediator;
	}
	
}
