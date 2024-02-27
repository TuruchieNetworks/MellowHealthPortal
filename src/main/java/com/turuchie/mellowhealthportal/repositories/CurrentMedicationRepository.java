package com.turuchie.mellowhealthportal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.PatientOperations.CurrentMedication;

@Repository
public interface CurrentMedicationRepository extends CrudRepository<CurrentMedication, Long> {
	List<CurrentMedication> findAll();
    Optional<CurrentMedication> findById(Long id);
    // Retrieve the most recent insurance information based on the start date
    Optional<CurrentMedication> findFirstByOrderByStartDateDesc();
}