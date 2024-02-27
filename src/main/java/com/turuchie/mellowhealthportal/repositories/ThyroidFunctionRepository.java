package com.turuchie.mellowhealthportal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.ThyroidFunction;

@Repository
public interface ThyroidFunctionRepository extends CrudRepository<ThyroidFunction, Long> {
	List<ThyroidFunction> findAll();
    Optional<ThyroidFunction> findById(Long id);
}