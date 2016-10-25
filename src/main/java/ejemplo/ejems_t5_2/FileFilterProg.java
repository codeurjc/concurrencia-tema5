package ejemplo.ejems_t5_2;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

class TextFilesFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith(".txt");
	}
}

public class FileFilterProg {
	
	public static void main(String[] args) {
		
		File currentDir = new File("");
		
		String[] files = currentDir.list(new TextFilesFilter());
		
		System.out.println("Files:"+Arrays.toString(files));
		
	}
}
