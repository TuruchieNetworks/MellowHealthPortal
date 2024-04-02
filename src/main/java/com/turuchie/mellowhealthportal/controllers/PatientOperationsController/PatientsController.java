package com.turuchie.mellowhealthportal.controllers.PatientOperationsController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.turuchie.mellowhealthportal.models.Administrations.PhysiciansPatient;
import com.turuchie.mellowhealthportal.models.PatientOperations.LoginPatient;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PhysiciansPatientService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
import com.turuchie.mellowhealthportal.utils.PatientUtils;
import com.turuchie.mellowhealthportal.utils.SearchUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth/patientsPortal")
public class PatientsController {
		@Autowired
		private PatientService patientServ;

		@Autowired
		private PhysiciansPatientService physiciansPatientServ;

		@Autowired
		private PatientUtils patientUtil;

		@Autowired
		private SearchUtil searchUtil;
	
		public PatientsController() {
		}
	
		@GetMapping("/patients/{id}")
		public String showOnePatient(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null){
	    	return "redirect:/mellowHealth/patientsPortal/login";
	    } 
	    
		Patient onePatient = patientServ.getOne(id);
		Patient loggedInPatient = patientServ.getOne(patientId);
	    String patientName = loggedInPatient.getPatientFirstName();

		if (onePatient == null || loggedInPatient == null) {
	        return "redirect:/mellowHealth/patientsPortal/login";
	    }

		if (patientName == null) {
		}

		// Add formatted dates to the model
		patientUtil.addSearchedMethods(model, patientName);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
		patientUtil.setPatientDateAttributes(model, loggedInPatient, onePatient);
        model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(patientName));
        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(patientName));
		return "/Patients/viewOnePatient.jsp";
		}

		@GetMapping("/patients")
		public String showAllPatients(@ModelAttribute("physiciansPatient") PhysiciansPatient physiciansPatient,
		Model model, HttpSession session) {
		Long patientId = (Long) session.getAttribute("patient_id");
		    if (patientId == null) {
		        return "redirect:/mellowHealth/patientsPortal/login";
		    }

		    Patient loggedInPatient = patientServ.getOne(patientId);
		    patientUtil.setPatientAttributes(model);
		    patientUtil.sortLoggedPatientAttributes(model, patientId);
		    patientUtil.setLoggedPatientCommonAttributes(model, loggedInPatient, null);
		    model.addAttribute("physiciansPatient", new PhysiciansPatient());
		    return "Patients/viewAllPatients.jsp";
		}

		@PostMapping("/hospitalDashboard/process/physiciansPatients/newPhysiciansPatient")
		public String followPhysician(@ModelAttribute("physiciansPatient")
			PhysiciansPatient newPhysiciansPatient, Model model, HttpSession session) {
			// Get patient ID from the session
		    Long patientId = (Long) session.getAttribute("patient_id");

		    if (patientId == null) {
		        return "redirect:/mellowHealth/patientsPortal/login";
		    }

		    // Get physician ID from the newPhysiciansPatient
		    Long physicianId = newPhysiciansPatient.getPhysician().getId();

		    // Check if the relationship already exists
		    boolean isRelationshipExists = physiciansPatientServ.isRelationshipExists(patientId, physicianId);

		    if (isRelationshipExists) {
		        // Relationship already exists, handle accordingly (e.g., show a message)
		        // You may redirect to a different page or display an error message
		        return "redirect:/mellowHealth/patientsPortal/patients?alreadyFollowing=true";
		    }

		    // If the relationship doesn't exist, create it
		    physiciansPatientServ.create(newPhysiciansPatient);
		    return "redirect:/mellowHealth/patientsPortal/patients?alreadyFollowing=true";
		}

		@GetMapping("/login")
		public String defaultLoginRegistration(@ModelAttribute("patient") Patient patient,@ModelAttribute("loginPatient") LoginPatient loginPatient, Model model) {
			patientUtil.setPatientAttributes(model);
			return "/Patients/LoginPatient.jsp";
		}	
		
		@GetMapping("/register")
		public String regPatient(@ModelAttribute("patient") Patient patient, Model model) {
			patientUtil.setPatientAttributes(model);
			
			return "/Patients/registerPatient.jsp";
		}
		
		@PostMapping("/process/register")
		public String processRegister(@RequestParam(value = "dateOfBirth", required = false) LocalDate dateOfBirth,
			@Valid @ModelAttribute("patient") Patient patient,
			BindingResult result, Model model, HttpSession session) {

			// reject if email is taken
		    if (patientServ.getOne(patient.getEmail()) != null) {
		        result.rejectValue("email", "Unique", "Patient Already Exists!");
		    }

		    // Check if email is already registered
		    Patient existingPatient = patientServ.getOne(patient.getEmail());
		    if (existingPatient != null) {
		        result.rejectValue("email", "Unique", "Patient with this email already exists!");
		    }

		    // Validate birth date
		    if (dateOfBirth != null) {
			    // Validate birth date
			    if (!patientUtil.isValidBirthDateRegularPatient(dateOfBirth)) {
			        result.rejectValue("dateOfBirth", "InvalidDate", "Invalid birth date");
			    }

		    }

		    // reject if passwords don't match
		    if (!patient.getPassword().equals(patient.getConfirmPassword())) {
		        result.rejectValue("password", "Match", "Passwords Must Match!");
		    }

		    // redirect if errors
		    if (result.hasErrors() || result.hasFieldErrors("dateOfBirth")) {
		        patientUtil.setPatientAttributes(model);
		        model.addAttribute("loginPatient", new LoginPatient());
		        return "Patients/registerPatient.jsp";
		    }

		    Patient newPatient = patientServ.registerPatient(patient);
		    session.setAttribute("patient_id", newPatient.getId());

		    return "redirect:/mellowHealth/patientsPortal/patients";
		}

		@PostMapping("/process/login")
		public String processLoginPatient(@Valid @ModelAttribute("loginPatient") LoginPatient loginPatient,
			BindingResult result, Model model, HttpSession session) {
			Patient loggingPatient = patientServ.login(loginPatient, result);
			if (loggingPatient == null || result.hasErrors()) {
			    model.addAttribute("patient", new Patient());
			    return "Patients/LoginPatient.jsp";
			}
	
			session.setAttribute("patient_id", loggingPatient.getId());
			return "redirect:/mellowHealth/patientsPortal/patients";
		}
		
		@GetMapping("/patients/edit/{id}")
		public String editPatient(@PathVariable("id") Long id, Model model, HttpSession session) {
		    Long patientId = (Long) session.getAttribute("patient_id");
		    if (patientId == null) {
		            return "redirect:/mellowHealth/patientsPortal/login";
		    } 

		    // Today's date 
	        patientUtil.setPatientAttributes(model);
			model.addAttribute("patient", patientServ.getOne(id));
			model.addAttribute("confirmPatientPassword", patientServ.getOne(id).getPassword());
			return "Patients/editPatient.jsp";
		}
		
		@PatchMapping("/process/patients/editPatient/{id}")
		public String processEditPatient(@PathVariable Long id, 
			@RequestParam("password") String password,
			@RequestParam("confirmPassword") String confirmPassword,
			@Valid @ModelAttribute("patient") Patient existingPatient,
		    BindingResult result, Model model, HttpSession session) {
		    Long patientId = (Long) session.getAttribute("patient_id");
		    if (result.hasErrors() || patientId == null) {
		        // Add necessary model attributes for rendering the form with validation errors
		        if (patientId != null) {
		            List<Integer> timeFormat = new ArrayList<>();
		            for (int i = 1; i <= 12; i++) {
		                timeFormat.add(i);
		            }

		            // Model attributes
		            model.addAttribute("timeFormat", timeFormat);
		            model.addAttribute("loggedInPatient", patientServ.getOne(patientId));
					model.addAttribute("confirmPassword", existingPatient.getConfirmPassword());
					model.addAttribute("patient", patientServ.getOne(id));
				    model.addAttribute("currentDateTime", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		            return "Patients/editPatient.jsp";
		        } else {
		            // Handle the case where patientId is null (redirect to login, show an error, etc.)
		            return "redirect:/mellowHealth/patientsPortal/login";
		        }
		    } else {
		        Patient patientToEdit = patientServ.getOne(existingPatient.getEmail());
		        // Reject if email is taken
		        if (patientServ.existsByEmailAndIdNot(patientToEdit.getEmail(), patientId)) {
		            model.addAttribute("currentDateTime", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		            result.rejectValue("email", "Unique", "Email is already taken by another patient!");
		        }  

		        // Check if confirm password matches password
		        if (existingPatient.getPassword().isEmpty() || existingPatient.getConfirmPassword().isEmpty()) {
		            result.rejectValue("password", "Size", "Please Enter Password!");
		            result.rejectValue("confirmPassword", "Size", "Please Enter Password!");
		        }

	            // Remaining Field Values To update
		        patientUtil.setCommonPatientAttributes(existingPatient, patientToEdit);

		        // Check if confirm password matches password
		        if (!existingPatient.getPassword().equals(existingPatient.getConfirmPassword())) {
		            result.rejectValue("confirmPassword", "Match", "Passwords Must Match!");
		        } else {
		        	patientServ.setHashedPasswords(patientToEdit, password, confirmPassword);
		        }

		        // Redirect if errors
		        if (result.hasErrors()) {
		            model.addAttribute("patient", existingPatient);
		            model.addAttribute("currentDateTime", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		            return "Patients/editPatient.jsp";
		        }

		        // Save the updated patient in the database
				patientServ.update(patientToEdit);
		        return "redirect:/mellowHealth/patientsPortal/patients/" + patientToEdit.getId();
		    }
		}

		@GetMapping("/logout")
		public String logout(HttpSession session) {
			session.invalidate();
			return "redirect:/mellowHealth/patientsPortal/login";
		}
}