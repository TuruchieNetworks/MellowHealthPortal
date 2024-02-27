package com.turuchie.mellowhealthportal.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.PatientOperations.InsuranceInformation;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.InsuranceInformationService;
import com.turuchie.mellowhealthportal.services.PatientCaseService;
import com.turuchie.mellowhealthportal.services.PatientService;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.utils.PatientFilterUtil;
import com.turuchie.mellowhealthportal.utils.PatientUtils;
import com.turuchie.mellowhealthportal.utils.SearchUtil;
import com.turuchie.mellowhealthportal.utils.UtilInputConverter;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth/hospitalDashboard")
public class PatientCaseController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/hospitalDashboard";
	private static final String PATIENT_PATH = "/mellowHealth/patientsPortal/patients";
	@Autowired
	private PatientCaseService patientCaseServ;

	@Autowired
	private PatientService patientServ;

	@Autowired
	private PhysicianService physicianServ;

	@Autowired
	private InsuranceInformationService insuranceInformationServ;
	
	@Autowired
	private PatientUtils patientUtil;
	
	@Autowired
	private PatientFilterUtil filterUtil;
	
	@Autowired
	private SearchUtil searchUtil;

	@Autowired
	public PatientCaseController(PatientCaseService patientCaseServ,PatientService patientServ,
		   PatientUtils patientUtil,PhysicianService physicianServ, SearchUtil searchUtil,InsuranceInformationService insuranceInformationServ) {
		        this.patientUtil = patientUtil;
		        this.searchUtil = searchUtil;
		        this.patientServ = patientServ;
		        this.physicianServ = physicianServ;
		        this.patientCaseServ = patientCaseServ;
		        this.insuranceInformationServ = insuranceInformationServ;
    }
	
	public PatientCaseController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/patientCases")
	public String patientCaseIndexPage(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		@RequestParam(value = "patientCaseId", required = false) Long patientCaseId,
	    Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Null Pointers
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }
	    // Null Pointers
	    if (patientCaseId == null) {
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);

	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

        model.addAttribute("allPatientCasesWithFilter", patientCaseServ.getAll());
	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
	        model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display patient cases
	        model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
	    }
	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);

	    model.addAttribute("loggedInPatient", loggedInPatient);
	    return "PatientCases/viewAllPatientCases.jsp";
	}

	@GetMapping("/patientCases/{id}")
	public String showOnePatientCase(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    if (physicianId == null) {
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    PatientCase onePatientCase = patientCaseServ.getOne(id);

	    if (loggedInPatient == null || onePatientCase == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }	

		// Add formatted dates to the model
		patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);

        // Date Ranges
        int onsetHistory = filterUtil.calculateDaysLocalDateDifference(onePatientCase.getOnset(), LocalDate.now());
        int visitHistory = filterUtil.calculateDaysLocaleDateTimeDiffference(onePatientCase.getCreatedAt(), LocalDateTime.now());

        // Date Formatting
        model.addAttribute("oneOnsetHistory", onsetHistory);
        model.addAttribute("oneVisitHistory", visitHistory);
		model.addAttribute("patientCase", patientCaseServ.getOne(id));
		model.addAttribute("dayCreatedAt", onePatientCase.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		model.addAttribute("createdAt", onePatientCase.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));	
		return "PatientCases/viewOnePatientCase.jsp";
	}

	@GetMapping("/patientCases/newPatientCase")
	public String createPatientCase(@ModelAttribute("patientCase") PatientCase patientCase,
	    @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	    Model model, HttpSession session) {
	    Long physicianId = (Long) session.getAttribute("physician_id");
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Null Pointers
	    if (patientId == null ) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    if (physicianId == null ) {
	    }

	    if (searchedPatientName == null ) {
	    }

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	searchUtil.searchByCharacterMethod(model, trimmedSearchTerm);
	    	searchUtil.searchPatientInsuranceByCharacter(model, trimmedSearchTerm);
	    	model.addAttribute("searchedPatient", searchedPatientName);
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    // Format the LocalDateTime objects
		patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    return "/PatientCases/createPatientCase.jsp";
	}

	@PostMapping("/process/patientCases/createNewPatientCase")
	public String processCreatePatientCase(
	        @RequestParam(value = "onset", required = false) LocalDate onset,
	        @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	        @RequestParam(value = "patient", required = false) Long patient,
	        @RequestParam(value = "insuranceInformation", required = false) Long insuranceInformation,
	        @Valid @ModelAttribute("patientCase") PatientCase newPatientCase,
	        BindingResult result, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Null Pointer check for patientId
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    // Validate Insurance Start Date
	    if (!patientUtil.isValidDateRange(onset)) {
	        result.rejectValue("onset", "error.onset", "Invalid Date Of Onset!");
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
        searchUtil.searchPatientInsuranceByCharacter(model, loggedInPatient.getPatientFirstName());

	    // Combine null checks for searchedPatientId and searchedInsuranceId
	    if (result.hasErrors()) {
	        // Add necessary model attributes for rendering the form with validation errors
	        model.addAttribute("loggedInPatient", loggedInPatient);
	        model.addAttribute("timeFormat", generateTimeFormatList());
	        return "/PatientCases/createPatientCase.jsp";
	    }

	    patientCaseServ.create(newPatientCase);

	    // Flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");

	    // Redirect to the physician's page after successful patientCase creation
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


	@GetMapping("/patientCases/edit/{id}")
	public String editPatientCase(@PathVariable("id") Long id,
		@ModelAttribute("inputCollector") UtilInputConverter inputCollector,
		Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (patientId == null){
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    } 

	    if (physicianId == null){
	    } 

	    patientUtil.setPatientAttributes(model);


	    Patient loggedInPatient = patientServ.getOne(patientId);
	    PatientCase patientCaseToEdit = patientCaseServ.getOne(id);

	    model.addAttribute("timeFormat", generateTimeFormatList());
		model.addAttribute("loggedInPatient", loggedInPatient);
	    searchUtil.searchByCharacterMethod(model, patientCaseServ.getOne(patientId).getPatient().getPatientFirstName());
	    searchUtil.searchPatientInsuranceByCharacter(model, patientCaseToEdit.getPatient().getPatientFirstName());

	    // Check if the logged-in physician is associated with the patientCase
	    if (patientCaseToEdit.getPatient().getId().equals(patientId)) {
			patientUtil.setPatientAttributes(model);
	        model.addAttribute("patientCase", patientCaseToEdit);
	        return "PatientCases/editPatientCase.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/patientCases";
	    }
	}

	@PatchMapping("/process/patientCases/edit/{id}")
	public String processEditPatientCase(@Valid @ModelAttribute("patientCase") PatientCase patientCase,
		BindingResult result, Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
		
	    if (result.hasErrors()) {
			patientUtil.setPatientAttributes(model);
	        model.addAttribute("loggedInPatient", patientServ.getOne(patientId));
	        // Add necessary model attributes for rendering the form with validation errors

	        // Model Attributes
	        if (patientId != null) {
			    model.addAttribute("timeFormat", generateTimeFormatList());
	            searchUtil.searchByCharacterMethod(model, patientServ.getOne(patientCase.getPatient().getId()).getPatientFirstName());
	            searchUtil.searchPatientInsuranceByCharacter(model, patientServ.getOne(patientCase.getPatient().getId()).getPatientFirstName());
	            // Direct Patient id way: searchUtil.searchPatientInsuranceByCharacter(model, patientServ.getOne(patientId).getPatientFirstName());

	            // Return the view with the model attributes
	            return "PatientCases/editPatientCase.jsp";
	        } else {
	            // Handle the case where physicianId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PATIENT_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the patientCase
	        patientCaseServ.update(patientCase);
	        return "redirect:/mellowHealth/hospitalDashboard/patientCases/{id}";
	    }
	}
	
	@DeleteMapping("/patientCases/delete/{id}")
	public String deletePatientCase(@PathVariable("id") Long id, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (patientId == null){
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    } 

	    if (physicianId == null){
	    } 

	    PatientCase patientCaseToDelete = patientCaseServ.getOne(id);
	    // Check if the logged-in physician is the owner of the patientCase
	    if (patientCaseToDelete != null && patientCaseToDelete.getPatient().getId().equals(patientId)) {
	        patientCaseServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the physician is not the owner
	        return "redirect:/mellowHealth/patientsPortal";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


}