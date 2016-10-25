package ejemplo.ejems_t5_4;

import java.util.ArrayList;
import java.util.List;

public class IncSynchronized2 {

	private double x = 0;
	private Object xLock = new Object();
	
	private double y = 0;
	private Object yLock = new Object();
	
	public void incX() {
		for (int i = 0; i < 10000000; i++) {
			synchronized (xLock) {
				x = x + 1;	
			}
		}
	}
	
	public void incY() {
		for (int i = 0; i < 10000000; i++) {
			synchronized (yLock) {
				y = y + 1;	
			}
		}
	}

	public void exec() throws InterruptedException {

		List<Thread> threads = new ArrayList<Thread>();		
		for(int i=0; i<3; i++){
			Thread t = new Thread(()->incX());
			threads.add(t);
			t.start();
		}
		
		for(int i=0; i<3; i++){
			Thread t = new Thread(()->incY());
			threads.add(t);
			t.start();
		}

		for(Thread thread : threads){
			thread.join();
		}
		
		System.out.println("x:" + x);
		System.out.println("y:" + x);
	}
	
	public static void main(String[] args) throws InterruptedException{
		new IncSynchronized2().exec();
	}
}