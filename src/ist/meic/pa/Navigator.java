package ist.meic.pa;
import java.util.ArrayList;
import java.util.HashMap;

public class Navigator {

	private int currentPosition;
	private ArrayList<Object> objectArray;
	private HashMap<String, Object> savedObjects;
	
	public Navigator(){
		objectArray = new ArrayList<Object>();
		savedObjects = new HashMap<String, Object>();
		currentPosition=-1;
	}
	
	public void add(Object obj){
		
		objectArray.add(obj);
		currentPosition+=1;
	}
	
	public void goTo(int dest){
		currentPosition=dest;
	}
		
	public Object next(){
		currentPosition+=1;
		return objectArray.get(currentPosition);
	}
	
	public Object previous(){
		currentPosition-=1;
		return objectArray.get(currentPosition);
	}
	
	public Object getObject(){
		return objectArray.get(currentPosition);
	}
	
	public void saveObject(String name){
		Object obj = getObject();
		savedObjects.put(name,obj);
	}
	
	public void printGraph(){
		for(int i=0;i<objectArray.size();i++){
			System.err.println("[" + i + "]" + objectArray.get(i));
		}
	}
	
	public void printSavedObjects(){
		for ( String key : savedObjects.keySet() ) {
			System.err.println("@" + key);
		}
	}
}
	