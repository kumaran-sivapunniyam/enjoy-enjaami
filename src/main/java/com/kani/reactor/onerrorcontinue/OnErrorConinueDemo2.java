package com.kani.reactor.onerrorcontinue;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
class OnErrorConinueDemo2 {

	public static void main(String[] args) {

		Flux.just("Minneapolis@", "Minneapolis2", "Minneapolis") //
		
				.concatMap(departmentId -> EmployeeRepository.findEmployeeByDepartmenId(departmentId))

				
				.onErrorContinue(
						(t, departmentId) -> log.info("departmentId={}, RE/NEF error={}", departmentId, t.getMessage()))

//				.onErrorContinue(t -> t instanceof NoEmployeeFoundException, //
//						(t, departmentId) -> log.info("departmentId={}, NEFE error={}", departmentId, t.getMessage()))

				.switchIfEmpty(Flux.just(buildEmployee())).doOnNext(employee -> System.out.println(employee.toString()))

				.concatMap(employee -> AddressRepository.findAddressByEmployeeId(employee.getId()))

				.switchIfEmpty(Mono.just(buildAddress()))

				
				.onErrorContinue((t, employee) -> System.out.println(String.format("%s", t.getMessage())))

				.doOnNext(address -> System.out.println(address.toString())) //
				.subscribe();

	}

	private static Address buildAddress() {

		return Address.builder().city("Chennai").state("TN").build();
	}

	private static Employee buildEmployee() {

		return Employee.builder().id("X1").name("XXX").build();
	}

}
