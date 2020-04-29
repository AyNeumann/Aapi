package com.example.Aapi.exception;

import org.springframework.http.HttpStatus;

/**
 * Aapi Entity Exception class.
 * @author Aymeric NEUMANN
 *
 */
public class AapiEntityException extends RuntimeException {
	
	/**serialVersionUID. */
	private static final long serialVersionUID = 46244567523552943L;
	
	private HttpStatus httpErrorNumber;
	
	
	public AapiEntityException(final String message) {
		super(message);
	}
	
	public AapiEntityException(final String message, final HttpStatus badRequest) {
		super(message);
		this.httpErrorNumber = badRequest;
	}
	
	
	/**
	 * @return the httpErrorNumber
	 */
	public HttpStatus getHttpErrorNumber() {
		return httpErrorNumber;
	}
	/**
	 * @param httpErrorNumber the httpErrorNumber to set
	 */
	public void setHttpErrorNumber(HttpStatus httpErrorNumber) {
		this.httpErrorNumber = httpErrorNumber;
	}

}
