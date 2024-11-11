package model;

/**
 * Interface implemented by remote bombs that defines their particular methods
 */
public interface TimerExplosion {

	/**
	 * Getter of boolean representation of trigger state of this remote bomb.
	 * Return true if the bomb is triggered.
	 * @return trigger state
	 */
	public boolean isTriggered();
	/**
	 * Convert to true the triggered state
	 */
	public void trigger();
}
