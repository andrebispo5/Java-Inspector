package ist.meic.pa;

import java.lang.reflect.Field;

public class TypeValidator {

	public Object assignValue(Field f, String val){
		Class<?> type = f.getType();
		Object retVal = null;
		String name = type.getName();
		if(name.contains("int")){
			retVal = Integer.parseInt(val);
		}else if(name.contains("boolean")){
			retVal = Boolean.parseBoolean(val);
		}else if(name.contains("double")){
			retVal = Double.parseDouble(val);
		}else if(name.contains("short")){
			retVal = Short.parseShort(val);
		}else if(name.contains("byte")){
			retVal = Byte.parseByte(val);
		}else if(name.contains("long")){
			retVal = Long.parseLong(val);
		}else if(name.contains("float")){
			retVal = Float.parseFloat(val);
		}else if(name.contains("String")){
			retVal = val;
		}else{
			System.err.println("Incompatible type to assign field! ");
		}
		
		return retVal;
	}
	
	public boolean isPrimitive(Class <?> c){
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
		}else{
			System.err.println("Incompatible type to assign field!");
		}
		return false;
	}
}
