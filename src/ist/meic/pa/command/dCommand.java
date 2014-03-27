package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;


/**
 * Module for represent the inspection details of current object.
 * This command can be called without arguments, which shows the details of the current object, or with an identifier
 * of an existing object, which shows the details of that object and makes that object the current.
 */
public class dCommand implements Command {

	
	@Override
	public void execute(Inspector gadget, String[] commandList) {
		Navigator nav = gadget.getNavigator();
		if(commandList.length == 1){
			gadget.inspectObject(nav.getObject());
		}else{
			int pos = Integer.parseInt(commandList[1]);
			nav.goTo(pos);
			gadget.inspectObject(nav.getObject());			
		}
	}

}
