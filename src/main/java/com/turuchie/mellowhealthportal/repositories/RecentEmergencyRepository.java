package com.turuchie.mellowhealthportal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.PatientOperations.RecentEmergency;

@Repository
public interface RecentEmergencyRepository extends CrudRepository<RecentEmergency, Long> {
	List<RecentEmergency> findAll();
    Optional<RecentEmergency> findById(Long id);
	Optional<RecentEmergency> findFirstByOrderByStartDateDesc();
}