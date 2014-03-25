package ist.meic.pa;
import java.util.ArrayList;
import java.util.HashMap;

public class Navigator {

	private static final int WINDOW_NAV_SIZE = 3; 
	private int currentPosition;
	private ArrayList<Object> objectArray;
	private HashMap<String, Object> savedObjects;
	
	public Navigator(){
		objectArray = new ArrayList<Object>();
		savedObjects = new HashMap<String, Object>();
		currentPosition=-1;
		for(int i=0;i<=20;i++){
			objectArray.add(i);
		}
	}
	
	public void add(Object obj){
		if(!objectArray.contains(obj)){
			if(currentPosition != -1){
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
	
	public void goTo(int dest){
		currentPosition=dest;
	}
		
	public Object next(){
		if(currentPosition < objectArray.size()-1)
			currentPosition+=1;
		return objectArray.get(currentPosition);
	}
	
	public Object previous(){
		if(currentPosition > 0)
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
		System.err.println("« « « Navigation Bar » » »");
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

	public void updateExistingObject(Object fieldValue) {
		if(objectArray.contains(fieldValue)){
			int posStoredObject = objectArray.indexOf(fieldValue);
			objectArray.set(posStoredObject,fieldValue);
		}	
	}
}
	