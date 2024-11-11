package view;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;

import model.CartesianCoordinate;
import model.Dir;
import model.NonPlayableCharacter;
import model.NpcMediator;
import model.NpcType;
import model.PlayerCharacter;
import model.Visitor;
/**
 * This class is a first decorative shell of an NPC component
 * in the way of Decorator Design Pattern. Images are animated through array.
 * With this class the decorator mimics the methods of its component.
 */
public abstract class DecoratorNonPlayableCharacter extends NonPlayableCharacter
				implements FootageFuncs {
	/**
	 * Decorated component
	 */
	protected NonPlayableCharacter component;
	/**
	 * Decorator constructor
	 * @param c component to decorate
	 */
	public DecoratorNonPlayableCharacter(NonPlayableCharacter c) {
		super(c.getYPanel(), c.getXPanel(), c.getSpeed(),
				c.getHeight(),c.getWidth(),c.getLives(),c.isHostile(),
				c.isScoreGiver(),c.getScoreValue(),
				c.getNpcType(),c.getNpcMediator());
		this.component = c;
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
	@Override
	public CartesianCoordinate getGravityCoords() {return component.getGravityCoords();}
	@Override
	public int getLives() {return component.getLives();}
	@Override
	public int getInvincibility() {return component.getInvincibility();}
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
		if(decB && component.getLives()>0) DynaSound.playEffect(13); //npcDamaged
		return decB;
	}
	@Override
	public void decInvincibility() {component.decInvincibility();}
	
	// Overrides of GameCharacter's methods
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
	
	// NonPlayableCharacter's methods
	// Getters
	@Override
	public Behavior getBehaviour() {return component.getBehaviour();}
	@Override
	public int getScoreValue() {return component.getScoreValue();}
	@Override
	public boolean isHostile() {return component.isHostile();}
	@Override
	public int getActivityTimer() {return component.getActivityTimer();}
	@Override
	public boolean haveFindAStop() {return component.haveFindAStop();}
	@Override
	public boolean isScoreGiver() {return component.isScoreGiver();}
	@Override
	public boolean isActionReady() { return component.isActionReady();}
	@Override
	public NpcType getNpcType() {return component.getNpcType();}
	
	//Setters
	@Override
	public void setPredefineBehaviour(Behavior behavior) {
		component.setPredefineBehaviour(behavior);
	}
	@Override
	public void setActivityTimer(int activityTimer) {
		component.setActivityTimer(activityTimer);;
	}
	@Override
	public void decActivityTimerByOne() {component.decActivityTimerByOne();}
	@Override
	public void setCasualBehaviour() {component.setCasualBehaviour();}
	@Override
	public void setActivityTimerRandomly() {
		component.setActivityTimerRandomly();
	}
	@Override
	public void setFindAStop(boolean findAWall) {
		component.setFindAStop(findAWall);
	}
	@Override
	public void setActionReady(boolean actionReady) {
		component.setActionReady(actionReady);
	}
	
	// Special Methods
	@Override
	public void doYourThing() {
		component.doYourThing();
	}
	@Override
	public void doRest() {component.doRest();}
	@Override
	public void doWalk() {
		component.doWalk();
	}
	@Override
	public void doAction() {
		component.doAction();
	}
	@Override
	public void accept(Visitor visitor) {
		component.accept(visitor);
	}
	@Override
	public void resonanceOnPc(PlayerCharacter pc) {component.resonanceOnPc(pc);}
	@Override
	public NpcMediator getNpcMediator() {
		return component.getNpcMediator();
	}
	/**
	 * Getter of this last footage image before death event (for Gravedigger purposes).
	 * @return image to provide to a decorated gravedigger
	 */
	public abstract BufferedImage getDeathImage();
	
	
}
