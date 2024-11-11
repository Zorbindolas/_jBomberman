package model;

/**
 * An enemy that can fly without limits in the game grid.
 */
public class EnemyRunner extends Enemy implements Runner{
	
	/**
	 * Constructor of an EnemyRunner
	 * @param yPanel starting y panel
	 * @param xPanel starting x panel
	 * @param speed starting speed
	 * @param h enemy's Height
	 * @param w enemy's Width
	 * @param lives starting lives
	 * @param scoreGiver if enemy is a scoreGiver
	 * @param scoreValue enemy's score value
	 * @param npcMediator enemy's npc mediator
	 */
	public EnemyRunner(
			int yPanel, int xPanel, int speed, int h, int w, int lives, 
			boolean scoreGiver, int scoreValue, NpcMediator npcMediator) {
		super(yPanel, xPanel, speed, h, w, lives, scoreGiver, 
				scoreValue, NpcType.RUNNER,npcMediator);
	}

	@Override
	public void doYourThing() {
		if(activityTimer>0) {
			switch(this.getBehaviour()) {
				case REST -> {
					doWalk();
					}
				case WALK -> {
					doWalk();
					}
				case ACTION -> {
					this.setDir(Dir.NONE);
					doAction();
					doWalk();
					}
				default -> {}
			}
		}
	}
	
}
