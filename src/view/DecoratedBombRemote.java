package view;

import model.Bomb;
import model.BombRemote;
import model.GameModel;
import model.LevelAbstract;
import model.TimerExplosion;
/**
 * Decorated Bomb only for BombRemote
 */
public class DecoratedBombRemote extends DecoratorBomb implements TimerExplosion {
	/**
	 * Paths from which load bomb images.
	 */
	private static final String[] path = new String[] {
			"/bombTimer/bomb0", 
			"/bombTimer/bomb1",
		};
	/**
	 * Constructor
	 * @param component BombRemote component
	 */
	public DecoratedBombRemote(Bomb component) {
		super(component, path);
	}
	
	@Override
	public boolean isTriggered() {return ((BombRemote)component).isTriggered();}
	@Override
	public void trigger() {((BombRemote)component).trigger();}
	
	@Override
	public void incFootage() {
		if( ((BombRemote)component).isTriggered()) {
			if(! iterForms.hasNext()) {
				this.trespasser(					
							GameModel.getInstance().getActualLevel()
							);
			} else {
				actualImage = iterForms.next();
			}
				
		}
	}
	
	@Override
	public void trespasser(LevelAbstract level) { 
		((BombRemote)component).trigger(); // in order to fix player's timerBombs list due to collateral explosions detonation
		super.trespasser(level);
	}
	

}
