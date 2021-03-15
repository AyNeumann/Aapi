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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Handle custom exceptions
 * @author Aymeric NEUMANN
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	/** Reference to the log4j logger. */
	private static final Logger LOG = LogManager.getLogger();
	
	/**
	 * Exception handler for Illegal Argument Exceptions.
	 * @param ex the thrown illegal argument exception
	 * @return error message as a string
	 */
	@ExceptionHandler({IllegalArgumentException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<AapiEntityException> handleIllegalArgumentException(final IllegalArgumentException ex) {
		AapiEntityException exception = new AapiEntityException(ex.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<AapiEntityException>(exception, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Exception handler for Method Argument Type Mismatch Exception.
	 * @param ex the thrown method argument type mismatch exception
	 * @return error message as a string
	 */
	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleTypeMissmatchException(final MethodArgumentTypeMismatchException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
		
	/**
	 * Exception handler for Aapi Entity Exception.
	 * @param ex the thrown Aapi entity exception
	 * @return an exception with a message and a httpErrorNumber 
	 */
	@ExceptionHandler(AapiEntityException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<AapiEntityException> handleAapiEntityException(final AapiEntityException ex) {
		AapiEntityException exception = new AapiEntityException(ex.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<AapiEntityException>(exception, exception.getHttpErrorNumber());
	}
	
	/**
	 * Exception handler for Constraint Violation Exception.
	 * @param ex thrown constraint violation exception
	 * @return an exception with a message and a httpErrorNumber 
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<AapiEntityException> handleConstraintViolationException(final ConstraintViolationException  ex) {
		
		//If there is multiple error constraintViolationException will contain multiple error message separated by commas
		String defaultMessage = ex.getMessage();
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
		AapiEntityException exception = new AapiEntityException(expMessage.toString(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<AapiEntityException>(exception, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Exception handler for Http Message Not Readable Exception.
	 * @param thrown HTTP message not readable exception
	 * @return error message as a string
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleHttpMessageNotReadableException(final HttpMessageNotReadableException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Format and log each error contained in the constraint violation exception.
	 * @param eMessage exception error message
	 * @return formatted error message as a string
	 */
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
