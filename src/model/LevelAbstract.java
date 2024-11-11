package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Abstract class used to manage every level subtype.
 * It has methods to flag object in the grid to trespass
 * and define a Collection in which set object to destruction
 */
public abstract class LevelAbstract implements MyGridFormat {

	/**
	 * Name of this level
	 */
	protected String name;
	/**
	 * Specific theater of this level
	 */
	protected Theater theater;
	/**
	 * Level grid
	 */
	protected TesseraAbstract[][] grid;
	/**
	 * Starting set of npcs 
	 */
	protected Set<NonPlayableCharacter> npcs;
	/**
	 * List of object to destroy
	 */
	private List<CartesianCoordinate> eraseList = new ArrayList<>();
	/**
	 * If there is a boss and it is defeated then you win the level
	 */
	private boolean bossDefeated;
	
	/**
	 * Constructor of Level
	 * @param name level's name
	 * @param theater level's theater
	 * @param tesserae level's grid with tesserae
	 * @param npcs level's starting set of npcs
	 */
	public LevelAbstract(
			String name, Theater theater, 
			TesseraAbstract[][] tesserae,
			Set<NonPlayableCharacter> npcs
			) {
		
		this.name = name;
		this.theater = theater;
		this.grid = tesserae;
		this.npcs = npcs;
	}
	/**
	 * Getter of level's name
	 * @return level's name
	 */
	public String getName() {return name;}
	/**
	 * Getter of level's Theater
	 * @return level's Theater
	 */
	public Theater getTheater() {return theater;}
	/**
	 * Getter of level's stage
	 * @return level's stage
	 */
	public Theater.Stage getStage(){return theater.getStage();}
	/**
	 * Getter of level's scene
	 * @return level's scene
	 */
	public Theater.Scene getScene(){return theater.getScene();}
	/**
	 * Getter of level's grid
	 * @return level's grid
	 */
	public TesseraAbstract[][] getGrid() {return grid;}
	/**
	 * Getter of a grid's tessera in input position (Cartesian point)
	 * @param y y coordinate position
	 * @param x x coordinate position
	 * @return tessera in that position
	 */
	public TesseraAbstract getSquare(int y, int x) {return grid[y][x];}
	/**
	 * Getter of starting npcs set (it's required by Npc Mediator)
	 * @return starting npcs set
	 */
	public Set<NonPlayableCharacter> getNpcs() {return npcs;}
	/**
	 * Getter of bossDefeated
	 * @return bossDefeated
	 */
	public boolean isBossDefeated() {return bossDefeated;}
	/**
	 * Setter of level's grid
	 * @param grid new level's grid
	 */
	public void setGrid(TesseraAbstract[][] grid) {this.grid = grid;}
	/**
	 * Setter of bossDefeated
	 * @param bossDefeated new bossDefeated
	 */
	public void setBossDefeated(boolean bossDefeated) {
		this.bossDefeated = bossDefeated;
	}
	
	/**
	 * Check if it's possible to release bomb in the input coordinates,
	 * that is tessera in that position hasn't a bundle 
	 * @param positionPC PC position
	 * @return true if player can release here a bomb
	 */
	public boolean canYouReleaseHere(CartesianCoordinate positionPC) {
		
		int y = positionPC.getY();
		int x = positionPC.getX();

		if (  (getGrid()[y][x]).getBundle() == null  ) 
			return true;
		else return false;

	}
	/**
	 * Set the bundle of the tessera in input position
	 * @param insertion Prop to bundle to tessera
	 * @param coords Coordinates of Tessera to bundle
	 */
	public void bundling(Prop insertion, CartesianCoordinate coords) {
		int y = coords.getY();
		int x = coords.getX();
		if(getGrid()[y][x].isWrapper()
				&& insertion.isWrappable()
				) {
			getGrid()[y][x].setBundle(insertion);
		}
	}
	
	// bundling overloading
	/**
	 * Apply bundling with input map
	 * @param decoratedMap Map of position and prop to bundle
	 */
	public void bundling(Map<CartesianCoordinate, Prop> decoratedMap) {
		for(CartesianCoordinate c : decoratedMap.keySet()) {
			bundling(decoratedMap.get(c),c);
		}
	}
	/**
	 * Activate trespasser method of the bundled prop
	 * wrapped to tessera on input position
	 * @param c input coordinates position
	 */
	public void setToBeTrespasser(CartesianCoordinate c) {
		int y = c.getY();
		int x = c.getX();
		if(getGrid()[y][x].isWrapper()
				&& getGrid()[y][x].getBundle() != null
				) {
			((Prop)getGrid()[y][x].getBundle()).trespasser(this);
		}
	}
	
	// setToBeTrespasser Overloading
	/**
	 * Apply setToBeTrespasser to input set
	 * @param set input set
	 */
	public void setToBeTrespasser(Set<CartesianCoordinate> set) {
		for(CartesianCoordinate c : set) {
			setToBeTrespasser(c);
		}
	}
	
	/**
	 * The Prop bundled to tessera on input position
	 * is added to eraseList for destruction if is breakable
	 * @param c input coordinate position
	 */
	public void setToBeFired(CartesianCoordinate c) {
		int y = c.getY();
		int x = c.getX();
		if(
				getGrid()[y][x].isWrapper()
				&& (getGrid()[y][x]).getBundle() != null
				&& (getGrid()[y][x]).getBundle().isBreakable()) {

			eraseList.add(c);
		}
	}
	
	/**
	 * Apply setToBeFired to the input coordinates set
	 * @param set input set
	 */
	public void setToBeFired(Set<CartesianCoordinate> set) {
		for(CartesianCoordinate c : set) {
			setToBeFired(c);
		}
	}
	
	/**
	 * De-bundle tesserae whose coordinates are located in eraseList
	 */
	public void erase() {
		if(!eraseList.isEmpty()) {
			for(CartesianCoordinate c : eraseList) {
				eraseNow(c);
			}
			eraseList.clear();
		}
	}
	/**
	 * De-bundle tessera with input coordinates.
	 * @param c
	 */
	private void eraseNow(CartesianCoordinate c) {
		int y = c.getY();
		int x = c.getX();
		if(getGrid()[y][x].isWrapper()) {
			(getGrid()[y][x]).setBundle(null);
		}
	}
	
	
}
