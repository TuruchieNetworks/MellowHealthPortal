package com.turuchie.mellowhealthportal.controllers.ClinicalOperationsControllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.turuchie.mellowhealthportal.models.PatientOperations.IncidentReport;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientCaseService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.IncidentReportService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
import com.turuchie.mellowhealthportal.utils.PatientFilterUtil;
import com.turuchie.mellowhealthportal.utils.PatientUtils;
import com.turuchie.mellowhealthportal.utils.SearchUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth")
public class IncidentReportController {
	@Autowired
	private IncidentReportService incidentReportServ;
		
	@Autowired
	private PatientService patientServ;

	@Autowired
	private PatientUtils patientUtil;
	
	@Autowired
	private PatientFilterUtil filterUtil;

	@Autowired
	private SearchUtil searchUtil;

	@Autowired
	public IncidentReportController(IncidentReportService incidentReportServ,
		PatientService patientServ, PatientCaseService patientCaseServ, PatientUtils patientUtil,
		 SearchUtil searchUtil, PatientFilterUtil filterUtil) {
		   	this.searchUtil = searchUtil;
		   	this.filterUtil = filterUtil;
		   	this.patientUtil = patientUtil;
	        this.patientServ = patientServ;
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

	private List<Integer> generatePainScaleValues() {
	    List<Integer> painScaleValues = new ArrayList<>();
	    for (int i = 0; i <= 10; i++) {
	        painScaleValues.add(i);
	    }
	    return painScaleValues;
	}

	@GetMapping("/incidentReports")
	public String incidentReportIndexPage(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		Model model, HttpSession session) {
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null){
	    	return "redirect:/mellowHealth/patientsPortal/login";
	    } 

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    if (loggedInPatient == null) {
	        return "redirect:/mellowHealth/patientsPortal/login";
	    }

	    String patientName = loggedInPatient.getPatientFirstName();

	    // Null Checks
	    if (patientName == null) {
	    }

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
        if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	    	patientUtil.addSearchedMethods(model, trimmedSearchTerm);
	        searchUtil.setSearchUtilMethods(model, trimmedSearchTerm);
            searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
        } else {
            model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
            model.addAttribute("searchedPatientCase", Collections.emptyList());
        }

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
		model.addAttribute("loggedInPatient", loggedInPatient);
		return "IncidentReports/viewAllIncidentReports.jsp";
	}

	@GetMapping("/incidentReports/{id}")
	public String showOneIncidentReport(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		@PathVariable("id") Long id, Model model, HttpSession session) {
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	    	return "redirect:/mellowHealth/patientsPortal/login";
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    String loggedInPatientName = patientServ.getOne(patientId).getPatientFirstName();

	    if (loggedInPatient == null) {
	        return "redirect:/mellowHealth/patientsPortal/login";
	    }

	    // Null Checks
	    if (loggedInPatient == null || loggedInPatientName == null) {
	    }

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
        if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	    	patientUtil.addSearchedMethods(model, trimmedSearchTerm);
	        //searchUtil.setSearchUtilMethods(model, trimmedSearchTerm);
            searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
        } else {
        	patientUtil.addSearchedMethods(model, loggedInPatientName);
	        searchUtil.searchSinglePatientCaseByCharacter(model, loggedInPatientName);
        }

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
		model.addAttribute("oneIncidentReport", incidentReportServ.getOne(id));
		return "IncidentReports/viewOneIncidentReport.jsp";
	}

	@GetMapping("/incidentReports/newIncidentReport")
	public String createIncidentReport(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		@ModelAttribute("incidentReport") IncidentReport incidentReport,
	    Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    if (patientId == null) {
	        return "redirect:/mellowHealth/patientsPortal/login";
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    String loggedInPatientName = patientServ.getOne(patientId).getPatientFirstName();

	    // Null Checks
	    if (loggedInPatient == null || loggedInPatientName == null) {
	    }

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
        if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	    	//patientUtil.addSearchedMethods(model, loggedInPatientName);
            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(loggedInPatientName));    	
        } else {
        	//patientUtil.addSearchedMethods(model, loggedInPatientName);
            model.addAttribute("searchedPatientCase", Collections.emptyList());
        }

	    patientUtil.setPatientAttributes(model);
       	patientUtil.sortLoggedPatientAttributes(model, patientId);
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    model.addAttribute("painScale", generatePainScaleValues());
	    return "IncidentReports/createIncidentReport.jsp";
	}

	@PostMapping("/process/incidentReports/createNewIncident")
	public String processCreateIncidentReport(
        @RequestParam(value = "onset", required = false) LocalDate onset,
        @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
        @Valid @ModelAttribute("incidentReport") IncidentReport incidentReport,	    
	    BindingResult result, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Redirect to login if patientId is null
		if (patientId == null) {
			return "redirect:/mellowHealth/patientsPortal/login";
		}

	    String loggedInPatientName = patientServ.getOne(patientId).getPatientFirstName();
		// Validate PatientCase Start Date And Add necessary model attributes for rendering the form with validation errors
	    if (result.hasErrors() || !patientUtil.validatePastDates(onset)) {
		    model.addAttribute("searchedOnset", onset);
	       	patientUtil.sortLoggedPatientAttributes(model, patientId);
	    	patientUtil.addSearchedMethods(model, loggedInPatientName);  
		    model.addAttribute("timeFormat", generateTimeFormatList());
		    model.addAttribute("painScale", generatePainScaleValues());
	        result.rejectValue("onset", "error.onset", "Invalid Date Of Onset!");
            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(loggedInPatientName));
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

	@GetMapping("/incidentReports/editIncidentReport/{id}")
	public String editIncidentReport(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		@PathVariable("id") Long id,Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	        return "redirect:/mellowHealth/patientsPortal/login";
	    }

	    // Fetch the incidentReport by id
	    IncidentReport incidentReportToEdit = incidentReportServ.getOne(id); 
	    if (incidentReportToEdit == null) {
	        // Handle the case where the incidentReport with the given id is not found
	        return "redirect:/mellowHealth/incidentReports/" + id;
	    }

	    patientUtil.setPatientAttributes(model);
	    LocalDate incidentReportOnset = incidentReportToEdit.getOnset();
	    String incidenceConditionStatus = incidentReportToEdit.getConditionStatus();
	    Patient loggedInPatient = patientServ.getOne(patientId);
	    String loggedInPatientName = patientServ.getOne(patientId).getPatientFirstName();
	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;

	    // Check if the logged-in patient is associated with the incidentReport
	    if (incidentReportToEdit.getPatient().getId().equals(patientId) || !patientUtil.validatePastDates(incidentReportOnset)) {
	        if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
		        //searchUtil.setSearchUtilMethods(model, trimmedSearchTerm);
	            searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
	            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        } else {
	        	patientUtil.addSearchedMethods(model, loggedInPatientName);
	        }

	        model.addAttribute("timeFormat", generateTimeFormatList());
	        model.addAttribute("incidentReport", incidentReportToEdit);
	    	model.addAttribute("incidenceOnset", incidentReportOnset);
            model.addAttribute("painScale", generatePainScaleValues());
		    model.addAttribute("loggedInPatient", loggedInPatient);
        	patientUtil.addSearchedMethods(model, loggedInPatientName);
	    	model.addAttribute("incidenceConditionStatus", incidenceConditionStatus);
	        return "IncidentReports/editOneIncidentReport.jsp";
	    } else {
	        return "redirect:/mellowHealth/incidentReports/{id}";
	    }
	}

	@PatchMapping("/process/incidentReports/editIncidentReport/{id}")
	public String processEditIncidentReport(@Valid @ModelAttribute("incidentReport") IncidentReport incidentReport,
		BindingResult result, Model model, HttpSession session) {  
	    if (incidentReport == null) {
	        // Handle the case where the incidentReport with the given id is not found
	        return "redirect:/mellowHealth/incidentReports";
	    }
	    if (result.hasErrors()) {
	        // Add necessary model attributes for rendering the form with validation errors
	        Long patientId = (Long) session.getAttribute("patient_id");
	        Patient loggedInPatient = patientServ.getOne(patientId);
	        LocalDate incidentReportOnset = incidentReport.getOnset();	
	        String incidenceConditionStatus = incidentReport.getConditionStatus();
		    String loggedInPatientName = patientServ.getOne(patientId).getPatientFirstName();

	        if (patientId != null) {
	            // Add other necessary attributes to the model
			    patientUtil.setPatientAttributes(model);
	            model.addAttribute("loggedInPatient", loggedInPatient);
		    	model.addAttribute("incidenceOnset", incidentReportOnset);
	        	patientUtil.addSearchedMethods(model, loggedInPatientName);
	            model.addAttribute("painScale", generatePainScaleValues());
		    	model.addAttribute("incidenceConditionStatus", incidenceConditionStatus);
	            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(loggedInPatientName));

	            // Return the view with the model attributes
	            return "IncidentReports/editOneIncidentReport.jsp";
	        } else {
	            // Handle the case where patientId is null (redirect to login, show an error, etc.)
	            return "redirect:/mellowHealth/patientsPortal/login";
	        }
	    } else {
	        // Validation passed, update the incidentReport
	        incidentReportServ.update(incidentReport);
	        return "redirect:/mellowHealth/incidentReports/{id}";
	    }
	}
	
	@DeleteMapping("/incidentReports/deleteIncidentReport/{id}")
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
	        return "redirect:/mellowHealth/incidentReports";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:/mellowHealth/patientsPortal/" + patientId;
	}
}