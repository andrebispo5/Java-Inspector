package ist.meic.pa.command;

import ist.meic.pa.Inspector;
import ist.meic.pa.Navigator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

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
			Field field = null;
			try {
				getFields(current,current.getClass(),commandList[1]);
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
				gadget.inspectObject(field.get(current));
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
			} catch (IllegalAccessException e) {
				System.err.println("Field cannot be accessed");
			} catch(IndexOutOfBoundsException e){
				System.err.println("Field not found.");
			}
			
		}

	public void getFields(Object obj, Class<?> objClass, String fieldName) throws SecurityException, NoSuchFieldException{
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
			getFields(obj,cl,fieldName);
			
	}
	
}

