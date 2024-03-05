package com.turuchie.mellowhealthportal.repositories.PatientOperationsRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.PatientOperations.PastMedicalHistory;

@Repository
public interface PastMedicalHistoryRepository extends CrudRepository<PastMedicalHistory, Long> {
    List<PastMedicalHistory> findAll();
    Optional<PastMedicalHistory> findById(Long id);
    Optional<PastMedicalHistory> findByMedication(String medication);
    Optional<PastMedicalHistory> findByMedicalCondition(String medicalCondition);
    Optional<PastMedicalHistory> findByMedicalConditionContainingIgnoreCase(String partialMedicalCondition);
    Optional<PastMedicalHistory> findByMedicationContainingIgnoreCase(String medication);
	Optional<PastMedicalHistory> findFirstByOrderByCreatedAtDesc();
}