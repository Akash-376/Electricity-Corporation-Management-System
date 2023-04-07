package com.powerhouse.exception;

public class NoRecordFoundException extends RuntimeException {
	
	public NoRecordFoundException (String message) {
		super(message);
	}

	@Override
	public String toString() {
		return getMessage();
	}
	
	
}
