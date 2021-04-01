package com.kani.reactor.rsb;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class OnErrorReturnTest {

	private final Flux<Integer> resultsInError = //

			Flux.just(1, 2, 3) //

					.flatMap(integer -> {

						if (integer == 2) {
							return Flux.error(new IllegalArgumentException("Oops!"));
						} else {
							return Flux.just(integer);
						}

					});

	@Test
	public void onErrorReturn() {
		Flux<Integer> integerFlux = resultsInError.onErrorReturn(0);

		StepVerifier.create(integerFlux).expectNext(1, 0).verifyComplete();
	}

}