package model;

import java.util.Objects;
/**
 * Class that carries information from a newly destroyed non-player character or collected power Up
 * to notify the Game Model of the score to be added to the current score value.
 */
public abstract class Gravedigger {
	/**
	 * Static key generator 
	 */
	private static long globalID = 0;
	/**
	 * Unique identifier for this Gravedigger
	 */
	private long id;
	/**
	 * The state of this object is represents with a number
	 */
	private int tempo;
	/**
	 * Score carried by this gravedigger
	 */
	private long score;
	/**
	 * Precalculated string of score
	 */
	private String scoreString;
	/**
	 * Panel coordinates of this gravedigger
	 */
	private int yPanel, xPanel;
	
	/**
	 * Gravedigger Constructor
	 * @param score number of score
	 * @param yPanel starting y panel coordinate
	 * @param xPanel starting x panel coordinate
	 */
	protected Gravedigger(long score, int yPanel, int xPanel) {
		this.score = score;
		this.scoreString = score+"";
		this.yPanel = yPanel;
		this.xPanel = xPanel;
		this.id = globalID;
		globalID++;
	}
	/**
	 * Factory methods with anonymous class
	 * @param score number of score
	 * @param yPanel starting y panel coordinate
	 * @param xPanel starting x panel coordinate
	 * @return a concrete gravedigger
	 */
	public static Gravedigger setTomb(long score, int yPanel, int xPanel) {
		return new Gravedigger(score, yPanel, xPanel) {};
	}
	/**
	 * Getter od identifier
	 * @return this identifier
	 */
	public long getId() {return id;}
	/**
	 * Getter of tempo
	 * @return this tempo
	 */
	public int getTempo() {return tempo;}
	/**
	 * Getter of score
	 * @return this score
	 */
	public long getScore() {return score;}
	/**
	 * Getter of score's string
	 * @return this score's string
	 */
	public String getScoreString() {return scoreString;}
	/**
	 * Getter of y panel coordinate
	 * @return y panel coordinate
	 */
	public int getYPanel() {return yPanel;}
	/**
	 * Getter of x panel coordinate
	 * @return x panel coordinate
	 */
	public int getXPanel() {return xPanel;}
	/**
	 * Setter for x panel coordinate
	 * @param xPanel new x panel coordinate
	 */
	public void setXPanel(int xPanel) {this.xPanel = xPanel;}
	/**
	 * Setter of y panel coordinate
	 * @param yPanel new y panel coordinate
	 */
	public void setYPanel(int yPanel) {this.yPanel = yPanel;}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gravedigger other = (Gravedigger) obj;
		return id == other.getId();
	}
	

	
}
