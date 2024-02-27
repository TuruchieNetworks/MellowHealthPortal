package com.turuchie.mellowhealthportal.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.turuchie.mellowhealthportal.models.Physicians.LoginPhysician;
import com.turuchie.mellowhealthportal.models.Physicians.Physician;
import com.turuchie.mellowhealthportal.repositories.PhysicianRepository;

@Service
public class PhysicianService {
	@Autowired
	private PhysicianRepository physicianRepo;

	public PhysicianService() {}
	
	public Iterable<Physician> findAll() {
	    return physicianRepo.findAll();
	}

	public Physician registerPhysician (Physician registeringPhysician) {
		String hashed = BCrypt.hashpw(registeringPhysician.getPassword(), BCrypt.gensalt());
		registeringPhysician.setPassword(hashed);
		return physicianRepo.save(registeringPhysician);
	}
	
	public Physician getOne(String email) {
		Optional<Physician> physician = physicianRepo.findByEmail(email);
		return physician.isPresent() ? physician.get() : null;
	}

	public Physician getOne(Long id) {
		Optional<Physician> physician = physicianRepo.findById(id);
		return physician.isPresent() ? physician.get() : null;
	}
	
	
	public Physician login(LoginPhysician loginPhysician, BindingResult result) {
		if(result.hasErrors()) {
			return null;
		}
		
		Physician existingPhysician = getOne(loginPhysician.getEmail());
		if(existingPhysician == null) {
			result.rejectValue("email", "Unique" ,"Invalid Login Credentials");
			return null;
		}
		if(!BCrypt.checkpw(loginPhysician.getPassword(), existingPhysician.getPassword())) {
			result.rejectValue("password", "Matches" ,"Invalid Login Credentials");
			return null;
		}
		return existingPhysician;
	}
}
