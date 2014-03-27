package ist.meic.pa;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The Class TypeValidator.
 * A simple type validator for the primitive types and strings. Converts the string given by the input to it's respective types
 * taking in account the representation of the primitive types in java (e.g. a String has to be input between quotation marks). 
 * Also has method to check if a certain object is a wrapper to a primitive type.
 */
public class TypeValidator {

	
	/**
	 * Instantiates an object from Type C with value val. Iterates the enum structure to find the appropriate method and class
	 * @param c
	 * @param val
	 * @return retVal
	 */
	public Object createObjectWithValue(Class<?> c, String val){
		Object retVal = null;
		String name = c.getSimpleName();
		for(Types t :Types.values()){
			if(name.contains(t.name) || name.contains(t.WrapperName)){
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
	
	
	/**
	 * Returns a Class for the expected input. Iterates the enum structure to find the appropriate class
	 * @param input
	 * @return retClass
	 */
	public Class<?> createClassWithValue(String input){
		Class<?> retClass = null;
		for(Types t :Types.values()){
			if(input.matches(t.regexDetection)){
				try {
					retClass=Class.forName("java.lang."+t.forName);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return retClass;
	}
	
	/**
	 * Returns true if the input class is a primitive Wrapper
	 * @param c
	 * @return boolean
	 */
	public boolean isPrimitiveWrapper(Class <?> c){
		String name = c.getName();
		for(Types t :Types.values()){
			if(name.contains(t.WrapperName)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Parses the input string to a char
	 * @param toParse
	 * @return char
	 * @throws IllegalArgumentException
	 */
	public static final char parseChar(String toParse) throws IllegalArgumentException{
		if(toParse.matches(Types.CHAR .regexDetection))
			return toParse.charAt(1);
		else
			throw new IllegalArgumentException();
	}
	
	/**
	 * Parses the input string to a Long
	 * @param toParse
	 * @return Long
	 * @throws NumberFormatException
	 */
	public static final long parseLong(String toParse) throws NumberFormatException {
		if(toParse.matches(Types.LONG.regexDetection))
			return Long.parseLong(toParse.substring(0,toParse.length() -1));
		else
			throw new NumberFormatException();
		
	}
	
	/**
	 * Parses the input string to a String
	 * @param toParse
	 * @return String
	 * @throws IllegalArgumentException
	 */
	public static final String parseString(String toParse) throws IllegalArgumentException{
		if(toParse.matches(Types.STRING.regexDetection))
			return toParse.substring(1,toParse.length() -1);
		else
			throw new IllegalArgumentException();
		
	}
}
