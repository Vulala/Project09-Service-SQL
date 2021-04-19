package com.abernathyclinic.mediscreen.service_sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.abernathyclinic.mediscreen.service_sql.model.User;
import com.abernathyclinic.mediscreen.service_sql.repository.UserRepository;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestEntityManager testEntityManager;

//	private User userToStoreInDB = new User("lastName", "firstName", "dateOfBirth", "sex", "homeAddress",
	// "phoneNumber");

	@BeforeEach
	void populateTheLocalDatabase() {
		// testEntityManager.persist(userToStoreInDB);
	}

	@DisplayName("Injected Components Are Rightly Setup")
	@Test
	void injectedComponentsAreRightlySetup() {
		assertThat(userRepository).isNotNull();
		assertThat(testEntityManager).isNotNull();
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
		User userToStoreInDB = new User("lastNameDelete", "firstNameDelete", "00/00/0000Delete", "sexDelete",
				"homeAddressDelete", "000.000.000");
		testEntityManager.persist(userToStoreInDB);
		userRepository.delete(userToStoreInDB);
		assertTrue(userRepository.findByLastNameAndFirstName("lastNameDelete", "firstNameDelete").isEmpty());
	}

}
