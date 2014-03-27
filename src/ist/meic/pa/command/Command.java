package ist.meic.pa.command;

import ist.meic.pa.Inspector;



/**
 * The Interface Command.
 */
public interface Command {

	/**
	 * Starts the execution of the command
	 *
	 * @param gadget 		The inspector object used to inspect the application
	 * @param commandList 	The input given by the user to interact with the inspector
	 */
	public void execute(Inspector gadget, String[] commandList);
}
 