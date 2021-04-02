package com.kani.reactor.rsb;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ControlFlowMergeTest {

	@Test
	public void merge() {
		Flux<Integer> fastest = Flux.just(5, 6);
		Flux<Integer> secondFastest = Flux.just(1, 2).delayElements(Duration.ofMillis(2));
		Flux<Integer> thirdFastest = Flux.just(3, 4).delayElements(Duration.ofMillis(20));
		Flux<Flux<Integer>> streamOfStreams = Flux.just(secondFastest, thirdFastest, fastest);
		Flux<Integer> merge = Flux.merge(streamOfStreams);
		StepVerifier.create(merge).expectNext(5, 6, 1, 2, 3, 4).verifyComplete();
	}

}