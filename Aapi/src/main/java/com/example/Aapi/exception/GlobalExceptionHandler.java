package com.example.Aapi.exception;

import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handle custom exceptions
 * @author Aymeric NEUMANN
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	/** Reference to the log4j logger. */
	private static final Logger LOG = LogManager.getLogger();
	
	/*@ExceptionHandler(AapiEntityException.class)
	public String handleAapiEntityException(final AapiEntityException aapiEntityException) {
		String errorMessage = aapiEntityException.getMessage();
		return errorMessage;
	}*/
	
	@ExceptionHandler({IllegalArgumentException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleException(final IllegalArgumentException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AapiEntityException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleAapiEntityException(final AapiEntityException aapiEntityException) {
		return new ResponseEntity<String>(aapiEntityException.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleConstraintViolationException(final ConstraintViolationException  constraintViolationException) {
		
		//If there is multiple error constraintViolationException will contain multiple error message separated by commas
		String defaultMessage = constraintViolationException.getMessage();
		LOG.warn("Default Message: " + defaultMessage);
		StringBuilder expMessage = new StringBuilder();
		
		if(defaultMessage.contains(",")) {
			String[] messages = defaultMessage.split(",");
			for(String m : messages) {
				expMessage.append(formatAndLogExceptionMessage(m));
				expMessage.append("\r\n");
			}
		} else {
			expMessage.append(formatAndLogExceptionMessage(defaultMessage));
		}
		
		return new ResponseEntity<String>(expMessage.toString(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleHttpMessageNotReadableException(final HttpMessageNotReadableException  httpMessageNotReadableException) {
		return new ResponseEntity<String>(httpMessageNotReadableException.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	private String formatAndLogExceptionMessage(String eMessage) {
		
		String number = eMessage.replaceAll("[^0-9]", "");
		
		StringBuilder logMessage = new StringBuilder();
		logMessage.append("The ");
		logMessage.append(number);
		logMessage.append(" BlobJ of the list contains invalid data");
		LOG.warn(logMessage);
		
		String[] errortype = eMessage.split("\\.");
		String[] errorfield = errortype[2].split(":");
		
		StringBuilder expMessage = new StringBuilder();
		expMessage.append(logMessage);
		expMessage.append(": the field '");
		expMessage.append(errorfield[0]);
		expMessage.append("' cannot be null");
		
		return expMessage.toString();
	}
}
