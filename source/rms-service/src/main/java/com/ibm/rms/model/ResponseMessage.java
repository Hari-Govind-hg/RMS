package com.ibm.rms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class ResponseMessage {	
	String status;
	String message;	
	Throwable cause;
	String stacktrace;
	String[] msg;
	
	public ResponseMessage(String status, String[] string) {
		 this.status = status;
		 this.msg = string;
	}
	public ResponseMessage(String status, String string) {
		 this.status = status;
		 this.message = string;
	}
	
	public ResponseMessage(String status, String message, String stacktrace) {
		 this.status = status;
		 this.message = message;
		 this.stacktrace = stacktrace;
	}
	
	public ResponseMessage(String status, String message, Throwable cause) {
		 this.status = status;
		 this.message = message;
		 this.cause = cause;
	}	


	public String getStatus() {
		return status;
	}	

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}
	
	public String getStacktrace() {
		return stacktrace;
	}

	public void setStacktrace(String stacktrace) {
		this.stacktrace = stacktrace;
	}	
}
