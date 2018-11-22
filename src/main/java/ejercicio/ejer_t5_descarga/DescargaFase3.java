package ejercicio.ejer_t5_descarga;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DescargaFase3 {

	private static final boolean EXIT_ON_EXCEPTION = true;

	private String downloadURL(URL website) throws IOException, InterruptedException {

		URLConnection connection = website.openConnection();

		try(BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

			StringBuilder response = new StringBuilder();
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}
				response.append(inputLine);
			}

			return response.toString();
		}
	}

	public List<String> loadURLs() throws IOException {
		return Files.readAllLines(Paths.get("webs.txt"));
	}

	private void exec() {

		ExecutorService executor = Executors.newFixedThreadPool(10);
		CompletionService<DownloadedWeb> cService = new ExecutorCompletionService<>(executor);

		List<DownloadedWeb> webs = new ArrayList<>();
		long totalChars = 0;

		long startTime = System.currentTimeMillis();

		try {

			List<String> urls = loadURLs();
			for (String url : urls) {
				cService.submit(() -> downloadAndProcessWeb(url));
			}
			try {
				for (int i = 0; i < urls.size(); i++) {

					DownloadedWeb dWeb = cService.take().get();
					webs.add(dWeb);

					String webContent = dWeb.getWebContent();
					if (webContent != null) {
						totalChars += dWeb.getWebContent().length();
					} else {
						if (EXIT_ON_EXCEPTION) {
							break;
						}
					}
				}

			} catch (InterruptedException e) {
			} catch (ExecutionException e) {
				System.out.println("Exception processing web: " + e.getMessage());
			}

			executor.shutdownNow();

			long totalTime = System.currentTimeMillis() - startTime;

			printFinalReport(totalChars, totalTime, webs);

			System.exit(0);

		} catch (IOException e) {
			System.out.println("Error reading webs.txt file: " + e.getMessage());
		}
	}

	private void printFinalReport(long totalChars, long totalTime, List<DownloadedWeb> webs) {

		System.out.println();
		System.out.println("Final report");
		System.out.println();
		System.out.println("Total downloaded chars: " + totalChars);
		System.out.println("Total time: " + totalTime + "ms");
		System.out.println();

		for (DownloadedWeb web : webs) {
			System.out.println("Web: " + web.getUrl());
			String webContent = web.getWebContent();
			if (webContent != null) {
				System.out.println("Chars: " + web.getWebContent().length());
			} else {
				System.out.println("Error: " + web.getException().getClass().getName());
			}
			System.out.println();
		}
	}

	private DownloadedWeb downloadAndProcessWeb(String url) {

		try {

			long startTime = System.currentTimeMillis();

			synchronized (this) {
				System.out.println("Start downloading: " + url.trim());
			}

			String webContent = downloadURL(new URL(url));

			int chars = webContent.length();

			long time = System.currentTimeMillis() - startTime;

			synchronized (this) {
				System.out.println("Downloaded web " + url);
				System.out.println("   Characters: " + chars);
				System.out.println("   First chars: " + webContent.substring(0, 100));
				System.out.println("   Time: " + time + "ms");
				System.out.println();
			}

			return new DownloadedWeb(url, webContent);

		} catch (Exception e) {

			synchronized (this) {
				System.out.println("Error downloading web " + url + ": " + e.getClass().getName());
			}

			return new DownloadedWeb(url, e);
		}
	}

	public static void main(String[] args) {
		new DescargaFase3().exec();
	}
}
