package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import javax.swing.JButton;

import controller.GameFirstOptionListener;
import controller.GameSecondOptionListener;
import controller.ButtonGameMouseListener;
import model.CartesianCoordinate;
import model.GameModel;
import model.GameState;
import model.Gravedigger;
import model.ModelPointer;
import model.ModelTimer;
import model.MyGridFormat;
import model.Order;
import model.PropWrapper;
import model.StateLabel;
import model.LevelAbstract;
import model.Figure;
import model.PlayerCharacter;
import model.Theater.Scene;
/**
 * This Class is the Game Model' Observer during running ThreadGame.
 * It represents the shown game session.
 * Game Model notifications updates it. 
 * It has a Game State Pattern similar to Controller to emulate it. 
 */
@SuppressWarnings("deprecation")
public class DynaSlaveGame extends DynaSlaveCard 
		implements Observer, MyJBombermanFormat, MyGridFormat {
	/**
	 * Serialized key for Serialization.
	 */
	private static final long serialVersionUID = 736170877595188129L;
	//private StateLabel stateLabel;
	/**
	 * Current state
	 */
	private StatePanel currentState;
	/**
	 * All states of this DynaSlaveGame
	 */
	private Map<StateLabel,StatePanel> states;
	/**
	 * Seconds remained to play. They are shown in the dock timer.
	 */
	private long time;
	/**
	 * Utility Random field
	 */
	private Random rand = new Random();
	/**
	 * Current PC's x coordinate
	 */
	private int xPC;
	/**
	 * Current PC's y coordinate
	 */
	private int yPC;
	/**
	 * Utility field to draw dock elements.
	 */
	private int xInit;
	/**
	 * Boolean to manage PC's drawing state.
	 */
	private boolean pcDeath, pcWin, bFlash,kantGlasses;
	
	// Gravediggers setting
	/**
	 * Current gravediggers
	 */
	private Set<Gravedigger> gravediggers = new HashSet<>();
	/**
	 * List of gravediggers to be removed
	 */
	private Set<Gravedigger> gravediggersToRemove = new HashSet<>();
	/**
	 * Condition to avoid Concurrent Exception managing gravediggers.
	 */
	private boolean goingConcurrent;
	/**
	 * Constructor.
	 * The panel in which the game session runs.
	 * @param master CardLayout Master reference
	 */
	public DynaSlaveGame(DynaMasterCard master) {
		super(
				master, 
				2,
				new String[] { "" , "" , "" },
				new int[] {5,6,7},
				"bgblue"
				);
		
		states = new HashMap<>();
		
		states.put(StateLabel.LOADING, new StatePanelLoading(this));
		states.put(StateLabel.STARTER, new StatePanelStarter(this));
		states.put(StateLabel.PLAYING, new StatePanelPlaying(this));
		states.put(StateLabel.PAUSED, new StatePanelPaused(this));
		states.put(StateLabel.CONTINUE, new StatePanelContinue(this));
		states.put(StateLabel.GAMEOVER, new StatePanelGameOver(this));
		states.put(StateLabel.WINLEVEL, new StatePanelGameWin(this));
		states.put(StateLabel.WINSTAGE, new StatePanelWinStage(this));
		
		setCurrentState(states.get(StateLabel.LOADING));
		
	}
	
	// Getters
	/**
	 * Getter of the actual level from the Game Model
	 * @return current Level
	 */
	public DecoratedLevel getLevel() {
		return (DecoratedLevel) GameModel.getInstance().getActualLevel();
	}
	/**
	 * Getter of seconds remained to play
	 * @return current game time
	 */
	public long getTime() {return time;}
	/**
	 * Check if gravediggers list is empty
	 * @return true if gravediggers list is empty
	 */
	public boolean isGravediggersEmpty() {return gravediggers.isEmpty();}
	
	// Setters
	/**
	 * Set the state in input as the current State
	 * @param currentState the new current state
	 */
	private void setCurrentState(StatePanel currentState) {
		this.currentState=currentState;
		updateOptionsStrings();
	}
	/**
	 * Setter of current state indicated by the input label
	 * @param stateLabel new state label
	 */
	public void setCurrentState(StateLabel stateLabel) {
		//this.stateLabel=stateLabel;
		setCurrentState(states.get(stateLabel));
	}
	/**
	 *Set drawing conditions and option strings according to the current state
	 */
	private void updateOptionsStrings(){
		currentState.handleUpdateOptionsStrings();
	}
	
	// Overrides of super type DynaSlaveCard
	@Override
	protected void addMouseListenerToTheButton(
			DynaSlaveCard dsc, JButton button, int iOption) {
		button.addMouseListener(new ButtonGameMouseListener(dsc,iOption));
	}
	
	@Override
	public void escFunction() {}
	
	@Override
	protected void optionSwitcher(int pointer) {
		currentState.handleOptionSwitcher(pointer);
	}
	
	@Override
	public Graphics2D drawStuff(Graphics2D g2){
		
		return currentState.handleDrawStuff(g2);
		
	}
	
	// --- UPDATE METHOD FOR O-O PATTERN. THIS PANEL IS AN OBSERVER TO GAME MODEL --- 
	
	@Override
	public void update(Observable o, Object arg) {
		
		if(arg!=null) {
		
			if(arg instanceof Order) {
				
				if( ((Order)arg).getLabel() == Order.Label.GAME_ON) {
					
					this.repaint();
				}
			
				if( ((Order)arg).getLabel() == Order.Label.RESTART_ON) {
					
					getAButton(0).removeActionListener(
							GameFirstOptionListener.getInstance()
							);
					
					getAButton(1).removeActionListener(
							GameSecondOptionListener.getInstance()
							);
					
					this.changePanel("start");
				}
				
			} else if(arg instanceof ModelTimer) {

				time = ((ModelTimer)arg).getValue(); // every second
				if(currentState instanceof StatePanelPlaying) {
					getLevel().incFootage();
				}
				
			} else if(arg instanceof DecoratorGravedigger) {
				goingConcurrent = true;
				gravediggers.add((DecoratorGravedigger)arg);
				goingConcurrent = false;
				
			} else if(arg instanceof CartesianCoordinate) {
				
				yPC = ((CartesianCoordinate)arg).getY();
				xPC = ((CartesianCoordinate)arg).getX();
				
			} else if(arg instanceof ModelPointer) {
						
				ModelPointer counter = (ModelPointer) arg;
				
				master.setPointer(counter.getValue());
				this.executeOption();
				
			} else if (arg instanceof Boolean) {
				
				Boolean bool = (Boolean) arg;
				
				if(bool) {
					this.executeOption();
				}
				
			} else if(arg instanceof Animations) {
				
				Animations animation = (Animations)arg;
				
				if( animation == Animations.GRID_INC) {
					
					bFlash = ! bFlash;
					
					// Grave diggers management
					if(!gravediggers.isEmpty() && !goingConcurrent) {
						goingConcurrent = true;
						// protection from Concurrent exception by set
						Gravedigger[] array = gravediggers.toArray(new Gravedigger[] {});
						for(int i=0; i<array.length; i++) {
							Gravedigger gd = array[i];
							((DecoratorGravedigger)gd).incState();
							getLevel().incFootageScore();
							if(gd instanceof DecoratedGravediggerByNpc) {
								((DecoratedGravediggerByNpc)gd).createScore(gravediggers);
							}
							if(((DecoratorGravedigger)gd).endFootage()) {
								gravediggersToRemove.add(gd);
							}
						}
						
						if(!gravediggersToRemove.isEmpty()) {
							gravediggersToRemove.forEach(gd -> gravediggers.remove(gd));
							gravediggersToRemove.clear();
						}
						
						goingConcurrent = false;

					}

				}
				
				if( animation == Animations.PC_DEATH ) {
					pcDeath = true;
				}
				if( animation == Animations.PC_WIN ) {
					pcWin = true;
				}
				
				((Animations)arg).animate((GameModel)o);
				
			} else if(arg instanceof GameState) {
				
				StateLabel stateLabel = ((GameState)arg).getStateLabel();

				switch(stateLabel) { // DynaSound
					case LOADING -> {}
					case STARTER -> {DynaSound.playSong("starter");}
					case PAUSED -> {DynaSound.playSong("paused");}
					case PLAYING -> {
						int ordinalScene = GameModel.getInstance().getActualLevel().getScene().ordinal()+1;
						DynaSound.playSong("level"+ordinalScene);
					}
					case CONTINUE -> {DynaSound.playSong("continue");}
					case GAMEOVER -> {DynaSound.playSong("gameOver");} 
					case WINLEVEL -> {DynaSound.playSong("winLevel");}
					case WINSTAGE -> {DynaSound.playSong("winStage");}
					default -> {}
				}
				
				//this.stateLabel = stateLabel;
				
				this.currentState = states.get(stateLabel);
				
				this.setCurrentState(currentState);

				this.repaint();
				
				if(currentState instanceof StatePanelPlaying) {
					pcDeath = false;
					pcWin = false;
					kantGlasses = GameModel.getInstance().getActualPC().hasKantGlasses();
				}				

			}
		}

	}
	
	
	// ------------- PAINTING METHODS ----------------------------------------------
	/**
	 * Check if the current scene level is the last (eighth) one
	 * @return true if the current scene is the eighth of the stage
	 */
	public boolean actualSceneIsEighth() {
		LevelAbstract level = getLevel();
		if(level!=null) {
			return level.getScene()==Scene.SCENE_8;
		}
		return false;
	}
	/**
	 * Draw a rectangle to fill the entire defScreen.
	 * If level scene is SCENE_8 rectangle color is rainbow, else is black.
	 * @param g2 graphic context in which we draw (the defScreen)
	 * @return updated drawn graphic context
	 */
	Graphics2D paintItBlack(Graphics2D g2) {
		if(actualSceneIsEighth()) {
			g2.setColor(new Color(rand.nextInt(0,256),rand.nextInt(0,256),rand.nextInt(0,256)));
		} else {
			g2.setColor(Color.BLACK);
		}
		g2.fillRect(0, 0, master.getFrame().getW(), master.getFrame().getH());
		return g2;
	}
	/**
	 * Draw a rectangle in the center of graphic context from input.
	 * If level scene is SCENE_8 rectangle color is rainbow, else is black.
	 * @param g2 graphic context in which we draw (the defScreen)
	 * @return updated drawn graphic context
	 */
	Graphics2D paintWindowBlack(Graphics2D g2) {
		if(actualSceneIsEighth()) {
			g2.setColor(new Color(rand.nextInt(0,256),rand.nextInt(0,256),rand.nextInt(0,256)));
		} else {
			g2.setColor(Color.BLACK);
		}
		g2.fillRect(
				(master.getFrame().getWStand() - 300) / 2, 
				(master.getFrame().getHStand() - 300) / 2, 
				300, 
				300
				);

		return g2;
	}
	/**
	 * Draw a text into the black window drawn by paintWindowBlack
	 * @param g2 graphic context in which we draw (the defScreen)
	 * @param title text to draw
	 * @return updated drawn graphic context
	 */
	Graphics2D paintString(Graphics2D g2, String title) {
		int wCharTitle = 30;
		int hCharTitle = 40;
		g2 = fountainPen(
				g2, 
				title, 
				(master.getFrame().getWStand() - title.length()*wCharTitle) / 2, 
				(master.getFrame().getHStand() - 200) / 2, 
				wCharTitle, 
				hCharTitle);
		return g2;
	}
	/**
	 * Draw the upper playing panel in which are contained informations relative to the player character
	 * @param g2 graphic context in which we draw (the defScreen)
	 */
	void renderDock(Graphics2D g2) {
		
		int quarterHD = HEIGHT_DOCK / 4;
		int eighthHD = quarterHD / 2;
		int centratedHD = quarterHD + eighthHD;
		
		// Dock color
		g2.setColor(getLevel().getDockColor());
		g2.fillRect(0, 0, master.getFrame().getW(), HEIGHT_DOCK);
		// Borders of panel
		g2.setColor(getLevel().getColorBrown());  // g2.fillRect(quarterHD, quarterHD, master.getFrame().getW()-quarterHD*2, quarterHD*2);
		g2.fillRect(quarterHD-5, quarterHD-5, master.getFrame().getW()-quarterHD*2, 4); // up-border
		g2.fillRect(quarterHD-5, quarterHD+5+UNIT_NORMAL, master.getFrame().getW()-quarterHD*2, 4); // down-border
		g2.fillRect(quarterHD-5, quarterHD-5, 4, UNIT_NORMAL+10); // left-border
		g2.fillRect(master.getFrame().getW()-quarterHD*2+15, quarterHD-5, 4, UNIT_NORMAL+10); // right-border
		
		// attemptsIcon METTERE ACCOUNTICON e togliere attemptsIcon
		xInit = 25;

		g2.drawImage(getLevel().getAttemptsIcon(),
				xInit,
				quarterHD,
				UNIT_NORMAL,
				UNIT_NORMAL,
				null);
		// attempts counter
		xInit += UNIT_NORMAL;
		int dimCounter = 25;
		String attemptsCounter = GameModel.getInstance().getActualPC().getAttempts()+"";
		g2 = fountainPen(g2, "b", xInit, quarterHD, dimCounter+1, dimCounter+1);
		g2 = fountainPen(g2, attemptsCounter, xInit, quarterHD, dimCounter, dimCounter);
		
		// livesIcon
		xInit += dimCounter+UNIT_SMALL;
		int lifeCounter = GameModel.getInstance().getActualPC().getLives();
		if(lifeCounter<0) lifeCounter = 0;
		String stringLifeCounter = lifeCounter+"";
		g2.drawImage(getLevel().getLivesIcon(),
				xInit,
				quarterHD,
				UNIT_NORMAL,
				UNIT_NORMAL,
				null);
		xInit += UNIT_NORMAL / 4;
		g2 = fountainPen(g2, stringLifeCounter, xInit, quarterHD+UNIT_NORMAL/4, dimCounter, dimCounter);
		
		// Draw Timer ---------------------------------------------------------------
		xInit += UNIT_NORMAL + UNIT_SMALL;
		g2.drawImage(getLevel().getImage(),
				xInit,
				quarterHD,
				UNIT_NORMAL,
				UNIT_NORMAL,
				null);
		xInit += UNIT_NORMAL;
		
		String stringMinutes = ((int) (time / 60))+"";
		String stringSeconds = ((int) (time % 60))+"";
		
		if(stringMinutes.length()<2) stringMinutes = " " + stringMinutes;
		if(stringSeconds.length()<2) stringSeconds = "0" + stringSeconds;
		
		String borderSeconds = "bb";
		String bordersMinutes = "";
		
		bordersMinutes = "b".repeat(stringMinutes.length());
		
		// Draw minutes
		g2 = fountainPen(g2, bordersMinutes, xInit, centratedHD, dimCounter, dimCounter);
		g2 = fountainPen(g2, stringMinutes, xInit, centratedHD, dimCounter, dimCounter);
		
		IntStream.range(0, stringMinutes.length()).forEach(i -> xInit += dimCounter);
		
		// draw ":"
		g2 = fountainPen(g2, "b", xInit, centratedHD, dimCounter, dimCounter);
		g2 = fountainPen(g2, "t", xInit, centratedHD, dimCounter, dimCounter);
		xInit += dimCounter;
		
//		// draw seconds
		g2 = fountainPen(g2, borderSeconds, xInit, centratedHD, dimCounter, dimCounter);
		g2 = fountainPen(g2, stringSeconds, xInit, centratedHD, dimCounter, dimCounter);
		xInit += dimCounter;

		
		// Draw Score ---------------------------------------------------------------
		xInit += UNIT_NORMAL + UNIT_SMALL;
		g2.drawImage(getLevel().getImageScore(),
				xInit,
				quarterHD,
				UNIT_NORMAL,
				UNIT_NORMAL,
				null);
		xInit += UNIT_NORMAL;
		
		String blankBlackboard = "bbbbbbbbbb";
		g2 = fountainPen(g2, blankBlackboard, xInit, centratedHD, dimCounter, dimCounter);

		g2 = fountainPen(
				g2, 
				fillBlackboard(
						blankBlackboard.length(),
						GameModel.getInstance().getScore()
						), 
				xInit, 
				centratedHD, 
				dimCounter, 
				dimCounter);
		
	}
	/**
	 * Format the score string in the manner it can be drawn with correct spacing.
	 * @param blackboardSpaces total of spaces
	 * @param score score value
	 * @return formatted score String
	 */
	private String fillBlackboard(int blackboardSpaces, long score) {
		String s = score+"";
		int spaceToAdd = blackboardSpaces - s.length();
		while(spaceToAdd>0) {
			s = "b"+s;
			spaceToAdd--;
		}
		return s;
	}
	
	// --------------- RENDER METHODS -----------------------------------------------
	/**
	 * Draw Arches of the Level with current Arches method
	 * @param g2 graphic context in which we draw (the defScreen)
	 */
	void renderArchs(Graphics2D g2) {
		getLevel().getArches().render(g2);
	}
	/**
	 * Draw the Tessera grid of the current Level
	 * @param g2 graphic context in which we draw (the defScreen)
	 */
	void renderBackgroundGrid(Graphics2D g2) {
		
		int offsetY = OFFSET_Y;
		int offsetX = OFFSET_X;
		
		for(int y=0; y<ROWS; y++) {
			for(int x=0; x<COLUMNS; x++) {
				Figure figure = GameModel.getInstance().getActualLevel().getGrid()[y][x];
				g2.drawImage(((FootageFuncs)(figure)).getImage(),
						x*UNIT_NORMAL+offsetX,
						y*UNIT_NORMAL+offsetY,
						UNIT_NORMAL,
						UNIT_NORMAL,
						null);
				if((figure instanceof PropWrapper) && ((PropWrapper)figure).getBundle()!=null) {
					g2.drawImage(((FootageFuncs)(((PropWrapper)figure).getBundle())).getImage(),
							x*UNIT_NORMAL+offsetX,
							y*UNIT_NORMAL+offsetY,
							UNIT_NORMAL,
							UNIT_NORMAL,
							null);
				}
			}
		}
		
	}
	/**
	 * Draw PowerUps presents in the grid using enjoyPowerUpMediator method.
	 * @param g2 graphic context in which we draw (the defScreen)
	 */
	void renderPowerUps(Graphics2D g2) {
		EnjoyPowerUpMediator dpum = (EnjoyPowerUpMediator)GameModel.getInstance().getPowerUpMediator();
		g2 = dpum.renderPowerUps(g2);
	}
	/**
	 * Draw the Player Character and all the NPCs. We uses the EnjoyNpcMediator method.
	 * @param g2 graphic context in which we draw (the defScreen)
	 */
	void renderNPCsWithPlayer(Graphics2D g2) {
		EnjoyNpcMediator dnpcm = ((EnjoyNpcMediator)GameModel.getInstance().getNpcMediator());
		g2 = dnpcm.renderNPCsWithPlayer(g2, xPC, yPC, choosePCImage(), bFlash, kantGlasses);
		
		// ----- PC's Gravity Center------
//		CartesianCoordinate c = GameModel.getInstance().getActualPC().getGravityCoords();
//		g2.setColor(Color.RED);
//		g2.drawRect(
//				c.getX(), 
//				c.getY(), 
//				50, 
//				50);
		//-----------------------
	}
	/**
	 * Select the correct PlayerCharacter image for the current game play
	 * @return correct PC image
	 */
    BufferedImage choosePCImage() {
    	BufferedImage pcImage;
    	PlayerCharacter pc = GameModel.getInstance().getActualPC();
    	if(pcDeath) {
    		pcImage = 
        		((FootageDeath) pc).getImageDeath();
    	} else if(pcWin) {
        	pcImage = 
            	((FootageWin) pc).getImageWin();
    	} else {
        	pcImage = 
            	((FootageFuncs) pc).getImage();
    	}

    	if(bFlash && pc.getInvincibility()>0) return ColorTwister.whitener(pcImage);

    	return pcImage;
    }
    /**
     * Draw current gravediggers
	 * @param g2 graphic context in which we draw (the defScreen)
     */
    void renderGravediggers(Graphics2D g2) {
    	
    	for(Gravedigger gd : new HashSet<Gravedigger>(gravediggers)) {

    		if(gd instanceof DecoratedGravediggerScore) {
    			
    			DecoratedGravediggerScore gdPu = (DecoratedGravediggerScore)gd;
    			
    			int i = 0;
    			for(BufferedImage bi : gdPu.getListImages()) {
    				g2.drawImage(
    						bi,
        					gdPu.getXPanel()+UNIT_SMALL*i++,
        					gdPu.getYPanel()-gdPu.getActualState()*2,
        					UNIT_SMALL,
        					UNIT_SMALL,
    						null);
    			}

    			
    		} else if(gd instanceof DecoratedGravediggerByNpc) {
    			
    			DecoratedGravediggerByNpc gdNpc =(DecoratedGravediggerByNpc)gd;

				g2.drawImage(
						gdNpc.getAnimatedImage(),
						gdNpc.getXPanel(),
						gdNpc.getYPanel(),
						gdNpc.getWGrave(),
						gdNpc.getHGrave(),
						null);
    			}
    			
    		}
    	
		g2.dispose(); // end of all renders, cancel the current account label on the left bottom corner
	    
    	}
    	
}
