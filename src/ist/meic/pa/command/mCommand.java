package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;
import ist.meic.pa.TypeValidator;

import java.lang.reflect.Field;


/**
 * Module to modify fields of inspected objects.
 * Receives two arguments: the name of the field to modify and its new value.
 * This command supports two modes: The user can either choose to modify a field of the current selected object or, if 
 * the name is preceded by @, to modify a previously saved field.
 */
public class mCommand implements Command {

	@Override
	public void execute(Inspector gadget, String[] commandList) {
		Navigator nav = gadget.getNavigator();
		Object obj = nav.getObject();
		Class<? extends Object> c = obj.getClass();
		Field f = null;

		try {
			String newValue = commandList[2];
			f = this.getField(obj, c, commandList[1]);
			Object oldValue = f.get(obj);
			TypeValidator t = new TypeValidator();
			if (newValue.startsWith("@")) {
				Object savedObject = nav.getSavedObject(newValue.substring(1,
						newValue.length()));
				System.out.println(savedObject);

				if (savedObject != null) {
					f.set(obj, savedObject);
					nav.updateExistingObject(oldValue, savedObject);
				}
			} else {
				Object fieldValue = t.createObjectWithValue(f.getType(), newValue);

				if (fieldValue != null) {
					f.set(obj, fieldValue);
					nav.updateExistingObject(oldValue, fieldValue);
				}
			}
		} catch (SecurityException e) {
			System.err.println("Field cannot be accessed.");
		} catch (IllegalArgumentException e) {
			System.err.println("Field not compatible with input.");
		} catch (IllegalAccessException e) {
			System.err.println("Field cannot be accessed.");
		} catch (NullPointerException e) {
			System.err.println("Field not found.");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Value to assign to field not found.");
		}

	}

	/**
	 * Gets the field matching the name given by the user. If the field is not
	 * from the current object, is recursively searches for it in the object's
	 * superclasses.
	 * 
	 * @param obj
	 *            The current selected object
	 * @param objClass
	 *            The current selected object's class
	 * @param fieldName
	 *            The name of the field to modify
	 * @return The field with the given name
	 */
	public Field getField(Object obj, Class<?> objClass, String fieldName) {
		Field[] fields = objClass.getDeclaredFields();
		Field wantedField = null;
		for (int j = 0; j < fields.length; j++) {
			fields[j].setAccessible(true);
			String fn = fields[j].getName();
			if (fn.equals(fieldName))
				wantedField = fields[j];
		}
		if (wantedField == null) {
			Class<?> cl = objClass.getSuperclass();
			if (cl != null)
				wantedField = getField(obj, cl, fieldName);
		}
		return wantedField;
	}
}
