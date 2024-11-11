package view;

import java.awt.Graphics2D;
/**
 * The Card shows current account data and can direct to account panels with advanced operations.
 */
public class DynaSlaveAccount extends DynaSlaveCard  implements RelocateSpecialComponents {
	/**
	 * Serialized key for Serialization
	 */
	private static final long serialVersionUID = 1267048599559481289L;
	/**
	 * Special JPanel component to show current account data
	 */
	private DynaSlaveAccountNested currAccountPanel;
	/**
	 * Constructor.
	 * This Card shows current account data and can direct to account panels with advanced operations.
	 * @param master CardLayout Master reference
	 */
	public DynaSlaveAccount(DynaMasterCard master) {
		super(
				master, 
				3,
				new String[] {"MANAGE ACCOUNTS", "STATISTICS", "BACK"},
				new int[] {7,8,9},
				"bgpink"
				);
		
		currAccountPanel = new DynaSlaveAccountNested(this);
		currAccountPanel.setBounds(0, 0, 10, 10);
		this.add(currAccountPanel);
		
		relocateSpecialComponents();
		
	}
	/**
	 * Getter of the current Account JPanel special component
	 * @return its special JPanel component
	 */
	public DynaSlaveAccountNested getCurrAccountPanel() {return currAccountPanel;}

	@Override
	public void optionSwitcher(int pointer) {
		switch(pointer) {
		case 0 -> {changePanel("manageAccounts");}
		case 1 -> {changePanel("statsAccounts");}
		case 2 -> escFunction();
		default -> {}
		}
	}

	@Override
	public void escFunction() {
		super.changePanel("start");
	}
	
	@Override
	public void relocateComponents() {
		super.relocateComponents();
		if(currAccountPanel!=null) relocateSpecialComponents();
	}
	
	@Override
	public void relocateSpecialComponents() {
		currAccountPanel.relocateMe(meterH+10, meterW, 5, 8);
	}
	
	@Override
	public Graphics2D drawStuff(Graphics2D g2){
		g2 = pencilPen(g2,"CURRENT ACCOUNT",meterH,meterW,30);
		return g2;
	}
	/**
	 * Refresh current account special component 
	 * with current account data saved in the view (and updated from the Model notifications)
	 */
	public void refresh() {
		currAccountPanel.refreshCurrAccountValues();
	}

}
