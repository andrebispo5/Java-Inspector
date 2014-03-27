package ist.meic.pa;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The Class TypeValidator.
 * A simple type validator for the primitive types and strings. Converts the string given by the input to it's respective types
 * taking in account the representation of the primitive types in java (e.g. a String has to be input between quotation marks). 
 * Also has method to check if a certain object is a wrapper to a primitive type.
 */
public class TypeValidator {

	public enum Types {
        INTEGER("int",		"Integer",		"parseInt",		Integer.class, 		"[0-9]+", 			0, 0), 
        BOOLEAN("boolean",	"Boolean",		"parseBoolean",	Boolean.class, 		"true|false", 		0, 0),
        DOUBLE("double",	"Double",		"parseDouble",	Double.class, 		"[0-9]+\\.[0-9]+",  0, 0),
        SHORT("short",		"Short",		"parseShort",	Short.class,		"UNDEFINED",		0, 0),
        BYTE("byte",		"Byte",			"parseByte", 	Byte.class,			"UNDEFINED", 		0, 0),
        LONG("long",		"Long",			"parseLong", 	TypeValidator.class,"[0-9]+L", 			0,-1),
        FLOAT("float",		"Float",		"parseFloat", 	Float.class,		"[0-9]+f",			0,-1),
        STRING("String",	"notDefined",	"parseString",	TypeValidator.class,"\"(.+?)*\"",		1,-1),
        CHAR("char", 		"Character", 	"parseChar", 	TypeValidator.class,"\'.\'", 			1,-1);

        private String name;
        private String WrapperName;
        private String method;
        private Class<?> clss;
        private String regexDetection;
        private int startPos;
        private int endPos;
        
        
        private Types(String name, String WrapperName, String method, Class<?> cls, String regex, int start, int end) {
                this.name = name;
                this.WrapperName=WrapperName;
                this.method= method;
                this.clss = cls;
                this.regexDetection = regex;
                this.startPos = start;
                this.endPos=end;
        }
	};

	
	public Object assignValue(Field f, String val){
		Class<?> type = f.getType();
		Object retVal = null;
		String name = type.getSimpleName();
		for(Types t :Types.values()){
			if(name.contains(t.name)){
			try {
				String  methodName= t.method;
				Method meth = t.clss.getMethod(methodName, String.class);
				retVal = meth.invoke(t.clss, val);
			}catch (NumberFormatException e) {
				System.err.println("Insert a valid value.");
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			} catch (InvocationTargetException e) {
				System.err.println("Insert a valid value.");
				return null;
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				return null;
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			}
		}
		return retVal;
	}
	
	public boolean isPrimitiveWrapper(Class <?> c){
		String name = c.getName();
		for(Types t :Types.values()){
			if(name.contains(t.WrapperName)){
				return true;
			}
		}
		return false;
	}
	
	public static final char parseChar(String toParse) throws IllegalArgumentException{
		if(toParse.matches(Types.CHAR .regexDetection))
			return toParse.charAt(1);
		else
			throw new IllegalArgumentException();
	}
	
	public static final long parseLong(String toParse) throws NumberFormatException {
		if(toParse.matches(Types.LONG.regexDetection))
			return Long.parseLong(toParse.substring(0,toParse.length() -1));
		else
			throw new NumberFormatException();
		
	}
	
	public static final String parseString(String toParse) throws IllegalArgumentException{
		if(toParse.matches(Types.STRING.regexDetection))
			return toParse.substring(1,toParse.length() -1);
		else
			throw new IllegalArgumentException();
		
	}
}
