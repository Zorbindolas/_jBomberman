package view;

import java.awt.Graphics2D;

import javax.swing.JOptionPane;
/**
 * The Card implements GUI to create a new Account.
 */
public class DynaSlaveAccountCreation extends DynaSlaveCard 
implements RelocateSpecialComponents {
	/**
	 * Serialized key for Serialization
	 */
	private static final long serialVersionUID = -4870618064302580798L;
	/**
	 * Created decoratedAccount
	 */
	private DecoratedAccount decoratedAccount;
	/**
	 * True if a new Account is created
	 */
	private boolean created;
	/**
	 * Special JPanel component in which define new account parameters
	 */
	private DynaSlaveAccountCreationNested creationPanel;
	/**
	 * Constructor. This Card implements GUI to create a new Account.
	 * @param master CardLayout Master reference
	 */
	public DynaSlaveAccountCreation(DynaMasterCard master) {
		super(
				master, 
				2,
				new String[] {"CREATE IT", "BACK"},
				new int[] {8,9},
				"bgpink"
				);
		
		creationPanel = new DynaSlaveAccountCreationNested(this);
		creationPanel.setBounds(0, 0, 10, 10);
		this.add(creationPanel);
		
		relocateSpecialComponents();
	}

	@Override
	public void optionSwitcher(int pointer) {
		switch(pointer) {
		case 0 -> {
			DecoratedAccount dAccount = creationPanel.getNewDecoratedAccount();
			if(dAccount!=null) {
				decoratedAccount = dAccount;
				created = true;
				DynaSound.playEffect(6); // accountCreated
				JOptionPane.showMessageDialog(this, "Your account has been created successfully");
			} else {
				DynaSound.playEffect(3); // cursorNegated
				JOptionPane.showMessageDialog(this, "Fields definitions are required before creating a new Account");
			}
		}
		case 1 -> escFunction();
		default -> {}
		}
	}

	@Override
	public void escFunction() {
		super.changePanel("manageAccounts");
	}

	@Override
	public void relocateComponents() {
		super.relocateComponents();
		if(creationPanel!=null) relocateSpecialComponents();
	}
	
	@Override
	public void relocateSpecialComponents() {
		creationPanel.relocateMe(meterH+10, meterW, 5, 8);
	}

	@Override
	protected Graphics2D drawStuff(Graphics2D g2) {
		g2 = pencilPen(g2,"CREATE NEW ACCOUNT",meterH,meterW,20);
		return g2;
	}
	/**
	 * Return true if created is true, that happens when a new account is created.
	 * The condition then became false.
	 * @return this.created
	 */
	public boolean isCreated() {
		if(created) {
			created = false;
			return true;
		}
		return false;
	}
	/**
	 * Return the created account as its decorated implementation
	 * @return the new decorated account
	 */
	public DecoratedAccount getCreatedDecoratedAccount() {
		if(decoratedAccount!=null) {
			DecoratedAccount dAcc = decoratedAccount;
			decoratedAccount = null;
			return dAcc;
		}
		return null;
	}
}
