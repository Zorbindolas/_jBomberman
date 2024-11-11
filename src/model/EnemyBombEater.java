package model;

/**
 * An enemy that eats bombs it can find in the direction it is moving.
 */
public class EnemyBombEater extends Enemy {
	
	/**
	 * Constructor of an EnemyBombEater
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
	public EnemyBombEater(
			int yPanel, int xPanel, int speed, int h, int w, int lives, 
			boolean scoreGiver, int scoreValue,	NpcMediator npcMediator) {
		super(yPanel, xPanel, speed, h, w, lives, scoreGiver, 
				scoreValue, NpcType.BOMB_EATER,npcMediator);
	}	
	
}
