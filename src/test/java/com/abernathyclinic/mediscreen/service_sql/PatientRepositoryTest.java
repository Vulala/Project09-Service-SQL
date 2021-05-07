package com.abernathyclinic.mediscreen.service_sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.abernathyclinic.mediscreen.service_sql.model.Patient;
import com.abernathyclinic.mediscreen.service_sql.repository.PatientRepository;

@DataJpaTest
class PatientRepositoryTest {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	void injectedComponentsAreRightlySetup() {
		assertThat(patientRepository).isNotNull();
		assertThat(testEntityManager).isNotNull();
	}

	@DisplayName("GET : /patient/{UUID}")
	@Test
	void givenGettingASpecificPatientUsingTheUUID_whenGetPatientByUUID_thenItReturnTheRightPatientFromTheDataBase() {
		UUID uuid = UUID.fromString("b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9");
		assertTrue(patientRepository.findById(uuid).isPresent());
	}

	@DisplayName("GET : /patient/{UUID} but it throw an exception because the patient is not present in the database")
	@Test
	void givenGettingASpecificPatientUsingTheUUIDButWhoDoesntExist_whenGetPatientByUUID_thenItThrowAPatientNotFoundExceptionWithACorrectHTTPStatusCode() {
		UUID uuid = UUID.fromString("b42a8ef5-8baa-4bc2-89aa-d18cdc3239f8");
		assertFalse(patientRepository.findById(uuid).isPresent());
	}

	@DisplayName("GET : /patient/lastName&firstName")
	@Test
	void givenGettingASpecificPatient_whenGetPatient_thenItReturnTheRightPatientFromTheDataBase() {
		assertTrue(patientRepository.findByLastNameAndFirstName("lastName", "firstName").isPresent());
	}

	@DisplayName("GET : /patient")
	@Test
	void givenGettingAllPatients_whenGetPatients_thenItReturnAllThePatientsFromTheDataBase() {
		assertFalse(patientRepository.findAll().isEmpty());
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

		assertEquals("Ternary",
				patientRepository.findByLastNameAndFirstName("lastName", "firstName").get().getGender());
	}

	@DisplayName("DELETE : /patient/{uuid}")
	@Test
	void givenDeletingAPatient_whenDeletePatient_thenItDeleteThePatientInTheDataBase() {
		Patient patientToStoreInDB = new Patient("lastNameDelete", "firstNameDelete", "00/00/0000Delete",
				"genderDelete", "homeAddressDelete", "000.000.000");
		testEntityManager.persist(patientToStoreInDB);
		patientRepository.delete(patientToStoreInDB);
		assertTrue(patientRepository.findByLastNameAndFirstName("lastNameDelete", "firstNameDelete").isEmpty());
	}

}
