package view;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import controller.DynaManager;
/**
 * The Card shows all accounts and proposes saving and loading file operations.
 */
public class DynaSlaveAccountsManager extends DynaSlaveCard 
implements RelocateSpecialComponents, TableRefresher {
    /**
     * Serialized key for Serialization
     */
	private static final long serialVersionUID = -5019518168778940447L;
	/**
	 * The nested panel in which accounts are shown.
	 */
	private DynaSlaveAccountsManagerNested managerPanel;
	/**
	 * The JFileChooser to chose the file for a saving or loading operation.
	 */
	private JFileChooser jFileChooser;
	/**
	 * Constructor.
	 * This Card shows all accounts and proposes saving and loading file operations.
	 * @param master CardLayout Master reference
	 */
	public DynaSlaveAccountsManager(DynaMasterCard master) {
		super(
				master, 
				4,
				new String[] {
						"NEW ACCOUNT",
						"EXPORT SAVE",
						"IMPORT SAVE",
						"BACK"},
				new int[] {6,7,8,9},
				"bgpink"
				);

		managerPanel = new DynaSlaveAccountsManagerNested(this);
		managerPanel.setBounds(0, 0, 10, 10);
		this.add(managerPanel);
		
		relocateSpecialComponents();
		
		jFileChooser = new JFileChooser();
		jFileChooser.addChoosableFileFilter(new FileFilterJBomberMan());
		jFileChooser.setAcceptAllFileFilterUsed(false);
	}

	@Override
	public void optionSwitcher(int pointer) {
		switch(pointer) {
		case 0 -> {changePanel("createAccount");}
		case 1 -> { // EXPORT
			if(jFileChooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION) {
				try {
					DynaManager.getInstance().exportSaveFile(jFileChooser.getSelectedFile());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this,"Data export on file is not allowed","Error",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}
		case 2 -> { // IMPORT
			if(jFileChooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
				try {
					DynaManager.getInstance().importSavedFile(jFileChooser.getSelectedFile());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this,"Data import from file is not allowed","Error",JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}
		case 3 -> escFunction();
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
		if(managerPanel!=null) relocateSpecialComponents();
	}
	
	@Override
	public void relocateSpecialComponents() {
		managerPanel.relocateMe(meterH+10, meterW, 4, 8);
	}

	@Override
	protected Graphics2D drawStuff(Graphics2D g2) {
		String title = "ACCOUNT MANAGER";
		g2 = pencilPen(
				g2,
				title,
				meterH, 
				(master.getFrame().getWStand()-title.length()*23)/2
				);
		return g2;
	}
	/**
	 * Redefine TabelModelAccounts accounts used by special component of this Card
	 * with updated current accounts saved in the view.
	 */
	public void refreshTable() {
		managerPanel.refreshTable();
	}
	
}
