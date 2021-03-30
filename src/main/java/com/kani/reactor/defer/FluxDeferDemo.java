package com.kani.reactor.defer;

import java.time.LocalDateTime;

import reactor.core.publisher.Flux;

class FluxDeferDemo {

	public static void main(String[] args) throws InterruptedException {

		System.out.println(LocalDateTime.now());
		
		Flux<LocalDateTime> currentTimeFlux = Flux.defer(() -> Flux.just(
				LocalDateTime.now(), 
				LocalDateTime.now().plusMinutes(5),
				LocalDateTime.now().plusHours(10)));
		
		Thread.sleep(3000);
		
		currentTimeFlux.doOnNext(System.out::println).blockLast();

	}

}
