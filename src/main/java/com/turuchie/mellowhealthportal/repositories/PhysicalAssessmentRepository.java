package com.turuchie.mellowhealthportal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PhysicalAssessment;

@Repository
public interface PhysicalAssessmentRepository extends CrudRepository<PhysicalAssessment, Long> {
	List<PhysicalAssessment> findAll();
    Optional<PhysicalAssessment> findById(Long id);
    // Retrieve the most recent insurance information based on the start date
    Optional<PhysicalAssessment> findFirstByOrderByCreatedAtDesc();
}