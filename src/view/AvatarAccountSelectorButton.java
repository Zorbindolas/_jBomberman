package view;

import javax.swing.JButton;
/**
 * A JButton with encapsulated AvatarAccount in it.
 * It's used in the DynaSlaveAccountCreationNested for the Account avatar creation context.
 */
public class AvatarAccountSelectorButton extends JButton {
	/**
	 * serialized key for serialization.
	 */
	private static final long serialVersionUID = 4016558749387265081L;
	/**
	 * This related AvatarAccount.
	 */
	private AvatarAccount avatarAccount;
	/**
	 * Constructor for this
	 * @param avatarAccount the encapsulated AvatarAccount
	 */
	public AvatarAccountSelectorButton(AvatarAccount avatarAccount) {
		this.avatarAccount = avatarAccount;
	}
	/**
	 * Getter for encapsulated AvatarAccount reference
	 * @return its AvatarAccount
	 */
	public AvatarAccount getAvatarAccount(){return avatarAccount;}
	
}
