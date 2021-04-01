package com.kani.reactor.rsb;

import org.junit.jupiter.api.Test;

import com.kani.reactor.rsb.AsyncApiIntegrationDemo;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class AsyncApiIntegrationDemoTest {
	
	@Test
	public void async() {

		
		AsyncApiIntegrationDemo demo = new AsyncApiIntegrationDemo();
		Flux<Integer> integers = demo.async();

		StepVerifier.create(integers.doFinally(signalType -> demo.executorService.shutdown()))
				.expectNextCount(5)
				.verifyComplete();
	}

		
}
