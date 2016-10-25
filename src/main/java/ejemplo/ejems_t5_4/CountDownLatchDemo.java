package ejemplo.ejems_t5_4;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

	private CountDownLatch latch = new CountDownLatch(4);

	public void runner() {
		try {
			System.out.println("Ready");
			latch.await();
			System.out.println("Running");
		} catch (InterruptedException e) {
		}
	}

	public void judge() {
		try {
			for (int i = 3; i >= 0; i--) {
				System.out.println(i);
				latch.countDown();
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
		}
	}

	public void exec() throws InterruptedException {

		for (int i = 0; i < 3; i++) {
			new Thread(() -> runner()).start();
		}

		new Thread(() -> judge()).start();
	}

	public static void main(String[] args) throws InterruptedException {
		new CountDownLatchDemo().exec();
	}
}
