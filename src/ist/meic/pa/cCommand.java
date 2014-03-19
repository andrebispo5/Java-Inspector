package ist.meic.pa;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class cCommand implements Command {

	private Class<?>[] classArgs;
	private Object[] args;
	
	
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
			computeAllArgs(argList);
			Method method = getMethod(commandList, c);
			System.err.println(method.invoke(obj, args));
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}


	private Method getMethod(String[] commandList, Class<? extends Object> c)
			throws NoSuchMethodException {
		Method meth=null;
		try{
		meth = c.getMethod(commandList[1],classArgs);
		}
		catch(NoSuchMethodException e){
			System.out.println("NOT FOUND");
		}
		if (meth==null)
			meth = getMethod(commandList, c.getSuperclass());
		return meth;
	}
	
	
	private void computeAllArgs(String[] argArray) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, SecurityException, InvocationTargetException, NoSuchMethodException{
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
		}
	}

}
