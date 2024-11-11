package controller;

import model.Heroes;
import model.PlayerCharacterConcrete;
import view.DecoratedPlayerCharacter;
import view.MyJBombermanFormat;

/**
 * This factory contains all the information and methods needed to create 
 * any playable character of this game.
 */
public class FactoryDecoratedPlayerCharacter implements MyJBombermanFormat {
	/**
	 * It supplies the decorated player character from its Heroes label
	 * @param hero label identifies the player character chosen
	 * @return a new decorated player character
	 */
	public static DecoratedPlayerCharacter getHero(
			Heroes hero) {
		
		switch(hero) {
		case WHITE_BOMBERMAN -> {return whiteBomberman();}
		case MISS_DINAH_MIGHT -> {return missDinahMight();}
		case RETRO_LADY -> {return retroLady();}
		case BARON_BOMBAROLO -> {return baronBombarolo();}
		case MECHA_BOMBERMAN -> {return mechaBomberman();}
		case MASKED_MAGICIAN -> {return maskMagician();}
		case MAD_MINER -> {return madMiner();}
		// case BOMBERMAN_1983 (the first)
		// case PICCOLO_ROSSO
		default -> {return null;}
		}
		
	}
	/**
	 * Create the Decorated Player Character White Bomberman
	 * @return a decorated player character
	 */
	private static DecoratedPlayerCharacter whiteBomberman() { 
		
		PlayerCharacterConcrete playerCharacter = 
				new PlayerCharacterConcrete.PlayerCharacterBuilder(
						START_PC_Y,
						START_PC_X,
						SPEED_STANDARD,
						UNIT_MEGA,
						UNIT_NORMAL)
				.setLives(3)
				.build();
		return new DecoratedPlayerCharacter(playerCharacter,"/pgWbm/",3,6,5,0);
	}
	/**
	 * Create the Decorated Player Character Miss Dinah Might
	 * @return a decorated player character
	 */
	private static DecoratedPlayerCharacter missDinahMight() { 
		
		PlayerCharacterConcrete playerCharacter = 
				new PlayerCharacterConcrete.PlayerCharacterBuilder(
						START_PC_Y,
						START_PC_X,
						SPEED_STANDARD,
						UNIT_MEGA,
						UNIT_NORMAL)
				.setHero(Heroes.MISS_DINAH_MIGHT)
				.setAttempts(1)
				.setLives(3)
				.build();
		return new DecoratedPlayerCharacter(playerCharacter,"/pgMDM/",3,2,8,0);
	}
	/**
	 * Create the Decorated Player Character Retro Lady
	 * @return a decorated player character
	 */
	private static DecoratedPlayerCharacter retroLady() { 
		
		PlayerCharacterConcrete playerCharacter = 
				new PlayerCharacterConcrete.PlayerCharacterBuilder(
						START_PC_Y,
						START_PC_X,
						SPEED_STANDARD,
						UNIT_MEGA,
						UNIT_NORMAL)
				.setHero(Heroes.RETRO_LADY)
				.setAttempts(0)
				.setLives(9)
				.build();
		return new DecoratedPlayerCharacter(playerCharacter,"/pgRL/",3,7,4,0);
	}
	/**
	 * Create the Decorated Player Character Baron Bombarolo
	 * @return a decorated player character
	 */
	private static DecoratedPlayerCharacter baronBombarolo() {
		
		PlayerCharacterConcrete playerCharacter = 
				new PlayerCharacterConcrete.PlayerCharacterBuilder(
						START_PC_Y,
						START_PC_X,
						SPEED_STANDARD,
						UNIT_MEGA,
						UNIT_NORMAL)
				.setHero(Heroes.BARON_BOMBAROLO)
				.build();
		for(int i=0;i<8;i++) playerCharacter.luckier();
		return new DecoratedPlayerCharacter(playerCharacter,"/pgBB/",3,4,4,6);
	}
	/**
	 * Create the Decorated Player Character Mecha Bomberman
	 * @return a decorated player character
	 */
	private static DecoratedPlayerCharacter mechaBomberman() {
		
		PlayerCharacterConcrete playerCharacter = 
				new PlayerCharacterConcrete.PlayerCharacterBuilder(
						START_PC_Y,
						START_PC_X,
						SPEED_STANDARD,
						UNIT_MEGA,
						UNIT_NORMAL)
				.setHero(Heroes.MECHA_BOMBERMAN)
				.build();
		playerCharacter.incMaxNumberOfBomb();
		playerCharacter.incMaxNumberOfBomb();
		playerCharacter.setEffectArea(2);
		playerCharacter.setSpeed(SPEED_STANDARD+1);
		return new DecoratedPlayerCharacter(playerCharacter,"/pgMB/",3,7,8,0);
	}
	/**
	 * Create the Decorated Player Character Mad Miner
	 * @return a decorated player character
	 */
	private static DecoratedPlayerCharacter madMiner() {
		
		PlayerCharacterConcrete playerCharacter = 
				new PlayerCharacterConcrete.PlayerCharacterBuilder(
						START_PC_Y,
						START_PC_X,
						SPEED_STANDARD,
						UNIT_MEGA,
						UNIT_NORMAL)
				.setHero(Heroes.MAD_MINER)
				.setAttempts(0)
				.setLives(0)
				.setHasRemoteBomb()
				.build();
		return new DecoratedPlayerCharacter(playerCharacter,"/pgMadMiner/",3,5,10,0);
	}
	/**
	 * Create the Decorated Player Character Mask Magician
	 * @return a decorated player character
	 */
	private static DecoratedPlayerCharacter maskMagician() {
		
		PlayerCharacterConcrete playerCharacter = 
				new PlayerCharacterConcrete.PlayerCharacterBuilder(
						START_PC_Y,
						START_PC_X,
						SPEED_STANDARD,
						UNIT_MEGA,
						UNIT_NORMAL)
				.setHero(Heroes.MASKED_MAGICIAN)
				.setKantGlasses()
				.build();
		return new DecoratedPlayerCharacter(playerCharacter,"/pgMKM/",3,6,4,18);
	}
	

}
