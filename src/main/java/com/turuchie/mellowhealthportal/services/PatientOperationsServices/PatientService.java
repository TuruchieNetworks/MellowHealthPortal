package com.turuchie.mellowhealthportal.services.PatientOperationsServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.turuchie.mellowhealthportal.models.PatientOperations.LoginPatient;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.repositories.PatientOperationsRepositories.PatientRepository;

import jakarta.transaction.Transactional;

@Service
public class PatientService {
	@Autowired
	private PatientRepository patientRepo;

	public PatientService() {}
	
	public Iterable<Patient> findAll() {
	    return patientRepo.findAll();
	}

	public Patient registerPatient (Patient registeringPatient) {
		String hashed = BCrypt.hashpw(registeringPatient.getPassword(), BCrypt.gensalt());
		registeringPatient.setPassword(hashed);
		return patientRepo.save(registeringPatient);
	}
	
	public Patient getOne(String email) {
		Optional<Patient> patient = patientRepo.findByEmail(email);
		return patient.isPresent() ? patient.get() : null;
	}

	public Patient getOne(Long id) {
		Optional<Patient> patient = patientRepo.findById(id);
		return patient.isPresent() ? patient.get() : null;
	}

	public List<Patient> getPatientsByContainingLetters(String letters) {
	    Iterable<Patient> allPatients = patientRepo.findAll();
	    
	    List<Patient> filteredPatients = new ArrayList<>();

	    for (Patient patient : allPatients) {
	        String fullName = patient.getPatientFirstName() + " " + patient.getPatientLastName();
	        if (fullName.contains(letters)) {
	            filteredPatients.add(patient);
	        }
	    }

	    return filteredPatients;
	}

	public List<Patient> getPatientsByFilteredLetters(String letters) {
	    Iterable<Patient> allPatients = patientRepo.findAll();
	    
	    List<Patient> filteredPatients = new ArrayList<>();

	    for (Patient patient : allPatients) {
	        String fullName = patient.getPatientFirstName() + " " + patient.getPatientLastName();
	        if (fullName.toLowerCase().contains(letters.toLowerCase())) {
	            filteredPatients.add(patient);
	        }
	    }

	    return filteredPatients;
	}

	public List<Patient> getPatientsByLetters(String letters) {
	    Iterable<Patient> allPatients = patientRepo.findAll();
	    
	    List<Patient> filteredPatients = new ArrayList<>();

	    for (Patient patient : allPatients) {
	        String fullName = patient.getPatientFirstName() + " " + patient.getPatientLastName();
	        if (fullName.contains(letters)) {
	            filteredPatients.add(patient);
	        }
	    }

	    return filteredPatients;
	}

    // Add a method to retrieve patients with non-null date of birth
    public List<Patient> findAllPatientsWithDateOfBirth() {
        return patientRepo.findByDateOfBirthIsNotNull();
    }

    public List<Patient> getAllPatientsMatchingSearchTerm(String searchTerm) {
        List<Patient> patients = patientRepo.findAll();
        return patients.stream()
                .filter(patient ->
                        (patient.getPatientFirstName() != null && patient.getPatientFirstName().contains(searchTerm)) ||
                        (patient.getPatientLastName() != null && patient.getPatientLastName().contains(searchTerm))
                )
                .collect(Collectors.toList());
    }

    public Patient getOneByEmail(String email) {
        return patientRepo.findByEmail(email).orElse(null);
    }
	
	public Patient getOnePatientFirstName(String patientFirstName) {
		Optional<Patient> patient = patientRepo.findByPatientFirstName(patientFirstName);
		return patient.isPresent() ? patient.get() : null;
	}
	
	public Patient getOnePatientLastName(String patientLastName) {
		Optional<Patient> patient = patientRepo.findByPatientFirstName(patientLastName);
		return patient.isPresent() ? patient.get() : null;
	}

	public Patient getOnePatientByFullName(String fullName) {
	    String[] nameParts = StringUtils.split(fullName, ' ');

	    if (nameParts != null && nameParts.length > 1) {
	        // If the name contains a space, assume it's a full name
	        String firstName = nameParts[0].toLowerCase();
	        String lastName = nameParts[1].toLowerCase();
	        return patientRepo.findByPatientFirstNameContainingOrPatientLastNameContaining(firstName, lastName).orElse(null);
	    } else {
	        // If there's no space, treat it as a single name
	        String firstName = fullName.toLowerCase();
	        return patientRepo.findByPatientFirstName(firstName).orElse(null);
	    }
	}

	public List<Patient> searchPatientsByFullName(String trimmedSearchName) {
	    return patientRepo.findByPatientFirstNameContainingIgnoreCaseOrPatientLastNameContainingIgnoreCase(trimmedSearchName, trimmedSearchName);
	}

	public Optional<Patient> searchPatientsByCharacter(String trimmedSearchName) {
	    return patientRepo.findByPatientFirstNameContainingIgnoreCase(trimmedSearchName)
	            .or(() -> patientRepo.findByPatientLastNameContainingIgnoreCase(trimmedSearchName));
	}

    public Patient getPatientsByPartialName(String partialName) {
        return patientRepo.findByPatientFirstNameContainingIgnoreCase(partialName)
                .orElseGet(() -> patientRepo.findByPatientLastNameContainingIgnoreCase(partialName).orElse(null));
    }

    // Strict Optional Search By Name and Date Using Optional for Null Checks (Java 8 and later):
    public List<Patient> strictOptionalSearchPatientsByNameAndDOB(String patientFirstName, String patientLastName, LocalDate dateOfBirth) {
        List<Patient> patients = patientRepo.findAll();

        return patients.stream()
                .filter(patient ->
                        Optional.ofNullable(patientFirstName).map(name -> name.equalsIgnoreCase(patient.getPatientFirstName())).orElse(true) &&
                        Optional.ofNullable(patientLastName).map(name -> name.equalsIgnoreCase(patient.getPatientLastName())).orElse(true) &&
                        Optional.ofNullable(dateOfBirth).map(dob -> patient.getDateOfBirth().isEqual(dob)).orElse(true)
                )
                .collect(Collectors.toList());
    }

    // Flexible Optional Search By Name and Date
    public List<Patient> flexibleOptionalSearchPatientsByNameAndDOB(String patientFirstName, String patientLastName, LocalDate dateOfBirth) {
        List<Patient> patients = patientRepo.findAll();

        return patients.stream()
                .filter(patient ->
                        Optional.ofNullable(patientFirstName).map(name -> patient.getPatientFirstName().toLowerCase().contains(name.toLowerCase())).orElse(true) &&
                        Optional.ofNullable(patientLastName).map(name -> patient.getPatientLastName().toLowerCase().contains(name.toLowerCase())).orElse(true) &&
                        Optional.ofNullable(dateOfBirth).map(dob -> patient.getDateOfBirth().isEqual(dob)).orElse(true)
                )
                .collect(Collectors.toList());
    }

    // Flexible Search By Name and Date Using Object.isNull Method
    public List<Patient> strictSearchPatientsByNameAndDOB(String patientFirstName, String patientLastName, LocalDate dateOfBirth) {
        List<Patient> patients = patientRepo.findAll();

        return patients.stream()
                .filter(patient ->
                        Objects.isNull(patientFirstName) || Objects.equals(patient.getPatientFirstName(), patientFirstName) &&
                        Objects.isNull(patientLastName) || Objects.equals(patient.getPatientLastName(), patientLastName) &&
                        Objects.isNull(dateOfBirth) || patient.getDateOfBirth().isEqual(dateOfBirth)
                )
                .collect(Collectors.toList());
    }

    // Strict Search By Name and Date Using Object.isNull Method
    public List<Patient> flexibleSearchPatientsByNameAndDOB(String patientFirstName, String patientLastName, LocalDate dateOfBirth) {
        List<Patient> patients = patientRepo.findAll();

        return patients.stream()
                .filter(patient ->
                        (Objects.isNull(patientFirstName) || patient.getPatientFirstName().toLowerCase().contains(patientFirstName.toLowerCase())) &&
                        (Objects.isNull(patientLastName) || patient.getPatientLastName().toLowerCase().contains(patientLastName.toLowerCase())) &&
                        (Objects.isNull(dateOfBirth) || patient.getDateOfBirth().isEqual(dateOfBirth))
                )
                .collect(Collectors.toList());
    }

    // Search By Characters
    public List<Patient> searchPatientsByCharacters(String searchTerm) {
        List<Patient> patients = patientRepo.findAll();

        return patients.stream()
                .filter(patient ->
                        (patient.getPatientFirstName() != null && patient.getPatientFirstName().toLowerCase().contains(searchTerm.toLowerCase())) ||
                        (patient.getPatientLastName() != null && patient.getPatientLastName().toLowerCase().contains(searchTerm.toLowerCase()))
                )
                .collect(Collectors.toList());
    }

	public Patient login(LoginPatient loginPatient, BindingResult result) {
		if(result.hasErrors()) {
			return null;
		}
		
		Patient existingPatient = getOne(loginPatient.getEmail());
		if(existingPatient == null) {
			result.rejectValue("email", "Unique" ,"Invalid Login Credentials");
			return null;
		}
		if(!BCrypt.checkpw(loginPatient.getPassword(), existingPatient.getPassword())) {
			result.rejectValue("password", "Matches" ,"Invalid Login Credentials");
			return null;
		}
		return existingPatient;
	}


    // Helper method to set hashed passwords
    public void setHashedPasswords(Patient patient, String password, String confirmPassword) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        patient.setPassword(hashedPassword);

        String hashedConfirmPassword = BCrypt.hashpw(confirmPassword, BCrypt.gensalt());
        patient.setConfirmPassword(hashedConfirmPassword);
    }

	@Transactional
	public Patient update(Patient patientToEdit) {
		try {
			return patientRepo.save(patientToEdit);
		} catch (Exception e) {
	        e.printStackTrace();
	        // Re-throw the exception or handle it appropriately
	        throw new RuntimeException("Error updating patient: " + e.getMessage(), e);
	    }
	}

    public boolean existsByEmailAndIdNot(String email, Long patientId) {
        return patientRepo.existsByEmailAndIdNot(email, patientId);
    }

    // Update method - Handle specific exceptions
    @Transactional
    public Patient alternativeUpdate(Patient patientToEdit) {
        try {
            return patientRepo.save(patientToEdit);
        } catch (DataAccessException e) {
            e.printStackTrace();
            // Handle DataAccessException or re-throw it
            throw new RuntimeException("Error updating patient: " + e.getMessage(), e);
        }
    }
}