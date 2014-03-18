package ist.meic.pa;



public class myProgram {

	
	public static void main(String[] args) {
		
		Inspector insp = new Inspector();
		try {
			E classE = new E();
			classE.setC("Hello World!");
			classE.setD(42);
			classE.f=true;
			classE.setValue(100);
			
			
			insp.inspect(classE);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
