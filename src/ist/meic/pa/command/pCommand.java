package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;

/**
 * Module to support navigation in the navigation bar. Sets the current object to the previous object in the bar, if it exists.
 */
public class pCommand implements Command {

	
	@Override
	public void execute(Inspector gadget, String[] commandList) {
		Navigator nav = gadget.getNavigator();
		nav.previous();
	}

}
