package com.kani.reactor.rsb;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.ReplayProcessor;

@SuppressWarnings("deprecation")
@Log4j2
public class ReplayProcessorDemo {
	
	public static void main(String args[]) {
		ReplayProcessor<String> processor =  replayProcessor();
		produce(processor.sink());
		consume(processor);
	}
	
	
	static ReplayProcessor<String> replayProcessor() {
		int historySize = 2;
		boolean unbounded = false;
		
		
		return ReplayProcessor.create(historySize, unbounded);
		
	}
	
	static void produce(FluxSink<String> sink) {
		sink.next("1");
		sink.next("2");
		sink.next("3");
		sink.complete();
	}
	
	private static void consume(Flux<String> publisher) {
		for(int i=0; i < 5; i++)
			publisher
				.doOnNext(log::info)
				.doOnComplete(() -> log.info("doOnComplete"))
				.doFinally(signalType -> log.info("signalType={}", signalType))
				.subscribe();
	}	

}
