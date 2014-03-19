package ist.meic.pa.command;

import ist.meic.pa.Inspector;


public interface Command {

	public void execute(Inspector gadget, String[] commandList);
}
 