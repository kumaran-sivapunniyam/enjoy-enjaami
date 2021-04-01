package com.kani.reactor.flatmap;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

 class CollectListFlatMapManyDemo {

	 static void main(String[] args) {
		
		Flux<String> stringFlux = Flux.just("Kumaran", "Karthi", "Pratheepa", "Kavin", "Nila");
		
		Mono<List<String>> kStringListMono = 
				stringFlux
				.filter(name -> name.startsWith("K"))
				.collectList();
			
		kStringListMono
			.flatMapMany(kList -> Flux.fromIterable(kList))
			.doOnNext(System.out::println)
			.subscribe();
			
	}

}
