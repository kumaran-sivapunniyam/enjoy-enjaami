package com.kani.reactor.rsb;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@SuppressWarnings("deprecation")
@Log4j2
public class EmitterProcessorDemo {
	
	public static void main(String args[]) {
		EmitterProcessor<String> processor =  emitterProcessor();
		produce(processor.sink());
		consume(processor);
	}
	
	
	static EmitterProcessor<String> emitterProcessor() {
		
		return EmitterProcessor.create();
		
	}
	
	static void produce(FluxSink<String> sink) {
		sink.next("1");
		sink.next("2");
		sink.next("3");
		sink.complete();
	}
	
	private static void consume(Flux<String> publisher) {
		publisher
			.doOnNext(log::info)
			.doOnComplete(() -> log.info("doOnComplete"))
			.doFinally(signalType -> log.info("signalType={}", signalType))
			.subscribe();
	}	

}
