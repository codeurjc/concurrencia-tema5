package ejercicio.ejer_t5_6_5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AlmacenPiezas {

	public static int MAX_PIEZAS = 5;
	
	private List<BlockingQueue<Double>> colas;
	
	public AlmacenPiezas(int numTiposPieza) {
		List<BlockingQueue<Double>> colasAux = new ArrayList<BlockingQueue<Double>>();
		for(int i=0; i<numTiposPieza; i++){
			colasAux.add(new LinkedBlockingQueue<Double>(MAX_PIEZAS));
		}
		colas = new ArrayList<BlockingQueue<Double>>(colasAux);
	}

	public void almacenarPieza(int tipoPieza, double pieza) throws InterruptedException {
		colas.get(tipoPieza).put(pieza);	
	}

	public double recogerPieza(int tipoPieza) throws InterruptedException {
		return colas.get(tipoPieza).take();
	}

}
