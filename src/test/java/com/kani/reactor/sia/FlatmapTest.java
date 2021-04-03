package com.kani.reactor.sia;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class FlatmapTest {

	//@Test
	public void map() {
		Flux<Player> playerFlux = Flux.fromArray(getFullNames()) //
				.map(fullName -> buildPlayer(fullName))//
				//.subscribeOn(Schedulers.single())
				//.subscribeOn(Schedulers.immediate())
				//.subscribeOn(Schedulers.newSingle("KK"))
				//.subscribeOn(Schedulers.elastic())
				.subscribeOn(Schedulers.parallel())
				.log();

		testPlayerFlux(playerFlux);

	}

	
	@Test
	public void flatMap() {
		Flux<Player> playerFlux = Flux.fromArray(getFullNames()) //
				.flatMap(fullName -> Mono.just(buildPlayer(fullName)) )//
				//.subscribeOn(Schedulers.single())
				//.subscribeOn(Schedulers.immediate())
				//.subscribeOn(Schedulers.newSingle("KK"))
				//.subscribeOn(Schedulers.elastic())
				.subscribeOn(Schedulers.parallel())
				.log();

		testPlayerFlux(playerFlux);

	}
	private void testPlayerFlux(Flux<Player> playerFlux) {
		List<Player> playerList = getFullNamesList();
		StepVerifier.create(playerFlux)//
				.expectNextMatches(p -> playerList.contains(p))//
				.expectNextMatches(p -> playerList.contains(p))//
				.expectNextMatches(p -> playerList.contains(p))//
				.expectNextMatches(p -> playerList.contains(p))//
				.expectNextMatches(p -> playerList.contains(p))//
				.expectNextMatches(p -> playerList.contains(p))//
				.expectNextMatches(p -> playerList.contains(p))//
				.expectNextMatches(p -> playerList.contains(p))//
				.verifyComplete();
	}

	//@Test
	public void flatMap1() {
		Flux<Player> playerFlux = Flux //
				.fromArray(getFullNames()) //
				// .map(fullName -> buildPlayer(fullName));
				.flatMap(fullName -> Mono.just(fullName)//
						.map(fn -> buildPlayer(fn))//
						.subscribeOn(Schedulers.single())//
				).log();

		testPlayerFlux(playerFlux);

	}

	private Player buildPlayer(String fullName) {
		String[] split = fullName.split("\\s");
		return new Player(split[0], split[1]);

	}

	private List<Player> getFullNamesList() {
		List<Player> fullNamesList = new ArrayList<>();
		String[] fullNames = getFullNames();
		for (String fullName : fullNames) {
			String[] split = fullName.split("\\s");
			fullNamesList.add(new Player(split[0], split[1]));
		}
		return fullNamesList;

	}

	private String[] getFullNames() {
		// TODO Auto-generated method stub
		return new String[] { //
				"Kumaran Sivapunniyam", "Pratheepa Kumaran", "Kavin Kumaran", "Nila Kumaran", //
				"Karthikeyan Ganapathi", "Revathi Karthikeyan", "Vignesh Karthikeyan", "Shankar Karthikeyan" //
		};
	}

}

@Data
@AllArgsConstructor
@EqualsAndHashCode
class Player {
	String firstName;
	String lastName;
}