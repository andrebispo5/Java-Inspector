package ist.meic.pa.command;

import ist.meic.pa.Inspector;

public class qCommand implements Command {

	@Override
	public void execute(Inspector gadget, String[] commandList) {
		gadget.quit();
		
	}

	

}
