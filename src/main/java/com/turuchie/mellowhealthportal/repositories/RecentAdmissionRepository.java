package com.turuchie.mellowhealthportal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.PatientOperations.RecentAdmission;

@Repository
public interface RecentAdmissionRepository extends CrudRepository<RecentAdmission, Long> {
	List<RecentAdmission> findAll();
    Optional<RecentAdmission> findById(Long id);
	Optional<RecentAdmission> findFirstByOrderByStartDateDesc();
}