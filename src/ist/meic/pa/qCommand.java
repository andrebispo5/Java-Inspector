package ist.meic.pa;

public class qCommand implements Command {

	@Override
	public void execute(Inspector gadget, String[] commandList) {
		gadget.quit();
		
	}

	

}
