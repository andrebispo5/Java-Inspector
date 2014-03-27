package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;



/**
 * Module to support navigation in the navigation bar. Sets the current object to the next object in the bar, if it exists.
 */
public class nCommand implements Command {


	@Override
	public void execute(Inspector gadget, String[] commandList) {
		Navigator nav = gadget.getNavigator();
		nav.next();
	}

}
