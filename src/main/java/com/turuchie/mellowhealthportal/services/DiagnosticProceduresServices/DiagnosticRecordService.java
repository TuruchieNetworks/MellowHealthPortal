package com.turuchie.mellowhealthportal.services.DiagnosticProceduresServices;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.DiagnosticRecord;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;
import com.turuchie.mellowhealthportal.repositories.DiagnosticProceduresRepositories.DiagnosticRecordRepository;

@Service
public class DiagnosticRecordService {
	@Autowired
	private DiagnosticRecordRepository diagnosticRecordRepo;

	public DiagnosticRecordService(DiagnosticRecordRepository diagnosticRecordRepo, PhysicianRepository physicianRepo) {
		this.diagnosticRecordRepo = diagnosticRecordRepo;
	}
	
	public DiagnosticRecord getOne(Long id) {
		Optional<DiagnosticRecord> diagnosticRecord = diagnosticRecordRepo.findById(id);
		return diagnosticRecord.isPresent() ? diagnosticRecord.get() : null;
	}

	public List<DiagnosticRecord> getAll() {
		return (List<DiagnosticRecord>) diagnosticRecordRepo.findAll();
	}

	public DiagnosticRecord create(DiagnosticRecord diagnosticRecord) {
		return diagnosticRecordRepo.save(diagnosticRecord);
	}

	public DiagnosticRecord update(DiagnosticRecord diagnosticRecord) {
		return diagnosticRecordRepo.save(diagnosticRecord);
	}

	public void delete(Long id) {
		diagnosticRecordRepo.deleteById(id);
	}

	//	*************************create patient with diagnosticRecord date logic****************************
    public DiagnosticRecord getMostRecentDiagnosticRecord() {
        Optional<DiagnosticRecord> mostRecentAssessment = diagnosticRecordRepo.findFirstByOrderByCreatedAtDesc();
        
        return mostRecentAssessment.orElse(null); // You can handle the case when there's no record if needed
    }
}