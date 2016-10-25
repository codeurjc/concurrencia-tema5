package ejercicio.ejer_t5_6_4;

import java.io.File;

public class RecursiveDirectoryList {

	public void listFiles(File root) {
		
		if (root.isDirectory()) {
			
			for (File file : root.listFiles()) {
				
				if (file.isDirectory()) {
					listFiles(file);
				} else {
					System.out.println(file.getAbsolutePath());
				}
			}
		}
	}

	public static void main(String[] args) {

		new RecursiveDirectoryList().listFiles(new File(
				"X:\\Docencia\\Asignaturas\\PC\\2011-2012\\Material\\Tema3"));

	}
}
