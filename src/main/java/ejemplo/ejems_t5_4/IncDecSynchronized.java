package ejemplo.ejems_t5_4;

import java.util.ArrayList;
import java.util.List;

public class IncDecSynchronized {

	private double x = 0;
	private Object xLock = new Object(); 
	
	public void inc() {
		for (int i = 0; i < 10000000; i++) {
			synchronized (xLock) {
				x = x + 1;	
			}
		}
	}
	
	public void dec() {
		for (int i = 0; i < 10000000; i++) {
			synchronized (xLock) {
				x = x - 1;	
			}
		}
	}

	public void exec() throws InterruptedException{

		List<Thread> threads = new ArrayList<Thread>();
		
		for(int i=0; i<3; i++){
			Thread t = new Thread(()->inc());
			threads.add(t);
			t.start();
		}
		
		for(int i=0; i<3; i++){
			Thread t = new Thread(()->dec());
			threads.add(t);
			t.start();
		}

		for(Thread thread : threads){
			thread.join();
		}
		
		System.out.println("x:" + x);
	}
	
	public static void main(String[] args) throws InterruptedException {
		new IncDecSynchronized().exec();
	}
}