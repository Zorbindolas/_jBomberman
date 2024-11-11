package model;
/**
 * Interface implemented by concrete powerup
 */
public interface Powerupper {
	/**
	 * Effect of this powerup
	 */
	public void applyPowerUp();
	/**
	 * Score given by this powerup
	 * @return score given
	 */
	public long getScoreValue();
	
}
