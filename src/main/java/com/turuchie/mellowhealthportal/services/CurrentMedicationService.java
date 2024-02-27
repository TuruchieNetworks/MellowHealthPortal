package com.turuchie.mellowhealthportal.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.PatientOperations.CurrentMedication;
import com.turuchie.mellowhealthportal.repositories.CurrentMedicationRepository;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;

@Service
public class CurrentMedicationService {
	@Autowired
	private CurrentMedicationRepository currentMedicationRepo;

	public CurrentMedicationService(CurrentMedicationRepository currentMedicationRepo, PhysicianRepository physicianRepo) {
		this.currentMedicationRepo = currentMedicationRepo;
	}
	
	public CurrentMedication getOne(Long id) {
		Optional<CurrentMedication> currentMedication = currentMedicationRepo.findById(id);
		return currentMedication.isPresent() ? currentMedication.get() : null;
	}

	public List<CurrentMedication> getAll() {
		return (List<CurrentMedication>) currentMedicationRepo.findAll();
	}

	public CurrentMedication create(CurrentMedication currentMedication) {
		return currentMedicationRepo.save(currentMedication);
	}

	public CurrentMedication update(CurrentMedication currentMedication) {
		return currentMedicationRepo.save(currentMedication);
	}

	public void delete(Long id) {
		currentMedicationRepo.deleteById(id);
	}

	//	*************************create patient with currentMedication date logic****************************
    public CurrentMedication getMostRecentCurrentMedication() {
        Optional<CurrentMedication> mostRecentMedication = currentMedicationRepo.findFirstByOrderByStartDateDesc();
        // Handle when there's no record if needed
        return mostRecentMedication.orElse(null); 
    }

    public CurrentMedication getMostRecentCurrentMedicalRecord() {
        Optional<CurrentMedication> mostRecentRecord = currentMedicationRepo.findFirstByOrderByStartDateDesc();
        return mostRecentRecord.orElse(null);
    }
}