package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Observable;
import java.util.Set;
import java.util.TreeSet;

/**
 * Game Model is the main actor of the application.
 * Contains conditional values ​​that govern information flow and thread generation. 
 * It holds all the information that is necessary to retain in compliance with the MVC pattern. 
 * Its fields are modified by the Controller who uses its methods. 
 * Changing its fields can trigger collateral responses in the view. 
 * In fact, as an observable it notifies its observers about some changes in its data.
 * 
 */
@SuppressWarnings("deprecation")
public class GameModel extends Observable{

	/**
	 * True if app is running.
	 * This condition keeps the menu thread looping.
	 */
	private static boolean app;
	/**
	 * True if a Thread Game is capable to start (to run)
	 * because Factories have have completed their tasks.
	 * becomes true if the main method thread is terminated, 
	 * i.e. all the main method operations have been performed. 
	 * In my version this fact is not exploited, however it would be possible 
	 * to preload certain assets in the main method by taking advantage 
	 * of the fact that this application is multi-threading. 
	 * However, a game session can begin if and only if all assets have been loaded. 
	 * In my case I load each level at runtime into the Loading state, contextually one at a time.
	 * The management of conditional architecture of the threads it's realized 
	 * by transmitting orders to observers.
	 */
	private static boolean ready;
	/**
	 * True if a game session is running.
	 * This condition keeps the game thread looping.
	 */
	private static boolean game;
	/**
	 * This is the current GameState 
	 * in Thread Game (State Design Pattern).
	 */
	private GameState state;
	/**
	 * Identify current state label.
	 */
	private StateLabel stateLabel;
	/**
	 * Reference of the actual level.
	 */
	private LevelAbstract actualLevel;
	/**
	 * Reference of the actual PC
	 */
	private PlayerCharacter actualPC;
	/**
	 * Reference of the actual Stage
	 */
	private Theater.Stage actualStage;
	/**
	 * Reference of the actual Scene
	 */
	private Theater.Scene actualScene;
	/**
	 * Model Timer used as flag for the flow of time: every bar in ThreadMenu, every second in Thread Game
	 */
	private ModelTimer modelTimer;
	/**
	 * Current value of a pointer
	 */
	private ModelPointer modelPointer;
	/**
	 * Holds the elapsed time. It is a counter that increases in the tread menu,
	 * while it is a countdown in the thread game.
	 */
	private ModelTimer playingTimer;
	/**
	 * Accumulated score in the current game Thread
	 */
	private long score;
	
	// Accounts
	/**
	 * Set of all accounts actually present in the application
	 */
	private Set<Account> accounts = new TreeSet<>();
	/**
	 * Current account
	 */
	private Account currAccount;
	/**
	 * Define the visibility of the current account
	 */
	private boolean currAccountVisible;
	
	// PowerUps
	/**
	 * Current Power Up Mediator
	 */
	private PowerUpMediator powerUpMediator;
	/**
	 * Enables the destructibility of power ups through the fires of the explosions
	 */
	private boolean destroyablePowerUp;
	
	// NPCs 
	/**
	 * Current Npc Mediator
	 */
	private NpcMediator npcMediator;

	// --------------- SIGLETON INNER CLASS VERSION -------------------------------
	/**
	 * Synchronized singleton with inner class to run correctly in multithreading.
	 * Lazy singleton or synchronized singleton didn't work in this case of multi threading.
	 */
	private GameModel() {
		
		app = true;
		game = false;
		ready = false;
		
		modelPointer = new ModelPointer(0);
		modelTimer = new ModelTimer(0,true);
		score = 0;
		
		destroyablePowerUp = true;
		currAccountVisible = true;
		
	}
	
	/**
	 * Singleton Pattern
	 * @return current Game Model instance
	 */
	public static GameModel getInstance() {
		return MyWrapperModel.INSTANCE;
	}
	/**
	 * Current instance of this singleton Game Model
	 */
    private static class MyWrapperModel {
        static GameModel INSTANCE = new GameModel();
    }
	// ----------------------------------------------------------------------------
    
	// Getters
    /**
     * Getter of app
     * @return boolean app
     */
	public boolean isApp() {return app;}
	/**
	 * Getter of ready
	 * @return boolean ready
	 */
	public boolean isReady() {return ready;}
	/**
	 * Getter of game
	 * @return boolean game
	 */
	public boolean isGame() {return game;}
	/**
	 * Getter of state
	 * @return current state
	 */
	public GameState getState() {return state;}
	/**
	 * Getter of stateLabel
	 * @return current stateLabel
	 */
	public StateLabel getStateLabel() {return stateLabel;}
	/**
	 * Getter of actualPC
	 * @return actualPC
	 */
	public PlayerCharacter getActualPC() {return actualPC;}
	/**
	 * Getter of Model Timer
	 * @return actual modelTimer
	 */
	public ModelTimer getModelTimer() {return modelTimer;}
	/**
	 * Getter of ModelPointer
	 * @return current modelPointer
	 */
	public ModelPointer getModelPointer() {return modelPointer;}
	/**
	 * Return the Cartesian Coordinate of Actual PC into the game grid
	 * @return actualPC's grid coordinates
	 */
	public CartesianCoordinate getPCXYGrid() {
		return new CartesianCoordinate(actualPC.getYGrid(),actualPC.getXGrid());
	}
	/**
	 * Getter of actual Level
	 * @return actualLevel
	 */
	public LevelAbstract getActualLevel() {return actualLevel;}
	/**
	 * Getter of actual Stage
	 * @return actualStage
	 */
	public Theater.Stage getActualStageLabel(){return actualStage;}
	/**
	 * Getter of actual Scene
	 * @return actualScene
	 */
	public Theater.Scene getActualSceneLabel(){return actualScene;}
	/**
	 * Getter of current Power Up Mediator
	 * @return current powerUpMediator
	 */
	public PowerUpMediator getPowerUpMediator() {return powerUpMediator;}
	/**
	 * Getter of boolean destroyablePowerUp
	 * @return boolean destroyablePowerUp
	 */
	public boolean isDestroyablePowerUp() {return destroyablePowerUp;}
	/**
	 * Getter of the value of playing timer
	 * @return playingTimer value
	 */
	public int getPlayingTimer() {return (int) playingTimer.getValue();}
	/**
	 * Getter of Npc Mediator
	 * @return actual Npc Mediator
	 */
	public NpcMediator getNpcMediator() {return npcMediator;}
	/**
	 * Getter of the set with all accounts
	 * @return all accounts
	 */
	public Set<Account> getAccounts(){return accounts;}
	/**
	 * Getter of the current account
	 * @return current account
	 */
	public Account getCurrAccount() {return currAccount;}
	/**
	 * Getter of current account's boolean visibility
	 * @return current account visible
	 */
	public boolean isCurrAccountVisible() {
		return currAccountVisible;
	}
	/**
	 * Getter of actual score value in memory
	 * @return actual score
	 */
	public long getScore() {return score;}	

	// Setters
	/**
	 * Set the actual Level and its corresponding Theater parameters
	 * @param level new level
	 * @param stage new stage
	 * @param scene new scene
	 */
	public void setActualLevel(
			LevelAbstract level, Theater.Stage stage, Theater.Scene scene) {
		this.actualLevel = level;
		actualStage = stage;
		actualScene = scene;
	}
	/**
	 * Set the current PC
	 * @param actualPC new current PC
	 */
	public void setActualPC(PlayerCharacter actualPC) {
		this.actualPC = actualPC;
	}
	
	// Methods for manage Power Up
	/**
	 * Set the power up mediator
	 * @param powerUpMediator current power up mediator
	 */
	public void setPowerUpMediator(PowerUpMediator powerUpMediator) {
		this.powerUpMediator = powerUpMediator;
	}
	/**
	 * Setter of destroyable power up
	 * @param destroyablePowerUp true if powerup are destroyable
	 */
	public void setDestroyablePowerUp(boolean destroyablePowerUp) {
		this.destroyablePowerUp = destroyablePowerUp;
	}
	
	// Methods for manage NPCs
	/**
	 * Setter of Npc Mediator
	 * @param npcMediator current Npc Mediator
	 */
	public void setNpcMediator(NpcMediator npcMediator) {
		this.npcMediator = npcMediator;
	}
	/**
	 * Set current account visible
	 * @param currAccountVisible new current account visible
	 */
	public void setCurrAccountVisible(boolean currAccountVisible) {
		this.currAccountVisible = currAccountVisible;
	}
	/**
	 * Reset value of score to zero (always at the end of a Thread Game)
	 */
	public void resetScore() {this.score = 0;}
	
	// ----------- MVC - OBSERVABLE-OBSERVER PATTERN -----------------------------------------

	/**
	 * Set Model Timer and transmit it to observers
	 * @param modelTimer new model Timer
	 */
	public void setModelTimer(ModelTimer modelTimer) {
		this.modelTimer = modelTimer;
		transmitTimer();
	}
	
	// Methods for set timer of StatePlaying and notify observer
	/**
	 * Setter of playingTimer with specific value
	 * @param time value of the new playingTimer
	 */
	public void setPlayingTimer(int time) {
		playingTimer = new ModelTimer(time,false);
	}
	/**
	 * Reduce playing timer and notify observer
	 */
	public void decPlayingTimer() {
		playingTimer.flow();
		transmit(playingTimer);
	}
	/**
	 * Notify observer to start a game session if game is true
	 */
	public void transmitGameOn() {
		if(game) {
			setChanged();
			notifyObservers(new Order(Order.Label.GAME_ON));
		}
	}
	
	// Setters Boolean to manage the game
	/**
	 * Setter of app and notify observer (to change condition of ThreadMenu's while looping)
	 * @param bool new app
	 */
	public void setApp(boolean bool) {
		app=bool;
		Order order = (bool==true)?
						new Order(Order.Label.APP_ON)
						:new Order(Order.Label.APP_OFF);
		setChanged();
		notifyObservers(order);
	}
	
	/**
	 * Setter of ready and notify observer
	 * @param bool new ready
	 */
	public void setReady(boolean bool) {
		ready=bool;
		Order order = (bool==true)?
				new Order(Order.Label.READY_ON)
				:new Order(Order.Label.READY_OFF);
		setChanged();
		notifyObservers(order);
	}
	/**
	 * Setter of game to start a new game session
	 * @param bool new game
	 */
	public void setGame(boolean bool) { 
		game = bool;
		Order order = (bool==true)?
				new Order(Order.Label.GAME_ON)
				:new Order(Order.Label.GAME_OFF);
		setChanged();
		notifyObservers(order);
	}
	/**
	 * It's used to restart the app.
	 * If true, it kills current Thread Game and wakes up Thread Menu.
	 * @param bool condition of restart
	 */
	public void transmitRestart(boolean bool) { 
		//game = bool;
		Order order = (bool==true)?
				new Order(Order.Label.RESTART_ON)
				:new Order(Order.Label.RESTART_OFF);
		setChanged();
		notifyObservers(order);
	}
	/**
	 * Setter of game state and update stateLabel
	 * @param state new game state
	 */
	public void changeState(GameState state) {
		this.state = state;
		this.stateLabel = state.getStateLabel();
		setChanged();
		notifyObservers(this.state);
	}
	/**
	 * Inrement score by gravedigger's score and transmit it to observers
	 * @param gravedigger gravedigger which gives the score value
	 */
	public void incScore(Gravedigger gravedigger) {
		this.score += gravedigger.getScore();
		transmit(gravedigger);
	}
	
	/**
	 * Transmit and Object to Observers.
	 * With this method you can transmit everything because Object is the supertype of every instance.
	 * @param o object to transmit
	 */
	public void transmit(Object o) { 
		setChanged();
		notifyObservers(o);
	}
	/**
	 * Transmit coordinates of actualPC
	 */
	public void transmitPcCoords() {
		setChanged();
		notifyObservers(new CartesianCoordinate(actualPC.getYPanel(),actualPC.getXPanel()));
	}
	
	
	// private transmitters
	/**
	 * transmission of modelPointer to observers
	 */
	private void transmitModelPointer() {
		setChanged();
		notifyObservers(modelPointer);
	}
	/**
	 * transmission of modelTimer to observers
	 */
	private void transmitTimer() {
		setChanged();
		notifyObservers(modelTimer);
	}
	
	/**
	 * Transmit a boolean to observers
	 * @param bool
	 */
	private void transmitBoolean(boolean bool) {
		setChanged();
		notifyObservers(bool);
	}
	
	/*
	 * Methods for Model Timer
	 */
	/**
	 * Increment ModelTimer and transmit it to observers
	 */
	public void timerFlow() {
		modelTimer.flow();
		transmitTimer();
	}
	/**
	 * Reset actual modelTimer
	 */
	public void resetModelTimer() {
		modelTimer.zero();
	}

	
	/*
	 * Methods for Model Pointer
	 */
	/**
	 * Increment modelPointer respecting its modulus
	 * and notify observers with it
	 * @param modulus modulus to respect
	 */
	public void incPointer(int modulus) {
		modelPointer.inc(modulus); 
		transmitModelPointer();
	}
	/**
	 * decrement modelPointer respecting its moudlus
	 * and notify observers with it
	 * @param modulus modulus to respect
	 */
	public void decPointer(int modulus) {
		modelPointer.dec(modulus);
		transmitModelPointer();
	}
	/**
	 * Reset pointer and notify observers with it
	 */
	public void resetPointer() {
		modelPointer.reset();
		transmitModelPointer();
	}
	
	
	/*
	 * Methods for boolean transmission
	 */
	/**
	 * Transmit true to Observers
	 */
	public void selected() {
		transmitBoolean(true);
	}
	/**
	 * Transmit false to Observers
	 */
	public void deselected() {
		transmitBoolean(false);
	}
	
	// ----------------------- MANAGE ACCOUNTS ----------------------------------------
	/**
	 * Transmit recent version of accounts to observers
	 */
	public void forwardAccounts() {
		// Type Object cannot be safely cast to TreeSet<Account>
		Account[] array = new Account[accounts.size()];
		accounts.toArray(array);
		transmit(array);
	}
	/**
	 * Transmit current Account (selected) to observers
	 */
	public void forwardCurrAccount() { 
		transmit(currAccount);
	}
	/**
	 * Set the current Account and trasmit it to observers
	 * @param currAccount the selected current Account
	 */
	public void setCurrAccount(Account currAccount) {
		this.currAccount=currAccount;
		forwardCurrAccount();
	}
	/**
	 * Add and Account to Game Model's accounts
	 * and transmit the updated Collection to observers
	 * @param a Account to add
	 */
	public void addAccount(Account a) {
		accounts.add(a);
		forwardAccounts();
	}
	/**
	 * Remove an account from Game Model's accounts
	 * and set its next account as current Account.
	 * Then transmit the update GameModel's accounts to observers.
	 * You can't remove Default account.
	 * @param a account to remove
	 */
	public void removeAccount(Account a) {
		if(!(a.getIdentifier()==0)) {
			accounts.remove(a);
			forwardAccounts();
			if(a.equals(currAccount)) {
				currAccount = accounts.iterator().next();
				forwardCurrAccount();
			}
		}		
	}
	
	/**
	 * Save accounts in a File with Serialization
	 * @param f file in which save the accounts' array
	 * @throws IOException from working with file stream
	 */
	public void exportSaveFile(File f) throws IOException {
		
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		Account[] arrayAccounts = accounts.toArray(new Account[accounts.size()]);
		
		oos.writeObject(arrayAccounts);
		
		oos.close();
		fos.close();
	}
	/**
	 * Load a save with Serialization.
	 * It deletes old accounts.
	 * In the end update observers with new accounts
	 * @param f file from which load the accounts' array
	 * @throws IOException from working with file stream
	 */
	public void importSavedFile(File f) throws IOException {
		
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		try {
			// delete every account that isn't default
			Iterator<Account> iter = accounts.iterator();
			while(iter.hasNext()) {
				Account account = iter.next();
				if(account.getIdentifier()!=0) {
					iter.remove();
				} else {
					// Keep in mind that if you modify the object returned by next(), those changes will be present in your collection.
					account.reset();
				}
			}
			Account[] loadedAccounts = (Account[]) ois.readObject();
			accounts.addAll(Arrays.asList(loadedAccounts));
			
			if(accounts.size()>0 && currAccount == null) {
				setCurrAccount(accounts.stream().findFirst().get()); 
				forwardCurrAccount();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();}
		
		ois.close();
		fis.close();
		
		forwardAccounts();
	}

}