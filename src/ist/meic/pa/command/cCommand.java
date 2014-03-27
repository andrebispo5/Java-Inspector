package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;
import ist.meic.pa.TypeValidator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * Module to support the call of methods of an inspected object.
 * It receives the name of the method and its arguments, searches for a match and invokes that method, displaying the result
 * of the invocation to the user.
 * If the method doesn't exist in the current object, it searches for a match in its superclasses.
 */
public class cCommand implements Command {

	
	private Class<?>[] classArgs;
	
	
	private Object[] args;
	
	
	private final Map<Object[], Method> candidateMethodsMap = new HashMap<Object[], Method>();
	
	
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

	
	/**
	 * Tries to invoke all methods matching the name given by the user until the invocation of one method with the arguments
	 * given is successful.
	 *
	 * @param obj 	The current selected object
	 * @param m 	The method with the exact match to the arguments given. It's value is null if no exact match was found
	 * @throws IllegalAccessException 	
	 * @throws InvocationTargetException 
	 */
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

	
	/**
	 * Searches for all methods with the given name and the same number of arguments and saves them as possible candidates
	 * to be invoked. Searches recursively for the methods in the current object's superclasses.
	 *
	 * @param commandList 	A list with the name of the method and the arguments input by the user
	 * @param c 			The class of the object being searched
	 * @throws NoSuchMethodException	Thrown if no method is matched to the given name and the number of arguments
	 */
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
	
	
	
	
	/**
	 * Computes the input arguments to check what types they correspond to and parses them to their correct type.
	 *
	 * @param argArray 	An array with the arguments specified by the user
	 * @param nav 		The navigation bar used with the inspector
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws NoSuchMethodException 
	 */
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
