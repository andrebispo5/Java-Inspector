package ist.meic.pa;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class cCommand implements Command {

	@Override
	public void execute(Inspector gadget, String[] commandList) {
		Navigator nav = gadget.getNavigator();
		Object obj = nav.getObject();
		Class<? extends Object> c = obj.getClass();
		Method[] methods = c.getDeclaredMethods();
		String[] argStringValue=new String[commandList.length-2];
		System.arraycopy(commandList, 2, argStringValue, 0, commandList.length - 2);
		try {
			ArrayList<Class<?>> classArgs = getAllClassArgs(argStringValue);
			for(Class caralho:classArgs){
			System.out.println(caralho);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	
	
	private ArrayList<Class<?>> getAllClassArgs(String[] argArray) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		ArrayList<Class<?>> objArray = new ArrayList<Class<?>>();
		for (int i=0;i<argArray.length;i++){
			if(argArray[i].startsWith("\"")){
				objArray.add(String.class);	
			}
			else if(argArray[i].contains(".")){
				objArray.add(Double.class);
			}else if (argArray[i].contains("true")||argArray[i].contains("false")){
				objArray.add(Boolean.class);	
			}
			else if(argArray[i].matches("[0-9]*")){
				objArray.add(Integer.class);
			}
			else if(argArray[i].matches("[0-9]+L")){
				objArray.add(Long.class);
			}
		}
		return objArray;
	}

}
