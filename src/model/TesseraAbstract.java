package model;

/**
 * TesseraAbstract is the super type of each concrete Tessera.
 * Every TesseraAbstract represents a play board cell.
 * The Prop reference saved in the tile transfers to it some properties
 * such as solidity and lethality.
 */
public abstract class TesseraAbstract extends Figure implements PropWrapper {

	/**
	 * Saved Prop reference of this TesseraAbstract
	 */
	private Prop bundle;
	/**
	 * It indicates if this TesseraAbstract is an exit
	 */
	private boolean isExit;   // initialized as false
	/**
	 * It indicates if this TesseraAbstract is blackened
	 */
	private boolean blackened; // initialized as false
	/**
	 * TesseraAbstract Construct
	 * @param yGrid fixed y grid
	 * @param xGrid fixed x grid
	 * @param isExit exit boolean value
	 * @param blackened blackened boolean value
	 */
	public TesseraAbstract(int yGrid, int xGrid, boolean isExit, boolean blackened) {
		super(yGrid, xGrid, true, false, false, false);
		this.isExit = isExit;
		this.blackened = blackened;
	}
	/**
	 * TesseraAbstract Construct. 
	 * You must define grid coordinates before use it.
	 * @param isExit exit boolean value
	 * @param blackened blackened boolean value
	 */
	public TesseraAbstract(boolean isExit, boolean blackened) {
		this(0, 0, isExit, blackened);
	}
	/**
	 * Getter of solid. If true is the wrapped Prop solid field
	 *  then this TesseraAbstract cannot be crossed by normal Game Characters.
	 * @return this lethality
	 */
	@Override
	public boolean isSolid() {
		if(bundle==null) return this.solid;
		return bundle.isSolid();
	}
	/**
	 * Getter of lethality. If true is the wrapped Prop lethality field
	 * then this TesseraAbstract can hurt a Game Character upon it.
	 * @return this lethality
	 */
	@Override
	public boolean isLethal() {
		if(bundle==null) return this.lethality;
		return bundle.isLethal();
	}
	@Override
	public Prop getBundle() {return bundle;}
	@Override
	public void setBundle(Prop bundle) {this.bundle = bundle;}
	/**
	 * Return a boolean indicates if this Tessera is an Exit
	 * @return true if this Tessera is an exit
	 */
	public boolean isExit() {return isExit;}
	/**
	 * Return a boolean indicates if this Tessera is under a Wall (in the grid)
	 * @return True if there some Wall above this Tessera
	 */
	public boolean isBlackened() {return blackened;}
	/**
	 * Setter of the boolean indicates if this Tessera is an exit
	 * @param exit new boolean exit value
	 */
	public void setExit(boolean exit) {this.isExit = exit;}
	/**
	 * Setter of the boolean indicates if this Tessera is under a Wall (in the grid)
	 * @param blackened new boolean blackened value
	 */
	public void setBlackened(boolean blackened) {this.blackened = blackened;}


}
