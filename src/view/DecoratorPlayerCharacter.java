package view;

import java.util.List;
import java.util.Map;
import java.util.Set;

import model.*;
/**
 * Decorated version of a PlayerCharacter to implement its View animations 
 * in a Decorator Design Patter way.
 * With this class the decorator mimics the methods of its component.
 */
public abstract class DecoratorPlayerCharacter extends PlayerCharacter 
implements Iterable<Integer>, FootageFuncs, ReleaseBehavior, FootageDeath, FootageWin {
	/**
	 * Decorated component.
	 */
	protected PlayerCharacter component;
	/**
	 * Constructor
	 * @param component PlayerCharacter to decorate
	 */
	public DecoratorPlayerCharacter(PlayerCharacterConcrete component) {
		super(component.getHero(), component.getLives(),component.getAttempts(),
				component.getYPanel(),component.getXPanel(),component.getSpeed(),
				component.getHeight(),component.getWidth(),
				component.getReleaseBehavior(), component.getEffectArea(), 
				component.getTriggerTime(), component.getMaxNumberOfBomb(),
				component.hasKantGlasses()
				);
		this.component = component;
	}
	
	// Overrides of Entity
	@Override
	public int getXGrid() {return component.getXGrid();}
	@Override
	public int getYGrid() {return component.getYGrid();}
	@Override
	public int getXPanel() {return component.getXPanel();}
	@Override
	public int getYPanel() {return component.getYPanel();}
	@Override
	public void setXGrid(int xGrid) {component.setXGrid(xGrid);}
	@Override
	public void setYGrid(int yGrid) {component.setYGrid(yGrid);}
	@Override
	public void setXPanel(int xPanel) {component.setXPanel(xPanel);}
	@Override
	public void setYPanel(int yPanel) {component.setYPanel(yPanel);}
	@Override
	public CartesianCoordinate getSquareCoords() {return component.getSquareCoords();}
	@Override
	public CartesianCoordinate getPanelCoords() {return component.getPanelCoords();}
	
	// Overrides of GameCharacter and PlayerCharacter methods
	@Override
	public CartesianCoordinate getGravityCoords() {return component.getGravityCoords();}
	@Override
	public int getAttempts() {return component.getAttempts();}
	@Override
	public int getLives() {return component.getLives();}
	@Override
	public int getInvincibility() {return component.getInvincibility();}
	@Override
	public void setAttempts(int attempts) {component.setAttempts(attempts);}
	@Override
	public void setLives(int lives) {component.setLives(lives);}
	@Override
	public void setSpeed(int speed) {component.setSpeed(speed);}
	@Override
	public void incSpeed() {component.incSpeed();}
	@Override
	public void setInvincibility(int invincibility) {
		component.setInvincibility(invincibility);
	}
	@Override
	public boolean decLives() {
		boolean decB = component.decLives();
		if(decB) DynaSound.playEffect(8); // pcDeath
		return decB;
	}
	@Override
	public void decInvincibility() {component.decInvincibility();}
	@Override
	public int getID() {return component.getID();}
	@Override
	public Dir getDir() {return component.getDir();}
	@Override
	public int getSpeed() {return component.getSpeed();}
	@Override
	public int getHeight() {return component.getHeight();}
	@Override
	public int getWidth() {return component.getWidth();}
	@Override
	public int getOffGravityY() {return component.getOffGravityY();}
	@Override
	public int getOffGravityX() {return component.getOffGravityX();}
	@Override
	public void setDir(Dir newDir) {component.setDir(newDir);}
	@Override
	public void move(Dir dir, int yourSpeed) {component.move(dir, yourSpeed);}
	@Override
	public void move(Dir dir) {component.move(dir);}
	@Override
	public Map<CartesianCoordinate,Degree> getProjectedSolidPoints(Dir dir){
		return component.getProjectedSolidPoints(dir);
	}
	@Override
	public Set<CartesianCoordinate> getActualSolidPoints(){
		return component.getActualSolidPoints();
	}
	@Override
	public Set<CartesianCoordinate> getActualSolidPointsExtremePrecision(){
		return component.getActualSolidPointsExtremePrecision();
	}
	@Override
	public Set<CartesianCoordinate> getActualSolidPointsLimits(){
		return component.getActualSolidPointsLimits();
	}
	@Override
	public CartesianCoordinate getLimitNordOvestRectCoord() {
		return component.getLimitNordOvestRectCoord();
	}
	@Override
	public CartesianCoordinate getLimitSudEstRectCoord() {
		return component.getLimitSudEstRectCoord();
	}
	@Override
	public int getLuck() {return component.getLuck();}
	@Override
	public void luckier() {component.luckier();}
	
	@Override
	public Heroes getHero() {return component.getHero();}
	
	@Override
	public boolean canIReleaseAnotherBomb() { 
		return component.canIReleaseAnotherBomb();
	}
	@Override
	public void reloadAmmunition() {
		component.reloadAmmunition();
	}
	@Override
	public Dir getLastValidDir() {return component.getLastValidDir();}
	@Override
	public Bomb release(CartesianCoordinate c) {
		return component.release(c);
	}
	@Override
	public int getActualNumberOfBombs() {return component.getActualNumberOfBombs();}
	
	@Override
	public int getMaxNumberOfBomb() {return component.getMaxNumberOfBomb();}
	
	@Override
	public ReleaseBehavior getReleaseBehavior() {return component.getReleaseBehavior();}

	@Override
	public int getEffectArea() {
		return component.getEffectArea();
	}
	
	@Override
	public void incMaxNumberOfBomb() {component.incMaxNumberOfBomb();}

	@Override
	public void setEffectArea(int effectArea) {
		component.setEffectArea(effectArea);
	}

	@Override
	public int getTriggerTime() {
		return component.getTriggerTime();
	}

	@Override
	public void setTriggerTime(int triggerTime) {
		component.setTriggerTime(triggerTime);
	}

	@Override
	public List<TimerExplosion> getRemoteBombs(){return component.getRemoteBombs();}
	@Override
	public boolean hasRemoteBomb() {return component.hasRemoteBomb();}
	@Override
	public void setHasRemoteBomb(boolean hasTimerBomb) {component.setHasRemoteBomb(hasTimerBomb);}
	@Override
	public void addRemoteBomb(TimerExplosion timerBomb) {
		component.addRemoteBomb(timerBomb);
	}
	@Override
	public void triggerFirstRemoteBomb() {component.triggerFirstRemoteBomb();}

	
	@Override
	public void remoteBombEated() {
		component.remoteBombEated();
	}
	@Override
	public boolean hasKantGlasses() {
		return component.hasKantGlasses();
	}
	// Visitor Design Pattern
	@Override
	public void accept(Visitor visitor) {
		component.accept(visitor);		
	}	
}
