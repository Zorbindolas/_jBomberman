package controller;

import model.GameModel;
import view.MyJBombermanFormat;
/**
 * Basic functionalities that a concrete ThreadGame must have.
 * The Game loop is realized with Delta Loop modality.
 * Every invocation of flowOfTime updates a delta variable by a fraction of a second.
 * When delta is greater than or equal to 1 that means a bar has been completed.
 * In that case delta will be reset to zero.  By definition, bar * FPS = 1 second. 
 * So the duration of a bar is determined by the desired FPS.
 * In that way an appropriate number of bars occurs to give the User the illusion of fluid animations
 * and a responsive and enjoyable reception of inputs.
 * (the images update happens every multiple of bars -eg.REFRESH_STANDARD, 5-).
 */ 
public abstract class ThreadDelta implements MyJBombermanFormat, Runnable {
	/**
	 * 1 second in nanoseconds
	 */
	protected final static long aSecondInNanos = 1_000_000_000;
	/**
	 * 1 tenth of a second in nanoseconds
	 */
	protected final static long tenthSecond = 100_000_000; 
	/**
	 * Nanoseconds (of life) of a frame. 
	 * This is the smallest interval of time elapsed: the bar of the game loop.
	 */
	protected final static double bar = aSecondInNanos / FPS; 
	/**
	 * Variable in which registered time (in nanoseconds) is stored during the previous flowOfTime invocation.
	 */
	protected long before = System.nanoTime(); 
	/**
	 * Variable in which registered time (in nanoseconds) is stored during the current flowOfTime invocation.
	 */
	protected long now;	// Initialized to 0
	/**
	 * Time fraction calculated into a flowOfTime invocation.
	 */
	protected double timeFraction; // Initialized to 0.0
	/**
	 * Nanoseconds that have passed since the last flowOfTime invocation.
	 */
	protected double carpeDiem;
	/**
	 * Sum of the fractions of time elapsed
	 */
	protected double delta;
	/**
	 * Completed bar counter. It's used to refresh animation' sprites.
	 */
	protected double clockNRoll;
	/**
	 * A counter inside the delta loop. Its measure is in nanoseconds.
	 */
	protected long timerGeneral;
	/**
	 * A counter outside the delta loop. Its measures is arbitrary decided in its concrete thread game.
	 */
	protected long timerGame; 
	/**
	 * Conditional variable from inputs Listener.
	 */
	protected boolean up, down, enter, esc; // Automatically initialized to false.
	/**
	 * Constructor of a Thread Delta.
	 */
	public ThreadDelta() {
//		up = down = enter = esc = false;
	}
	// Getters
	/**
	 * Getter of up
	 * @return current up value
	 */
	public boolean isUp() {return up;}
	/**
	 * Getter of down
	 * @return current down value
	 */
	public boolean isDown() {return down;}
	/**
	 * Getter of enter
	 * @return current enter value
	 */
	public boolean isEnter() {return enter;}
	/**
	 * Getter of esc
	 * @return current esc value
	 */
	public boolean isEsc() {return esc;}
	/**
	 * Getter of completed bars counter (clockNRoll)
	 * @return current completed bars counter value
	 */
	public double getClockNRoll() {return clockNRoll;}
	/**
	 * Getter of timerGeneral
	 * @return current timerGeneral value
	 */
	public long getTimerGeneral() {return timerGeneral;}
	/**
	 * Getter of timerGame
	 * @return current timerGame value
	 */
	public long getTimerGame() {return timerGame;}
	
	// Setters
	/**
	 * Setter of up
	 * @param up new up value
	 */
	public void setUp(boolean up) {this.up=up;}
	/**
	 * Setter of down
	 * @param down new down value
	 */
	public void setDown(boolean down) {this.down=down;}
	/**
	 * Setter of enter
	 * @param enter new enter value
	 */
	public void setEnter(boolean enter) {this.enter=enter;}
	/**
	 * Setter of esc
	 * @param esc new esc value
	 */
	public void setEsc(boolean esc) {this.esc=esc;}
	/**
	 * Setter of clockNRoll
	 * @param clockNRoll new clockNRoll value
	 */
	public void setClockNRoll(double clockNRoll) {this.clockNRoll = clockNRoll;}
	
	// Utilities 
	/**
	 * The inputs registered in this' up|down variables
	 * modify the GameModel's pointer (which will be retransmitted)
	 * @param modulus number of options to scroll
	 */
	protected void checkUpOrDown(int modulus) {
		if(up) {
			GameModel.getInstance().decPointer(modulus);
			up = false;
		} else if(down) {
			GameModel.getInstance().incPointer(modulus);
			down = false;
		} 
	}
	/**
	 * The inputs registered in this' enter|esc variables
	 * invoke the corresponded GameModel's method (a boolean will be transmitted)
	 */
	protected void checkEnterOrEsc() {
		if(enter) {
			GameModel.getInstance().selected();
			enter = false;
		} else if(esc) {
			GameModel.getInstance().deselected();
			esc = false;
		} 
	}
	/**
	 * Run method implemented with Template Method Design Pattern
	 */
	@Override
	public void run() {
		preparation();
		while(loopCondition()) {
			flowOfTime();
			if(delta>=1) {
				coreFunctions();
				delta--;
			}
		}
	}
	/**
	 * Rearrange the variables correctly for a delta loop session to begin
	 */
	protected abstract void preparation();
	/**
	 * the conditions for the loop to continue
	 * @return true if loop can continue
	 */
	protected abstract boolean loopCondition();
	/**
	 * This method marks the passage of time.
	 * When the delta exceeds the unit value, 
	 * a unit of time must be considered completed 
	 * (with a value of one bar each times).
	 */
	protected void flowOfTime() {
		
		now = System.nanoTime();
		
		carpeDiem = now - before;
		
		timeFraction = carpeDiem/bar;
		
		delta += timeFraction;
		
		clockNRoll += timeFraction;
		
		timerGeneral += carpeDiem; // incrementing Timer General

		before = now;
		
	}
	/**
	 * Specific functions for this thread to repeat into the game loop
	 */
	protected abstract void coreFunctions();
	
			
}
