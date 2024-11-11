package model;
/**
 * Class models a pointer, as a cursor pointer
 */
public class ModelPointer {

	/**
	 * Value of the pointer
	 */
	private Integer pointer;
	/**
	 * Pointer constructor
	 * @param pointer starting pointer value
	 */
	public ModelPointer(int pointer) {
		this.pointer = pointer;
	}
	/**
	 * Setter of pointer value
	 * @param pointer new pointer value
	 */
	public void setPointer(int pointer) {this.pointer = pointer;}
	/**
	 * Getter of pointer value
	 * @return pointer value
	 */
	public Integer getValue() {return pointer;}
	/**
	 * Increment pointer value by one
	 */
	public void inc() {pointer++;}
	/**
	 * Decrement pointer value by one
	 */
	public void dec() {pointer--;}
	/**
	 * set pointer value to zero
	 */
	public void reset() {pointer = 0;}
	/**
	 * Increment pointer value respecting a modulus
	 * @param modulus modulus to respect
	 */
	public void inc(int modulus) {
		this.inc();
		if(pointer>=modulus) this.reset();
	}
	/**
	 * Decrement pointer value respecting a modulus
	 * @param modulus modulus to respect
	 */
	public void dec(int modulus) {
		this.dec();
		if(pointer<0) this.setPointer(modulus-1);
	}

}
