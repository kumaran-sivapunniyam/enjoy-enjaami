package com.kani.reactor.rsb;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.util.context.Context;

@Log4j2
public class ContextTest {

	@Test
	public void context() throws Exception {
		ConcurrentHashMap<String, AtomicInteger> observedContextValues = new ConcurrentHashMap<String, AtomicInteger>();

		int max = 3;
		String key = "name";
		CountDownLatch cdl = new CountDownLatch(max);
		Context context = Context.of(key, "kumaran");

		Flux<Integer> just = Flux//
				.range(0, max)//

				.delayElements(Duration.ofMillis(1))//

				 .doOnEach((Signal<Integer> integerSignal) -> {
					 
					Context currentContext = integerSignal.getContext();
					
					if (integerSignal.getType().equals(SignalType.ON_NEXT)) {
						String val = currentContext.get(key);
						Assertions.assertNotNull(val);
						Assertions.assertEquals(val, "kumaran");
						observedContextValues.computeIfAbsent("name", k -> new AtomicInteger(0)).incrementAndGet();
					}
				})//
				.subscriberContext(context);

		just.subscribe(integer -> {
			log.info("integer: " + integer);
			cdl.countDown();
		});

		cdl.await();

		Assertions.assertEquals(observedContextValues.get(key).get(), max);

	}

}