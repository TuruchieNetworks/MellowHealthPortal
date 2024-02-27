package com.turuchie.mellowhealthportal.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.turuchie.mellowhealthportal.models.Physicians.Physician;

@Repository
public interface PhysicianRepository extends CrudRepository<Physician,Long> {
	List<Physician> findAll();
	Optional<Physician> findByEmail(String email);
	Optional<Physician> findById(Long id);
	Optional<Physician> findByEmailAndPassword(String physicianName, String password);
    Optional<Physician> findByFirstName(String firstName);
    Optional<Physician> findByLastName(String lastName);
}