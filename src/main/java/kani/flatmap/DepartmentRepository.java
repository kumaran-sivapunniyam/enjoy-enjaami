package kani.flatmap;

import reactor.core.publisher.Mono;

 class DepartmentRepository {
	
	 static Mono<Department> findDepartmentById(String departmentId) {
		
		if("Minneapolis".equalsIgnoreCase(departmentId)) {
			Department d = new Department(departmentId, "M");
			return Mono.just(d);
		}
		else if("Chicago".equalsIgnoreCase(departmentId)) {
			Department d = new Department(departmentId, "C");
			return Mono.just(d);
		}
		return Mono.empty();
		
	}

}
