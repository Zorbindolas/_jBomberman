package model;

/**
 * An NPC with hostility true against the player character.
 */
public abstract class Enemy extends NonPlayableCharacter {

	/**
	 * Constructor of an Enemy
	 * @param yPanel starting y panel
	 * @param xPanel starting x panel
	 * @param speed starting speed
	 * @param h enemy's Height
	 * @param w enemy's Width
	 * @param lives starting lives
	 * @param scoreGiver if enemy is a scoreGiver
	 * @param scoreValue enemy's score value
	 * @param npcType enemy's npc type
	 * @param npcMediator enemy's npc mediator
	 */
	public Enemy(
			int yPanel, int xPanel, int speed, 
			int h, int w, int lives,
			boolean scoreGiver,int scoreValue,
			NpcType npcType,NpcMediator npcMediator) {
		super(yPanel, xPanel, speed, h, w, lives, 
				true, scoreGiver, scoreValue,npcType,npcMediator);
	}

	/**
	 * An enemy's resonance effect towards the player results in the loss of a life
	 */
	@Override
	public void resonanceOnPc(PlayerCharacter pc) { // Monsters take off one life from pc if they collide
		if(pc.getInvincibility()<1) pc.decLives();
	}


}
