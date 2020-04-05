package com.example.Aapi.exception;

/**
 * Aapi Entity Exception class.
 * @author Aymeric NEUMANN
 *
 */
public class AapiEntityException extends RuntimeException {
	
	/**serialVersionUID. */
	private static final long serialVersionUID = 46244567523552943L;
	
	public AapiEntityException(final String message) {
		super(message);
	}

}
