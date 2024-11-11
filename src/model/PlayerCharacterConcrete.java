package model;
/**
 * Instance of a concrete player character
 */
public class PlayerCharacterConcrete extends PlayerCharacter {
	/**
	 * PlayerCharacterConcrete constructor
	 * @param hero pc's hero
	 * @param lives pc's starting lives
	 * @param attempts pc's starting attempts
	 * @param yPanel pc's y panel
	 * @param xPanel pc's x panel
	 * @param speed pc's starting speed
	 * @param h pc's Height
	 * @param w pc's Width
	 * @param releaseBehavior pc's starting releaseBehavior
	 * @param effectArea pc's starting effectArea
	 * @param triggerTime pc's starting triggerTime
	 * @param maxNumberOfBomb pc's starting max number of bombs
	 * @param hasRemoteBomb
	 * @param kantGlasses true if pc has kant glasses
	 */
	protected PlayerCharacterConcrete(
			Heroes hero,int lives, int attempts, int yPanel, int xPanel, 
			double speed, int h, int w, ReleaseBehavior releaseBehavior, 
			int effectArea, int triggerTime, int maxNumberOfBomb,
			boolean hasRemoteBomb, boolean kantGlasses) {
		super(hero,lives, attempts, yPanel, xPanel, speed, h, w, 
				releaseBehavior, effectArea, triggerTime, maxNumberOfBomb, kantGlasses);
		this.setHasRemoteBomb(hasRemoteBomb);
	}
	
	// builder pattern
	/**
	 * Builder Design Pattern for PlayerCharacterConcrete
	 */
		public static class PlayerCharacterBuilder {
			/**
			 * Builder's hero 
			 */
			private Heroes hero;
			/**
			 * Builder's attempts
			 */
			private int attempts;
			/**
			 * Builder's lives
			 */
			private int lives;
			/**
			 * Builder's y panel
			 */
			private int yPanel;
			/**
			 * Builder's x panel
			 */
			private int xPanel;
			/**
			 * Builder's speed
			 */
			private double speed;
			/**
			 * Builder's Height
			 */
			private int h;
			/**
			 * Builder's width
			 */
			private int w;
			/**
			 * Builder's releaseBehavior
			 */
			private ReleaseBehavior releaseBehavior;
			/**
			 * Builder's effectArea
			 */
			private int effectArea;
			/**
			 * Builder's maxNumberOfBomb
			 */
			private int maxNumberOfBomb;
			/**
			 * Builder's triggerTime
			 */
			private int triggerTime;
			/**
			 * Builder's hasRemoteBomb
			 */
			private boolean hasRemoteBomb;
			/**
			 * Builder's kantGlasses
			 */
			private boolean kantGlasses;
			/**
			 * Player Character Builder Constructor
			 * @param yPanel starting y panel
			 * @param xPanel starting x panel
			 * @param speed starting speed
			 * @param h Height
			 * @param w Width
			 */
			public PlayerCharacterBuilder(
					int yPanel, int xPanel, double speed, int h, int w) {
				
				this.hero = Heroes.WHITE_BOMBERMAN;
				
				this.lives = 1;
				this.attempts = 3;
				
				this.yPanel = yPanel;
				this.xPanel = xPanel;
				this.speed = speed;
				this.h = h;
				this.w = w;
				
				this.maxNumberOfBomb = 1;
				this.releaseBehavior = new DropBombStandard();
				this.effectArea = 0;
				this.triggerTime = 4;
				this.hasRemoteBomb = false;
			}
			/**
			 * Builder setter of hero
			 * @param hero new hero
			 * @return this builder object
			 */
			public PlayerCharacterBuilder setHero(Heroes hero) {
				this.hero = hero;
				return this;
			}
			/**
			 * Setter of builder attempts
			 * @param attempts new attempts
			 * @return this Builder object
			 */
			public PlayerCharacterBuilder setAttempts(int attempts) {
				this.attempts = attempts;
				return this;
			}
			/**
			 * Setter of builder lives
			 * @param lives new lives
			 * @return this builder object
			 */
			public PlayerCharacterBuilder setLives(int lives) {
				this.lives = lives;
				return this;
			}
			/**
			 * setter of builder release behavior
			 * @param releaseBehavior new releaseBehavior
			 * @return this builder object
			 */
			public PlayerCharacterBuilder setReleaseBehavior(ReleaseBehavior releaseBehavior) {
				if(hasRemoteBomb) {
					this.releaseBehavior = new DropBombRemote();
				} else {
					this.releaseBehavior = releaseBehavior;
				}
				return this;
			}
			/**
			 * Setter of builder hasTimerBomb 
			 * @return this builder object
			 */
			public PlayerCharacterBuilder setHasRemoteBomb() {
				this.hasRemoteBomb = true;
				this.releaseBehavior = new DropBombRemote();
				return this;
			}	
			/**
			 * Setter of builder kantGlasses
			 * @return this builder object
			 */
			public PlayerCharacterBuilder setKantGlasses() {
				this.kantGlasses = true;
				return this;
			}
			/**
			 * Setter of builder effectArea
			 * @param effectArea new effectArea
			 * @return this builder object
			 */
			public PlayerCharacterBuilder setEffectArea(int effectArea) {
				this.effectArea = effectArea;
				return this;
			}
			/**
			 * Setter of builder triggerTime
			 * @param triggerTime new triggerTime
			 * @return this builder object
			 */
			public PlayerCharacterBuilder setTriggerTime(int triggerTime) {
				this.triggerTime = triggerTime;
				return this;
			}
			/**
			 * Setter of builder max number of bombs
			 * @param maxNumberOfBomb new maxNumberOfBomb
			 * @return this builder object
			 */
			public PlayerCharacterBuilder setMaxNumbBombs(int maxNumberOfBomb) {
				this.maxNumberOfBomb = maxNumberOfBomb;
				return this;
			}
			/**
			 * Construct a concrete Player Character instance
			 * @return a new concrete player character
			 */
			public PlayerCharacterConcrete build() {
				return new PlayerCharacterConcrete(
						hero,lives,attempts,yPanel,xPanel,speed,h,w,
						releaseBehavior, effectArea, triggerTime, maxNumberOfBomb,
						hasRemoteBomb, kantGlasses
						);
			}
			
		}
	
	
}
