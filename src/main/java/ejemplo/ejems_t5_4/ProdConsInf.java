package ejemplo.ejems_t5_4;

import java.util.concurrent.Exchanger;

public class ProdConsInf {

	private Exchanger<Double> exchanger = new Exchanger<Double>();

	public void productor() {
		try {

			double productoL = 0;

			for (int i = 0; i < 10; i++) {
				productoL++;
				Thread.sleep(1000);
				exchanger.exchange(productoL);
			}

		} catch (InterruptedException e) {
		}
	}

	public void consumidor() {
		try {

			for (int i = 0; i < 10; i++) {
				double producto = exchanger.exchange(null);
				System.out.println("Producto: " + producto);
				Thread.sleep(1000);
			}

		} catch (InterruptedException e) {
		}
	}

	public void exec() {

		// Se crean inician dos hilos que llaman
		// a productor() y consumidor()

		new Thread(() -> productor()).start();
		new Thread(() -> consumidor()).start();
	}
	
	public static void main(String[] args) {
		new ProdConsInf().exec();
	}
}
