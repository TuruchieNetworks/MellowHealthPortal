package com.turuchie.mellowhealthportal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.Administrations.InsuredPatients;
import com.turuchie.mellowhealthportal.repositories.InsuredPatientsRepository;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;

@Service
public class InsuredPatientsService {
	@Autowired
	private InsuredPatientsRepository insuredPatientsRepo;

	public InsuredPatientsService(InsuredPatientsRepository insuredPatientsRepo, PhysicianRepository physicianRepo) {
		this.insuredPatientsRepo = insuredPatientsRepo;
	}
	
	public InsuredPatients getOne(Long id) {
		Optional<InsuredPatients> insuredPatients = insuredPatientsRepo.findById(id);
		return insuredPatients.isPresent() ? insuredPatients.get() : null;
	}

	public List<InsuredPatients> getAll() {
		return (List<InsuredPatients>) insuredPatientsRepo.findAll();
	}

	public InsuredPatients create(InsuredPatients insuredPatients) {
		return insuredPatientsRepo.save(insuredPatients);
	}

	public InsuredPatients update(InsuredPatients insuredPatients) {
		return insuredPatientsRepo.save(insuredPatients);
	}

	public void delete(Long id) {
		insuredPatientsRepo.deleteById(id);
	}

	//	*************************create patient with insuredPatients date logic****************************
    public InsuredPatients getMostRecentInsuredPatients() {
        Optional<InsuredPatients> mostRecentInsurance = insuredPatientsRepo.findFirstByOrderByCreatedAtDesc();
        
        return mostRecentInsurance.orElse(null); // You can handle the case when there's no record if needed
    }
}