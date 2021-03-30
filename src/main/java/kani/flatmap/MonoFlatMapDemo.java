package kani.flatmap;

import reactor.core.publisher.Mono;


 class MonoFlatMapDemo {

	public static void main(String[] args) {
		
		//find department by departmentId and log department
//		Mono.just("Minneapolis")
//			.map(departmentId -> DepartmentRepository.findDepartmentById(departmentId))
//			.doOnNext(departmentMono -> System.out.println(departmentMono.toString()))
//			.subscribe();
		
		//find department by departmentId and log department
//		Mono.just("Minneapolis")
//			.flatMap(departmentId -> DepartmentRepository.findDepartmentById(departmentId))
//			.doOnNext(department -> System.out.println(department.toString()))
//			.subscribe();

		//find address by employeeId and log employees one by one
		//Mono -> Flux -> Mono
		Mono.just("Minneapolis")
		.flatMapMany(departmentId -> EmployeeRepository.findEmployeeByDepartmenId(departmentId))
		.doOnNext(employee -> System.out.println(employee.toString()))
		.flatMap(employee -> AddressRepository.findAddressByEmployeeId(employee.getId()))
		.doOnNext(address -> System.out.println(address))
		.subscribe();
	}

}
