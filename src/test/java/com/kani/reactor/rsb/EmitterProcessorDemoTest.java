package com.kani.reactor.rsb;

import org.junit.jupiter.api.Test;

import com.kani.reactor.rsb.EmitterProcessorDemo;

import reactor.core.publisher.EmitterProcessor;
import reactor.test.StepVerifier;

public class EmitterProcessorDemoTest {

	@Test
	public void emitterProcessor() {
		
		EmitterProcessor<String> processor = EmitterProcessorDemo.emitterProcessor();
		EmitterProcessorDemo.produce(processor.sink());
		
		StepVerifier.create(processor)
			.expectNext("1")
			.expectNext("2")
			.expectNext("3")
			.verifyComplete();
		
		
		
	}
}
