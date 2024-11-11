package model;

/**
 * Power Up class.
 * Object typed as Poweruppable can release a randomic Power up.
 */
public abstract class PowerUp extends Prop implements Powerupper {
	/**
	 * label of this powerup
	 */
	private Power label;
	/**
	 * reference to mediator
	 */
	private NpcMediator npcMediator;
	/**
	 * Types of powerup implemented
	 */
	public enum Power {
		/**
		 * EXPLOSION_EXPANDER POWERUP
		 */
		EXPLOSION_EXPANDER,
		/**
		 * EXTRA_BOMB POWERUP
		 */
		EXTRA_BOMB,
		/**
		 * EXTRA HEART POWERUP
		 */
		EXTRA_HEART,
		/**
		 * TIMER RESTORER POWERUP
		 */
		TIMER_RESTORER,
		/**
		 * INDESTRUCTIBLE ARMOR POWERUP
		 */
		INDESTRUCTIBLE_ARMOR,
		/**
		 * TABULA RASA POWERUP
		 */
		TABULA_RASA,
		/**
		 * ACCELERATOR POWERUP
		 */
		ACCELERATOR,
		/**
		 * FAST BOMB POWERUP
		 */
		FAST_BOMB,
		/**
		 * FOUR LEAF CLOVER POWERUP
		 */
		FOUR_LEAF_CLOVER;
	}
	/**
	 * PowerUp Constructor
	 * @param label powerup's label
	 * @param yGrid fixed y grid coordinate
	 * @param xGrid fixed x grid coordinate
	 * @param yPanel y panel coordinate
	 * @param xPanel x panel coordinate
	 * @param npcMediator mediator for powerup
	 */
	private PowerUp(
			Power label, int yGrid, int xGrid, int yPanel, int xPanel,
			NpcMediator npcMediator) {
		super(false, false);
		this.label = label;
		this.yGrid = yGrid;
		this.xGrid = xGrid;
		this.yPanel = yPanel;
		this.xPanel = xPanel;
		this.npcMediator = npcMediator;
	}
	/**
	 * Getter of powerup's label
	 * @return this label
	 */
	public Power getLabel() {return label;}
	/**
	 * Getter of npcMediator
	 * @return its npcMediator
	 */
	public NpcMediator getNpcMediator() {
		return npcMediator;
	}
	
	/**
	 * Generate a new Powerup using anonymous classes
	 * @param label powerup's label
	 * @param yGrid fixed y grid coordinate
	 * @param xGrid fixed x grid coordinate
	 * @param yPanel y panel coordinate
	 * @param xPanel x panel coordinate
	 * @param npcMediator mediator for powerup
	 * @return a concrete powerup
	 */
	public final static PowerUp newPowerUp(
			Power label, int yGrid, int xGrid, int yPanel, int xPanel,
			NpcMediator npcMediator) {
		
		PlayerCharacter player = GameModel.getInstance().getActualPC();
		
		switch(label) {
		case ACCELERATOR -> {
			return new PowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator) {

				@Override
				public void applyPowerUp() {
					player.incSpeed();
				}
				
				@Override
				public long getScoreValue() {
					return 100;
				}
				
			};
		}
		case EXPLOSION_EXPANDER -> {
			return new PowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator) {

				@Override
				public void applyPowerUp() {
					player.setEffectArea(
							player.getEffectArea()+1);
				}
				
				@Override
				public long getScoreValue() {
					return 50;
				}
				
			};
		}
		case EXTRA_BOMB -> {
			return new PowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator) {

				@Override
				public void applyPowerUp() {
					player.incMaxNumberOfBomb();
					//player.setHasRemoteBomb(true);		TODO	
				}
				
				@Override
				public long getScoreValue() {
					return 50;
				}
			};
		}
		case FAST_BOMB -> {
			return new PowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator) {

				@Override
				public void applyPowerUp() {
					player.setTriggerTime(
							player.getTriggerTime()-1
							);
				}
				
				@Override
				public long getScoreValue() {
					return 100;
				}
				
			};
		}
		case EXTRA_HEART -> {
			return new PowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator) {

				@Override
				public void applyPowerUp() {
					int lives = player.getLives();
					if(lives<9) {
						player.setLives(lives+1);
					}
				}
				
				@Override
				public long getScoreValue() {
					return 50;
				}
				
			};
		}
		case INDESTRUCTIBLE_ARMOR -> {
			return new PowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator) {

				@Override
				public void applyPowerUp() {
					player.setInvincibility(3);
				}
				
				@Override
				public long getScoreValue() {
					return 100;
				}
				
			};
		}
		case TABULA_RASA -> {
			return new PowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator) {

				@Override
				public void applyPowerUp() {
					player.decLives();
					player.setTriggerTime(
							player.getTriggerTime()+1
							);
					npcMediator.doKillEveryNpcOneTime();
				}
				
				@Override
				public long getScoreValue() {
					return 500;
				}
				
			};
		}
		case TIMER_RESTORER -> {
			return new PowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator) {
				
				@Override
				public void applyPowerUp() {
					if(
							(GameModel.getInstance().getPlayingTimer() * 2)
							< (99*60)) {
						
						GameModel.getInstance().setPlayingTimer(
								GameModel.getInstance().getPlayingTimer()*2
								);
					} else {
						GameModel.getInstance().setPlayingTimer(
								99*60
								);
					}
				}
				
				@Override
				public long getScoreValue() {
					return 200;
				}
				
			};
		}
		case FOUR_LEAF_CLOVER -> {
			return new PowerUp(label,yGrid,xGrid,yPanel,xPanel,npcMediator) {

				@Override
				public void applyPowerUp() {
					player.luckier();
				}
				
				@Override
				public long getScoreValue() {
					return 150;
				}
				
			};
		}
		default -> { return null; }
		}
		

	}

}
