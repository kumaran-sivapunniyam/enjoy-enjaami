package com.kani.reactor.rsb;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.FluxSink;

/*
 * The code in HotStreamTest1.java demonstrates that subscribers that subscribe 
 * after a Publisher<T> has started publishing will only see the new values.
 */

public class HotStreamTest1 {

	@Test
	public void hot() throws Exception {

		ArrayList<Integer> first = new ArrayList<Integer>();
		ArrayList<Integer> second = new ArrayList<Integer>();

		@SuppressWarnings("deprecation")
		EmitterProcessor<Integer> emitter = EmitterProcessor.create(2);
		@SuppressWarnings("deprecation")
		FluxSink<Integer> sink = emitter.sink();

		emitter.subscribe(collect(first));
		sink.next(1);
		sink.next(2);

		emitter.subscribe(collect(second));
		sink.next(3);
		sink.complete();

		Assertions.assertTrue(first.size() > second.size());
	}

	Consumer<Integer> collect(List<Integer> collection) {
		return collection::add;
	}

}