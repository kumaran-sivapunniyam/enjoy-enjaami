package com.kani.reactor;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class SimpleFluxFactoriesTest {

	@Test
	public void simple() {

		Publisher<Integer> rangeOfIntegers = Flux.range(0, 10);
		StepVerifier.create(rangeOfIntegers).expectNextCount(10).verifyComplete();

		Flux<String> letters = Flux.just("A", "B", "C");
		StepVerifier.create(letters).expectNext("A", "B", "C").verifyComplete();

		long now = System.currentTimeMillis();
		Mono<Date> greetingMono = Mono.just(new Date(now));
		StepVerifier.create(greetingMono).expectNext(new Date(now)).verifyComplete();

		Mono<Object> empty = Mono.empty();
		StepVerifier.create(empty).verifyComplete();

		Flux<Integer> fromArray = Flux.fromArray(new Integer[] { 1, 2, 3 });
		StepVerifier.create(fromArray).expectNext(1, 2, 3).verifyComplete();

		Flux<Integer> fromIterable = Flux.fromIterable(Arrays.asList(1, 2, 3));
		StepVerifier.create(fromIterable).expectNext(1, 2, 3).verifyComplete();

	}
}
