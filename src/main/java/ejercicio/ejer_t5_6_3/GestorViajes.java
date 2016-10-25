package ejercicio.ejer_t5_6_3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GestorViajes {

	private List<Viaje> viajes = new ArrayList<Viaje>();
	private Map<String, List<Viaje>> viajesPorOrigen = new HashMap<>();
	private Map<String, List<Viaje>> viajesPorDestino = new HashMap<>();
	private Set<String> ciudades = new HashSet<>();

	public void addViaje(Viaje viaje) {
		viajes.add(viaje);
		put(viajesPorOrigen, viaje.getOrigen(), viaje);
		put(viajesPorDestino, viaje.getDestino(), viaje);
		ciudades.add(viaje.getOrigen());
		ciudades.add(viaje.getDestino());
	}

	private void put(Map<String, List<Viaje>> ciudadesViajes, String ciudad, Viaje viaje) {

		List<Viaje> viajes = ciudadesViajes.get(ciudad);
		if (viajes == null) {
			viajes = new ArrayList<Viaje>();
			ciudadesViajes.put(ciudad, viajes);
		}
		viajes.add(viaje);
	}

	public Collection<Viaje> getViajesOrigen(String origen) {
		return viajesPorOrigen.get(origen);
	}

	public Collection<Viaje> getViajesDestino(String destino) {
		return viajesPorDestino.get(destino);
	}

	public Collection<String> getCiudades() {
		return ciudades;
	}

	public Collection<Viaje> getViajes() {
		return viajes;
	}

}
