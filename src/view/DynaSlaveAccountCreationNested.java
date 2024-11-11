package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.Account;
import model.AccountConcrete;
/**
 * The nested JPanel receives input by User to create a new Decorated Account
 */
public class DynaSlaveAccountCreationNested extends DynaJPanelAccountNested {
	/**
	 * Serialized key for Serialization
	 */
	private static final long serialVersionUID = 4248790697734981647L;
	/**
	 * Selector of chosen Account Avatar
	 */
	private AvatarAccountSelectorInCreationNested selectorImage;
	/**
	 * Field in which nickname is inserted by User
	 */
	private JTextField nicknameField;
	/**
	 * Chosen nickname
	 */
	private String chosenNickname;
	/**
	 * Chosen Account Avatar
	 */
	private AvatarAccount chosenAvatarUser;
	/**
	 * Chosen Account Color
	 */
	private Color chosenColor;
	/**
	 * Constructor.
	 * This nested JPanel receives input by User to create a new Decorated Account.
	 * @param dsc DynaSlaveCard in which this panel is nested
	 */
	public DynaSlaveAccountCreationNested(DynaSlaveCard dsc) {
		super(dsc);
		this.setLayout(new GridBagLayout());
		selectorImage = new AvatarAccountSelectorInCreationNested();
		
		// User chooses nickname
		JLabel nicknameLabel = new JLabel();
		nicknameLabel.setText("Enter your nickname: ");
		GridBagConstraints gbcNicknameLabel = new GridBagConstraints(
				0, 0,  //gridx, gridy
				1, 1, //gridwidth, gridheight
				0.1, 0.1, // weightx, weighty
				GridBagConstraints.LINE_END, // anchor
				GridBagConstraints.CENTER, // fill
				new Insets(0,0,0,0), //insets
				0, 0 //ipadx, ipady
				);
		this.add(nicknameLabel,gbcNicknameLabel);
		
		nicknameField = new JTextField(20);
		GridBagConstraints gbcNicknameField = new GridBagConstraints(
				1, 0,  //gridx, gridy
				1, 1, //gridwidth, gridheight
				0.5, 0.1, // weightx, weighty
				GridBagConstraints.LINE_START, // anchor
				GridBagConstraints.HORIZONTAL, // fill
				new Insets(0,0,0,0), //insets
				0, 0 //ipadx, ipady
				);
		this.add(nicknameField,gbcNicknameField);
		
		
		// User chooses favorite color
		JLabel colorLabel = new JLabel();
		colorLabel.setText("Chosen color: ");
		GridBagConstraints gbcColorLabel = new GridBagConstraints(
				0, 1,  //gridx, gridy
				1, 1, //gridwidth, gridheight
				0.1, 0.1, // weightx, weighty
				GridBagConstraints.LINE_END, // anchor
				GridBagConstraints.CENTER, // fill
				new Insets(5,5,5,5), //insets
				0, 0 //ipadx, ipady
				);
		this.add(colorLabel,gbcColorLabel);
		
		JButton colorButton = new JButton();
		colorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				chosenColor = JColorChooser.showDialog(
						new JFrame(), "Color Selection", Color.RED);
			}
			
		});
		colorButton.setText("Click here for color selection");
		GridBagConstraints gbcColorButton = new GridBagConstraints(
				1, 1,  //gridx, gridy
				1, 1, //gridwidth, gridheight
				0.1, 0.1, // weightx, weighty
				GridBagConstraints.LINE_START, // anchor
				GridBagConstraints.HORIZONTAL, // fill
				new Insets(5,5,5,5), //insets
				0, 0 //ipadx, ipady
				);
		this.add(colorButton,gbcColorButton);
		
		
		// User chooses avatarImage
		JLabel selectorLabel = new JLabel();
		selectorLabel.setText("Choose your Avatar: ");
		GridBagConstraints gbcSelectorLabel = new GridBagConstraints(
				0, 2,  //gridx, gridy
				1, 1, //gridwidth, gridheight
				0.1, 0.1, // weightx, weighty
				GridBagConstraints.LINE_END, // anchor
				GridBagConstraints.CENTER, // fill
				new Insets(0,0,0,0), //insets
				0, 0 //ipadx, ipady
				);
		this.add(selectorLabel, gbcSelectorLabel);
		
		JScrollPane scrollSelectorImage = new JScrollPane(selectorImage);
		GridBagConstraints gbcScrollSelectorImage = new GridBagConstraints(
				0, 3,  //gridx, gridy
				3, 1, //gridwidth, gridheight
				0.3, 0.3, // weightx, weighty
				GridBagConstraints.FIRST_LINE_START, // anchor
				GridBagConstraints.BOTH, // fill
				new Insets(0,0,0,0), //insets
				0, 0 //ipadx, ipady
				);
		this.add(scrollSelectorImage, gbcScrollSelectorImage);
	}
	/**
	 * Processes all the inputs received to generate the corresponding account
	 * @return the new Decorated Account or null if it's not possible to generate
	 */
	public DecoratedAccount getNewDecoratedAccount() {
		chosenNickname = nicknameField.getText();
		chosenAvatarUser = selectorImage.getSelectedAvatar();
		if(
				chosenNickname==null || chosenNickname.equals("") || chosenNickname.equals(" ")
				|| chosenAvatarUser == null || chosenColor==null) {
			return null;
		} else {
			if(chosenNickname.length()>=18)	chosenNickname = chosenNickname.substring(0, 17);
			Account account = new AccountConcrete(chosenNickname);
			DecoratedAccount decoratedAccount = new DecoratedAccount(
					account,chosenAvatarUser,chosenColor
					);
			this.chosenNickname = null;
			nicknameField.setText("");
			this.chosenAvatarUser = null;
			this.chosenColor = null;
			return decoratedAccount;
		}

	}

}
