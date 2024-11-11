package model;

/**
 * class of a concrete Tessera that can be instantiated.
 */
public class TesseraConcrete extends TesseraAbstract{

	/**
	 * Complete Constructor for TesseraConcrete
	 * @param yGrid fixed y grid
	 * @param xGrid fixed x grid
	 * @param blackened blackened value
	 */
	public TesseraConcrete(int yGrid, int xGrid, boolean blackened) {
		super(yGrid, xGrid, false, blackened);
	}
	/**
	 * Partial Constructor for TesseraConcrete.
	 * You must initialize grid coordinates before use this Tessera.
	 * @param blackened blackened value
	 */
	public TesseraConcrete(boolean blackened) {
		this(0,0,blackened);
	}
	
}
