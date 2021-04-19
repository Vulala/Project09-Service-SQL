package com.abernathyclinic.mediscreen.service_sql.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * Entity representing the user, it is also used to define the database and
 * contains a small validation on fields. <br>
 */
@Entity
@Table(name = "users", schema = "abernathyclinic_mediscreen")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID uuid;
	@NotBlank(message = "The last name is mandatory")
	private String lastName;
	@NotBlank(message = "The first name is mandatory")
	private String firstName;
	@NotBlank(message = "The date of birth is mandatory")
	private String dateOfBirth;
	@NotBlank(message = "The sex is mandatory")
	private String sex;
	private String homeAddress;
	private String phoneNumber;

	public User() {
	}

	public User(@NotBlank(message = "The last name is mandatory") String lastName,
			@NotBlank(message = "The first name is mandatory") String firstName,
			@NotBlank(message = "The date of birth is mandatory") String dateOfBirth,
			@NotBlank(message = "The sex is mandatory") String sex, String homeAddress, String phoneNumber) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.dateOfBirth = dateOfBirth;
		this.sex = sex;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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
		return "User [UUID =" + uuid + ", Last Name :" + lastName + ", First Name :" + firstName + ", Date Of Birth :"
				+ dateOfBirth + ", Sex :" + sex + ", Home Address :" + homeAddress + ", Phone Number :" + phoneNumber
				+ "]";
	}

}
