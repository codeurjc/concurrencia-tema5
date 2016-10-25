package ejercicio.ejer_t5_6_2;

public class Aeropuerto {

	private String nombre;
	private String ciudad;
	private int capacidad;
	private int numPistas;

	public Aeropuerto(String nombre, String ciudad, int capacidad, int numPistas) {
		super();
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.capacidad = capacidad;
		this.numPistas = numPistas;
	}

	public String getCiudad() {
		return ciudad;
	}

	@Override
	public String toString() {
		return "Aeropuerto [nombre=" + nombre + ", ciudad=" + ciudad + ", capacidad=" + capacidad
				+ ", numPistas=" + numPistas + "]";
	}
}
