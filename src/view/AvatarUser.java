package view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/**
 * User's Avatar in game. It is related to the chosen AvatarAccount.
 * These type object concurs also to show visible account 
 * on the left bottom corner of the application screen.
 */
public class AvatarUser implements FootageFuncs {
	/**
	 * Avatar static icon that is visible in DynaSlaveAccount.
	 */
	private ImageIcon avatarFixedImage;
	/**
	 * Current avatar image of this animation.
	 */
	private BufferedImage avatarDynaImage;
	/**
	 * Animation footage.
	 */
	private List<BufferedImage> images;
	/**
	 * Selector for the next footage image for the animation  sequence.
	 */
	private Iterator<BufferedImage> iter;
	/**
	 * Instances of AvatarUser by each AvatarAccount, in a one-of-a-kind Singleton way.
	 */
	private static AvatarUser instance_BALOON, instance_BOMBERMAN,instance_DINAH,
							  instance_DROPPY, instance_DROWZEE, instance_FLIPPER,
							  instance_FUNCKY_GALLO, instance_PINGU, instance_QUACK,
							  instance_SCHWIFTY, instance_TIGRO;
	/**
	 * Private Constructor (Singleton).
	 * @param avatarAccount corresponded AvatarAccount
	 */
	private AvatarUser(AvatarAccount avatarAccount) {
		String namePath = avatarAccount.getNamePath();
		int numberOfForms = avatarAccount.getNumberOfForms();
		images = new ArrayList<>();
		try {
			BufferedImage bi = ImageIO.read(getClass()
					.getResourceAsStream("/av"+namePath+"/av"+namePath+sfx));
			
			avatarFixedImage = new ImageIcon((Image)bi);
			
			for(int i=0 ; i<numberOfForms; i++) {
				images.add(
						ImageIO.read(getClass()
								.getResourceAsStream("/av"+namePath+"/av"+namePath+i+sfx))
						);
			}
			resetFootage();
				
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	/**
	 * Getter of AvatarUser with this avatarAccount input.
	 * This method is conceptually similar to the Singleton Pattern getInstance.
	 * @param avatarAccount avatarAccount of the requested AvatarUser
	 * @return requested AvatarUser
	 */
	public static AvatarUser getInstanceByAvatarAccount(AvatarAccount avatarAccount) {
		switch(avatarAccount) {
		case BALOON -> {
			if(instance_BALOON==null)instance_BALOON= new AvatarUser(AvatarAccount.BALOON);
			return instance_BALOON;
		}
		case BOMBERMAN -> {
			if(instance_BOMBERMAN==null)instance_BOMBERMAN= new AvatarUser(AvatarAccount.BOMBERMAN);
			return instance_BOMBERMAN;
		}
		case DINAH -> {
			if(instance_DINAH==null)instance_DINAH = new AvatarUser(AvatarAccount.DINAH);
			return instance_DINAH;
		}
		case DROPPY -> {
			if(instance_DROPPY==null)instance_DROPPY = new AvatarUser(AvatarAccount.DROPPY);
			return instance_DROPPY;
		}
		case DROWZEE -> {
			if(instance_DROWZEE==null)instance_DROWZEE = new AvatarUser(AvatarAccount.DROWZEE);
			return instance_DROWZEE;
		}
		case FLIPPER -> {
			if(instance_FLIPPER==null)instance_FLIPPER = new AvatarUser(AvatarAccount.FLIPPER);
			return instance_FLIPPER;
		}
		case FUNCKY_GALLO -> {
			if(instance_FUNCKY_GALLO==null)instance_FUNCKY_GALLO = new AvatarUser(AvatarAccount.FUNCKY_GALLO);
			return instance_FUNCKY_GALLO;
		}
		case PINGU -> {
			if(instance_PINGU==null)instance_PINGU = new AvatarUser(AvatarAccount.PINGU);
			return instance_PINGU;
		}
		case QUACK -> {
			if(instance_QUACK==null)instance_QUACK = new AvatarUser(AvatarAccount.QUACK);
			return instance_QUACK;
		}
		case SCHWIFTY -> {
			if(instance_SCHWIFTY==null)instance_SCHWIFTY = new AvatarUser(AvatarAccount.SCHWIFTY);
			return instance_SCHWIFTY;
		}
		case TIGRO -> {
			if(instance_TIGRO==null)instance_TIGRO = new AvatarUser(AvatarAccount.TIGRO);
			return instance_TIGRO;
		}
		default -> {
			if(instance_BOMBERMAN==null)instance_BOMBERMAN= new AvatarUser(AvatarAccount.BOMBERMAN);
			return instance_BOMBERMAN;
		}
		}
	}

	@Override
	public void incFootage() {
		if(!iter.hasNext()) {
			resetFootage();
		}
		avatarDynaImage = iter.next();
	}

	@Override
	public void resetFootage() {
		iter = images.iterator();
	}

	@Override
	public BufferedImage getImage() {
		return avatarDynaImage;
	}
	/**
	 * Getter of the fixed icon for the User's avatar.
	 * @return this fixed avatar icon.
	 */
	public ImageIcon getFixedIcon() {
		return avatarFixedImage;
	}
	
}
