package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;
import ist.meic.pa.TypeValidator;

import java.lang.reflect.Field;

/*Module to modify fields of inspected objects*/
public class mCommand implements Command {

	/*sets the field to modify to the new given value*/
	@Override
	public void execute(Inspector gadget, String[] commandList) {
		Navigator nav = gadget.getNavigator();
		Object obj = nav.getObject();
		Class<? extends Object> c = obj.getClass();
		Field f = null;
		String newValue = commandList[2];
		try {
			f = this.getField(obj, c, commandList[1]);
			TypeValidator t = new TypeValidator();
			if(newValue.startsWith("@")){
				Object savedObject = nav.getSavedObject(newValue.substring(1,newValue.length()));
				System.out.println(savedObject);
				nav.updateExistingObject(f.get(obj), savedObject);
				f.set(obj,savedObject);
			}
			else{
				Object fieldValue = t.assignValue(f, newValue);
				nav.updateExistingObject(f.get(obj),fieldValue);
				f.set(obj, fieldValue);
			}
		} catch (SecurityException e) {
			System.err.println("Field cannot be accessed.");
		} catch (IllegalArgumentException e) {
			System.err.println("Field not compatible with input.");
		} catch (IllegalAccessException e) {
			System.err.println("Field cannot be accessed.");
		} catch(NullPointerException e){
			System.err.println("Field not found.");
		} catch(ArrayIndexOutOfBoundsException e){
			System.err.println("Value to assign to field not found.");
		}

	}
	
	/*Gets the field of object given  with the name given*/
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
