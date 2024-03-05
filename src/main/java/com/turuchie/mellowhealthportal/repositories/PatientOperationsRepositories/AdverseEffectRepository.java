package com.turuchie.mellowhealthportal.repositories.PatientOperationsRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.PatientOperations.AdverseEffect;

@Repository
public interface AdverseEffectRepository extends CrudRepository<AdverseEffect, Long> {
	List<AdverseEffect> findAll();
    Optional<AdverseEffect> findById(Long id);
}