package com.turuchie.mellowhealthportal.services.DiagnosticProceduresServices;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PhysicalAssessment;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;
import com.turuchie.mellowhealthportal.repositories.DiagnosticProceduresRepositories.PhysicalAssessmentRepository;

@Service
public class PhysicalAssessmentService {
	@Autowired
	private PhysicalAssessmentRepository physicalAssessmentRepo;

	public PhysicalAssessmentService(PhysicalAssessmentRepository physicalAssessmentRepo, PhysicianRepository physicianRepo) {
		this.physicalAssessmentRepo = physicalAssessmentRepo;
	}
	
	public PhysicalAssessment getOne(Long id) {
		Optional<PhysicalAssessment> physicalAssessment = physicalAssessmentRepo.findById(id);
		return physicalAssessment.isPresent() ? physicalAssessment.get() : null;
	}

	public List<PhysicalAssessment> getAll() {
		return (List<PhysicalAssessment>) physicalAssessmentRepo.findAll();
	}

	public PhysicalAssessment create(PhysicalAssessment physicalAssessment) {
		return physicalAssessmentRepo.save(physicalAssessment);
	}

	public PhysicalAssessment update(PhysicalAssessment physicalAssessment) {
		return physicalAssessmentRepo.save(physicalAssessment);
	}

	public void delete(Long id) {
		physicalAssessmentRepo.deleteById(id);
	}

	//	*************************create patient with physicalAssessment date logic****************************
    public PhysicalAssessment getMostRecentPhysicalAssessment() {
        Optional<PhysicalAssessment> mostRecentAssessment = physicalAssessmentRepo.findFirstByOrderByCreatedAtDesc();
        
        return mostRecentAssessment.orElse(null); // You can handle the case when there's no record if needed
    }
}