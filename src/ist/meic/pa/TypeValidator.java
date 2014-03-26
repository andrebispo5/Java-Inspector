package ist.meic.pa;

import java.lang.reflect.Field;

/**
 * The Class TypeValidator.
 * A simple type validator for the primitive types and strings. Converts the string given by the input to it's respective types
 * taking in account the representation of the primitive types in java (e.g. a String has to be input between quotation marks). 
 * Also has method to check if a certain object is a wrapper to a primitive type.
 */
public class TypeValidator {

	
	public Object assignValue(Field f, String val){
		Class<?> type = f.getType();
		Object retVal = null;
		String name = type.getSimpleName();
		if(name.contains("int")){
			try {
				retVal = Integer.parseInt(val);
			} catch (NumberFormatException e) {
				System.err.println("Insert an integer value.");
			}
		}else if(name.contains("boolean")){
			if(val.equals("true")||val.equals("false"))
				retVal = Boolean.parseBoolean(val);
			else
				System.err.println("Insert a boolean value.");
		}else if(name.contains("double")){
			try {
				retVal = Double.parseDouble(val);
			} catch (NumberFormatException e) {
				System.err.println("Insert an double value.");
			}
		}else if(name.contains("short")){
			try {
				retVal = Short.parseShort(val);
			} catch (NumberFormatException e) {
				System.err.println("Insert an short value.");
			}
		}else if(name.contains("byte")){
			try {
				retVal = Byte.parseByte(val);
			} catch (NumberFormatException e) {
				System.err.println("Insert an byte value.");
			}
		}else if(name.contains("long")){
			try {
				if(val.endsWith("L"))
						val=val.substring(0,val.length()-1);
				retVal = Long.parseLong(val);
			} catch (NumberFormatException e) {
				System.err.println("Insert an long value.");
			}
		}else if(name.contains("float")){
			try {
				retVal = Float.parseFloat(val);
			} catch (NumberFormatException e) {
				System.err.println("Insert an float value.");
			}
		}else if(name.contains("String")){
			retVal = val;
		}else if(name.contains("char[]")){
			retVal=val.toCharArray();
		}else if(name.matches("char")){
			retVal=val.charAt(1);
		}
		else{
			System.err.println("Incompatible type to assign field! ");
		}
		
		return retVal;
	}
	
	public boolean isPrimitiveWrapper(Class <?> c){
		String name = c.getName();
		if(name.contains("Integer")){
			return true;
		}else if(name.contains("Boolean")){
			return true;
		}else if(name.contains("Double")){
			return true;
		}else if(name.contains("Short")){
			return true;
		}else if(name.contains("Byte")){
			return true;
		}else if(name.contains("Long")){
			return true;
		}else if(name.contains("Float")){
			return true;
		}else if(name.contains("Character")){
			return true;
		}
		
		return false;
	}
}
