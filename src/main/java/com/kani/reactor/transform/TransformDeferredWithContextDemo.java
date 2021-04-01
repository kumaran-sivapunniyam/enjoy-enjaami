package com.kani.reactor.transform;

import java.util.Arrays;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class TransformDeferredWithContextDemo {

	private static final String LAST_NAME = "lastName";

	private static final Function<Flux<String>, Flux<String>> filterAndMap = incomingFlux -> Flux
			.deferContextual(contextView -> incomingFlux
					.filter(name -> name.endsWith(contextView.get(LAST_NAME).toString())).map(String::toUpperCase));

	/*
	 * Same behavior is achieved, no difference between transform and
	 * transformDeferred transform -> only once, the below function is executed
	 * transoformDeferred -> gets executed once for every subscriber
	 */
	public static void main(String[] args) {

		Flux<String> familyFlux = Flux.fromIterable(
				Arrays.asList("Kavin Kumaran", "Nila Kumaran", "Vignesh Karthi", "Shankar Karthi", "Elle Vignesh"))
				.transform(filterAndMap);
		// .transformDeferred(filterAndMap);

		familyFlux.contextWrite(context -> context.put(LAST_NAME, "Karthi"))
				.subscribe(name -> log.info("Subscriber Karthi to Composed MapAndFilter: {}", name));
		familyFlux.contextWrite(context -> context.put(LAST_NAME, "Kumaran"))
				.subscribe(name -> log.info("Subscriber Kumaran to Composed MapAndFilter: {}", name));
		familyFlux.contextWrite(context -> context.put(LAST_NAME, "Vignesh"))
				.subscribe(name -> log.info("Subscriber Vignesh to Composed MapAndFilter: {}", name));
	}

}
