package view;

import model.NonPlayableCharacter;
import model.Runner;
/**
 * This class is a DecoratedNpcDuo class with Runner type.
 */
public class DecoratedNpcDuoRunner extends DecoratedNpcDuo implements Runner {
	/**
	 * Constructor
	 * @param npc component to decorate
	 * @param path path for images loading
	 */
	public DecoratedNpcDuoRunner(NonPlayableCharacter npc, String path) {
		super(npc, path);
	}

}
