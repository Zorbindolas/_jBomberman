package model;

import java.util.Arrays;
import java.util.List;
/**
 * A concrete EnemyBoss object
 */
public class EnemyBossSergeant extends EnemyBoss {

	/**
	 * Counter to manage this special shootStars
	 */
	private int counter;
	
	/**
	 * Constructor of EnemyBossSergeant
	 * @param yPanel starting y panel
	 * @param xPanel starting x panel
	 * @param h boss height
	 * @param w boss width
	 * @param npcMediator boss' npc mediator
	 */
	public EnemyBossSergeant(
			int yPanel, int xPanel, int h, int w, NpcMediator npcMediator) {
		super(yPanel, xPanel, 9, h, w, 7, npcMediator);
	}
	
	@Override
	protected void shootStars() {
		counter++;
		if(counter>3) counter = 0;
		switch(counter) {
			case 0 -> {		
				generateStar(Arrays.asList(new Dir[] {Dir.UP}));
				generateStar(Arrays.asList(new Dir[] {Dir.UP,Dir.RIGHT}));
				generateStar(Arrays.asList(new Dir[] {Dir.UP,Dir.LEFT}));
			}
			case 1 -> {
				generateStar(Arrays.asList(new Dir[] {Dir.RIGHT}));
				generateStar(Arrays.asList(new Dir[] {Dir.DOWN,Dir.RIGHT}));
				generateStar(Arrays.asList(new Dir[] {Dir.UP,Dir.RIGHT}));
			}
			case 2 -> {
				generateStar(Arrays.asList(new Dir[] {Dir.DOWN}));
				generateStar(Arrays.asList(new Dir[] {Dir.DOWN,Dir.RIGHT}));
				generateStar(Arrays.asList(new Dir[] {Dir.DOWN,Dir.LEFT}));
			}
			case 3 -> {
				generateStar(Arrays.asList(new Dir[] {Dir.LEFT}));
				generateStar(Arrays.asList(new Dir[] {Dir.DOWN,Dir.LEFT}));
				generateStar(Arrays.asList(new Dir[] {Dir.UP,Dir.LEFT}));
			}
		}
	}
	
	@Override
	protected void generateStar(List<Dir> dirs) {
		npcMediator.addPyro(getGravityCoords(), dirs, hStar, wStar, 9, npcMediator);
	}

	
	
	
	
	
	
	
	
	
}
