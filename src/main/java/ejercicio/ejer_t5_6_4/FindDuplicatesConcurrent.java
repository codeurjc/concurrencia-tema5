package ejercicio.ejer_t5_6_4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FindDuplicatesConcurrent {

	private static final int N_THREADS = 4;
	private ConcurrentMap<String, String> duplicates = new ConcurrentHashMap<>();
	private Object screenLock = new Object();

	private ExecutorService exec = Executors.newFixedThreadPool(N_THREADS);
	
	private AtomicInteger tareasPendientes = new AtomicInteger();	
	
	public void find(File dir) {
		findConcurrent(dir);
		
		//Esperar a que todas las tareas terminen
		try {
			exec.awaitTermination(2000000, TimeUnit.DAYS);
		} catch (InterruptedException e) {
		}
	}
	
	public void findConcurrent(File dir) {		

		for (File file : dir.listFiles()) {

			if (file.isDirectory()) {
				
				exec.execute(()-> findConcurrent(file));
				tareasPendientes.incrementAndGet();

			} else {

				String path = duplicates.putIfAbsent(file.getName(),
						file.getAbsolutePath());

				if (path != null) {
					synchronized (screenLock) {
						System.out.println(
								"Found duplicate file: " + file.getName());
						System.out.println("    " + path);
						System.out.println("    " + file.getAbsolutePath());
					}
				}
			}
		}
		
		if(tareasPendientes.decrementAndGet() == 0){
			exec.shutdown();
		}
	}

	public static void main(String[] args) {

		new FindDuplicatesConcurrent().find(new File(
				"/home/mica/Data/Docencia/Asignaturas/PC/2015-2016"));
		
		System.out.println("Terminado");

	}
}
