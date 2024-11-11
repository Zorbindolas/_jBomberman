package view;

import java.awt.Graphics2D;

import model.GameModel;
import model.StateLabel;
/**
 * It's the state of the view that introduces the player to the level he is about to begin.
 */
public class StatePanelStarter extends StatePanel {
	/**
	 * Constructor.
	 * It's the state of the view that introduces the player to the level he is about to begin.
	 * @param dsg DynaSlaveGame reference
	 */
	public StatePanelStarter(DynaSlaveGame dsg) {
		super(
				dsg, 
				StateLabel.STARTER, 
				new String[] {"LEVEL ", "STAGE ", ""}
				);
	}

	@Override
	public void handleUpdateOptionsStrings() {
		
		dsg.getMaster().setDrawDefaultBackground(false);
		dsg.getMaster().setDrawPointer(false);
		dsg.getMaster().setDrawOptions(true);
			
		DecoratedLevel level = (DecoratedLevel) GameModel.getInstance().getActualLevel();
		
		String stringStage = level.getStage().toString();
		String stringScene = level.getScene().toString();
		
		dsg.changeTextOfAnOptionAtRuntime(0, "STAGE " + stringStage);
		dsg.changeTextOfAnOptionAtRuntime(1, "SCENE " + stringScene);
		
		DecoratedLevel dl = (DecoratedLevel) GameModel.getInstance().getActualLevel();
		dsg.changeTextOfAnOptionAtRuntime(2, dl.getName());
		
	}

	@Override
	public Graphics2D handleDrawStuff(Graphics2D bufferedGraphics) {
		dsg.paintItBlack(bufferedGraphics);
        dsg.renderDock(bufferedGraphics);
        
		return bufferedGraphics;
	}

}
