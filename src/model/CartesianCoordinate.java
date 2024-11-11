package model;

import java.util.Iterator;
/**
 * This class models a Cartesian point
 */
public class CartesianCoordinate 
		implements Iterable<Integer>, Comparable<CartesianCoordinate>{

	/**
	 * Ordinate value
	 */
	private int y;
	/**
	 * Abscissa value
	 */
	private int x;
	
	/**
	 * Construct a Cartesian point
	 * @param y ordinate value
	 * @param x abscissa value
	 */
	public CartesianCoordinate(int y, int x) {
		this.y = y;
		this.x = x;
	}
	
	/**
	 * Getter of ordinate value
	 * @return ordinate value
	 */
	public int getY() {return y;}
	/**
	 * Getter of abscissa value
	 * @return abscissa value
	 */
	public int getX() { return x;}
	/**
	 * Setter of Ordinate value
	 * @param y new Ordinate value
	 */
	public void setY(int y) {this.y = y;}
	/**
	 * Setter of Abscissa value
	 * @param x new abscissa value
	 */
	public void setX(int x) {this.x = x;}
	
	@Override
	public String toString() {
		return "(y "+y+", x "+x+")";
	}
	
	@Override
	public int hashCode() {
		return 31 * (x+y);
	}

	@Override
	public boolean equals(Object o) {
		if (this==o) return true;
		return o!=null
				&& getClass().equals(o.getClass())
				&& x == (((CartesianCoordinate)o).getX()) 
				&& y == (((CartesianCoordinate)o).getY());
	}

	@Override
	public int compareTo(CartesianCoordinate o) {
		int order = y - o.getY();  // ascending order for y
		if (order==0) return x - o.getX(); // ascending order for x
		return order;
	}
	
//	public static void main(String[] args) {
//		CartesianCoordinate c2 = new CartesianCoordinate(1,6);
//		CartesianCoordinate c1 = new CartesianCoordinate(0,0);
//		CartesianCoordinate c3 = new CartesianCoordinate(2,5);
//		CartesianCoordinate c4 = new CartesianCoordinate(1,6);
//		CartesianCoordinate c5 = new CartesianCoordinate(3,4);
//		Set<CartesianCoordinate> s = new TreeSet<CartesianCoordinate>();
//		s.add(c1);s.add(c2);s.add(c3);s.add(c4);s.add(c5);
//		s.forEach(x->System.out.println(x));
//	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			int p = 0;
			
			@Override
			public boolean hasNext() {
				if(p>1) return false;
				return true;
			}

			@Override
			public Integer next() {
				return (p++==0)? y : ( (p++==1)? x : null ) ;
			}
			
		};
	}
	
	
}
