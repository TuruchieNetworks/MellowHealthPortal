package com.turuchie.mellowhealthportal.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.turuchie.mellowhealthportal.models.Administrations.PhysiciansPatient;
import com.turuchie.mellowhealthportal.models.Physicians.LoginPhysician;
import com.turuchie.mellowhealthportal.models.Physicians.Physician;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.PhysiciansPatientService;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientCaseService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth")
public class PhysicianController {
	@Autowired
	private PhysicianService physicianServ;

	@Autowired
	private PatientCaseService patientCaseServ;
	
	@Autowired
	private PhysiciansPatientService physiciansPatientServ;

	@Autowired
	public PhysicianController(PatientCaseService patientCaseServ,
		PatientService PatientServ,
		PatientCaseService PatientCaseServ,
		PhysicianService physicianServ,
		PhysiciansPatientService physiciansPatientServ) {
        this.patientCaseServ = patientCaseServ;
        this.physicianServ = physicianServ;
        this.physiciansPatientServ = physiciansPatientServ;
	}
	
	public PhysicianController() {}

	// Helper method to validate physician and get physician id
    private Long validatePhysicianAndGetId(HttpSession session) {
	   Long physicianId = (Long) session.getAttribute("physician_id");
	   return physicianId;
	}

	// Helper method to set physician attributes in the model
	private Physician setPhysicianAttributes(Model model, Long physicianId) {
	    Physician loggedInPhysician = physicianServ.getOne(physicianId);
	    model.addAttribute("loggedInPhysician", loggedInPhysician);
	    return loggedInPhysician;
	}

	@GetMapping("/physicians/{id}")
	public String showOnePhysician(@PathVariable("id") Long id, Model model, HttpSession session) {
	Long physicianId = validatePhysicianAndGetId(session);
	    if (physicianId == null){
	    	return "redirect:/mellowHealth/physicians/login";
	    }
	    Physician loggedInPhysician = setPhysicianAttributes(model, physicianId);
		Physician onePhysician = physicianServ.getOne(id);
		if (onePhysician == null) {
	        return "redirect:/mellowHealth/physicians/login";
	    }
		// Assuming onePhysician.getCreatedAt() returns a Date object
		Date createdAtDate = onePhysician.getCreatedAt();

		// Convert Date to LocalDateTime
		Instant instant = createdAtDate.toInstant();
		LocalDateTime createdAtLocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

		// Use now() to get the current date and time
		LocalDateTime currentDateTime = LocalDateTime.now();

		// Format the LocalDateTime objects
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy");
		DateTimeFormatter dayformatter = DateTimeFormatter.ofPattern("EEE, yyyy");
		String createdAtFormatted = createdAtLocalDateTime.format(formatter);
		String currentDateTimeFormatted = currentDateTime.format(formatter);
		String dayCreatedAtFormatted = createdAtLocalDateTime.format(dayformatter);
		String dayCurrentDateTimeFormatted = currentDateTime.format(dayformatter);

		// Add formatted dates to the model
		model.addAttribute("createdAt", createdAtFormatted);
		model.addAttribute("currentDateTime", currentDateTimeFormatted);
		model.addAttribute("dayCreatedAt", dayCreatedAtFormatted);
		model.addAttribute("dayCurrentDateTime", dayCurrentDateTimeFormatted);
		model.addAttribute("loggedInPhysician", loggedInPhysician);
		model.addAttribute("onePhysician", onePhysician);
		model.addAttribute("allPatientCases", patientCaseServ.getAll());
		return "/Physicians/viewOnePhysician.jsp";
		}

	@GetMapping("/physicians")
	public String showAllPhysicians(Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");

	    if (physicianId == null){
	    	return "redirect:/mellowHealth/physicians/login";
	    }

		Physician loggedInPhysician = physicianServ.getOne(physicianId);

		model.addAttribute("loggedInPhysician", loggedInPhysician);
		model.addAttribute("allPhysicians", physicianServ.findAll());
		model.addAttribute("allPatientCases", patientCaseServ.getAll());

		// Add formatted dates to the model
		model.addAttribute("dayCreatedAt", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		model.addAttribute("dayCurrentDateTime", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	    // Add today's date to the model
	    model.addAttribute("currentDateTime", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		model.addAttribute("createdAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		// Additional attributes for seconds, minutes, hours, and days
		model.addAttribute("currentSecond", LocalTime.now().getSecond());
		model.addAttribute("currentMinute", LocalTime.now().getMinute());
		model.addAttribute("currentHour", LocalTime.now().getHour());
		model.addAttribute("currentDayOfYear", LocalDate.now().getDayOfYear());
		return "Physicians/viewAllPhysicians.jsp";
	}

	@PostMapping("/hospitalDashboard/process/physiciansPatients/newPhysiciansPatient")
	public String followPhysician(@ModelAttribute("physiciansPatient")
		PhysiciansPatient newPhysiciansPatient, Model model, HttpSession session) {
	    // Get patient ID from the session
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Get physician ID from the newPhysiciansPatient
	    Long physicianId = newPhysiciansPatient.getPhysician().getId();

	    // Check if the relationship already exists
	    boolean isRelationshipExists = physiciansPatientServ.isRelationshipExists(patientId, physicianId);

	    if (isRelationshipExists) {
	        // Relationship already exists, handle accordingly (e.g., show a message)
	        // You may redirect to a different page or display an error message
	        return "redirect:/mellowHealth/physicians?alreadyFollowing=true";
	    }

	    // If the relationship doesn't exist, create it
	    physiciansPatientServ.create(newPhysiciansPatient);
        return "redirect:/mellowHealth/physicians?alreadyFollowing=true";
	}
	@GetMapping("/physicians/login")
	public String defaultLoginRegistration(@ModelAttribute("physician") Physician physician, @ModelAttribute("loginPhysician") LoginPhysician loginPhysician) {
		return "/Physicians/PhysiciansLoginRegistration.jsp";
	}	
	
	@GetMapping("/physicians/login/register")
	public String loginRegPhysician(@ModelAttribute("physician") Physician physician, @ModelAttribute("loginPhysician") LoginPhysician loginPhysician) {
		return "/Physicians/PhysiciansLoginRegistration.jsp";
	}
		
	@PostMapping("/process/physicians/register")
	public String processRegister(@Valid @ModelAttribute("physician") Physician physician, BindingResult result, Model model, HttpSession session) {
		// reject if email is taken
		if(physicianServ.getOne(physician.getEmail()) != null) {
			result.rejectValue("email", "Unique", "Physician Already Exits!");
		}
		// Check if email is already registered
		Physician existingPhysician = physicianServ.getOne(physician.getEmail());
		if (existingPhysician != null) {
		    result.rejectValue("email", "Unique", "Physician with this email already exists!");
		}
	
		//reject if passwords dont match
		if(!physician.getPassword().equals(physician.getConfirm())) {
			result.rejectValue("password", "Match", "Passwords Must Match!");
		}
	
		//redirect if errors
		if(result.hasErrors()) {
			model.addAttribute("loginPhysician", new LoginPhysician());
			return "Physicians/PhysiciansLoginRegistration.jsp";
		}
		Physician newPhysician = physicianServ.registerPhysician(physician);
		session.setAttribute("physician_id", newPhysician.getId());

		// Add formatted dates to the model
		model.addAttribute("dayCreatedAt", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		model.addAttribute("dayCurrentDateTime", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	    // Add today's date to the model
	    model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		model.addAttribute("createdAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		// Additional attributes for seconds, minutes, hours, and days
		model.addAttribute("currentSecond", LocalTime.now().getSecond());
		model.addAttribute("currentMinute", LocalTime.now().getMinute());
		model.addAttribute("currentHour", LocalTime.now().getHour());
		model.addAttribute("currentDayOfYear", LocalDate.now().getDayOfYear());
		return "redirect:/mellowHealth/physicians";
	}

	@PostMapping("/process/physicians/login")
	public String processLoginPhysician(@Valid @ModelAttribute("loginPhysician") LoginPhysician loginPhysician, 
		BindingResult result, Model model, HttpSession session) {
		Physician loggingPhysician = physicianServ.login(loginPhysician, result);
		if (loggingPhysician == null || result.hasErrors()) {
		    model.addAttribute("physician", new Physician());
		    return "/Physicians/PhysiciansLoginRegistration.jsp";
		}
		session.setAttribute("physician_id", loggingPhysician.getId());
		return "redirect:/mellowHealth/physicians";
	}
	
	@GetMapping("/physicians/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/mellowHealth/physicians/login";
	}

}