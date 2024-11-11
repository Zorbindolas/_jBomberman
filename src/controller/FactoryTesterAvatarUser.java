package controller;

import java.awt.Color;

import model.Account;
import model.AccountConcrete;
import model.GameModel;
import model.Heroes;
import view.AvatarAccount;
import view.DecoratedAccount;
/**
 * Factory for tester accounts creation.
 * It creates the Default Account (always present in the application).
 * Other accounts are testing purpose only.
 */
public class FactoryTesterAvatarUser {
	/**
	 * Add all tester accounts to Game Model's accounts
	 * at the start of the application.
	 */
	public static void addTesterAvatarsToModel() {
		Account currAccount = getAccountTesterAlfa();
		GameModel.getInstance().addAccount(currAccount);
		GameModel.getInstance().addAccount(getAccountTesterTheta());
		GameModel.getInstance().addAccount(getAccountTesterBeta());
		GameModel.getInstance().addAccount(getAccountTesterGamma());
		GameModel.getInstance().addAccount(getAccountTesterEpsilon());
		GameModel.getInstance().addAccount(getAccountTesterDelta());
		GameModel.getInstance().setCurrAccount(currAccount);
	}
	/**
	 * Create tester account Alfa
	 * @return a tester decorated account
	 */
	private static Account getAccountTesterAlfa() {
		Account alfaAccount = new AccountConcrete("Default",0);
		return new DecoratedAccount(alfaAccount, AvatarAccount.BOMBERMAN, Color.GREEN);
	}
	/**
	 * Create tester account Beta
	 * @return a tester decorated account
	 */
	private static Account getAccountTesterBeta() {
		Account betaAccount = new AccountConcrete("Beta",1);
		betaAccount.setBestScore(10000);
		betaAccount.setPlayTimeTotal(50*60);
		betaAccount.setPauseTimeTotal(10*60);
		betaAccount.setMatchesTotal(20);
		betaAccount.setVictoriesTotal(10);
		betaAccount.setFailuresTotal(9);
		betaAccount.setEscapesTotal(1);
		betaAccount.setCompletedStagesTotal(1);
		betaAccount.setBrokenHeartsTotal(10);
		betaAccount.setAttemptsTotal(30);
		betaAccount.incChosenPc(Heroes.MISS_DINAH_MIGHT);
		betaAccount.incChosenPc(Heroes.MISS_DINAH_MIGHT);
		return new DecoratedAccount(betaAccount, AvatarAccount.BALOON, Color.ORANGE);
	}
	/**
	 * Create tester account Gamma
	 * @return a tester decorated account
	 */
	private static Account getAccountTesterGamma() {
		Account gammaAccount = new AccountConcrete("Gamma",2);
		gammaAccount.setBestScore(30000);
		gammaAccount.setPlayTimeTotal(100*60);
		gammaAccount.setPauseTimeTotal(20*60);
		gammaAccount.setMatchesTotal(50);
		gammaAccount.setSpeedrunTime(783);
		gammaAccount.setVictoriesTotal(20);
		gammaAccount.setFailuresTotal(20);
		gammaAccount.setEscapesTotal(10);
		gammaAccount.setCompletedStagesTotal(3);
		gammaAccount.setBrokenHeartsTotal(30);
		gammaAccount.setAttemptsTotal(50);
		gammaAccount.incChosenPc(Heroes.MISS_DINAH_MIGHT);
		return new DecoratedAccount(gammaAccount, AvatarAccount.FLIPPER, Color.PINK);
	}
	/**
	 * Create tester account Theta
	 * @return a tester decorated account
	 */
	private static Account getAccountTesterTheta() {
		Account thetaAccount = new AccountConcrete("Theta",3);
		thetaAccount.setBestScore(20000);
		thetaAccount.setPlayTimeTotal(80*60);
		thetaAccount.setSpeedrunTime(800);
		thetaAccount.setPauseTimeTotal(50*60);
		thetaAccount.setMatchesTotal(10);
		thetaAccount.setVictoriesTotal(2);
		thetaAccount.setFailuresTotal(2);
		thetaAccount.setEscapesTotal(6);
		thetaAccount.setCompletedStagesTotal(0);
		thetaAccount.setBrokenHeartsTotal(10);
		thetaAccount.setAttemptsTotal(2);
		thetaAccount.incChosenPc(Heroes.MASKED_MAGICIAN);
		return new DecoratedAccount(thetaAccount, AvatarAccount.FUNCKY_GALLO, Color.YELLOW);
	}
	/**
	 * Create tester account Delta
	 * @return a tester decorated account
	 */
	private static Account getAccountTesterDelta() {
		Account deltaAccount = new AccountConcrete("Delta",4);
		deltaAccount.setBestScore(1500);
		deltaAccount.setPlayTimeTotal(40*60);
		deltaAccount.setPauseTimeTotal(30*60);
		deltaAccount.setSpeedrunTime(1000);
		deltaAccount.setMatchesTotal(9);
		deltaAccount.setVictoriesTotal(8);
		deltaAccount.setFailuresTotal(10);
		deltaAccount.setEscapesTotal(6);
		deltaAccount.setCompletedStagesTotal(0);
		deltaAccount.setBrokenHeartsTotal(20);
		deltaAccount.setAttemptsTotal(10);
		deltaAccount.incChosenPc(Heroes.BARON_BOMBAROLO);
		return new DecoratedAccount(deltaAccount, AvatarAccount.QUACK, Color.RED);
	}
	/**
	 * Create tester account Epsilon
	 * @return a tester decorated account
	 */
	private static Account getAccountTesterEpsilon() {
		Account epsilonAccount = new AccountConcrete("Epsilon",5);
		epsilonAccount.setBestScore(100);
		epsilonAccount.setPlayTimeTotal(80*60);
		epsilonAccount.setSpeedrunTime(372);
		epsilonAccount.setPauseTimeTotal(50*60);
		epsilonAccount.setMatchesTotal(10);
		epsilonAccount.setVictoriesTotal(2);
		epsilonAccount.setFailuresTotal(2);
		epsilonAccount.setEscapesTotal(6);
		epsilonAccount.setCompletedStagesTotal(0);
		epsilonAccount.setBrokenHeartsTotal(10);
		epsilonAccount.setAttemptsTotal(2);
		epsilonAccount.incChosenPc(Heroes.MECHA_BOMBERMAN);
		return new DecoratedAccount(epsilonAccount, AvatarAccount.SCHWIFTY, Color.RED);
	}


}
