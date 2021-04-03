package com.kani.reactor.onerrorcontinue;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
class OnErrorConinueDemo {

	public static void main(String[] args) {

		Flux.just("Kumaran", "Pratheepa", "Kavin", "Nila", "Karthi", "Revathi") //

				.flatMap(name -> {

					if (name.equalsIgnoreCase("Pratheepa")) {
						return Flux.error(new PratheepaException());
					} else if (name.equalsIgnoreCase("Nila")) {
						return Flux.error(new NilaException());
					}

					return Flux.just(name);
				}).onErrorContinue(PratheepaException.class, //
						(t, name) -> log.info("{} - {}", name, t.getClass().getName()))

				.onErrorContinue(NilaException.class, //
						(t, name) -> log.info("{} - {}", name, t.getClass().getName()))
				
				.doOnNext(log::info) //
				.subscribe();

	}

}
