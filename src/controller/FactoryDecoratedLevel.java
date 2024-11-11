package controller;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import model.CartesianCoordinate;
import model.Hound;
import model.InconsistentCoordinateException;
import model.LevelAbstract;
import model.LevelConcrete;
import model.MyGridFormat;
import model.NonPlayableCharacter;
import model.Obstacle;
import model.Prop;
import model.TesseraAbstract;
import model.TesseraConcrete;
import model.Theater;
import model.Theater.Scene;
import model.Theater.Stage;
import model.Wall;

import view.FootageFuncs;
import view.ArchesCity;
import view.ArchesClassical;
import view.ArchesJungle;
import view.DecoratedLevel;
import view.DecoratedObstacle;
import view.DecoratedTessera;
import view.DecoratedWall;
import view.MyJBombermanFormat;
import view.EnjoyPowerUpMediator;

/**
 * This factory contains all the information, references and methods needed to create any level in this game.
 */
public final class FactoryDecoratedLevel implements MyGridFormat, MyJBombermanFormat{
	/**
	 * Random static utility
	 */
	private static Random r = new Random();
	/**
	 * name path to load the exit tile
	 */
	private static String[] exitNode = new String[] {"/evergreen/exit"};
	
	// Stage 1 : City
	/**
	 * root name path for city level plate
	 */
	private static String fatherPlateS1 = "/platesCity/";
	/**
	 * name paths for city level plates
	 */
	private static String[] sonsPlateS1 = new String[] {fatherPlateS1+"plate",fatherPlateS1+"plateGreen",fatherPlateS1+"plateOrange",fatherPlateS1+"plateRed"};
	/**
	 * name paths for city level shadowed plates
	 */
	private static String[] sonsAltPlateS1 = new String[] {fatherPlateS1+"plateShadow",fatherPlateS1+"plateShadowGreen",fatherPlateS1+"plateShadowOrange",fatherPlateS1+"plateShadowRed"};
	/**
	 * root name path for city level wall
	 */
	private static String fatherWallS1 = "/wallsCity/";
	/**
	 * name paths for city level wall
	 */
	private static String[] sonsWallS1 = new String[] {fatherWallS1+"wall0",fatherWallS1+"wall1",fatherWallS1+"wall2",fatherWallS1+"wall3",fatherWallS1+"wall4"};
	/**
	 * root name path for city level obstacles
	 */
	private static String fatherObS1 = "/obstaclesCity/";
	/**
	 * name paths for city level obstacles
	 */
	private static String[] sonsObS1 = new String[] {fatherObS1+"ob0",fatherObS1+"ob1",fatherObS1+"ob2",fatherObS1+"ob3"};
	/**
	 * name paths for city level destroyed obstacles
	 */
	private static String[] sonsAltObS1 = new String[] {fatherObS1+"obDestroyed0",fatherObS1+"obDestroyed1",fatherObS1+"obDestroyed2",fatherObS1+"obDestroyed3",fatherObS1+"obDestroyed4",fatherObS1+"obDestroyed5"};
	/**
	 * city level 1 name
	 */
	private static String nameS1S1 = "ALIEN HITCHHIKERS THUMBS";
	/**
	 * city level 2 name
	 */
	private static String nameS1S2 = "TAXI NUMBER 42";
	/**
	 * city level 3 name
	 */
	private static String nameS1S3 = "SPACE CAROUSEL";
	/**
	 * city level 4 name
	 */
	private static String nameS1S4 = "ALIEN GARBAGE COLLECTORS";
	/**
	 * city level 5 name
	 */
	private static String nameS1S5 = "UFO JELLYFISH RIDERS";
	/**
	 * city level 6 name
	 */
	private static String nameS1S6 = "FLU FROM ANOTHER PLANET";
	/**
	 * city level 7 name
	 */
	private static String nameS1S7 = "CITY LANDING";
	/**
	 * city level 8 name
	 */
	private static String nameS1S8 = "MR UFO BRAIN";

	// Stage 2 : Jungle
	/**
	 * root name path for jungle level plate
	 */
	private static String fatherPlateS2 = "/platesJungle/";
	/**
	 * name paths for jungle level plates
	 */
	private static String[] sonsPlateS2 = new String[] {fatherPlateS2+"plate"};
	/**
	 * name paths for jungle level shadowed plates
	 */
	private static String[] sonsAltPlateS2 = new String[] {fatherPlateS2+"plateShadow"};
	/**
	 * root name path for jungle level wall
	 */
	private static String fatherWallS2 = "/wallsJungle/";
	/**
	 * name paths for jungle level walls
	 */
	private static String[] sonsWallS2 = new String[] {fatherWallS2+"wall0"};
	/**
	 * root name path for jungle level obstacle
	 */
	private static String fatherObS2 = "/obstaclesJungle/";
	/**
	 * name paths for jungle level obstacles
	 */
	private static String[] sonsObS2 = new String[] {fatherObS2+"ob0",fatherObS2+"ob1",fatherObS2+"ob2",fatherObS2+"ob3",fatherObS2+"ob4",fatherObS2+"ob5",fatherObS2+"ob6",fatherObS2+"ob7",fatherObS2+"ob8",fatherObS2+"ob9",fatherObS2+"ob10",fatherObS2+"ob11"};
	/**
	 * name paths for jungle level destroyed obstacles
	 */
	private static String[] sonsAltObS2 = new String[] {fatherObS2+"obDestroyed0",fatherObS2+"obDestroyed1",fatherObS2+"obDestroyed2",fatherObS2+"obDestroyed3",fatherObS2+"obDestroyed4",fatherObS2+"obDestroyed5"};
	/**
	 * jungle level 1 name
	 */
	private static String nameS2S1 = "WELCOME TO THE JUNGLE";
	/**
	 * jungle level 2 name
	 */
	private static String nameS2S2 = "BOMBPOCALYPSE";
	/**
	 * jungle level 3 name
	 */
	private static String nameS2S3 = "NAPALM DANCES";
	/**
	 * jungle level 4 name
	 */
	private static String nameS2S4 = "HUNGRY BOMB SQUAD";
	/**
	 * jungle level 5 name
	 */
	private static String nameS2S5 = "YOU ARE THE TARGET";
	/**
	 * jungle level 6 name
	 */
	private static String nameS2S6 = "HEAVY ARTILLERY";
	/**
	 * jungle level 7 name
	 */
	private static String nameS2S7 = "ENEMY BASE";
	/**
	 * jungle level 8 name
	 */
	private static String nameS2S8 = "DESTROY MR PROTOTYPE";
	
	// Stage 3 : Classical
	/**
	 * root name path for classical level plate
	 */
	private static String fatherPlateS3 = "/platesClassical/";
	/**
	 * name paths for classical level plates
	 */
	private static String[] sonsPlateS3 = new String[] {fatherPlateS3+"plate"};
	/**
	 * name path for classical shadowed plate
	 */
	private static String[] sonsAltPlateS3 = new String[] {fatherPlateS3+"plateShadow"};
	/**
	 * root name path for classical level wall
	 */
	private static String fatherWallS3 = "/wallsClassical/";
	/**
	 * name paths for classical level walls
	 */
	private static String[] sonsWallS3 = new String[] {fatherWallS3+"wall0"};
	/**
	 * root name path for classical level obstacle
	 */
	private static String fatherObS3 = "/obstaclesClassical/";
	/**
	 * name paths for classical level obstacles
	 */
	private static String[] sonsObS3 = new String[] {fatherObS3+"ob0"};
	/**
	 * name paths for classical level shadowed obstacles
	 */
	private static String[] sonsAltObS3 = new String[] {fatherObS3+"obDestroyed0",fatherObS3+"obDestroyed1",fatherObS3+"obDestroyed2",fatherObS3+"obDestroyed3",fatherObS3+"obDestroyed4",fatherObS3+"obDestroyed5"};
	/**
	 * classical level 1 name
	 */
	private static String nameS3S1 = "INFLATABLE DREAMS";
	/**
	 * classical level 2 name
	 */
	private static String nameS3S2 = "REMAINS OF A PARTY";
	/**
	 * classical level 3 name
	 */
	private static String nameS3S3 = "THAT IS LIFE";
	/**
	 * classical level 4 name
	 */
	private static String nameS3S4 = "CLEAR THE TABLE";
	/**
	 * classical level 5 name
	 */
	private static String nameS3S5 = "RAIN DROPS";
	/**
	 * classical level 6 name
	 */
	private static String nameS3S6 = "PUT ON A HAPPY FACE";
	/**
	 * classical level 7 name
	 */
	private static String nameS3S7 = "WHAT ABOUT ANOTHER JOKEq";
	/**
	 * classical level 8 name
	 */
	private static String nameS3S8 = "DO NOT CRY MR CLOWN";

	/**
	 * Create all levels
	 * @return map with all levels
	 */
	public static Map<Stage,Map<Scene,LevelAbstract>> getAllLevels(){
		
		Map<Stage,Map<Scene,LevelAbstract>> macroMap = new HashMap<>();
		
		for(Stage stage : Theater.Stage.values()) {
			Map<Scene,LevelAbstract> microMap = new HashMap<>();
			
			for(Scene scene : Theater.Scene.values()) {
				microMap.put(scene, generateDecoratedLevel(Theater.getTheater(stage, scene)));
			}
			
			macroMap.put(stage, microMap);
		}
		
		return macroMap;
	}
	/**
	 * Insert a new level in the input map
	 * @param map map in which insert the new level
	 * @param t theater of the level to generate
	 */
	public static void setLevel(Map<Stage,Map<Scene,LevelAbstract>> map, Theater t){
		
		map.get(t.getStage()).put(t.getScene(),generateDecoratedLevel(t));
			
	}
	/**
	 * Create level identified by a theater
	 * @param theater theater identifier of the level to create
	 * @return level created
	 */
	public static DecoratedLevel generateDecoratedLevel(Theater theater) {
		
		switch(theater) {
		// City Stage
		case LEVEL_1_1 -> {return levelStageCity(nameS1S1, Theater.LEVEL_1_1, r.nextInt(32,36),0,5);}
		case LEVEL_1_2 -> {return levelStageCity(nameS1S2, Theater.LEVEL_1_2, r.nextInt(30,40),5,10);}
		case LEVEL_1_3 -> {return levelStageCity(nameS1S3, Theater.LEVEL_1_3, r.nextInt(32,36),10,15);}
		case LEVEL_1_4 -> {return levelStageCity(nameS1S4, Theater.LEVEL_1_4, r.nextInt(32,36),15,21);}
		case LEVEL_1_5 -> {return levelStageCity(nameS1S5, Theater.LEVEL_1_5, r.nextInt(35,40),10,15);}
		case LEVEL_1_6 -> {return levelStageCity(nameS1S6, Theater.LEVEL_1_6, r.nextInt(32,36),0,21);}
		case LEVEL_1_7 -> {return levelStageCity(nameS1S7, Theater.LEVEL_1_7, r.nextInt(5,10),0,11);}
		case LEVEL_1_8 -> {return levelBossStageCity(nameS1S8, Theater.LEVEL_1_8);} 
		// Jungle Stage
		case LEVEL_2_1 -> {return levelStageJungle(nameS2S1, Theater.LEVEL_2_1, r.nextInt(32,36),0,5);}
		case LEVEL_2_2 -> {return levelStageJungle(nameS2S2, Theater.LEVEL_2_2, r.nextInt(30,40),5,10);}
		case LEVEL_2_3 -> {return levelStageJungle(nameS2S3, Theater.LEVEL_2_3, r.nextInt(32,36),10,15);}
		case LEVEL_2_4 -> {return levelStageJungle(nameS2S4, Theater.LEVEL_2_4, r.nextInt(32,36),15,21);}
		case LEVEL_2_5 -> {return levelStageJungle(nameS2S5, Theater.LEVEL_2_5, r.nextInt(35,40),10,15);}
		case LEVEL_2_6 -> {return levelStageJungle(nameS2S6, Theater.LEVEL_2_6, r.nextInt(32,36),0,21);}
		case LEVEL_2_7 -> {return levelStageJungle(nameS2S7, Theater.LEVEL_2_7, r.nextInt(5,10),0,11);}
		case LEVEL_2_8 -> {return levelBossStageJungle(nameS2S8, Theater.LEVEL_2_8);}
		// Classical Stage
		case LEVEL_3_1 -> {return levelStageClassical(nameS3S1, Theater.LEVEL_3_1, r.nextInt(32,36),0,5);}
		case LEVEL_3_2 -> {return levelStageClassical(nameS3S2, Theater.LEVEL_3_2, r.nextInt(30,40),5,10);}
		case LEVEL_3_3 -> {return levelStageClassical(nameS3S3, Theater.LEVEL_3_3, r.nextInt(32,36),10,15);}
		case LEVEL_3_4 -> {return levelStageClassical(nameS3S4, Theater.LEVEL_3_4, r.nextInt(32,36),15,21);}
		case LEVEL_3_5 -> {return levelStageClassical(nameS3S5, Theater.LEVEL_3_5, r.nextInt(35,40),10,15);}
		case LEVEL_3_6 -> {return levelStageClassical(nameS3S6, Theater.LEVEL_3_6, r.nextInt(32,36),0,21);}
		case LEVEL_3_7 -> {return levelStageClassical(nameS3S7, Theater.LEVEL_3_7, r.nextInt(12,21),0,11);}
		case LEVEL_3_8 -> {return levelBossStageClassical(nameS3S8, Theater.LEVEL_3_8);} 
		default -> {return null;}
		}
		
	}
	
	// All the private methods
	
	// Partial Factory methods
	/**
	 * Set the grid with decorated tesserae
	 * @param sons plate paths
	 * @param sonsAlternative shadowed plate paths
	 * @param exit exit path
	 * @return matrix with decorated tesserae
	 */
	private static DecoratedTessera[][] gridOfVirginTesserae(
			String[] sons, String[] sonsAlternative, String[] exit) {
		
		DecoratedTessera[][] decoratedTesseraeGrid = new DecoratedTessera[ROWS][COLUMNS];
		
		for(int y=0; y<ROWS; y++)
			for(int x=0; x<COLUMNS; x++) {
				TesseraAbstract tessera = new TesseraConcrete(x,y,false);
				if(y==0) tessera.setBlackened(true);
				decoratedTesseraeGrid[y][x] = new DecoratedTessera(tessera, sons, sonsAlternative, exit);				
			}
		
		return decoratedTesseraeGrid;	
	}
		
	/**
	 * With grid given, insert decorated walls into it.
	 * @param sons wall paths
	 * @param grid grid in which insert walls
	 * @return grid with inserted walls
	 */
	private static DecoratedTessera[][] gridWithRegularDecoratedWalls(
			String[] sons, DecoratedTessera[][] grid) {
		
		for(int y=0; y<ROWS; y++) {
			for(int x=0; x<COLUMNS; x++) {
				if(y%2==1 && x%2==1) {
					DecoratedWall dWall = new DecoratedWall(new Wall(),sons);
					DecoratedTessera dt = (grid[y][x]);
					dt.setBundle(dWall);
				}
			}
		}
		
		return grid;
	}
	/**
	 * Adjust the shading of tesserae that have a wall above them
	 * @param levelConcrete level of the grid to adjust
	 */
	private static void gridWithAdjustedShadowedPlates(LevelConcrete levelConcrete) {
		
		TesseraAbstract[][] grid = levelConcrete.getGrid();
		
		for(int y=1; y<ROWS; y++) {
			for(int x=0; x<COLUMNS; x++) {
				if( 
						(grid[y-1][x]).getBundle() instanceof DecoratedWall ) {
					
					(grid[y][x]).setBlackened(true);
					((FootageFuncs)(grid[y][x])).resetFootage();
					((FootageFuncs)(grid[y][x])).incFootage();
				}
			}
		}
		
		levelConcrete.setGrid(grid);
	}
	
	// wrap obstacles and set the exit
	/**
	 * Randomly inserts obstacles into the input grid
	 * @param sonsOb normal images paths
	 * @param sonsAltOb destroyed images paths 
	 * @param numberOfObstacles number of obstacles to insert
	 * @param grid input grid 
	 * @return same grid from input with decorated obstacles
	 */
	private static DecoratedTessera[][] gridWithDecoratedObstacles(
			String[] sonsOb, String[] sonsAltOb, int numberOfObstacles, DecoratedTessera[][] grid) {
		
		while(numberOfObstacles >= 0) {
			
			int rX = r.nextInt(0,COLUMNS);
			int rY = r.nextInt(0,ROWS);
			
			if(
					! (rX % 2 != 0 && rY % 2 != 0)
					
					&& 
					
					(!(rX == 0 && rY == 0) && !(rX == 0 && rY == 1) 
							&& !(rX == 1 && rY == 0))
					
					&&
					
					(grid[rY][rX]).getBundle()==null
					
					) {
				
				
				if(numberOfObstacles==0) grid[rY][rX].setExit(true); // set this plate as exit
				
				
				DecoratedObstacle db = new DecoratedObstacle(
						new Obstacle(rX,rY,EnjoyPowerUpMediator.getInstance()), 
						sonsOb, sonsAltOb);
				
				(grid[rY][rX]).setBundle(db);
				

				numberOfObstacles--;
				
			}
		}
		
		return grid;
	}
	
	/**
	 * Set xGrid yGrid xPanel yPanel of all Tessera and their bundles
	 * @param levelConcrete Level of the grid to set
	 */
	private static void setXYs(LevelConcrete levelConcrete) {
		for(int y = 0; y<ROWS; y++) {
			for(int x=0; x<COLUMNS; x++) {
				(levelConcrete.getGrid()[y][x]).setYGrid(y);
				(levelConcrete.getGrid()[y][x]).setXGrid(x);
				int xPanel = OFFSET_X + x*UNIT_NORMAL;
				int yPanel = OFFSET_Y + y*UNIT_NORMAL;
				
				(levelConcrete.getGrid()[y][x]).setYPanel(yPanel);
				(levelConcrete.getGrid()[y][x]).setXPanel(xPanel);

				if((levelConcrete.getGrid()[y][x]).getBundle()!=null) {
					((levelConcrete.getGrid()[y][x]).getBundle()).setYGrid(y);
					((levelConcrete.getGrid()[y][x]).getBundle()).setXGrid(x);
					((levelConcrete.getGrid()[y][x]).getBundle()).setYPanel(yPanel);
					((levelConcrete.getGrid()[y][x]).getBundle()).setXPanel(xPanel);
				}
			}
		}
	}
	/**
	 * Generate an undecorated level with its npcs set given by the npc factory
	 * @param name level's name
	 * @param theater level's theater
	 * @param grid level's grid
	 * @return the created level
	 */
	private static LevelConcrete generateLevel( // and define set of enemies
			String name, Theater theater, 
			DecoratedTessera[][] grid) {
		
		LevelConcrete levelConcrete = null;

		Set<NonPlayableCharacter> npcs = FactoryDecoratedNPC.getNPCSet(theater);
		
		try {
			
			levelConcrete = new LevelConcrete.LevelBuilder(name, theater)
						.setGrid(grid, true)
						.setNpcs(npcs)
						.build();
			
		} catch (InconsistentCoordinateException e) {}
			
		setXYs(levelConcrete);
		
		return levelConcrete;
	}
	/**
	 * Create a boss-fight level (no exit, no obstacles).
	 * @param name level's name
	 * @param theater level's theater
	 * @param fatherPlate root plate path
	 * @param sonsPlate normal plates' paths
	 * @param sonsAltPlate shadowed plates' paths
	 * @param exit exit's path
	 * @param sonsWalls walls' paths
	 * @return the created level
	 */
	private static LevelConcrete generateLevelRegularWallsWithoutObsWithoutExitTrue(
			String name, Theater theater,
			String fatherPlate, String[] sonsPlate, String[] sonsAltPlate, 
			String[] exit, String[] sonsWalls
			) {
		
		return generateLevel(
				name,
				theater,
				gridWithRegularDecoratedWalls(
						sonsWalls,
						gridOfVirginTesserae(
								sonsPlate,
								sonsAltPlate,
								exit
								)
						)
				);
	}
	/**
	 * Create a standard level.
	 * @param name level's name
	 * @param theater level's theater
	 * @param fatherPlate root plate path
	 * @param sonsPlate normal plate paths
	 * @param sonsAltPlate altered plate paths
	 * @param exit exit's path
	 * @param sonsWalls walls' path
	 * @param fatherOb root obstacle path
	 * @param sonsOb normal obstacles paths
	 * @param sonsAltOb altered obstacles path
	 * @param numbOb number of obstacles to insert
	 * @return the created level
	 */
	private static LevelConcrete generateLevelRegularWallsCasualObs(
			String name, Theater theater,
			String fatherPlate, String[] sonsPlate, String[] sonsAltPlate, String[] exit,
			String[] sonsWalls,
			String fatherOb, String[] sonsOb, String[] sonsAltOb, int numbOb
			) {
		
		return generateLevel(
				name,
				theater,
				gridWithDecoratedObstacles(
						sonsOb,
						sonsAltOb,
						numbOb,
						gridWithRegularDecoratedWalls(
								sonsWalls,
								gridOfVirginTesserae(
										sonsPlate,
										sonsAltPlate,
										exit
										)
								)
						)
						
				);
	}
	/**
	 * Adds the required number of walls ( or a casual number of them)
	 * to the grid, making it possible to reach the exit starting from Tessera (0,0)
	 * @param levelConcrete level which grid you want to edit
	 * @param sonWalls walls' paths
	 * @param minWalls minimum number of walls (inclusive)
	 * @param maxWalls maximum number of walls (exclusive)
	 */
	private static void extraDecoratedWalls(
			LevelConcrete levelConcrete, String[] sonWalls, int minWalls, int maxWalls) {
		Set<CartesianCoordinate> coordsToTest = new HashSet<>();
		CartesianCoordinate exit = null;
		TesseraAbstract[][] grid = levelConcrete.getGrid();
		for(int y = 0; y<ROWS; y++) { // find the exit's coordinates
			for (int x = 0;x<COLUMNS; x++) {
				if(grid[y][x].isExit()) exit = new CartesianCoordinate(y,x);
			}
		}
		if (exit==null) return; // if no exit then return
		do {
			for(CartesianCoordinate c : coordsToTest) { // reset bundles at every coordsToTest (if first while it's failed)
				levelConcrete.getGrid()[c.getY()][c.getX()].setBundle(null);;
			}
			int nWalls = r.nextInt(minWalls, maxWalls); // fix number of walls
			coordsToTest = new HashSet<>(getASetOfCoords(nWalls,new HashSet<>(),exit)); // get a set of coordinates to test
			for(CartesianCoordinate c : coordsToTest) {
				levelConcrete.getGrid()[c.getY()][c.getX()].setBundle(new DecoratedWall(new Wall(),sonWalls)); // and bundle their tesserae with extra walls
			}
		} while(
				!testLevelByExit(levelConcrete, new CartesianCoordinate(0,0),new HashSet<>()) // loop if it cannot find the exit
				);
		
	}
	/**
	 * Generate a randomly set of coordinates
	 * respecting the initial game situation
	 * @param nWalls number of needed coordinates to bundle these walls
	 * @param set set to return
	 * @param exit exit's point
	 * @return set of points
	 */
	private static Set<CartesianCoordinate> getASetOfCoords(
			int nWalls, Set<CartesianCoordinate> set, CartesianCoordinate exit) {
		if(nWalls>0) {
			int nRow, nCol;
			do {
				nRow = r.nextInt(0,11);
				nCol = r.nextInt(0,13);
			} while(
					// Coords can't be exit coords !
					(nRow==exit.getY() && nCol==exit.getX()) ||
					// Coords can't be regular walls coords
					(nRow%2!=0 && nCol%2!=0) ||
					// Coords can't be near player spawn
					(nRow==0 && nCol==0) || (nRow==1 && nCol==0) 
					|| (nRow==0 && nCol==1)
					);
			set.add(new CartesianCoordinate(nRow,nCol));
			return getASetOfCoords(nWalls-1,set,exit);
		}
		return set; 
	}
	
	/**
	 * Backtracking algorithm to find the exit in the given game grid.
	 * @param levelConcrete level of the tested grid
	 * @param c reached point
	 * @param setVisitedCoords collected visited points
	 * @return Return true if starting by the input point it is possible to reach the exit
	 */
	private static boolean testLevelByExit(
			LevelConcrete levelConcrete, CartesianCoordinate c, Set<CartesianCoordinate> setVisitedCoords) {
		try {
			//System.out.println("in recursive loop with "+c);
			int x = c.getX();
			int y = c.getY();
			if(x<0||y<0||x>=COLUMNS||y>=ROWS) return false;
			if(levelConcrete.getGrid()[y][x].isExit()) return true;
			if(setVisitedCoords.contains(c)) return false;
			setVisitedCoords.add(c);
			Prop p = levelConcrete.getGrid()[y][x].getBundle();
			if( p instanceof DecoratedWall || p instanceof Wall) return false;

			return 
					testLevelByExit(levelConcrete, new CartesianCoordinate(y,x+1), setVisitedCoords) ||
					testLevelByExit(levelConcrete, new CartesianCoordinate(y-1,x), setVisitedCoords) ||
					testLevelByExit(levelConcrete, new CartesianCoordinate(y+1,x), setVisitedCoords) ||
					testLevelByExit(levelConcrete, new CartesianCoordinate(y,x-1), setVisitedCoords);

		}catch(StackOverflowError e) {
			e.printStackTrace();
//			System.out.println("StackOverflowError with  these coords: ");
//			setVisitedCoords.forEach(x-> System.out.println(x));
			return false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	/**
	 * Position every npc from input level
	 * Do not use it with the boss.
	 * This method is able to reduce total number of walls.
	 * @param levelConcrete level of npcs set
	 */
	private static void positioningNPCs(LevelConcrete levelConcrete) {
		int x,y;
		CartesianCoordinate c;
		for(NonPlayableCharacter npc : levelConcrete.getNpcs()) {
			do {
				y = r.nextInt(0,11);
				x = r.nextInt(0,13);
				c = new CartesianCoordinate(y,x);
				// if isn't a standard wall...
				if(!(y%2!=0 && x%2!=0)) {
					// and there are too many walls then I must reduce entropy limitations
					Prop hardSpawn = levelConcrete.getGrid()[y][x].getBundle();
					if(hardSpawn instanceof DecoratedWall
							|| hardSpawn instanceof Wall) {
						levelConcrete.getGrid()[y][x].setBundle(null);
					}
				}
			} while(
					// tessera must not be exit to be a possible spawn for enemies
					levelConcrete.getGrid()[y][x].isExit()==true
					// enemies don't spawn near the player
					|| y<2 
					|| x<2 
					 // enemies have to be reachable by the player 
					|| (!testLevelByExit(levelConcrete, c, new HashSet<CartesianCoordinate>()))
					);
			// if tessera has bundle then it will be nulled
			if(levelConcrete.getGrid()[y][x].getBundle()!=null) {
				levelConcrete.getGrid()[y][x].setBundle(null);
			}
			npc.setXGrid(x);
			npc.setYGrid(y);
			npc.setXPanel(START_PC_X + x*UNIT_NORMAL);
			npc.setYPanel(START_PC_Y + y*UNIT_NORMAL);
//			System.out.println("positioning an npc at "+c);
			
		}

//		System.out.println("Positioning npcs completed");
	}
	/**
	 * Position the boss near the center of the panel
	 * @param levelConcrete level in which there is the boss to collocate
	 */
	private static void positioningBoss(LevelConcrete levelConcrete) {
		int x,y;
		for(NonPlayableCharacter npc : levelConcrete.getNpcs()) {
			if(npc instanceof Hound) {
				y = 5;
				x = 6;
				npc.setXGrid(x);
				npc.setYGrid(y);
				npc.setXPanel(START_PC_X + x*UNIT_NORMAL);
				npc.setYPanel(START_PC_Y + y*UNIT_NORMAL);	
			}
		}
	}
	
	// Complete Methods for Factories
	/**
	 * Generate a level belonging to City Stage
	 * @param name level's name
	 * @param theater level's theater
	 * @param numb number of obstacles to insert
	 * @param minWalls minimum number of extra walls can be added (inclusive)
	 * @param maxWalls maximum number of extra walls can be added (exclusive)
	 * @return the created level
	 */
	private static DecoratedLevel levelStageCity(
			String name, Theater theater, int numb,
			int minWalls, int maxWalls
			) {
		LevelConcrete levelConcrete = generateLevelRegularWallsCasualObs(
				name, theater,
				fatherPlateS1, sonsPlateS1, sonsAltPlateS1, exitNode,
				sonsWallS1,
				fatherObS1, sonsObS1, sonsAltObS1, numb
				);
		extraDecoratedWalls(levelConcrete,sonsWallS1,minWalls,maxWalls);
		positioningNPCs(levelConcrete);
		gridWithAdjustedShadowedPlates(levelConcrete); // must be the last methods before return
		return new DecoratedLevel(levelConcrete, new ArchesCity(), "/iconsClassical/", new Color(255,120,0));
	}
	/**
	 * Generate last level (boss-fight level) for City Stage
	 * @param name level's name
	 * @param theater level's theater
	 * @return the created level
	 */
	private static DecoratedLevel levelBossStageCity(
			String name, Theater theater
			) {
		LevelConcrete levelConcrete = generateLevelRegularWallsWithoutObsWithoutExitTrue(
				name, theater,
				fatherPlateS1, sonsPlateS1, sonsAltPlateS1, exitNode,
				sonsWallS1
				);
		positioningBoss(levelConcrete);
		gridWithAdjustedShadowedPlates(levelConcrete);
		return new DecoratedLevel(levelConcrete, new ArchesCity(), "/iconsClassical/", new Color(255,120,0));
	}
	/**
	 * Generate a level belonging to Jungle Stage
	 * @param name level's name
	 * @param theater level's theater
	 * @param numb number of obstacles to insert
	 * @param minWalls minimum number of extra walls can be added (inclusive)
	 * @param maxWalls maximum number of extra walls can be added (exclusive)
	 * @return the created level
	 */
	private static DecoratedLevel levelStageJungle(
			String name, Theater theater, int numb,
			int minWalls, int maxWalls
			) {
		LevelConcrete levelConcrete = generateLevelRegularWallsCasualObs(
				name, theater,
				fatherPlateS2, sonsPlateS2, sonsAltPlateS2, exitNode,
				sonsWallS2,
				fatherObS2, sonsObS2, sonsAltObS2, numb
				);
		extraDecoratedWalls(levelConcrete,sonsWallS2,minWalls,maxWalls);
		positioningNPCs(levelConcrete);
		gridWithAdjustedShadowedPlates(levelConcrete);
		return new DecoratedLevel(levelConcrete, new ArchesJungle(), "/iconsClassical/", new Color(255,120,0));
	}
	/**
	 * Generate last level (boss-fight level) for Jungle Stage
	 * @param name level's name
	 * @param theater level's theater
	 * @return the created level
	 */
	private static DecoratedLevel levelBossStageJungle(
			String name, Theater theater
			) {
		LevelConcrete levelConcrete = generateLevelRegularWallsWithoutObsWithoutExitTrue(
				name, theater,
				fatherPlateS2, sonsPlateS2, sonsAltPlateS2, exitNode,
				sonsWallS2
				);
		positioningBoss(levelConcrete);
		gridWithAdjustedShadowedPlates(levelConcrete);
		return new DecoratedLevel(levelConcrete, new ArchesCity(), "/iconsClassical/", new Color(255,120,0));
	}
	/**
	 * Generate a level belonging to Classical Stage
	 * @param name level's name
	 * @param theater level's theater
	 * @param numb number of obstacles to insert
	 * @param minWalls minimum number of extra walls can be added (inclusive)
	 * @param maxWalls maximum number of extra walls can be added (exclusive)
	 * @return the created level
	 */
	private static DecoratedLevel levelStageClassical(
			String name, Theater theater, int numb,
			int minWalls, int maxWalls
			) {
		LevelConcrete levelConcrete = generateLevelRegularWallsCasualObs(
				name, theater,
				fatherPlateS3, sonsPlateS3, sonsAltPlateS3, exitNode,
				sonsWallS3,
				fatherObS3, sonsObS3, sonsAltObS3, numb
				);
		extraDecoratedWalls(levelConcrete,sonsWallS3,minWalls,maxWalls);
		positioningNPCs(levelConcrete);
		gridWithAdjustedShadowedPlates(levelConcrete);
		return new DecoratedLevel(levelConcrete, new ArchesClassical(), "/iconsClassical/", new Color(255,120,0));
	}
	/**
	 * Generate last level (boss-fight level) for Classical Stage
	 * @param name level's name
	 * @param theater level's theater
	 * @return the created level
	 */
	private static DecoratedLevel levelBossStageClassical(
			String name, Theater theater
			) {
		LevelConcrete levelConcrete = generateLevelRegularWallsWithoutObsWithoutExitTrue(
				name, theater,
				fatherPlateS3, sonsPlateS3, sonsAltPlateS3, exitNode,
				sonsWallS3
				);
		positioningBoss(levelConcrete);
		gridWithAdjustedShadowedPlates(levelConcrete);
		return new DecoratedLevel(levelConcrete, new ArchesCity(), "/iconsClassical/", new Color(255,120,0));
	}
	
}