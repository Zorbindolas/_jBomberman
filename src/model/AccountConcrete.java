package model;

import java.io.Serializable;
/**
 * Concrete class account.
 */
public class AccountConcrete extends Account implements Serializable {

	/**
	 * Serialization  number
	 */
	private static final long serialVersionUID = 2750090826288622809L;

	/**
	 * Standard constructor of an account.
	 * The identifier is establish by current time
	 * @param nickname name chosen by the user of this account
	 */
	public AccountConcrete(String nickname) {
		super(nickname);
	}
	
	/**
	 * Special constructor for an account. It's used by the developer for testing reasons.
	 * @param nickname name chosen for this account
	 * @param birthday identifier value forced by the developer for testing purposes
	 */
	public AccountConcrete(String nickname, long birthday) {
		super(nickname, birthday);
	}

}
