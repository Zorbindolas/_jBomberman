package view;

import model.Figure;
import model.GameModel;
import model.PropWrapper;
/**
 * A set of Animate classes to increase all Decorator's game footage.
 * The parameter is GameModel from which we get the to be increased Decorator.
 */
public enum Animations implements Animate {
	/**
	 * Reset PC animation from the beginning
	 */
	PC_RESET{
		@Override
		public void animate(Object g) {
			((FootageFuncs)(((GameModel)g).getActualPC())).resetFootage();
		}
	},
	/**
	 * Increment PC animation by one sprite (the next one)
	 */
	PC_INC{
		@Override
		public void animate(Object g) {
			((FootageFuncs)(((GameModel)g).getActualPC())).incFootage();
		}
	},
	/**
	 * Increment PC winning animation 
	 */
	PC_WIN{
		@Override
		public void animate(Object g) {
			((FootageWin)(((GameModel)g).getActualPC())).incFootageWin();
		}
	},
	/**
	 * Increment PC dying animation
	 */
	PC_DEATH{
		@Override
		public void animate(Object g) {
			((FootageDeath)(((GameModel)g).getActualPC())).incFootageDeath();
		}
	},
	/**
	 * Increment each tessera animation (or its bundled Prop animation if present)
	 */
	GRID_INC{
		@Override
		public void animate(Object g) {
			Figure[][] grid = GameModel.getInstance().getActualLevel().getGrid();
			for(int y = 0; y<grid.length; y++)
				for (int x = 0; x<grid[0].length; x++) {
					if ((grid[y][x] instanceof PropWrapper)
							&& (((PropWrapper) (grid[y][x])).getBundle()!=null)
							) {
						((FootageFuncs)((DecoratorTessera)grid[y][x]).getBundle()).incFootage();
					}
				}
		}
	},
	/**
	 * Increment each powerupper animation
	 */
	POWER_UP_INC{
		@Override
		public void animate(Object g) {
			((FootageFuncs)(((GameModel)g).getPowerUpMediator())).incFootage();;
		}
	},
	/**
	 * Increment each NPC animation
	 */
	NPC_INC{
		@Override
		public void animate(Object g) {
			((FootageFuncs)(((GameModel)g).getNpcMediator())).incFootage();
		}
	};

}
