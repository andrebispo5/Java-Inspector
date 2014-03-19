package ist.meic.pa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class goCommand implements Command {

	@Override
	public void execute(Inspector gadget, String[] commandList) {
		Navigator nav = gadget.getNavigator();
		System.err.println("CHOOSE YOUR DESTINY: #MortalKombat");
		nav.printGraph();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			int option = Integer.parseInt(in.readLine());
			nav.goTo(option);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
