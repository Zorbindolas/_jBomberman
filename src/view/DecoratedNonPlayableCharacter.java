package view;

import java.util.Iterator;

import model.NonPlayableCharacter;
/**
 * This class is a second level of abstraction to decorate an NPC component in a more specific way.
 * Common footage methods are implemented.
 */
public abstract class DecoratedNonPlayableCharacter 
	extends DecoratorNonPlayableCharacter 
	implements Iterable<Integer>{
	/**
	 * Path String to load images.
	 */
	protected String path;
	/**
	 * Pointer referred to footage collection.
	 * It determines the current animation image.
	 */
	protected int actualPointer;
	/**
	 * Iteration of numbers referred to animation process.
	 */
	protected Iterator<Integer> iterForms;
	/**
	 * Constructor
	 * @param c component to decorate
	 * @param path path for loading images
	 */
	public DecoratedNonPlayableCharacter(
			NonPlayableCharacter c, String path) {
		super(c);
		this.path = path;
		resetFootage();
	}
	
	@Override
	public void incFootage() {
		if(!iterForms.hasNext()) {
			resetFootage();
		}
		actualPointer = iterForms.next();
	}
	
	@Override
	public void resetFootage() {
		iterForms = this.iterator();
	}

}
