package view;

import javax.swing.JPanel;

import model.CartesianCoordinate;
/**
 * Personalized JPanel intended to be nested into a DynaSlaveCard JPanel 
 */
public class DynaJPanelAccountNested extends JPanel {
	/**
	 * Serialized key for Serialization
	 */
	private static final long serialVersionUID = -5626828355409648970L;
	/**
	 * DynaSlaveCard container
	 */
	protected DynaSlaveCard dsc;
	
	// Parameters of this component in the DynaSlaveAccount
	/**
	 * Origin point of this JPanel into the Container
	 */
	private CartesianCoordinate origin;
	/**
	 * This JPanel Height
	 */
	private int height;
	/**
	 * This JPanel Width
	 */
	private int width;
	/**
	 * Constructor
	 * @param dsc DynaSlaveCard container
	 */
	public DynaJPanelAccountNested(DynaSlaveCard dsc) {
		this.dsc = dsc;
	}
	
	// Getters
	/**
	 * Getter of origin point (as CartesianCoordinate)
	 * @return current origin point
	 */
	private CartesianCoordinate getOrigin() {return origin;}
	
	@Override
	public int getWidth() {return width;}
	
	@Override
	public int getHeight() {return height;}
	
	// Setters
	/**
	 * Setter of origin point
	 * @param origin new origin point (as CartesianCoordinate)
	 */
	private void setOrigin(CartesianCoordinate origin) {this.origin = origin;}
	/**
	 * Setter of width
	 * @param width new width
	 */
	private void setWidth(int width) { this.width = width;}
	/**
	 * Setter of height
	 * @param height new height
	 */
	private void setHeight(int height) {this.height = height;}
	/**
	 * Method to relocate this JPanel into its container
	 * @param meterH height unit
	 * @param meterW width unit
	 * @param hTimes number of height units
	 * @param wTimes number of width units
	 */
	public void relocateMe(
			int meterH, int meterW,
			int hTimes, int wTimes) {
		
		CartesianCoordinate newOrigin = new CartesianCoordinate(meterH,meterW);
		setOrigin(newOrigin);
		setWidth(meterW*wTimes);
		setHeight(meterH*hTimes);

		setBounds(
				getOrigin().getX(),
				getOrigin().getY(),
				getWidth(),
				getHeight()
				);
	}
	
	
	
	
}
