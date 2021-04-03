package com.kani.reactor.onerrorcontinue;

public class NoEmployeeFoundException extends RuntimeException {

	private static final long serialVersionUID = -6426976625117182585L;

	public NoEmployeeFoundException(String message) {
		super(message);
	}

}
