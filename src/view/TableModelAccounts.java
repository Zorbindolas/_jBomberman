package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Account;
/**
 * Personalized AbstractTableModel as model for JTable in the DynaSlaveAccountsManagerNested.
 * This model represents fields of interest for all accounts.
 */
public class TableModelAccounts extends AbstractTableModel {
	/**
	 * Serialized key for Serialization.
	 */
	private static final long serialVersionUID = -1639041417909127045L;
	/**
	 * DynaFrame reference
	 */
	protected DynaFrame dynaFrame;
	/**
	 * Saved Accounts List to refer for this model
	 */
	protected List<Account> accountsList = new ArrayList<>();
	/**
	 * Columns names for the JTable
	 */
	protected String[] colNames = {
			"Selected","ID","Nickname","Level","Stage Completed","Win/Loss Ratio",
			"Best Score", "Speedrun Time", "Game Time Total","Pause Time Total","Matches",
			"Victories","Failures","Escapes","Broken Hearts","Attempts","Favorite PC"
	};
	/**
	 * Constructor. 
	 * Personalized AbstractTableModel as model for JTable in the DynaSlaveAccountsManagerNested.
	 * This model represents fields of interest for all accounts.
	 * @param dynaFrame Current DynaFrame reference
	 */
	public TableModelAccounts(DynaFrame dynaFrame) {
		this.dynaFrame = dynaFrame;
	}
	
	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}
	
	@Override
	public int getRowCount() {
		return dynaFrame.getAccounts().size();
	}

	@Override
	public int getColumnCount() {
		return 17; // Account fields
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DecoratedAccount account = (DecoratedAccount) accountsList.get(rowIndex);
		switch(columnIndex) {
		case 0 -> { 
			if(account.equals(dynaFrame.getCurrAccount())){
				return " > ";
			}; 
			return " ";
		}
		case 1 -> {return account.getIdentifier();}
		case 2 -> {return account.getNickname();}
		case 3 -> {return account.getLevel();}
		case 4 -> {return account.getCompletedStagesTotal();}
		case 5 -> {return account.getRatio();}
		case 6 -> {return account.getBestScore();}
		case 7 -> {return account.getSpeedrunTime();}
		case 8 -> {return account.getPlayTimeTotal();}
		case 9 -> {return account.getPauseTimeTotal();}
		case 10 -> {return account.getMatchesTotal();}
		case 11 -> {return account.getVictoriesTotal();}
		case 12 -> {return account.getFailuresTotal();}
		case 13 -> {return account.getEscapesTotal();}
		case 14 -> {return account.getBrokenHeartsTotal();}
		case 15 -> {return account.getAttemptsTotal();}
		case 16 -> {return account.getFavoritePC();}
		default -> {return null;}
		}
	}
	/**
	 * Save the latest view accounts list in this panel and update the JTable
	 */
	public void refreshListAccounts() {
		accountsList.clear();
		dynaFrame.getAccounts().forEach(
				x -> accountsList.add(x)
				);
		this.fireTableDataChanged();
	}
	/**
	 * Return the Account at the specify index
	 * @param i Account index
	 * @return the requested Account
	 */
	public Account getAccountAt(Integer i) {
		return accountsList.get(i);
	}

}
