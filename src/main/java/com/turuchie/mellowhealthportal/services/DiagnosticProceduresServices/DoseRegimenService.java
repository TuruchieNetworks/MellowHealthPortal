package com.turuchie.mellowhealthportal.services.DiagnosticProceduresServices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.DoseRegimen;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;
import com.turuchie.mellowhealthportal.repositories.DiagnosticProceduresRepositories.DoseRegimenRepository;

@Service
public class DoseRegimenService {
	@Autowired
	private DoseRegimenRepository doseRegimenRepo;

	public DoseRegimenService(DoseRegimenRepository doseRegimenRepo, PhysicianRepository physicianRepo) {
		this.doseRegimenRepo = doseRegimenRepo;
	}
	
	public DoseRegimen getOne(Long id) {
		Optional<DoseRegimen> doseRegimen = doseRegimenRepo.findById(id);
		return doseRegimen.isPresent() ? doseRegimen.get() : null;
	}

	public List<DoseRegimen> getAll() {
		return (List<DoseRegimen>) doseRegimenRepo.findAll();
	}

	public DoseRegimen create(DoseRegimen doseRegimen) {
		return doseRegimenRepo.save(doseRegimen);
	}

	public DoseRegimen update(DoseRegimen doseRegimen) {
		return doseRegimenRepo.save(doseRegimen);
	}

	public void delete(Long id) {
		doseRegimenRepo.deleteById(id);
	}

	//	*************************create patient with doseRegimen date logic****************************
    public DoseRegimen getMostRecentDoseRegimen() {
        Optional<DoseRegimen> mostRecentAssessment = doseRegimenRepo.findFirstByOrderByCreatedAtDesc();
        
        return mostRecentAssessment.orElse(null); // You can handle the case when there's no record if needed
    }
}