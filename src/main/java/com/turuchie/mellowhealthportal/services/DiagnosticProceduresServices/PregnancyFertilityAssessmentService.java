package com.turuchie.mellowhealthportal.services.DiagnosticProceduresServices;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PregnancyFertilityAssessment;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;
import com.turuchie.mellowhealthportal.repositories.ClinicalOperationsRepositories.PregnancyFertilityAssessmentRepository;

@Service
public class PregnancyFertilityAssessmentService {
	@Autowired
	private PregnancyFertilityAssessmentRepository pregnancyFertilityAssessmentRepo;

	public PregnancyFertilityAssessmentService(PregnancyFertilityAssessmentRepository pregnancyFertilityAssessmentRepo, PhysicianRepository physicianRepo) {
		this.pregnancyFertilityAssessmentRepo = pregnancyFertilityAssessmentRepo;
	}
	
	public PregnancyFertilityAssessment getOne(Long id) {
		Optional<PregnancyFertilityAssessment> pregnancyFertilityAssessment = pregnancyFertilityAssessmentRepo.findById(id);
		return pregnancyFertilityAssessment.isPresent() ? pregnancyFertilityAssessment.get() : null;
	}

	public List<PregnancyFertilityAssessment> getAll() {
		return (List<PregnancyFertilityAssessment>) pregnancyFertilityAssessmentRepo.findAll();
	}

	public PregnancyFertilityAssessment create(PregnancyFertilityAssessment pregnancyFertilityAssessment) {
		return pregnancyFertilityAssessmentRepo.save(pregnancyFertilityAssessment);
	}

	public PregnancyFertilityAssessment update(PregnancyFertilityAssessment pregnancyFertilityAssessment) {
		return pregnancyFertilityAssessmentRepo.save(pregnancyFertilityAssessment);
	}

	public void delete(Long id) {
		pregnancyFertilityAssessmentRepo.deleteById(id);
	}
}