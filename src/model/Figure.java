package model;

/**
 * Figure is an entity that exists within the game grid,
 * it occupies a specific space entirely and does not move.
 */
public abstract class Figure extends Entity {
	/**
	 * True if it can be a wrapper of other figure
	 */
	protected boolean wrapper;
	/**
	 * True if it can be wrapped by other figure
	 */
	protected boolean wrappable;
	/**
	 * True if it can be destroyed
	 */
	protected boolean breakable;        
	/** 
	 * true if it blocks the path to Game Characters
	 */
	protected boolean solid;
	/**
	 * if it is true then it can damage a character standing on it
	 */
	protected boolean lethality;        // initialized to false

	/**
	 * Constructor of a Figure
	 * @param yGrid fixed y grid value
	 * @param xGrid fixed x grid value 
	 * @param wrapper this wrapper
	 * @param wrappable this wrappable
	 * @param breakable this breakable
	 * @param solid this solid
	 */
	public Figure(int yGrid, int xGrid, boolean wrapper, boolean wrappable, 
			boolean breakable, boolean solid) {
		this.yGrid = yGrid;
		this.xGrid = xGrid;
		this.wrapper = wrapper;
		this.wrappable = wrappable;
		this.breakable = breakable;
		this.solid = solid;
	}
	/**
	 * Getter of wrapper
	 * @return this wrapper
	 */
	public boolean isWrapper() {return wrapper;}
	/**
	 * Getter of wrappable
	 * @return this wrappable
	 */
	public boolean isWrappable() {return wrappable;}
	/**
	 * Getter of breakable
	 * @return this brekable
	 */
	public boolean isBreakable() {return breakable;}
	/**
	 * Getter of solid. If true is the solid field then the object cannot be crossed.
	 * @return this solid
	 */
	public boolean isSolid() {return solid;}
	/**
	 * Getter of lethality. If true is the lethality field then the object can hurt a Game Character upon it.
	 * @return this lethality
	 */
	public boolean isLethal() { return lethality;}
	
}
