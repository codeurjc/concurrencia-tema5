package ejemplo.ejems_t5_4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IncLock {

	private double x = 0;
	private Lock xLock = new ReentrantLock(); 
	
	public void inc() {
		for (int i = 0; i < 10000000; i++) {
			xLock.lock();
			x = x + 1;	
			xLock.unlock();
		}
	}

	public void exec() throws InterruptedException {

		List<Thread> threads = new ArrayList<Thread>();		
		for(int i=0; i<3; i++){
			Thread t = new Thread(()->inc());
			threads.add(t);
			t.start();
		}

		for(Thread thread : threads){
			thread.join();
		}
		
		System.out.println("x:" + x);
	}
	
	public static void main(String[] args) throws InterruptedException {
		new IncLock().exec();
	}
}