package kani.onerrorcontinue;

import reactor.core.publisher.Mono;

class AddressRepository {
	
	static Mono<Address> findAddressByEmployeeId(String employeeId) {
		
		if("ME11".equalsIgnoreCase(employeeId)) {
			Address a = Address.builder().employeeId(employeeId)
					.street("123 Stree")
					.city("Minnepolis")
					.state("MN")
					.zip("12345")
					.build();
			return Mono.just(a);
		}
		else if("ME2".equalsIgnoreCase(employeeId)) {
			Address a = Address.builder().employeeId(employeeId)
					.street("321 Street")
					.city("Minnepolis")
					.state("MN")
					.zip("12346")
					.build();
			return Mono.just(a);
		}
		else if("CE1".equalsIgnoreCase(employeeId)) {
			Address a = Address.builder().employeeId(employeeId)
					.street("123 Street")
					.city("Chicago")
					.state("IL")
					.zip("67890")
					.build();
			return Mono.just(a);
		}
		else if("CE2".equalsIgnoreCase(employeeId)) {
			Address a = Address.builder().employeeId(employeeId)
					.street("12356 Street")
					.city("Chicago")
					.state("IL")
					.zip("67891")
					.build();
			return Mono.just(a);
		}
		return Mono.error(new RuntimeException("No Data Found"));
		
	}

}
