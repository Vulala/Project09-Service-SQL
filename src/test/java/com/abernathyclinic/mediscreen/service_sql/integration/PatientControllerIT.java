package com.abernathyclinic.mediscreen.service_sql.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.abernathyclinic.mediscreen.service_sql.repository.PatientRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PatientControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PatientRepository patientRepository;

	private UUID uuidOfThePatientInDB = UUID.fromString("b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9");

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
		assertThat(patientRepository).isNotNull();
	}

	@DisplayName("GET : /")
	@Test
	void givenGettingTheIndex_whenIndex_thenItReturnTheIndex() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(200, status);
	}

	@DisplayName("GET : /patient{lastName}{firstName}")
	@Test
	void givenGettingASpecificPatient_whenGetPatient_thenItReturnTheRightPatientFromTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patient?lastName=lastName&firstName=firstName")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
	}

	@DisplayName("GET : /patient{lastName}{firstName} but it throw an exception because the entity is not present in the database")
	@Test
	void givenGettingASpecificPatientWhoDoesntExist_whenGetPatient_thenItThrowAPatientNotFoundExceptionWithACorrectHTTPStatusCode()
			throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/patient?lastName=throw&firstName=exception")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(404, status);
	}

	@DisplayName("POST : /patient")
	@Test
	void givenSavingAPatient_whenSavePatient_thenItSaveThePatientInTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(
				post("/patient").contentType(MediaType.APPLICATION_JSON).content("{\"lastName\": \"nameLast\"}"))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(201, response.getStatus());
		assertEquals("Patient sucessfully saved", response.getContentAsString());
	}

	@DisplayName("PUT : /patient/{uuid}")
	@Test
	void givenUpdatingAPatient_whenUpdatePatient_thenItUpdateThePatientInTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(put("/patient/" + uuidOfThePatientInDB)
				.contentType(MediaType.APPLICATION_JSON).content("{\"lastName\": \"nameLast\"}")).andDo(print())
				.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("Patient successfully updated", response.getContentAsString());
	}

	@DisplayName("PUT : /patient/{uuid} but it throw an exception because the patient is not present in the database")
	@Test
	void givenUpdatingAPatientWhoDoesntExist_whenUpdatePatient_thenItThrowAPatientNotFoundExceptionWithACorrectHTTPStatusCode()
			throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(put("/patient/" + UUID.randomUUID())
				.contentType(MediaType.APPLICATION_JSON).content("{\"lastName\": \"nameLast\"}")).andDo(print())
				.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(404, response.getStatus());
	}

	@DisplayName("DELETE : /patient/{uuid}")
	@Test
	void givenDeletingAPatient_whenDeletePatient_thenItDeleteThePatientInTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc
				.perform(delete("/patient/" + uuidOfThePatientInDB).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("The patient has been successfully deleted in the database.", response.getContentAsString());
	}

	@DisplayName("DELETE : /patient/{uuid} but it throw an exception because the patient is not present in the database")
	@Test
	void givenDeletingAPatientWhoDoesntExist_whenDeletePatient_thenItThrowAPatientNotFoundExceptionWithACorrectHTTPStatusCode()
			throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc
				.perform(delete("/patient/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(404, response.getStatus());
	}
}
