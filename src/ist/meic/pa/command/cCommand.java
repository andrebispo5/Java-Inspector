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
	private final Map<Object[], Method> parameterTypeMap = new HashMap<Object[], Method>();
	
	
	@Override
	public void execute(Inspector gadget, String[] commandList) {
		int numArgs=commandList.length-2;
		Navigator nav = gadget.getNavigator();
		Object obj = nav.getObject();
		Class<? extends Object> c = obj.getClass();
		//ARRAY COM INPUT DOS ARGUMENTOS
		String[] argList=new String[numArgs];
		//COPIA ARGUMENTOS PARA ARRAY ANTERIOR
		System.arraycopy(commandList, 2, argList, 0, numArgs);
		classArgs = new Class<?>[numArgs];
		args= new Object[numArgs];
		try {
			computeAllArgs(argList, nav);
			getMethod(commandList, c);
			for(Object[] object : parameterTypeMap.keySet()){
				if (object.length == args.length){
					System.err.println(parameterTypeMap.get(object).invoke(obj, args));
					return;
				}
			}

			System.err.println("METHOD NOT FOUND!");
		} catch (InstantiationException e) {
			System.err.println("1");
		} catch (IllegalAccessException e) {
			System.err.println("2");
		} catch (SecurityException e) {
			System.err.println("3");
		} catch (NoSuchMethodException e) {
			System.err.println("4");
		} catch (IllegalArgumentException e) {
			System.err.println("5");
		} catch (InvocationTargetException e) {
			System.err.println("6");
		} catch (ClassNotFoundException e) {
			System.err.println("7");
		}
	}


	private void getMethod(String[] commandList, Class<? extends Object> c)
			throws NoSuchMethodException {
		Method[] meths=null;
		meths = c.getDeclaredMethods();
		for (Method method : meths) {
			if (method.getName().equals(commandList[1])) 
				parameterTypeMap.put(method.getParameterTypes(), method);
		}
		Class <?> sc = c.getSuperclass();
		if (sc != null){
			System.err.println("Method not found in class:" + c + " searching superclass.");
			getMethod(commandList, sc);
		}
		return;
	}
	
	
	

	private void computeAllArgs(String[] argArray, Navigator nav) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException{
		for (int i=0;i<argArray.length;i++){
			if(argArray[i].startsWith("\"")){
				classArgs[i]=String.class;
				args[i] = argArray[i].substring(1,argArray[i].length() -1);
			}
			else if(argArray[i].contains(".")){
				classArgs[i]=double.class;
				args[i] = Double.parseDouble(argArray[i]);
			}else if (argArray[i].contains("true")||argArray[i].contains("false")){
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
