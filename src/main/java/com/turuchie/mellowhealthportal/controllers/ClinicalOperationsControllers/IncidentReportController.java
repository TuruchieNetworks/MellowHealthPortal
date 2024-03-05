package com.turuchie.mellowhealthportal.controllers.ClinicalOperationsControllers;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.turuchie.mellowhealthportal.models.PatientOperations.IncidentReport;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientCaseService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.IncidentReportService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth/incidentReports")
public class IncidentReportController {
	@Autowired
	private IncidentReportService incidentReportServ;
	
	@Autowired
	private PatientCaseService patientCaseServ;
	
	@Autowired
	private PatientService patientServ;

	@Autowired
	public IncidentReportController(IncidentReportService incidentReportServ,
			PatientService patientServ, PatientCaseService patientCaseServ) {
		        this.patientServ = patientServ;
		        this.patientCaseServ = patientCaseServ;
		        this.incidentReportServ = incidentReportServ;
    }
	
	public IncidentReportController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/incidents")
	public String incidentReportIndexPage(Model model, HttpSession session) {
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null){
	    	return "redirect:/mellowHealth/patientsPortal/login";
	    } 
		Patient loggedInPatient = patientServ.getOne(patientId);

	    if (loggedInPatient == null) {
	        return "redirect:/mellowHealth/patientsPortal/login";
	    }

		model.addAttribute("loggedInPatient", loggedInPatient);
		model.addAttribute("allIncidentReports", incidentReportServ.getAll());
		model.addAttribute("allPatients", patientServ.findAll());
		model.addAttribute("allPatientCases", patientCaseServ.getAll());
		return "IncidentReports/viewAllIncidentReport.jsp";
	}

	@GetMapping("/incidents/{id}")
	public String showOneIncidentReport(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	    	return "redirect:/mellowHealth/patientsPortal/login";
	    }
		Patient loggedInPatient = patientServ.getOne(patientId);

	    if (loggedInPatient == null) {
	        return "redirect:/mellowHealth/patientsPortal/login";
	    }
		
		model.addAttribute("loggedInPatient", loggedInPatient);
		model.addAttribute("allIncidentReports", incidentReportServ.getAll());
		model.addAttribute("incidentReport", incidentReportServ.getOne(id));
		return "IncidentReports/viewOneIncidentReport.jsp";
	}

	@GetMapping("/newIncident")
	public String createIncidentReport(@ModelAttribute("incidentReport") IncidentReportService incidentReport,
	    Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	        return "redirect:/mellowHealth/patientsPortal/login";
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    model.addAttribute("allPatients", patientServ.findAll());
	    model.addAttribute("allPatients", patientServ.findAll());
	    return "IncidentReports/createIncidentReport.jsp";
	}

	@PostMapping("/process/incidentReports/createNewIncident")
	public String processCreateIncidentReport(@Valid @ModelAttribute("incidentReport") IncidentReport incidentReport,	    
	    BindingResult result, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
	    Long patientId = (Long) session.getAttribute("patient_id");
		// Redirect to login if patientId is null
		if (patientId == null) {
			return "redirect:/mellowHealth/patientsPortal/login";
		}

		// Add necessary model attributes for rendering the form with validation errors
	    if (result.hasErrors()) {
        Patient loggedInPatient = patientServ.getOne(patientId);
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    model.addAttribute("allPatients", patientServ.findAll());
	    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
        return "/IncidentReports/createIncidentReport.jsp";
	    }
	    // Continue processing if there are no validation errors
		incidentReport.setPatient(patientServ.getOne(patientId));
	    incidentReportServ.create(incidentReport);
	    // Add flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Incident Report Was Successfully Created!");
	    
	    // Redirect to the patient's page after successful incidentReport creation
	    return "redirect:/mellowHealth/patientsPortal/patients/" + patientId;
	}

	@GetMapping("/patients/edit/{id}")
	public String editIncidentReport(@PathVariable("id") Long id,
	    Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	        return "redirect:/mellowHealth/patientsPortal/login";
	    }

	    // Fetch the incidentReport by id
	    IncidentReport incidentReportToEdit = incidentReportServ.getOne(id); 
	    if (incidentReportToEdit == null) {
	        // Handle the case where the incidentReport with the given id is not found
	        return "redirect:/mellowHealth/incidentReports/incidents";
	    }
	    // Check if the logged-in patient is associated with the incidentReport
	    if (incidentReportToEdit.getPatient().getId().equals(patientId)) {
	        model.addAttribute("timeFormat", generateTimeFormatList());
	        model.addAttribute("incidentReport", incidentReportToEdit);
	        return "IncidentReports/editIncidentReport.jsp";
	    } else {
	        return "redirect:/mellowHealth/incidentReports/incidents";
	    }
	}

	@PatchMapping("/process/edit/{id}")
	public String processEditIncidentReport(@Valid @ModelAttribute("incidentReport") IncidentReport incidentReport,
		Model model, HttpSession session, BindingResult result) {
	    if (result.hasErrors()) {
	        // Add necessary model attributes for rendering the form with validation errors
	        Long patientId = (Long) session.getAttribute("patient_id");
	        if (patientId != null) {
	            List<Integer> timeFormat = new ArrayList<>();
	            for (int i = 1; i <= 12; i++) {
	                timeFormat.add(i);
	            }

	            // Add other necessary attributes to the model
	            model.addAttribute("timeFormat", timeFormat);
	            model.addAttribute("loggedInPatient", patientServ.getOne(patientId));

	            // Return the view with the model attributes
	            return "IncidentReports/editIncidentReport.jsp";
	        } else {
	            // Handle the case where patientId is null (redirect to login, show an error, etc.)
	            return "redirect:/mellowHealth/patientsPortal/login";
	        }
	    } else {
	        // Validation passed, update the incidentReport
	        incidentReportServ.update(incidentReport);
	        return "redirect:/mellowHealth/incidentReports/incidents/{id}";
	    }
	}
	
	@DeleteMapping("/patients/delete/{id}")
	public String deleteIncidentReport(@PathVariable("id") Long id, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Redirect to login if patientId is null
	    if (patientId == null) {
	        return "redirect:/mellowHealth/patientsPortal/login";
	    }

	    IncidentReport incidentReportToDelete = (IncidentReport) incidentReportServ.getOne(id);

	    // Check if the logged-in patient is the owner of the incidentReport
	    if (incidentReportToDelete != null && incidentReportToDelete.getPatient().getId().equals(patientId)) {
	        incidentReportServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the patient is not the owner
	        return "redirect:/mellowHealth/incidencePortal/incidents";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:/mellowHealth/incidencePortal/incidents/" + patientId;
	}
}