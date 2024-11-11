package model;
/**
 * Class models a Timer counter
 */
public class ModelTimer {

	/**
	 * Define unit to add to the timer. 
	 */
	private boolean timerUp; // true: timer++, false: timer--
	/**
	 * timer' s value
	 */
	private long value;
	/**
	 * timer's limit (as a clock work)
	 */
	private long limit;
	/**
	 * timer's label
	 */
	private String label;
	/**
	 * timer constructor
	 * @param value starting value
	 * @param timerUp starting functionality
	 */
	public ModelTimer(int value, boolean timerUp) {
		this.value = value;
		this.timerUp = timerUp;
	}
	/**
	 * timer constructor
	 * @param value starting value
	 * @param timerUp starting functionality
	 * @param limit timer limit
	 */
	public ModelTimer(int value, boolean timerUp, long limit) {
		this.value = value;
		this.timerUp = timerUp;
		this.limit = limit;
	}
	/**
	 * timer constructor
	 * @param value starting value
	 * @param timerUp starting functionality
	 * @param label timer's label
	 */
	public ModelTimer(int value, boolean timerUp, String label) {
		this(value, timerUp);
		this.label=label;
	}

	/**
	 * Add +1 to timer if timerUp true, else -1
	 */
	public void flow() {
		if(timerUp) value++;
		else value--;
	}
	/**
	 * Decrement timer value by one
	 */
	public void dec() {
		value--;
	}
	/**
	 * Check if timer go over its limit
	 * @return true if limit is surpassed
	 */
	public boolean overLimit() {
		if( (timerUp && (limit<=value) ) 
					||
				(!timerUp && (limit>=value))
				)
			return true;
			
		return false;
	}
	/**
	 * Getter of timer value
	 * @return timer's value
	 */
	public long getValue() {return value;}
	/**
	 * Getter of timer's label
	 * @return timer's label
	 */
	public String getLabel() {return label;}
	/**
	 * Set timer's value to zero
	 */
	public void zero() {
		value = 0;	
	}
	
	@Override
	public String toString() {
		return ""+value;
	}
	
	
}
