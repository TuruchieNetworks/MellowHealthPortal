package com.turuchie.mellowhealthportal.repositories.DiagnosticProceduresRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.DoseRegimen;

@Repository
public interface DoseRegimenRepository extends CrudRepository<DoseRegimen, Long> {
	List<DoseRegimen> findAll();
    Optional<DoseRegimen> findById(Long id);
    Optional<DoseRegimen> findFirstByOrderByCreatedAtDesc();
}