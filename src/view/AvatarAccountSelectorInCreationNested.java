package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
/**
 * The nested panel into DynaSlaveAccountCreationNested in which all choosable AvatarAccount are showed.
 */
public class AvatarAccountSelectorInCreationNested extends JPanel {
	/**
	 * Serial key for serialization.
	 */
	private static final long serialVersionUID = -5937040473838318433L;
	/**
	 * Actual selected AvatarAccount from the panel.
	 */
	private AvatarAccount selectedAvatar;
	/**
	 * Current JButton.
	 */
	private JButton greenButton;
	/**
	 * Constructor
	 */
	public AvatarAccountSelectorInCreationNested() {
		super();
		this.setLayout(new FlowLayout());
		for(AvatarAccount aui : AvatarAccount.values()) {
			AvatarAccountSelectorButton button = new AvatarAccountSelectorButton(aui);
			button.setOpaque(true);
			button.setBackground(Color.GRAY);
			button.setPreferredSize(new Dimension(120,120));
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					selectedAvatar = button.getAvatarAccount();
					if(greenButton!=null) {
						greenButton.setBackground(Color.GRAY);
					}
					greenButton = button;
					button.setBackground(Color.GREEN);
				}

			});
			AvatarUser avatar = AvatarUser.getInstanceByAvatarAccount(aui);
			ImageIcon icon = avatar.getFixedIcon();
			button.setIcon(icon);
			this.add(button, Component.CENTER_ALIGNMENT);
		}
	}
	/**
	 * Getter of the selectedAvatar that reset background color for the greenButton.
	 * @return this selected AvatarAccount
	 */
	public AvatarAccount getSelectedAvatar(){
		if(greenButton!=null) greenButton.setBackground(Color.GRAY);
		greenButton = new JButton();
		return selectedAvatar;
	}
	
}
