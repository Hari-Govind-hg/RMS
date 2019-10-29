package com.ibm.rms.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ibm.rms.model.ResponseMessage;

@ControllerAdvice
public class RmsExceptionHandler {
	
	private static Logger log = LoggerFactory.getLogger(RmsExceptionHandler.class);
	
	@ExceptionHandler(RmsApplicationException.class)
	public ResponseEntity<ResponseMessage> handleRmsApplicationExcpetion(RmsApplicationException e) {
		log.error("ERROR OCCURED: {}", e.getMessage(), e);
		ResponseMessage resMsg = new ResponseMessage("Failure", new String (e.getMessage()), ExceptionUtils.getStackTrace(e));
		return ResponseEntity.badRequest().body(resMsg);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseMessage> handleAppExcpetion(Exception e) {
		log.error("ERROR OCCURED: {}", e.getMessage(), e);
		ResponseMessage resMsg = new ResponseMessage("Failure", new String (e.getMessage()), ExceptionUtils.getStackTrace(e));
		return ResponseEntity.badRequest().body(resMsg);
	}
}
