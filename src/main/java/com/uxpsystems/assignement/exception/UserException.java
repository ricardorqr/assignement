package com.uxpsystems.assignement.exception;

public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserException() {
		super();
	}

	public UserException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UserException(final String message) {
		super(message);
	}

	public UserException(final Throwable cause) {
		super(cause);
	}

}