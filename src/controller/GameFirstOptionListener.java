package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.GameModel;
import model.PlayerCharacter;
import model.StateLabel;
/**
 * Concrete Command (Command Pattern) provided by Java Swing Architecture.
 * It implements the performed action when the DynaSlaveGame's first button is clicked.
 * It performs the contextual operation relative to the state of game.
 */
public class GameFirstOptionListener extends GameOptionListener implements ActionListener {
	/**
	 * Singleton instance
	 */
	private static GameFirstOptionListener instance;
	/**
	 * Private Constructor
	 */
	private GameFirstOptionListener() {}
	/**
	 * Singleton getInstance method
	 * @return this singleton instance
	 */
	public static GameFirstOptionListener getInstance() {
		if(instance==null) instance = new GameFirstOptionListener();
		return instance;
	}
	/**
	 * Command execute method
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		performance();
	}
	@Override
	protected final void performance() {
		StateLabel label = GameModel.getInstance().getStateLabel();
		switch(label) {
		
			case STARTER -> {}
			
			case PAUSED -> {
				KeyListenerGame.getInstance().setPause(true);
			}
			
			case PLAYING -> {}
			
			case CONTINUE -> {
				
				PlayerCharacter pc = GameModel.getInstance().getActualPC();
				
				pc.setAttempts(pc.getAttempts()-1);
				pc.setLives(1);
				pc.resetTemporaryStatus(); //reset bombs of the pc
				
				DynaManager.getInstance().repositioningActualPC();
				
				// restart level of game
				DynaManager.getInstance().resetActualLevel();
			
				// reset the current State of the TG
				DynaManager.getInstance().getTG().getCurrentState().
					transitionTo(StateStarter.getInstance(
							DynaManager.getInstance().getTG()));
			}
			
			case GAMEOVER -> {
				DynaManager.getInstance().getTG().setGameOver(true);
			} 
			
			case WINLEVEL -> {
				
				// reset the level just played for the future game session
				DynaManager.getInstance().resetActualLevel();
				

				PlayerCharacter pc = GameModel.getInstance().getActualPC();
				
				pc.resetTemporaryStatus(); //reset bombs of the pc
				
				DynaManager.getInstance().repositioningActualPC();
				
				// set next level on GameModel
				DynaManager.getInstance().setNextLevel();
			
				// reset the current State of the TG
				DynaManager.getInstance().getTG().getCurrentState().
					transitionTo(StateStarter.getInstance(
							DynaManager.getInstance().getTG()));
			}
			
			case WINSTAGE -> {
				
				DynaManager.getInstance().getTG().setGameWin(true);
				DynaManager.getInstance().resetActualPC();
				DynaManager.getInstance().resetActualLevel();
				GameModel.getInstance().transmitRestart(true);
			}
			
			case LOADING -> {}
			
			default -> {}
		}
	}

}
