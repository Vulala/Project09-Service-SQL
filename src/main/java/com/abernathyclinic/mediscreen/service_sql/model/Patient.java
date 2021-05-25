package com.abernathyclinic.mediscreen.service_sql.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Entity representing a patient, it is also used to define the database and
 * contains a small validation on fields. <br>
 */
@Entity
@Table(name = "patients", schema = "abernathyclinic_mediscreen")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID uuid;
	@NotBlank(message = "The last name is mandatory")
	@Size(min = 2, max = 32, message = "The last name must be between 2 and 32 characters")
	private String lastName;
	@NotBlank(message = "The first name is mandatory")
	@Size(min = 2, max = 32, message = "The first name must be between 2 and 32 characters")
	private String firstName;
	@NotBlank(message = "The date of birth is mandatory")
	@Size(min = 8, max = 16, message = "The date of birth must be between 8 and 16 characters")
	private String dateOfBirth;
	@NotBlank(message = "The gender is mandatory")
	@Size(min = 1, max = 32, message = "The gender must be between 1 and 32 characters")
	private String gender;
	@Size(min = 1, max = 128, message = "The home address must be between 1 and 128 characters")
	private String homeAddress;
	@Size(min = 8, max = 16, message = "The phone number must be between 8 and 16 characters")
	private String phoneNumber;

	public Patient() {
	}

	public Patient(
			@NotBlank(message = "The last name is mandatory") @Size(min = 2, max = 32, message = "The last name must be between 2 and 32 characters") String lastName,
			@NotBlank(message = "The first name is mandatory") @Size(min = 2, max = 32, message = "The first name must be between 2 and 32 characters") String firstName,
			@NotBlank(message = "The date of birth is mandatory") @Size(min = 8, max = 16, message = "The date of birth must be between 8 and 16 characters") String dateOfBirth,
			@NotBlank(message = "The gender is mandatory") @Size(min = 1, max = 32, message = "The gender must be between 1 and 32 characters") String gender,
			@Size(min = 1, max = 128, message = "The home address must be between 1 and 128 characters") String homeAddress,
			@Size(min = 8, max = 16, message = "The phone number must be between 8 and 16 characters") String phoneNumber) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.homeAddress = homeAddress;
		this.phoneNumber = phoneNumber;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Patient [UUID: " + uuid + ", Last Name: " + lastName + ", First Name: " + firstName
				+ ", Date of Birth: " + dateOfBirth + ", Gender: " + gender + ", Home Address: " + homeAddress
				+ ", Phone Number: " + phoneNumber + "]";
	}

}
