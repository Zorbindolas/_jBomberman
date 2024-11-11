package view;

import java.awt.Graphics2D;

import controller.DynaManager;
import controller.KeyListenerGame;
import controller.KeyListenerMenu;
import model.CartesianCoordinate;
import model.StateLabel;
import model.Theater;
/**
 * The Card in which User can choose the Stage to play.
 */
public class DynaSlaveStage extends DynaSlaveCard 
				implements RelocateSpecialComponents {
	/**
	 * Serialized key for Serialization
	 */
	private static final long serialVersionUID = -7188439757343397840L;
	/**
	 * Panel to show selected stage or change it
	 */
	private DynaSlaveStageNested switchPanel;
	/**
	 * Constructor.
	 * This Card allows User to choose the Stage to play.
	 * @param master CardLayout Master reference
	 */
	public DynaSlaveStage(DynaMasterCard master) {
		super(
				master, 
				3,
				new String[] {
						"CHOOSE YOUR STAGE",
						"SELECT NEXT STAGE",
						"BACK"},
				new int[] {7,8,9},
				"bgorange"
				);
		// selectionPanel
		switchPanel = new DynaSlaveStageNested(this);
		switchPanel.setBounds(0, 0, 10, 10);
		this.add(switchPanel);
		
		relocateSpecialComponents();
	}
	
	@Override
	public void relocateComponents() {
		super.relocateComponents();
		if(switchPanel!=null) relocateSpecialComponents();
	}
	
	@Override
	public void relocateSpecialComponents() {
		
		int spacer = 30;
		CartesianCoordinate newOrigin = new CartesianCoordinate(spacer,spacer);
		switchPanel.setOrigin(newOrigin);
		switchPanel.setWidth(meterW*10-spacer*2);
		switchPanel.setHeight(meterH*6);

		switchPanel.setBounds(
				switchPanel.getOrigin().getX(),
				switchPanel.getOrigin().getY(),
				switchPanel.getWidth(),
				switchPanel.getHeight()
				);
	}

	@Override
	public void optionSwitcher(int pointer) {
		switch(pointer) {
		case 0 -> {

			master.getFrame().setActualStage(switchPanel.getSelectedStage());
			master.getFrame().setActualScene(Theater.Scene.SCENE_1);

			startThreadGame();
			
		}
		
		case 1 -> {
			switchPanel.incPointer();
		}
		
		case 2 -> {
			escFunction();
		}
		
		default -> {}
		}
	}

	@Override
	public void escFunction() {
		super.changePanel("player");
	}
	
	@Override
	protected Graphics2D drawStuff(Graphics2D g2){
		
		return g2;
	}

	/**
	 * Trigger the start of a new ThreadGame and set View GameState (in DynaSlaveGame) on loading state.
	 */
	private void startThreadGame() {
		
		// Set Master with the correct Keyistener
		master.removeKeyListener(
				KeyListenerMenu.getInstance()
				);
		
		master.addKeyListener(
				KeyListenerGame.getInstance()
				);
		
		// set DynaSlaveGame's state to LOADING
		((DynaSlaveGame)DynaManager.getInstance().getFrame()
	       		.getMaster().getSpecificPanel("game")).setCurrentState(
	       				StateLabel.LOADING);
		
		// Setting for painting Master's active panel
		master.setDrawDefaultBackground(true);
		master.setDrawPointer(false);
		master.setDrawOptions(true);
		
		// Show the Game Panel
		changePanel("game");
		
		// Stop ThreadMenu and start a new ThreadGame
		master.getFrame().setTimeToPlay(true);
	}
	
}
