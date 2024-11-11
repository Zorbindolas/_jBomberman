package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class models an instance of Player Character 
 */
public abstract class PlayerCharacter extends GameCharacter 
		implements ReleaseBehavior {
	/**
	 * Last valid direction expressed by the player and different from Dir.NONE
	 */
	private Dir lastValidDir = STARTING_DIR;
	/**
	 * Hero of this pc
	 */
	protected Heroes hero;
	/**
	 * Attempts left to this pc
	 */
	protected int attempts;
	/**
	 * pc's luck determine drop rate of power up after obstacles destruction 
	 */
	private int luck;
	/**
	 * Strategy Pattern for release a bomb
	 */
	protected ReleaseBehavior releaseBehavior;
	/**
	 * Area for bomb created by this pc
	 */
	protected int effectArea;
	/**
	 * triggerTime for bomb created by this pc
	 */
	protected int triggerTime;
	/**
	 * Max number of bomb of this pc
	 */
	protected int maxNumberOfBomb;
	/**
	 * actual number of bombs actual present in the grid
	 */
	protected int actualNumberOfBombs;
	/**
	 * True if pc release remote bombs
	 */
	protected boolean hasRemoteBomb;
	/**
	 * True if pc have kant Glasses
	 */
	protected boolean kantGlasses;
	/**
	 * List of remote bombs to detonate
	 */
	protected List<TimerExplosion> remoteBombs = new ArrayList<>();
	/**
	 * Player Character fixed constructor
	 * @param hero pc's hero
	 * @param lives pc's starting lives
	 * @param attempts pc's starting attempts
	 * @param yPanel pc's y panel
	 * @param xPanel pc's x panel
	 * @param speed pc's starting speed
	 * @param h pc's Height
	 * @param w pc's Width
	 * @param releaseBehavior pc's starting releaseBehavior
	 * @param effectArea pc's starting effectArea
	 * @param triggerTime pc's starting triggerTime
	 * @param maxNumberOfBomb pc's starting max number of bombs
	 * @param kantGlasses true if pc has kant glasses
	 */
	protected PlayerCharacter(
			Heroes hero,
			int lives, int attempts,
			int yPanel, int xPanel, double speed, int h, int w,
			ReleaseBehavior releaseBehavior, int effectArea, 
			int triggerTime, int maxNumberOfBomb, boolean kantGlasses
			) {
		
		super(yPanel, xPanel, speed, h, w, lives);
		
		this.hero = hero;
		this.attempts = attempts;
		this.releaseBehavior = releaseBehavior;
		this.effectArea = effectArea;
		this.triggerTime = triggerTime;
		this.maxNumberOfBomb = maxNumberOfBomb;
		this.luck = 8;
		this.kantGlasses = kantGlasses;
		
		actualNumberOfBombs = 0;
		
	}
	
	// getters
	/**
	 * Getter of pc's last valid direction
	 * @return pc's last valid direction
	 */
	public Dir getLastValidDir() {return lastValidDir;}
	/**
	 * Getter of pc's hero label
	 * @return pc's hero label
	 */
	public Heroes getHero() {return hero;}
	/**
	 * Getter of total attempts
	 * @return pc's remain total attempts
	 */
	public int getAttempts() {return attempts;}
	/**
	 * Getter of actual number of bombs
	 * @return actual number of bombs
	 */
	public int getActualNumberOfBombs() {return actualNumberOfBombs;}
	/**
	 * Getter of max number of bomb
	 * @return max number of bomb
	 */
	public int getMaxNumberOfBomb() {return maxNumberOfBomb;}
	/**
	 * Getter of pc's release behavior
	 * @return pc's release behavior
	 */
	public ReleaseBehavior getReleaseBehavior() {return releaseBehavior;}

	/**
	 * Getter of pc's effectArea
	 * @return pc's effectArea
	 */
	public int getEffectArea() {return effectArea;}
	/**
	 * Getter of pc's trigger time
	 * @return pc's trigger time
	 */
	public int getTriggerTime() {return triggerTime;}
	/**
	 * Getter of remote bombs list
	 * @return pc's remote bombs list
	 */
	public List<TimerExplosion> getRemoteBombs(){return remoteBombs;}
	/**
	 * Getter of pc' luck
	 * @return pc's luck
	 */
	public int getLuck() {
		if(luck<0) return 0;
		return luck; 
	}
	/**
	 * Getter of pc's hasRemoteBomb
	 * @return pc's hasRemoteBomb
	 */
	public boolean hasRemoteBomb() {return hasRemoteBomb;}
	/**
	 * Getter of pc's kantGlasses
	 * @return this kantGlasses
	 */
	public boolean hasKantGlasses() {
		return kantGlasses;
	}
	
	// Setters 
	@Override
	public void setDir(Dir newDir) {
		if(newDir!=Dir.NONE)lastValidDir = newDir;
		super.setDir(newDir);
	}
	
	
	/**
	 * Setter of attempts
	 * @param attempts new attempts
	 */
	public void setAttempts(int attempts) {this.attempts = attempts;}
	/**
	 * Increment max number of bomb by one
	 */
	public void incMaxNumberOfBomb() {maxNumberOfBomb++;}
	/**
	 * Setter of pc's effectArea
	 * @param effectArea new effectArea
	 */
	public void setEffectArea(int effectArea) {this.effectArea = effectArea;}
	/**
	 * Setter of pc's trigger time
	 * @param triggerTime new TriggerTimer
	 */
	public void setTriggerTime(int triggerTime) {this.triggerTime = triggerTime;}
	/**
	 * Setter of hasRemoteBomb
	 * @param hasRemoteBomb new hasRemoteBomb
	 */
	public void setHasRemoteBomb(boolean hasRemoteBomb) {
		this.hasRemoteBomb = hasRemoteBomb;
		if(hasRemoteBomb) {
			releaseBehavior = new DropBombRemote();
		} else {
			releaseBehavior = new DropBombStandard();
		}
	}
	/**
	 * Add a remote bomb in the remote bombs list
	 * @param remoteBomb added bomb to the list
	 */
	public void addRemoteBomb(TimerExplosion remoteBomb) {
		remoteBombs.add(remoteBomb);
	}
	/**
	 * Detonate first remote bomb
	 */
	public void triggerFirstRemoteBomb() {
		if(!remoteBombs.isEmpty()) {
			TimerExplosion hasRemoteBomb = remoteBombs.remove(0);
			if(hasRemoteBomb.isTriggered()) { // in order to fix player's timerBombs list due to collateral explosions detonation
				this.triggerFirstRemoteBomb(); 
			} else { // bomb trespasser turn trigger to true
				hasRemoteBomb.trigger();
			}		
		}
	}
	/**
	 * Correct bug caused by EnemyBombEater
	 */
	public void remoteBombEated() {
		if(!remoteBombs.isEmpty()) remoteBombs.remove(0);
	}
	/**
	 * Reduce value luck increment the luck performance
	 */
	public void luckier() {
		if(luck>0) luck--;
	}

	// Class methods
	/**
	 * Reset actual number of bomb to zero
	 */
	public void resetTemporaryStatus() {
		this.actualNumberOfBombs = 0;
	}
	/**
	 * Check if is possible to release another bomb
	 * @return true if is possible
	 */
	public boolean canIReleaseAnotherBomb() {
		return actualNumberOfBombs < maxNumberOfBomb;
	}
	/**
	 * Reload one bomb
	 */
	public void reloadAmmunition() { 
		actualNumberOfBombs--;
		if(actualNumberOfBombs<0) actualNumberOfBombs = 0;
	}
	
	@Override
	public Bomb release(CartesianCoordinate c) {
		actualNumberOfBombs++;
		return releaseBehavior.release(c);
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}


}