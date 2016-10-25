package ejemplo.ejems_t5_4;

public class SincBarreraSynchronizedBien {

	private static final int NPROCESOS = 3;
	
	private int nProcesos = 0;
	private Object procesosLock = new Object();
	
	public void proceso() {
		System.out.println("A");

		synchronized (procesosLock) {
			nProcesos++;
			if (nProcesos < NPROCESOS) {
				try {
					while (nProcesos < NPROCESOS) {
						procesosLock.wait();
					}
				} catch (InterruptedException e) {}
			} else {
				procesosLock.notifyAll();
			}
		}
		System.out.println("B");
	}
	
	public void exec(){		
		for (int i = 0; i < NPROCESOS; i++) {
			new Thread(()->proceso()).start();
		}
	}
	
	public static void main(String[] args) {
		new SincBarreraSynchronizedBien().exec();
	}
}
