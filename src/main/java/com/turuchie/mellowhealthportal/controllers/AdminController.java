package com.turuchie.mellowhealthportal.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.Physicians.Physician;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.PhysiciansPatientService;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientCaseService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
import com.turuchie.mellowhealthportal.utils.ListConverterUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth/Admin")
public class AdminController {
	@Autowired
	private PatientCaseService patientCaseServ;
	
	@Autowired
	private PhysicianService physicianServ;	

	@Autowired
	private PhysiciansPatientService physiciansPatientServ;
	
	@Autowired
	private PatientService patientServ;

	@Autowired
	public AdminController(PhysicianService physicianServ,
			PatientService patientServ,
			PatientCaseService patientCaseServ,
			ListConverterUtil utilInputConverter,
			PhysiciansPatientService physiciansPatientServ) {
		        this.physicianServ = physicianServ;
		        this.physiciansPatientServ = physiciansPatientServ; 
		        this.patientServ = patientServ; 
		        this.patientCaseServ = patientCaseServ;
    }
	
	public AdminController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/hospitalIndex")
	public String adminIndexPage(Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
	    if (physicianId == null){
	    	return "redirect:/physicians/login";
	    } 
		Physician loggedInPhysician = physicianServ.getOne(physicianId);

	    if (loggedInPhysician == null) {
	        return "redirect:/physicians/login";
	    }
		
		model.addAttribute("loggedInPhysician", loggedInPhysician);
		model.addAttribute("allPatientCases", patientCaseServ.getAll());
		model.addAttribute("allPhysicians", physicianServ.findAll());
		return "PatientCases/viewAllPatientCase.jsp";
	}
	
	@GetMapping("/hospital/Dashboard")
	public String patientCaseIndexPage(Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
	    if (physicianId == null){
	    	return "redirect:/physicians/login";
	    } 
		Physician loggedInPhysician = physicianServ.getOne(physicianId);

	    if (loggedInPhysician == null) {
	        return "redirect:/physicians/login";
	    }
		
		model.addAttribute("loggedInPhysician", loggedInPhysician);
		model.addAttribute("allPatientCases", patientCaseServ.getAll());
		model.addAttribute("allPhysicians", physicianServ.findAll());
		return "PatientCases/viewAllPatientCase.jsp";
	}

	@GetMapping("/{id}")
	public String showOnePatientCase(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
	    if (physicianId == null) {
	    	return "redirect:/physicians/login";
	    }
		Physician loggedInPhysician = physicianServ.getOne(physicianId);

	    if (loggedInPhysician == null) {
	        return "redirect:/physicians/login";
	    }
		
		model.addAttribute("loggedInPhysician", loggedInPhysician);
		model.addAttribute("allPatientCases", patientCaseServ.getAll());
		model.addAttribute("patientCase", patientCaseServ.getOne(id));
		return "PatientCases/viewOnePatientCase.jsp";
	}
	
	@GetMapping("/newPatientCase")
	public String createPatientCase(@ModelAttribute("patientCase") PatientCase patientCase, Model model, HttpSession session) {
	    Long physicianId = (Long) session.getAttribute("physician_id");
	    if (physicianId == null) {
	        return "redirect:/physicians/login";
	    }

	    model.addAttribute("timeFormat", generateTimeFormatList());
	    // Create a list of physicians and add the logged-in physician to it
	    List<Physician> physicians = new ArrayList<>();
	    Physician loggedInPhysician = physicianServ.getOne(physicianId);
	    physicians.add(loggedInPhysician);
	    model.addAttribute("loggedInPhysician", loggedInPhysician); 
	    return "PatientCases/createPatientCase.jsp";
	}

	
	@PostMapping("/process/create")
	public String processCreatePatientCase(@Valid @ModelAttribute("patientCase") PatientCase patientCase,@ModelAttribute("inputCollector") ListConverterUtil inputCollector, BindingResult result, Model model, HttpSession session) {
	    Long physicianId = (Long) session.getAttribute("physician_id");

	    // Redirect to login if physicianId is null
	    if (physicianId == null) {
	        return "redirect:/physicians/login";
	    }

	    if (result.hasErrors()) {
	        // Add necessary model attributes for rendering the form with validation errors
	        model.addAttribute("timeFormat", generateTimeFormatList());
	        model.addAttribute("loggedInPhysician", physicianServ.getOne(physicianId));

	        // Return the view with the model attributes
	        return "PatientCases/createPatientCase.jsp";
	    }

	    // Continue processing if there are no validation errors
	    patientCase.setPhysician(physicianServ.getOne(physicianId)); // Set the physician for the patientCase
	    patientCaseServ.create(patientCase);

	    // Redirect to the pmellowHealth/hysician'spage after successful patientCase creation
	    return "redirect:/mellowHealth/physicians" + physicianId;
	}


	@GetMapping("/patients/edit/{id}")
	public String editPatientCase(@PathVariable("id") Long id, Model model,@ModelAttribute("inputCollector") ListConverterUtil inputCollector, HttpSession session) {
	    Long physicianId = (Long) session.getAttribute("physician_id");
	    if (physicianId == null) {
	        return "redirect:/physicians/login";
	    }


	    model.addAttribute("timeFormat", generateTimeFormatList());
	    
        List<String> pastMedicalHistory = inputCollector.getPastMedicalHistory();
        List<String> chiefComplaints = inputCollector.getChiefComplaints();
	    PatientCase patientCaseToEdit = patientCaseServ.getOne(id);

	    // Check if the logged-in physician is associated with the patientCase
	    if (patientCaseToEdit.getPhysician().getId().equals(physicianId)) {
	        model.addAttribute("patientCase", patientCaseToEdit);
	        return "PatientCases/editPatientCase.jsp";
	    } else {
	        return "redirect:/patientCases";
	    }
	}

	@PatchMapping("/process/edit/{id}")
	public String processEditPatientCase(@Valid @ModelAttribute("patientCase") PatientCase patientCase, Model model, HttpSession session, BindingResult result) {
	    if (result.hasErrors()) {
	        // Add necessary model attributes for rendering the form with validation errors
	        Long physicianId = (Long) session.getAttribute("physician_id");
	        if (physicianId != null) {
	            List<Integer> timeFormat = new ArrayList<>();
	            for (int i = 1; i <= 12; i++) {
	                timeFormat.add(i);
	            }

	            // Add other necessary attributes to the model
	            model.addAttribute("timeFormat", timeFormat);
	            model.addAttribute("loggedInPhysician", physicianServ.getOne(physicianId));

	            // Return the view with the model attributes
	            return "PatientCases/editPatientCase.jsp";
	        } else {
	            // Handle the case where physicianId is null (redirect to login, show an error, etc.)
	            return "redirect:/physicians/login";
	        }
	    } else {
	        // Validation passed, update the patientCase
	        patientCaseServ.update(patientCase);
	        return "redirect:/patientCases/{id}";
	    }
	}
	
	@DeleteMapping("/patients/delete/{id}")
	public String deletePatientCase(@PathVariable("id") Long id, HttpSession session) {
	    Long physicianId = (Long) session.getAttribute("physician_id");

	    // Redirect to login if physicianId is null
	    if (physicianId == null) {
	        return "redirect:/physicians/login";
	    }

	    PatientCase patientCaseToDelete = patientCaseServ.getOne(id);

	    // Check if the logged-in physician is the owner of the patientCase
	    if (patientCaseToDelete != null && patientCaseToDelete.getPhysician().getId().equals(physicianId)) {
	        patientCaseServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the physician is not the owner
	        return "redirect:/mellowHealth/physicians";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:/mellowHealth/physicians/" + physicianId;
	}


}