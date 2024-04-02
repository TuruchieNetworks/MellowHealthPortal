package com.turuchie.mellowhealthportal.repositories.DiagnosticProceduresRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.FollowUpRecord;

@Repository
public interface FollowUpRecordRepository extends CrudRepository<FollowUpRecord, Long> {
	List<FollowUpRecord> findAll();
    Optional<FollowUpRecord> findById(Long id);
    Optional<FollowUpRecord> findFirstByOrderByCreatedAtDesc();
}