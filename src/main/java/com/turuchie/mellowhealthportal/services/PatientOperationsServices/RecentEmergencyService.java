package com.turuchie.mellowhealthportal.services.PatientOperationsServices;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.PatientOperations.RecentEmergency;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;
import com.turuchie.mellowhealthportal.repositories.PatientOperationsRepositories.RecentEmergencyRepository;

@Service
public class RecentEmergencyService {
	@Autowired
	private RecentEmergencyRepository recentEmergencyRepo;

	public RecentEmergencyService(RecentEmergencyRepository recentEmergencyRepo, PhysicianRepository physicianRepo) {
		this.recentEmergencyRepo = recentEmergencyRepo;
	}
	
	public RecentEmergency getOne(Long id) {
		Optional<RecentEmergency> recentEmergency = recentEmergencyRepo.findById(id);
		return recentEmergency.isPresent() ? recentEmergency.get() : null;
	}

	public List<RecentEmergency> getAll() {
		return (List<RecentEmergency>) recentEmergencyRepo.findAll();
	}

	public RecentEmergency create(RecentEmergency recentEmergency) {
		return recentEmergencyRepo.save(recentEmergency);
	}

	public RecentEmergency update(RecentEmergency recentEmergency) {
		return recentEmergencyRepo.save(recentEmergency);
	}

	public void delete(Long id) {
		recentEmergencyRepo.deleteById(id);
	}

    public RecentEmergency getMostRecentRecentEmergency() {
        Optional<RecentEmergency> mostRecentEmergency = recentEmergencyRepo.findFirstByOrderByStartDateDesc();
        return mostRecentEmergency.orElse(null);
    }
}