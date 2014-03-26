package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;

/*Module to show the current saved objects in the navigator*/
public class vCommand implements Command {

	@Override
	public void execute(Inspector gadget, String[] commandList) {
		Navigator nav = gadget.getNavigator();
		System.err.println("Saved Objects: ");
		nav.printSavedObjects();

	}

}
