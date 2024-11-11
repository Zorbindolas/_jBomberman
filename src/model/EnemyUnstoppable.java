package model;

/**
 * An unstoppable enemy doesn't use visitor mover for moving
 * so it isn't affect by solidity collisions
 */
public abstract class EnemyUnstoppable extends Enemy {

	/**
	 * Modifier of panel coordinates during moving action
	 */
	protected int modXPanel, modYPanel;
	
	/**
	 * Constructor of an unstoppable enemy
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
	public EnemyUnstoppable(
			int yPanel, int xPanel, int speed, int h, int w, int lives, 
			boolean scoreGiver, int scoreValue, NpcMediator npcMediator) {
		super(yPanel, xPanel, speed, h, w, lives, scoreGiver, 
				scoreValue, NpcType.UNSTOPPABLE,npcMediator);
		this.setHW(h, w);
	}
	/**
	 * An EnemyUnstoppable has a special solidity area
	 */
	@Override
	public void setHW(int h, int w) {
		this.h = h;
		this.w = w;
		this.ySolid1 = h/4;
		this.ySolid2 = h*3/4;
		this.xSolid1 = w/4;
		this.xSolid2 = w*3/4;
		this.xOffsetGravityCenter = xSolid1 + (xSolid2 - xSolid1)/2;
		this.yOffsetGravityCenter = ySolid1 + (ySolid2 - ySolid1)/2;
	}
//	@Override
//	public void setHW(int h, int w) {
//		this.h = h;
//		this.w = w;
//		this.ySolid1 = 0;
//		this.ySolid2 = h;
//		this.xSolid1 = 0;
//		this.xSolid2 = w;
//		this.xOffsetGravityCenter = xSolid1 + (xSolid2 - xSolid1)/2;
//		this.yOffsetGravityCenter = ySolid1 + (ySolid2 - ySolid1)/2;
//	}
	
	@Override
	public void doWalk() {
		this.unstoppableMove();
	}
	
	/**
	 * Methods to determine new panel coordinates after a moving operation
	 */
	public abstract void unstoppableMove();
	


}
