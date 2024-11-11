package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import model.CartesianCoordinate;
import model.GameModel;
import model.LevelAbstract;
import model.Prop;
/**
 * Decorated version of a Prop to implement its View animations 
 * in a Decorator Design Patter way. Images are animated through List Collections.
 * With this class the decorator mimics the methods of its component.
 */
public abstract class DecoratorProp extends Prop  
				implements Iterable<BufferedImage>, FootageFuncs {
	/**
	 * Decorated Prop
	 */
	protected Prop component;
	/**
	 * Current image of the running animation
	 */
	protected BufferedImage actualImage;
	/**
	 * Iterator for the animation process
	 */
	protected Iterator<BufferedImage> iterForms;
	/**
	 * Main animation footage
	 */
	protected List<BufferedImage> mainImages;
	/**
	 * Destroyed animation footage
	 */
	protected List<BufferedImage> destroyedImages;
	/**
	 * Alternative animation footage
	 */
	protected List<BufferedImage> alternativeImages;
	/**
	 * True if must run alternative footage
	 */
	protected boolean altering;
	/**
	 * True if must run destroyed footage
	 */
	protected boolean destroying; 
	/**
	 * Constructor for decoration with only main footage
	 * @param c component to decorate
	 * @param path main footage path
	 */
	public DecoratorProp(Prop c, String[] path) {
		this(c, path, new String[] {}, new String[] {});
	}
	/**
	 * Constructor for decoration with main and destroyed footages
	 * @param c component to decorate
	 * @param path main footage path
	 * @param destroyedPath destroyed footage path
	 */
	public DecoratorProp(Prop c, String[] path, String[] destroyedPath) {
		this(c, path, destroyedPath, new String[] {});
	}
	/**
	 * Constructor for decoration with main, destroyed and alternative footages
	 * @param prop component to decorate
	 * @param path main footage path
	 * @param destroyedPath destroyed footage path
	 * @param alternativePath alternative footage path
	 */
	public DecoratorProp(Prop prop, String[] path, String[] destroyedPath, 
			 String[] alternativePath) {
		
		super(prop.isBreakable(),prop.isSolid());
		this.component = prop;
	
		mainImages = new LinkedList<>();
		destroyedImages = new LinkedList<>();
		alternativeImages = new LinkedList<>();
		
		try {
			
			for(String p : path) {
				this.mainImages.add(ImageIO.read(getClass().getResourceAsStream(p+sfx)));
			}
			
			for(String p : destroyedPath) {
				this.destroyedImages.add(ImageIO.read(getClass().getResourceAsStream(p+sfx)));
			}
			
			for(String p : alternativePath) {
				this.alternativeImages.add(ImageIO.read(getClass().getResourceAsStream(p+sfx)));
			}
			
			actualImage = mainImages.get(0);
			
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
		resetFootage();
		
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
	public void setAlternative(boolean destroying) { 
		this.destroying = destroying;
	}

	@Override
	public void trespasser(LevelAbstract level) {
		if(component.isBreakable()) {
			destroying = true;
		} else if (component.isWrapper()) {
			altering = true;	
		}
		resetFootage();
	}
	
	// Funcs decorator implementations
	@Override
	public Iterator<BufferedImage> iterator() {
		if(destroying) {
			return destroyedImages.iterator();
		} 
		return mainImages.iterator();
	}
	
	@Override
	public void incFootage() {
		if(! iterForms.hasNext()) {
			if(destroying) {
				component.trespasser(					
						GameModel.getInstance().getActualLevel()
						);
			}
			resetFootage();
		}
		actualImage = iterForms.next();
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
