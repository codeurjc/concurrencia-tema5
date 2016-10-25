package ejemplo.ejems_t5_5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedCollectionsSample2 {

	private List<String> sharedList;

	private void process(int num) {
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep((long) (Math.random() * 500));
			} catch (InterruptedException e) {
			}

			sharedList.add("H" + num + "_I" + i);

			synchronized (sharedList) {
				for (String elem : sharedList) {
					System.out.print(elem + ",");
				}
				System.out.println();
			}
		}
	}

	public void exec() throws InterruptedException {

		sharedList = Collections.synchronizedList(new ArrayList<String>());

		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < 3; i++) {
			int num = i;
			Thread t = new Thread(() -> process(num));
			threads.add(t);
			t.start();
		}

		for (Thread t : threads) {
			t.join();
		}

		System.out.println("List: " + sharedList);
	}

	public static void main(String[] args) throws InterruptedException {
		new SynchronizedCollectionsSample2().exec();
	}
}
