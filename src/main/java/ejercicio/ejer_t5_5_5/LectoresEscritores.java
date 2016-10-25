package ejercicio.ejer_t5_5_5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LectoresEscritores {

	private ReentrantLock lock = new ReentrantLock();

	private Condition escritoresCond = lock.newCondition();
	private Condition lectoresCond = lock.newCondition();
	
	private boolean escritoresBD = false;
	private int numLectoresBD = 0;
	
	//Evita que un nuevo lector se "cuele" entre escritores
	private boolean escritorPreparado = false;

	public void inicioLectura() {
		lock.lock();
		try {
			try {
				while (escritorPreparado || escritoresBD || lock.hasWaiters(escritoresCond)) {
					lectoresCond.await();
				}
			} catch (InterruptedException e) {
			}
			numLectoresBD++;
		} finally {
			lock.unlock();
		}
	}

	public void finLectura() {
		lock.lock();
		try {
			numLectoresBD--;
			if (numLectoresBD == 0 && lock.hasWaiters(escritoresCond)) {
				escritoresCond.signal();
			}
		} finally {
			lock.unlock();
		}
	}

	public void inicioEscritura() {
		lock.lock();
		try {
			try {
				while (numLectoresBD > 0 || escritoresBD) {
					escritoresCond.await();
					escritorPreparado = false;
				}
			} catch (InterruptedException e) {
			}
			escritoresBD = true;
		} finally {
			lock.unlock();
		}
	}

	public void finEscritura() {
		lock.lock();
		try {
			escritoresBD = false;
			if (lock.hasWaiters(escritoresCond)) {
				escritorPreparado = true;
				escritoresCond.signal();
			} else {
				lectoresCond.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}

	public void lector() {
		while (true) {
			inicioLectura();
			System.out.println("Leer datos 1");
			sleep();
			System.out.println("Leer datos 2");
			finLectura();
			System.out.println("Procesar datos");
			sleep();
		}
	}

	public void escritor() {
		while (true) {
			System.out.println("Generar datos");
			inicioEscritura();
			System.out.println("Escribir datos 1");
			sleep();
			System.out.println("Escribir datos 2");
			finEscritura();
			sleep();
		}
	}

	public void exec() {
		for (int i = 0; i < 5; i++) {
			new Thread(() -> lector()).start();
		}

		for (int i = 0; i < 3; i++) {
			new Thread(() -> escritor()).start();
		}
	}

	public static void main(String[] args) {
		new LectoresEscritores().exec();
	}
	
	private void sleep() {
		try {
			Thread.sleep((long) (Math.random()*500));
		} catch (InterruptedException e) {}
	}
}