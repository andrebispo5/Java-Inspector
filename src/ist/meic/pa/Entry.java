//NOT USED FOR ANYTHING. DESIGN TIME DECISIONS
package ist.meic.pa;


public class Entry {

	private String type;
	private Object obj;
	
	public Entry(Object obj, String type){
		this.setType(type);
		this.setObj(obj);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
