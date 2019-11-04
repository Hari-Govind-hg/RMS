package com.ibm.rms.exception;

public class RmsApplicationException extends Exception {
	
	public RmsApplicationException() {
		super();
	}
	
	public RmsApplicationException(String message, Throwable e) {
		super(message, e);
	}
	
}
