package com.kani.reactor.defer;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class DeferContextualDemo {

	public static void main(String[] args) throws InterruptedException {
		
		log.info("Start Time:: {}", LocalDateTime.now().toString());
		
		Mono<String> greetingMono = 
				Mono.deferContextual(ctx -> Mono.just("Greeting Time:: " + ctx.get("localTime") + " Hello " + ctx.get("message")))
				.doOnNext(greeting -> log.info(greeting.toString()))
				.map(String::toUpperCase)
				.contextWrite(ctx -> ctx.put("message", "Karthi").put("localTime", LocalDateTime.now()));
		
		Thread.sleep(5000);
		greetingMono.doOnNext(s -> log.info(s.toString()))
				.subscribe();

		
	}

}
