package ejercicio.ejer_t5_5_6;

public class DataAccess {

	private static final int NUM_PROCESOS_TIPO = 3;

	private static final int ITERS = 5;

	private DataSourceManager dsManager = new DataSourceManager();

	public void ds0() {
		try {
			for(int i=0; i<ITERS; i++){
				Thread.sleep((long) Math.random() * 500);
				dsManager.accessDataSource(0);
				System.out.println("DataSource 0");
				dsManager.freeDataSource(0);
			}
		} catch (InterruptedException e) {
		}
	}

	public void ds1() {
		try {
			for(int i=0; i<ITERS; i++){
				Thread.sleep((long) Math.random() * 500);
				dsManager.accessDataSource(1);
				System.out.println("DataSource 1");
				dsManager.freeDataSource(1);
			}
		} catch (InterruptedException e) {
		}
	}

	public void dsAny() {
		try {
			for(int i=0; i<ITERS; i++){
				Thread.sleep((long) Math.random() * 500);
				int ds = dsManager.accessAnyDataSource();
				System.out.println("DataSource Any (" + ds + ")");
				dsManager.freeDataSource(ds);
			}
		} catch (InterruptedException e) {
		}
	}

	public void exec() {

		for (int i = 0; i < NUM_PROCESOS_TIPO; i++) {
			new Thread(()-> ds0()).start();
			new Thread(()-> ds1()).start();
			new Thread(()-> dsAny()).start();
		}
	}

	public static void main(String[] args) {
		new DataAccess().exec();
	}
}