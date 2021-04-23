package com.abernathyclinic.mediscreen.service_sql.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.abernathyclinic.mediscreen.service_sql.controller.PatientController;

/**
 * Custom exception used in the {@link PatientController} <br>
 * This exception is used to inform that the patient researched couldn't be
 * found in the database. <br>
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException {

	/**
	 * Required serial UID version
	 */
	private static final long serialVersionUID = -3807440365908099081L;

	/**
	 * Constructs a {@code PatientNotFoundException} with the specified detail
	 * message. <br>
	 * Exception to throw when the database do not found the patient that was
	 * searched. <br>
	 * 
	 * @param errorMessage : detail message about the error that have been thrown.
	 */
	public PatientNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
