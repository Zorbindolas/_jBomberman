package view;

import model.MyGridFormat;
/**
 * Interface with public static field for these parameters: Game fluidity, Sprite standard dimensions,
 * Game grid dimensions and derived values, PC's spawn coordinates.
 */
public interface MyJBombermanFormat {

	// Game Fluidity parameters
	/**
	 * FPS of the game
	 */
	int FPS = 60; // FPS standard del gioco
	/**
	 * Constant to make a standard speed with other FPS standards
	 */
	int ALACRITY_CONSTANT = 240;
	/**
	 * Number of refreshes in a second
	 */
	int REFRESH_RATE = 12;
	/**
	 * Duration of a Refresh standard
	 */
	int REFRESH_STANDARD = (int)(FPS/REFRESH_RATE );
	/**
	 * Speed standard of the player character
	 */
	int SPEED_STANDARD = (int) (ALACRITY_CONSTANT / FPS);
	
	// Sprites parameters
	/**
	 * Normal format for a sprite
	 */
	int FORMAT_SPRITE_NORMAL = 16;
	/**
	 * Small format for a sprite
	 */
	int FORMAT_SPRITE_SMALL = (int)( FORMAT_SPRITE_NORMAL / 2);   // 8
	/**
	 * Mega format for a sprite
	 */
	int FORMAT_SPRITE_MEGA = (int)( FORMAT_SPRITE_NORMAL * 1.5);  // 24
	/**
	 * Normal unit format
	 */
	int UNIT_NORMAL = FORMAT_SPRITE_NORMAL * 3;
	/**
	 * Small unit format
	 */
	int UNIT_SMALL = FORMAT_SPRITE_SMALL * 3;
	/**
	 * Mega unit format
	 */
	int UNIT_MEGA = FORMAT_SPRITE_MEGA * 3;
	
	// Game grid parameters
	/**
	 * Game panel border width
	 */
	int BORDER_WIDTH = UNIT_MEGA;
	/**
	 * Game panel superior border height
	 */
	int BORDER_HEIGHT_SUP = UNIT_SMALL;
	/**
	 * Game panel inferior border height
	 */
	int BORDER_HEIGHT_INF = UNIT_SMALL;
	/**
	 * Game panel height
	 */
	int GAME_HEIGHT = MyGridFormat.ROWS * UNIT_NORMAL;
	/**
	 * Game panel width
	 */
	int GAME_WIDTH = MyGridFormat.COLUMNS * UNIT_NORMAL;
	/**
	 * Dock height
	 */
	int HEIGHT_DOCK = UNIT_NORMAL * 2;
	/**
	 * Total width of the default screen
	 */
	int TOTAL_WIDTH = BORDER_WIDTH * 2 + GAME_WIDTH;
	/**
	 * Total height of the default screen
	 */
	int TOTAL_HEIGHT = HEIGHT_DOCK + BORDER_HEIGHT_SUP + GAME_HEIGHT + BORDER_HEIGHT_INF;
	
	// Prepared calculations
	/**
	 * X coordinate to spawn nord Arch
	 */
	int X_SPAWN_ARCH_NORD = BORDER_WIDTH+GAME_WIDTH;
	/**
	 * Y coordinate to spawn sud Arch
	 */
	int Y_SPAWN_ARCS_SUD = HEIGHT_DOCK+GAME_HEIGHT;
	/**
	 * Y coordinate to spawn mid sud Arch
	 */
	int Y_SPAWN_ARCS_MID_SUD = HEIGHT_DOCK + BORDER_HEIGHT_SUP + GAME_HEIGHT;
	/**
	 * Y coordinate to spawn mid overst est arcs
	 */
	int Y_SPAWN_ARCS_MID_OVEST_EST_PARTIAL = HEIGHT_DOCK + UNIT_NORMAL;
	/**
	 * X Offset for the game session panel
	 */
	int OFFSET_X = BORDER_WIDTH;
	/**
	 * Y Offset for the game session panel
	 */
	int OFFSET_Y = HEIGHT_DOCK + BORDER_HEIGHT_SUP;
	
	// PC's Spawn
	/**
	 * PC spawn X coordinate
	 */
	int START_PC_X = BORDER_WIDTH;
	/**
	 * PC spawn Y coordinate
	 */
	int START_PC_Y = HEIGHT_DOCK;
}
