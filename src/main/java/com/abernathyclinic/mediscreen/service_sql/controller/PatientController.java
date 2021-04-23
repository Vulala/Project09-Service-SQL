package com.abernathyclinic.mediscreen.service_sql.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abernathyclinic.mediscreen.service_sql.exception.BodyNotValidException;
import com.abernathyclinic.mediscreen.service_sql.exception.PatientNotFoundException;
import com.abernathyclinic.mediscreen.service_sql.model.Patient;
import com.abernathyclinic.mediscreen.service_sql.repository.PatientRepository;

/**
 * Main controller of the application, it provide CRUD mapping allowing the user
 * to communicate with a relational database. <br>
 */
@RestController
public class PatientController {

	@Autowired
	private PatientRepository patientRepository;

	@GetMapping("/")
	public String index() {
		return "Welcome on the Service-SQL REST API, targeted to be used as a micro-service to communicate with a relational database.";
	}

	/**
	 * GET mapping to retrieve a {@link Patient} from the database by using his last
	 * name and first name. <br>
	 * 
	 * @param lastName and firstName : of the patient to retrieve
	 * @return the patient if present in the database, else throw a
	 *         {@link PatientNotFoundException}
	 */
	@GetMapping("/patient{lastName}{firstName}")
	public Patient getPatient(@RequestParam("lastName") String lastName, @RequestParam("firstName") String firstName) {
		return patientRepository.findByLastNameAndFirstName(lastName, firstName)
				.orElseThrow(() -> new PatientNotFoundException("The patient with the last name provided : '" + lastName
						+ "' and first name provided : '" + firstName + "' could not be found in the database."));

	}

	/**
	 * POST mapping to save a {@link Patient} and save it in the database. <br>
	 * It firstly verifies that the patient created successfully satisfies the
	 * constraints set on the entity. <br>
	 * 
	 * @param patient       : to save
	 * @param bindingResult : verifies that the patient to save satisfies the
	 *                      constraints
	 * @return a success message if the request is a success, else throw a
	 *         {@link BodyNotValidException}
	 */
	@PostMapping("/patient")
	public String savePatient(@Valid @RequestBody Patient patient, BindingResult bindingResult,
			HttpServletResponse httpServletResponse) {
		if (bindingResult.hasErrors()) {
			return new BodyNotValidException(
					"The patient provided : '" + patient.toString() + "' doesn't satisfy the required field: '"
							+ bindingResult.getFieldError().getDefaultMessage() + "'.").toString();
		}

		httpServletResponse.setStatus(201);
		patientRepository.save(patient);
		return "Patient sucessfully saved";
	}

	/**
	 * PUT mapping to update an existing {@link Patient} in the database. <br>
	 * It firstly verifies that the patient to update is present in the database.
	 * <br>
	 * 
	 * @param uuid    : of the patient to update
	 * @param patient : the informations to update
	 * @return a success message if the request is a success, else throw a
	 *         {@link PatientNotFoundException} or a {@link BodyNotValidException}
	 *         depending of what went wrong
	 */
	@PutMapping("/patient/{uuid}")
	public String updatePatient(@PathVariable("uuid") UUID uuid, @Valid @RequestBody Patient patient,
			BindingResult bindingResult, HttpServletResponse httpServletResponse) {
		Patient patientToUpdate = patientRepository.findById(uuid).orElseThrow(() -> new PatientNotFoundException(
				"The provided uuid : '" + uuid + "' is not attributed to an existing patient."));

		if (bindingResult.hasErrors()) {
			return new BodyNotValidException(
					"The patient provided : '" + patient.toString() + "' doesn't satisfy the required field: '"
							+ bindingResult.getFieldError().getDefaultMessage() + "'.").toString();
		}

		patientToUpdate.setDateOfBirth(patient.getDateOfBirth());
		patientToUpdate.setFirstName(patient.getFirstName());
		patientToUpdate.setLastName(patient.getLastName());
		patientToUpdate.setHomeAddress(patient.getHomeAddress());
		patientToUpdate.setPhoneNumber(patient.getPhoneNumber());
		patientToUpdate.setGender(patient.getGender());
		patientRepository.save(patientToUpdate);

		return "Patient successfully updated";
	}

	/**
	 * DELETE mapping used to delete a {@link Patient}. <br>
	 * It firstly verifies that the patient is present in the database. <br>
	 * 
	 * @param uuid : of the patient to delete
	 * @return a success message if the request is a success, else throw a
	 *         {@link PatientNotFoundException}
	 */
	@DeleteMapping("/patient/{uuid}")
	public String deletePatient(@PathVariable("uuid") UUID uuid) {
		Patient patient = patientRepository.findById(uuid).orElseThrow(() -> new PatientNotFoundException(
				"The provided uuid : '" + uuid + "' is not attributed to an existing patient."));
		patientRepository.delete(patient);

		return "The patient has been successfully deleted in the database.";
	}
}
