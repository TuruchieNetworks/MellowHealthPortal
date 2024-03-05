package com.turuchie.mellowhealthportal.services.PatientOperationsServices;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.PatientOperations.PastMedicalHistory;
import com.turuchie.mellowhealthportal.repositories.PatientOperationsRepositories.PastMedicalHistoryRepository;

@Service
public class PastMedicalHistoryService {
	@Autowired
	private PastMedicalHistoryRepository pastMedicalHistoryRepo;
		
	public PastMedicalHistoryService(PastMedicalHistoryRepository pastMedicalHistoryRepo) {
		this.pastMedicalHistoryRepo = pastMedicalHistoryRepo;
	}
	
	public PastMedicalHistory getOne(Long id) {
		Optional<PastMedicalHistory> pastMedicalHistory = pastMedicalHistoryRepo.findById(id);
		return pastMedicalHistory.isPresent() ? pastMedicalHistory.get() : null;
	}

	public PastMedicalHistory getOnePastMedicalHistoryMedicalCondition(String medicalCondition) {
		Optional<PastMedicalHistory> pastMedicalHistory = pastMedicalHistoryRepo.findByMedicalConditionContainingIgnoreCase(medicalCondition);
		return pastMedicalHistory.isPresent() ? pastMedicalHistory.get() : null;
	}

	public PastMedicalHistory getOnePastMedicalCondition(String medicalCondition) {
		Optional<PastMedicalHistory> pastMedicalHistory = pastMedicalHistoryRepo.findByMedicalCondition(medicalCondition);
		return pastMedicalHistory.isPresent() ? pastMedicalHistory.get() : null;
	}

	public List<PastMedicalHistory> getAll() {
		return (List<PastMedicalHistory>) pastMedicalHistoryRepo.findAll();
	}

	public PastMedicalHistory create(PastMedicalHistory pastMedicalHistory) {
		return pastMedicalHistoryRepo.save(pastMedicalHistory);
	}

	public PastMedicalHistory update(PastMedicalHistory pastMedicalHistory) {
		return pastMedicalHistoryRepo.save(pastMedicalHistory);
	}

	public void delete(Long id) {
		pastMedicalHistoryRepo.deleteById(id);
	}

//	*************************create patient with pastMedicalHistory logic****************************

    public PastMedicalHistory getMostRecentPastMedicalHistory() {
        Optional<PastMedicalHistory> mostPastMedicalHistory = pastMedicalHistoryRepo.findFirstByOrderByCreatedAtDesc();
        return mostPastMedicalHistory.orElse(null);
    }

}