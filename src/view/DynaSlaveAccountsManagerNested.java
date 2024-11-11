package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.AccountRemoverListener;
import controller.AccountSelectCurrentListener;
import model.Account;
/**
 * The nested panel in which the table of all accounts is shown.
 */
public class DynaSlaveAccountsManagerNested extends DynaJPanelAccountNested
implements TableRefresher {
	/**
	 * Serialized key for Serialization.
	 */
	private static final long serialVersionUID = -48289410311170564L;
	/**
	 * The current JTable
	 */
	private JTable table;
	/**
	 * The JTable model
	 */
	private TableModelAccounts tableModelAccounts;
	/**
	 * The selected Account from the table
	 */
	private Account selectedAccount;
	/**
	 * Constructor.
	 * The nested panel in which the table of all accounts is shown.
	 * @param dsc DynaSlaveCard in which this panel is nested
	 */
	public DynaSlaveAccountsManagerNested(DynaSlaveCard dsc) {
		super(dsc);
		
		this.setBackground(Color.BLUE);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		tableModelAccounts = new TableModelAccounts(
				dsc.getMaster().getFrame()
				);
		
		table = new JTable(tableModelAccounts);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Integer index = table.getSelectedRow();
				selectedAccount = tableModelAccounts.getAccountAt(index);;
			}
		});
		this.add(new JScrollPane(table));
		
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		
		JButton changeButton = new JButton();
		changeButton.addActionListener(new AccountSelectCurrentListener(this));
		changeButton.setText("SET AS CURRENT ACCOUNT");
		changeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		changeButton.setBackground(Color.GREEN);
		this.add(changeButton);
		
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		
		JButton removeButton = new JButton();
		removeButton.addActionListener(new AccountRemoverListener(this));
		removeButton.setText("REMOVE THIS ACCOUNT");
		removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		removeButton.setBackground(Color.RED);
		this.add(removeButton);
		
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		
	}
	@Override
	public void refreshTable() {
		tableModelAccounts.refreshListAccounts();
	}
	/**
	 * Getter of the selected Account
	 * @return the selected Account
	 */
	public Account getSelectedAccount() {return selectedAccount;}

}
