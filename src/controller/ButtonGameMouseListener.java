package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.DynaSlaveCard;
/**
 * Concrete Command (Command Pattern) provided by Java Swing Architecture.
 * It is used during ThreadGame's activity.
 * It's binded to DynaSlaveGame's buttons rather then ButtonMenuMouseListener version.
 * The command execution happens when the mouse is pressed and released on the button.
 */
public class ButtonGameMouseListener implements MouseListener {
	/**
	 * DynaSlaveCard's reference
	 */
	private DynaSlaveCard dsc;
	/**
	 * Ordinal position of the button
	 */
	private int iOption;
	/**
	 * Listener Constructor
	 * @param dsc dynaSlaveCard's reference
	 * @param iOption ordinal position of the button, it's used to call corresponding method
	 */
	public ButtonGameMouseListener(DynaSlaveCard dsc, int iOption) {
		this.dsc = dsc;
		this.iOption = iOption;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		dsc.getMaster().setEnterPressedByMouse(true);
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {
		dsc.executeOptionByButton(iOption);
	}
	@Override
	public void mouseExited(MouseEvent e) {}

}
