package com.turuchie.mellowhealthportal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    List<Patient> findAll();
    Optional<Patient> findById(Long id);
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByPassword(String password);
    Optional<Patient> findByPatientFirstName(String patientFirstName);
    Optional<Patient> findByPatientLastName(String patientLastName);
    Optional<Patient> findByPatientFirstNameContaining(String firstName);  
    Optional<Patient> findByPatientLastNameContaining(String lastName);
    Optional<Patient> findByPatientFirstNameContainingOrPatientLastNameContaining(String firstName, String lastName);
    Optional<Patient> findByPatientFirstNameContainingIgnoreCase(String partialName);
    Optional<Patient> findByPatientLastNameContainingIgnoreCase(String partialName);
    Optional<Patient> findByPatientFirstNameContainingOrPatientLastNameContainingIgnoreCase(String firstName, String lastName);
    boolean existsByEmailAndIdNot(String email, Long patientId);
    @Query("SELECT p FROM Patient p WHERE LOWER(p.patientFirstName) LIKE %:partialName% OR LOWER(p.patientLastName) LIKE %:partialName%")
    List<Patient> findByPartialName(@Param("partialName") String partialName);
    @Query("SELECT p FROM Patient p WHERE LOWER(REPLACE(p.patientFirstName, ' ', '')) LIKE %:partialName% OR LOWER(REPLACE(p.patientLastName, ' ', '')) LIKE %:partialName%")
    List<Patient> findByPartialNames(@Param("partialName") String partialName);
	List<Patient> findByDateOfBirthIsNotNull();
	List<Patient> findByPatientFirstNameContainingIgnoreCaseOrPatientLastNameContainingIgnoreCase(
			String trimmedSearchName, String trimmedSearchName2);
}