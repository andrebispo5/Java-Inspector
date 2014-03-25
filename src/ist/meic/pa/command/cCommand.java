package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;
import ist.meic.pa.TypeValidator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class cCommand implements Command {

	private Class<?>[] classArgs;
	private Object[] args;
	private final Map<Object[], Method> candidateMethodsMap = new HashMap<Object[], Method>();
	
	
	@Override
	public void execute(Inspector gadget, String[] commandList) {
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
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	private void callMethod(Object obj, Method m)
			throws IllegalAccessException, InvocationTargetException {
		if (m==null){
			for(Object[] object : candidateMethodsMap.keySet()){

				try{
					System.err.println(candidateMethodsMap.get(object).invoke(obj, args));
				}
				catch (IllegalArgumentException e) {
					continue;
				} 
				break;
			}
		}
		else{
			System.err.println(m.invoke(obj, args));
		}
	}


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
	
	
	

	private void computeAllArgs(String[] argArray, Navigator nav) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException{
		for (int i=0;i<argArray.length;i++){
			if(argArray[i].startsWith("\"")){
				classArgs[i]=String.class;
				args[i] = argArray[i].substring(1,argArray[i].length() -1);
			}
			else if(argArray[i].endsWith("f")){
				classArgs[i]=float.class;
				args[i] = Float.parseFloat(argArray[i].substring(0,argArray[i].length()-1));
			}
			else if(argArray[i].contains(".")){
				classArgs[i]=double.class;
				args[i] = Double.parseDouble(argArray[i]);
			}
			else if (argArray[i].contains("true")||argArray[i].contains("false")){
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
			else if(argArray[i].startsWith("\'")){
				classArgs[i]=char.class;
				args[i] = argArray[i].charAt(1);
			}
			else if(argArray[i].startsWith("@")){
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
