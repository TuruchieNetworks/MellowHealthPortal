package com.turuchie.mellowhealthportal.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.Administrations.PhysiciansPatient;

@Repository
public interface PhysiciansPatientRepository extends CrudRepository<PhysiciansPatient, Long> {
	List<PhysiciansPatient> findAll();
    Optional<PhysiciansPatient> findByPatientName(String patientName);
    Optional<PhysiciansPatient> findById(Long id);
    Optional<PhysiciansPatient> findByPatientIdAndPhysicianId(Long patientId, Long physicianId);
}