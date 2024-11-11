package view;

import model.Obstacle;
/**
 * DecoratorProp for an Obstacle type object
 */
public class DecoratedObstacle extends DecoratorProp {
	/**
	 * Constructor of a Decorated Obstacle
	 * @param c Obstacle to decorate
	 * @param path main footage path
	 * @param pathDestroyed destroyed footage path
	 */
	public DecoratedObstacle(Obstacle c, String[] path, String[] pathDestroyed) {
		super(c, path, pathDestroyed);
	}

}
