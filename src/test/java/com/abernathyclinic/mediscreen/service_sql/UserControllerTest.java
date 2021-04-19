package com.abernathyclinic.mediscreen.service_sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.abernathyclinic.mediscreen.service_sql.controller.UserController;
import com.abernathyclinic.mediscreen.service_sql.model.User;
import com.abernathyclinic.mediscreen.service_sql.repository.UserRepository;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	void injectedComponentsAreRightlySetup() {
		assertThat(mockMvc).isNotNull();
		assertThat(userRepository).isNotNull();
	}

	@DisplayName("GET : /")
	@Test
	void givenGettingTheIndex_whenIndex_thenItReturnTheIndex() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/")).andDo(print()).andReturn();
		int status = mvcResult.getResponse().getStatus();

		assertEquals(200, status);
	}

	@DisplayName("GET : /user{lastName}{firstName}")
	@Test
	void givenGettingASpecificUser_whenGetUser_thenItReturnTheRightUserFromTheDataBase() throws Exception {
		// ARRANGE
		when(userRepository.findByLastNameAndFirstName("lastName", "firstName")).thenReturn(Optional.of(new User()));

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/user?lastName=lastName&firstName=firstName")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
		verify(userRepository, times(1)).findByLastNameAndFirstName("lastName", "firstName");
	}

	@DisplayName("GET : /user{lastName}{firstName} but it throw an exception because the user is not present in the database")
	@Test
	void givenGettingASpecificUserWhoDoesntExist_whenGetUser_thenItThrowAUserNotFoundExceptionWithACorrectHTTPStatusCode()
			throws Exception {
		// ARRANGE
		when(userRepository.findByLastNameAndFirstName("lastName", "firstName")).thenReturn(Optional.of(new User()));

		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/user?lastName=throw&firstName=exception")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(404, status);
	}

	@DisplayName("POST : /user")
	@Test
	void givenSavingAUser_whenSaveUser_thenItSaveTheUserInTheDataBase() throws Exception {
		// ARRANGE
		User userToSave = new User("lastName", "firstName", "dateOfBirth", "sex", "homeAddress", "phoneNumber");
		when(userRepository.save(userToSave)).thenReturn(userToSave);

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content("{\"lastName\": \"lastName\"}"))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(201, response.getStatus());
		assertEquals("User sucessfully saved", response.getContentAsString());
	}

	@DisplayName("PUT : /user/{uuid}")
	@Test
	void givenUpdatingAUser_whenUpdateUser_thenItUpdateTheUserInTheDataBase() throws Exception {
		// ARRANGE
		User userToUpdate = new User("lastName", "firstName", "dateOfBirth", "sex", "homeAddress", "phoneNumber");
		UUID randomUUID = UUID.randomUUID();
		when(userRepository.findById(randomUUID)).thenReturn(Optional.of(userToUpdate));
		when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);

		// ACT
		MvcResult mvcResult = mockMvc.perform(put("/user/" + randomUUID).contentType(MediaType.APPLICATION_JSON)
				.content("{\"lastName\": \"nameLast\"}")).andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("User successfully updated", response.getContentAsString());
		verify(userRepository, times(1)).findById(randomUUID);
		verify(userRepository, times(1)).save(userToUpdate);
	}

	@DisplayName("PUT : /user/{uuid} but it throw an exception because the user is not present in the database")
	@Test
	void givenUpdatingAUserWhoDoesntExist_whenUpdateUser_thenItThrowAUserNotFoundExceptionWithACorrectHTTPStatusCode()
			throws Exception {
		// ARRANGE
		User userToUpdate = new User("lastName", "firstName", "dateOfBirth", "sex", "homeAddress", "phoneNumber");
		UUID randomUUID = UUID.randomUUID();
		when(userRepository.findById(randomUUID)).thenReturn(Optional.of(userToUpdate));

		// ACT
		MvcResult mvcResult = mockMvc.perform(put("/user/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON)
				.content("{\"lastName\": \"throwException\"}")).andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(404, response.getStatus());
	}

	@DisplayName("DELETE : /user/{uuid}")
	@Test
	void givenDeletingAUser_whenDeleteUser_thenItDeleteTheUserInTheDataBase() throws Exception {
		// ARRANGE
		User userToDelete = new User("lastName", "firstName", "dateOfBirth", "sex", "homeAddress", "phoneNumber");
		UUID randomUUID = UUID.randomUUID();
		when(userRepository.findById(randomUUID)).thenReturn(Optional.of(userToDelete));
		doNothing().when(userRepository).delete(userToDelete);

		// ACT
		MvcResult mvcResult = mockMvc.perform(delete("/user/" + randomUUID).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("The user has been successfully deleted in the database.", response.getContentAsString());
		verify(userRepository, times(1)).findById(randomUUID);
		verify(userRepository, times(1)).delete(userToDelete);
	}

	@DisplayName("DELETE : /user/{uuid} but it throw an exception because the user is not present in the database")
	@Test
	void givenDeletingAUserWhoDoesntExist_whenDeleteUser_thenItThrowAUserNotFoundExceptionWithACorrectHTTPStatusCode()
			throws Exception {
		// ARRANGE
		User userToDelete = new User("lastName", "firstName", "dateOfBirth", "sex", "homeAddress", "phoneNumber");
		UUID randomUUID = UUID.randomUUID();
		when(userRepository.findById(randomUUID)).thenReturn(Optional.of(userToDelete));

		// ACT
		MvcResult mvcResult = mockMvc
				.perform(delete("/user/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(404, response.getStatus());
	}
}
