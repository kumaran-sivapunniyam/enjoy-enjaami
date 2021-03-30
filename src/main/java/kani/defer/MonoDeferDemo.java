package kani.defer;

import java.time.LocalDateTime;

import reactor.core.publisher.Mono;

class MonoDeferDemo {

	public static void main(String[] args) throws InterruptedException {

		System.out.println(LocalDateTime.now().toString());
		
		//Mono<LocalDateTime> currentTimeMono = Mono.just(LocalDateTime.now());
		
		Mono<LocalDateTime> currentTimeMono = Mono.defer(() -> Mono.just(LocalDateTime.now()));
		
		Thread.sleep(3000);
		
		currentTimeMono.doOnNext(System.out::println).block();

	}

}
