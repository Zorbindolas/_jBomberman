package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import model.Dir;
/**
 * Personalized version of a KeyListener to manage inputs reception 
 * during a game session (running ThreadGame).
 */
public class KeyListenerGame implements KeyListener {
	/**
	 * This nested class encapsulates a direction.
	 * It isn't strictly necessary, but it can allow new functionalities in the future
	 * (as the number of times in which a button has been pressed).
	 */
	private class KeyMemo{ // Nested class
		/**
		 * The direction of this KeyMemo
		 */
		private Dir oneWayRoad;
		/**
		 * Private Constructor
		 * @param direction the direction of this KeyMemo
		 */
		KeyMemo(Dir direction){this.oneWayRoad = direction;}
		/**
		 * Getter of this' direction
		 * @return this' direction
		 */
		public Dir getMemo() {return oneWayRoad;}
//		public void press() {}
	}
	/**
	 * Instance for the Singleton Design Pattern
	 */
	private static KeyListenerGame instance;
	/**
	 * Encapsulated one-of-a-kind directions in this keyListener.
	 */
	private static KeyMemo up, down, left, right;
	/**
	 * On-off switch for a game condition
	 */
	private boolean action, trigger, pause, enter;
	private boolean victory;
	/**
	 * Register of the latest pressed input directions.
	 * It is used as a stack.
	 */
	private static List<KeyMemo> keyMemories = new ArrayList<>();
	/**
	 * Private constructor in which the KeyMemos are defined
	 */
	private KeyListenerGame() {
		up = new KeyMemo(Dir.UP);
		down = new KeyMemo(Dir.DOWN);
		left = new KeyMemo(Dir.LEFT);
		right = new KeyMemo(Dir.RIGHT);
	}
	/**
	 * singleton's instance getter
	 * @return this singleton's instance
	 */
	public static KeyListenerGame getInstance() {
		if(instance==null) instance = new KeyListenerGame();
		return instance;
	}
	
	// Getters
	/**
	 * Getter of action condition.
	 * KeyListenerGame's action can provoke a bomb release.
	 * This getter exhausts its signal in order to conform
	 * the Controller to User's digital experience.
	 * @return conditional signal of action
	 */
	public boolean isPerformAction() {
		if(action) {
			action = false;
			return true;
		}
		return false;
	}
	/**
	 * Getter of trigger condition.
	 * KeyListenerGame's trigger can provoke a bomb explosion
	 * only if ReleaseBehavior is set to BombRemote.
	 * This getter exhausts its signal in order to conform
	 * the Controller to User's digital experience.
	 * @return conditional signal of trigger
	 */
	public boolean isTriggerAction() {
		if(trigger) {
			trigger = false;
			return true;
		}
		return false;
	}
	/**
	 * Getter of pause condition.
	 * KeyListenerGame's pause changes GameState from StatePlaying to StatePaused and vice versa.
	 * This getter exhausts its signal in order to conform
	 * the Controller to User's digital experience.
	 * @return conditional signal of pause
	 */
	public boolean isPaused() {
		if(pause) {
			pause = false;
			return true;
		}
		return false;
	}
	/**
	 * Getter of enter condition.
	 * KeyListenerGame's enter activates the commands of options selection
	 * if the current GameState owns it.
	 * This getter exhausts its signal in order to conform
	 * the Controller to User's digital experience.
	 * @return conditional signal of enter
	 */
	public boolean isEnter() {
		if(enter) {
			enter= false;
			return true;
		}
		return false;
	}
	
	public boolean isVictory() {
		if(victory) {
			victory = false;
			return true;
		}
		return false;
	}
	
	//Resetters
	/**
	 * This resetter forces action to false.
	 */
	public void resetPerformAction() {action=false;}
	/**
	 * This resetter forces pause to false.
	 */
	public void resetPause() {pause=false;}
	/**
	 * This resetter forces enter to false.
	 */
	public void resetEnter() {enter = false;}
	/**
	 * Setter of pause.
	 * @param pause new pause value
	 */
	public void setPause(boolean pause) {this.pause = pause;}
	/**
	 * Check if there is at least one direction inside the register.
	 * @return true if register isn't empty.
	 */
	public boolean  containsDir(){
		return ! keyMemories.isEmpty();
	}
	/**
	 * Get the latest direction inserted into the register.
	 * If many directions are contained only the latest one is considered.
	 * @return most recent direction
	 */
	public Dir getDir() {
		try {
			KeyMemo k;
			if(containsDir()) {
				k = keyMemories.get(0);
				return k.getMemo();
			}

		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
		return Dir.NONE;

	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode(); // returns the integer key code associated with the key in this event
		
		switch(keyCode) {
		case KeyEvent.VK_W , KeyEvent.VK_UP -> 
			{ if(!keyMemories.contains(up)) keyMemories.add(0,up); } // up.press();
		case KeyEvent.VK_D , KeyEvent.VK_RIGHT -> 
			{ if(!keyMemories.contains(right)) keyMemories.add(0,right); } // right.press();
		case KeyEvent.VK_S , KeyEvent.VK_DOWN -> 
			{ if(!keyMemories.contains(down)) keyMemories.add(0,down); } // down.press();
		case KeyEvent.VK_A , KeyEvent.VK_LEFT -> 
			{ if(!keyMemories.contains(left)) keyMemories.add(0,left); } // left.press();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int keyCode = e.getKeyCode(); // returns the integer keycode associated with the key in this event
		
		switch(keyCode) {
		case KeyEvent.VK_W , KeyEvent.VK_UP -> { keyMemories.remove(up); }//  up.reset();
		case KeyEvent.VK_D , KeyEvent.VK_RIGHT -> { keyMemories.remove(right); } // right.reset();
		case KeyEvent.VK_S , KeyEvent.VK_DOWN -> { keyMemories.remove(down); }// down.reset();
		case KeyEvent.VK_A , KeyEvent.VK_LEFT -> { keyMemories.remove(left); } // left.reset();
		case KeyEvent.VK_B , KeyEvent.VK_SPACE -> action = true;
		case KeyEvent.VK_T -> trigger = true;
		case KeyEvent.VK_ENTER -> enter = true;
		case KeyEvent.VK_P -> pause = true;
		// EXPOSE FUNC
		case KeyEvent.VK_K -> victory = true;
		}
	}

}
	
	
//	public List<KeyMemo> onlyTrue() {
//	return keyMemories.stream()
//			.filter(x->x.getBool()==true)
//			.collect(Collectors.toList());
//}

//public Dir whatsUp() {
//	return keyMemories.stream()
//			.filter(x->x.getBool()==true)
//			.sorted(Comparator.comparing(x->((KeyMemo) x).getFingersRaised()).reversed())
//			.findFirst()
//			.orElse(up)
//			.getDir();
//}
