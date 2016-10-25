package ejercicio.ejer_t5_5_2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ejer_Museo {

	private volatile int personas = 0;
	private Lock lock = new ReentrantLock();

	public void persona() {

		while (true) {

			boolean regalo = false;
			lock.lock();
			try {
				if (personas == 0) {
					regalo = true;
				}
				personas++;
				System.out.println("hola, somos " + personas);
			} finally {
				lock.unlock();
			}

			if (regalo) {
				System.out.println("Tengo regalo");
			} else {
				System.out.println("No tengo regalo");
			}

			System.out.println("qué bonito!");
			System.out.println("alucinante!");

			lock.lock();
			try {
				personas--;
				System.out.println("adiós a los " + personas);
			} finally {
				lock.unlock();
			}

			System.out.println("paseo");
		}
	}

	public void exec() {
		for (int i = 0; i < 3; i++) {
			new Thread(() -> persona()).start();
		}
	}

	public static void main(String[] args) {
		new Ejer_Museo().exec();
	}
}
