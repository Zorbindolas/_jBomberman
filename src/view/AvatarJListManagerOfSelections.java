package view;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 * Personalized Listener of the DynaSlavePlayerNested JList Selection Model.
 * It updates the right side of the player nested panel to show current selected hero's data.
 */
public class AvatarJListManagerOfSelections implements ListSelectionListener {
	/**
	 * The DynaSlavePlayerNested in which we found the personalized JList to listen.
	 */
	private DynaSlavePlayerNested dspn;
	/**
	 * Constructor for this Listener.
	 * @param dspn DynaSlavePlayerNested's reference.
	 */
	public AvatarJListManagerOfSelections(
			DynaSlavePlayerNested dspn) {
		this.dspn = dspn;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
		AvatarHeroItem avatar = dspn.getJavaList().getSelectedValue();

		dspn.setActualAvatarItem(avatar);

		dspn.setActualHero(avatar.getHero()); // Set the selected hero
		
		//title of nested panel
		String nameHeroSelected = dspn.getActualHero().getName();
		dspn.getNameField().setText(nameHeroSelected);
		
		// option[0] of dsp
		nameHeroSelected = nameHeroSelected.toUpperCase();
		dspn.getDsp().setTextOptionOfAgreedSelection(nameHeroSelected);
		
		// Set html-style text from current hero
		dspn.getDescriptionField().setText(
				HeroesDescriptions.getDescription(dspn.getActualHero())
				);
		
		dspn.getDsp().resetTenthsOfPause(); // for reset counter for pause between different selection of characters
		
		dspn.getActualAvatarItem().restartFootageIcon();  // start animation
		
		// No more necessary. for resetting the focus on the window of card layout
		//dspn.getDsp().getMaster().requestFocusInWindow();
		
	}

}
