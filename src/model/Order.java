package model;
/**
 * This Class represents an Order object from the Observable Model to all its Observers.
 * It's the encapsulation of an Order request.
 */
public class Order {

	/**
	 * Possible labels to choose
	 */
	public static enum Label {
		/**
		 * APPLICATION ON LABEL
		 */
		APP_ON, 
		/**
		 * APPLICATION OFF LABEL
		 */
		APP_OFF,
		/**
		 * READY ON LABEL
		 */
		READY_ON, 
		/**
		 * READY OFF LABEL
		 */
		READY_OFF,
		/**
		 * GAME ON LABEL
		 */
		GAME_ON, 
		/**
		 * GAME OFF LABEL
		 */
		GAME_OFF, 
		/**
		 * RESTART ON LABEL
		 */
		RESTART_ON, 
		/**
		 * RESTART OFF LABEL
		 */
		RESTART_OFF;
	}
	/**
	 * label of this order
	 */
	private Label label;
	/**
	 * Order constructor
	 * @param label this order's label
	 */
	public Order(Label label) {
		this.label = label;
	}
	/**
	 * Getter of order's label
	 * @return this order's label
	 */
	public Label getLabel() {return label;}
	
}
