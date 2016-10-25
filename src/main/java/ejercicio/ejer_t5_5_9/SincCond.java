package ejercicio.ejer_t5_5_9;

import java.util.concurrent.CountDownLatch;

public class SincCond {
	private CountDownLatch cdD = new CountDownLatch(2);
	private CountDownLatch cdB = new CountDownLatch(1);
	private CountDownLatch cdG = new CountDownLatch(1);
	private CountDownLatch cdE = new CountDownLatch(1);
	private CountDownLatch cdH = new CountDownLatch(1);
	private CountDownLatch cdC = new CountDownLatch(1);

	public void proceso1() {
		try {

			System.out.println("A");
			cdD.countDown();

			cdB.await();
			System.out.println("B");
			cdE.countDown();
			cdH.countDown();

			cdC.await();
			System.out.println("C");

		} catch (InterruptedException e) {
		}
	}

	public void proceso2() {
		try {
			
			cdD.await();
			System.out.println("D");
			cdB.countDown();
			cdG.countDown();

			cdE.await();
			System.out.println("E");
			cdC.countDown();
			
		} catch (InterruptedException e) {
		}
	}

	public void proceso3() {
		try {
			
			System.out.println("F");
			cdD.countDown();

			cdG.await();
			System.out.println("G");

			cdH.await();
			System.out.println("H");
			
		} catch (InterruptedException e) {
		}
	}

	public void exec() {

		new Thread(() -> proceso1()).start();
		new Thread(() -> proceso2()).start();
		new Thread(() -> proceso3()).start();
	}

	public static void main(String[] args) {
		new SincCond().exec();
	}

}
