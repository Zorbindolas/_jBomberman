package controller;

import model.Bomb;
import model.CartesianCoordinate;
import model.Dir;
import model.GameModel;
import model.Heroes;
import model.LevelAbstract;
import model.PlayerCharacter;
import model.PowerUpMediator;
import model.StateLabel;
import model.TimerExplosion;
import model.VisitorCharactersMover;
import model.NpcMediator;
import view.Animations;
import view.DecoratedBomb;
import view.DecoratedBombRemote;
import view.DecoratedLevel;
import view.EnjoyNpcMediator;
import view.EnjoyPowerUpMediator;
import view.DynaSlaveCard;
import view.DynaSlaveGame;
import view.DynaSound;
import view.MyJBombermanFormat;
/**
 * This state is connected by design in these ways:
 * (1) from StateStarter to this at the end of StateStarter's count down;
 * (2) from this to StatePaused if this' actualTG.isPaused = true; 
 * (3) from this to StateContinue if this' gameContinue = true;
 * (4) from this to StateGameOver if this' gameOver = true;
 * (5) from this to StateWinLevel if this' gameWin = true and there's another next level;
 * (6) from this to StateWinStage if this' gameWin = true and there's not another next level.
 * 
 * This is the state in which the game is played.
 */
public class StatePlaying extends StateAbstract implements MyJBombermanFormat {
	/**
	 * Condition for player'death check.
	 */
	private boolean death;
	/**
	 * The actual visitor (Visitor Design Pattern) to manage the movements of the player's character
	 */
	private VisitorCharactersMover vcm;
	/**
	 * The mediator to manage dependencies towards power-ups and power-ups mechanics
	 */
	private PowerUpMediator powerUpMediator = EnjoyPowerUpMediator.getInstance();
	/**
	 * The mediator to manage dependencies towards NonPlayableCharacters and their mechanics
	 */
	private NpcMediator npcMediator = EnjoyNpcMediator.getInstance();
	/**
	 * Total number of seconds of the timer at the beginning
	 */
	private int wholePlayingTimer;	// StateStarter set playingTimer to 180 when start a new Scene (Level).
	/**
	 * Number used to divide the Timer's starting total.
	 */
	private int timerDivision;
	/**
	 * REFRESH_STANDARD counter to complete a transition from this state to another
	 */
	private int transitionTimer;
	/**
	 * Number of REFRESH_STANDARD for Continue|GameOver transitions
	 */
	private final int pauseTransitionContinueAndGameOver = 10;
	/**
	 * Number of REFRESH_STANDARD for LevelWin|StageWin transitions
	 */
	private final int pauseTransitionWin = 20;
	/**
	 * This condition is used to know if a transition is in progress
	 */
	private boolean transition;
	/**
	 * This condition establishes whether the transition to StateContinue must occur
	 */
	private boolean gameContinue;
	/**
	 * This condition establishes whether the transition to StateGameOver must occur
	 */
	private boolean gameOver;
	/**
	 * This condition establishes whether the transition to StateWinLevel||StateWinStage must occur
	 */
	private boolean gameWin;
	/**
	 * Concrete GameState constructor in which is automatically fixed the StateLabel of this state.
	 * It's also defined the Visitor to move player's character, based on View fixed parameters.
	 */
	private StatePlaying() {
		
		super(StateLabel.PLAYING);
		
		// Initialize Visitor for manage Characters' movement ...
		this.vcm = new VisitorCharactersMover(
				BORDER_WIDTH ,              // left X
				GAME_WIDTH + UNIT_MEGA,     // right X
				HEIGHT_DOCK + UNIT_SMALL,   // upper Y
				HEIGHT_DOCK + GAME_HEIGHT + UNIT_SMALL,  // lower Y
				UNIT_NORMAL,                 // Tessera unit
				HEIGHT_DOCK + UNIT_SMALL,   // offsetY
				UNIT_MEGA                   // offsetX
				);
		
		// ... and set it into the Npc Mediator
		npcMediator.setVisitor(vcm);
		
	}
	/**
	 * Singleton's getInstance method that also update current ThreadGame reference.
	 * Its instance is held by the super type.
	 * @param actualTG current ThreadGame
	 * @return this instance
	 */
	public static StatePlaying getInstance(ThreadGame actualTG) {
		if(playingInstance==null) playingInstance = new StatePlaying();
		playingInstance.setActualTG(actualTG);
		return playingInstance;
	}
	/**
	 * Getter of current starting whole-playingTimer. 
	 * @return this current starting whole-playingTimer
	 */
	public int getWholePlayingTimer() { return wholePlayingTimer;}
	/**
	 * Reset fields to manage the flow of time.
	 * The actual Timer Object is in the Game Model.
	 * @param wholePlayingTimer new starting whole-playingTimer of this State
	 */
	public void resetVariablesAndPlayingTimerWith(int wholePlayingTimer) {
		this.wholePlayingTimer = wholePlayingTimer;
		GameModel.getInstance().setPlayingTimer(wholePlayingTimer); 
		this.timerDivision = 1;
		this.transitionTimer = pauseTransitionContinueAndGameOver;
		transition = gameContinue = gameOver = false;
	}
	
	@Override
	public void handleOperations() {
		
		if(!transition) {
			
			PlayerCharacter pc = GameModel.getInstance().getActualPC();
			DecoratedLevel dl = (DecoratedLevel) GameModel.getInstance().getActualLevel();
			
			// ---------------- TIME SECTION --------------------------------------------
			
			// Decrease Timer of GamePlay by one every second
			if(actualTG.getTimerGeneral() == 0) {
				GameModel.getInstance().decPlayingTimer();// print timer on the dock
				if(pc.getInvincibility()>0) pc.decInvincibility();
				npcMediator.oneSecondOfActivityPassed();
				GameModel.getInstance().getCurrAccount().incPlayTime();
			}
			
			// Pause The Game
			if(actualTG.isPaused()) {
				transitionTo(StatePaused.getInstance(actualTG));
			}
			
			
			// ---------------- PC INTERACTION SECTION ------------------------

//			vcm.setPlayerDir(); // Moving characters
			pc.setDir(actualTG.getDirection());
			pc.accept(vcm);
			
			DynaManager.getInstance().updateActualXYGrid(pc);
			
			checkPerformAction(pc,dl);
			
			// ---------------- NPCs -----------------------------------------
			
			npcMediator.managementCore(pc,dl.getGrid(),vcm);
			
			// ---------------- POWER UPS ------------------------------------
			
			powerUpMediator.setPowerUps(dl.getGrid());
			
			powerUpMediator.collectsPower(pc);
			
			// ---------------- CHECK ENDING SECTION -------------------------
			
			kill(pc);
			
			checkWin(pc);
			
			checkDeath(pc); // must be the last check ending function
			
			// ---------------- ANIMATIONS SECTION --------------------------------------
			
			refreshAnimations();
			
			dl.erase();
			
		} else {
			
			powerUpMediator.reset();
			
			if(gameOver || gameContinue) {
				animationsDeathAndTransitionToContinue();
			} else if(gameWin) {
				animationsWinAndTransition();
			}

		}
	
	}
	/**
	 * Perform the bomb release if the corresponding input has been given
	 * @param pc current PlayerCharacter
	 * @param dl current LevelAbstract
	 */
	private void checkPerformAction(
			PlayerCharacter pc,
			LevelAbstract dl
			) {
		if(pc.hasRemoteBomb() && actualTG.isTriggerAction()) {
			pc.triggerFirstRemoteBomb();
		}
		if(actualTG.isPerformedAction()) {
			CartesianCoordinate cPC = pc.getSquareCoords();
			if(
					pc.canIReleaseAnotherBomb()
					&&
					dl.canYouReleaseHere(cPC)
					) {
				
				Bomb bomb = (Bomb)pc.release(cPC);
				if(bomb instanceof TimerExplosion) {
					pc.addRemoteBomb((TimerExplosion)bomb);
					DecoratedBombRemote dbr = new DecoratedBombRemote(bomb);
					dl.bundling(dbr, cPC);
				} else {
					DecoratedBomb decoratedBomb = new DecoratedBomb(bomb);
					dl.bundling(decoratedBomb, cPC);
				}

			}
		}
	}
	/**
	 * Perform the bomb release without input
	 * @param pc current PlayerCharacter
	 * @param dl current LevelAbstract
	 */
	private void releaseBombWithoutLimits(
			PlayerCharacter pc,
			LevelAbstract dl
			) {
		CartesianCoordinate c = pc.getSquareCoords();
		DecoratedBomb decoratedBomb = 
				new DecoratedBomb((Bomb) pc.getReleaseBehavior().release(c));
		dl.bundling(decoratedBomb, c);
	}
	/**
	 * Check if the player is killed
	 * @param pc current PlayerCharacter
	 */
	private void kill(PlayerCharacter pc) {

		if(
			(GameModel.getInstance() // PC dies if its square is lethal ...
				.getActualLevel().getGrid()
						[pc.getYGrid()]
						[pc.getXGrid()])
										.isLethal() 
										
				|| GameModel.getInstance().getPlayingTimer()<0 // ... or time is over ...
										
			) {
			
				death = true;
				pc.decLives(); // remove a life
				
		} else if (npcMediator.checkCollisions(pc)) { // ... or if it collides against (at least) an enemy
			death = true;
		}
	
	}
	/**
	 * Check if the player won
	 * @param pc current PlayerCharacter
	 */
	private void checkWin(PlayerCharacter pc) {
		
		LevelAbstract level = GameModel.getInstance().getActualLevel();
		if((
				( // PC wins if there're no more enemies and its square is an exit.
				(level.getGrid()[pc.getYGrid()][pc.getXGrid()]).isExit() 
							&& npcMediator.nobodyLeft())
				|| (level.isBossDefeated() && checkGravediggersCompleted())
			
			) || actualTG.isVictory()) {
			transition = true;
			gameWin = true;
			transitionTimer = pauseTransitionWin;
		}
	}
	/**
	 * Check whether the player who is being killed actually dies
	 * @param pc current PlayerCharacter
	 */
	private void checkDeath(PlayerCharacter pc) {
		
		if(pc.getInvincibility()<1) {
			
			if(death) {
				GameModel.getInstance().getCurrAccount().incBrokenHearts();
				if(pc.getHero()==Heroes.MISS_DINAH_MIGHT) {
					releaseBombWithoutLimits(pc,GameModel.getInstance().getActualLevel());
				}
				if(pc.getLives()>=0) {
					pc.setInvincibility(3); 
					if(GameModel.getInstance().getPlayingTimer()<0) {
						DynaSound.playEffect(11); // timeOut
						timerDivision++;
						GameModel.getInstance().setPlayingTimer(
								wholePlayingTimer / timerDivision
								);
					}
					death = false;
					actualTG.setNeverLose(false);
				}
			}
			
			if(death || pc.getAttempts()<0) {
				death = false;
				actualTG.setGameWin(false);
				transition = true; // do Death Animation
				transitionTimer = pauseTransitionContinueAndGameOver;
				if(pc.getAttempts()>0) {
					gameContinue = true;
				} else {
					gameOver = true;
				}
				
			}
		}
		
	}
	/**
	 * Change the Decorators' sprites to their next 
	 * every REFRESH_STANDARD frame 
	 * to generate the effect of motion pictures 
	 * and realize animations.
	 * IMPORTANT: This method reset the TG's clockNRoll to zero
	 * when the refresh happens.
	 */
	private void refreshAnimations() {
		if(actualTG.getClockNRoll()>=REFRESH_STANDARD) {
			if(
					KeyListenerGame.getInstance().getDir()==Dir.NONE 
					
										|| 
					
					! KeyListenerGame.getInstance().containsDir()
					
					
					) {
				
				GameModel.getInstance().transmit(Animations.PC_RESET);
			} else {
				GameModel.getInstance().transmit(Animations.PC_INC);
			}
			
			GameModel.getInstance().transmit(Animations.GRID_INC);
			
			GameModel.getInstance().transmit(Animations.POWER_UP_INC);
			
			GameModel.getInstance().transmit(Animations.NPC_INC);
			
			//GameModel.getInstance().erase();
			
			actualTG.setClockNRoll(0);
		}
	}
	/**
	 * Realize death animation and change state to Continue or GameOver
	 */
	private void animationsDeathAndTransitionToContinue() {
		if(actualTG.getClockNRoll()>=REFRESH_STANDARD) {

			GameModel.getInstance().transmit(Animations.PC_DEATH);

			// good collateral effect to see the short looped recursion of the bomb exploding
			GameModel.getInstance().transmit(Animations.GRID_INC);
			
			if(transitionTimer<1) {
				transition = false;
				if(gameContinue) {
					GameModel.getInstance().getCurrAccount().incAttemptsTotal();
					transitionTo(StateContinue.getInstance(actualTG));
				} else if(gameOver) {
					transitionTo(StateGameOver.getInstance(actualTG));
				}
				gameWin = gameContinue = gameOver = false;
			} else {
				transitionTimer--;
			}
			
			actualTG.setClockNRoll(0);

		}
	}
	/**
	 * Realize Win animation and change state to WinLevel or WinStage
	 */
	private void animationsWinAndTransition() {
		
		if(actualTG.getClockNRoll()>=REFRESH_STANDARD) {
	
			GameModel.getInstance().transmit(Animations.PC_WIN);
			DynaSound.playEffect(12);
			
			if(transitionTimer<1) {
				transition = false;
				if (DynaManager.getInstance().hasNextLevelSameStage()) {
					transitionTo(StateWinLevel.getInstance(actualTG));
				} else {
					transitionTo(StateWinStage.getInstance(actualTG));
				}
				gameWin = gameContinue = gameOver = false;
			} else {
				transitionTimer--;
			}
	
			actualTG.setClockNRoll(0);
		}	
	}
	/**
	 * Check if there are gravediggers to complete for waiting purposes
	 * before changing current state
	 * @return true if there's no more gravediggers to wait for
	 */
	private boolean checkGravediggersCompleted() {
		DynaSlaveCard  dsc = DynaManager.getInstance().getFrame().getMaster().getActivePanel();
		if(dsc instanceof DynaSlaveGame) {
			DynaSlaveGame dsg = (DynaSlaveGame) dsc;
			if(dsg.isGravediggersEmpty()) {
				return true;
			}
		}
		return false;
	}

	
}
