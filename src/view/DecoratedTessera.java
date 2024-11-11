package view;

import model.TesseraAbstract;
/**
 * Decorated version of a Tessera object
 */
public class DecoratedTessera extends DecoratorTessera {
	/**
	 * Constructor.
	 * @param c component to wrap
	 * @param path main images path
	 * @param shadowPath shadowed images path
	 * @param exitPath exit images path
	 */
	public DecoratedTessera(TesseraAbstract c, String[] path, String[] shadowPath, String[] exitPath) {
		super(c, path, shadowPath, exitPath);
	}


}
