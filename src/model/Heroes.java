package model;
/**
 * Set of predefined heroes playable in this video game
 */
public enum Heroes {
	/**
	 * WHITE_BOMBERMAN hero
	 */
	WHITE_BOMBERMAN, 
	/**
	 * MISS_DINAH_MIGHT hero
	 */
	MISS_DINAH_MIGHT, 
	/**
	 * BARON_BOMBAROLO hero
	 */
	BARON_BOMBAROLO, 
	/**
	 * MECHA_BOMBERMAN hero
	 */
	MECHA_BOMBERMAN, 
	/**
	 * RETRO_LADY hero
	 */
	RETRO_LADY,
	/**
	 * MAD_MINER hero
	 */
	MAD_MINER,
	/**
	 * MASKED_MAGICIAN hero
	 */
	MASKED_MAGICIAN;
	
	/**
	 * Return a name for a Hero
	 * @return Hero's name
	 */
	public String getName() {
		switch(this) {
		case WHITE_BOMBERMAN ->{return "White BomberMan";}
		case MISS_DINAH_MIGHT ->{return "Miss Dinah Might";}
		case BARON_BOMBAROLO ->{return "Baron Bombarolo";}
		case MECHA_BOMBERMAN ->{return "Mecha BomberMan";}
		case RETRO_LADY ->{return "Retro Lady";}
		case MAD_MINER ->{return "The Mad Miner";}
		case MASKED_MAGICIAN ->{return "The Masked Magician";}
		default ->{return "";}
		}	
	}
	
}
