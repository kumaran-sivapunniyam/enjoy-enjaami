package com.kani.reactor.onerrorcontinue;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class OnErrorConinueDemo {

	public static void main(String[] args) {

		Flux.just("Minneapolis3", "Minneapolis2", "Minneapolis").doOnNext(System.out::println)
				.concatMap(departmentId -> EmployeeRepository.findEmployeeByDepartmenId(departmentId))
				.onErrorContinue((t, departmentId) -> System.out.println(String.format("%s", t.getMessage())))
				.switchIfEmpty(Flux.just(buildEmployee())).doOnNext(employee -> System.out.println(employee.toString()))
				.concatMap(employee -> AddressRepository.findAddressByEmployeeId(employee.getId()))
				.switchIfEmpty(Mono.just(buildAddress()))
				.onErrorContinue((t, employee) -> System.out.println(String.format("%s", t.getMessage())))
				.doOnNext(address -> System.out.println(address.toString())).subscribe();

	}

	private static Address buildAddress() {

		return Address.builder().city("Chennai").state("TN").build();
	}

	private static Employee buildEmployee() {

		return Employee.builder().id("X1").name("XXX").build();
	}

}
