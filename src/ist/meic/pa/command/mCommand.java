package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;
import ist.meic.pa.TypeValidator;

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
			TypeValidator t = new TypeValidator();
			Object fieldValue = t.assignValue(f, commandList[2]);
			nav.updateExistingObject(fieldValue);
			f.set(obj, fieldValue);
		} catch (SecurityException e) {
			System.err.println("Field cannot be accessed.");
		} catch (IllegalArgumentException e) {
			System.err.println("Field not compatible with input.");
		} catch (IllegalAccessException e) {
			System.err.println("Field cannot be accessed.");
		} catch(NullPointerException e){
			System.err.println("Field not found.");
		} catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
			System.err.println("Value to assign to field not found.");
		}
		
	}
	
	public Field getField(Object obj, Class<?> objClass, String fieldName){
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
