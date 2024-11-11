package model;

/**
 * Interface of a Visitor Design Pattern
 */
public interface Visitor {

	/**
	 * Applied visit to PlayerCharacter
	 * @param pc player character to visit
	 */
	void visit(PlayerCharacter pc);
	/**
	 * Applied visit to NonPlayableCharacter
	 * @param npc non playable character to visit
	 */
	void visit(NonPlayableCharacter npc);
	/**
	 * Applied visit to Runner
	 * @param runner runner to visit
	 */
	void visit(Runner runner);
}
