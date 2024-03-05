package com.turuchie.mellowhealthportal.services.DiagnosticProceduresServices;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PainAssessment;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;
import com.turuchie.mellowhealthportal.repositories.DiagnosticProceduresRepositories.PainAssessmentRepository;

@Service
public class PainAssessmentService {
	@Autowired
	private PainAssessmentRepository painAssessmentRepo;

	public PainAssessmentService(PainAssessmentRepository painAssessmentRepo, PhysicianRepository physicianRepo) {
		this.painAssessmentRepo = painAssessmentRepo;
	}
	
	public PainAssessment getOne(Long id) {
		Optional<PainAssessment> painAssessment = painAssessmentRepo.findById(id);
		return painAssessment.isPresent() ? painAssessment.get() : null;
	}

	public List<PainAssessment> getAll() {
		return (List<PainAssessment>) painAssessmentRepo.findAll();
	}

	public PainAssessment create(PainAssessment painAssessment) {
		return painAssessmentRepo.save(painAssessment);
	}

	public PainAssessment update(PainAssessment painAssessment) {
		return painAssessmentRepo.save(painAssessment);
	}

	public void delete(Long id) {
		painAssessmentRepo.deleteById(id);
	}

	//	*************************create patient with painAssessment date logic****************************
    public PainAssessment getMostRecentPainAssessment() {
        Optional<PainAssessment> mostRecentAssessment = painAssessmentRepo.findFirstByOrderByCreatedAtDesc();
        
        return mostRecentAssessment.orElse(null);
    }
}