package com.abernathyclinic.mediscreen.service_sql.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.abernathyclinic.mediscreen.service_sql.model.Patient;
import com.abernathyclinic.mediscreen.service_sql.repository.PatientRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PatientRepositoryIT {

	@Autowired
	private PatientRepository patientRepository;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	void injectedComponentsAreRightlySetup() {
		assertThat(patientRepository).isNotNull();
	}

	@DisplayName("GET : /patient{lastName}{firstName}")
	@Test
	void givenGettingASpecificPatient_whenGetPatient_thenItReturnTheRightPatientFromTheDataBase() {
		assertTrue(patientRepository.findByLastNameAndFirstName("lastName", "firstName").isPresent());
	}

	@DisplayName("POST : /patient")
	@Test
	void givenSavingAPatient_whenSavePatient_thenItSaveThePatientInTheDataBase() {
		Patient patientToSave = new Patient("lastNameSave", "firstNameSave", "dateOfBirthSave", "genderSave",
				"homeAddressSave", "phoneNumberSave");
		patientRepository.save(patientToSave);

		assertTrue(patientRepository.findByLastNameAndFirstName("lastNameSave", "firstNameSave").isPresent());
	}

	@DisplayName("PUT : /patient/{uuid}")
	@Test
	void givenUpdatingAPatient_whenUpdatePatient_thenItUpdateThePatientInTheDataBase() {
		Patient patientToUpdate = patientRepository.findByLastNameAndFirstName("lastName", "firstName").get();
		patientToUpdate.setGender("Ternary");
		patientRepository.save(patientToUpdate);

		assertEquals("Ternary", patientRepository.findByLastNameAndFirstName("lastName", "firstName").get().getGender());
	}

	@DisplayName("DELETE : /patient/{uuid}")
	@Test
	void givenDeletingAPatient_whenDeletePatient_thenItDeleteThePatientInTheDataBase() {
		patientRepository.delete(patientRepository.findByLastNameAndFirstName("lastName", "firstName").get());
		assertTrue(patientRepository.findByLastNameAndFirstName("lastName", "firstName").isEmpty());
	}

}
