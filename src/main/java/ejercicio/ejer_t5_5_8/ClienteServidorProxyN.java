package ejercicio.ejer_t5_5_8;

import java.util.concurrent.Exchanger;

public class ClienteServidorProxyN {

	private Exchanger<Double> peticion = new Exchanger<>();
	private Exchanger<Double> respuesta = new Exchanger<>();

	private Exchanger<Double> peticionProxy = new Exchanger<>();
	private Exchanger<Double> respuestaProxy = new Exchanger<>();

	public void client() {
		try {
			for (int i = 0; i < 10; i++) {
				double datoPeticionProxy = i;
				peticionProxy.exchange(datoPeticionProxy);
				double datoRespuestaProxy = respuestaProxy.exchange(null);
				System.out.println("Response: " + datoRespuestaProxy);
			}
		} catch (InterruptedException e) {
		}
	}

	public void proxy() {
		try {

			for (int i = 0; i < 10; i++) {				
				double datoPeticionProxy = peticionProxy.exchange(null);				
				double datoPeticion = datoPeticionProxy + 1;				
				peticion.exchange(datoPeticion);				
				double datoRespuesta = respuesta.exchange(null);
				respuestaProxy.exchange(datoRespuesta);
			}
		} catch (InterruptedException e) {
		}
	}

	public void server() {
		try {
			for (int i = 0; i < 10; i++) {
				double datoPeticion = peticion.exchange(null);
				double datoRespuesta = datoPeticion + 1;
				respuesta.exchange(datoRespuesta);
			}
		} catch (InterruptedException e) {
		}
	}

	public void exec() {
		new Thread(() -> client()).start();
		new Thread(() -> proxy()).start();
		new Thread(() -> server()).start();
	}

	public static void main(String[] args) {
		new ClienteServidorProxyN().exec();
	}
}
