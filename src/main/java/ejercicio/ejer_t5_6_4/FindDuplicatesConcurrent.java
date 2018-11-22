package ejercicio.ejer_t5_6_4;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FindDuplicatesConcurrent {

	private ConcurrentMap<String, String> duplicates = new ConcurrentHashMap<>();

	public void findDuplicates(File root) {
		if (root.isDirectory()) {
			for (File file : root.listFiles()) {
				if (file.isDirectory()) {
					findDuplicates(file);
				} else {

					String path = duplicates.putIfAbsent(file.getName(), file.getAbsolutePath());

					if (path != null) {
						System.out.println("Found duplicate file: " + file.getName());
						System.out.println("  " + path);
						System.out.println("  " + file.getAbsolutePath());
					}
				}
			}
		}
	}

	public void exec() {
		new Thread(() -> findDuplicates(new File("X:\\Dir"))).start();
		new Thread(() -> findDuplicates(new File("X:\\Dir2"))).start();
	}
}
