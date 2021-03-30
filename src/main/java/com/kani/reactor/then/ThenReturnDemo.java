package com.kani.reactor.then;

import reactor.core.publisher.Mono;


class ThenReturnDemo {

	public static int counter = 100;
	
	public static void main(String[] args) {
		
		Mono<Integer> integerMono = Mono.just(15);
		
		Mono<String> stringMono = count(integerMono);
		stringMono
			.doOnNext(System.out::println)
			.subscribe();

	}
	
	public static Mono<String> count(Mono<Integer> integerMono) {
		
		return integerMono
			.map(i -> counter + i)
			.doOnNext(System.out::println)
			.then(Mono.just("then-Kumaran"));
			//.thenReturn("thenReturn-Kumaran");
		
			
			
			
		
		
	}
	

}
