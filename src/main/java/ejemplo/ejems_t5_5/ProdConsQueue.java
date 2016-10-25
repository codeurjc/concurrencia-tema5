package ejemplo.ejems_t5_5;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProdConsQueue {

	private BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

	public void productor() {
		try {
			for (int i = 0; i < 20; i++) {
				Thread.sleep((long) (Math.random() * 500));
				queue.put(i);
			}
		} catch (InterruptedException e) {
		}
	}

	public void consumidor() {
		try {
			while (true) {
				int data = queue.take();
				Thread.sleep((long) (Math.random() * 500));
				System.out.println(data + " ");
			}
		} catch (InterruptedException e) {
		}
	}

	public void exec(){

		for (int i = 0; i < 5; i++) {
			new Thread(()->productor()).start();
		}

		for (int i = 0; i < 3; i++) {
			new Thread(()->consumidor()).start();
		}
	}
	
	public static void main(String[] args) {
		new ProdConsQueue().exec();
	}
}
