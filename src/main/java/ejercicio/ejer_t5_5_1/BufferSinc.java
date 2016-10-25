package ejercicio.ejer_t5_5_1;

import java.util.concurrent.Semaphore;

class BufferSinc {

	private static final int BUFFER_SIZE = 10;
	
	private int[] datos = new int[BUFFER_SIZE];
	private int posInser = 0;
	private int posSacar = 0;
	
	private Semaphore nHuecos = new Semaphore(BUFFER_SIZE);
	private Semaphore nProductos = new Semaphore(0);
	private Semaphore emPosInser = new Semaphore(1);
	private Semaphore emPosSacar = new Semaphore(1);
	
	public void insertar(int dato) throws InterruptedException {		
		nHuecos.acquire();		
		
		emPosInser.acquire();
		datos[posInser] = dato;
		posInser = (posInser+1) % datos.length;
		emPosInser.release();
		
		nProductos.release();
	}

	public int sacar() throws InterruptedException {		
		nProductos.acquire();
		
		emPosSacar.acquire();
		int dato = datos[posSacar];
		posSacar = (posSacar+1) % datos.length;
		emPosSacar.release();
		
		nHuecos.release();	
		
		return dato;
	}
}