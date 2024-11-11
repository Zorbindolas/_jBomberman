package model;

/**
 * Class that establishes the basis of any object 
 * that directly or indirectly determines the fate of a game scene.
 */
public abstract class Entity {

	/**
	 * Coordinates of this Entity.
	 * Each entity has two types of coordinates: 
	 * coordinates that refer to the game grid (Grid Coordinates)
	 * and coordinates that refer to a real position (Panel Coordinates) 
	 * arbitrarily defined to be in relation to the grid
	 * as its ideal multiples.
	 * The panel can be defined as the result of the application 
	 * of a biunivocal function which associates a set of unique coordinates 
	 * to each coordinate of the game grid.
	 * In video game terms, the panel could be intended as the monitor.
	 */
	protected int xGrid, yGrid, xPanel, yPanel;
	
	/**
	 * Constructor without setting
	 */
	public Entity() {}
	
	/**
	 * Constructor of an Entity in the game grid
	 * @param yGrid this y grid
	 * @param xGrid this x grid
	 */
	public Entity(int yGrid, int xGrid) {
		this.yGrid = yGrid;
		this.xGrid = xGrid;
	}
	
	// Getters
	/**
	 * Getter of x grid coordinate
	 * @return x grid coordinate
	 */
	public int getXGrid() {return xGrid;}
	/**
	 * Getter of y grid coordinate
	 * @return y grid coordinate
	 */
	public int getYGrid() {return yGrid;}
	/**
	 * Getter of x panel coordinate
	 * @return x panel coordinate
	 */
	public int getXPanel() {return xPanel;}
	/**
	 * Getter of y panel coordinate
	 * @return y panel coordinate
	 */
	public int getYPanel() {return yPanel;}
	
	/**
	 * Returns a point representing the grid box (tessera) in which the entity is located
	 * @return Cartesian point of Entity's grid position
	 */
	public CartesianCoordinate getSquareCoords() {
		return new CartesianCoordinate(yGrid,xGrid);
	}
	/**
	 * Returns a point representing the panel points in which the entity is located
	 * @return Cartesian point of Entity's panel position
	 */
	public CartesianCoordinate getPanelCoords() {
		return new CartesianCoordinate(yPanel,xPanel);
	}
	
	// Setters
	/**
	 * Setter of x grid coordinate
	 * @param xGrid new x grid coordinate
	 */
	public void setXGrid(int xGrid) {this.xGrid = xGrid;}
	/**
	 * Setter of y grid coordinate
	 * @param yGrid new y grid coordinate
	 */
	public void setYGrid(int yGrid) {this.yGrid = yGrid;}
	/**
	 * Setter of x panel coordinate
	 * @param xPanel new x panel coordinate
	 */
	public void setXPanel(int xPanel) {this.xPanel = xPanel;}
	/**
	 * Setter of y panel coordinate
	 * @param yPanel new y panel coordinate
	 */
	public void setYPanel(int yPanel) {this.yPanel = yPanel;}

}
