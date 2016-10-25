package ejemplo.ejems_t5_4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SincBarreraLocksBien {

	private static final int NPROCESOS = 3;
	
	private int nProcesos = 0;
	private Lock procesosLock = new ReentrantLock();
	private Condition sb = procesosLock.newCondition();

	public void proceso() {
		
		System.out.println("A");

		procesosLock.lock();
			nProcesos++;
			if (nProcesos < NPROCESOS) {
				try {
					while (nProcesos < NPROCESOS) {
						sb.await();
					}
				} catch (InterruptedException e) {}
			} else {
				sb.signalAll();
			}
		procesosLock.unlock();
		
		System.out.println("B");
	}

	public void exec(){		
		for (int i = 0; i < NPROCESOS; i++) {
			new Thread(()->proceso()).start();
		}
	}
	
	public static void main(String[] args) {
		new SincBarreraLocksBien().exec();
	}
}
