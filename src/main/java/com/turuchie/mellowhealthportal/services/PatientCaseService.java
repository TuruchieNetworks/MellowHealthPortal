package com.turuchie.mellowhealthportal.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.repositories.PatientCaseRepository;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;

@Service
public class PatientCaseService {
	@Autowired
	private PatientCaseRepository patientCaseRepo;
		
	public PatientCaseService(PatientCaseRepository patientCaseRepo, PhysicianRepository physicianRepo) {
		this.patientCaseRepo = patientCaseRepo;
	}
	
	public PatientCase getOne(Long id) {
		Optional<PatientCase> patientCase = patientCaseRepo.findById(id);
		return patientCase.isPresent() ? patientCase.get() : null;
	}

	public PatientCase getOnePatientCaseName(String patientName) {
		Optional<PatientCase> patientCase = patientCaseRepo.findByPatientName(patientName);
		return patientCase.isPresent() ? patientCase.get() : null;
	}

	public List<PatientCase> getAll() {
		return (List<PatientCase>) patientCaseRepo.findAll();
	}

	public PatientCase create(PatientCase patientCase) {
		return patientCaseRepo.save(patientCase);
	}

	public PatientCase update(PatientCase patientCase) {
		return patientCaseRepo.save(patientCase);
	}

	public void delete(Long id) {
		patientCaseRepo.deleteById(id);
	}

//	*************************create physician with patientCase logic****************************
	public PatientCase getPatientCaseByName(String patientName) {
	    Optional<PatientCase> patientCase = patientCaseRepo.findByPatientName(patientName);
	    return patientCase.orElse(null);
	}

	public PatientCase createPatientCaseIfNotInDatabase(String patientCaseName) {
	    PatientCase existingPatientCase = getPatientCaseByName(patientCaseName);
	    if (existingPatientCase == null) {
	        PatientCase newPatientCase = new PatientCase();
	        newPatientCase.setPatientName(patientCaseName);
	        return patientCaseRepo.save(newPatientCase);
	    }
	    return existingPatientCase;
	}

    public PatientCase getMostRecentPatientCase() {
        Optional<PatientCase> mostPatientCase = patientCaseRepo.findFirstByOrderByCreatedAtDesc();
        return mostPatientCase.orElse(null);
    }
}