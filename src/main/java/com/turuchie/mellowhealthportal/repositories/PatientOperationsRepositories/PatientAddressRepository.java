package com.turuchie.mellowhealthportal.repositories.PatientOperationsRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.PatientOperations.PatientAddress;

@Repository
public interface PatientAddressRepository extends CrudRepository<PatientAddress, Long> {
	List<PatientAddress> findAll();
    Optional<PatientAddress> findById(Long id);
    Optional<PatientAddress> findFirstByOrderByCreatedAtDesc();
}