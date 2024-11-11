package view;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import model.CartesianCoordinate;
import model.Heroes;
/**
 * The nested panel that encapsulates a GridBagLayout to show choosable player Characters.
 */
public class DynaSlavePlayerNested extends JPanel {
	/**
	 * Serialized key for Serialization.
	 */
	private static final long serialVersionUID = -1446014526775468986L;
	
	// Container
	/**
	 * Container of this nested panel
	 */
	private DynaSlavePlayer dsp;

	// Parameters of this component in the DynaSlavePlayer
	/**
	 * Cartesian point of origin for this nested panel in its container
	 */
	private CartesianCoordinate origin;
	/**
	 * Height of this nested panel
	 */
	private int height;
	/**
	 * Width of this nested panel
	 */
	private int width;
	
	// Sub-components of this JPanel
	/**
	 * The JScrollPane of the Player Characters Avatars
	 */
	private JScrollPane scrollPane;
	/**
	 * JList of the Player Characters Avatars
	 */
	private JList<AvatarHeroItem> javaList;
	/**
	 * Label in which is set selected PC name text
	 */
	private JLabel nameField;
	/**
	 * Panel in which selected PC image is visible
	 */
	private JPanel animeSelected;
	/**
	 * Label in which is set selected PC description (HTML-formatted).
	 */
	private JLabel descriptionField;
	
	// Manage Player's choose
	/**
	 * Selected Avatar
	 */
	private AvatarHeroItem actualAvatarItem;
	/**
	 * Label of selected player character
	 */
	private Heroes actualHero;
	/**
	 * image for matte Border
	 */
	private ImageIcon matteBM;

	/**
	 * Constructor. This nested panel encapsulates a GridBagLayout to show choosable player Characters. 
	 * @param dsp DynaSlavePanel in which this panel is nested
	 */
	public DynaSlavePlayerNested(DynaSlavePlayer dsp) {

		this.dsp = dsp;
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.BLUE);

		try {
			this.matteBM = new ImageIcon(ImageIO.read(getClass().getResourceAsStream(
							"/iconsClassical/matteBM.png")));
		} catch (IOException e) {e.printStackTrace();}

		int xMatte = (dsp.getMaster().getFrame().getW()/30);
		int yMatte = (dsp.getMaster().getFrame().getH()/30);
		this.setBorder(BorderFactory.createMatteBorder(
				xMatte, yMatte, xMatte, yMatte, matteBM));
		
		setActualHero(Heroes.WHITE_BOMBERMAN);
		setActualAvatarItem(new AvatarHeroItem(actualHero));
		dsp.setTextOptionOfAgreedSelection(actualHero.getName().toUpperCase());
		
		
		// JList: ScrollingPanel for selecting icon --------------------------------
		JList<AvatarHeroItem> javaList = new JList<>();
		javaList.setBackground(new Color(30,60,255));
		
		AvatarJListModel modelList = new AvatarJListModel();
		AvatarJListRenderer rendererList = new AvatarJListRenderer();
		
		javaList.setModel(modelList);
		javaList.setCellRenderer(rendererList);
		javaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		javaList.getSelectionModel().addListSelectionListener(
				new AvatarJListManagerOfSelections(this));
		javaList.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if(keyCode==KeyEvent.VK_ENTER) dsp.getMaster().requestFocusInWindow();
			}
			
		});
		
		this.javaList = javaList;
		
		
		// JScrollPane wrapped to scrollingPanel ------------------------------------
		JScrollPane scrollPane = new JScrollPane(javaList);
		scrollPane.setBackground(null);
		scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createTitledBorder(
				javaList.getBorder(), "If Mouse, press Enter"));
		
		GridBagConstraints gbcScrollPane =
				new GridBagConstraints(
						0, 0,  //gridx, gridy
						4, 6, //gridwidth, gridheight
						0.1, 0.1, // weightx, weighty
						GridBagConstraints.CENTER, // anchor
						GridBagConstraints.BOTH, // fill
						new Insets(10,10,10,10), //insets
						0, 0 //ipadx, ipady
						);
		this.add(scrollPane, gbcScrollPane);
		this.scrollPane = scrollPane;
		
		
		// JLabel for selected pc's name --------------------------------------------
		JLabel nameField = new JLabel();
		nameField.setForeground(Color.WHITE);
		nameField.setFont(dsp.getFont().deriveFont(30F));
		nameField.setText(actualHero.getName());
		this.nameField = nameField;
		
		GridBagConstraints gbcNameField =
				new GridBagConstraints(
						4, 0,  //gridx, gridy
						4, 1, //gridwidth, gridheight
						0.1, 0.1, // weightx, weighty
						GridBagConstraints.PAGE_START, // anchor
						GridBagConstraints.CENTER, // fill
						new Insets(10,10,10,10), //insets
						0, 0 //ipadx, ipady
						);
		this.add(nameField, gbcNameField);
		
		
		// JPanel for selected pc's animated icon ----------------------------------
//		JLabel animeIcon = new JLabel();
//		animeIcon.setHorizontalAlignment(JLabel.CENTER);
//		this.animeIcon = animeIcon;
//		animeIcon.setIcon(actualAvatarItem.getStatue());
		animeSelected = new JPanel() {
			
			private static final long serialVersionUID = -1285608048369222331L;

			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(actualAvatarItem.getImage(), 
						(this.getWidth()-80)/2, 
						(this.getHeight()-100)/2, 
						80,
						100,
						null);
			}
		};
		GridBagConstraints gbcAnimeIcon =
				new GridBagConstraints(
						4, 1,  //gridx, gridy
						2, 1, //gridwidth, gridheight
						0.5, 0.5, // weightx, weighty
						GridBagConstraints.CENTER, // anchor
						GridBagConstraints.BOTH, // fill
						new Insets(0,0,0,0), //insets
						0, 0 //ipadx, ipady
						);
		this.add(animeSelected, gbcAnimeIcon);
		
		
		// TextField for selected pc's description -----------------------------------
		JLabel descriptionField = new JLabel();
		this.descriptionField = descriptionField;
		descriptionField.setHorizontalAlignment(JLabel.CENTER);
		descriptionField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		descriptionField.setForeground(Color.WHITE);
//		descriptionField.setFont(dsp.getFont());//.deriveFont(30F));
		descriptionField.setText(HeroesDescriptions.getDescription(actualHero));
		GridBagConstraints gbcDescriptionField =
				new GridBagConstraints(
						4, 3,  //gridx, gridy
						4, 3, //gridwidth, gridheight
						0.1, 0.1, // weightx, weighty
						GridBagConstraints.PAGE_END, // anchor
						GridBagConstraints.CENTER, // fill
						new Insets(10,10,10,10), //insets
						0, 0 //ipadx, ipady
						);
		this.add(descriptionField, gbcDescriptionField);
		
	}
	
	// Getters
	/**
	 * Getter of this panel container
	 * @return DynaSlaveCard in which this panel is nested
	 */
	public DynaSlavePlayer getDsp() {return dsp;}
	/**
	 * Getter of this panel origin
	 * @return Cartesian Coordinate origin of this nested panel in its container
	 */
	public CartesianCoordinate getOrigin() {return origin;}
	/**
	 * Getter of this panel width
	 * @return its width
	 */
	public int getWidth() {return width;}
	/**
	 * Getter of this panel height
	 * @return its height
	 */
	public int getHeight() {return height;}
	/**
	 * Getter of avatars scrollPane
	 * @return its scrollPane
	 */
	public JScrollPane getScrollPane() {return scrollPane;}
	/**
	 * Getter of Avatars JList
	 * @return its avatars JList
	 */
	public JList<AvatarHeroItem> getJavaList() {return javaList;}
	/**
	 * Getter of current nameField
	 * @return its nameField
	 */
	public JLabel getNameField() {return nameField;}
	/**
	 * Getter of current animeSelected
	 * @return its animeSelected
	 */
	public JPanel getAnimeSelected() {return animeSelected;}
	/**
	 * Getter of current descriptionField
	 * @return its descriptionField
	 */
	public JLabel getDescriptionField() {return descriptionField;}
	/**
	 * Getter of actualAvatarItem
	 * @return its actualAvatarItem
	 */
	public AvatarHeroItem getActualAvatarItem() {return actualAvatarItem;}
	/**
	 * Getter of current hero label
	 * @return its actualHero label
	 */
	public Heroes getActualHero(){return actualHero;}
	
	// Setters
	/**
	 * Setter of origin point
	 * @param origin new origin point
	 */
	public void setOrigin(CartesianCoordinate origin) {this.origin = origin;}
	/**
	 * Setter of width
	 * @param width new width value
	 */
	public void setWidth(int width) { this.width = width;}
	/**
	 * Setter of height
	 * @param height new height value
	 */
	public void setHeight(int height) {this.height = height;}
	
//	public void setJavaList(JList javaList) {this.javaList = javaList;}
	/**
	 * Setter of nameField
	 * @param nameField new nameField value
	 */
	public void setNameField(JLabel nameField) {this.nameField = nameField;}
	/**
	 * Setter of animeSelected
	 * @param animeSelected new animeSelected value
	 */
	public void setAnimeSelected(JPanel animeSelected) {this.animeSelected = animeSelected;}
	/**
	 * Setter of descriptionField
	 * @param descriptionField new descriptionField
	 */
	public void setDescriptionField(JLabel descriptionField) {this.descriptionField = descriptionField;}
	/**
	 * setter of actualAvatarItem
	 * @param actualAvatarItem new actualAvatarItem value
	 */
	public void setActualAvatarItem(AvatarHeroItem actualAvatarItem) {this.actualAvatarItem = actualAvatarItem;}
	/**
	 * Setter of actualHero label
	 * @param actualHero new actualHero label value
	 */
	public void setActualHero(Heroes actualHero) {this.actualHero = actualHero;}
	/**
	 * Setter of matte-image
	 * @param matteBM new matte-image 
	 */
	public void setMatteBM(ImageIcon matteBM) {this.matteBM = matteBM;}
	
	// Methods for managing animations
	/**
	 * Increment actualAvatarItem footage to its next sprite
	 */
	public void incFootageSelectedCharacter() {
		actualAvatarItem.incFootage();
	}
	/**
	 * Check if the footage loop is completed
	 * @return true if actualAvatarItem footage loop is completed
	 */
	public boolean isFootageLoopDone() {
		return actualAvatarItem.isFootageDone();
	}
	/**
	 * Restart the footage of the actualAvatarItem.
	 */
	public void resetFootageLoop() {
		actualAvatarItem.restartFootageIcon();
	}
	

}
