package com.turuchie.mellowhealthportal.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turuchie.mellowhealthportal.models.PatientOperations.AdverseEffect;
import com.turuchie.mellowhealthportal.repositories.AdverseEffectRepository;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;


@Service
public class AdverseEffectService {
	@Autowired
	private AdverseEffectRepository adverseEffectRepo;

	public AdverseEffectService(AdverseEffectRepository adverseEffectRepo, PhysicianRepository physicianRepo) {
		this.adverseEffectRepo = adverseEffectRepo;
	}
	
	public AdverseEffect getOne(Long id) {
		Optional<AdverseEffect> adverseEffect = adverseEffectRepo.findById(id);
		return adverseEffect.isPresent() ? adverseEffect.get() : null;
	}

	public List<AdverseEffect> getAll() {
		return (List<AdverseEffect>) adverseEffectRepo.findAll();
	}

	public AdverseEffect create(AdverseEffect adverseEffect) {
		return adverseEffectRepo.save(adverseEffect);
	}

	public AdverseEffect update(AdverseEffect adverseEffect) {
		return adverseEffectRepo.save(adverseEffect);
	}

	public void delete(Long id) {
		adverseEffectRepo.deleteById(id);
	}

	//	*************************create physician with adverseEffect logic****************************
//	public AdverseEffect getAdverseEffectByBodyTemperature(Integer bodyTemperature) {
//	    Optional<AdverseEffect> adverseEffect = adverseEffectRepo.findByVitalRecordByBodyTemperature(bodyTemperature);
//	    return adverseEffect.orElse(null);
//	}
}