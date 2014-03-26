package ist.meic.pa.command;

import ist.meic.pa.Inspector;

/*Module to quit from the read-eval-print loop*/
public class qCommand implements Command {

	@Override
	public void execute(Inspector gadget, String[] commandList) {
		gadget.quit();
	}

	

}
