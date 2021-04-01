package com.kani.reactor.rsb;

import org.junit.jupiter.api.Test;

import com.kani.reactor.rsb.EmitterProcessorDemo;
import com.kani.reactor.rsb.ReplayProcessorDemo;

import reactor.core.publisher.ReplayProcessor;
import reactor.test.StepVerifier;

public class ReplayProcessorDemoTest {

	@Test
	public void emitterProcessor() {
		
		ReplayProcessor<String> processor = ReplayProcessorDemo.replayProcessor();
		EmitterProcessorDemo.produce(processor.sink());
		
		for(int i=0; i<5; i++)
			StepVerifier.create(processor)
				.expectNext("2")
				.expectNext("3")
				.verifyComplete();
			
		
		
	}
}
