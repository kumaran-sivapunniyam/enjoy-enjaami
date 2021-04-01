package com.kani.reactor.rsb;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FlatMapTest {
	@Test
	public void flatMap() {

		Flux<Integer> data = Flux //
				.just(new Pair(1, 300), new Pair(2, 200), new Pair(3, 100)) //
				.flatMap(pair -> this.delayReplyFor(pair.id, pair.delay));

		StepVerifier.create(data) //
				.expectNext(3, 2, 1) //
				.verifyComplete();

	}

	private Flux<Integer> delayReplyFor(Integer i, long delay) {
		return Flux.just(i) //
				.delayElements(Duration.ofMillis(delay));
	}

	@AllArgsConstructor
	static class Pair {

		private int id;

		private long delay;

	}
}
