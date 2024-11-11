package model;

import java.util.List;

/**
 * This enemy represents a special effects that damage the player character
 * as an enemy when collides with him.
 */
public class EnemyPyro extends EnemyUnstoppable {
	/**
	 * Constructor of an EnemyPyro.
	 * Panel modifiers are fixed at creation.
	 * This character can only move in a specific direction.
	 * When it leaves the grid it is destroyed by the mediator.
	 * @param yPanel starting y panel
	 * @param xPanel starting x panel
	 * @param speed starting speed
	 * @param h enemy's Height
	 * @param w enemy's Width
	 * @param lives starting lives
	 * @param scoreGiver if enemy is a scoreGiver
	 * @param scoreValue enemy's score value
	 * @param dirs this enemy modifiers direction
	 * @param npcMediator enemy's npc mediator
	 */
	public EnemyPyro(int yPanel, int xPanel, 
			int speed, int h, int w, int lives, 
			boolean scoreGiver, int scoreValue,
			List<Dir> dirs,NpcMediator npcMediator) {
		
		super(yPanel, xPanel, speed, h, w, lives, 
				scoreGiver, scoreValue,npcMediator);
		
		modXPanel = modYPanel = 0;
		
		for(Dir dir : dirs) {
			switch(dir) {
			case UP: modYPanel -= speed; break;
			case DOWN: modYPanel += speed; break;
			case LEFT: modXPanel -= speed; break;
			case RIGHT: modXPanel += speed; break;
			case NONE : break;
			}
		}

	}
	
	@Override
	public void doYourThing() {
		doWalk();
	}
	
	@Override
	public void doRest() {
		doWalk();
	}
	
	@Override
	public void doAction() {
		doWalk();
	}
	
	@Override
	public void unstoppableMove() {
		yPanel += modYPanel;
		xPanel += modXPanel;
	}

}
