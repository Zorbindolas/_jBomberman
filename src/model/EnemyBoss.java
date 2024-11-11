package model;

import java.util.List;

/**
 * A special enemy whose defeat allows you to complete the stage
 */
public abstract class EnemyBoss extends EnemyUnstoppable implements Boss, Hound {

	/**
	 * Height and width of the star released by EnemyBoss's doAction 
	 */
	protected int hStar, wStar;

	/**
	 * Constructor of an EnemyBoss
	 * @param yPanel starting y panel
	 * @param xPanel starting x panel
	 * @param speed starting speed
	 * @param h fixed height
	 * @param w fixed width
	 * @param lives starting lives
	 * @param npcMediator enemy's npc mediator
	 */
	public EnemyBoss(int yPanel, int xPanel, int speed, 
			int h, int w, int lives,NpcMediator npcMediator) {
		super(yPanel, xPanel, speed, h, w, lives, true, 
				5000,npcMediator);
		this.npcMediator = npcMediator;
	}
		
	@Override
	public void doYourThing() {
		//System.out.println("passo di qui con "+this.getBehaviour());
		switch(this.getBehaviour()) {
			case REST -> {
				doRest();
				}
			case WALK -> {
				doWalk();
				}
			case ACTION -> {
				doAction();
				}
			default -> {}
		}
	}
	
	@Override
	public void doRest() {
		doAction();
	}
	
	@Override
	public void doAction() {
		if(isActionReady()) {
			shootStars();
		}
		setActionReady(false);
	}
	
	@Override
	public void unstoppableMove() {
		yPanel += modYPanel;
		xPanel += modXPanel;
	}
	
	@Override
	public void updateDirModders(List<Dir> dirs) {
		modXPanel = modYPanel = 0;
		for(Dir dir : dirs) {
			switch(dir) {
			case UP: modYPanel -= 1; break;
			case DOWN: modYPanel += 1; break;
			case LEFT: modXPanel -= 1; break;
			case RIGHT: modXPanel += 1; break;
			case NONE : break;
			}
		}
	}
	
	/**
	 * Method that use combinations of generateStar
	 * used in the EnemyBoss doAction
	 */
	protected abstract void shootStars();
	
	/**
	 * Method that calls npc mediator to add an EnemyPyro
	 * @param dirs EnemyPyro's directions
	 */
	protected abstract void generateStar(List<Dir> dirs);
	

	

}
