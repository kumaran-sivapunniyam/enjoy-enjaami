package com.kani.reactor.transform;

import java.util.Arrays;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class TransformDemo {

	public static void main(String[] args) {

		Function<Flux<String>, Flux<String>> filterAndMap = f -> f.filter(name -> !name.contains("Kumaran"))
				.map(String::toUpperCase);

		Flux.fromIterable(Arrays.asList("Kavin Kumaran", "Nila Kumaran", "Vignesh Karthi", "Shankar Karthi"))
				.doOnNext(log::info)
				.transform(filterAndMap)
				.map(name -> name.concat(" Revathi"))
				.subscribe(name -> log.info("Subscriber to Transformed MapAndFilter: {}",  name));
	}

	

}
