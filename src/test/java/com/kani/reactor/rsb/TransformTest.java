package com.kani.reactor.rsb;

import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class TransformTest {

	FamilyFluxBuilder familyFluxBuilder = Mockito.mock(FamilyFluxBuilder.class);

//	@Mock
//	FamilyFluxBuilder familyFluxBuilder;

	public Flux<String> buildFamilyFlux() {

		Flux<String> familyFlux = Flux
				.fromIterable(Arrays.asList("Kavin Kumaran", "Nila Kumaran", "Vignesh Karthi", "Shankar Karthi",
						"Elle Vignesh"))

				.transformDeferred(incomingFlux -> familyFluxBuilder.buildTransformedFlux(incomingFlux));

		return familyFlux;

	}

	@Test
	public void testTransformDeferred() {

		when(familyFluxBuilder.buildTransformedFlux(Mockito.any()))
				.thenReturn(Flux.just("Vignesh1 Karthi".toUpperCase(), "Shankar1 Karthi".toUpperCase()));

		Flux<String> familyFlux = buildFamilyFlux();

		StepVerifier.create(familyFlux) //
				.expectNext("Vignesh1 Karthi".toUpperCase()) //
				.expectNext("Shankar1 Karthi".toUpperCase()) //
				.verifyComplete();

//		StepVerifier.create(familyFlux).expectNext("Kavin Kumaran".toUpperCase())
//				.expectNext("Nila Kumaran".toUpperCase()).verifyComplete();

	}
}
