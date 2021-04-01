package com.kani.reactor.rsb;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class TransformTest {

	/*
	 * The transform operator gives us a chance to act on a Flux<T>, customizing it.
	 * This can be quite useful if you want to avoid extra intermediate variables.
	 */

	@Test
	public void transform() {

		AtomicBoolean finished = new AtomicBoolean();

		Flux<String> lettersFlux = Flux.just("A", "B", "C")
				.transform(stringFlux -> stringFlux.doFinally(signal -> finished.set(true)));

		StepVerifier.create(lettersFlux) //
				.expectNextCount(3) //
				.verifyComplete();

		Assertions.assertTrue(finished.get());

	}
}
