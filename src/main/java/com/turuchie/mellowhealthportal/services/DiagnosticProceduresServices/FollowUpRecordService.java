package com.turuchie.mellowhealthportal.services.DiagnosticProceduresServices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.FollowUpRecord;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;
import com.turuchie.mellowhealthportal.repositories.DiagnosticProceduresRepositories.FollowUpRecordRepository;

@Service
public class FollowUpRecordService {
	@Autowired
	private FollowUpRecordRepository followUpRecordRepo;

	public FollowUpRecordService(FollowUpRecordRepository followUpRecordRepo, PhysicianRepository physicianRepo) {
		this.followUpRecordRepo = followUpRecordRepo;
	}
	
	public FollowUpRecord getOne(Long id) {
		Optional<FollowUpRecord> followUpRecord = followUpRecordRepo.findById(id);
		return followUpRecord.isPresent() ? followUpRecord.get() : null;
	}

	public List<FollowUpRecord> getAll() {
		return (List<FollowUpRecord>) followUpRecordRepo.findAll();
	}

	public FollowUpRecord create(FollowUpRecord followUpRecord) {
		return followUpRecordRepo.save(followUpRecord);
	}

	public FollowUpRecord update(FollowUpRecord followUpRecord) {
		return followUpRecordRepo.save(followUpRecord);
	}

	public void delete(Long id) {
		followUpRecordRepo.deleteById(id);
	}

	//	*************************create patient with followUpRecord date logic****************************
    public FollowUpRecord getMostRecentFollowUpRecord() {
        Optional<FollowUpRecord> mostRecentAssessment = followUpRecordRepo.findFirstByOrderByCreatedAtDesc();
        
        return mostRecentAssessment.orElse(null); // You can handle the case when there's no record if needed
    }
}