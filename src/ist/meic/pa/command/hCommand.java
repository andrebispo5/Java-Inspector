package ist.meic.pa.command;

import ist.meic.pa.Inspector;

public class hCommand implements Command {

	@Override
	public void execute(Inspector gadget, String[] commandList) {
		System.err.println("-----------------------------------------COMMAND HELP-----------------------------------------------");
		System.err.println("q \t\t\t- exits inspection.");
		System.err.println("i [name] \t\t- inspects the field with [name].");
		System.err.println("m [name] [value] \t- modifies the field with [name] to [value].");
		System.err.println("c [name] [arg1] [...] \t- calls the method with [name] using as arguments [arg1] [...].");
		System.err.println("go \t\t\t- prompts the user an object to go back to.");
		System.err.println("go [position] \t\t- go back to the object in [position].");
		System.err.println("p \t\t\t- go back to the object in the previous position.");
		System.err.println("n \t\t\t- go back to the object in the next position.");
		System.err.println("save [identifier] \t- saves the current object with [identifier].Called later with @[identifier]");
		System.err.println("----------------------------------------------------------------------------------------------------");
	}

}
