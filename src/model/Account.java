package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * This class contains all the information related to an account created by the user.
 * Objects of this type are serialized because serialization allows saving or importing operations by the user.
 */
public abstract class Account implements Comparable<Account>, Serializable {
	/**
	 * Number for serialization runtime check 
	 */
	private static final long serialVersionUID = 4337015674464305540L;
	/**
	 * Key used for equals and hash code. It is define by current time in which account is created.
	 */
	private long identifier; // the key is the birthday
	/**
	 * Name choosen by the player
	 */
	private String nickname;
	/**
	 * best performance score of the player of this account
	 */
	private long bestScore;
	/**
	 * Total playing time in seconds from every session of game
	 */
	private int playTimeTotal; // in seconds
	/**
	 * Shortest time to complete a stage
	 */
	private int speedrunTime;
	/**
	 * Total pause time in seconds from every session of game
	 */
	private int pauseTimeTotal; // in seconds
	/**
	 * Total number of played matches 
	 */
	private int matchesTotal;
	/**
	 * Total number of earned victories
	 */
	private int victoriesTotal;
	/**
	 * Total number of accumulated failures
	 */
	private int failuresTotal;
	/**
	 * Total number of used escapes 
	 */
	private int escapesTotal;
	/**
	 * Total number of completed stage by this account
	 */
	private int completedStagesTotal;
	/**
	 * Total number of hearts lost
	 */
	private int brokenHeartsTotal;
	/**
	 * Total number of hearts lost
	 */
	private int attemptsTotal;
	/**
	 * Map that associates the playable characters with the number in which they were chosen by player's account
	 */
	private Map<Heroes,Integer> chosenPCsMap = new HashMap<>();
	
	/**
	 * Constructor used only by the developer to show testing accounts.
	 * @param nickname name chosen for the account by the user
	 * @param identifier forced birthday ID
	 */
	public Account(String nickname, long identifier) { // only for programmer
		this.nickname = nickname;
		this.identifier = identifier;
		this.speedrunTime = Integer.MAX_VALUE;
	}
	
	/**
	 * Constructor of an account created by User. 
	 * @param nickname name chosen for the account by the user
	 */
	public Account(String nickname) {
		this.nickname = nickname;
		this.identifier = System.currentTimeMillis();
	}
	
	// Getters
	/**
	 * Getter for identifier
	 * @return identifier of this account
	 */
	public long getIdentifier() {return identifier;}
	/**
	 * Getter for nickname
	 * @return nickname of this account
	 */
	public String getNickname() {return nickname;}
	/**
	 * Calculate the level of the account to show to the user
	 * @return level value
	 */
	public int getLevel() {
		return (int) (getRatio() * bestScore/1000 * Math.log(Math.pow(2,completedStagesTotal+1)));
	}
	/**
	 * Getter for best score
	 * @return best score associated to this account
	 */
	public long getBestScore() {return bestScore;}
	/**
	 * Getter for PlayTimeTotal
	 * @return play time total associated to this account
	 */
	public int getPlayTimeTotal() {return playTimeTotal;}
	/**
	 * Getter for Pause time total
	 * @return pause time total associated to this account
	 */
	public int getPauseTimeTotal() {return pauseTimeTotal;}
	/**
	 * Formatted string by hours / minute / seconds
	 * of the seconds passed
	 * @param seconds seconds to format
	 * @return formatted string 
	 */
	private String getFormatTime(int seconds) {
		int hours = seconds / (60*60);
		seconds %= (60*60);
		int minutes = seconds / 60;
		seconds %= 60;
		return hours+"h "+minutes+"' "+seconds+"'' ";
	}
	/**
	 * Format playTime in a string in hours / minutes / seconds
	 * @return formatted string of play time
	 */
	public String getPlayTimeString() {
		return getFormatTime(playTimeTotal);
	}
	/**
	 * Format pauseTime in a string in hours / minutes / seconds
	 * @return formatted string of pause time
	 */
	public String getPauseTimeString() {
		return getFormatTime(pauseTimeTotal);
	}
	/**
	 * Getter of matches total
	 * @return matches total
	 */
	public int getMatchesTotal() {return matchesTotal;}
	/**
	 * Getter of victories total
	 * @return victories total
	 */
	public int getVictoriesTotal() {return victoriesTotal;}
	/**
	 * Getter of failures total
	 * @return failures total
	 */
	public int getFailuresTotal() {return failuresTotal;}
	/**
	 * Getter of escapes total
	 * @return escapes total
	 */
	public int getEscapesTotal() {return escapesTotal;}
	/**
	 * Getter of completed stages total
	 * @return completed stages total
	 */
	public int getCompletedStagesTotal() {return completedStagesTotal;}
	/**
	 * Getter of hearts lost total by this account
	 * @return hearts lost total
	 */
	public int getBrokenHeartsTotal() {return brokenHeartsTotal;}
	/**
	 * Getter of attempts total
	 * @return attempts total
	 */
	public int getAttemptsTotal() {return attemptsTotal;}
	/**
	 * Getter of the map of Chosen PC
	 * @return map of Chosen PC
	 */
	public Map<Heroes, Integer> getChosenPCsMap() {return chosenPCsMap;}
	/**
	 * Getter of the PC used the most
	 * @return PC character used more times
	 */
	public Heroes getFavoritePC(){
		Heroes hero;
		hero = Heroes.WHITE_BOMBERMAN;
		try {
			hero = chosenPCsMap.entrySet().stream()
					.max(Map.Entry.comparingByValue())
					.get().getKey();
		}catch(NoSuchElementException e) {
			
		}
		return hero;
	}
	/**
	 * Ratio of victories/failures
	 * @return the ratio of this account
	 */
	public double getRatio() {
		if(victoriesTotal==0) return 0.0;
		if(failuresTotal==0) return 1.0;
		return (double)victoriesTotal/(double)failuresTotal;
	}
	/**
	 * Getter of speed run time 
	 * @return speed run time
	 */
	public int getSpeedrunTime() {return speedrunTime;}
	
	// Setters
	/**
	 * Setter of identifier
	 * @param birthday new identifier
	 */
	public void setIdentifier(long birthday) {this.identifier = birthday;}
	/**
	 * Setter of nickname
	 * @param nickname new nickname
	 */
	public void setNickname(String nickname) {this.nickname = nickname;}
	/**
	 * Setter of best score
	 * @param bestScore new best score
	 */
	public void setBestScore(long bestScore) {this.bestScore = bestScore;}
	/**
	 * Setter of play time total
	 * @param playTimeTotal new play time total
	 */
	public void setPlayTimeTotal(int playTimeTotal) {this.playTimeTotal = playTimeTotal;}
	/**
	 * Setter of pause time total
	 * @param pauseTimeTotal new pause time total
	 */
	public void setPauseTimeTotal(int pauseTimeTotal) {this.pauseTimeTotal = pauseTimeTotal;}
	/**
	 * Setter of matches total
	 * @param matchesTotal new matches total
	 */
	public void setMatchesTotal(int matchesTotal) {this.matchesTotal = matchesTotal;}
	/**
	 * Setter of victoriesTotal
	 * @param victoriesTotal new victoriesTotal
	 */
	public void setVictoriesTotal(int victoriesTotal) {this.victoriesTotal = victoriesTotal;}
	/**
	 * Setter of failuresTotal
	 * @param failuresTotal new failuresTotal
	 */
	public void setFailuresTotal(int failuresTotal) {this.failuresTotal = failuresTotal;}
	/**
	 * Setter of escapesTotal
	 * @param escapesTotal new escapesTotal
	 */
	public void setEscapesTotal(int escapesTotal) {this.escapesTotal = escapesTotal;}
	/**
	 * Setter of completedStagesTotal
	 * @param completedStagesTotal new completedStagesTotal
	 */
	public void setCompletedStagesTotal(int completedStagesTotal) {this.completedStagesTotal = completedStagesTotal;}
	/**
	 * Setter of brokenHeartsTotal
	 * @param brokenHeartsTotal new brokenHeartsTotal
	 */
	public void setBrokenHeartsTotal(int brokenHeartsTotal) {this.brokenHeartsTotal = brokenHeartsTotal;}
	/**
	 * Setter of attemptsTotal
	 * @param attemptsTotal new attemptsTotal
	 */
	public void setAttemptsTotal(int attemptsTotal) {this.attemptsTotal = attemptsTotal;}
	/**
	 * Setter of chosenPCsMap
	 * @param chosenPCsMap new chosenPCsMap
	 */
	public void setChosenPCsMap(Map<Heroes, Integer> chosenPCsMap) {this.chosenPCsMap = chosenPCsMap;}
	/**
	 * Setter of speedrunTime
	 * @param speedrunTime new speedrunTime
	 */
	public void setSpeedrunTime(int speedrunTime) {this.speedrunTime = speedrunTime;}
	
	// Incrementers
	/**
	 * Update best score with the highest value between this.bestScore and the new one
	 * @param bestScore bestScore to compare with the actual one
	 */
	public void updateBestScore(long bestScore) {
		if(bestScore>this.bestScore) {
			this.bestScore = bestScore;
		}
	}
	/**
	 * Update speed run time with the lowest value between this.speedrunTime and the new one
	 * @param speedrunTime speedrunTime to compare with the actual one
	 */
	public void updateSpeedrunTime(int speedrunTime) {
		if(speedrunTime<this.speedrunTime) {
			this.speedrunTime = speedrunTime;
		}
	}
	/**
	 * increases this character's usage value by one
	 * @param hero Chosen hero for this game run
	 */
	public void incChosenPc(Heroes hero) {
		Integer value = chosenPCsMap.getOrDefault(hero, 0);
		value++;
		chosenPCsMap.put(hero, value);
	}
	// Incrementers by one point
	/**
	 * Increment playTime by one
	 */
	public void incPlayTime() {playTimeTotal++;}
	/**
	 * Increment pauseTime by one
	 */
	public void incPauseTime() {pauseTimeTotal++;}
	/**
	 * Increment matchesTotal by one
	 */
	public void incMatchesTotal() {matchesTotal++;}
	/**
	 * Increment victoriesTotal by one
	 */
	public void incVictoriesTotal() {victoriesTotal++;}
	/**
	 * Increment failuresTotal by one
	 */
	public void incFailuresTotal() {failuresTotal++;}
	/**
	 * Increment escapesTotal by one
	 */
	public void incEscapesTotal() {escapesTotal++;}
	/**
	 * Increment completedStagesTotal by one
	 */
	public void incCompletedStagesTotal() {completedStagesTotal++;}
	/**
	 * Increment brokenHearts by one
	 */
	public void incBrokenHearts() {brokenHeartsTotal++;}
	/**
	 * Increment attemptsTotal by one
	 */
	public void incAttemptsTotal() {attemptsTotal++;}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return identifier == other.getIdentifier()
				&& nickname.equals(other.getNickname());
	}

	@Override
	public int hashCode() {
        final int prime = 31;
        return prime * (
        		(int)(identifier ^ (identifier >>> 32)) /*  >>> unsigned right bit-shift operator :
        		it's birthday divides 2 to the power of 32, with MSB to 0 if necessary. see Josh Bloch's Effective Java - 8*/
        		+
        		nickname.hashCode()
        		);
	}
	
	@Override
	public int compareTo(Account o) {
		return (int)(identifier - o.getIdentifier()); // from older to newer
	}
	
	@Override
	public String toString() {
		return nickname +"'s account -Lv."+getLevel();
	}
	
	/**
	 * Reset methods for an account used for default account by the developer 
	 * in import serialization context.
	 */
	public void reset() {
		this.bestScore = 0;
		this.playTimeTotal = 0;
		this.pauseTimeTotal = 0;
		this.matchesTotal = 0;
		this.victoriesTotal = 0;
		this.failuresTotal = 0;
		this.escapesTotal = 0;
		this.completedStagesTotal = 0;
		this.brokenHeartsTotal = 0;
		this.attemptsTotal = 0;
		this.chosenPCsMap = new HashMap<>();
	}

}
