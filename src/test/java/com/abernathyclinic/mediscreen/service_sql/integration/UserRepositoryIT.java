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

import com.abernathyclinic.mediscreen.service_sql.model.User;
import com.abernathyclinic.mediscreen.service_sql.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryIT {

	@Autowired
	private UserRepository userRepository;

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	void injectedComponentsAreRightlySetup() {
		assertThat(userRepository).isNotNull();
	}

	@DisplayName("GET : /user{lastName}{firstName}")
	@Test
	void givenGettingASpecificUser_whenGetUser_thenItReturnTheRightUserFromTheDataBase() {
		assertTrue(userRepository.findByLastNameAndFirstName("lastName", "firstName").isPresent());
	}

	@DisplayName("POST : /user")
	@Test
	void givenSavingAUser_whenSaveUser_thenItSaveTheUserInTheDataBase() {
		User userToSave = new User("lastNameSave", "firstNameSave", "dateOfBirthSave", "sexSave", "homeAddressSave",
				"phoneNumberSave");
		userRepository.save(userToSave);

		assertTrue(userRepository.findByLastNameAndFirstName("lastNameSave", "firstNameSave").isPresent());
	}

	@DisplayName("PUT : /user/{uuid}")
	@Test
	void givenUpdatingAUser_whenUpdateUser_thenItUpdateTheUserInTheDataBase() {
		User userToUpdate = userRepository.findByLastNameAndFirstName("lastName", "firstName").get();
		userToUpdate.setSex("Ternary");
		userRepository.save(userToUpdate);

		assertEquals("Ternary", userRepository.findByLastNameAndFirstName("lastName", "firstName").get().getSex());
	}

	@DisplayName("DELETE : /user/{uuid}")
	@Test
	void givenDeletingAUser_whenDeleteUser_thenItDeleteTheUserInTheDataBase() {
		userRepository.delete(userRepository.findByLastNameAndFirstName("lastName", "firstName").get());
		assertTrue(userRepository.findByLastNameAndFirstName("lastName", "firstName").isEmpty());
	}

}
