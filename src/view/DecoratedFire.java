package view;

import java.awt.image.BufferedImage;
import java.util.Iterator;

import model.Dir;
import model.Fire;
import model.GameModel;
/**
 * Decorated Prop version of a Fire from Model
 */
public class DecoratedFire extends DecoratorProp {
	/**
	 * Direction of the Fire component
	 */
	private Dir dir;
	/**
	 * Generate images paths to insert in the constructor.
	 * @param dir direction of the Fire component
	 * @param fs type of Fire
	 * @param numberOfForms Footage cardinality
	 * @return images paths of a DecoratedFire
	 */
	private static String[] createPath(Dir dir, Fire.FlameSpread fs, int numberOfForms) {
		
		String[] strings = new String[numberOfForms];
		
		String fatherNode = "/bomb_fire_"+dir.toString();
		String sonsNode = "";
		
		if(! dir.equals(Dir.NONE))
			fatherNode += "_"+fs.toString();
		
		sonsNode = fatherNode + "_";
		
		for(int i=0; i<numberOfForms;i++) {
			strings[i] = fatherNode + sonsNode + i;  
		}
		
		return strings;
	}
	
//	//Test
//	public static void main(String[] args) {
//		for(Dir d : Dir.values()) {
//			for(Fire.FlameSpread fs : Fire.FlameSpread.values()) {
//				String[] s = createPath(d,fs,5);
//				for(int i=0;i<s.length;i++)System.out.println(s[i]);
//			}
//		}
//	}
	/**
	 * Constructor
	 * @param c Fire component
	 * @param numberOfForms footage cardinality
	 */
	public DecoratedFire(Fire c, int numberOfForms)  {
		super(c, createPath(c.getFireDir(), c.getFireSpread(), numberOfForms));
		this.dir = c.getFireDir();
	}
	/**
	 * Constructor with footage cardinality formatted to 5
	 * @param c Fire component
	 */
	public DecoratedFire(Fire c)  {
		this(c, 5);
	}

	@Override
	public Iterator<BufferedImage> iterator() {
		return new Iterator<BufferedImage>() {

				int[] actualForm = new int[] {0,1,2,3,4,4,3,2,1,0};
				int shoots = 9;  // final index
				int puntatore;  // initialized to 0
				
				// default constructor
				
				@Override
				public boolean hasNext() {
					if(puntatore>shoots) return false;
					return true;
				}

				@Override
				public BufferedImage next() {
					return mainImages.get(actualForm[puntatore++]);
				}
				
			};
	}
	
	@Override
	public void incFootage() {
		
		if(! iterForms.hasNext()) {
			if(this.dir.equals(Dir.NONE)) {
				GameModel.getInstance().getActualPC().reloadAmmunition();
				this.dir = Dir.UP;
			}
				
			component.trespasser(
					GameModel.getInstance().getActualLevel()
					);
			resetFootage(); 
			
		}
		
		actualImage = iterForms.next();
	}
}
