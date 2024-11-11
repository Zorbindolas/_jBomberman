package controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.Dir;
import model.GameCharacter;
import model.GameModel;
import model.Heroes;
import model.LevelAbstract;
import model.MyGridFormat;
import model.Theater;
import model.Theater.Scene;
import model.Theater.Stage;
import view.DecoratedPlayerCharacter;
import view.DynaFrame;
import view.MyJBombermanFormat;


/**
 * The DynaManager has the main role of holding the objects that represent the levels, 
 * managing their loading and selection necessary for their effective use.
 * For this reasons it communicates with the Game Model.
 * Additionally, DynaManager holds the reference to the current game thread instance
 * and that to the View Frame.
 * It has some methods for managing player's starter position.
 * It encapsulates serialization methods from Game Model (used in the view).
 * 
 */
public class DynaManager implements MyGridFormat, MyJBombermanFormat{
	/**
	 * Reference to the actual View Frame 
	 */
	private static DynaFrame df = new DynaFrame();
	/**
	 * Game levels order by Stage and Scene.
	 */
	private Map<Stage,Map<Scene,LevelAbstract>> levels;
	/**
	 * Reference to the actual Thread Game
	 */
	private ThreadGame game;
	
	// synchronized singleton with inner class
	/**
	 * Private constructor for Singleton Pattern
	 */
	private DynaManager() {
		
		levels = new HashMap<>();
		
		for(Stage s : Theater.Stage.values()) {
			levels.put(s, new HashMap<>());
		}
		
	}
	/**
	 * Singleton method to return the singleton instance
	 * @return this instance
	 */
	public static DynaManager getInstance() {
		return MyWrapperManager.INSTANCE;
	}
	/**
	 * Singleton is implemented using nested class for multi-threading safety.
	 * I must avoid lazy singleton and avoid the creation of the instance in the getInstance() method.
	 */
    private static class MyWrapperManager {
        static DynaManager INSTANCE = new DynaManager();
    }

	// Getters
    /**
     * Getter of actual View Frame
     * @return actual View JFrame
     */
	public DynaFrame getFrame() {return df;}
	/**
	 * Getter of current Thread Game
	 * @return current Thread Game in loop
	 */
	public ThreadGame getTG() {return game;}
	
	// Setters
	/**
	 * Set this.levels from external input map
	 * @param levels settled levels
	 */
	public void setLevels(Map<Stage,Map<Scene,LevelAbstract>> levels) {
		this.levels = levels;
	}
	/**
	 * Regenerate the level chosen by inputs. The old one will be lost.
	 * @param stage stage of level to regenerate
	 * @param scene scene of level to regenerate
	 */
	public void setALevel(Stage stage, Scene scene) {
		Theater theater = Theater.getTheater(stage,scene);
		levels.get(stage).put(
				scene, 
				FactoryDecoratedLevel.generateDecoratedLevel(theater));
	}
	
	// Interactions with Game Model
	/**
	 * Regenerate the actual Level and save it in the Game Model.
	 */
	public void resetActualLevel() {
		Theater.Stage stage = GameModel.getInstance().getActualStageLabel();
		Theater.Scene scene = GameModel.getInstance().getActualSceneLabel();
		Theater theater = Theater.getTheater(stage,scene);
		LevelAbstract la = FactoryDecoratedLevel.generateDecoratedLevel(theater);
		levels.get(stage).put(
				scene, 
				la);
		GameModel.getInstance().setActualLevel(la, stage, scene);
	}
	/**
	 * Regenerate the PlayerCharacter chosen in the view.
	 * Then, it's saved in the Game Model.
	 */
	public void resetActualPC() {
		Heroes heroLabel = this.getFrame().getSelectedPCLabel();
		DecoratedPlayerCharacter pc = FactoryDecoratedPlayerCharacter.getHero(heroLabel);
		this.setActualPC(pc);
	}
	/**
	 * Set the level extracted from this Collection with input keys
	 * and set the Game Model with it.
	 * @param stage stage of level to set
	 * @param scene scene of level to set
	 */
	public void setActualLevel(Theater.Stage stage, Theater.Scene scene) {
		Theater t = Theater.getTheater(stage,scene);
		FactoryDecoratedLevel.setLevel(levels, t);
		LevelAbstract la = (LevelAbstract) levels.get(stage).get(scene);
		GameModel.getInstance().setActualLevel(la,stage,scene);
	}
	/**
	 * Set the input player character as the actual pc in the Game Model
	 * @param pc new Game Model's actual pc
	 */
	public void setActualPC(DecoratedPlayerCharacter pc) {
		GameModel.getInstance().setActualPC(pc);
	}
	/**
	 * Check if there is another level to load from the current stage
	 * @return true if there is another next level
	 */
	public boolean hasNextLevelSameStage() {
		Theater.Stage stage = GameModel.getInstance().getActualStageLabel();
		Theater.Scene scene = GameModel.getInstance().getActualSceneLabel();
//		Theater theater = Theater.getTheater(stage,scene);
		return Theater.hasNextTheaterSameStage(stage,scene);
	}
	/**
	 * Create next level and set it as the actual one.
	 */
	public void setNextLevel() {
		// process is encapsulated in Theater enum
		Theater.Stage stage = GameModel.getInstance().getActualStageLabel();
		Theater.Scene scene = GameModel.getInstance().getActualSceneLabel();
//		Theater theater = Theater.getTheater(stage,scene);
		Theater t = Theater.nextTheaterSameStage(stage, scene);
		stage = t.getStage();
		scene = t.getScene();
		setActualLevel(stage,scene);
	}
	
	// interactions with Controller
	/**
	 * Set the current instance of Thread Game.
	 * @param game new thread game instance
	 */
	public void setThreadGame(ThreadGame game) {this.game=game;}
	/**
	 * Nullify the reference to current thread game.
	 */
	public void nullifyTG() {this.game=null;}
	
	// Manage Game Characters' panel position
	/**
	 * Reinitialize actual pc's coordinates.
	 */
	public void repositioningActualPC() {
		DecoratedPlayerCharacter pc = (DecoratedPlayerCharacter) GameModel.getInstance().getActualPC();
		pc.setXGrid(0);
		pc.setYGrid(0);
		pc.setXPanel(START_PC_X);
		pc.setYPanel(START_PC_Y);
		pc.setDir(Dir.RIGHT);
	}
	/**
	 * Update actual game character's grid coordinates
	 * @param gc game character to update
	 */
	public void updateActualXYGrid(GameCharacter gc) {
		gc.setYGrid((gc.getYPanel()+gc.getOffGravityY()-OFFSET_Y)/UNIT_NORMAL);
		gc.setXGrid((gc.getXPanel()+gc.getOffGravityX()-OFFSET_X)/UNIT_NORMAL);
	}
	
	// Import - export from GameModel
	/**
	 * Export a file representing a game save, invoking Game Model's method.
	 * @param f exported file
	 * @throws IOException exception for Input/Output files management
	 */
	public void exportSaveFile(File f) throws IOException {
		GameModel.getInstance().exportSaveFile(f);
	}
	/**
	 * Import a file representing a game save, invoking Game Model's method.
	 * @param f imported file
	 * @throws IOException exception for Input/Output files management
	 */
	public void importSavedFile(File f) throws IOException {
		GameModel.getInstance().importSavedFile(f);
	}

}
