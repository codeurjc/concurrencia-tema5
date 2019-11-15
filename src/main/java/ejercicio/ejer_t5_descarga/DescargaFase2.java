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
import java.util.concurrent.atomic.AtomicInteger;

public class DescargaFase2 {

	private static final boolean EXIT_ON_EXCEPTION = true;

	private AtomicInteger totalChars = new AtomicInteger();

	private volatile boolean error = false;

	private String downloadURL(URL website) throws IOException {

		StringBuilder response = new StringBuilder();
				
		URLConnection connection = website.openConnection();
		
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream()))) {

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				if (Thread.interrupted()) {
					break;
				}
			}
		}

		return response.toString();
	}

	public List<String> loadURLs() throws IOException {
		return Files.readAllLines(Paths.get("webs.txt"));
	}

	private void exec() throws Exception {

		List<String> urls;
		try {
			urls = loadURLs();
		} catch (IOException e) {
			System.out.println("Error reading webs.txt file: " + e.getMessage());
			return;
		}

		List<DownloadedWeb> webs = Collections.synchronizedList(new ArrayList<>());

		long startTotalTime = System.currentTimeMillis();

		List<Thread> ts = new ArrayList<>();
		for (String url : urls) {
			Thread t = new Thread(() -> donwloadWeb(webs, url));
			t.start();
			ts.add(t);
		}

		for (Thread t : ts) {
			t.join();
			if (error) {
				break;
			}
		}

		if (error) {
			for (Thread t : ts) {
				t.interrupt();
			}
		}

		long totalTime = System.currentTimeMillis() - startTotalTime;

		System.out.println();
		System.out.println("Final report");
		System.out.println();
		System.out.println("Total downloaded chars: " + totalChars.intValue());
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

	private void donwloadWeb(List<DownloadedWeb> webs, String url) {
		try {

			long startTime = System.currentTimeMillis();

			synchronized (this) {
				System.out.println("Start downloading: " + url.trim());
			}

			String webContent = downloadURL(new URL(url));

			int chars = webContent.length();
			totalChars.addAndGet(chars);

			long time = System.currentTimeMillis() - startTime;

			synchronized (this) {
				System.out.println("Downloaded web " + url);
				System.out.println("   Characters: " + chars);
				System.out.println("   First chars: " + webContent.substring(0, 100));
				System.out.println("   Time: " + time + "ms");
				System.out.println();
			}

			webs.add(new DownloadedWeb(url, webContent));

		} catch (Exception e) {
			System.out.println("Error downloading web " + url + ": " + e.getClass().getName());

			webs.add(new DownloadedWeb(url, e));

			if (EXIT_ON_EXCEPTION) {
				error = true;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		new DescargaFase2().exec();
	}
}
