package view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.ImageIcon;

import model.Account;
/**
 * It's an Account (from Model) in which is wrapped an AvatarAccount
 * (to represent it in the View) and a Color.
 */
public class DecoratedAccount extends DecoratorAccount implements Serializable {
	/**
	 * Serial key for Serialization process.
	 */
	private static final long serialVersionUID = 4336029660154447601L;
	/**
	 * This related AvatarAccount.
	 */
	private AvatarAccount avatarAccount;
	/**
	 * This related Color.
	 */
	private Color color;
	/**
	 * Constructor
	 * @param component The Account to decorate
	 * @param avatarAccount The AvataAccount to wrap
	 * @param color The color to wrap
	 */
	public DecoratedAccount(
			Account component, AvatarAccount avatarAccount, Color color) {
		super(component);
		this.component = component;
		this.avatarAccount = avatarAccount;
		this.color = color;
	}
	/**
	 * Getter of this AvatarAccount
	 * @return its AvatarAccount
	 */
	public AvatarAccount getAvatarAccount() {return avatarAccount;}
	/**
	 * Getter of this Fixed Icon (from the AvatarUser related to this AvatarAccount).
	 * @return current fixed icon
	 */
	public ImageIcon getFixedIcon() {return AvatarUser.getInstanceByAvatarAccount(avatarAccount).getFixedIcon();}
	/**
	 * Getter of current image for its animation.
	 * @return its current image 
	 */
	public BufferedImage getDynaImage() {return AvatarUser.getInstanceByAvatarAccount(avatarAccount).getImage();}
	/**
	 * Getter of related Color
	 * @return its color
	 */
	public Color getColor() {return color;}
}
