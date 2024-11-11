package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.GameModel;
import view.DecoratedAccount;
import view.DynaSlaveAccountsManagerNested;
/**
 * Concrete Command (Command Pattern) provided by Java Swing Architecture.
 * It implements the performed action when the change button (in DynaSlaveAccountsManagerNested) is clicked.
 * It change the Game Model's current account with the one selected in the view that is equals to it.
 * At last, the method setCurrAccount by Game Model notify the update changing also the DynaSlaveAccount
 * (in which it is possible to see the current account's parameters).
 */
public class AccountSelectCurrentListener implements ActionListener {
	/**
	 * Reference to DynaSlaveAccountsManagerNested in which it located the change button.
	 */
	private DynaSlaveAccountsManagerNested managerNested;
	/**
	 * Constructor for this Command
	 * @param managerNested button's DynaSlaveAccountsManagerNested
	 */
	public AccountSelectCurrentListener(DynaSlaveAccountsManagerNested managerNested) {
		this.managerNested = managerNested;
	}
	/**
	 * Concrete implementation of this Command.
	 * It changes the Game Model's current account with the one that is equals to that selected in the view.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		DecoratedAccount da = (DecoratedAccount) managerNested.getSelectedAccount();
		GameModel.getInstance().setCurrAccount(da);
	}

}
