package ejercicio.ejer_t5_5_3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LectoresEscritores {

	public void lector(Lock readLock) {
		while (true) {
			readLock.lock();
			System.out.println("Leer datos");
			readLock.unlock();
			System.out.println("Procesar datos");
		}
	}

	public void escritor(Lock writeLock) {
		while (true) {
			System.out.println("Generar datos");
			writeLock.lock();
			System.out.println("Escribir datos");
			writeLock.unlock();
		}
	}

	public void exec() {

		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		Lock readLock = readWriteLock.readLock();
		Lock writeLock = readWriteLock.writeLock();

		for (int i = 0; i < 5; i++) {
			new Thread(()->lector(readLock)).start();
		}

		for (int i = 0; i < 3; i++) {
			new Thread(()->escritor(writeLock)).start();
		}
	}
	
	public static void main(String[] args){
		new LectoresEscritores().exec();
	}
}
