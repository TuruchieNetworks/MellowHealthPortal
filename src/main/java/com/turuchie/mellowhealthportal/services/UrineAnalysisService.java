package com.turuchie.mellowhealthportal.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.repositories.UrineAnalysisRepository;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.UrineAnalysis;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;

@Service
public class UrineAnalysisService {
	@Autowired
	private UrineAnalysisRepository urineAnalysisRepo;

	public UrineAnalysisService(UrineAnalysisRepository urineAnalysisRepo, PhysicianRepository physicianRepo) {
		this.urineAnalysisRepo = urineAnalysisRepo;
	}
	
	public UrineAnalysis getOne(Long id) {
		Optional<UrineAnalysis> urineAnalysis = urineAnalysisRepo.findById(id);
		return urineAnalysis.isPresent() ? urineAnalysis.get() : null;
	}

	public List<UrineAnalysis> getAll() {
		return (List<UrineAnalysis>) urineAnalysisRepo.findAll();
	}

	public UrineAnalysis create(UrineAnalysis urineAnalysis) {
		return urineAnalysisRepo.save(urineAnalysis);
	}

	public UrineAnalysis update(UrineAnalysis urineAnalysis) {
		return urineAnalysisRepo.save(urineAnalysis);
	}

	public void delete(Long id) {
		urineAnalysisRepo.deleteById(id);
	}

	//	*************************create patient with urineAnalysis logic****************************
}