package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Dir;
/**
 * Personalized version of a KeyListener to manage inputs reception 
 * during menu' (running ThreadMenu).
 */
public class KeyListenerMenu implements KeyListener  {
	/**
	 * Instance for the Singleton Design Pattern
	 */
	private static KeyListenerMenu instance;
	/**
	 * Conditional input for the menu's arrow
	 */
	private boolean up,down;
	/**
	 * Conditional input to activate the command from options selection
	 * actually pointed by menu's arrow.
	 */
	private boolean enter;
	/**
	 * Conditional input to apply esc function by current panel.
	 */
	private boolean esc;
	/**
	 * Private constructor
	 */
	private KeyListenerMenu() {}
	/**
	 * singleton's instance getter
	 * @return this singleton's instance
	 */
	public static KeyListenerMenu getInstance() {
		if(instance==null) instance = new KeyListenerMenu();
		return instance;
	}
	/**
	 * Getter of direction to apply to menu's arrow
	 * @return direction to apply
	 */
	public Dir getDir() {
		if(up) {
			up = false;
			return Dir.UP;
		}
		else if(down) {
			down = false;
			return Dir.DOWN;
		}
		else return Dir.NONE;
	}
	/**
	 * Getter of enter condition.
	 * KeyListenerMenu's enter activates the commands of options selection
	 * if the current GameState owns it.
	 * This getter exhausts its signal in order to conform
	 * the Controller to User's digital experience.
	 * @return conditional signal of enter
	 */
	public boolean isEnter() {
		if(enter) {
			enter = false;
			return true;
		}
		return false;
	}
	/**
	 * Getter of esc condition.
	 * KeyListenerGame's esc activates current panel's esc function.
	 * This getter exhausts its signal in order to conform
	 * the Controller to User's digital experience.
	 * @return conditional signal of esc
	 */
	public boolean isEsc() {
		if(esc) {
			esc = false;
			return true;
		}
		return false;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int keyCode = e.getKeyCode(); // returns the integer key code associated with the key in this event
		
		switch(keyCode) {
		case KeyEvent.VK_W , KeyEvent.VK_A,
			 KeyEvent.VK_UP, KeyEvent.VK_LEFT -> up = true;
		case KeyEvent.VK_D , KeyEvent.VK_S,
			 KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN ->  down = true;
		case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE -> enter = true;
		case KeyEvent.VK_ESCAPE, KeyEvent.VK_BACK_SPACE -> esc = true;
		default -> {}
		}
	}
}
