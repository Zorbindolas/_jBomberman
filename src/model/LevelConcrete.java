package model;

import java.util.Set;
/**
 * Class that define what is a level in this game
 */
public class LevelConcrete extends LevelAbstract {
	/**
	 * Constructor of a concrete level
	 * @param name name of the level
	 * @param theater Theater of this level
	 * @param grid grid of this level
	 * @param npcs npcs in this level
	 */
	public LevelConcrete(
			String name, Theater theater, 
			TesseraAbstract[][] grid,
			Set<NonPlayableCharacter> npcs
			) {
		
		super(name, theater, grid, npcs);
	}
	/**
	 * Builder Pattern to help level construction
	 */
	public static class LevelBuilder{
		/**
		 * Builder level name
		 */
		private String name;
		/**
		 * Builder level theater
		 */
		private Theater theater;
		/**
		 * Builder level grid
		 */
		private TesseraAbstract[][] tesserae;
		/**
		 * Builder level set of npcs
		 */
		private Set<NonPlayableCharacter> npcs;
		/**
		 * Builder constructor
		 * @param name name of builder level
		 * @param theater theater of builder level
		 */
		public LevelBuilder(String name, Theater theater) {
			this.name = name;
			this.theater = theater;
			tesserae = new TesseraAbstract[ROWS][COLUMNS];
		}
		/**
		 * insert a Tessera in grid matrix
		 * @param y ordinate
		 * @param x abscissa
		 * @param tessera tessera to insert
		 * @return this builder object
		 * @throws InconsistentCoordinateException exception management
		 */
		public LevelBuilder setSquare(int y, int x, TesseraAbstract tessera) throws InconsistentCoordinateException {
			try {
				if(y<0 || x<0 || y>=ROWS || x>= COLUMNS) throw new InconsistentCoordinateException(y,x);
				tesserae[y][x] = tessera;
			} catch (InconsistentCoordinateException e) {
				System.out.println(e);
			}
			return this;
		}
		/**
		 * Set the grid of this level grid with an input matrix of tesserae objects
		 * @param propsIn input matrix tesserae
		 * @param convertOnlyNullities if true set only null position in the array grid
		 * @return this builder object
		 * @throws InconsistentCoordinateException exception management
		 */
		public LevelBuilder setGrid(
				TesseraAbstract[][] propsIn, boolean convertOnlyNullities) 
						throws InconsistentCoordinateException {	
			try {
				
				int lenY = propsIn.length;
				int lenX = propsIn[0].length;
				if(lenY < 0 || lenX < 0 || lenY > ROWS || lenX > COLUMNS) throw new InconsistentCoordinateException(lenY,lenX);
				
				for(int y=0; y<lenY; y++)
					for(int x=0; x<lenX; x++)
						if(convertOnlyNullities && tesserae[y][x]!=null) continue;
						else tesserae[y][x] = propsIn[y][x];
				
			} catch (InconsistentCoordinateException e) {
				System.out.println(e);
			}
			return this;
		}
		/**
		 * Use input array to convert only odd position
		 * @param array input array
		 * @return this builder object
		 * @throws InconsistenArrayException exception management
		 */
		public LevelBuilder setOddPosition(TesseraAbstract[] array)
							throws InconsistenArrayException {
			try {
				if(array.length != REGULAR_WALLS) throw new InconsistenArrayException(array.length,REGULAR_WALLS);
				
				int p = 0;
				
				for(int y=0; y<ROWS; y++)
					for(int x=0; x<COLUMNS; x++)
						if(y%2!=0 && x%2!=0) tesserae[y][x] = array[p++];
				
			} catch (InconsistenArrayException e) {
				System.out.println(e);
			}
			return this;
		}
		/**
		 * Set npcs of this builder object
		 * @param npcs input npcs
		 * @return this builder object
		 */
		public LevelBuilder setNpcs(
				Set<NonPlayableCharacter> npcs) {
			
			this.npcs = npcs;
			
			return this;
		}
		/**
		 * Build a concrete level with this builder parameters
		 * @return a concrete level
		 */
		public LevelConcrete build() {
			return new LevelConcrete(name, theater, tesserae, npcs);
		}
		
	}
	
}
