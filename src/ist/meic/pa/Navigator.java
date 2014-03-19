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
		if(!objectArray.contains(obj)){
			objectArray.add(obj);
			currentPosition=objectArray.size()-1;
		}else{
			int objPos = objectArray.indexOf(obj);
			objectArray.set(objPos, obj);
			currentPosition=objPos;
		}
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
			Object obj = objectArray.get(i);
			String cName = obj.getClass().getSimpleName();
			System.err.println("[" + i + "] Object «" + cName +"» value «"+ obj+"» ");
		}
	}
	
	public void printSavedObjects(){
		for ( String key : savedObjects.keySet() ) {
			System.err.println("@" + key);
		}
	}

	public int maxOption() {
		int size = objectArray.size();
		return size;
	}
	
	public Object getSavedObject(String name){
		return savedObjects.get(name);
	}
	
	public void printNavigationBar(){
		System.err.println(" ");
		System.err.println("« « « Navigation Bar » » »");
		for(Object o : objectArray){
			String cName = o.getClass().getSimpleName();
			String value = o.toString();; 
			if(value.contains(o.getClass().getPackage().getName()))
				value = value.substring(o.getClass().getPackage().getName().length()+1, value.length());
			if(objectArray.indexOf(o) == currentPosition)
				System.err.print("[X]" + cName + "=" + value + "   " );
			else
				System.err.print("["+ objectArray.indexOf(o)+"]"+ cName + "=" + value + "   " );
		}
		System.err.println("");
	}
}
	