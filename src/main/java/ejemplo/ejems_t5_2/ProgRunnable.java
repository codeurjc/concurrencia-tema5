package ejemplo.ejems_t5_2;

public class ProgRunnable {
	
	public static void main(String[] args) {
		
		Runnable r = ()-> System.out.println("Hola caracola");
		Runnable r2 = ()-> System.out.println("Hola caracola2");
		
		r.run();
		r2.run();
		
	}

}
