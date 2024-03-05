package com.turuchie.mellowhealthportal.repositories.ClinicalOperationsRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.CoagulationRecord;

@Repository
public interface CoagulationRecordRepository extends CrudRepository<CoagulationRecord, Long> {
	List<CoagulationRecord> findAll();
    Optional<CoagulationRecord> findById(Long id);
    Optional<CoagulationRecord> findFirstByOrderByCreatedAtDesc();
}