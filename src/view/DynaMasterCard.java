package view;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import controller.KeyListenerGame;
import controller.KeyListenerMenu;
import model.Account;
import model.GameModel;
import model.ModelPointer;
import model.ModelTimer;
import model.Order;
/**
 * This Class is the Game Model' Observer during running ThreadMenu
 * and it is the Master of its CardLayout: the Layout that manages JPanels of the application.
 * A fundamental function of this class is its paintComponent method,
 * because it is the draw method used for each panel, also the game session panel (DynaSlaveGame).
 * Everything is drawn on the Master's defScreen by the active panel (A DynaSlaveCard),
 * then this defScreen is drawn by Master's paintComponent() with the real DynaFrame proportions.
 * In this way I achieve possibility to resize application image to desired resolution: 
 * normal screen, full-screen (but it's possible to add others resolutions).
 * Furthermore, having a panel of a fixed size allows me to define the dimensions and the positions
 * of all objects accordingly only once. The drawing in its entirety will then be resized.
 */
@SuppressWarnings("deprecation")
public class DynaMasterCard extends JPanel implements Observer, MyJBombermanFormat{
	/**
	 * Serialized key for Serialization.
	 */
	private static final long serialVersionUID = 3360726941883019303L;
	/**
	 * Reference to application frame.
	 */
	private DynaFrame dynaFrame;
	/**
	 * Each drawing is done on the defScreen. 
	 * Then the defScreen will be drawn by the Master 
	 * according to the real proportions of the DynaFrame.
	 */
	private BufferedImage defScreen;
	/**
	 * The pointer indicates the position of the arrow visible on the screen.
	 * It's updated by notifications from Game Model.
	 */
	private int pointer;
	/**
	 * Map of String as keys and panels as values.
	 * In this map there are all the panels managed by the Master (and the Application).
	 */
	private Map<String,DynaSlaveCard> panels;
	/**
	 * Name key of the current panel. 
	 */
	private String activePanel;
	/**
	 * Time counter updated every bar passed.
	 * It's used by DynaSlavePlayer (that is an Animate type object).
	 * 
	 */
	private long time;
	/**
	 * Last registered timer value, updated every second.
	 */
	private long tempoForSecond;
	/**
	 * Decide if default background must be drawn.
	 */
	private boolean drawDefaultBackground;
	/**
	 * Decide if options must be drawn.
	 */
	private boolean drawOptions;
	/**
	 * Decide if arrow pointer must be drawn.
	 */
	private boolean drawPointer;
	/**
	 * True every second passed.
	 */
	private boolean bSecond;
	// The Layout
	/**
	 * I use CardLayout to manage Application panels.
	 */
	private CardLayout cardLayout;
	// Panels
	/**
	 * DynaSlaveStart's reference in the Application.
	 */
	private DynaSlaveStart startPanel;
	/**
	 * DynaSlavePlayer's reference in the Application.
	 */
	private DynaSlavePlayer playerPanel;
	/**
	 * DynaSlaveStage's reference in the Application.
	 */
	private DynaSlaveStage stagePanel;
	/**
	 * DynaSlaveOption's reference in the Application.
	 */
	private DynaSlaveOption optionPanel;
	/**
	 * DynaSlaveAccount's reference in the Application.
	 */
	private DynaSlaveAccount accountPanel;
	/**
	 * DynaSlaveAccountsManager's reference in the Application.
	 */
	private DynaSlaveAccountsManager manageAccountsPanel;
	/**
	 * DynaSlaveAccountsStats's reference in the Application.
	 */
	private DynaSlaveAccountsStats statsAccountsPanel;
	/**
	 * DynaSlaveAccountCreation's reference in the Application.
	 */
	private DynaSlaveAccountCreation createAccountPanel;
	/**
	 * DynaSlaveGame's reference in the Application.
	 */
	private DynaSlaveGame gamePanel;
	/**
	 * Check the clicked condition from the mouse
	 */
	private boolean enterPressedByMouse;
	/**
	 * Constructor
	 * @param dynaFrame frame reference
	 */
	public DynaMasterCard(DynaFrame dynaFrame) {
		
		this.dynaFrame = dynaFrame;
		
		defScreen = new BufferedImage(dynaFrame.getWStand(),
				dynaFrame.getHStand(),
				BufferedImage.TYPE_INT_ARGB);
		
		panels = new HashMap<>();

		this.setOpaque(false);
		setFocusable(true);
		requestFocusInWindow(true);
		setDoubleBuffered(true);
		
		this.cardLayout = new CardLayout();
		
		setLayout(cardLayout);
		
		startPanel = new DynaSlaveStart(this);
		playerPanel = new DynaSlavePlayer(this);
		stagePanel = new DynaSlaveStage(this);
		optionPanel = new DynaSlaveOption(this);
		accountPanel = new DynaSlaveAccount(this);
		manageAccountsPanel = new DynaSlaveAccountsManager(this);
		statsAccountsPanel = new DynaSlaveAccountsStats(this);
		createAccountPanel = new DynaSlaveAccountCreation(this);
		gamePanel = new DynaSlaveGame(this);
		
		add(startPanel,"start");
		add(playerPanel,"player");
		add(stagePanel,"stage");
		add(optionPanel,"option");
		add(accountPanel,"account");
		add(manageAccountsPanel,"manageAccounts");
		add(statsAccountsPanel,"statsAccounts");
		add(createAccountPanel,"createAccount");
		add(gamePanel,"game");
		
		panels.put("start", startPanel);
		panels.put("player", playerPanel);
		panels.put("stage", stagePanel);
		panels.put("option", optionPanel);
		panels.put("account",accountPanel);
		panels.put("manageAccounts",manageAccountsPanel);
		panels.put("statsAccounts",statsAccountsPanel);
		panels.put("createAccount",createAccountPanel);
		panels.put("game", gamePanel);
		
		activePanel = "start"; 
		
		drawDefaultBackground = true;
		
		drawOptions = true;
		
		drawPointer = true;
		
		DynaSound.playSong("intro");
	}
	// Getters 
	/**
	 * Getter of frame related to this
	 * @return its frame
	 */
	public DynaFrame getFrame() { return dynaFrame; }
	/**
	 * Getter of the defScreen (default Screen)
	 * @return its defScreen
	 */
	public BufferedImage getDefScreen() {return defScreen;}
	/**
	 * Getter of the pointer
	 * @return its pointer
	 */
	public int getPointer() {return pointer;}
	/**
	 * Getter of registered time
	 * @return its registered time value
	 */
	public long getTime() {return time;}
	/**
	 * Getter of CardLayout
	 * @return its CardLayout
	 */
	public CardLayout getCardLayout() {return cardLayout;}
	/**
	 * Getter of panels map
	 * @return its panels map
	 */
	public Map<String,DynaSlaveCard> getPanels() {return panels;}
	/**
	 * Getter of active panel
	 * @return its active panel
	 */
	public DynaSlaveCard getActivePanel() {return panels.get(activePanel);}
	/**
	 * Getter of the specify panel
	 * @param name desired panel name
	 * @return desired panel
	 */
	public DynaSlaveCard getSpecificPanel(String name) {
		if(panels.containsKey(name)) {
			return panels.get(name);
		} else return null;
	}
	/**
	 * Getter of active panel name
	 * @return its active panel name
	 */
	public String getActiveName() {return activePanel;}
	/**
	 * Getter of enteredPressedeByMouse
	 * @return true if mouse has clicked
	 */
	public boolean isEnteredPressedByMouse() {
		if(enterPressedByMouse) {
			enterPressedByMouse = false;
			return true;
		}
		return false;
	}
	/**
	 * True during a second, false the next second
	 * @return true during a second
	 */
	public boolean isBSecond() {return bSecond;}
	
	// Setters
	/**
	 * Setter if pointer value
	 * @param pointer new pointer value
	 */
	public void setPointer(int pointer) {
		this.pointer = pointer;
	}
	/**
	 * Setter of drawDefaultBackground
	 * @param drawDefaultBackground new drawDefaultBackground value
	 */
	public void setDrawDefaultBackground(boolean drawDefaultBackground) {
		this.drawDefaultBackground = drawDefaultBackground;
	}
	/**
	 * Setter of drawOptions
	 * @param drawOptions new drawOptions value
	 */
	public void setDrawOptions(boolean drawOptions) {
		this.drawOptions = drawOptions;
	}
	/**
	 * Setter of drawPointer
	 * @param drawPointer new drawPointer value
	 */
	public void setDrawPointer(boolean drawPointer) {
		this.drawPointer = drawPointer;
	}
	/**
	 * Setter of enterPressedByMouse
	 * @param enterPressedByMouse new enterPressedByMouse value
	 */
	public void setEnterPressedByMouse(boolean enterPressedByMouse) {
		this.enterPressedByMouse = enterPressedByMouse;
	}
	
	// Paint Methods
	/**
	 * Change the active panel name of this CardLayout,
	 * so the getActivePanel() can return the requested one.
	 * @param name requested active panel name
	 */
	public void changeActivePanel(String name) {
		activePanel = name;
		tempoForSecond = time = 0;
		DynaSound.playSong(name);
	}
	/**
	 * Reinitialize the defScreen with a new BufferedImage.
	 */
	public void resetDefScreen() {
		defScreen = new BufferedImage(
				dynaFrame.getWStand(),
				dynaFrame.getHStand(),
				BufferedImage.TYPE_INT_ARGB);
	}
	/**
	 * Reset counter of tempo to zero.
	 */
	public void resetTempo() { time = 0; }
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		this.getActivePanel().paintDefScreen(
				drawDefaultBackground,
				drawOptions,
				drawPointer);
		
		g2.drawImage(defScreen, 
				0, 
				0, 
				dynaFrame.getW(),
				dynaFrame.getH(),
				null);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if(arg!=null) {

			if(arg instanceof Order) {
				
				// When ThreadGame die and ThreadMenu restart by join
				if( ((Order)arg).getLabel() == Order.Label.RESTART_OFF) {
					
					// Give the correct KeyListener to the Master 
					removeKeyListener(
							KeyListenerGame.getInstance()
							);
					
					addKeyListener(
							KeyListenerMenu.getInstance()
							);
					
					// Correct setting
					setDrawDefaultBackground(true);
					setDrawPointer(true);
					setDrawOptions(true);
					
				}
				
			} else if(arg instanceof ModelTimer) {
				
				this.repaint();
				ModelTimer timer = (ModelTimer) arg;
				time = timer.getValue(); 
				
				if(time>(tempoForSecond+FPS)) { // every 1 second
					
					tempoForSecond = time;
					
					bSecond = !bSecond;
					
					AvatarUser.getInstanceByAvatarAccount(
							((DecoratedAccount)GameModel.getInstance()
									.getCurrAccount())
									.getAvatarAccount()).incFootage();
				}
				
				
			} else if(arg instanceof Animate) { // getActivePanel() instanceof Animate

				if(getActivePanel() instanceof Animate) {
					((Animate)arg).animate(time);  // arg <= getActivePanel() 
				}
				
			} else if(arg instanceof ModelPointer) {
	
				int counter = ((ModelPointer) arg).getValue(); // unboxing
				if(pointer!=counter) DynaSound.playEffect(2);
				pointer = counter;
				
			} else if(arg instanceof Boolean) {
				
				Boolean bool = (Boolean) arg;
				if(bool) {
					panels.get(activePanel).executeOption();
				}
				else {
					DynaSound.playEffect(1);
					panels.get(activePanel).escFunction();
				}
				
			} else if(arg instanceof Account[]) {
				
				Account[] modelArrayAccounts = (Account[])arg;
				dynaFrame.setRequestAccountsToModel(false);
				dynaFrame.setAccounts(modelArrayAccounts);
				
				((DynaSlaveAccountsManager)panels.get("manageAccounts")).refreshTable();
				((DynaSlaveAccountsStats)panels.get("statsAccounts")).refreshTable();
				
			} else if(arg instanceof Account) {
				
				Account currAcc = (Account) arg;
				dynaFrame.setRequestCurrAccountToModel(false);
				dynaFrame.setCurrAccount(currAcc);
				
				((DynaSlaveAccount)panels.get("account"))
											.getCurrAccountPanel()
											.refreshCurrAccountValues();
				
				((DynaSlaveAccountsManager)panels.get("manageAccounts")).refreshTable();
				((DynaSlaveAccountsStats)panels.get("statsAccounts")).refreshTable();
				
			}
				
		}
	}
	
}
