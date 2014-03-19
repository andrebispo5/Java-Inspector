package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class goCommand implements Command {
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	@Override
	public void execute(Inspector gadget, String[] commandList) {
		Navigator nav = gadget.getNavigator();
		if(commandList.length == 1){
			this.chooseOptions(nav);
		}else{
			this.quickGoTo(nav,commandList);
		}
		
	}

	private void chooseOptions(Navigator nav) {
		System.err.println("CHOOSE YOUR DESTINY: #MortalKombat");
		nav.printGraph();
		System.err.print("=>");
		try {
			int option = Integer.parseInt(in.readLine());
			if(0 <= option && option < nav.maxOption())
				nav.goTo(option);
			else
				System.err.println("Invalid object number. Try again.");
		} catch (NumberFormatException e) {
			System.err.println("Invalid object number. Try again.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void quickGoTo(Navigator nav, String[] commandList) {
		try {
			int option = Integer.parseInt(commandList[1]);
			if(0 <= option && option < nav.maxOption())
				nav.goTo(option);
			else
				System.err.println("Invalid object number. Try again.");
		} catch (NumberFormatException e) {
			System.err.println("Invalid object number. Try again.");
		}
	}
}
