package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.GameModel;
import model.StateLabel;
/**
 * Concrete Command (Command Pattern) provided by Java Swing Architecture.
 * It implements the performed action when the DynaSlaveGame's second button is clicked.
 * It performs the contextual operation relative to the state of game.
 */
public class GameSecondOptionListener extends GameOptionListener implements ActionListener {
	/**
	 * Singleton instance
	 */
	private static GameSecondOptionListener instance;
	/**
	 * Private Constructor
	 */
	private GameSecondOptionListener() {}
	/**
	 * Singleton getInstance method
	 * @return this singleton instance
	 */
	public static GameSecondOptionListener getInstance() {
		if(instance==null) instance = new GameSecondOptionListener();
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
				ThreadGame tg = DynaManager.getInstance().getTG();
				if(tg.isNeverLose()){
					escape();
					exitFunc();
				} else {
					tg.getCurrentState().transitionTo(
							StateGameOver.getInstance(tg)
							);
				}

			}
			
			case PLAYING -> {}
			
			case CONTINUE -> {
				ThreadGame tg = DynaManager.getInstance().getTG();
				tg.getCurrentState().transitionTo(
						StateGameOver.getInstance(tg)
						);
			}
			
			case GAMEOVER -> {
				gameOver();
				exitFunc();
			} 
			
			case WINLEVEL -> {}
			
			case WINSTAGE -> {}
			
			case LOADING -> {}
			
			default -> {}
		}
	}
	/**
	 * Operations to complete before 
	 * restart the app from Thread Game to Thread Menu.
	 */
	private void exitFunc() {
		
		DynaManager.getInstance().resetActualPC();
		DynaManager.getInstance().resetActualLevel();
		GameModel.getInstance().transmitRestart(true);

	}
	/**
	 * Set ThreadGame's Escape to True.
	 * It causes death of the current Thread Game.
	 */
	private void escape() {

		DynaManager.getInstance().getTG().setEscape(true);
	}
	/**
	 * Set ThreadGame's GameOver to True.
	 * It causes death of the current Thread Game.
	 */
	private void gameOver() {
		
		DynaManager.getInstance().getTG().setGameOver(true);
	}

}
