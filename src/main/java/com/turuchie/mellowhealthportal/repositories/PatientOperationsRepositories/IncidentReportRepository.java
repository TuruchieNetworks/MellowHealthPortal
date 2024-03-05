package com.turuchie.mellowhealthportal.repositories.PatientOperationsRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.PatientOperations.IncidentReport;

@Repository
public interface IncidentReportRepository extends CrudRepository<IncidentReport, Long> {
	List<IncidentReport> findAll();
    Optional<IncidentReport> findByEvent(String event);
    Optional<IncidentReport> findById(Long id);
}