package com.kani.reactor.transform;

import java.util.Arrays;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class TransformDeferredWithContextDemo {

	
	
public static void main(String[] args) {
		
		Function<Flux<String>, Flux<String>> filterAndMap = incomingFlux -> {
			
			Flux<String> transformedFlux = null;
			
			transformedFlux = Flux.deferContextual(contextView -> {
				
				if(contextView.get("lastName").toString().equalsIgnoreCase("Karthi")) {
					return incomingFlux.filter(name -> getLastName(name).equalsIgnoreCase("Karthi")).map(String::toUpperCase);
				}
				else if(contextView.get("lastName").toString().equalsIgnoreCase("Kumaran")) {
					return incomingFlux.filter(name -> getLastName(name).equalsIgnoreCase("Kumaran")).map(String::toUpperCase);
				}
				else if(contextView.get("lastName").toString().equalsIgnoreCase("Vignesh")) {
					return incomingFlux.filter(name -> getLastName(name).equalsIgnoreCase("Vignesh")).map(String::toUpperCase);
				}
				return Flux.empty();
			});
			
			return transformedFlux;
			
		};
				
				

		Flux<String> familyFlux = Flux.fromIterable(
				Arrays.asList("Kavin Kumaran", "Nila Kumaran", "Vignesh Karthi", "Shankar Karthi", "Elle Vignesh"))
				//.doOnNext(log::info)
				.transform(filterAndMap);
				
		
		familyFlux
			.contextWrite(context -> context.put("lastName", "Karthi"))
			.subscribe(name -> log.info("Subscriber Karthi to Composed MapAndFilter: {}",  name));
		familyFlux
			.contextWrite(context -> context.put("lastName", "Kumaran"))
			.subscribe(name -> log.info("Subscriber Kumaran to Composed MapAndFilter: {}",  name));
		familyFlux
			.contextWrite(context -> context.put("lastName", "Vignesh"))
			.subscribe(name -> log.info("Subscriber Vignesh to Composed MapAndFilter: {}",  name));
	}

private static String getLastName(String name) {
	return name.split(" ")[1];
}	
}
