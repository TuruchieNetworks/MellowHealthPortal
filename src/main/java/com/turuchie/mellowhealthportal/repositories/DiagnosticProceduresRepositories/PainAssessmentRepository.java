package com.turuchie.mellowhealthportal.repositories.DiagnosticProceduresRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PainAssessment;

@Repository
public interface PainAssessmentRepository extends CrudRepository<PainAssessment, Long> {
	List<PainAssessment> findAll();
    Optional<PainAssessment> findById(Long id);
    // Retrieve the most recent insurance information based on the start date
    Optional<PainAssessment> findFirstByOrderByCreatedAtDesc();
}