package view;

import java.io.Serializable;
import java.util.Map;

import model.Account;
import model.Heroes;
/**
 * Decorator Pattern to decorate an Account from Model.
 */
public abstract class DecoratorAccount extends Account implements Serializable {
	/**
	 * serialization number
	 */
	private static final long serialVersionUID = 6834741802350712218L;
	/**
	 * Account to wrap in this Decorator
	 */
	protected Account component;
	/**
	 * Constructor
	 * @param component Account to decorate
	 */
	public DecoratorAccount(Account component) {
		super(component.getNickname(), component.getIdentifier());
	}
	// Getters
	@Override
	public long getIdentifier() {return component.getIdentifier();}
	@Override
	public String getNickname() {return component.getNickname();}
	@Override
	public int getLevel() {return component.getLevel();}
	@Override
	public long getBestScore() {return component.getBestScore();}
	@Override
	public int getPlayTimeTotal() {return component.getPlayTimeTotal();}
	@Override
	public int getPauseTimeTotal() {return component.getPauseTimeTotal();}
	@Override
	public String getPlayTimeString() {return component.getPlayTimeString();}
	@Override
	public String getPauseTimeString() {return component.getPauseTimeString();}
	@Override
	public int getMatchesTotal() {return component.getMatchesTotal();}
	@Override
	public int getVictoriesTotal() {return component.getVictoriesTotal();}
	@Override
	public int getFailuresTotal() {return component.getFailuresTotal();}
	@Override
	public int getEscapesTotal() {return component.getEscapesTotal();}
	@Override
	public int getCompletedStagesTotal() {return component.getCompletedStagesTotal();}
	@Override
	public int getBrokenHeartsTotal() {return component.getBrokenHeartsTotal();}
	@Override
	public int getAttemptsTotal() {return component.getAttemptsTotal();}
	@Override
	public Map<Heroes, Integer> getChosenPCsMap() {return component.getChosenPCsMap();}
	@Override
	public Heroes getFavoritePC(){return component.getFavoritePC();}
	@Override
	public double getRatio() {return component.getRatio();}
	@Override
	public int getSpeedrunTime() {return component.getSpeedrunTime();}
	
	// Setters
	@Override
	public void setIdentifier(long birthday) {component.setIdentifier(birthday);}
	@Override
	public void setNickname(String nickname) {component.setNickname(nickname);}
	@Override
	public void setBestScore(long scoreTotal) {component.setBestScore(scoreTotal);}
	@Override
	public void setPlayTimeTotal(int playTimeTotal) {component.setPlayTimeTotal(playTimeTotal);}
	@Override
	public void setPauseTimeTotal(int pauseTimeTotal) {component.setPauseTimeTotal(pauseTimeTotal);}
	@Override
	public void setMatchesTotal(int matchesTotal) {component.setMatchesTotal(matchesTotal);}
	@Override
	public void setVictoriesTotal(int victoriesTotal) {component.setVictoriesTotal(victoriesTotal);}
	@Override
	public void setFailuresTotal(int failuresTotal) {component.setFailuresTotal(failuresTotal);}
	@Override
	public void setEscapesTotal(int escapesTotal) {component.setEscapesTotal(escapesTotal);}
	@Override
	public void setCompletedStagesTotal(int completedStagesTotal) {component.setCompletedStagesTotal(completedStagesTotal);}
	@Override
	public void setBrokenHeartsTotal(int brokenHeartsTotal) {component.setBrokenHeartsTotal(brokenHeartsTotal);}
	@Override
	public void setAttemptsTotal(int attemptsTotal) {component.setAttemptsTotal(attemptsTotal);}
	@Override
	public void setChosenPCsMap(Map<Heroes, Integer> chosenPCsMap) {
		component.setChosenPCsMap(chosenPCsMap);
	}
	@Override
	public void setSpeedrunTime(int speedrunTime) {component.setSpeedrunTime(speedrunTime);}
	
	// Incrementers
	@Override
	public void updateBestScore(long bestScore) {component.updateBestScore(bestScore);}
	@Override
	public void updateSpeedrunTime(int speedrunTime) {component.updateSpeedrunTime(speedrunTime);}
	@Override
	public void incPlayTime() {component.incPlayTime();}
	@Override
	public void incPauseTime() {component.incPauseTime();}
	@Override
	public void incMatchesTotal() {component.incMatchesTotal();}
	@Override
	public void incVictoriesTotal() {component.incVictoriesTotal();}
	@Override
	public void incFailuresTotal() {component.incFailuresTotal();}
	@Override
	public void incEscapesTotal() {component.incEscapesTotal();}
	@Override
	public void incCompletedStagesTotal() {component.incCompletedStagesTotal();}
	@Override
	public void incBrokenHearts() {component.incBrokenHearts();}
	@Override
	public void incAttemptsTotal() {component.incAttemptsTotal();}
	@Override
	public void incChosenPc(Heroes hero) {component.incChosenPc(hero);}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DecoratedAccount other = (DecoratedAccount) obj;
		return component.equals(other.getComponent());
	}
	
	@Override
	public int hashCode() {return component.hashCode();}
	@Override
	public int compareTo(Account o) {return component.compareTo(o);}
	@Override
	public String toString() {return component.toString();}
	/**
	 * Getter of its wrapped decorated Account component
	 * @return its component
	 */
	public Account getComponent() {return component;}

}
