package com.turuchie.mellowhealthportal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientVitalRecord;

@Repository
public interface PatientVitalRecordRepository extends CrudRepository<PatientVitalRecord, Long> {
	List<PatientVitalRecord> findAll();
    Optional<PatientVitalRecord> findById(Long id);
	Optional<PatientVitalRecord> findFirstByOrderByCreatedAtDesc();
}