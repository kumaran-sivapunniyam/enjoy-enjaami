package com.kani.reactor.rsb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Log4j2
public class AsyncApiIntegrationDemo {
	final ExecutorService executorService = Executors.newFixedThreadPool(1);

	Flux<Integer> async() {
		return Flux.create(emitter -> this.launch(emitter, 5));
	}

	private void launch(FluxSink<Integer> integerFluxSink, int count) {
		this.executorService.submit(() -> {
			AtomicInteger integer = new AtomicInteger();
			while (integer.get() < count) {
				double random = Math.random();
				integerFluxSink.next(integer.incrementAndGet());
				this.sleep((long) (random * 1_000));
			}
			integerFluxSink.complete();
		});
	}

	private void sleep(long s) {
		try {
			Thread.sleep(s);
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	public static void main(String[] args) {
		AsyncApiIntegrationDemo demo = new AsyncApiIntegrationDemo();
		demo.async()
			.doOnNext(log::info)
			.doFinally(signalType -> demo.executorService.shutdown())
			.subscribe();

		
	}
}
