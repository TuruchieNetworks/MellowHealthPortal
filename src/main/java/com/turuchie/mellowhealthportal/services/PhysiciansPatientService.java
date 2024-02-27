package com.turuchie.mellowhealthportal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.Administrations.PhysiciansPatient;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;
import com.turuchie.mellowhealthportal.repositories.PhysiciansPatientRepository;

@Service
public class PhysiciansPatientService {
	
	@Autowired
	private PhysiciansPatientRepository physiciansPatientRepo;
	
	@SuppressWarnings("unused")
	@Autowired
	private PhysicianRepository physicianRepo;
	
	public PhysiciansPatientService(PhysiciansPatientRepository physiciansPatientRepo, PhysicianRepository physicianRepo) {
		this.physiciansPatientRepo = physiciansPatientRepo;
	}
	
	public PhysiciansPatient getOne(Long id) {
		Optional<PhysiciansPatient> physiciansPatient = physiciansPatientRepo.findById(id);
		return physiciansPatient.isPresent() ? physiciansPatient.get() : null;
	}

	public PhysiciansPatient getOnePhysiciansPatientName(String patientName) {
		Optional<PhysiciansPatient> physiciansPatient = physiciansPatientRepo.findByPatientName(patientName);
		return physiciansPatient.isPresent() ? physiciansPatient.get() : null;
	}

	public List<PhysiciansPatient> getAll() {
		return (List<PhysiciansPatient>) physiciansPatientRepo.findAll();
	}

	public PhysiciansPatient create(PhysiciansPatient physiciansPatient) {
		return physiciansPatientRepo.save(physiciansPatient);
	}

	public PhysiciansPatient update(PhysiciansPatient physiciansPatient) {
		return physiciansPatientRepo.save(physiciansPatient);
	}

	public void delete(Long id) {
		physiciansPatientRepo.deleteById(id);
	}

//	*************************create physician with physiciansPatient logic****************************
	
	public PhysiciansPatient getPhysiciansPatientByName(String patientName) {
	    Optional<PhysiciansPatient> physiciansPatient = physiciansPatientRepo.findByPatientName(patientName);
	    return physiciansPatient.orElse(null);
	}

	public PhysiciansPatient createPhysiciansPatientIfNotInDatabase(String physiciansPatientName) {
	    PhysiciansPatient existingPhysiciansPatient = getPhysiciansPatientByName(physiciansPatientName);
	    if (existingPhysiciansPatient == null) {
	        PhysiciansPatient newPhysiciansPatient = new PhysiciansPatient();
	        newPhysiciansPatient.setPatientName(physiciansPatientName);
	        return physiciansPatientRepo.save(newPhysiciansPatient);
	    }
	    return existingPhysiciansPatient;
	}

//	*************************create patients with physiciansPatient logic****************************
	public boolean isRelationshipExists(Long patientId, Long physicianId) {
        //repository or data access layer to check if the relationship already exists
        Optional<PhysiciansPatient> existingRelationship = physiciansPatientRepo.findByPatientIdAndPhysicianId(patientId, physicianId);

        // Return true if the relationship already exists, false otherwise
        return existingRelationship.isPresent();
	}
}