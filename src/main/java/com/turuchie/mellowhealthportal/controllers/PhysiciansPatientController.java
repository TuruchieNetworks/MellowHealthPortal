package com.turuchie.mellowhealthportal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.turuchie.mellowhealthportal.models.Administrations.PhysiciansPatient;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.models.Physicians.Physician;
import com.turuchie.mellowhealthportal.services.PatientCaseService;
import com.turuchie.mellowhealthportal.services.PatientService;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.PhysiciansPatientService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mellowHealth")
public class PhysiciansPatientController {
	@Autowired
	private PhysicianService physicianServ;

	@Autowired
	private PatientCaseService patientCaseServ;

	@Autowired
	private PatientService patientServ;
	
	@Autowired
	private PhysiciansPatientService physiciansPatientServ;

	@Autowired
	public PhysiciansPatientController(PatientCaseService patientCaseServ,
		PatientService PatientServ,
		PatientCaseService PatientCaseServ,
		PhysicianService physicianServ,
		PhysiciansPatientService physiciansPatientServ) {
        this.patientCaseServ = patientCaseServ;
        this.physicianServ = physicianServ;
        this.physiciansPatientServ = physiciansPatientServ;
	}
		
	public PhysiciansPatientController() {}

	// Helper method to set physician attributes in the model
	private Physician setPhysicianAttributes(Model model, Long physicianId) {
	    Physician loggedInPhysician = physicianServ.getOne(physicianId);
	    model.addAttribute("loggedInPhysician", loggedInPhysician);
	    return loggedInPhysician;
	}

	// Helper method to set physician attributes in the model
	private Patient setPatientAttributes(Model model, Long patientId) {
	    Patient loggedInPatient = patientServ.getOne(patientId);
	    model.addAttribute("loggedInPatient", loggedInPatient);
		    return loggedInPatient;
		}

	@GetMapping("/physiciansPatients")
	public String showAllPhysicians(@ModelAttribute("physiciansPatient") PhysiciansPatient physiciansPatient, Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

	    if (physicianId == null){
	    	return "redirect:/mellowHealth/physicians/login";
	    }

		Physician loggedInPhysician = setPhysicianAttributes(model, physicianId);
		Patient loggedInPatient = setPatientAttributes(model, patientId);

		model.addAttribute("loggedInPatient", loggedInPatient);
		model.addAttribute("loggedInPhysician", loggedInPhysician);
		model.addAttribute("allPhysicians", physicianServ.findAll());
		model.addAttribute("allPatientCases", patientCaseServ.getAll());
		return "Physicians/viewAllPhysicians.jsp";
	}
	
	@DeleteMapping("/physiciansPatients/delete/{id}")
	public String deleteOnePhysiciansPatient(@PathVariable("id") Long id, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Redirect to login if patientId is null
	    if (patientId == null) {
	        return "redirect:/mellowHealth/patientsPortal/login";
	    }

	    PhysiciansPatient physiciansPatientToDelete = (PhysiciansPatient) physiciansPatientServ.getOne(id);

	    // Check if the logged-in patient is the owner of the incidentReport
	    if (physiciansPatientToDelete != null && physiciansPatientToDelete.getPatient().getId().equals(patientId)) {
	    	physiciansPatientServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the patient is not the owner
	        return "redirect:/mellowHealth/patientsPortal/patients";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:/mellowHealth/patientsPortal/patients";
	}

}
