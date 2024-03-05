package com.turuchie.mellowhealthportal.services.ClinicalOperationsServices;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.ThyroidFunction;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;
import com.turuchie.mellowhealthportal.repositories.ClinicalOperationsRepositories.ThyroidFunctionRepository;

@Service
public class ThyroidFunctionService {
	@Autowired
	private ThyroidFunctionRepository thyroidFunctionRepo;

	public ThyroidFunctionService(ThyroidFunctionRepository thyroidFunctionRepo, PhysicianRepository physicianRepo) {
		this.thyroidFunctionRepo = thyroidFunctionRepo;
	}
	
	public ThyroidFunction getOne(Long id) {
		Optional<ThyroidFunction> thyroidFunction = thyroidFunctionRepo.findById(id);
		return thyroidFunction.isPresent() ? thyroidFunction.get() : null;
	}

	public List<ThyroidFunction> getAll() {
		return (List<ThyroidFunction>) thyroidFunctionRepo.findAll();
	}

	public ThyroidFunction create(ThyroidFunction thyroidFunction) {
		return thyroidFunctionRepo.save(thyroidFunction);
	}

	public ThyroidFunction update(ThyroidFunction thyroidFunction) {
		return thyroidFunctionRepo.save(thyroidFunction);
	}

	public void delete(Long id) {
		thyroidFunctionRepo.deleteById(id);
	}

	//	*************************create physician with thyroidFunction logic****************************
}