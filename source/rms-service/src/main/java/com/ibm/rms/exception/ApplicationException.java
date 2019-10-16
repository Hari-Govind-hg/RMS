package com.ibm.rms.exception;

public class ApplicationException extends Exception {
	
	public ApplicationException() {
		super();
	}
	
	public ApplicationException(String message, Throwable e) {
		super(message, e);
	}
	
}
