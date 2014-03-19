package ist.meic.pa;

import ist.meic.pa.dummys.E;



public class myProgram {

	
	public static void main(String[] args) {
		E classE = new E();
		classE.setC("Hello World!");
		classE.setD(42);
		classE.setF(true);
		classE.setValue(100);
		
		Inspector insp = new Inspector();
		insp.inspect(classE);
	}

}
