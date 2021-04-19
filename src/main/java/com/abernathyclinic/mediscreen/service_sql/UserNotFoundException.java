package com.abernathyclinic.mediscreen.service_sql;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception used in the Controller. <br>
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	/**
	 * Required serial UID version
	 */
	private static final long serialVersionUID = -3807440365908099081L;

	/**
	 * Constructs a {@code UserNotFoundException} with the specified detail message.
	 * <br>
	 * Exception to throw when the database do not found the user that was searched.
	 * <br>
	 * 
	 * @param errorMessage : detail message about the error that have been thrown.
	 */
	public UserNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
