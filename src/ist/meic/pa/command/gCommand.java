package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*Module to use the navigation system*/
public class gCommand implements Command {
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	/* if user provides an integer, sets the current position to the given integer, 
	 * otherwise shows the options in a menu*/
	@Override
	public void execute(Inspector gadget, String[] commandList) {
		Navigator nav = gadget.getNavigator();
		if(commandList.length == 1){
			this.chooseOptions(nav);
		}else{
			this.quickGoTo(nav,commandList);
		}
		
	}

	/*Prints the menu with the possible options to choose*/
	private void chooseOptions(Navigator nav) {
		System.err.println("Choose the object you want to go!");
		nav.printGraph();
		System.err.print("=>");
		try {
			int option = Integer.parseInt(in.readLine());
			if(0 <= option && option < nav.maxOption())
				nav.goTo(option);
			else
				System.err.println("Invalid object number.");
		} catch (NumberFormatException e) {
			System.err.println("Invalid object number.");
		} catch (IOException e) {
			System.err.println("Invalid input.");
		}
	}
	
	/*goes directly to the position given by the user*/
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
