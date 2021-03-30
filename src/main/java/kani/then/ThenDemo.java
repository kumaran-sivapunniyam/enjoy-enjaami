package kani.then;

import reactor.core.publisher.Mono;


class ThenDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Mono<Integer> integerMono1 = Mono.just(1);
		Mono<String> stringMono1 = Mono.just("Kumaran");
		
		Mono<String> stringMono2 = integerMono1.map(i -> i+100)
			.doOnNext(System.out::println)
			
			.then(stringMono1)
			
			.map(String::toUpperCase)
			.doOnNext(System.out::println);
			
			
		stringMono2.subscribe();
		
		
				
		

	}
	

}
