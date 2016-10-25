package ejercicio.ejer_t5_6_6;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AlmacenPiezas {

	private static final int MAX_PIEZAS = 5;

	private List<Queue<Double>> colasPiezas;

	private Lock almacenLock = new ReentrantLock();
	private List<Condition> conditionsRobots = new ArrayList<Condition>();
	private List<Condition> conditionsMaquinas = new ArrayList<Condition>();

	private int[] robotsEsperando;
	private int[] maquinasEsperando;

	private boolean[] maquinaAlmacenandoPieza;
	private int robotRecogiendoPieza;
	
	public AlmacenPiezas(int numTiposPiezas) {

		this.conditionsRobots = new CopyOnWriteArrayList<Condition>();
		this.conditionsMaquinas = new CopyOnWriteArrayList<Condition>();
		this.colasPiezas = new CopyOnWriteArrayList<Queue<Double>>();

		for (int i = 0; i < numTiposPiezas; i++) {
			colasPiezas.add(new LinkedList<Double>());
			conditionsRobots.add(almacenLock.newCondition());
			conditionsMaquinas.add(almacenLock.newCondition());
		}

		this.robotRecogiendoPieza = -1;
		this.maquinaAlmacenandoPieza = new boolean[numTiposPiezas];

		this.robotsEsperando = new int[numTiposPiezas];
		this.maquinasEsperando = new int[numTiposPiezas];
	}

	public void almacenarPieza(int tipoPieza, double pieza) throws InterruptedException {

		inicioAlmacenarPieza(tipoPieza);

		Thread.sleep((long) (Math.random() * 1000));
		colasPiezas.get(tipoPieza).offer(pieza);

		finAlmacenarPieza(tipoPieza);
	}

	public double recogerPieza(int tipoPieza) throws InterruptedException {

		inicioRecogerPieza(tipoPieza);

		Thread.sleep((long) (Math.random() * 1000));
		double pieza = colasPiezas.get(tipoPieza).poll();

		finRecogerPieza(tipoPieza);

		return pieza;
	}

	private void inicioAlmacenarPieza(int tipoPieza) throws InterruptedException {

		// Cualquier número de máquinas puede almacenar concurrentemente piezas
		// de distinto tipo en el almacén de piezas. Sin embargo, dos máquinas
		// nunca pueden almacenar simultáneamente piezas del mismo tipo.

		// Una máquina puede almacenar una pieza y un robot puede recoger otra
		// de tipo distinto concurrentemente. Si las piezas son del mismo tipo,
		// el almacenamiento y la recogida debe hacerse bajo exclusión mutua.

		// Una máquina sólo puede almacenar una pieza de un cierto tipo si:
		// * El almacén tiene espacio para albergar piezas de ese tipo
		// * No hay ninguna otra máquina almacenando una pieza de ese tipo
		// * No hay ningún robot recogiendo una pieza de ese tipo.
		// En caso contrario, la máquina debe esperar para poder almacenar la
		// pieza.

		almacenLock.lock();
		try {

			do {
				boolean lleno = this.colasPiezas.get(tipoPieza).size() == MAX_PIEZAS;
				boolean maquinaMismaPiezaAlmac = this.maquinaAlmacenandoPieza[tipoPieza];
				boolean robotMismaPiezaRecog = this.robotRecogiendoPieza == tipoPieza;

				if (lleno || maquinaMismaPiezaAlmac || robotMismaPiezaRecog) {

					System.out.println(Thread.currentThread().getName()
							+ ": Bloqueado " + (lleno? "lleno " : "")
							+ (maquinaMismaPiezaAlmac? "maquinaMismaPiezaAlmac ":"") 
							+ (robotMismaPiezaRecog? "robotMismaPiezaRecog":""));

					this.maquinasEsperando[tipoPieza]++;
					this.conditionsMaquinas.get(tipoPieza).await();
					this.maquinasEsperando[tipoPieza]--;
				} else {
					break;
				}

			} while (true);

			maquinaAlmacenandoPieza[tipoPieza] = true;

			System.out.println(Thread.currentThread().getName() + ": Almacenando pieza "
					+ tipoPieza);

		} finally {
			almacenLock.unlock();
		}
	}

	private void finAlmacenarPieza(int tipoPieza) {

		// Cuando una máquina termina de almacenar una pieza de un cierto tipo:
		// * Primero comprueba si algún robot espera a recoger una pieza de ese
		// tipo y puede recogerla, en cuyo caso lo libera.
		// * Si no hubiera tal robot, comprueba si alguna otra máquina espera a
		// almacenar una pieza de ese tipo y puede almacenarla, en cuyo caso la
		// libera.

		almacenLock.lock();
		try {

			maquinaAlmacenandoPieza[tipoPieza] = false;

			if (robotsEsperando[tipoPieza] > 0) {
				
				System.out.println(Thread.currentThread().getName()
						+ ": Desbloquear robot de pieza " + tipoPieza);
				
				this.conditionsRobots.get(tipoPieza).signal();
				
			} else {
				
				if (this.maquinasEsperando[tipoPieza] > 0 && 
						this.colasPiezas.get(tipoPieza).size() < MAX_PIEZAS) {
					
					System.out.println(Thread.currentThread().getName()
							+ ": Desbloquear maquina de pieza " + tipoPieza);
				
					this.conditionsMaquinas.get(tipoPieza).signal();
				
				}
			}			

			System.out.println(Thread.currentThread().getName() + ": Fin almacenar pieza "
					+ tipoPieza);

		} finally {
			almacenLock.unlock();
		}
	}

	private void inicioRecogerPieza(int tipoPieza) throws InterruptedException {

		// Los robots siempre deben acceder al almacén de piezas bajo exclusión
		// mutua entre sí.

		// Una máquina puede almacenar una pieza y un robot puede recoger otra
		// de tipo distinto concurrentemente. Si las piezas son del mismo tipo,
		// el almacenamiento y la recogida debe hacerse bajo exclusión mutua.

		// Un robot sólo puede recoger una pieza de un cierto tipo si:
		// * Hay piezas de ese tipo en el almacén
		// * No hay ninguna máquina almacenando una pieza de ese tipo
		// * No hay ningún robot recogiendo una pieza de cualquier tipo.
		// En otro caso, el robot debe esperar para poder recoger la pieza.

		almacenLock.lock();
		try {

			do {
				boolean disponible = this.colasPiezas.get(tipoPieza).size() > 0;
				boolean maquinaMismaPiezaAlmac = this.maquinaAlmacenandoPieza[tipoPieza];
				boolean robotRecog = (this.robotRecogiendoPieza != -1);

				if (!disponible || maquinaMismaPiezaAlmac || robotRecog) {

					System.out.println(Thread.currentThread().getName()
							+ ": Bloqueado " + (!disponible? "!disponible " : "")
							+ (maquinaMismaPiezaAlmac? "maquinaMismaPiezaAlmac ":"") 
							+ (robotRecog? "robotRecog":""));

					this.robotsEsperando[tipoPieza]++;
					this.conditionsRobots.get(tipoPieza).await();
					this.robotsEsperando[tipoPieza]--;
				} else {
					break;
				}

			} while (true);

			robotRecogiendoPieza = tipoPieza;

			System.out.println(Thread.currentThread().getName() + ": Cogiendo pieza " + tipoPieza);

		} finally {
			almacenLock.unlock();
		}
	}

	private void finRecogerPieza(int tipoPieza) {

		// Cuando un robot termina de recoger una pieza de un cierto tipo:
		// * Primero busca algún otro robot que espere a recoger una pieza de
		// cualquier tipo y pueda recogerla, en cuyo caso lo libera. Esta
		// búsqueda se debe realizar de menor a mayor tipo de pieza.
		// * Después comprueba si alguna máquina espera a almacenar una pieza
		// del mismo tipo que la pieza que se ha terminado de recoger y, si
		// puede almacenarla, también la libera.

		almacenLock.lock();
		try {

			robotRecogiendoPieza = -1;
			
			for (int i = 0; i < robotsEsperando.length; i++) {
				if (robotsEsperando[i] > 0 && !this.colasPiezas.get(i).isEmpty()) {
					System.out.println(Thread.currentThread().getName()
							+ ": Desbloquear robot pieza " + tipoPieza);
					conditionsRobots.get(i).signal();
					break;
				}
			}

			if (this.maquinasEsperando[tipoPieza] > 0) {
				System.out.println(Thread.currentThread().getName() + ": Desbloquear máquina "
						+ tipoPieza);
				conditionsMaquinas.get(tipoPieza).signal();
			}

			System.out.println(Thread.currentThread().getName() + ": Fin coger pieza " + tipoPieza);

		} finally {
			almacenLock.unlock();
		}
	}
}
