package com.abernathyclinic.mediscreen.service_sql.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abernathyclinic.mediscreen.service_sql.model.Patient;

/**
 * Interface used to define <b>CRUD</b> operations with the patient table. <br>
 * It extends the {@link JpaRepository} interface delivered by Spring Data JPA.
 */
public interface PatientRepository extends JpaRepository<Patient, UUID>, JpaSpecificationExecutor<Patient> {

	Optional<Patient> findByLastNameAndFirstName(String lastName, String firstName);

}
