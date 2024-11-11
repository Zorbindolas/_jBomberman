package controller;

import model.GameModel;
import model.Theater;
/**
 * Main method class
 */
public class JBomberMan {
	/**
	 * Current Thread menu static instance.
	 */
	private static ThreadMenu menu;
	/**
	 * Current Thread game static instance
	 */
	private static ThreadGame game; 
	/**
	 * This method creates a new Thread, started from Runnable class ThreadGame.
	 * The returned reference is used by ThreadMenu for join purpose.
	 * @return current thread game reference
	 */
	public static Thread runGameSession() {
		game = new ThreadGame();
		Thread gameThread = new Thread(game);
		gameThread.start();
		DynaManager.getInstance().setThreadGame(game);
		return gameThread;
	}
	/**
	 * Main method of the entire application
	 * @param args main method's arguments 
	 */
	public static void main(String[] args) {
		
		FactoryTesterAvatarUser.addTesterAvatarsToModel();
		
		menu = new ThreadMenu();
		Thread menuThread = new Thread(menu);
		menuThread.start();
	
		// Loaders data
		
		// I benefit of concurrency, but I must use a special version of singleton for dm
//		DynaManager.getInstance().setLevels(FactoryDecoratedLevel.getAllLevels());
		DynaManager.getInstance().setALevel(Theater.Stage.STAGE_1,Theater.Scene.SCENE_1);
		DynaManager.getInstance().setALevel(Theater.Stage.STAGE_2,Theater.Scene.SCENE_1);
		DynaManager.getInstance().setALevel(Theater.Stage.STAGE_3,Theater.Scene.SCENE_1);

		GameModel.getInstance().setReady(true);

	}

}
