package ejemplo.ejems_t5_4;

import java.util.ArrayList;
import java.util.List;

class Counter {
	
	private int x = 0;

	public synchronized void inc() {
		x = x + 1;
	}

	public int getValue() {
		return x;
	}
}

public class IncSynchronizedMethod {

	private Counter c = new Counter();

	public void inc() {
		for (int i = 0; i < 10000000; i++) {
			c.inc();
		}
	}

	public void exec() throws InterruptedException {

		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < 3; i++) {
			Thread t = new Thread(()->inc());
			threads.add(t);
			t.start();
		}

		for (Thread thread : threads) {
			thread.join();
		}

		System.out.println("x:" + c.getValue());
	}
	
	public static void main(String[] args) throws InterruptedException {
		new IncSynchronizedMethod().exec();
	}
}