package ejercicio.ejer_t5_descarga;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DescargaFase2 {

	private static final boolean EXIT_ON_EXCEPTION = true;

	private List<DownloadedWeb> webs = Collections.synchronizedList(new ArrayList<>());

	private AtomicInteger totalChars = new AtomicInteger();

	private CountDownLatch finishedTasks;

	private boolean aborted = false;

	private String downloadURL(URL website) throws IOException {

		URLConnection connection = website.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		StringBuilder response = new StringBuilder();
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);

		in.close();

		return response.toString();
	}

	public List<String> loadURLs() throws IOException {
		return Files.readAllLines(Paths.get("webs.txt"));
	}

	private void exec() throws InterruptedException {

		List<String> urls;
		try {
			urls = loadURLs();
		} catch (IOException e) {
			System.out.println("Error reading webs.txt file: " + e.getMessage());
			return;
		}

		finishedTasks = new CountDownLatch(urls.size());

		long startTime = System.currentTimeMillis();

		for (String url : urls) {
			new Thread(() -> downloadAndProcessWeb(url)).start();
		}

		finishedTasks.await();

		long totalTime = System.currentTimeMillis() - startTime;

		printFinalReport(totalTime);

		System.exit(0);

	}

	private synchronized void printFinalReport(long totalTime) {

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

	private void downloadAndProcessWeb(String url) {
		try {

			long startTime = System.currentTimeMillis();

			synchronized (this) {
				System.out.println("Start downloading: " + url.trim());
			}

			String webContent = downloadURL(new URL(url));

			int chars = webContent.length();
			totalChars.addAndGet(chars);

			long time = System.currentTimeMillis() - startTime;

			if (!aborted) {

				synchronized (this) {
					System.out.println("Downloaded web " + url);
					System.out.println("   Characters: " + chars);
					System.out.println("   First chars: " + webContent.substring(0, 100));
					System.out.println("   Time: " + time + "ms");
					System.out.println();
				}

				webs.add(new DownloadedWeb(url, webContent));

				finishedTasks.countDown();
			}

		} catch (Exception e) {

			synchronized (this) {
				System.out.println("Error downloading web " + url + ": " + e.getClass().getName());
			}

			webs.add(new DownloadedWeb(url, e));

			if (EXIT_ON_EXCEPTION) {
				aborted = true;
				while (finishedTasks.getCount() != 0) {
					finishedTasks.countDown();
				}
			} else {
				finishedTasks.countDown();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new DescargaFase2().exec();
	}
}
