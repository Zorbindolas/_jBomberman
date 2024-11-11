package model;

/**
 * This interface permits to locate Prop referral to the implementation 
 */
public interface PropWrapper {

	/**
	 * Return the Prop bundled to this Tessera
	 * @return this bundled Prop
	 */
	public Prop getBundle();
	/**
	 * This method changes Tessera's wrapped new Prop.
	 * The Referral to the previously Prop gets lost.
	 * @param insertion this inserted Prop
	 */
	public void setBundle(Prop insertion);

}
