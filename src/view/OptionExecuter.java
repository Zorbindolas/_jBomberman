package view;
/**
 * An OptionExecuter is an executor of options Commands and esc function Command
 */
public interface OptionExecuter {
	/**
	 * Execute the option Command
	 */
	public void executeOption();
	/**
	 * Execute the esc function Command
	 */
	public void escFunction();
	
}
