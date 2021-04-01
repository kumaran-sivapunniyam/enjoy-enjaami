package com.kani.reactor.rsb;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

@Log4j2
public class HotStreamTest2 {

	@Test
	public void hot() throws Exception {
		int factor = 10;
		log.info("start");
		CountDownLatch cdl = new CountDownLatch(2);
		Flux<Integer> live = Flux.range(0, 10).delayElements(Duration.ofMillis(factor)).share();
		ArrayList<Integer> one = new ArrayList<Integer>();
		ArrayList<Integer> two = new ArrayList<Integer>();
		live.doFinally(signalTypeConsumer(cdl)).subscribe(collect(one));
		Thread.sleep(factor * 2);
		live.doFinally(signalTypeConsumer(cdl)).subscribe(collect(two));
		cdl.await(5, TimeUnit.SECONDS);
		Assertions.assertTrue(one.size() > two.size());
		log.info("stop");
	}

	private Consumer<SignalType> signalTypeConsumer(CountDownLatch cdl) {
		return signal -> {
			if (signal.equals(SignalType.ON_COMPLETE)) {
				try {
					cdl.countDown();
					log.info("await()...");
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	private Consumer<Integer> collect(List<Integer> ints) {
		return ints::add;
	}
}