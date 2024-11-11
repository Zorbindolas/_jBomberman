package view;
/**
 * View component that has a JTable and need to recalculate it because view updates.
 */
public interface TableRefresher {
	/**
	 * Method to update the model list of the JTable
	 */
	public void refreshTable();
}
