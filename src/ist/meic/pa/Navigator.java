package ist.meic.pa;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Class Navigator.
 * Provides a navigation bar to show the state of the inspection to the user.
 */
public class Navigator {

	private static final int EMPTY_ARRAY_POSITION = -1; 
	private static final int WINDOW_NAV_SIZE = 5; 
	private int currentPosition;
	private ArrayList<Object> objectArray;
	private HashMap<String, Object> savedObjects;
	
	public Navigator(){
		objectArray = new ArrayList<Object>();
		savedObjects = new HashMap<String, Object>();
		currentPosition=EMPTY_ARRAY_POSITION;
	}
	
	/**
	 * Adds the object being inspected to the navigation bar. If the object already exists in the navigation bar, sets the 
	 * current position to the position of that object.
	 *
	 * @param obj 	The object being inspected
	 */
	public void add(Object obj){
		if(!objectArray.contains(obj)){
			if(currentPosition != EMPTY_ARRAY_POSITION){
				objectArray.subList(currentPosition+1, objectArray.size()).clear();
			}
			objectArray.add(obj);
			currentPosition+=1;
		}else{
			int objPos = objectArray.indexOf(obj);
			objectArray.set(objPos, obj);
			currentPosition=objPos;
		}
	}
	
	/**
	 * Sets the current position to the object chosen by the user.
	 *
	 * @param dest 	The position of the object chosen by the user
	 */

	public void goTo(int dest){
		currentPosition=dest;
	}
	
	/**
	 * Moves 1 step forward in the inspection.
	 */
	public Object next(){
		if(currentPosition < objectArray.size()-1)
			currentPosition+=1;
		return objectArray.get(currentPosition);
	}
	
	/**
	 * Moves 1 step backward in the inspection.
	 */
	public Object previous(){
		if(currentPosition > 0)
			currentPosition-=1;
		return objectArray.get(currentPosition);
	}
	
	/**
	 * Gets the object in the current position.
	 */
	public Object getObject(){
		return objectArray.get(currentPosition);
	}
	
	/**
	 * Saves an object with a name specified by the user.
	 */
	public void saveObject(String name){
		Object obj = getObject();
		savedObjects.put(name,obj);
	}
	
	/**
	 * Prints the navigation bar when the user uses the command g without arguments.
	 */
	public void printGraph(){
		for(int i=0;i<objectArray.size();i++){
			Object obj = objectArray.get(i);
			String cName = obj.getClass().getSimpleName();
			System.err.println("[" + i + "] Object " + cName +" value "+ obj+" ");
		}
	}
	
	/**
	 * Prints the saved objects.
	 */
	public void printSavedObjects(){
		for ( String key : savedObjects.keySet() ) {
			System.err.println("@" + key);
		}
	}
	
	/**
	 * Returns the number of objects in the graph.
	 */
	public int maxOption() {
		int size = objectArray.size();
		return size;
	}
	
	/**
	 * Returns the list of saved objects
	 */
	public Object getSavedObject(String name){
		return savedObjects.get(name);
	}
	
	/**
	 * Prints the navigation bar.
	 */
	public void printNavigationBar(){
		int[] pos = getStartEndPositions();
		int start = pos[0];
		int end = pos[1];
		System.err.println("\n--------Navigation Bar--------");
		for(int i = start; i<end ; i++){
			Object o = objectArray.get(i);
			String cName = o.getClass().getSimpleName();
			String objToString = String.valueOf(o); 
			if(i == currentPosition)
				System.err.print("[X]" + cName + "=" + objToString + "   " );
			else
				System.err.print("["+ i +"]"+ cName + "=" + objToString + "   " );
		}
		System.err.println("");
	}
	
	/**
	 * Computes the Start and End Position of navigation window.
	 */
	public int[] getStartEndPositions(){
		int l = objectArray.size();
		int start = (currentPosition-(WINDOW_NAV_SIZE/2));
		int end = start + (WINDOW_NAV_SIZE);
		if(start<0){
			start=0;
			end = WINDOW_NAV_SIZE;
		}
		if(l > WINDOW_NAV_SIZE){
			if(end>l){
				start-=end%l;
				end=l;
			}
		}
		else
			end=l;
		return new int[]{start,end};
	}
	
	/**
	 * Updates the value, in the navigation bar, of an object that was modified via the command m.
	 *
	 * @param fieldValue 	The modified object
	 */
	public void updateExistingObject(Object toUpdate, Object fieldValue) {
		if(objectArray.contains(toUpdate)){
			int posStoredObject = objectArray.indexOf(toUpdate);
			objectArray.set(posStoredObject,fieldValue);
		}	
	}
}
	