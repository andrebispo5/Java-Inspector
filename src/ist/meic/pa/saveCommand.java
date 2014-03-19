package ist.meic.pa;

public class saveCommand implements Command {

	@Override
	public void execute(Inspector gadget, String[] commandList) {
		Navigator nav = gadget.getNavigator();
		nav.saveObject(commandList[1]);
	}

}
