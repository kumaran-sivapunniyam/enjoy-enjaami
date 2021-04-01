package com.kani.reactor.rsb;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

@Log4j2
public class SchedulersSubscribeOnTest {

	@Test
	public void subscribeOn() {
		String rsbThreadName = SchedulersSubscribeOnTest.class.getName();
		ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<String, AtomicInteger>();

		Executor executor = Executors.newFixedThreadPool(5, runnable -> {
			Runnable wrapper = () -> {
				String key = Thread.currentThread().getName();
				AtomicInteger result = map.computeIfAbsent(key, s -> new AtomicInteger());
				result.incrementAndGet();
				runnable.run();
			};
			return new Thread(wrapper, rsbThreadName);
		});

		Scheduler scheduler = Schedulers.fromExecutor(executor);
		Mono<Integer> integerFlux = Mono.just(1).subscribeOn(scheduler)
				.doFinally(signal -> map.forEach((k, v) -> log.info(k + '=' + v)));

		StepVerifier.create(integerFlux) //
				.expectNextCount(1) //
				.verifyComplete();

		AtomicInteger atomicInteger = map.get(rsbThreadName);
		Assertions.assertEquals(atomicInteger.get(), 1);
	}

}