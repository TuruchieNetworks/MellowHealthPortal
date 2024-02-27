package com.turuchie.mellowhealthportal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;

@Repository
public interface PatientCaseRepository extends CrudRepository<PatientCase, Long> {
	List<PatientCase> findAll();
    Optional<PatientCase> findById(Long id);
    Optional<PatientCase> findByPatientName(String patientName);
    Optional<PatientCase> findFirstByOrderByCreatedAtDesc();
}