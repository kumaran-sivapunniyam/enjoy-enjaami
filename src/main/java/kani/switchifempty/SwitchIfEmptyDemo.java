package kani.switchifempty;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class SwitchIfEmptyDemo {

	public static void main(String[] args) {

		Flux.just("Minneapolis3", "Minneapolis2", "Minneapolis").doOnNext(System.out::println)
				.concatMap(departmentId -> EmployeeRepository.findEmployeeByDepartmenId(departmentId))
				.switchIfEmpty(Flux.just(buildEmployee())).doOnNext(employee -> System.out.println(employee.toString()))
				.concatMap(employee -> AddressRepository.findAddressByEmployeeId(employee.getId()))
				.switchIfEmpty(Mono.just(buildAddress())).doOnNext(address -> System.out.println(address.toString()))
				.subscribe();

	}

	private static Address buildAddress() {

		return Address.builder().city("Chennai").state("TN").build();
	}

	private static Employee buildEmployee() {

		return Employee.builder().id("X1").name("XXX").build();
	}

}
