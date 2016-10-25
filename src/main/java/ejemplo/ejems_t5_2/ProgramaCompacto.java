package ejemplo.ejems_t5_2;

public class ProgramaCompacto {
	public static void main(String[] args) {

		Runnable r = new Runnable() {
			@Override public void run() {
				System.out.println("Soy un hilo");
			}
		};

		Thread thread = new Thread(r, "Hilo1");
		thread.start();
	}
}
