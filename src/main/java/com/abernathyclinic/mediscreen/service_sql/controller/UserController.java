package com.abernathyclinic.mediscreen.service_sql.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abernathyclinic.mediscreen.service_sql.UserNotFoundException;
import com.abernathyclinic.mediscreen.service_sql.model.User;
import com.abernathyclinic.mediscreen.service_sql.repository.UserRepository;

/**
 * Main controller of the application, it provide CRUD mapping allowing the user
 * to communicate with a relational database. <br>
 */
@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String index() {
		return "Welcome on the Service-SQL REST API, targeted to be used as a micro-service to communicate with a relational database.";
	}

	/**
	 * GET mapping to retrieve a {@link User} from the database by using his last
	 * name and first name. <br>
	 * 
	 * @param lastName and firstName : of the user to retrieve
	 * @return the user if present in the database, else throw a
	 *         {@link UserNotFoundException}
	 */
	@GetMapping("/user{lastName}{firstName}")
	public User getUser(@RequestParam("lastName") String lastName, @RequestParam("firstName") String firstName) {
		return userRepository.findByLastNameAndFirstName(lastName, firstName)
				.orElseThrow(() -> new UserNotFoundException("The user with the last name provided : '" + lastName
						+ "' and first name provided : '" + firstName + "' could not be found in the database."));

	}

	/**
	 * POST mapping to save a {@link User} and save it in the database. <br>
	 * It firstly verifies that the user created successfully satisfies the
	 * constraints set on the entity. <br>
	 * 
	 * @param user          : to save
	 * @param bindingResult : verifies that the user to save satisfies the
	 *                      constraints
	 * @return a success message if the request is a success, else throw an
	 *         exception
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/user")
	public String saveUser(@Valid @RequestBody User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new IllegalArgumentException(
					"The user provided : '" + user.toString() + "' doesn't satisfy the required fields.");
		}

		userRepository.save(user);
		return "User sucessfully saved";
	}

	/**
	 * PUT mapping to update an existing {@link User} in the database. <br>
	 * It firstly verifies that the user to update is present in the database. <br>
	 * 
	 * @param uuid : of the user to update
	 * @param user : the informations to update
	 * @return a success message if the request is a success, else throw a
	 *         {@link UserNotFoundException}
	 */
	@PutMapping("/user/{uuid}")
	public String updateUser(@PathVariable("uuid") UUID uuid, @Valid @RequestBody User user) {
		User userToSave = userRepository.findById(uuid).orElseThrow(() -> new UserNotFoundException(
				"The provided uuid : '" + uuid + "' is not attributed to an existing user."));
		userToSave.setDateOfBirth(user.getDateOfBirth());
		userToSave.setFirstName(user.getFirstName());
		userToSave.setLastName(user.getLastName());
		userToSave.setHomeAddress(user.getHomeAddress());
		userToSave.setPhoneNumber(user.getPhoneNumber());
		userToSave.setSex(user.getSex());
		userRepository.save(userToSave);

		return "User successfully updated";
	}

	/**
	 * DELETE mapping used to delete a {@link User}. <br>
	 * It firstly verifies that the user is present in the database. <br>
	 * 
	 * @param uuid : of the user to delete
	 * @return a success message if the request is a success, else throw a
	 *         {@link UserNotFoundException}
	 */
	@DeleteMapping("/user/{uuid}")
	public String deleteUser(@PathVariable("uuid") UUID uuid) {
		User user = userRepository.findById(uuid).orElseThrow(() -> new UserNotFoundException(
				"The provided uuid : '" + uuid + "' is not attributed to an existing user."));
		userRepository.delete(user);

		return "The user has been successfully deleted in the database.";
	}
}
