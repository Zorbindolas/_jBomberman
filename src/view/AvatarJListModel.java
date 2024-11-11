package view;

import javax.swing.DefaultListModel;

import model.Heroes;
/**
 * Personalized model for the JList used in DynaSlavePlayerNested.
 */
public class AvatarJListModel extends DefaultListModel<AvatarHeroItem>{
	/**
	 * serialized key for serialization
	 */
	private static final long serialVersionUID = 3289150758594690550L;
	/**
	 * This model contains all AvatarItem heroes.
	 */
	public AvatarJListModel() {
		
		for(Heroes hero : Heroes.values()) {
			this.addElement(new AvatarHeroItem(hero));
		}
		
	}
	
}
