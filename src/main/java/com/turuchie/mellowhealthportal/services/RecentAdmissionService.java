package com.turuchie.mellowhealthportal.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.PatientOperations.RecentAdmission;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;
import com.turuchie.mellowhealthportal.repositories.RecentAdmissionRepository;

@Service
public class RecentAdmissionService {
	@Autowired
	private RecentAdmissionRepository recentAdmissionRepo;

	public RecentAdmissionService(RecentAdmissionRepository recentAdmissionRepo, PhysicianRepository physicianRepo) {
		this.recentAdmissionRepo = recentAdmissionRepo;
	}
	
	public RecentAdmission getOne(Long id) {
		Optional<RecentAdmission> recentAdmission = recentAdmissionRepo.findById(id);
		return recentAdmission.isPresent() ? recentAdmission.get() : null;
	}

	public List<RecentAdmission> getAll() {
		return (List<RecentAdmission>) recentAdmissionRepo.findAll();
	}

	public RecentAdmission create(RecentAdmission recentAdmission) {
		return recentAdmissionRepo.save(recentAdmission);
	}

	public RecentAdmission update(RecentAdmission recentAdmission) {
		return recentAdmissionRepo.save(recentAdmission);
	}

	public void delete(Long id) {
		recentAdmissionRepo.deleteById(id);
	}

    public RecentAdmission getMostRecentRecentAdmission() {
        Optional<RecentAdmission> mostRecentAdmission = recentAdmissionRepo.findFirstByOrderByStartDateDesc();
        return mostRecentAdmission.orElse(null);
    }
}