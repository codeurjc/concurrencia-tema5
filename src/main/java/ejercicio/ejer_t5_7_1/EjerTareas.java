package ejercicio.ejer_t5_7_1;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EjerTareas {

	public static final int N_TAREAS = 5;

	public String ejecutaTarea(int numTask) {

		try {
			Thread.sleep((long) (Math.random() * 500));
		} catch (InterruptedException e) {
		}

		if (Math.random() < 0.8) {
			return "Tarea " + numTask + " correcta";
		} else {
			throw new RuntimeException("Tarea " + numTask + " fallo");
		}
	}

	public void exec() throws InterruptedException, ExecutionException {

		ExecutorService executor = Executors.newFixedThreadPool(N_TAREAS);

		try {

			CompletionService<String> completionService = new ExecutorCompletionService<>(
					executor);

			for (int i = 0; i < N_TAREAS; i++) {
				int numTask = i;
				completionService.submit(() -> ejecutaTarea(numTask));
			}

			for (int i = 0; i < N_TAREAS; i++) {

				Future<String> completedTask = completionService.take();

				System.out.println("Task: " + completedTask.get());
			}

		} finally {
			executor.shutdown();
		}

	}

	public static void main(String[] args)
			throws InterruptedException, ExecutionException {
		new EjerTareas().exec();
	}

}
