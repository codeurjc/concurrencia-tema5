package ejemplo.ejems_t5_2;

public class Programa {

	public void metodo(String saludo){
		System.out.println(saludo);
	}
	
	public void exec(){
		new Thread(()-> metodo("Hola")).start();
		new Thread(()-> metodo("Adios")).start();
	}
	
	public static void main(String[] args) {
		new Programa().exec();
	}	
}
