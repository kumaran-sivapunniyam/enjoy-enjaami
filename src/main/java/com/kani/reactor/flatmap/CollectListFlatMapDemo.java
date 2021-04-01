package com.kani.reactor.flatmap;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

 class CollectListFlatMapDemo {

	 static void main(String[] args) {
		
		Flux<String> stringFlux = Flux.just("Kumaran", "Karthi", "Pratheepa", "Kavin", "Nila");
		
		Mono<List<String>> kStringListMono = 
				stringFlux
				.filter(name -> name.startsWith("K"))
				.collectList();
			
//		kStringListMono
//			.flatMapMany(kList -> Flux.fromIterable(kList))
//			.doOnNext(System.out::println)
//			.subscribe();
		
		kStringListMono
			.map(kList -> kList.size())
			.doOnNext(size -> System.out.println("size=" + size))
			.subscribe();
			
	}

}
