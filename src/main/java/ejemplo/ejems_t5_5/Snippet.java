package ejemplo.ejems_t5_5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Snippet {

	public static void main(String[] args) {
		List<String> nombres = new ArrayList<>();
		nombres.add("Juanin");
		nombres.add("Pepe");
		nombres.add("Antonio");
		
		Collections.sort(nombres,
		    Comparator.comparing(String::length));
		
		System.out.println(nombres);
		
		List<String> sharedList = 
				   Collections.synchronizedList(new ArrayList<>());
		
		System.out.println(sharedList);

	}

}
