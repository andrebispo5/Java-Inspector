package ist.meic.pa.dummys;


public class E extends B {

	private boolean f;
	private int[] x = {1,2,3,4};
	private long t = 2L;
	private byte b = 3;
	public byte getB() {
		return b;
	}

	public void setB(byte b) {
		this.b = b;
	}

	public short getS() {
		return s;
	}

	public void setS(short s) {
		this.s = s;
	}

	private short s = 4;
	
	public int g(int h){
		return getD()+h;
	}
	
	public int j(boolean b){
		return 1;
	}
	
	public boolean getF() {
		return f;
	}

	public void setF(boolean f) {
		this.f = f;
	}
	
	public void setT(long v){
		t=v;
	}

	public static float i =10.22f;
	
}
