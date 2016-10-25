package ejercicio.ejer_t5_5_7;

import java.util.concurrent.CyclicBarrier;

public class DownloaderMultiple {

	private static final int N_FICHEROS = 7;
	private static final int N_FRAGMENTS = 10;
	private static final int N_THREADS = 3;

	private int[] file = new int[N_FRAGMENTS];

	private Object nextFragmentLock = new Object();
	private int nextFragment = 0;

	private CyclicBarrier barrier;

	private int downloadData(int numFragment) {
		try {
			Thread.sleep((long) (Math.random() * 1000));
		} catch (InterruptedException e) {
		}
		return numFragment * 2;
	}

	private void printFile() {
		System.out.println("--------------------------------------------------");
		System.out.print("File = [");
		for (int i = 0; i < N_FRAGMENTS; i++) {
			System.out.print(file[i] + ",");
		}
		System.out.println("]");
	}

	public void downloader() {

		for (int i = 0; i < N_FICHEROS; i++) {

			downloadFragments();

			try {
				System.out.println(Thread.currentThread().getName() + " waiting the next file");
				barrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void downloadFragments() {

		do {

			int localFragment = 0;

			synchronized (nextFragmentLock) {
				if (nextFragment < N_FRAGMENTS) {
					localFragment = nextFragment;
					nextFragment++;
				} else {
					break;
				}
			}

			System.out.println(
					Thread.currentThread().getName() + " starts downloading fragment " + localFragment);

			int data = downloadData(localFragment);

			System.out.println(
					Thread.currentThread().getName() + " ends downloading fragment " + localFragment);

			file[localFragment] = data;

		} while (true);
	}

	private void fileRefresh() {

		printFile();

		// Reset attributes
		nextFragment = 0;
		file = new int[N_FRAGMENTS];
	}

	public void exec() {

		barrier = new CyclicBarrier(N_THREADS, () -> fileRefresh());

		for (int i = 0; i < N_THREADS; i++) {
			new Thread(() -> downloader(), "Downloader_" + i).start();
		}
	}

	public static void main(String[] args) {
		new DownloaderMultiple().exec();
	}

}
