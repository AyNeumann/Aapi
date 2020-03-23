package com.example.Aapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@ExceptionHandler({IllegalArgumentException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleException(final Exception ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AapiEntityException.class)
	public String handleAapiEntityException(final AapiEntityException aapiEntityException) {
		String errorMessage = aapiEntityException.getMessage();
		return errorMessage;
	}
}
