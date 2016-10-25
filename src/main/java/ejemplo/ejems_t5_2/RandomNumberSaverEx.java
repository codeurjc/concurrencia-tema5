package ejemplo.ejems_t5_2;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RandomNumberSaverEx {

	public void generateNumbers() {
		try {
			FileWriter writer = new FileWriter("output.txt");
			while (true) {
				BigInteger prime = BigInteger.probablePrime(1024, new Random());
				writer.append(prime.toString());
				writer.append("\r\n");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					writer.append("Fin fichero");
					writer.close();
					return;
				}
			}
		} catch (IOException e) {
			System.err.println("Exception using file");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void exec() {

		Thread t = new Thread(() -> generateNumbers());
		t.start();

		Scanner teclado = new Scanner(System.in);
		System.out.print("Pulse ENTER para finalizar...");
		teclado.nextLine();

		t.interrupt();

		System.out.println("Hilo interrumpido.");
	}

	public static void main(String[] args) {
		new RandomNumberSaver().exec();
	}
}
