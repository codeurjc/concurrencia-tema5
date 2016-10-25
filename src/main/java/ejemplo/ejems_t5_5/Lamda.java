package ejemplo.ejems_t5_5;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lamda {
	
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();
		
//		List<Double> list = new ArrayList<>();
//		for (int i = 0; i < 50_000_000; i++) {
//		   list.add(Math.random());
//		}
		
//		Stream<Double> stream = Stream.generate(Math::random);
//		List<Double> list = stream.limit(50_000_000)
//		   .collect(Collectors.toList());
		
		Stream<Double> stream = Stream.generate(Math::random);
		List<Double> list = stream.parallel().limit(50_000_000)
		   .collect(Collectors.toList());

		System.out.println("Duration: "+(System.currentTimeMillis()-start));
		
	}

}
