package ejercicio.ejer_t5_6_3;


public class Viaje {

	private String origen;
	private String destino;
	private float duracion;
	
	protected Viaje(String origen, String destino, float duracion) {
		this.origen = origen;
		this.destino = destino;
		this.duracion = duracion;
	}

	public String getOrigen() {
		return origen;
	}

	public String getDestino() {
		return destino;
	}
	
	public float getDuracion() {
		return duracion;
	}
	
	@Override
	public String toString() {
		return "Viaje de "+this.origen+" a "+this.destino+" ("+duracion+")";
	}

}
