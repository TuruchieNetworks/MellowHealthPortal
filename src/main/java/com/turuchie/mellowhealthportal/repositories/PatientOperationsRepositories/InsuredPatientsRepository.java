package com.turuchie.mellowhealthportal.repositories.PatientOperationsRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.Administrations.InsuredPatients;

@Repository
public interface InsuredPatientsRepository extends CrudRepository<InsuredPatients, Long> {
	List<InsuredPatients> findAll();
    Optional<InsuredPatients> findById(Long id);
    // Retrieve the most recent insurance information based on the start date
    Optional<InsuredPatients> findFirstByOrderByCreatedAtDesc();
}