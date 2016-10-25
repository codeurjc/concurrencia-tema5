package ejercicio.ejer_t5_6_4;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FindDuplicates {

	private Map<String, String> duplicates = new HashMap<>();

	public void find(File root) {
		
		if (root.isDirectory()) {
			
			for (File file : root.listFiles()) {
				
				if (file.isDirectory()) {
					
					find(file);
					
				} else {
					
					String path = duplicates.get(file.getName());
					if (path == null) {
						duplicates.put(file.getName(), file.getAbsolutePath());
					} else {
						System.out.println("Found duplicate file: " + file.getName());
						System.out.println("    " + path);
						System.out.println("    " + file.getAbsolutePath());
					}
				}
			}
		}
	}

	public static void main(String[] args) {

		new FindDuplicates().find(
				new File("X:\\Docencia\\Asignaturas\\PC\\2011-2012\\Material\\Tema3\\Pruebas"));

	}
}
