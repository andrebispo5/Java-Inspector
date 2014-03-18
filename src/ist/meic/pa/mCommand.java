package ist.meic.pa;

import java.lang.reflect.Field;

public class mCommand implements Command {

	@Override
	public void execute(Inspector gadget, String[] commandList) {
		Navigator nav = gadget.getNavigator();
		Object obj = nav.getObject();
		Class<? extends Object> c = obj.getClass();
		Field f = null;
		try {
			f = this.getField(obj, c, commandList[1]);
			if(f==null){
				System.err.println("Field of object"+ obj +"not found");
			}else{
				TypeValidator t = new TypeValidator();
				Object value = t.assignValue(f, commandList[2]);
				f.set(obj, value);
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Field getField(Object obj, Class<?> objClass, String fieldName) throws SecurityException, NoSuchFieldException{
		Field[] fields = objClass.getDeclaredFields();
		Field wantedField = null;
		for (int j=0;j<fields.length;j++){
			fields[j].setAccessible(true);
			String fn = fields[j].getName();
			if (fn.equals(fieldName))
				wantedField = fields[j];
		}
		if(wantedField == null){
			Class<?> cl = objClass.getSuperclass();
			if(cl != null)
				wantedField = getField(obj,cl,fieldName);
		}
		return wantedField;
	}
}
