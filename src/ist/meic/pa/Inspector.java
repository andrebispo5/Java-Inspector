package ist.meic.pa;

import ist.meic.pa.command.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.*;

/**
 * The Class Inspector.
 * Main class of the application. Provides methods to inspect objects and to print messages to the console.
 * It's also the class that provides interaction with the user.
 */
public class Inspector {
	
	private boolean running ;
	private Navigator navigator;
	
	public Inspector(){
		navigator = new Navigator();
		running=true;
	}
	
	/**
	 * Starts the inspection of an object and the interaction with the user.
	 *
	 * @param object	the object to inspect 
	 */
	public void inspect(Object object){
		printWelcomeMsg();
		inspectObject(object);
		listenConsole();
	}
	
	/**
	 * Prints the welcome message.
	 */
	private void printWelcomeMsg() {
		System.err.println("JAVA INSPECTOR v1.0");
		System.err.println("-------------------------------------------------------");
		System.err.println("");
	}
	
	/**
	 * This method is responsible for the interaction with the user. While the inspector is running, it awaits commands from the user
	 * and runs them.
	 */
	private void listenConsole() {
		String command = null;
		String[] commandSplit = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while(running){
			printNavigation();
			System.err.print(">");
			try {
				command = in.readLine();
				commandSplit = command.split(" ");
				Class<? extends Command> cmd = Class.forName("ist.meic.pa.command." + commandSplit[0] + "Command").asSubclass(Command.class);
				cmd.newInstance().execute(this, commandSplit);
			} catch (IOException e) {
				System.err.println("Input not working properly please restart.");
			} catch (ClassNotFoundException e) {
				System.err.println("Command not found. Try again or enter h for help.");
			} catch (InstantiationException e) {
				System.err.println("Command not found. Try again or enter h for help.");			
			} catch (IllegalAccessException e) {
				System.err.println("Command not found. Try again or enter h for help.");
			}
		}
		
	}

	
	/**
	 * Inspects a general object. Distinguishes between primitive types, arrays and classes 
	 * and acts accordingly. If the object is a primitive type, it adds the object to the navigation bar and prints its value.
	 * If the object is an array, it calls a method to inspect the array. If the object is a class, it calls methods to print
	 * its fields and methods.
	 * 
	 *
	 * @param object 	the object to inspect
	 */
	public void inspectObject(Object object){
		TypeValidator tv = new TypeValidator(); 
		navigator.add(object);
		Class<?> c = object.getClass();
		if(tv.isPrimitiveWrapper(c)){
			navigator.add(object);
			System.err.println(object);
		}else if(c.isArray()){
			inspectArray(object);
		}else{
			System.err.println(object.toString() + " is an instance of class "  + c.getName() );
			System.err.println("\n-----FIELDS-----");
			printFields(object, c);
			System.err.println("\n-----METHODS-----");
			printMethods(object, c);
		}
	}

	/**
	 * Inspects an object representing an array. Grants the option to view the entire content of an array or
	 * to inspect a specific position of the array.
	 *
	 * @param object 	An object representing an array
	 */
	private void inspectArray(Object object) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int length = Array.getLength(object);
		System.err.println("Inspecting an Array of length "+ length +", press v to view elements or enter number.");
		String command;
		try {
			command = in.readLine();
			if(command.contains("v")){
				printArray(object, length);
			}else{
				int position = Integer.parseInt(command);
				inspectObject(Array.get(object, position));
			}
		} catch (IOException e) {
			System.err.println("Cannot complete the operation.");
		}catch (ArrayIndexOutOfBoundsException e){
			System.err.println("Choose a valid array position to inspect.");
		}catch (NumberFormatException e){
			System.err.println("Choose a valid array position to inspect.");
		}
	}
	
	/**
	 * Prints the fields of a class and its superclasses.
	 *
	 * @param object 	The object being inspected
	 * @param c 		The class of the object being inspected
	 */
	private void printFields(Object object, Class<? extends Object> c){
		Field[] fields = c.getDeclaredFields();
		for (int j=0;j<fields.length;j++){
			fields[j].setAccessible(true);
			String modfs = Modifier.toString(fields[j].getModifiers());
			String fieldName = fields[j].getName();
			Class<?> type = fields[j].getType();
			String fieldType;
			if (type.isArray())
				fieldType = type.getComponentType().getName() + "[] ";
			else
				fieldType=type.getName();
			if (!(modfs.equals("")))
				System.err.print(modfs + " ");
			try {
				System.err.println(fieldType + " " + fieldName + " " + "=" + " " +fields[j].get(object));
			} catch (IllegalArgumentException e) {
				System.err.println("Field cannot be accessed.");
			} catch (IllegalAccessException e) {
				System.err.println("Field cannot be accessed.");
			}
		}
		Class<?> cl = c.getSuperclass();
		if(cl != null)
			printFields(object, cl);
	}
	
	/**
	 * Prints the methods of an object and its superclasses.
	 *
	 * @param object 	The object being inspected
	 * @param c 		The class of the object being inspected
	 */
	private void printMethods(Object object, Class<? extends Object> c){
		Method[] meth = c.getDeclaredMethods();
		for (int j=0;j<meth.length;j++){
			meth[j].setAccessible(true);
			String modfs = Modifier.toString(meth[j].getModifiers());
			String methodName = meth[j].getName();
			Class<?> type = meth[j].getReturnType();
			String methReturnType;
			if (type.isArray())
				methReturnType = type.getComponentType().getName() + "[] ";
			else
				methReturnType=type.getName();
			Class<?> param[] = meth[j].getParameterTypes();
			String parameters = argumentsToString(param);
			if (!(modfs.equals("")))
				System.err.print(modfs + " ");
			System.err.println(methReturnType + " " + methodName + "(" + " " + parameters + " );"); 

		}
		Class<?> cl = c.getSuperclass();
		if(cl != Object.class)
			printMethods(object, cl);
	}

	/**
	 * Prints the content of an array to the console
	 *
	 * @param object 	An object representing an array
	 * @param length 	The length of the array
	 */
	private void printArray(Object object, int length) {
		int colCount = 0;
		for (int i = 0; i < length; i ++) {
			Object arrayElement = Array.get(object, i);
			System.err.print("["+i+"]"+arrayElement + " ");
			if(colCount ==10){
				System.err.print("\n");
		        colCount=0;
			}
			colCount++;
		}
	}

	/**
	 * Prints the navigation bar.
	 */
	private void printNavigation() {
		navigator.printNavigationBar();
	}
	
	/**
	 * Causes the inspection to terminate.
	 */
	public void quit(){
		running=false;
	}
	
	/**
	 * Takes the parameters of a method and converts them into a string.
	 *
	 * @param param 	The parameters of the method
	 * @return A string of the parameters
	 */
	private String argumentsToString(Class<?>[] param) {
		String parameters = "";
		for ( int i =0; i<param.length;i++){
			if (param[i].isArray())
				parameters += param[i].getComponentType().getName() + "[] ";
			else
				parameters+=param[i].getName();
			if (i < param.length-1)
				parameters +=", ";
		}
		return parameters;
	}

	/**
	 * Gets the navigation bar.
	 *
	 * @return The navigation bar
	 */
	public Navigator getNavigator() {
		return this.navigator;
	}

}
