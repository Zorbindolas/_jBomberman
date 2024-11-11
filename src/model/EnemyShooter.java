package model;

import java.util.ArrayList;
import java.util.List;

/**
 * An enemy that can shoots EnemyPyro in casual direction.
 */
public class EnemyShooter extends Enemy {
	
	/**
	 * Constructor of an EnemyShooter
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
	public EnemyShooter(
			int yPanel, int xPanel, int speed, int h, int w, int lives, 
			boolean scoreGiver, int scoreValue, NpcMediator npcMediator) {
		super(yPanel, xPanel, speed, h, w, lives, scoreGiver, 
				scoreValue, NpcType.SHOOTER,npcMediator);
	}
	
	@Override
	public void doAction() {
		if(isActionReady()) {
			List<Dir> dir = new ArrayList<>();
			dir.add(Dir.getCasualDirWithoutNone());
			npcMediator.addPyro(this.getPanelCoords(), dir, h, w, 6, npcMediator);
		}
		setActionReady(false);
	}
	
}
