package ist.meic.pa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.*;

public class Inspector {
	
	private boolean running ;
	private Navigator navigator;
	
	public Inspector(){
		navigator = new Navigator();
		running=true;
	}
	
	
	
	public void inspect(Object object) throws IllegalArgumentException, IllegalAccessException{

		System.out.println("Welcome to the most advanced shell in the world. Enjoy!");
		System.out.println("-------------------------------------------------------");
		System.out.println("");
		inspectObject(object);
		listenConsole();
	}
	
	public void inspectObject(Object object){
		try {
			navigator.add(object);
			Class<? extends Object> c = object.getClass();
			System.out.println(object.toString() + " is an instance of class "  + c.getName() );
			System.out.println("\n-----FIELDS-----");
			printFields(object, c);
			System.out.println("\n-----METHODS-----");
			printMethods(object, c);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void listenConsole() {
		String command = null;
		String[] commandSplit = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while(running){
			System.out.print(">");
			try {
				command = in.readLine();
				commandSplit = command.split(" ");
				Class<? extends Command> cmd = Class.forName("ist.meic.pa." + commandSplit[0] + "Command").asSubclass(Command.class);
				cmd.newInstance().execute(this, commandSplit);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
		}
		
	}

	private void printFields(Object object, Class<? extends Object> c)
			throws IllegalAccessException {
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
				System.out.print(modfs + " ");
			System.out.println(fieldType + " " + fieldName + " " + "=" + " " +fields[j].get(object));

		}
		Class<?> cl = c.getSuperclass();
		if(cl != null)
			printFields(object, cl);
	}
	
	protected void quit(){
		running=false;
	}
	
	private void printMethods(Object object, Class<? extends Object> c)
			throws IllegalAccessException {
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
			String parameters = new String();
			for ( int i =0; i<param.length;i++){
				if (param[i].isArray())
					parameters += param[i].getComponentType().getName() + "[] ";
				else
					parameters+=param[i].getName();
				if (i < param.length-1)
					parameters +=", ";
			}
			if (!(modfs.equals("")))
				System.out.print(modfs + " ");
			System.out.println(methReturnType + " " + methodName + "(" + " " + parameters + " );"); 

		}
		Class<?> cl = c.getSuperclass();
		if(cl != null)
			printMethods(object, cl);
	}



	public Navigator getNavigator() {
		return this.navigator;
	}

}
