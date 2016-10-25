package ejemplo.ejems_t5_5;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelLamdaPerformance {
	
	public static void main(String[] args) {
		
		
		
		String[] array = new String[1000000];
		Arrays.fill(array, "AbabagalamagA");

		Stream<String> stream = Arrays.stream(array).parallel();

		long time1 = System.nanoTime();

		List<String> list = stream.map((x) -> x.toLowerCase()).collect(Collectors.toList());

		long time2 = System.nanoTime();

		System.out.println((time2 - time1) / 1000000f);
		
	}

}
