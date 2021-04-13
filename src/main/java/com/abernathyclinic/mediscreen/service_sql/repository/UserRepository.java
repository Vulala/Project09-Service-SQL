package com.abernathyclinic.mediscreen.service_sql.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abernathyclinic.mediscreen.service_sql.model.User;

/**
 * Interface used to define <b>CRUD</b> operations with the user table. <br>
 * It extends the {@link JpaRepository} interface delivered by Spring Data JPA.
 */
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

	Optional<User> findByLastNameAndFirstName(String lastName, String firstName);

}
