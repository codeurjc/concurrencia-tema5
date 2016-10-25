package ejercicio.ejer_t5_4_1;

public class SincCond {
	
	private volatile boolean signal = false;

	public void signal() {
		signal = true;		
	}

	public void await() {
		while (!signal);
		signal = false;
	}
}
