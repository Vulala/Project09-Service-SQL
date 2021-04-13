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

import com.abernathyclinic.mediscreen.service_sql.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	private UUID uuidOfTheUserInDB = UUID.fromString("b42a8ef5-8baa-4bc2-89aa-d18cdc3239f9");

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
		// ACT
		MvcResult mvcResult = mockMvc.perform(get("/user?lastName=lastName&firstName=firstName")).andDo(print())
				.andReturn();
		int status = mvcResult.getResponse().getStatus();

		// ASSERT
		assertEquals(200, status);
	}

	@DisplayName("POST : /user")
	@Test
	void givenSavingAUser_whenSaveUser_thenItSaveTheUserInTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc
				.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content("{\"lastName\": \"nameLast\"}"))
				.andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("User sucessfully saved", response.getContentAsString());
	}

	@DisplayName("PUT : /user/{uuid}")
	@Test
	void givenUpdatingAUser_whenUpdateUser_thenItUpdateTheUserInTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc.perform(put("/user/" + uuidOfTheUserInDB).contentType(MediaType.APPLICATION_JSON)
				.content("{\"lastName\": \"nameLast\"}")).andDo(print()).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("User sucessfully updated", response.getContentAsString());
	}

	@DisplayName("DELETE : /user/{uuid}")
	@Test
	void givenDeletingAUser_whenDeleteUser_thenItDeleteTheUserInTheDataBase() throws Exception {
		// ACT
		MvcResult mvcResult = mockMvc
				.perform(delete("/user/" + uuidOfTheUserInDB).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();

		// ASSERT
		assertEquals(200, response.getStatus());
		assertEquals("The user have been sucessfully deleted in the database.", response.getContentAsString());
	}
}
