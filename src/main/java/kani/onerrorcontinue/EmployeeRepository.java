package kani.onerrorcontinue;

import reactor.core.publisher.Flux;

class EmployeeRepository {
	
	static Flux<Employee> findEmployeeByDepartmenId(String departmentId) {
		
		if("Minneapolis".equalsIgnoreCase(departmentId)) {
			Employee e1 = new Employee("ME1", "Karthi");
			Employee e2 = new Employee("ME2", "Revathi");
			return Flux.just(e1, e2);
		}
		else if("Chicago".equalsIgnoreCase(departmentId)) {
			Employee e1 = new Employee("CE1", "Kumaran");
			Employee e2 = new Employee("CE2", "Pratheepa");
			return Flux.just(e1, e2);
		}
		return Flux.error(new RuntimeException("NoEmployeeFound for department:" + departmentId));
		
	}

}
