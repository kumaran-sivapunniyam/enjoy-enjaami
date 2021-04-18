package com.kani.reactor.rsb;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.EnumOptions;

import lombok.var;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

@Log4j2
public class ControlFlowRetryTest {

	
	//@Test
	public void retry() {

		var errored = new AtomicBoolean();
		
		Flux<String> producer = Flux.create(sink -> {
			if (!errored.get()) {
				errored.set(true);
				sink.error(new RuntimeException("Nope!"));
				log.info("returning a " + RuntimeException.class.getName() + "!");
			} else {
				log.info("we've already errored so here's the value");
				sink.next("hello");
			}
			sink.complete();
		});

		Flux<String> retryOnError = producer.retry();
		StepVerifier.create(retryOnError).expectNext("hello").verifyComplete();
	}
	/*
	 * try retry(numRetries) retryBackoff, repeatWhenEmpty, defaultIfEmpty
	 */

	@Test
	public void retryBackoff() {

		var errored = new AtomicBoolean();
		
		
		var numberOfTimesErrored = new AtomicInteger(0);
		
		Flux<String> producer = Flux.create(sink -> {
			
			
			log.info("numberOfTimesErrored=" + numberOfTimesErrored.incrementAndGet());
			
			if ( numberOfTimesErrored.intValue() < 3) {
				
				sink.error(new RuntimeException("Nope!"));
				
			} else {
				log.info("we've already errored so here's the value");
				sink.next("hello1");
				sink.next("hello2");
				sink.next("hello3");
				sink.next("hello4");
			}
			sink.complete();
		});

		Flux<String> retryOnError = producer //
				.retryWhen(Retry.backoff(5, Duration.ofMillis(500))) //
				.doOnEach(log::info);
		
		retryOnError.blockLast();

		//StepVerifier.create(retryOnError).verifyError();
	}

	//@Test
	public void doBeforeRetry() {

		var errored = new AtomicBoolean();
		Flux<String> producer = Flux.create(sink -> {
			if (!errored.get()) {
				// errored.set(true);
				sink.error(new RuntimeException("Nope!"));
				// log.info("returning a " + RuntimeException.class.getName() + "!");
			} else {
				log.info("we've already errored so here's the value");
				sink.next("hello1");
				sink.next("hello2");
				sink.next("hello3");
				sink.next("hello4");
			}
			sink.complete();
		});

		Flux<String> retryOnError = producer //
				.retryWhen(Retry.backoff(3, Duration.ofMillis(500)) //
						.doBeforeRetry(log::info));

		StepVerifier.create(retryOnError).verifyError();
	}
}
