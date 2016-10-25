package ejercicio.ejer_t5_6_2;

import java.util.HashMap;
import java.util.Map;

public class Aena {

	public static void main(String[] args) {

		Map<String, Aeropuerto> aeropuertos = new HashMap<>();
		
		addAeropuerto(aeropuertos, new Aeropuerto("Barajas", "Madrid", 10, 4));
		addAeropuerto(aeropuertos, new Aeropuerto("Prat", "Barcelona", 6, 5));
		addAeropuerto(aeropuertos, new Aeropuerto("Aeropuerto de Castellón", "Castellón", 0, 4));

		Aeropuerto a = aeropuertos.get("Madrid");
		
		System.out.println(a);
	}
	
	public static void addAeropuerto(Map<String, Aeropuerto> aeropuertos, Aeropuerto aeropuerto){
		aeropuertos.put(aeropuerto.getCiudad(), aeropuerto);
	}

}
