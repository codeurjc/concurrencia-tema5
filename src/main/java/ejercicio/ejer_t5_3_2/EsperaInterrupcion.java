package ejercicio.ejer_t5_3_2;

public class EsperaInterrupcion {

	// Muestra un mensaje precedido por el nombre del hilo
	private void println(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + ":" + message);
	}

	private void mensajes() {
		String mensajes[] = { "La vida es bella", "O no...", "Los pajaritos cantan",
				"Y molestan..." };
		try {
			for (int i = 0; i < mensajes.length; i++) {
				Thread.sleep(1000);
				println(mensajes[i]);
			}
		} catch (InterruptedException e) {
			println("Se acabó!");
		}
	}

	public void exec() throws InterruptedException {

		println("Inicio del hilo de mensajes");
		Thread t = new Thread(() -> mensajes(), "Mensajes");
		t.start();

		long horaInicio = System.currentTimeMillis();
		println("Esperando hasta que el hilo termine");

		// Esperar hasta que el hilo termine
		while (t.isAlive()) {

			println("Todavía esperando...");
			// Esperar como máximo 1 second para ver si ha terminado el hilo
			t.join(1000);

			long tiempoEspera = System.currentTimeMillis() - horaInicio;

			if (tiempoEspera > 5000 && t.isAlive()) {
				println("Cansado de esperar!");
				t.interrupt();

				// Esperamos indefinidamente porque no debería tardar en
				// finalizar después de la interrupción
				t.join();
			}
		}

		println("Por fin!");
	}

	public static void main(String args[]) throws InterruptedException {
		new EsperaInterrupcion().exec();
	}
}