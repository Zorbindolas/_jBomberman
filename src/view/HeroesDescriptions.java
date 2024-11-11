package view;

import model.Heroes;

// 		descriptionField.setText("<html><body><p style='width: 200px;'>\""+"aa"+"\"</p></body></html>" ); //"<html> <h1> testo </h1> <p> di </p> <p>prova</p> <p>prova</p> <p>prova</p> <p>prova</p> </html>");

/**
 * Description container to use for selecting characters in the DynaSlavePlayerNested.
 * All descriptions are formatted using HTML style paragraphs. 
 * In this way the performance of the GridBagLayout is better.
 */
public final class HeroesDescriptions {
	/**
	 * WhiteBomberman description.
	 */
	private final static String wbm = 
			"<html><body>"
			+ "<p>  </p>"
			+ "<p> White Bomberman is back.    </p>"
			+ "<p> He is ready to make all evil forces </p>"
			+ "<p> explode.  </p>"
			+ "<p>  </p>"
			+ "<p> Best whishes to every player.</p>"
			+ "<p>  </p>"
			+ "<p> WASD for moving. </p>"
			+ "<p> Press B for bomb dropping. </p>"
			+ "<p>  </p> </body></html>";
	/**
	 * MissDinahMight description.
	 */
	private final static String missDinah = 
			"<html><body>"
			+ "<p> The Ten Times BomberWoman Prize Winner </p>"
			+ "<p> isn't satisfied yet.    </p>"
			+ "<p>  </p>"
			+ "<p> Miss Dinah Might may walks with you. </p>"
			+ "<p> Be careful not to make her angry !</p>"
			+ "<p>  </p>"
			+ "<p> Dynamite runs into her veins. </p>"
			+ "<p>  </p>"
			+ "<p> It spurs out of her body when losing a life.</p> </body></html>";
	/**
	 * BaronBombarolo description.
	 */
	private final static String baronBomb = 
			"<html><body>"
			+ "<p> Bombarolo Baron is the luckiest</p>"
			+ "<p> bomberman ever. </p>"
			+ "<p> He's then the strongest one. </p>"
			+ "<p> </p>"
			+ "<p> Choose it if you want to stay safe. </p>"
			+ "<p> </p>"
			+ "<p> This videogame developer played with him </p>"
			+ "<p> to test some match workings. </p>"
			+ "<p> Do not overcome on powerups. </p> </body></html>";
	/**
	 * MechaBomberman description.
	 */
	private final static String mecha = 
			"<html><body>"
			+ "<p> Its related to a wider combat </p>"
			+ "<p> storage. </p>"
			+ "<p> MechaBomberman drops </p>"
			+ "<p> several more powerful bombs. </p>"
			+ "<p> It is able to move quicker.</p>"
			+ "<p> Future wars' soldiers will </p>"
			+ "<p> take it as a model. Meanwhile </p>"
			+ "<p> it prefers drinking some coffee. </p>"
			+ "<p> </p> </body></html>";
	/**
	 * RetroLady description.
	 */
	private final static String retroLady = 
			"<html><body>"
			+ "<p> RetroLady comes from such a far past. </p>"
			+ "<p> </p>"
			+ "<p> How would her elegance fit into </p>"
			+ "<p> Present Day ? </p>"
			+ "<p> </p>"
			+ "<p> Shall we give her the honour she deserves ? </p>"
			+ "<p> </p>"
			+ "<p> Just like her cat She can relies upon 9 lives. </p>"
			+ "<p> </p> </body></html>";
	/**
	 * MadMiner description.
	 */
	private final static String madMiner =
			"<html><body>"
			+ "<p> He is an old warrior </p>"
			+ "<p> and his time to die has come.</p>"
			+ "<p> Most of his body has been </p>"
			+ "<p> put into mecchanical parts. </p>"
			+ "<p> He is so fragile. </p>"
			+ "<p> So that now he seems to be a cyborg. </p>"
			+ "<p> His unique power consists in making </p>"
			+ "<p> bombs explode from a distance.</p>"
			+ "<p> He is my favorite character.</p> </body></html>";
	/**
	 * MaskedMagician description.
	 */
	private final static String maskedMagician = 
			"<html><body>"
			+ "<p> This already tall man </p>"
			+ "<p> wears a big hat to resemble still taller. </p>"
			+ "<p> Furthemore his shoes are high-heeled </p>"
			+ "<p> even if you can't notice them.</p>"
			+ "<p> He's not able to make </p>"
			+ "<p> bombs appear or disappear. Actually,</p>"
			+ "<p> He is the most perfect poker face.</p>"
			+ "<p> He can precisely spot every single enemy. </p>"
			+ "<p> </p> </body></html>";

	/**
	 * Getter of the specified hero description.
	 * @param hero hero whose description you want
	 * @return requested description
	 */
	public final static String getDescription(Heroes hero) {
		switch(hero) {
		case WHITE_BOMBERMAN -> {return wbm;}
		case MISS_DINAH_MIGHT -> {return missDinah;}
		case BARON_BOMBAROLO ->  {return baronBomb;}
		case MECHA_BOMBERMAN -> {return mecha;}
		case RETRO_LADY -> {return retroLady;}
		case MASKED_MAGICIAN -> {return maskedMagician;}
		case MAD_MINER -> {return madMiner;}
		default -> {return "";}
		}
	}
}
