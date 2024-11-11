package controller;

import model.Dir;
import model.GameModel;
import model.ModelTimer;
import view.DynaFrame;
import view.DynaMasterCard;
import view.DynaSlaveAccountCreation;
import view.DynaSlaveCard;

/**
 * The JBomberMan's Main method always start an instance of this class
 * which will be the same in all life-cycle of the application.
 * This class represents the code of the Thread that regulates all operations
 * in the menus sections of the application.
 * This thread dies only when the application is terminated.
 */
public class ThreadMenu extends ThreadDelta {
	/**
	 * key received from KeyListenerMenu (input by User).
	 * The key value determines values of up and down (super's fields).
	 */
	private Dir key;
	/**
	 * modulus of the current DynaSlaveCard for selecting options
	 */
	private int mod;
	/**
	 * The DynaFrame reference will be taken from DynaManager.
	 * It's saved here only to reduce wordiness and DynaManager invocations.
	 */
	private DynaFrame dynaFrame;
	/**
	 * Cardlayout's Master reference taken from this.dynaFrame.
	 * It's saved here only to reduce wordiness and DynaManager invocations.
	 */
	private DynaMasterCard master;
	/**
	 * Reference of the current DynaSlaveCard
	 */
	private DynaSlaveCard ds;
	/**
	 *Constructor of ThreadMenu
	 */
	public ThreadMenu() {}
	
	// ----------------- Run's Template Method --------------------
	@SuppressWarnings("deprecation")
	@Override
	protected void preparation() {
		
		// Timers
		GameModel.getInstance().setModelTimer(new ModelTimer(0,true));
		
		// Observable-Observer
		GameModel.getInstance().addObserver(
				DynaManager.getInstance().getFrame().getMaster()
				);
		
		GameModel.getInstance().transmitRestart(false);
		
		dynaFrame = DynaManager.getInstance().getFrame();
		
		master = dynaFrame.getMaster();
	}
	
	@Override
	protected boolean loopCondition() {
		return GameModel.getInstance().isApp();
	}
	@Override
	protected void coreFunctions() {
		GameModel.getInstance().timerFlow(); 
		
		if(timerGeneral>=tenthSecond) {
			GameModel.getInstance().transmit(ds);
			timerGeneral=0;
		}
		
		
		if(master.getActivePanel()!=null) {
			ds = master.getActivePanel();
			mod = ds.getMod();
		}
		
		checkAccountContext();
		
		// --------- OPTIONS SELECTION ------------------
		
		receivingOrder();
		
		checkUpOrDown(mod);
		
		checkEnterOrEsc();
		
		// --------- THREAD GAME STARTER ----------------
		
		if(DynaManager.getInstance().getFrame().isTimeToPlay()) {
			startThreadGame(); // freeze here
			preparation(); // reset ObservableObserver with Master Observer and its KeyListener
		}
	}
	// ------------- CLASS' METHODS ------------------------
	/**
	 * In particular, this method deals with account status updates.
	 * Consistent with MVC, this method exposes the view requests (from the DynaFrame)
	 * to the model which will respond via the Observable-Observer pattern.
	 */
	private void checkAccountContext() {
		// ask accounts to model 
		if(dynaFrame.isRequestingAccountsToModel()) {
			GameModel.getInstance().forwardAccounts();
		}
		
		// ask current account to model
		if(dynaFrame.isRequestCurrAccountToModel()) {
			GameModel.getInstance().forwardCurrAccount();
		}
		
		if(ds instanceof DynaSlaveAccountCreation) {
			if(((DynaSlaveAccountCreation)ds).isCreated()) {
				GameModel.getInstance().addAccount(
				((DynaSlaveAccountCreation)ds).getCreatedDecoratedAccount());
			}
		}
	}
	
	/**
	 * Capture and update inputs conditional booleans from the KeyListenerMenu
	 */
	private void receivingOrder() {
		
		key = KeyListenerMenu.getInstance().getDir();
		
		up = key==Dir.UP;
		down = key==Dir.DOWN;
		enter = KeyListenerMenu.getInstance().isEnter();
		esc = KeyListenerMenu.getInstance().isEsc();
		
	}
	/**
	 * Generate a new ThreadGame and wait for that to die
	 */
	private void startThreadGame() {
		
		Thread game = JBomberMan.runGameSession();
		
		try {
			game.join(); // freeze this thread while current threadGame is running
		} catch (InterruptedException e) {e.printStackTrace();}
		
	}
}
