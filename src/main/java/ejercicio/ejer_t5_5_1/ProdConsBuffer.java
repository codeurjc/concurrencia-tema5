package ejercicio.ejer_t5_5_1;

public class ProdConsBuffer {

	private static final int PRODUCTORES = 5;
	private static final int CONSUMIDORES = 3;
	
	private BufferSinc buffer;

	private static void sleepRandom(long max) throws InterruptedException {
		Thread.sleep((long) (Math.random() * max));		
	}

	public void productor() {
		try {
			for (int i = 0; i < 20; i++) {
				sleepRandom(500);
				System.out.println("Producido:"+i);
				buffer.insertar(i);
			}
		} catch (InterruptedException e) {
		}
	}

	public void consumidor() {
		try {
			while (true) {
				int data = buffer.sacar();
				sleepRandom(1000);
				System.out.println("Consumido:"+data);
			}
		} catch (InterruptedException e) {
		}
	}

	public void exec() {

		buffer = new BufferSinc();

		for (int i = 0; i < PRODUCTORES; i++) {
			new Thread(()->productor(),"Productor " + i).start();
		}

		for (int i = 0; i < CONSUMIDORES; i++) {
			new Thread(()->consumidor(),"Consumidor " + i).start();
		}
	}
	
	public static void main(String[] args){
		new ProdConsBuffer().exec();
	}
}
