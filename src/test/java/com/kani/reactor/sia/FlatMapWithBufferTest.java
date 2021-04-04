package com.kani.reactor.sia;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class FlatMapWithBufferTest {

	public static int ARRAY_SIZE = 9;

	@Test
	public void flatMapWithBuffer() {
		Flux<Player> playerFlux = Flux.fromArray(getFullNames()) //
				.buffer(3)
				.flatMap(tenFullNamesList -> Flux.fromIterable(tenFullNamesList) //
						.map(fn -> buildPlayer(fn)) //
						.subscribeOn(Schedulers.parallel()) //
						.log()
				// .subscribeOn(Schedulers.single()) //
				// .subscribeOn(Schedulers.immediate()) //
				// .subscribeOn(Schedulers.newSingle("KK")) //
				// .subscribeOn(Schedulers.elastic()) //
				);

		testPlayerFlux(playerFlux);

	}

	private String[] getFullNames() {

		String[] fullNames = new String[ARRAY_SIZE];
		for (int i = 0; i < ARRAY_SIZE; i++) {
			fullNames[i] = "Kumaran-" + i + " " + "Sivapunniyam-" + i;

		}
		return fullNames;
	}

	private void testPlayerFlux(Flux<Player> playerFlux) {
		// List<Player> playerList = getFullNamesList();
		StepVerifier.create(playerFlux)//
				.expectNextCount(ARRAY_SIZE).verifyComplete();
	}

	private Player buildPlayer(String fullName) {
		String[] split = fullName.split("\\s");
		return new Player(split[0], split[1]);

	}

}
