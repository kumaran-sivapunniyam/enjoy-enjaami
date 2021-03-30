package com.kani.reactor.then;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Signal;


class ThenManyDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		Flux<Integer> integerFlux1 = Flux.just(1, 2, 3, 4, 5);
//		Flux<String> stringFlux = Flux.just("Kumaran", "Karthi", "Pratheepa");
//		
//		Flux<String> stringFlux2 = integerFlux1
//				.doOnEach(signal -> log(signal) )
//				.map(i -> i+100)
//				.doOnNext(System.out::println)
//				
//				.thenMany(stringFlux)
//				
//				.map(String::toUpperCase)
//				.doOnEach(signal -> log(signal) )
//				.doOnNext(System.out::println);
//		
//		stringFlux2.subscribe();
		
		Flux<Integer> integerFlux1 = Flux.just(1, 2, 3, 4, 5);
		
		Flux<String> stringFlux = integerFlux1
				.doOnEach(signal -> log(signal) )
				.doOnNext(System.out::println)
				.doOnComplete(() -> System.out.println("doOnComplete from integerFlux"))
				.map(i -> "Karthi-" + i);
		
		stringFlux
			.map(String::toUpperCase)
			.doOnNext(System.out::println)
			.doOnEach(signal -> log(signal) )
			.doOnComplete(() -> System.out.println("doOnComplete from stringFlux"))
			.subscribe();

	}

	private static <T> void log(Signal<T> signal) {
		
		System.out.println(signal.getType() + ", " + signal.get());
		
	}
	

}
