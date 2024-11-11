package controller;

import model.PowerUp;
import model.Powerupper;
import model.NpcMediator;
import view.DecoratedPowerUp;
/**
 * This factory releases decorated version of power-ups.
 */
public class FactoryDecoratedPowerUp {

	/**
	 * Generate a powerupper from a powerup
	 * @param label powerup's label
	 * @param yGrid fixed y grid coordinate
	 * @param xGrid fixed x grid coordinate
	 * @param yPanel fixed y panel coordinate
	 * @param xPanel fixed x panel coordinate
	 * @param npcMediator npcMediator's reference (some poweruppers may be affect npcs)
	 * @return a DecoratedPowerUp (DecoratorProp implementing Powerupper)
	 */
	public static Powerupper getDecoratedPowerUp(
			PowerUp.Power label, int yGrid, int xGrid, int yPanel, int xPanel,
			NpcMediator npcMediator) {
		
		switch(label) {
			case ACCELERATOR -> {
				return new DecoratedPowerUp(
						PowerUp.newPowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator),
						new String[] {"/powerUps/accelerator0","/powerUps/accelerator1"}
								);
			}
			case EXPLOSION_EXPANDER -> {
				return new DecoratedPowerUp(
						PowerUp.newPowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator),
						new String[] {"/powerUps/explosionExpander0","/powerUps/explosionExpander1"}
								);
			}
			case EXTRA_BOMB -> {
				return new DecoratedPowerUp(
						PowerUp.newPowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator),
						new String[] {"/powerUps/extraBomb0","/powerUps/extraBomb1"}
								);
			}
			case FAST_BOMB -> {
				return new DecoratedPowerUp(
						PowerUp.newPowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator),
						new String[] {"/powerUps/fastBomb0","/powerUps/fastBomb1"}
								);
			}
			case EXTRA_HEART -> {
				return new DecoratedPowerUp(
						PowerUp.newPowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator),
						new String[] {"/powerUps/extraHeart0","/powerUps/extraHeart1"}
								);
			}
			case INDESTRUCTIBLE_ARMOR -> {
				return new DecoratedPowerUp(
						PowerUp.newPowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator),
						new String[] {"/powerUps/indestructibleArmor0","/powerUps/indestructibleArmor1"}
								);
			}
			case TABULA_RASA -> {
				return new DecoratedPowerUp(
						PowerUp.newPowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator),
						new String[] {"/powerUps/tabulaRasa0","/powerUps/tabulaRasa1"}
								);
			}
			case TIMER_RESTORER -> {
				return new DecoratedPowerUp(
						PowerUp.newPowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator),
						new String[] {"/powerUps/timerRestorer0","/powerUps/timerRestorer1"}
								);
			}
			case FOUR_LEAF_CLOVER -> {
				return new DecoratedPowerUp(
						PowerUp.newPowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator),
						new String[] {"/powerUps/fourLeafClover0","/powerUps/fourLeafClover1"}
								);
			}
			default -> { return null;}
		}

	}

}
