package com.turuchie.mellowhealthportal.repositories.PatientOperationsRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.PatientOperations.InsuranceInformation;

@Repository
public interface InsuranceInformationRepository extends CrudRepository<InsuranceInformation, Long> {
	List<InsuranceInformation> findAll();
    Optional<InsuranceInformation> findById(Long id);
    // Retrieve the most recent insurance information based on the start date
    Optional<InsuranceInformation> findFirstByOrderByStartDateDesc();
}