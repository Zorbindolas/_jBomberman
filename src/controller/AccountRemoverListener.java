package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.GameModel;
import view.DecoratedAccount;
import view.DynaSlaveAccountsManagerNested;
/**
 * Concrete Command (Command Pattern) provided by Java Swing Architecture.
 * It implements the performed action when the remove button (in DynaSlaveAccountsManagerNested) is clicked.
 * It removes the selected account directly from the GameModel's accounts.
 * The Game Model removed account is the same to the managerNested selected account by equals method . 
 * At last, the method removeAccount by Game Model notify the DynaSlaveAccountsManagerNested 
 * to update the table represented in that object.
 */
public class AccountRemoverListener implements ActionListener {

	/**
	 * Reference to DynaSlaveAccountsManagerNested in which it located the remove button.
	 */
	private DynaSlaveAccountsManagerNested managerNested;
	/**
	 * Constructor for this Command
	 * @param managerNested button's DynaSlaveAccountsManagerNested
	 */
	public AccountRemoverListener(DynaSlaveAccountsManagerNested managerNested) {
		this.managerNested = managerNested;
	}
	/**
	 * Concrete implementation of this Command.
	 * It removes the account equals to that selected in the view from the Game Model.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		DecoratedAccount da = (DecoratedAccount) managerNested.getSelectedAccount();
		GameModel.getInstance().removeAccount(da);
	}

}
