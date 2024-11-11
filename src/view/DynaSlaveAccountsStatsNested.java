package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.AccountFilter;
import model.AccountFilter.Filters;
/**
 * The nested panel in which filtered accounts table is shown.
 * Accounts are filtered by User selected statistic operation.
 */
public class DynaSlaveAccountsStatsNested extends DynaJPanelAccountNested 
implements TableRefresher {
	/**
	 * Serialized key for Serialization.
	 */
	private static final long serialVersionUID = 6765805173214547687L;
	/**
	 * Current JTable
	 */
	private JTable table;
	/**
	 * Current JTable Model
	 */
	private TableModelAccounts tableModelAccounts;
	/**
	 * Label with "SELECT FILTER FUNCTION" text
	 */
	private JLabel filterLabel;
	/**
	 * JComboBox to select the current filter operation
	 */
	private JComboBox<Filters> filterMenu = new JComboBox<Filters>(Filters.values());
	/**
	 * Label with text derived from a filter function.
	 */
	private JLabel informationLabel, informationLabelValue;
	/**
	 * Constructor.
	 * The nested panel in which filtered accounts table is shown.
	 * Accounts are filtered by User selected statistic operation.
	 * @param dsc DynaSlaveCard in which this panel is nested.
	 */
	public DynaSlaveAccountsStatsNested(DynaSlaveCard dsc) {
		super(dsc);
		
		this.setBackground(Color.BLUE);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		tableModelAccounts = new TableModelAccounts(dsc.getMaster().getFrame()) {

			private static final long serialVersionUID = 5241771962347189125L;
			
			@Override
			public int getRowCount() {
				return accountsList.size();
			}
			
			@Override
			public void refreshListAccounts() {
				accountsList.clear();
				dynaFrame.getAccounts().forEach(
						x -> accountsList.add(x)
						);
				accountsList = AccountFilter.filtering(accountsList,getCurrFilter());
				informationLabelValue.setText(AccountFilter.answer(accountsList,getCurrFilter()));
				this.fireTableDataChanged();
			}
		};
		
		table = new JTable(tableModelAccounts);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.add(new JScrollPane(table));
		
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		
		filterLabel = new JLabel();
		filterLabel.setText("SELECT FILTER FUNCTION");
		filterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(filterLabel);
		
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		
		filterMenu.setSelectedIndex(0);
		filterMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		filterMenu.setBackground(Color.WHITE);
		this.add(filterMenu);
		
		this.add(Box.createRigidArea(new Dimension(10, 10)));

		informationLabel = new JLabel();
		informationLabel.setText("BACKED INFORMATION");
		informationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(informationLabel);
		
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		
		informationLabelValue = new JLabel();
		informationLabelValue.setText("");
		informationLabelValue.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(informationLabelValue);
		
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		
	}
	/**
	 * Getter of the current filter
	 * @return the current filter
	 */
	public Filters getCurrFilter() {
		return (Filters) filterMenu.getSelectedItem();
	}
	@Override
	public void refreshTable() {
		tableModelAccounts.refreshListAccounts();
	}

}
