package com.turuchie.mellowhealthportal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.UrineAnalysis;

@Repository
public interface UrineAnalysisRepository extends CrudRepository<UrineAnalysis, Long> {
	List<UrineAnalysis> findAll();
    Optional<UrineAnalysis> findById(Long id);
}