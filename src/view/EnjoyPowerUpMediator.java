package view;

import model.PowerUpMediator;
import model.Powerupper;
import model.Prop;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import controller.FactoryDecoratedPowerUp;
import model.CartesianCoordinate;
import model.GameModel;
import model.Gravedigger;
import model.PowerUp.Power;
/**
 * Implemented version of PowerUpMediator from Model in which 
 * view components are developed.
 * The Singleton Pattern simplifies the realization 
 * of dependencies towards this mediator.
 */
public class EnjoyPowerUpMediator extends PowerUpMediator
 implements FootageFuncs, MyJBombermanFormat {
	/**
	 * Singleton instance
	 */
	private static EnjoyPowerUpMediator instance;
	/**
	 * Private constructor for Singleton Design Pattern
	 */
	private EnjoyPowerUpMediator() {
		super(EnjoyNpcMediator.getInstance());
	}
	/**
	 * Singleton getInstance method
	 * @return Singleton instance of this class
	 */
	public static PowerUpMediator getInstance() {
		if(instance==null) instance = new EnjoyPowerUpMediator();
		return instance;
	}
	
	@Override
	protected Powerupper generateCasualPowerUp(CartesianCoordinate c) {
		Power label = super.generateCasualLabel();
		return FactoryDecoratedPowerUp
				.getDecoratedPowerUp(
						label,
						c.getY(),
						c.getX(),
						OFFSET_Y + c.getY()*UNIT_NORMAL,
						OFFSET_X + c.getX()*UNIT_NORMAL,
						npcMediator
						);
	}

	@Override
	public void incFootage() {
		for(Powerupper p : droppedPowerUps.values()) {
			((FootageFuncs)p).incFootage();
		}
	}

	@Override
	public void resetFootage() {
	}

	@Override
	public BufferedImage getImage() {
		return null;
	}
	
	/**
	 * Override for applying Decorator Pattern to gravedigger
	 * before release it in the application
	 */
	@Override
	protected void incScoreByCollecting(Prop powerUp) {
		GameModel.getInstance().incScore(
				new DecoratedGravediggerScore(
						Gravedigger.setTomb(
								((Powerupper)powerUp).getScoreValue(), 
								powerUp.getYPanel(), 
								powerUp.getXPanel()
								)
						)
				);
	}
	/**
	 * This method allows you to draw all the powerups on the game grid. 
	 * There is no risk of overlap as a Tessera can hosts only one Prop. 
	 * It is important to use a support array for these operations 
	 * as you want to avoid a Concurrent Exception.
	 * This method is used by the DynaSlaveGame.
	 * 
	 * @param g2 graphic context
	 * @return updated drawn graphic context
	 */
	public Graphics2D renderPowerUps(Graphics2D g2) {
		CartesianCoordinate[] arrayDroppedPU = droppedPowerUps.keySet().toArray(new CartesianCoordinate[] {});
		for(CartesianCoordinate c : arrayDroppedPU) {
			DecoratedPowerUp p = (DecoratedPowerUp) droppedPowerUps.get(c);
	    	g2.drawImage(
	    			p.getImage(),
	    			p.getXPanel(), 
	    			p.getYPanel(),
					UNIT_NORMAL, 
					UNIT_NORMAL,
					null);
		}
		return g2;
	}
	
}
