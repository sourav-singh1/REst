package com.yash.demo.exception;

public class MyEmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MyEmployeeNotFoundException(String message) {
		super(message);
	}

}
