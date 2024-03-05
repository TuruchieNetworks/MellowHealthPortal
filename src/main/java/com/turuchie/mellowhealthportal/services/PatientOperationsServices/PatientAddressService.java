package com.turuchie.mellowhealthportal.services.PatientOperationsServices;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.PatientOperations.PatientAddress;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;
import com.turuchie.mellowhealthportal.repositories.PatientOperationsRepositories.PatientAddressRepository;

@Service
public class PatientAddressService {
	@Autowired
	private PatientAddressRepository patientAddressRepo;

	public PatientAddressService(PatientAddressRepository patientAddressRepo, PhysicianRepository physicianRepo) {
		this.patientAddressRepo = patientAddressRepo;
	}
	
	public PatientAddress getOne(Long id) {
		Optional<PatientAddress> patientAddress = patientAddressRepo.findById(id);
		return patientAddress.isPresent() ? patientAddress.get() : null;
	}

	public List<PatientAddress> getAll() {
		return (List<PatientAddress>) patientAddressRepo.findAll();
	}

	public PatientAddress create(PatientAddress patientAddress) {
		return patientAddressRepo.save(patientAddress);
	}

	public PatientAddress update(PatientAddress patientAddress) {
		return patientAddressRepo.save(patientAddress);
	}

	public void delete(Long id) {
		patientAddressRepo.deleteById(id);
	}

	//	*************************create patient with patient's address date logic****************************
    public PatientAddress getMostRecentPatientAddress() {
        Optional<PatientAddress> mostRecentInsurance = patientAddressRepo.findFirstByOrderByCreatedAtDesc();
        return mostRecentInsurance.orElse(null);
    }
}