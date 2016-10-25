package ejemplo.ejems_t5_2;

public class ProgramaJoin {
	
	public void metodo(){
		for(int i=0; i<3; i++){
			System.out.println("Iteración "+i);
		}
	}
	
	public void exec() throws InterruptedException {
		
		Thread thread = new Thread(()->	metodo());
		
		thread.start();
		thread.join();
		
		System.out.println("Programa finalizado");
	}
	
	public static void main(String[] args) throws InterruptedException {
		new ProgramaJoin().exec();
	}
}
