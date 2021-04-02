package com.kani.reactor.rsb;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ControlFlowTimeoutTest {

	@Test
	public void timeout() throws Exception {

		Flux<Integer> ids = Flux.just(1, 2, 3) //
				.delayElements(Duration.ofSeconds(1)) //
				.timeout(Duration.ofMillis(500)) //
				.onErrorResume(this::given);

		StepVerifier.create(ids) //
				.expectNext(0) //
				.verifyComplete();
	}

	private Flux<Integer> given(Throwable t) {
		Assertions.assertTrue(t instanceof TimeoutException);
		return Flux.just(0);
	}

}