package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import model.CartesianCoordinate;
import model.Prop;
import model.TesseraAbstract;
/**
 * Decorated version of a TesseraAbstract to implement its View animations 
 * in a Decorator Design Patter way. Images are animated through List Collections.
 * With this class the decorator mimics the methods of its component.
 */
public abstract class DecoratorTessera extends TesseraAbstract 
implements Iterable<BufferedImage>, FootageFuncs {
	/**
	 * Decorated TesseraAbstract
	 */
	protected TesseraAbstract component;
	/**
	 * Current image of the running animation
	 */
	protected BufferedImage actualImage;
	/**
	 * Iteration of the animation process
	 */
	protected Iterator<BufferedImage> iterForms;
	/**
	 * Main animation footage
	 */
	protected List<BufferedImage> mainImages;
	/**
	 * Shadowed animation footage
	 */
	protected List<BufferedImage> shadowedImages;
	/**
	 * Exit animation footage
	 */
	protected List<BufferedImage> exitImages;
	/**
	 * True if this wrapped TesseraAbstract is an exit.
	 */
	protected BufferedImage exitImage;
	/**
	 * True if shadowed animation is running.
	 */
	protected boolean altering;
	/**
	 * Constructor to decorate a TesseraAbstract
	 * @param tessera TesseraAbstract to decorate
	 * @param path main footage images path
	 * @param shadowPath shadowed footage images path
	 * @param exitPath exit footage images path
	 */
	public DecoratorTessera(
			TesseraAbstract tessera, 
			String[] path, String[] shadowPath, String[] exitPath) {
		
		super(tessera.isExit(),tessera.isBlackened());
		
		this.component = tessera;
		
		mainImages = new LinkedList<>();
		shadowedImages = new LinkedList<>();
		exitImages = new LinkedList<>();
		
		try {
			
			for(String p : path) {
				this.mainImages.add(ImageIO.read(getClass().getResourceAsStream(p+sfx)));
			}
			
			for(String p : shadowPath) {
				this.shadowedImages.add(ImageIO.read(getClass().getResourceAsStream(p+sfx)));
			}
			
			for(String p : exitPath) {
				this.exitImages.add(ImageIO.read(getClass().getResourceAsStream(p+sfx)));
			}
			
			actualImage = mainImages.get(0);
			exitImage = exitImages.get(0);
			
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
		resetFootage();
		
		incFootage(); 
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
	public boolean isLethal() {return component.isLethal();}

	// Mimic method
	@Override
	public boolean isSolid() {
		if(component.getBundle()==null) return component.isSolid();
		return (component.getBundle()).isSolid();
	}
	
	// TesseraAbstract Methods
	@Override
	public Prop getBundle() {return component.getBundle();}
	@Override
	public boolean isExit() {return component.isExit();}
	@Override
	public boolean isBlackened() {return component.isBlackened();}
	@Override
	public void setBundle(Prop bundle) {component.setBundle(bundle);}
	@Override
	public void setExit(boolean exit) { component.setExit(exit);}
	@Override
	public void setBlackened(boolean blackened) { component.setBlackened(blackened);}
	
	// Funcs Decorator implementations
	@Override
	public Iterator<BufferedImage> iterator() {
		if(altering) {
			return exitImages.iterator();
		} else if(this.isBlackened()) {
			return shadowedImages.iterator();
		}
		return mainImages.iterator();
	}
	
	@Override
	public void incFootage() {
		
		if(! iterForms.hasNext()) resetFootage();
		actualImage = iterForms.next();
	}
	
	@Override
	public void resetFootage() {
		iterForms = this.iterator();
		
	}
	
	@Override
	public BufferedImage getImage() {
		if(component.isExit() && component.getBundle()==null)
			return exitImage;
		return actualImage;
	
	}
	
}
