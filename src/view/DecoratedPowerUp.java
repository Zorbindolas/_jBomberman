package view;

import model.PowerUp;
import model.Powerupper;
import model.PowerUp.Power;
/**
 * Decorated version of a Prop object implementing Powerupper interface
 */
public class DecoratedPowerUp extends DecoratorProp implements Powerupper {
	/**
	 * its POWERUp label
	 */
	private Power label;
	/**
	 * Constructor
	 * @param c Prop to wrap
	 * @param path main images path
	 */
	public DecoratedPowerUp(PowerUp c, String[] path) {
		super(c, path);
		this.label = c.getLabel();
	}
	/**
	 * Getter of POWERUP label
	 * @return its POWERUP label
	 */
	public Power getLabel() {return label;}

	@Override
	public void applyPowerUp() {
		if(label==Power.TABULA_RASA) {
			DynaSound.playEffect(10); // skull
		} else {
			DynaSound.playEffect(5); // collectsItem
		}
		((PowerUp)component).applyPowerUp();	
	}

	@Override
	public long getScoreValue() {
		return ((PowerUp)component).getScoreValue();
	}

}
