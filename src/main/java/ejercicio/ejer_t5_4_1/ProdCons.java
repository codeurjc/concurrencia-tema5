package ejercicio.ejer_t5_4_1;

public class ProdCons {

	private SincCond sincCond;
	private volatile double producto;

	public void productor() {
		producto = Math.random();
		sincCond.signal();
	}

	public void consumidor() {
		sincCond.await();
		System.out.println("Producto: " + producto);
	}

	public void exec() {

		sincCond = new SincCond();
		
		new Thread(()-> productor()).start();
		new Thread(()-> consumidor()).start();
	}
	
	public static void main(String[] args) {
		new ProdCons().exec();
	}
}
