package ist.meic.pa.command;

import ist.meic.pa.Inspector;


/**
 * Module to support the termination of the inspection.
 */
public class qCommand implements Command {

	
	@Override
	public void execute(Inspector gadget, String[] commandList) {
		gadget.quit();
	}

	

}
