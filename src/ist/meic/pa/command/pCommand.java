package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;

/*Module to support the next command, used for setting the current object to the previous*/
public class pCommand implements Command {

	@Override
	public void execute(Inspector gadget, String[] commandList) {
		Navigator nav = gadget.getNavigator();
		nav.previous();
	}

}
