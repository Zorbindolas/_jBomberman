package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import model.Account;
/**
 * The nested JPanel to show current Account data.
 * This panel is nested into the DynaSlaveAccount.
 */
public class DynaSlaveAccountNested extends DynaJPanelAccountNested {
	/**
	 * Serialized key for Serialization.
	 */
	private static final long serialVersionUID = -9036530559872411145L;
	/**
	 * Left GUI column label
	 */
	private JLabel  labelNickname,labelLevel,labelPlayTimeTotal,
					labelMatches,labelVictories,labelFailures,labelEscapes;
	/**
	 * Central GUI column label
	 */
	private JLabel 	labelNicknameValue,labelLevelValue,labelPlayTimeTotalValue,
					labelMatchesValue,labelVictoriesValue,labelFailuresValue,labelEscapesValue;
	/**
	 * Right GUI column label
	 */
	private JLabel 	labelSpeedrunImage,labelSpeedrunValue,labelAvatarFixedImage,labelTrophy,
					labelCompletedStages,labelRibbon,labelWinLossRatio,labelScoreIcon,labelScoreValue;
	/**
	 * Constructor.
	 * The nested JPanel to show current Account data.
	 * This panel is nested into the DynaSlaveAccount.
	 * @param dsc DynaSlaveAccount in which this panel is nested.
	 */
	public DynaSlaveAccountNested(DynaSlaveCard dsc) {
		super(dsc);
		
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.BLUE);
		
		// Left Col --------------------------------------------
		labelNickname = makeLabel("Nickname");
		GridBagConstraints gbcNickname = makeGbcLeftCol(0);
		this.add(labelNickname,gbcNickname);
		
		labelLevel = makeLabel("Level");
		GridBagConstraints gbcLevel = makeGbcLeftCol(1);
		this.add(labelLevel,gbcLevel);
		
		labelPlayTimeTotal = makeLabel("Game Time");
		GridBagConstraints gbcPlayTimeTotal = makeGbcLeftCol(2);
		this.add(labelPlayTimeTotal,gbcPlayTimeTotal);
		
		labelMatches = makeLabel("Matches");
		GridBagConstraints gbcMatches = makeGbcLeftCol(3);
		this.add(labelMatches,gbcMatches);
		
		labelVictories = makeLabel("Victories");
		GridBagConstraints gbcVictories = makeGbcLeftCol(4);
		this.add(labelVictories,gbcVictories);
		
		labelFailures = makeLabel("Failures");
		GridBagConstraints gbcLFailures = makeGbcLeftCol(5);
		this.add(labelFailures,gbcLFailures);
		
		labelEscapes = makeLabel("Escapes");
		GridBagConstraints gbcEscapes = makeGbcLeftCol(6);
		this.add(labelEscapes,gbcEscapes);
		
		// Center Col ------------------------------------------
		labelNicknameValue = makeLabel();
		GridBagConstraints gbcNicknameValue = makeGbcCenterCol(0);
		this.add(labelNicknameValue,gbcNicknameValue);
		
		labelLevelValue = makeLabel();
		GridBagConstraints gbcLevelValue = makeGbcCenterCol(1);
		this.add(labelLevelValue,gbcLevelValue);
		
		labelPlayTimeTotalValue = makeLabel();
		GridBagConstraints gbcPlayTimeTotalValue = makeGbcCenterCol(2);
		this.add(labelPlayTimeTotalValue,gbcPlayTimeTotalValue);
		
		labelMatchesValue = makeLabel();
		GridBagConstraints gbcMatchesValue = makeGbcCenterCol(3);
		this.add(labelMatchesValue,gbcMatchesValue);
		
		labelVictoriesValue = makeLabel();
		GridBagConstraints gbcVictoriesValue = makeGbcCenterCol(4);
		this.add(labelVictoriesValue,gbcVictoriesValue);
		
		labelFailuresValue = makeLabel();
		GridBagConstraints gbcLFailuresValue = makeGbcCenterCol(5);
		this.add(labelFailuresValue,gbcLFailuresValue);
		
		labelEscapesValue = makeLabel();
		GridBagConstraints gbcEscapesValue = makeGbcCenterCol(6);
		this.add(labelEscapesValue,gbcEscapesValue);
		
		// Right col
		
		// Avatar Image
		labelAvatarFixedImage = new JLabel();
		labelAvatarFixedImage.setOpaque(true);
		GridBagConstraints gbcAvatarFixedImage = new GridBagConstraints(
				2, 1,  //gridx, gridy
				2, 2, //gridwidth, gridheight
				0.1, 0.1, // weightx, weighty
				GridBagConstraints.PAGE_START, // anchor
				GridBagConstraints.CENTER, // fill
				new Insets(10,10,10,10), //insets
				0, 0 //ipadx, ipady
				);
		this.add(labelAvatarFixedImage,gbcAvatarFixedImage);

		// Speedrun Image and Value
		labelSpeedrunImage = new JLabel();
		labelSpeedrunImage.setToolTipText("Best Speedrun Time");
		ImageIcon bi = null;
		try {
			 bi = new ImageIcon(
					 (new ImageIcon(
							 ImageIO.read(getClass()
							 .getResourceAsStream(
							 "/iconsClassical/speedrunIcon.png")))
					 .getImage()
					 .getScaledInstance(24, 30, Image.SCALE_DEFAULT)
					 )
					 );
		} catch (IOException e) {e.printStackTrace();}
		labelSpeedrunImage.setIcon(bi);
		GridBagConstraints gbcSpeedrun = new GridBagConstraints(
				2, 3,  //gridx, gridy
				1, 1, //gridwidth, gridheight
				0.1, 0.1, // weightx, weighty
				GridBagConstraints.FIRST_LINE_END, // anchor
				GridBagConstraints.CENTER, // fill
				new Insets(10,10,10,10), //insets
				0, 0 //ipadx, ipady
				);
		this.add(labelSpeedrunImage,gbcSpeedrun);

		labelSpeedrunValue = new JLabel();
		GridBagConstraints gbcSpeedrunValue = new GridBagConstraints(
				3, 3,  //gridx, gridy
				1, 1, //gridwidth, gridheight
				0.1, 0.1, // weightx, weighty
				GridBagConstraints.LINE_START, // anchor
				GridBagConstraints.CENTER, // fill
				new Insets(10,10,10,10), //insets
				0, 0 //ipadx, ipady
				);
		this.add(labelSpeedrunValue,gbcSpeedrunValue);
		
		// Trophy Image and Stage Completed Value
		labelTrophy = new JLabel();
		labelTrophy.setToolTipText("Number of Stage Completed");
		try {
			 bi = new ImageIcon(
					 (new ImageIcon(
							 ImageIO.read(getClass()
							 .getResourceAsStream(
							 "/iconsClassical/trophy.png")))
					 .getImage()
					 .getScaledInstance(30, 30, Image.SCALE_DEFAULT)
					 )
					 );
		} catch (IOException e) {e.printStackTrace();}
		labelTrophy.setIcon(bi);
		GridBagConstraints gbcTrophy = new GridBagConstraints(
				2, 4,  //gridx, gridy
				1, 1, //gridwidth, gridheight
				0.1, 0.1, // weightx, weighty
				GridBagConstraints.FIRST_LINE_END, // anchor
				GridBagConstraints.CENTER, // fill
				new Insets(10,10,10,10), //insets
				0, 0 //ipadx, ipady
				);
		this.add(labelTrophy,gbcTrophy);
		
		labelCompletedStages = new JLabel();
		GridBagConstraints gbcCompletedStages = new GridBagConstraints(
				3, 4,  //gridx, gridy
				1, 1, //gridwidth, gridheight
				0.1, 0.1, // weightx, weighty
				GridBagConstraints.LINE_START, // anchor
				GridBagConstraints.CENTER, // fill
				new Insets(10,10,10,10), //insets
				0, 0 //ipadx, ipady
				);
		this.add(labelCompletedStages,gbcCompletedStages);
		
		// Ribbon Image and Win/Loss Ratio Value
		labelRibbon = new JLabel();
		labelRibbon.setToolTipText("Win/Loss Ratio");
		try {
			 bi = new ImageIcon(
					 (new ImageIcon(
							 ImageIO.read(getClass()
							 .getResourceAsStream(
							 "/iconsClassical/ribbon.png")))
					 .getImage()
					 .getScaledInstance(30, 30, Image.SCALE_DEFAULT)
					 )
					 );
		} catch (IOException e) {e.printStackTrace();}
		labelRibbon.setIcon(bi);
		GridBagConstraints gbcRibbon= new GridBagConstraints(
				2, 5,  //gridx, gridy
				1, 1, //gridwidth, gridheight
				0.1, 0.1, // weightx, weighty
				GridBagConstraints.FIRST_LINE_END, // anchor
				GridBagConstraints.CENTER, // fill
				new Insets(10,10,10,10), //insets
				0, 0 //ipadx, ipady
				);
		this.add(labelRibbon,gbcRibbon);
		
		labelWinLossRatio = new JLabel();
		GridBagConstraints gbcWinLossRatio = new GridBagConstraints(
				3, 5,  //gridx, gridy
				1, 1, //gridwidth, gridheight
				0.1, 0.1, // weightx, weighty
				GridBagConstraints.LINE_START, // anchor
				GridBagConstraints.CENTER, // fill
				new Insets(10,10,10,10), //insets
				0, 0 //ipadx, ipady
				);
		this.add(labelWinLossRatio,gbcWinLossRatio);
		
		// Score image and Best Score Value showed
		labelScoreIcon = new JLabel();
		try {
			 bi = new ImageIcon(
					 (new ImageIcon(
							 ImageIO.read(getClass()
							 .getResourceAsStream(
							 "/iconsClassical/cuteStar.png")))
					 .getImage()
					 .getScaledInstance(30, 30, Image.SCALE_DEFAULT)
					 )
					 );
		} catch (IOException e) {e.printStackTrace();}
		labelScoreIcon.setIcon(bi);
		labelScoreIcon.setToolTipText("Best Score");
		GridBagConstraints gbcScoreIcon = new GridBagConstraints(
				2, 6,  //gridx, gridy
				1, 1, //gridwidth, gridheight
				0.1, 0.1, // weightx, weighty
				GridBagConstraints.FIRST_LINE_END, // anchor
				GridBagConstraints.CENTER, // fill
				new Insets(10,10,10,10), //insets
				0, 0 //ipadx, ipady
				);
		this.add(labelScoreIcon,gbcScoreIcon);
		
		labelScoreValue = new JLabel();
		GridBagConstraints gbcScoreValue = new GridBagConstraints(
				3, 6,  //gridx, gridy
				1, 1, //gridwidth, gridheight
				0.1, 0.1, // weightx, weighty
				GridBagConstraints.LINE_START, // anchor
				GridBagConstraints.CENTER, // fill
				new Insets(10,10,10,10), //insets
				0, 0 //ipadx, ipady
				);
		this.add(labelScoreValue,gbcScoreValue);

	}
	/**
	 * Create a new JLabel for this panel
	 * @return a new JLabel
	 */
	private JLabel makeLabel() {
		JLabel jLabel = new JLabel();
		jLabel.setForeground(Color.WHITE);
		jLabel.setFont(dsc.getFont().deriveFont(30F));
		return jLabel;
	}
	// Overloading
	/**
	 * Create a new JLabel with a title for this panel
	 * @param title the JLabel title 
	 * @return a new titled JLabel
	 */
	private JLabel makeLabel(String title) {
		JLabel jLabel = new JLabel();
		jLabel.setForeground(Color.BLACK);
		jLabel.setFont(dsc.getFont().deriveFont(30F));
		jLabel.setText(title);
		return jLabel;
	}
	/**
	 * Create the GridBagConstrains for a JLabel on the left GUI column
	 * @param y JLabel index to refer
	 * @return the correct GridBagConstrains
	 */
	private GridBagConstraints makeGbcLeftCol(int y) {
		return new GridBagConstraints(
						0, y,  //gridx, gridy
						1, 1, //gridwidth, gridheight
						0.1, 0.1, // weightx, weighty
						GridBagConstraints.PAGE_START, // anchor
						GridBagConstraints.CENTER, // fill
						new Insets(10,10,10,10), //insets
						0, 0 //ipadx, ipady
						);
	}
	/**
	 * Create the GridBagConstrains for a JLabel on the center GUI column
	 * @param y JLabel index to refer
	 * @return the correct GridBagConstrains
	 */
	private GridBagConstraints makeGbcCenterCol(int y) {
		return new GridBagConstraints(
						1, y,  //gridx, gridy
						1, 1, //gridwidth, gridheight
						0.1, 0.1, // weightx, weighty
						GridBagConstraints.PAGE_END, // anchor
						GridBagConstraints.CENTER, // fill
						new Insets(10,10,10,10), //insets
						0, 0 //ipadx, ipady
						);
	}
	/**
	 * Update labels values from the current account saved on the view
	 */
	public void refreshCurrAccountValues() {
		Account account = dsc.getMaster().getFrame().getCurrAccount();
		if(account!=null) {
			labelNicknameValue.setText(
					account.getNickname());
			labelLevelValue.setText(
					account.getLevel()+"");
			labelPlayTimeTotalValue.setText(
					account.getPlayTimeString());
			labelMatchesValue.setText(
					account.getMatchesTotal()+"");
			labelVictoriesValue.setText(
					account.getVictoriesTotal()+"");
			labelFailuresValue.setText(
					account.getFailuresTotal()+"");
			labelEscapesValue.setText(
					account.getEscapesTotal()+"");
			
			labelAvatarFixedImage.setBackground(
					((DecoratedAccount)account).getColor()
					);
			labelAvatarFixedImage.setIcon(
					((DecoratedAccount)account).getFixedIcon()
					);
			String speedrunValue = 
					(account.getSpeedrunTime()==Integer.MAX_VALUE)?
							"---" : account.getSpeedrunTime()+"";
			labelSpeedrunValue.setText(
					speedrunValue
					);
			labelCompletedStages.setText(
					account.getCompletedStagesTotal()+""
					);
			labelWinLossRatio.setText(
					String.format("%.3f", account.getRatio())
					);
			
			labelScoreValue.setText(
					(
							(account.getBestScore()>1000)?
							(account.getBestScore()/1000.0)+" K":
								(account.getBestScore())+" ")
					);
			
		}

	}	

}
