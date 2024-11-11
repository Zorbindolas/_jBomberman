package model;

import java.util.Arrays;
import java.util.List;

/**
 * A concrete EnemyBoss object
 */
public class EnemyBossClown extends EnemyBoss {

	/**
	 * Constructor of EnemyBossClown
	 * @param yPanel starting y panel
	 * @param xPanel starting x panel
	 * @param h boss height
	 * @param w boss width
	 * @param npcMediator boss' npc mediator
	 */
	public EnemyBossClown(
			int yPanel, int xPanel, int h, int w, NpcMediator npcMediator) {
		super(yPanel, xPanel, 6, h, w, 7, npcMediator);
	}
	
	@Override
	protected void shootStars() {
//		npcMediator.add(generateStar(Arrays.asList(new Dir[] {Dir.UP}))); //Attention to ClassCastException
		generateStar(Arrays.asList(new Dir[] {Dir.UP}));
		generateStar(Arrays.asList(new Dir[] {Dir.RIGHT}));
		generateStar(Arrays.asList(new Dir[] {Dir.DOWN}));
		generateStar(Arrays.asList(new Dir[] {Dir.LEFT}));
		generateStar(Arrays.asList(new Dir[] {Dir.UP,Dir.RIGHT}));
		generateStar(Arrays.asList(new Dir[] {Dir.UP,Dir.LEFT}));
		generateStar(Arrays.asList(new Dir[] {Dir.DOWN,Dir.RIGHT}));
		generateStar(Arrays.asList(new Dir[] {Dir.DOWN,Dir.LEFT}));
	}
	
	@Override
	protected void generateStar(List<Dir> dirs) {
		npcMediator.addPyro(getGravityCoords(), dirs, hStar, wStar, 6, npcMediator);
	}

}
