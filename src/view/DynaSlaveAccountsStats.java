package view;

import java.awt.Graphics2D;
/**
 * The Card shows accounts table filtered by User selected statistic operation.
 */
public class DynaSlaveAccountsStats extends DynaSlaveCard 
implements RelocateSpecialComponents, TableRefresher {
	/**
	 * Serialized key for Serialization.
	 */
	private static final long serialVersionUID = 7785650224513495209L;
	/**
	 * The nested panel to show filtered accounts table
	 */
	private DynaSlaveAccountsStatsNested filteredPanel;
	/**
	 * Constructor.
	 * This Card shows accounts table filtered by User selected statistic operation.
	 * @param master CardLayout Master reference
	 */
	public DynaSlaveAccountsStats(DynaMasterCard master) {
		super(
				master, 
				2,
				new String[] {"FILTER", "BACK"},
				new int[] {8,9},
				"bgpink"
				);
		
		filteredPanel = new DynaSlaveAccountsStatsNested(this);
		filteredPanel.setBounds(0, 0, 10, 10);
		this.add(filteredPanel);
		
		relocateSpecialComponents();
	}

	@Override
	public void optionSwitcher(int pointer) {
		switch(pointer) {
		case 0 -> {
			filteredPanel.refreshTable();
		}
		case 1 -> escFunction();
		default -> {}
		}
	}

	@Override
	public void escFunction() {
		super.changePanel("account");
	}

	@Override
	public void relocateComponents() {
		super.relocateComponents();
		if(filteredPanel!=null) relocateSpecialComponents();
	}
	
	@Override
	public void relocateSpecialComponents() {
		filteredPanel.relocateMe(meterH+10, meterW, 5, 8);
	}

	@Override
	protected Graphics2D drawStuff(Graphics2D g2) {
		g2 = pencilPen(g2,"ACCOUNT FILTERED BY FUNCTION SELECTED",meterH,meterW,20);
		return g2;
	}
	@Override
	public void refreshTable() {
		filteredPanel.refreshTable();
	}
}
