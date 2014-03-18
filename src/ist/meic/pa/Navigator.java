package ist.meic.pa;
import java.util.ArrayList;

public class Navigator {

	private int currentPosition;
	private ArrayList<Object> objectArray;
	
	public Navigator(){
		objectArray = new ArrayList<Object>();
		currentPosition=-1;
	}
	
	public void add(Object obj){
		
		objectArray.add(obj);
		currentPosition+=1;
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
}
	