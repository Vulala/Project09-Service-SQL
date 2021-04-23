package com.abernathyclinic.mediscreen.service_sql.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.abernathyclinic.mediscreen.service_sql.controller.PatientController;

/**
 * Custom exception used in {@link PatientController} <br>
 * This exception is used to inform that the body provided in the http request
 * is not validating the constraints set on the fields . <br>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BodyNotValidException extends RuntimeException {

	/**
	 * Required serial UID version
	 */
	private static final long serialVersionUID = 7358475117681145659L;

	/**
	 * Constructs a {@code BodyNotValidException} with the specified detail message.
	 * <br>
	 * Exception to throw when the body provided doesn't valid the fields's
	 * constraints. <br>
	 * 
	 * @param errorMessage : detail message about the error that have been thrown.
	 */
	public BodyNotValidException(String errorMessage) {
		super(errorMessage);
	}
}
