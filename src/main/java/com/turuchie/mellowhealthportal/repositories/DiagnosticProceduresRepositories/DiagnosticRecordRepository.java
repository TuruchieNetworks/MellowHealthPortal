package com.turuchie.mellowhealthportal.repositories.DiagnosticProceduresRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.DiagnosticRecord;

@Repository
public interface DiagnosticRecordRepository extends CrudRepository<DiagnosticRecord, Long> {
	List<DiagnosticRecord> findAll();
    Optional<DiagnosticRecord> findById(Long id);
    Optional<DiagnosticRecord> findFirstByOrderByCreatedAtDesc();
}