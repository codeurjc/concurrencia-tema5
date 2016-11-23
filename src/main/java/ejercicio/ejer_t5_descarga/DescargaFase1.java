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

public class DescargaFase1 {

	private static final boolean EXIT_ON_EXCEPTION = true;

	private String downloadURL(URL website) throws IOException {

		URLConnection connection = website.openConnection();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));

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

	private void exec() throws Exception {

		List<String> urls;
		try {
			urls = loadURLs();
		} catch (IOException e) {
			System.out
					.println("Error reading webs.txt file: " + e.getMessage());
			return;
		}
		
		List<DownloadedWeb> webs = new ArrayList<>();

		int totalChars = 0;

		long startTotalTime = System.currentTimeMillis();

		for (String url : urls) {
			
			try {

				long startTime = System.currentTimeMillis();

				System.out.println("Start downloading: " + url.trim());

				String webContent = downloadURL(new URL(url));

				int chars = webContent.length();
				totalChars += chars;

				long time = System.currentTimeMillis() - startTime;

				System.out.println("Downloaded web " + url);
				System.out.println("   Characters: " + chars);
				System.out.println(
						"   First chars: " + webContent.substring(0, 100));
				System.out.println("   Time: " + time + "ms");
				System.out.println();

				webs.add(new DownloadedWeb(url, webContent));

			} catch (Exception e) {
				System.out.println("Error downloading web " + url + ": "
						+ e.getClass().getName());

				webs.add(new DownloadedWeb(url, e));

				if (EXIT_ON_EXCEPTION) {
					break;
				}
			}
		}

		long totalTime = System.currentTimeMillis() - startTotalTime;

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
				System.out.println(
						"Error: " + web.getException().getClass().getName());
			}
			System.out.println();
		}

	}

	public static void main(String[] args) throws Exception {
		new DescargaFase1().exec();
	}
}
