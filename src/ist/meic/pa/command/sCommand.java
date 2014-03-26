package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;

/*Module to save the current object to further recall*/
public class sCommand implements Command {

	@Override
	public void execute(Inspector gadget, String[] commandList) {
		Navigator nav = gadget.getNavigator();
		if(commandList.length ==2)
			nav.saveObject(commandList[1]);
		else
			System.err.println("Command sintax: s <name to save the object>");
	}

}
