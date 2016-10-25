package ejemplo.ejems_t5_5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SynchronizedCollectionsSample3 {

	private Map<String, Integer> sharedMap;

	private void process(int num) {
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep((long) (Math.random() * 500));
			} catch (InterruptedException e) {
			}

			sharedMap.put("H" + num, i);

			synchronized (sharedMap) {
				for (Entry<String, Integer> elem : sharedMap.entrySet()) {
					System.out.print(elem.getKey() + ">" + elem.getValue()
							+ ",");
				}
				System.out.println();
			}
		}
	}

	public void exec() throws InterruptedException {

		sharedMap = Collections.synchronizedMap(new HashMap<String, Integer>());

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

		System.out.println("Map: " + sharedMap);
	}
	
	public static void main(String[] args) throws InterruptedException {
		new SynchronizedCollectionsSample3().exec();
	}
}
