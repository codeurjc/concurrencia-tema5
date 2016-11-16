package ejemplo.ejems_t5_5;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class CompletableFutureSandbox {

	public static class DownloadTwitterTask
			implements Supplier<String>, Callable<String> {

		@Override
		public String call() throws Exception {
			return null;
		}

		@Override
		public String get() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(10);

		Future<String> future = executor.submit(new DownloadTwitterTask());

		CompletableFuture<String> future2 = CompletableFuture
				.supplyAsync(new DownloadTwitterTask(), executor);

	}

}
