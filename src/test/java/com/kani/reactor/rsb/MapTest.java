package com.kani.reactor.rsb;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class MapTest {
	@Test
	public void maps() {
		
		Flux<String> data = Flux.just("a", "b", "c").map(String::toUpperCase);
		
		StepVerifier.create(data).expectNext("A", "B", "C").verifyComplete();
	}

}
