package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * Module to inspect an object.
 * Inspects a field, given as an argument by the user, of the current object. If the object extends some other class
 * and that class has a field with the same name as the one specified by the user, this command displays all the fields
 * with that name and the classes they correspond to and shows the available options to the user. 
 */
public class iCommand implements Command {
	
	
	
	private ArrayList<Field> fieldsList;
	

	private String options;
	
	
	public iCommand() {
		this.fieldsList = new ArrayList<Field>();
		this.options = new String("Found shadowed field! Choose a class to retreive:\n");
	}

	
	
	@Override
	public void execute(Inspector gadget, String[] commandList) {
			Navigator nav = gadget.getNavigator(); 
			Object current = nav.getObject();
			Field field = getAvailableField(commandList, current);
			try {
				if(field!=null)	
					gadget.inspectObject(field.get(current));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	
	/**
	 * Gets the available fields. If there is only one field, in the current object and its superclasses, with the name specified
	 * by the user, it return that field for inspection. If there are more than one field with that name, it displays the 
	 * options to the user and awaits his choice, returning the chosen field for inspection.
	 *
	 * @param commandList 	The command list with the name of the field to inspect
	 * @param current 		The current selected object
	 * @return The field to inspect
	 */
	private Field getAvailableField(String[] commandList, Object current) {
		Field field = null;
		try {
			getMatchingFields(current,current.getClass(),commandList[1]);
			if(fieldsList.size()<=1){
				field = fieldsList.get(0);	
			}else{
				System.err.println(options);
				System.err.print("=>");
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				int option = Integer.parseInt(in.readLine());
				if(option>=0 && option<fieldsList.size())
					field = fieldsList.get(option);
				else
					throw new NumberFormatException();
			}
		} catch (SecurityException e) {
			System.err.println("Field cannot be accessed");
		} catch (NullPointerException e) {
			System.err.println("Insert a valid option number.");
		} catch (NumberFormatException e) {
			System.err.println("Insert a valid option number.");
		} catch (NoSuchFieldException e) {
			System.err.println("Field not found.");
		} catch (IllegalArgumentException e) {
			System.err.println("Insert a valid option number.");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Insert a field to inspect.");
		} catch (IOException e) {
			System.err.println("Insert a valid number.");
		} catch(IndexOutOfBoundsException e){
			System.err.println("Field not found.");
		}
		return field;
	}

	
	/**
	 * Recursive function to look in the current object and its superclasses for fields with the name specified by the user.
	 * With each match an array is updated with the matched fields in order to support shadowed fields.
	 *
	 * @param obj 		The current selected object
	 * @param objClass 	The current selected object's class
	 * @param fieldName The field name specified by the user
	 * @throws SecurityException 	Thrown when the field cannot be accessed
	 * @throws NoSuchFieldException Thrown when there are no fields with the given name
	 */
	public void getMatchingFields(Object obj, Class<?> objClass, String fieldName) throws SecurityException, NoSuchFieldException{
		Field[] fields = objClass.getDeclaredFields();
		for (int j=0;j<fields.length;j++){
			fields[j].setAccessible(true);
			String fn = fields[j].getName();
			if (fn.equals(fieldName)){
				options+="[" + fieldsList.size() + "] " + objClass.getSimpleName() +"\n";
				fieldsList.add(fields[j]);
			}
		}
		Class<?> cl = objClass.getSuperclass();
		if(cl != null)
			getMatchingFields(obj,cl,fieldName);
			
	}
	
}

