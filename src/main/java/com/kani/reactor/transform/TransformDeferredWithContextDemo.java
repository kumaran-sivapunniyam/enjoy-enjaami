package com.kani.reactor.transform;

import java.util.Arrays;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class TransformDeferredWithContextDemo {

	public static void main(String[] args) {

		//Same behavior is achieved, no difference between transform and transformDeferred
		//transform -> only once, the below function is executed
		//transoformDeferred -> gets executed once for every subscriber
		Function<Flux<String>, Flux<String>> filterAndMap = incomingFlux -> 

			Flux.deferContextual(contextView -> {
				return incomingFlux
						.filter(name -> getLastName(name).equalsIgnoreCase(contextView.get("lastName").toString()))
						.map(String::toUpperCase);
			});


		Flux<String> familyFlux = Flux
				.fromIterable(Arrays.asList("Kavin Kumaran", "Nila Kumaran", "Vignesh Karthi", "Shankar Karthi",
						"Elle Vignesh"))
				.transform(filterAndMap);
				//.transformDeferred(filterAndMap);

		familyFlux.contextWrite(context -> context.put("lastName", "Karthi"))
				.subscribe(name -> log.info("Subscriber Karthi to Composed MapAndFilter: {}", name));
		familyFlux.contextWrite(context -> context.put("lastName", "Kumaran"))
				.subscribe(name -> log.info("Subscriber Kumaran to Composed MapAndFilter: {}", name));
		familyFlux.contextWrite(context -> context.put("lastName", "Vignesh"))
				.subscribe(name -> log.info("Subscriber Vignesh to Composed MapAndFilter: {}", name));
	}

	private static String getLastName(String name) {
		return name.split(" ")[1];
	}
}
