package model;

/**
 * They are the objects use to be bundle to Tesserae.
 * They cannot exist out of Tesserae.
 * In fact, these objects occupy the game grid as if they were props
 * (Prop stands for TheatricalProperty).
 */
public abstract class Prop extends Figure {
	/**
	 * Prop constructor
	 * @param yGrid fixed y grid
	 * @param xGrid fixed x grid
	 * @param breakable true if it can be break
	 * @param solid true if it is solid
	 */
	public Prop(int yGrid, int xGrid, boolean breakable, 
			boolean solid) {
		super(yGrid, xGrid,false, true, true, true);
		this.breakable = breakable;
		this.solid = solid;
	}
	/**
	 * Prop constructor without coordinates definition 
	 * @param breakable breakable value
	 * @param solid solid value
	 */
	public Prop(boolean breakable, boolean solid) {
		this(0, 0, breakable, solid);
	}
	/**
	 * Setter of this Prop.solid
	 * @param solid new solid
	 */
	public void setSolid(boolean solid) {this.solid = solid;}
	
	/**
	 * This method will be override if the object wants to do something
	 * before its destruction. It is a point of access
	 * to make something happen before this object gets destroyed
	 * generally it's important that a subtype of Prop (after
	 * doing its stuffs) concludes its override with the super in here.
	 * This super is required to effectively set to be fired the object
	 * if there isn't a specific mechanics doing this.
	 * @param level The level in which set to be fired this
	 */
	public void trespasser(LevelAbstract level) {
		level.setToBeFired(this.getSquareCoords());
	}
}
