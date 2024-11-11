package controller;

import java.util.HashSet;
import java.util.Set;

import model.Enemy;
import model.EnemyBombEater;
import model.EnemyBoss;
import model.EnemyBossClown;
import model.EnemyBossSergeant;
import model.EnemyBossUfoBrain;
import model.EnemyRunner;
import model.EnemyShooter;
import model.NonPlayableCharacter;
import model.NpcType;
import model.Theater;

import view.DecoratedNpcDuo;
import view.DecoratedNpcDuoRunner;
import view.DecoratedNpcMono;
import view.DecoratedEnemyBoss;
import view.DecoratedNpcTetra;
import view.DecoratedNonPlayableCharacter;
import view.EnjoyNpcMediator;
import view.MyJBombermanFormat;
/**
 * This factory contains all the information and methods needed to create 
 * non playable character set for any level of the game.
 */
public class FactoryDecoratedNPC implements MyJBombermanFormat {
	/**
	 * Boss Standard Height
	 */
	private static final int H_BOSS = UNIT_MEGA*3;
	/**
	 * Boss Standard Width
	 */
	private static final int W_BOSS = UNIT_MEGA*4;
	/**
	 * Creates the non playable character set that corresponds to the requested level's theater
	 * @param theater theater identifies the level
	 * @return the created npc set
	 */
	public static Set<NonPlayableCharacter> getNPCSet(
			Theater theater) {
		Set<NonPlayableCharacter> setNpc = new HashSet<NonPlayableCharacter>();
		switch(theater) {
		// Stage: City
			case LEVEL_1_1 -> {			
				setNpc.add(ufoHitchHiker());
				setNpc.add(ufoHitchHiker());
				setNpc.add(ufoHitchHiker());
				setNpc.add(ufoHitchHiker());
			}
			case LEVEL_1_2 -> {
				setNpc.add(ufoHitchHiker());
				setNpc.add(ufoHitchHiker());
				setNpc.add(ufoSpinner());
				setNpc.add(ufoSpinner());
			}
			case LEVEL_1_3 -> {
				setNpc.add(ufoSpinner());
				setNpc.add(ufoSpinner());
				setNpc.add(ufoSpinner());
				setNpc.add(ufoSpinner());
			}
			case LEVEL_1_4 -> {
				setNpc.add(ufoKidnapper());
				setNpc.add(ufoKidnapper());
				setNpc.add(ufoKidnapper());
				setNpc.add(ufoKidnapper());
			}
			case LEVEL_1_5 -> {
				setNpc.add(ufoBulb());
				setNpc.add(ufoBulb());
				setNpc.add(ufoBulb());
				setNpc.add(ufoBulb());
			}
			case LEVEL_1_6 -> {
				setNpc.add(ufoSpitting());
				setNpc.add(ufoSpitting());
				setNpc.add(ufoSpitting());
				setNpc.add(ufoSpitting());
			}
			case LEVEL_1_7 -> {
				setNpc.add(ufoHitchHiker());
				setNpc.add(ufoKidnapper());
				setNpc.add(ufoSpinner());
				setNpc.add(ufoBulb());
				setNpc.add(ufoSpitting());
			}
			case LEVEL_1_8 -> {
				setNpc.add(mrBrainBomb()); 
			}
			// Stage: Jungle
			case LEVEL_2_1 -> {
				setNpc.add(mrSoldier());
				setNpc.add(mrSoldier());
				setNpc.add(mrSoldier());
				setNpc.add(mrSoldier());
				setNpc.add(mrSoldier());
				setNpc.add(mrSoldier());
				setNpc.add(mrSoldier());
			}
			case LEVEL_2_2 -> {
				setNpc.add(mrSoldier());
				setNpc.add(mrSoldier());
				setNpc.add(mrSoldier());
				setNpc.add(mrHellcopter());
				setNpc.add(mrHellcopter());
				setNpc.add(mrHellcopter());
			}
			case LEVEL_2_3 -> {
				setNpc.add(mrHellcopter());
				setNpc.add(mrHellcopter());
				setNpc.add(mrHellcopter());
				setNpc.add(mrHellcopter());
				setNpc.add(mrHellcopter());
				setNpc.add(mrHellcopter());
				setNpc.add(mrHellcopter());
			}
			case LEVEL_2_4 -> {
				setNpc.add(mrLeadEater());
				setNpc.add(mrLeadEater());
				setNpc.add(mrLeadEater());
				setNpc.add(mrLeadEater());
				setNpc.add(mrLeadEater());
				setNpc.add(mrLeadEater());
				setNpc.add(mrLeadEater());
			}
			case LEVEL_2_5 -> {
				setNpc.add(mrSharkMissile());
				setNpc.add(mrSharkMissile());
				setNpc.add(mrSharkMissile());
				setNpc.add(mrSharkMissile());
				setNpc.add(mrSharkMissile());
				setNpc.add(mrSharkMissile());
				setNpc.add(mrSharkMissile());
			}
			case LEVEL_2_6 -> {
				setNpc.add(mrTank());
				setNpc.add(mrTank());
				setNpc.add(mrTank());
				setNpc.add(mrTank());
				setNpc.add(mrTank());
				setNpc.add(mrTank());
				setNpc.add(mrTank());
			}
			case LEVEL_2_7 -> {
				setNpc.add(mrSoldier());
				setNpc.add(mrSoldier());
				setNpc.add(mrHellcopter());
				setNpc.add(mrHellcopter());
				setNpc.add(mrLeadEater());
				setNpc.add(mrLeadEater());
				setNpc.add(mrSharkMissile());
				setNpc.add(mrSharkMissile());
				setNpc.add(mrTank());
				setNpc.add(mrTank());
			}
			case LEVEL_2_8 -> {
				setNpc.add(mrSergeantBomb());
			}
			// Stage: Classical 
			case LEVEL_3_1 -> {					
				setNpc.add(mrBaloon());
				setNpc.add(mrBaloon());
			}
			case LEVEL_3_2 -> {
				setNpc.add(mrBaloon());
				setNpc.add(mrDroppy());
			}
			case LEVEL_3_3 -> {
				setNpc.add(mrDroppy());
				setNpc.add(mrLoveBuzz());
			}
			case LEVEL_3_4 -> {
				setNpc.add(mrLoveBuzz());
				setNpc.add(mrLoveBuzz());
				setNpc.add(mrLoveBuzz());
			}
			case LEVEL_3_5 -> {
				setNpc.add(mrBlueBlob());
				setNpc.add(mrBlueBlob());
			}
			case LEVEL_3_6 -> {
				setNpc.add(mrSmiley());
				setNpc.add(mrSmiley());
			}
			case LEVEL_3_7 -> {
				setNpc.add(mrBaloon());
				setNpc.add(mrDroppy());
				setNpc.add(mrBlueBlob());
				setNpc.add(mrLoveBuzz());
				setNpc.add(mrSmiley());
			}
			case LEVEL_3_8 -> {
				setNpc.add(mrBossClown());
			}
			default -> {return setNpc;}
		}
		return setNpc;
	}
	
	// Archetypes with anonymous classes
	/**
	 * It supplies a gregarius enemy
	 * @return an anonymous gregarius enemy
	 */
	private static Enemy enemyGregarius() {
		return new Enemy(
				0,0,1,
				0,0, 
				1,true,50,
				NpcType.GREGARIUS,
				EnjoyNpcMediator.getInstance()
				) {};
	}
	/**
	 * It supplies a standard enemy
	 * @return an anonymous standard enemy
	 */
	private static Enemy enemyStandard() {
		return new Enemy(
				0,0,2,
				0,0, 
				2,true,150,
				NpcType.STANDARD,
				EnjoyNpcMediator.getInstance()
				) {};
	}
	/**
	 * It supplies a bombEater enemy
	 * @return an anonymous bombEater enemy
	 */
	private static Enemy enemyBombEater() {
		return new EnemyBombEater(
				0,0,3,
				0,0, 
				3,true,300,
				EnjoyNpcMediator.getInstance()
				) {};
	}
	/**
	 * It supplies a runner enemy
	 * @return an anonymous runner enemy
	 */
	private static Enemy enemyRunner() {
		return new EnemyRunner(
				0,0,5,
				0,0, 
				3,true,400,
				EnjoyNpcMediator.getInstance()
				) {};
	}
	/**
	 * It supplies a shooter enemy
	 * @return an anonymous shooter enemy
	 */
	private static Enemy enemyShooter() {
		return new EnemyShooter(
				0,0,4,
				0,0, 
				4,true,500,
				EnjoyNpcMediator.getInstance()
				) {};
	}
	
//	private static DecoratedNonPlayableCharacter mrPyroWhiteBlue(List<Dir> dirs) { 
//		EnemyPyro enemyPyro = new EnemyPyro(
//				0,0,7,
//				UNIT_MEGA, 
//				UNIT_NORMAL,
//				2,false,0,
//				dirs,
//				SuperNpcMediator.getInstance()
//				);
//		return new DecoratedMrPyroWhiteBlue(enemyPyro);
//	}
	
	// Gregarius
	/**
	 * It supplies decorated gregarius for city stage
	 * @return a decorated gregarius
	 */
	private static DecoratedNonPlayableCharacter ufoHitchHiker() { 
		Enemy enemy = enemyGregarius();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);		
		return new DecoratedNpcTetra(enemy,"/ufoHitchHiker/");
	}
	
//	private static DecoratedNonPlayableCharacter mrFireman() { 
//		Enemy enemy = enemyGregarius();
//		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);		
//		return new DecoratedNpcTetra(enemy,"/mrFireman/");
//	}
	/**
	 * It supplies decorated gregarius for jungle stage
	 * @return a decorated gregarius
	 */
	private static DecoratedNonPlayableCharacter mrSoldier() { 
		Enemy enemy = enemyGregarius();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);		
		return new DecoratedNpcTetra(enemy,"/mrSoldier/");
	}
	/**
	 * It supplies decorated gregarius for classical stage
	 * @return a decorated gregarius
	 */
	private static DecoratedNonPlayableCharacter mrBaloon() { 
		Enemy enemy = enemyGregarius();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);		
		return new DecoratedNpcDuo(enemy,"/mrBaloon/");
	}
	
	// Standards
	/**
	 * It supplies decorated standard enemy for city stage
	 * @return a decorated standard enemy
	 */
	private static DecoratedNonPlayableCharacter ufoSpinner() { 
		Enemy enemy = enemyStandard();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);
		return new DecoratedNpcMono(enemy,"/ufoSpinner/",9);
	}
	/**
	 * It supplies decorated standard enemy for jungle stage
	 * @return a decorated standard enemy
	 */
	private static DecoratedNonPlayableCharacter mrHellcopter() { 
		Enemy enemy = enemyStandard();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);
		return new DecoratedNpcTetra(enemy,"/mrHellcopter/");
	}
	/**
	 * It supplies decorated standard enemy for classical stage
	 * @return a decorated standard enemy
	 */
	private static DecoratedNonPlayableCharacter mrDroppy() { 
		Enemy enemy = enemyStandard();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);
		return new DecoratedNpcDuo(enemy,"/mrDroppy/");
	}
	
	// Runner npcs
	/**
	 * It supplies decorated runner enemy for city stage
	 * @return a decorated runner enemy
	 */
	private static DecoratedNonPlayableCharacter ufoBulb() { 
		Enemy enemy = enemyRunner();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);		
		return new DecoratedNpcMono(enemy,"/ufoBulb/",6);
	}
	/**
	 * It supplies decorated runner enemy for jungle stage
	 * @return a decorated runner enemy
	 */
	private static DecoratedNonPlayableCharacter mrSharkMissile() { 
		Enemy enemy = enemyRunner();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);		
		return new DecoratedNpcTetra(enemy,"/mrSharkMissile/");
	}
	/**
	 * It supplies decorated runner enemy for classical stage
	 * @return a decorated runner enemy
	 */
	private static DecoratedNonPlayableCharacter mrBlueBlob() { 
		Enemy enemy = enemyRunner();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);		
		return new DecoratedNpcDuoRunner(enemy,"/mrBlueBlob/");
	}
	
	// Bomb Eaters
	/**
	 * It supplies decorated bombEater enemy for city stage
	 * @return a decorated bombEater enemy
	 */
	private static DecoratedNonPlayableCharacter ufoKidnapper() { 
		Enemy enemy = enemyBombEater();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);		
		return new DecoratedNpcMono(enemy,"/ufoKidnapper/",13);
	}
	/**
	 * It supplies decorated bombEater enemy for jungle stage
	 * @return a decorated bombEater enemy
	 */
	private static DecoratedNonPlayableCharacter mrLeadEater() { 
		Enemy enemy = enemyBombEater();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);		
		return new DecoratedNpcMono(enemy,"/mrLeadEater/",7);
	}
	/**
	 * It supplies decorated bombEater enemy for classical stage
	 * @return a decorated bombEater enemy
	 */
	private static DecoratedNonPlayableCharacter mrLoveBuzz() { 
		Enemy enemy = enemyBombEater();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);		
		return new DecoratedNpcDuo(enemy,"/mrLoveBuzz/");
	}
	
	// Shooters
	/**
	 * It supplies decorated shooter enemy for city stage
	 * @return a decorated shooter enemy
	 */
	private static DecoratedNonPlayableCharacter ufoSpitting() { 
		Enemy enemy = enemyShooter();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);		
		return new DecoratedNpcMono(enemy,"/ufoSpitting/",9);
	}
	/**
	 * It supplies decorated shooter enemy for jungle stage
	 * @return a decorated shooter enemy
	 */
	private static DecoratedNonPlayableCharacter mrTank() { 
		Enemy enemy = enemyShooter();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);		
		return new DecoratedNpcTetra(enemy,"/mrTank/");
	}
	/**
	 * It supplies decorated shooter enemy for classical stage
	 * @return a decorated shooter enemy
	 */
	private static DecoratedNonPlayableCharacter mrSmiley() { 
		Enemy enemy = enemyShooter();
		enemy.setHW(UNIT_MEGA, UNIT_NORMAL);		
		return new DecoratedNpcDuo(enemy,"/mrSmiley/");
	}
	
	// Bosses
	/**
	 * It supplies decorated boss enemy for city stage
	 * @return a decorated boss enemy
	 */
	private static DecoratedEnemyBoss mrBrainBomb() {
		EnemyBoss enemyBoss = new EnemyBossUfoBrain(
				0,0,
				H_BOSS,
				W_BOSS,
				EnjoyNpcMediator.getInstance()
				) {};
		return new DecoratedEnemyBoss(enemyBoss,"/ufoBrain/");
	}
	/**
	 * It supplies decorated boss enemy for jungle stage
	 * @return a decorated boss enemy
	 */
	private static DecoratedEnemyBoss mrSergeantBomb() {
		EnemyBoss enemyBoss = new EnemyBossSergeant(
				0,0,
				H_BOSS,
				W_BOSS,
				EnjoyNpcMediator.getInstance()
				) {};
		return new DecoratedEnemyBoss(enemyBoss,"/mrSergeantBomb/");
	}
	/**
	 * It supplies decorated boss enemy for classical stage
	 * @return a decorated boss enemy
	 */
	private static DecoratedEnemyBoss mrBossClown() {
		EnemyBoss enemyBoss = new EnemyBossClown(
				0,0,
				H_BOSS,
				W_BOSS,
				EnjoyNpcMediator.getInstance()
				);
		return new DecoratedEnemyBoss(enemyBoss,"/mrBossClown/");
	}
	
	
	
}
