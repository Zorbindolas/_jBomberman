package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

import model.Bomb;
import model.CartesianCoordinate;
import model.Fire;
import model.GameModel;
import model.LevelAbstract;
import model.Prop;
/**
 * Decorated version of a Bomb object
 */
public class DecoratorBomb extends Bomb 
							implements Iterable<BufferedImage>, FootageFuncs {
	/**
	 * Bomb to decorate
	 */
	protected Bomb component;
	/**
	 * main images array
	 */
	protected BufferedImage[] mainImages;
	/**
	 * Current image of the bomb
	 */
	protected BufferedImage actualImage;
	/**
	 * Iteration of the animation process
	 */
	protected Iterator<BufferedImage> iterForms;
	/**
	 * True if bomb is destroyed
	 */
	protected boolean destroying;
	/**
	 * Constructor.
	 * @param component Bomb to wrap
	 * @param path main images path
	 */
	public DecoratorBomb(Bomb component, String[] path) {
		super(component.getYGrid(),component.getXGrid());
		this.component = component;
		
		mainImages = new BufferedImage[path.length];
		
		try {
			
			int i = 0;
			for(String p : path) {
				mainImages[i] = ImageIO.read(getClass().getResourceAsStream(p+sfx));
				i++;
			}
		
			actualImage = mainImages[0];
		
		} catch (IOException e) {e.printStackTrace();}
		
		resetFootage();
		
		DynaSound.playEffect(7); // bombPlaced
	}
	
	// Overrides of Entity
	@Override
	public int getXGrid() {return component.getXGrid();}
	@Override
	public int getYGrid() {return component.getYGrid();}
	@Override
	public int getXPanel() {return component.getXPanel();}
	@Override
	public int getYPanel() {return component.getYPanel();}
	
	@Override
	public void setXGrid(int xGrid) {component.setXGrid(xGrid);}
	@Override
	public void setYGrid(int yGrid) {component.setYGrid(yGrid);}
	@Override
	public void setXPanel(int xPanel) {component.setXPanel(xPanel);}
	@Override
	public void setYPanel(int yPanel) {component.setYPanel(yPanel);}
	@Override
	public CartesianCoordinate getSquareCoords() {return component.getSquareCoords();}
	
	// Overrides of Figure
	@Override
	public boolean isWrapper() {return component.isWrapper();}
	@Override
	public boolean isWrappable() {return component.isWrappable();}
	@Override
	public boolean isBreakable() {return component.isBreakable();}
	@Override
	public boolean isSolid() {return component.isSolid();}
	@Override
	public boolean isLethal() {return component.isLethal();}
	
	// Overrides of Prop
	@Override
	public void setSolid(boolean solid) {component.setSolid(solid);}
	/**
	 * Set the alternative images path
	 * @param destroying new destroying value
	 */
	public void setAlternative(boolean destroying) { this.destroying = destroying;}
	
	@Override
	public void trespasser(LevelAbstract level) { 
		DynaSound.playEffect(4); // bombExplosion
		explode(level);
	
		Map<CartesianCoordinate,Fire> virginMap = getFiresToBeCreated();
		Map<CartesianCoordinate,Prop> decoratedMap = new HashMap<CartesianCoordinate, Prop>();
		
		for(CartesianCoordinate c : virginMap.keySet()) {
			Fire fire = (Fire)virginMap.get(c);
			DecoratedFire decoratedFire = new DecoratedFire(fire);
			decoratedMap.put(c, decoratedFire);
		}
		
		level.bundling(decoratedMap);

		level.setToBeTrespasser(getSquareToBeDestroyed());
	}
	
	@Override
	public int[] getForms() {
		return component.getForms();
	}
	
	// Funcs decorator implementations
	@Override
	public Iterator<BufferedImage> iterator() {
		return new Iterator<BufferedImage>() {

				int[] actualForm = component.getForms();
				int shoots = actualForm.length-1;  // final index
				int puntatore;  // initialized to 0
				int invarianceAccumulated;
				
				// default constructor
				
				@Override
				public boolean hasNext() {
					if(invarianceAccumulated++>component.getTriggerTime()) {
						invarianceAccumulated = 0;
						puntatore++;
					}
					if(puntatore>shoots) return false;
					return true;
				}

				@Override
				public BufferedImage next() {
					return mainImages[actualForm[puntatore]];
				}
				
			};
	}
	
	@Override
	public void incFootage() {
		if(! iterForms.hasNext()) {
			this.trespasser(					
						GameModel.getInstance().getActualLevel()
						);
		} else {
			actualImage = iterForms.next();
		}
	}
	
	@Override
	public void resetFootage() {
		iterForms = this.iterator();
	}
	
	@Override
	public BufferedImage getImage() {
		return actualImage;
	}


}
