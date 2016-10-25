package ejercicio.ejer_t5_6_1;

import java.util.ArrayList;
import java.util.List;

public class ListaStrings {

	public static void main(String[] args) {

		List<String> nombres = new ArrayList<String>();

		nombres.add("Pepe");
		nombres.add("Juan");
		nombres.add("Antonio");

		System.out.println(nombres);
		System.out.println("Tamaño: " + nombres.size());

		String primero = nombres.remove(0);

		System.out.println("Primero: " + primero);
		System.out.println(nombres);
		System.out.println("Tamaño: " + nombres.size());

	}

}
