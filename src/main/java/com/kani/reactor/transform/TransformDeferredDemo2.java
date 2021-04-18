package com.kani.reactor.transform;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class TransformDeferredDemo2 {

	public static void main(String[] args) {

		AtomicInteger ai = new AtomicInteger();

		Flux<String> familyFlux = Flux.fromIterable(
				Arrays.asList("Kavin Kumaran", "Nila Kumaran", "Vignesh Karthi", "Shankar Karthi", "Elle Vignesh"))
				
				.doOnNext(name -> log.info(name))
				
				.transformDeferred(incomingFlux -> {
				
					
					int aiValue = ai.incrementAndGet();
					log.info("ai={}", aiValue);

					Flux<String> transformedFlux = null;

					if (aiValue == 1) {
						transformedFlux = incomingFlux.filter(name -> name.contains("Karthi")).map(String::toUpperCase);
					} else if (aiValue == 2) {
						transformedFlux = incomingFlux.filter(name -> name.contains("Kumaran"))
								.map(String::toUpperCase);
					} else {
						transformedFlux = incomingFlux.filter(name -> name.contains(" Vignesh"))
								.map(String::toUpperCase);
					}

					return transformedFlux;
				});

		
		familyFlux.subscribe(name -> log.info("Subscriber Karthi to Composed MapAndFilter: {}", name));
		familyFlux.subscribe(name -> log.info("Subscriber Kumaran to Composed MapAndFilter: {}", name));
		familyFlux.subscribe(name -> log.info("Subscriber Vignesh to Composed MapAndFilter: {}", name));
	}

}
