package com.kani.reactor.rsb;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ThenManyTest {

	@Test
	public void thenMany() {
		AtomicInteger letters = new AtomicInteger();
		AtomicInteger numbers = new AtomicInteger();

		Flux<String> lettersPublisher = Flux.just("a", "b", "c").doOnNext(value -> letters.incrementAndGet());
		Flux<Integer> numbersPublisher = Flux.just(1, 2, 3).doOnNext(number -> numbers.incrementAndGet());

		Flux<Integer> thisBeforeThat = lettersPublisher.thenMany(numbersPublisher);

		StepVerifier.create(thisBeforeThat).expectNext(1, 2, 3).verifyComplete();
		Assertions.assertEquals(letters.get(), 3);
		Assertions.assertEquals(numbers.get(), 3);
	}
}
