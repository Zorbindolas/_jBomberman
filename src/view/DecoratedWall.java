package view;

import java.util.Random;

import model.Wall;
/**
 * Decorated version of a Wall object
 */
public class DecoratedWall extends DecoratorProp {
	/**
	 * Utility random field
	 */
	private static Random r = new Random();
	/**
	 * Constructor
	 * @param c Wall to wrap
	 * @param path main images path
	 */
	public DecoratedWall(Wall c, String[] path) {
		super(c, path);
	}
	
	@Override
	public void incFootage() {
		if(r.nextInt(0,11)>8) super.incFootage();
	}

}
