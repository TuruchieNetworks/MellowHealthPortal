package com.turuchie.mellowhealthportal.services.ClinicalOperationsServices;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientVitalRecord;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;
import com.turuchie.mellowhealthportal.repositories.DiagnosticProceduresRepositories.PatientVitalRecordRepository;

@Service
public class PatientVitalRecordService {
	@Autowired
	private PatientVitalRecordRepository patientVitalRecordRepo;

	public PatientVitalRecordService(PatientVitalRecordRepository patientVitalRecordRepo, PhysicianRepository physicianRepo) {
		this.patientVitalRecordRepo = patientVitalRecordRepo;
	}
	
	public PatientVitalRecord getOne(Long id) {
		Optional<PatientVitalRecord> patientVitalRecord = patientVitalRecordRepo.findById(id);
		return patientVitalRecord.isPresent() ? patientVitalRecord.get() : null;
	}

	public List<PatientVitalRecord> getAll() {
		return (List<PatientVitalRecord>) patientVitalRecordRepo.findAll();
	}

	public PatientVitalRecord create(PatientVitalRecord patientVitalRecord) {
		return patientVitalRecordRepo.save(patientVitalRecord);
	}

	public PatientVitalRecord update(PatientVitalRecord patientVitalRecord) {
		return patientVitalRecordRepo.save(patientVitalRecord);
	}

	public void delete(Long id) {
		patientVitalRecordRepo.deleteById(id);
	}

	//	*************************Create Patient With Descending patientVitalRecord Logic****************************
    public PatientVitalRecord getMostRecentPatientVitalRecord() {
        Optional<PatientVitalRecord> mostPatientVitalRecord = patientVitalRecordRepo.findFirstByOrderByCreatedAtDesc();
        return mostPatientVitalRecord.orElse(null);
    }
}