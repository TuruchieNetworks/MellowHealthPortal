package com.turuchie.mellowhealthportal.repositories.ClinicalOperationsRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PregnancyFertilityAssessment;

@Repository
public interface PregnancyFertilityAssessmentRepository extends CrudRepository<PregnancyFertilityAssessment, Long> {
	List<PregnancyFertilityAssessment> findAll();
    Optional<PregnancyFertilityAssessment> findById(Long id);
}