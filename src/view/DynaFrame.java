package view;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JFrame;

import model.Account;
import model.Heroes;
import model.Theater;
/**
 * This is the personalized JFrame of the application in which the View manifests.
 */
public class DynaFrame extends JFrame implements MyJBombermanFormat {
	/**
	 * Serialized key for Serialization
	 */
	private static final long serialVersionUID = 7110570838852888177L;
	// Booleans to manage Conditional Architecture
	/**
	 * True when User inserts all input so a new game session can starts.
	 */
	private boolean timeToPlay;
	
	// Parameters of the GUI
	/**
	 * GUI standard width
	 */
	private final int standardWidth = TOTAL_WIDTH;
	/**
	 * GUI standard Height
	 */
	private final int standardHeight = TOTAL_HEIGHT;
	/**
	 * Current frame's width
	 */
	private int width;
	/**
	 * Current frame's height
	 */
	private int height;
	/**
	 * Current tenth of frame's width
	 */
	private int tenthWidth;
	/**
	 * Current tenth of frame's height
	 */
	private int tenthHeight;
	/**
	 * Correction factor to perfectly position the invisible 
	 * selection buttons above the panels option labels 
	 * when switching from fullscreen to normal screen and vice versa.
	 */
	private double correctionFactor;
	/**
	 * True if frame is on fullscreen
	 */
	private boolean fullscreen;
	/**
	 * Cardlayout's Master reference
	 */
	private DynaMasterCard master;
	// Selections by User
	/**
	 * User's chosen Stage
	 */
	private Theater.Stage selectedStage;
	/**
	 * Selected Scene to start
	 */
	private Theater.Scene selectedScene;
	/**
	 * User's chosen hero (to play)
	 */
	private Heroes selectedPC;
	// Accounts
	/**
	 * True if view asks to Model for updated accounts list
	 */
	private boolean requestAccountsToModel;
	/**
	 * True if view asks to Model for updated current account
	 */
	private boolean requestCurrAccountToModel;
	/**
	 * Accounts List saved in the view
	 */
	private Set<Account> accounts = new TreeSet<>();
	/**
	 * Current account saved in the view
	 */
	private Account currAccount;
	/**
	 * Constructor of this application's JFrame
	 */
	public DynaFrame() {
		super("JBomberman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		width = standardWidth;
		height = standardHeight;
		calculateDerivatedLens();
		setSize(width,height);
		
		correctionFactor = 1;
		
		setResizable(false);
		fullscreen = false;
		setUndecorated(true);
		DynaSound.getInstance(); // initialized DynaSound instance
		master = new DynaMasterCard(this);
		master.setBounds(0,0,width,height);
		add(master);

		//setLocationRelativeTo(null); // al centro del monitor	
		setVisible(true);
		
		requestAccountsToModel = requestCurrAccountToModel = true;
	}
	
	// Getters
	/**
	 * Getter of this frame's standard height
	 * @return standard height
	 */
	public int getWStand() {return standardWidth;}
	/**
	 * Getter of this frame's standard width
	 * @return standard width
	 */
	public int getHStand() {return standardHeight;}
	/**
	 * Getter of tenth of this frame's standard width
	 * @return tenth of standard width
	 */
	public int getTenthWStand() {return standardWidth/10;}
	/**
	 * Getter of tenth of this frame's standard height
	 * @return tenth of standard height
	 */
	public int getTenthHStand() {return standardHeight/10;}
	/**
	 * Getter of this frame's width
	 * @return current width
	 */
	public int getW() {return width;}
	/**
	 * Getter of this frame's height
	 * @return current height
	 */
	public int getH() {return height;}
	/**
	 * Getter of a tenth of current width
	 * @return tenth of current width
	 */
	public int getTenthW() {return tenthWidth;}
	/**
	 * Getter of a tenth of current height
	 * @return tenth of current height
	 */
	public int getTenthH() {return tenthHeight;}
	/**
	 * Getter of timeToPlay.
	 * If timeToPlay is true, it will be reset to false.
	 * @return timeToPlay value
	 */
	public boolean isTimeToPlay() {
		if(timeToPlay) {
			timeToPlay = false;
			return true;
		}
		return false;
		}
	/**
	 * Getter of fullscreen
	 * @return true if fullscreen is on
	 */
	public boolean isFullscreen() {return fullscreen;}
	/**
	 * Getter of correction factor
	 * @return current correction factor
	 */
	public double getFactor() {return correctionFactor;}
	/**
	 * Getter of the selected stage label
	 * @return selected stage
	 */
	public Theater.Stage getInitialSelectedStageLabel(){return selectedStage;}
	/**
	 * Getter of the selected scene label
	 * @return selected scene
	 */
	public Theater.Scene getInitialSelectedSceneLabel(){return selectedScene;}
	/**
	 * Getter of the selected hero label
	 * @return selected hero to play
	 */
	public Heroes getSelectedPCLabel(){return selectedPC;}
	/**
	 * Getter of CardLayout's Master
	 * @return CardLayout's Master
	 */
	public DynaMasterCard getMaster() { return master; }
	/**
	 * Getter of current CardLayout's slave (DynaSlaveCard)
	 * @return current DynaSlaveCard showed by CardLayout
	 */
	public DynaSlaveCard getSlave() { return master.getActivePanel(); }
	/**
	 * Getter of accounts list
	 * @return current accounts list in the view
	 */
	public Set<Account> getAccounts(){return accounts;}
	/**
	 * Getter of current account
	 * @return current account in the view
	 */
	public Account getCurrAccount() {return currAccount;}
	/**
	 * Getter of requestingAccountsToModel
	 * @return true if the view is requesting accounts to Model
	 */
	public boolean isRequestingAccountsToModel() {return requestAccountsToModel;}
	/**
	 * Getter of requestCurrAccountToModel
	 * @return true if the view is requesting current account to Model
	 */
	public boolean isRequestCurrAccountToModel() {return requestCurrAccountToModel;}
	
	// Setters
	/**
	 * Setter of actual stage
	 * @param actualStage new selected stage
	 */
	public void setActualStage(Theater.Stage actualStage){this.selectedStage = actualStage;}
	/**
	 * Setter of actual scene
	 * @param actualScene new selected scene
	 */
	public void setActualScene(Theater.Scene actualScene){this.selectedScene = actualScene;}
	/**
	 * Setter of actual hero
	 * @param actualPC selected hero to play
	 */
	public void setActualPC(Heroes actualPC){this.selectedPC = actualPC;}
	/**
	 * Setter of timeToPlay
	 * @param timeToPlay current timeToPlay value
	 */
	public void setTimeToPlay(boolean timeToPlay) {this.timeToPlay = timeToPlay;}
	/**
	 * Setter of requestAccountsToModel
	 * @param requestAccountsToModel new requestAccountsToModel value
	 */
	public void setRequestAccountsToModel(boolean requestAccountsToModel) {
		this.requestAccountsToModel = requestAccountsToModel;
	}
	/**
	 * Setter of requestCurrAccountToModel
	 * @param requestCurrAccountToModel new requestCurrAccountToModel value
	 */
	public void setRequestCurrAccountToModel(boolean requestCurrAccountToModel) {
		this.requestCurrAccountToModel = requestCurrAccountToModel;
	}
	/**
	 * Setter of view accounts list
	 * @param array new accounts list
	 */
	public void setAccounts(Account[] array) {
		Set<Account> modelAccounts = new TreeSet<>(Arrays.asList(array));
		this.accounts = modelAccounts;
	}
	/**
	 * Setter of view current account
	 * @param currAccount new current account value
	 */
	public void setCurrAccount(Account currAccount) {
		this.currAccount = currAccount;
	}
	/**
	 * Calculate the tenth based on current frame parameters
	 */
	public void calculateDerivatedLens() {
		tenthWidth = width / 10;
		tenthHeight = height / 10;
	}
	/**
	 * Relocate panels components based on current frame parameters
	 */
	public void redefinePanels() {
		master.getPanels().forEach( (x,y) -> y.relocateComponents());
	}
	/**
	 * Set the fullscreen mode on
	 */
	public void setFullScreenOn() {
		GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
		gDev.setFullScreenWindow(this);
		this.width = this.getWidth();
		this.height = this.getHeight();
		correctionFactor = (float)width / (float)standardWidth;
		calculateDerivatedLens();
		redefinePanels();
		fullscreen = true;
//		master.revalidate();
//		master.repaint();
	}
	/**
	 * Set the fullscreen mode off
	 */
	public void setFullScreenOff() {
		GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
		gDev.setFullScreenWindow(null);
		this.width = standardWidth;
		this.height = standardHeight;
		correctionFactor = 1;
		calculateDerivatedLens();
		redefinePanels();
		fullscreen = false;
	}
	
}
