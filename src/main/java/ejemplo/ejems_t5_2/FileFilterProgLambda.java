package ejemplo.ejems_t5_2;

import java.io.File;
import java.util.Arrays;

public class FileFilterProgLambda {

	public static void main(String[] args) {

		File currentDir = new File("");

		String[] files = currentDir.list((dir, name) -> name.endsWith(".txt"));

		System.out.println("Files:" + Arrays.toString(files));

	}
}
