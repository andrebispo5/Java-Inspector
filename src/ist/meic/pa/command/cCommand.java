package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;
import ist.meic.pa.TypeValidator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/*Module for implementation of command to call methods*/
public class cCommand implements Command {

	private Class<?>[] classArgs;
	private Object[] args;
	private final Map<Object[], Method> candidateMethodsMap = new HashMap<Object[], Method>();
	
	/*Computes the list of arguments given as string, 
	 * checks for the possible method to call with the name and arguments given 
	 * and calls the suitable method*/
	@Override
	public void execute(Inspector gadget, String[] commandList) {
		if (commandList.length < 2){
			System.err.println("Call command syntax: c <method> <argument1> ... ");
			return;
		}
			
		Navigator nav = gadget.getNavigator();
		Object obj = nav.getObject();
		Class<? extends Object> c = obj.getClass();
		int numArgs=commandList.length-2;
		String[] argList=new String[numArgs];
		System.arraycopy(commandList, 2, argList, 0, numArgs);
		classArgs = new Class<?>[numArgs];
		args= new Object[numArgs];
		try {
			computeAllArgs(argList, nav);
			candidateMethodsToInvoke(commandList, c);
			Method m = candidateMethodsMap.get(classArgs);
			callMethod(obj, m);
		} catch (InstantiationException e) {
			System.err.println("Not possible to invoke method");
		} catch (IllegalAccessException e) {
			System.err.println("Not possible to invoke method");
		} catch (SecurityException e) {
			System.err.println("Not possible to invoke method");
		} catch (NoSuchMethodException e) {
			System.err.println("Method not found");
		} catch (InvocationTargetException e) {
			System.err.println("Not possible to invoke method");
		} catch (ClassNotFoundException e) {
			System.err.println("Method not found");
		}
	}

	/*tries to invoke one of the possible methods*/
	private void callMethod(Object obj, Method m)
			throws IllegalAccessException, InvocationTargetException {
		if (m==null){
			boolean MethodFound = false;
			for(Object[] object : candidateMethodsMap.keySet()){
				try{
					System.err.println(candidateMethodsMap.get(object).invoke(obj, args));
					MethodFound=true;
				}
				catch (IllegalArgumentException e) {
					continue;
				} 
				break;
			}
			if(!MethodFound)
				System.err.println("Method Not Found.");
		}
		else{
			System.err.println(m.invoke(obj, args));
			
		}
	}

	/*Computes the list of methods assignable to invoke*/
	private void candidateMethodsToInvoke(String[] commandList, Class<? extends Object> c)
			throws NoSuchMethodException {
		Method[] meths=null;
		meths = c.getDeclaredMethods();
		for (Method method : meths) {
			if (method.getName().equals(commandList[1]) && method.getParameterTypes().length == args.length ) 
				candidateMethodsMap.put(method.getParameterTypes(), method);
		}
		Class <?> sc = c.getSuperclass();
		if (sc != null){
			candidateMethodsToInvoke(commandList, sc);
		}
		return;
	}
	
	
	
	/*Computes the arguments given as a string to the respective specific objects and classes*/
	private void computeAllArgs(String[] argArray, Navigator nav) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException{
		for (int i=0;i<argArray.length;i++){
			if(argArray[i].startsWith("\"")){
				classArgs[i]=String.class;
				args[i] = argArray[i].substring(1,argArray[i].length() -1);
			}
			else if(argArray[i].matches("[0-9]+f")){
				classArgs[i]=float.class;
				args[i] = Float.parseFloat(argArray[i].substring(0,argArray[i].length()-1));
			}
			else if(argArray[i].contains(".")){
				classArgs[i]=double.class;
				args[i] = Double.parseDouble(argArray[i]);
			}
			else if (argArray[i].matches("true")||argArray[i].matches("false")){
				classArgs[i]=boolean.class;	
				args[i] = Boolean.parseBoolean(argArray[i]);
			}
			else if(argArray[i].matches("[0-9]*")){
				classArgs[i]=int.class;
				args[i] = Integer.parseInt(argArray[i]);
			}
			else if(argArray[i].matches("[0-9]+L")){
				classArgs[i]=long.class;
				args[i] = Long.parseLong(argArray[i].substring(0,argArray[i].length() -1));
			}
			else if(argArray[i].matches("\'[A-Za-z0-9]\'")){
				classArgs[i]=char.class;
				args[i] = argArray[i].charAt(1);
			}
			else if(argArray[i].matches("@[A-Za-z0-9]+")){
				Object newObj = nav.getSavedObject(argArray[i].substring(1,argArray[i].length()));
				Class<?> c = newObj.getClass();
				TypeValidator tv = new TypeValidator();
				if(tv.isPrimitiveWrapper(c)){
					try {
						c = (Class<?>) c.getField("TYPE").get(null);
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					}
				}
				classArgs[i]= c;
				args[i] =newObj;
			}
		}
	}

}
