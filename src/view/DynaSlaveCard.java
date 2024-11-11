package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.ButtonMenuMouseListener;
import model.GameModel;
/**
 * This class defines the abstraction common to all card panels in the Master's CardLayout.
 * Each Card panel has options which can be clicked because there are invisible buttons on them.
 */
public abstract class DynaSlaveCard extends JPanel implements OptionExecuter {
	/**
	 * Serialized key for Serialization
	 */
	private static final long serialVersionUID = 9208793730768434390L;
	/**
	 * CardLayout Master reference
	 */
	protected DynaMasterCard master;
	/**
	 * Master's CardLayout reference
	 */
	protected CardLayout cl;
	/**
	 * Total number of options in this card
	 */
	protected final int modulus;
	/**
	 * Panel background image
	 */
	protected BufferedImage background;
	/**
	 * Panel skyscrapers image
	 */
	protected BufferedImage skyscrapers;
	
	// Children specifics
	/**
	 * real width unit of the frame
	 */
	protected int meterW;
	/**
	 * real height unit of the frame
	 */
	protected int meterH;
	// Components for all children
	// I must recalculate their real position if I switch to another resolution
	/**
	 * Options Strings. They don't have specific X coordinates because they are centered on the screen.
	 */
	protected List<String> options;
	/**
	 * Multiplication factors for calculating the options Y coordinates. 
	 * They will be multiplied by the real height unit of the frame
	 * for options drawing.
	 */
	protected int[] yOptions;
	/**
	 * X real coordinates of the invisible buttons
	 */
	protected List<Integer> posX;
	/**
	 * Y real coordinates of the invisible buttons
	 */
	protected List<Integer> posY;
	/**
	 * Panel buttons (they are invisible)
	 */
	protected List<JButton> buttons;
	
	// fixed parameters by default screen
	/**
	 * X coordinates in defScreen of the invisible buttons
	 */
	protected List<Integer> posStandX;
	/**
	 * Y coordinates in defScreen of the invisible buttons
	 */
	protected List<Integer> posStandY;
	/**
	 * Standard width of an option character
	 */
	protected int standardWidthChar;
	/**
	 * Standard height of an option character
	 */
	protected int standardHeightChar;
	/**
	 * standard button height
	 */
	protected int buttonHeight;
	/**
	 * It dispenses symbols and letters sprites in a game-like fashion style
	 */
	protected Vocabulary vocabo;
	/**
	 * standard char dimension
	 */
	protected int dimChar;
	/**
	 * Special font for pencilPen method
	 */
	protected Font font;

	/**
	 * Constructor of a card in the CardLayout, named DynaSlaveCard
	 * @param master CardLayout Master
	 * @param modulus number of options in this card
	 * @param stringOptions options Strings
	 * @param yOps Y positioning of options
	 * @param backgroundString string for loading of background image
	 */
	public DynaSlaveCard(
			DynaMasterCard master, 
			final int modulus,
			String[] stringOptions,
			int[] yOps,
			String backgroundString
			) {		
		
		this.master = master;
		this.modulus = modulus;
		
		this.standardWidthChar = 30;
		this.standardHeightChar = 30;
		this.buttonHeight = standardHeightChar;
		
		options = new ArrayList<>();
		posX = new ArrayList<>();
		posY = new ArrayList<>();
		buttons = new ArrayList<>();
		
		for(String s : stringOptions) options.add(s);
		
		try {
			this.yOptions = yOps;
			
			if(yOptions.length != options.size()) 
				throw new Exception() {
							private static final long serialVersionUID = -6156882961410844684L;

							@Override
							public String toString() {
								return super.toString() + " <init> : Needs one factor for each string option ";
							};
			};
		} catch(Exception e) {System.out.println(e.toString());};
		
		
		for(int i=0; i<options.size();i++) {
			JButton button = makesButton(i);
			button.setBounds(0, 0, 10, 10);
			this.add(button);
			this.buttons.add(button);
		}
		
		this.setLayout(null);
		
		cl = (CardLayout)(master.getLayout());
		
		vocabo = Vocabulary.getInstance();
		
		dimChar = 30;
		
		this.setOpaque(false);
		
		try {
			skyscrapers = ImageIO.read(getClass().getResourceAsStream("/menu/skyscrapers.png"));
			InputStream is = getClass().getResourceAsStream("/fonts/PixelBug.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, is);
			background = ImageIO.read(getClass().getResourceAsStream("/menu/"+backgroundString+".png"));
		} catch (IOException e) {} catch (FontFormatException e) {}

		
		// Methods to initialize children components, working thanks to polymorphism
		
		relocateComponents();
		
		initializePosStandards();		
	}
	
	// Getters
	/**
	 * Getter of CardLayout Master reference
	 * @return this Master reference
	 */
	public DynaMasterCard getMaster() {return master;}
	/**
	 * Getter of modulus
	 * @return its modulus
	 */
	public int getMod() {return modulus;}
	/**
	 * Getter of width unit value
	 * @return its width unit
	 */
	public int getMeterW() {return meterW;}
	/**
	 * Getter of height unit value
	 * @return its height unit
	 */
	public int getMeterH() {return meterH;}
	/**
	 * Getter of special font
	 * @return its special font
	 */
	public Font getFont() {return font;}
	/**
	 * Getter of invisible buttons
	 * @return invisible buttons List
	 */
	public List<JButton> getButtons(){return buttons;}
	/**
	 * Getter of a specific invisible button
	 * @param i button position
	 * @return the specified button
	 */
	public JButton getAButton(int i) {
		if(i<buttons.size()) return buttons.get(i);
		return null;
	}
	
	// Methods to use only one time in the constructor --------------------------------
	/**
	 * Define standard coordinates of components (buttons) by defScreen definition
	 */
	protected void initializePosStandards() {
		posStandX = new ArrayList<>();
		posStandY = new ArrayList<>();
		posX.forEach((x) -> posStandX.add(x));
		posY.forEach((y) -> posStandY.add(y));
	}
	
	// Methods to manage components ---------------------------------------------------
	/**
	 * Change the active panel in CardLayout
	 * @param panelName the new active panel name
	 */
	public void changePanel(String panelName) {
		
		master.resetTempo();
		GameModel.getInstance().resetPointer();
		GameModel.getInstance().resetModelTimer();
		cl.show(master, panelName);
		master.changeActivePanel(panelName);
		
	}
	/**
	 * Generate an invisible button in this panel, with a mouse listener
	 * @param iOption option index to refer
	 * @return a new invisible button
	 */
	protected JButton makesButton(int iOption) {
		
		JButton button = getAnInvisibleButton();

		addMouseListenerToTheButton(this,button,iOption);

//		button.setBorder(null);
//		button.setContentAreaFilled(false);

		return button;
	}
	/**
	 * Create a new invisible button
	 * @return an invisible button
	 */
	protected JButton getAnInvisibleButton() {
		return new JButton() 
		{
			private static final long serialVersionUID = 698303177638510959L;

			@Override
			public void paint(Graphics g) { // this override makes button invisible
				
			}
		};
	}
	/**
	 * Add a MouseListener to a button with encapsulated references to this panel
	 * @param dsc DynaSlaveCard reference
	 * @param button JButton with the MouseListener
	 * @param iOption option index to refer
	 */
	protected void addMouseListenerToTheButton(
			DynaSlaveCard dsc, JButton button, int iOption) {
		button.addMouseListener(new ButtonMenuMouseListener(dsc,iOption));
	}
	
	// Methods to relocate Components

	 // define posY and posX
	/**
	 * Relocate all component consistent with current frame dimensions.
	 */
	public void relocateComponents() {
		
		this.meterW = master.getFrame().getTenthW();
		this.meterH = master.getFrame().getTenthH();
		
		// posY
		redefinePosY(meterH,yOptions);
		
		// posX
		redefinePosXCentralPositioning(standardWidthChar);

		
		// Relocate buttons		
		relocateButtonsToDrawnText(standardWidthChar, buttonHeight);

	}
	/**
	 * Recalculate real Y coordinates of the invisible buttons
	 * @param meterH height unit to refer
	 * @param yOps pre-established multiplication factors for calculating the options Y coordinates
	 */
	protected void redefinePosY(int meterH, int[] yOps) {
		posY.clear();
		
		for(int yOp : yOps) {
			posY.add(meterH*yOp);
		}
		
	}
	/**
	 * Recalculate real X coordinates of the invisible buttons
	 * @param standWidthChar option character standard width
	 */
	protected void redefinePosXCentralPositioning(int standWidthChar) {
		posX.clear();
		
		for(String op : this.options) {
			posX.add((int)(master.getFrame().getW() 
					- standWidthChar*op.length() 
					* master.getFrame().getFactor()) / 2);
		}
		
	}
	/**
	 * Relocate buttons consistently to current x-Y real coordinates
	 * @param standWidthChar option character standard width
	 * @param bHeight button height
	 */
	protected void relocateButtonsToDrawnText(int standWidthChar, int bHeight) {
		for(int i=0;i<options.size();i++) {
			relocateAButtonToDrawnText(i,standWidthChar,bHeight);
		}
	}
	/**
	 * Relocate a button consistently to current x-Y real coordinates
	 * @param index option index to refer
	 * @param standWidthChar option character standard width
	 * @param bHeight button height
	 */
	protected void relocateAButtonToDrawnText(
			int index, int standWidthChar, int bHeight) {
		setButton(buttons.get(index),
				posX.get(index),
				posY.get(index),
				(int)(standWidthChar
				*options.get(index).length()
				* master.getFrame().getFactor()),
				bHeight);
	}
	/**
	 * Setter of button parameters
	 * @param b button to set
	 * @param x fixed origin x coordinate
	 * @param y fixed origin y coordinate
	 * @param bWidth button width
	 * @param bHeight button height
	 */
	protected void setButton(JButton b, int x, int y, int bWidth, int bHeight) {
		b.setBounds(x, y, bWidth, bHeight);
	}
	/**
	 * Change String associated with an option in this panel
	 * @param indexOption index option to change
	 * @param newOptionText new option String
	 */
	public void changeTextOfAnOptionAtRuntime(int indexOption, String newOptionText) {
		
		// update the list options with the new string in pos 0
		this.options.set(indexOption, newOptionText);
		
		// update pos standard for the drawing text option for fountain pen 
		posStandX.set(
				indexOption, 
				(int)(
						(master.getFrame().getWStand() 
					- standardWidthChar*newOptionText.length() )/2 
						)
				);
		
		// update real pos for relocate the invisible button correctly
		posX.set(
				indexOption, 
				(int)(
						(master.getFrame().getW() 
					- standardWidthChar*newOptionText.length() 
					* master.getFrame().getFactor())/2
						)
				);
		
		relocateAButtonToDrawnText(indexOption, standardWidthChar, buttonHeight);
		
	}
	

	// Executer Methods for options----------------------------------------------------
	/**
	 * Execute the Command associated to the option at pointer index
	 * @param pointer index of option to execute
	 */
	public final void executeOptionByButton(int pointer) {
		DynaSound.playEffect(0); // executeOption
		optionSwitcher(pointer);
		/* 
		 * Press a button that not change the slave panel deactivate the 
		 * keylistener, because master panel lose focus. Solution:
		// 1) example for request focus in the present window: 
		changePanel(different window);
		changePanel(present window);

		// 2) example, more elegant
		master.requestFocusInWindow();
		*/
		master.requestFocusInWindow();
	}
	/**
	 * Selector of a specific option Command. 
	 * It contains all options Commands
	 * @param pointer option index to refer
	 */
	protected abstract void optionSwitcher(int pointer);
	/**
	 * Executes the command associated with the option indicated by the Master pointer
	 */
	@Override
	public final void executeOption() {
		this.executeOptionByButton(master.getPointer());
	}
	
	
	// Methods to draw on the Master's Default Screen ---------------------------------
	/**
	 * We draw everything on the defScreen, than we draw the defScreen on the panel
	 * with the correct proportions of the JFrame.
	 * We can have a personalized background with this method on subtype of this class.
	 * The paintbrush of the supertype is different from the subtypes one.
	 * @param drawBackground true if you have to draw the background 
	 * @param drawOptions true if you have to draw the options 
	 * @param drawPointer true if you have to draw the arrow pointer 
	 */
	public void paintDefScreen(
			boolean drawBackground, 
			boolean drawOptions,
			boolean drawPointer
			) {
		
		Graphics2D g2 = master.getDefScreen().createGraphics();
		
		if(drawBackground) {
			g2.drawImage(background,
					0, 0,
					master.getFrame().getWStand(),
					master.getFrame().getHStand(),
					null
					);
		}

		// draws stuff on DefScreen
		g2 = drawStuff(g2);
		
		// draw Current Account
		if(GameModel.getInstance().isCurrAccountVisible()) {
			g2 = drawCurrAccount(g2);
		}

		if(drawOptions) {
			// draw string options
			g2 = drawStringOptions(g2);
		}
		
		if(drawPointer) {
			// draw pointer
			g2 = drawPointer(g2);
		}
		
		g2.dispose();
	}
	/**
	 * Draw the current Account on the left bottom of the panel
	 * @param g2 graphics context in which you draw (it's the defScreen)
	 * @return updated drawn graphics context
	 */
	protected Graphics2D drawCurrAccount(Graphics2D g2) {
		
		DecoratedAccount dAcc = (DecoratedAccount) GameModel.getInstance().getCurrAccount();
		
		Color c = dAcc.getColor();
		g2.setColor(c);
		g2.fillRect(
				10, 
				master.getFrame().getHStand()-40, 
				240, 
				30);
		
		g2.drawImage(
				dAcc.getDynaImage(),
				10, 
				master.getFrame().getHStand()-40,
				30,
				30,
				null
				);
		
		g2 = pencilPen(
				g2, 
				ColorTwister.getComplementary(c), 
				dAcc.getNickname(),
				master.getFrame().getHStand()-20,
				50,
				20);
		
		return g2;
	}
	/**
	 * A sub type implements this method to draw its specific view on the defScreen
	 * @param g2 graphics context in which you draw (it's the defScreen)
	 * @return updated drawn graphics context
	 */
	protected abstract Graphics2D drawStuff(Graphics2D g2);
	/**
	 * Draw the panel options on the defScreen
	 * @param g2 graphics context in which you draw (it's the defScreen)
	 * @return updated drawn graphics context
	 */
	protected Graphics2D drawStringOptions(Graphics2D g2) {
		
		for(int i=0; i<options.size();i++) {
			
			g2 = fountainPen(
					g2,
					options.get(i),
					posStandX.get(i), 
					posStandY.get(i),
					standardWidthChar,
					standardHeightChar
					);
		}
		
		return g2;
	}
	/**
	 * Draw the arrow pointer on the defScreen. It indicates the selected option to be executed by User.
	 * @param g2 graphics context in which you draw (it's the defScreen)
	 * @return updated drawn graphics context
	 */
	protected Graphics2D drawPointer(Graphics2D g2) {
		
		int p = master.getPointer();

		g2 = fountainPen(
				g2,
				"p",
				posStandX.get(p)-standardWidthChar, 
				posStandY.get(p),
				standardWidthChar,
				standardHeightChar
				);
		
		return g2;
	}
	/**
	 * Draw the skyscrapers image on the defScreen
	 * @param g2 graphics context in which you draw (it's the defScreen)
	 * @return updated drawn graphics context
	 */
	protected Graphics2D drawSkyscrapers(Graphics2D g2) {
		g2.drawImage(skyscrapers,
				0, 0,
				master.getFrame().getWStand(),
				master.getFrame().getHStand(),
				null
				);
		return g2;
	}
	
//	private void paintPanel() {
//		Graphics2D g = (Graphics2D) getGraphics();
//		g.drawImage(
//				master.getScreen(),
//				0,
//				0,
//				master.getFrame().getW(),
//				master.getFrame().getH(),
//				null);
//		//g.dispose();
//	}
//	
//	public void artAttack() {
//		paintDefScreen();
//		paintPanel();
//
//	}
	
//	protected Graphics2D superFountainPen(Graphics2D g2, String text, int x, int y, int w, int h) {
//		int i=0;
//		for(char c : text.toCharArray()) {
//			g2.drawImage(vocabo.getIm(c), x+w*i, y, w, h, null);
//			i++;
//		}
//		
//		return g2;
//	}
	
	// Methods to Draw text on the Panel ----------------------------------------------
	/**
	 * Draw a sprite version of an input String on the defScreen
	 * @param g2 graphics context
	 * @param text String to convert in sprite version
	 * @param xInit origin x coordinate
	 * @param yInit origin y coordinate
	 * @param w standard width for a sprite character of the converted String
	 * @param h standard height for a sprite character of the converted String
	 * @return updated drawn graphics context
	 */
	protected Graphics2D fountainPen(
			Graphics2D g2, String text, int xInit, int yInit, int w, int h) {
		
		int i = 0;
		for(char c : text.toCharArray()) {
			g2.drawImage(
					vocabo.getIm(c),
					xInit + w*i,
					yInit,
					w,
					h,
					null
					);
			i++;
		}
		return g2;
		
	}
	/**
	 * Draw a text on the defScreen using the special format chosen for this application
	 * @param g2 graphics context
	 * @param color text color
	 * @param text the text to draw
	 * @param y y origin coordinate
	 * @param x x origin coordinate
	 * @param dim font dimension
	 * @return updated drawn graphics context
	 */
	protected Graphics2D pencilPen(
			Graphics2D g2, Color color, String text, int y, int x, float dim) {
		g2.setFont(font);
		g2.setFont(g2.getFont().deriveFont(dim));
		g2.setPaint(Color.BLACK);
		g2.drawString(text, x-1, y-1);
//		g2.setPaint(Color.WHITE);
//		g2.drawString(text, x, y);
		g2.setFont(g2.getFont().deriveFont(dim));
		g2.setPaint(color);
		g2.drawString(text, x, y);
		return g2;
	}
	/**
	 * Draw a text on the defScreen using the special format chosen for this application.
	 * The color is the standard bomberman color.
	 * @param g2 graphics context
	 * @param text the text to draw
	 * @param y y origin coordinate
	 * @param x x origin coordinate
	 * @param dim font dimension
	 * @return updated drawn graphics context
	 */
	protected Graphics2D pencilPen(Graphics2D g2, String text, int y, int x, float dim) {
		return pencilPen(g2,new Color(255,80,0),text,y,x,dim);
	}
	/**
	 * Draw a text on the defScreen using the special format chosen for this application,
	 * The color is the standard bomberman color. The font dimension is the standard 40F
	 * @param g2 graphics context
	 * @param text the text to draw
	 * @param y y origin coordinate
	 * @param x x origin coordinate
	 * @return updated drawn graphics context
	 */
	protected Graphics2D pencilPen(Graphics2D g2, String text, int y, int x) {
		return pencilPen(g2,text,y,x,40F);
	}
	
	// --------------------------------------------------------------------------------
}
