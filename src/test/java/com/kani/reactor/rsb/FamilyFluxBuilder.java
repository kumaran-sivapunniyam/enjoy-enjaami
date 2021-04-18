package com.kani.reactor.rsb;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FamilyFluxBuilder {

	AtomicInteger ai = new AtomicInteger();

	public Flux<String> buildTransformedFlux(Flux<String> incomingFlux) {

		int aiValue = ai.incrementAndGet();
		log.info("ai={}", aiValue);
		Flux<String> transformedFlux = null;
		if (aiValue == 1) {
			transformedFlux = incomingFlux.filter(name -> name.contains("Karthi")).map(String::toUpperCase);
		} else if (aiValue == 2) {
			transformedFlux = incomingFlux.filter(name -> name.contains("Kumaran")).map(String::toUpperCase);
		} else {
			transformedFlux = incomingFlux.filter(name -> name.contains(" Vignesh")).map(String::toUpperCase);
		}
		return transformedFlux;

	}

}
