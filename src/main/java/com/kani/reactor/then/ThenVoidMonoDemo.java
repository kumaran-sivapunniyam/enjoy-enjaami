package com.kani.reactor.then;

import reactor.core.publisher.Mono;


class ThenVoidMonoDemo {

	public static int counter = 100;
	
	public static void main(String[] args) {
		
		Mono<Integer> integerMono = Mono.just(15);
		
		Mono<Void> voidMono = count(integerMono);
		voidMono.subscribe();

	}
	
	public static Mono<Void> count(Mono<Integer> integerMono) {
		
		return integerMono
			.map(i -> counter + i)
			.doOnNext(System.out::println)
			.then();
			
			
			
		
		
	}
	

}
