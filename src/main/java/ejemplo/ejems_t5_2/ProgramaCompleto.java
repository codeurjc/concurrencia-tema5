package ejemplo.ejems_t5_2;

public class ProgramaCompleto {
	
	private void ejecutar() {		
		
		Thread thread = new Thread(new Runnable() {
			@Override public void run() {
				metodo();
			}
		}, "Hilo1");
			
		thread.start();
	}
	
	public void metodo(){
		System.out.println("Metodo ejecutado por un hilo");
	}
	
	public static void main(String[] args) {
		ProgramaCompleto prog = new ProgramaCompleto();
		prog.ejecutar();
	}
}
