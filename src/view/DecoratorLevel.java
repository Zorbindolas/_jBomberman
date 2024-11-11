package view;

import java.util.Map;
import java.util.Set;

import model.*;
/**
 * Decorated version of a Level object
 */
public abstract class DecoratorLevel extends LevelAbstract {
	/**
	 * Wrapped LevelAbstract
	 */
	protected LevelAbstract component;
	/**
	 * Related Arches 
	 */
	private Arches arches;
	/**
	 * Constructor
	 * @param component LevelAbstract to decorate
	 * @param arches Arches to be related
	 */
	public DecoratorLevel(LevelConcrete component, Arches arches) {
		super(component.getName(), component.getTheater(),
				component.getGrid(), component.getNpcs());
		this.component = component;
		this.arches = arches;
	}
	
	// Overrides of LevelAbstract
	
	@Override
	public String getName() {return component.getName();}
	@Override
	public Theater getTheater() {return component.getTheater();}
	@Override
	public TesseraAbstract[][] getGrid() {return component.getGrid();}
	@Override
	public TesseraAbstract getSquare(int y, int x) {return component.getSquare(y, x);}
	@Override
	public Set<NonPlayableCharacter> getNpcs(){return component.getNpcs();}
	@Override
	public boolean isBossDefeated() {return component.isBossDefeated();}
	@Override
	public void setGrid(TesseraAbstract[][] grid) {component.setGrid(grid);}
	@Override
	public void setBossDefeated(boolean bossDefeated) {component.setBossDefeated(bossDefeated);}
	@Override
	public boolean canYouReleaseHere(CartesianCoordinate positionPC) {
		return component.canYouReleaseHere(positionPC);
	}
	@Override
	public void bundling(Prop insertion, CartesianCoordinate coords) {
		component.bundling(insertion, coords);
	}
	@Override
	public void bundling(Map<CartesianCoordinate, Prop> decoratedMap) {
		component.bundling(decoratedMap);
	}
	@Override
	public void setToBeTrespasser(CartesianCoordinate c) {
		component.setToBeTrespasser(c);
	}
	@Override
	public void setToBeTrespasser(Set<CartesianCoordinate> set) {
		component.setToBeTrespasser(set);
	}
	@Override
	public void setToBeFired(CartesianCoordinate c) {
		component.setToBeFired(c);
	}
	@Override
	public void setToBeFired(Set<CartesianCoordinate> set) {
		component.setToBeFired(set);
	}
	@Override
	public void erase() {
		component.erase();
	}
	
	// Decorator's inner methods
	/**
	 * Getter of this Arches
	 * @return its Arches
	 */
	public Arches getArches() {return arches;}

}
