package com.turuchie.mellowhealthportal.services.ClinicalOperationsServices;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.CoagulationRecord;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;
import com.turuchie.mellowhealthportal.repositories.ClinicalOperationsRepositories.CoagulationRecordRepository;

@Service
public class CoagulationRecordService {
	@Autowired
	private CoagulationRecordRepository coagulationRecordRepo;

	public CoagulationRecordService(CoagulationRecordRepository coagulationRecordRepo, PhysicianRepository physicianRepo) {
		this.coagulationRecordRepo = coagulationRecordRepo;
	}
	
	public CoagulationRecord getOne(Long id) {
		Optional<CoagulationRecord> coagulationRecord = coagulationRecordRepo.findById(id);
		return coagulationRecord.isPresent() ? coagulationRecord.get() : null;
	}

	public List<CoagulationRecord> getAll() {
		return (List<CoagulationRecord>) coagulationRecordRepo.findAll();
	}

	public CoagulationRecord create(CoagulationRecord coagulationRecord) {
		return coagulationRecordRepo.save(coagulationRecord);
	}

	public CoagulationRecord update(CoagulationRecord coagulationRecord) {
		return coagulationRecordRepo.save(coagulationRecord);
	}

	public void delete(Long id) {
		coagulationRecordRepo.deleteById(id);
	}

	//	*************************create patient with coagulationRecord date logic****************************
    public CoagulationRecord getMostRecentCoagulationRecord() {
        Optional<CoagulationRecord> mostRecentMedication = coagulationRecordRepo.findFirstByOrderByCreatedAtDesc();
        // Handle when there's no record if needed
        return mostRecentMedication.orElse(null); 
    }

    public CoagulationRecord getMostRecentCurrentMedicalRecord() {
        Optional<CoagulationRecord> mostRecentRecord = coagulationRecordRepo.findFirstByOrderByCreatedAtDesc();
        return mostRecentRecord.orElse(null);
    }
}