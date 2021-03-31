package com.kani.reactor.dooncallback;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class DoOnSubscribeDemo {

	public static void main(String[] args) {
		
		Flux<String> stringFlux = Flux.just("Kumaran", "Karthi", "Kavin", "Nila", "Vignesh", "Shankar", "Pratheepa", "Revathi")
				.map(String::toUpperCase)
				.doOnNext(System.out::println);
		
		stringFlux
			.doOnSubscribe(s -> log.info("{}", s.toString()))	
			.subscribe();
				
	}

	

}
