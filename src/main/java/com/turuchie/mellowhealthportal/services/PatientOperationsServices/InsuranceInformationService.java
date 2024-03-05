package com.turuchie.mellowhealthportal.services.PatientOperationsServices;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.PatientOperations.InsuranceInformation;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;
import com.turuchie.mellowhealthportal.repositories.PatientOperationsRepositories.InsuranceInformationRepository;

@Service
public class InsuranceInformationService {
	@Autowired
	private InsuranceInformationRepository insuranceInformationRepo;

	public InsuranceInformationService(InsuranceInformationRepository insuranceInformationRepo, PhysicianRepository physicianRepo) {
		this.insuranceInformationRepo = insuranceInformationRepo;
	}
	
	public InsuranceInformation getOne(Long id) {
		Optional<InsuranceInformation> insuranceInformation = insuranceInformationRepo.findById(id);
		return insuranceInformation.isPresent() ? insuranceInformation.get() : null;
	}

	public List<InsuranceInformation> getAll() {
		return (List<InsuranceInformation>) insuranceInformationRepo.findAll();
	}

	public InsuranceInformation create(InsuranceInformation insuranceInformation) {
		return insuranceInformationRepo.save(insuranceInformation);
	}

	public InsuranceInformation update(InsuranceInformation insuranceInformation) {
		return insuranceInformationRepo.save(insuranceInformation);
	}

	public void delete(Long id) {
		insuranceInformationRepo.deleteById(id);
	}

	//	*************************create patient with insuranceInformation date logic****************************
    public InsuranceInformation getMostRecentInsuranceInformation() {
        Optional<InsuranceInformation> mostRecentInsurance = insuranceInformationRepo.findFirstByOrderByStartDateDesc();
        
        return mostRecentInsurance.orElse(null); // You can handle the case when there's no record if needed
    }
}