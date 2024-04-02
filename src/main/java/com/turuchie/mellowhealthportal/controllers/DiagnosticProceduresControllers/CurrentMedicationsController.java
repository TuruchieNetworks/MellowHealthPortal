package com.turuchie.mellowhealthportal.controllers.DiagnosticProceduresControllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

import com.turuchie.mellowhealthportal.models.ClinicalOperations.CurrentMedication;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientCaseService;
import com.turuchie.mellowhealthportal.services.DiagnosticProceduresServices.CurrentMedicationService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
import com.turuchie.mellowhealthportal.utils.DiagnosticUtils;
import com.turuchie.mellowhealthportal.utils.ListConverterUtil;
import com.turuchie.mellowhealthportal.utils.PatientFilterUtil;
import com.turuchie.mellowhealthportal.utils.PatientUtils;
import com.turuchie.mellowhealthportal.utils.SearchUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth")
public class CurrentMedicationsController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/hospitalDashboard";
	private static final String PATIENT_PATH = "/mellowHealth/patientsPortal/patients";
	@Autowired
	private CurrentMedicationService currentMedicationServ;

	@Autowired
	private PatientService patientServ;

	@Autowired
	private PatientCaseService patientCaseServ;

	
	@Autowired
	private PatientUtils patientUtil;
	
	@Autowired
	private PatientFilterUtil filterUtil;

	@Autowired
	private SearchUtil searchUtil;

	@Autowired
	private DiagnosticUtils diagnosticUtil;

	@Autowired
	public CurrentMedicationsController(CurrentMedicationService currentMedicationServ,
		PatientService patientServ,PatientCaseService patientCaseServ,PatientUtils patientUtil,
		PhysicianService physicianServ, SearchUtil searchUtil,DiagnosticUtils diagnosticUtil) {
		        this.patientUtil = patientUtil;
		        this.searchUtil = searchUtil;
		        this.diagnosticUtil = diagnosticUtil;
		        this.patientServ = patientServ;
		        this.patientCaseServ = patientCaseServ;
		        this.currentMedicationServ = currentMedicationServ;
    }
	
	public CurrentMedicationsController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	private List<Integer> generateDoseFrequencyValues() {
	    List<Integer> doseValues = new ArrayList<>();
	    for (int i = 1; i <= 100; i++) {
	        doseValues.add(i);
	    }
	    return doseValues;
	}

	private List<Integer> generateHourlyFrequencyValues() {
	    List<Integer> doseValues = new ArrayList<>();
	    for (int i = 1; i <= 5; i++) {
	        doseValues.add(i);
	    }
	    return doseValues;
	}


	@GetMapping("/currentMedications")
	public String currentMedicationIndexPage(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	    Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Null Pointers
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);

	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
	        diagnosticUtil.searchCurrentMedicationByCharacter(model, trimmedSearchTerm);
	        diagnosticUtil.searchSingleCurrentMedicationByCharacter(model, trimmedSearchTerm);
	        model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display physical oneCurrentMedicationHistory. one of the JSP is looping over filtered patient case cases
	        model.addAttribute("allCurrentMedicationsWithFilter", Collections.emptyList());
	        model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
	    }

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    return "CurrentMedications/viewAllCurrentMedicationRecords.jsp";
	}

	@GetMapping("/currentMedications/{id}")
	public String showOneCurrentMedication(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		@PathVariable("id") Long id, Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    if (physicianId == null) {
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    CurrentMedication oneCurrentMedication = currentMedicationServ.getOne(id);
	    String patientName = oneCurrentMedication.getPatient().getPatientFirstName();

	    if (loggedInPatient == null || oneCurrentMedication == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }	

		// Add formatted dates to the model
		patientUtil.setPatientAttributes(model);;
	    patientUtil.sortLoggedPatientAttributes(model, patientId);

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	        diagnosticUtil.searchCurrentMedicationByCharacter(model, trimmedSearchTerm);
	        diagnosticUtil.searchSingleCurrentMedicationByCharacter(model, trimmedSearchTerm);
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display physical oneCurrentMedicationHistory. one of the JSP is looping over filtered patient case cases
	        //model.addAttribute("allCurrentMedicationsWithFilter", Collections.emptyList());
	        diagnosticUtil.searchSingleCurrentMedicationByCharacter(model, patientName);
	        diagnosticUtil.searchCurrentMedicationAndDetailsByCharacter(model, patientName);
	        model.addAttribute("allPatientCasesWithFilter",searchUtil.returnSearchPatientCaseByCharacter(patientName));
	    }

        // Date Ranges
        String formattedDayCreatedAtDate = oneCurrentMedication.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy"));
        String formattedCreatedAtDate = oneCurrentMedication.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
        int oneCurrentMedicationHistory = filterUtil.calculateDaysLocalDateDifference(oneCurrentMedication.getCreatedAt().toLocalDate(), LocalDate.now());
        
        // Date Formatting
		model.addAttribute("dayCreatedAt", formattedCreatedAtDate);
		model.addAttribute("createdAt", formattedDayCreatedAtDate);	
		model.addAttribute("oneCurrentMedication", oneCurrentMedication);
        model.addAttribute("oneCurrentMedicationHistory", oneCurrentMedicationHistory);
		return "CurrentMedications/viewOneCurrentMedicationRecord.jsp";
	}

	@GetMapping("/currentMedications/newCurrentMedication")
	public String createCurrentMedication(
	    @ModelAttribute("currentMedication") CurrentMedication currentMedication,
	    @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	    @RequestParam(value = "patientCaseId", required = false) Long patientCaseId,
        Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Null Pointer check
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    // Fetch logged-in patient
	    Patient loggedInPatient = patientServ.getOne(patientId);
	    String patientName = loggedInPatient.getPatientFirstName();

	    // Null Pointer check
	    if (loggedInPatient == null || patientCaseId == null) {
	    }

	    searchUtil.searchPatientCaseByCharacter(model, patientName);
        String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
        if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
            searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
		    model.addAttribute("frequencyFormat", generateDoseFrequencyValues());
		    model.addAttribute("doseHrFrequency", generateHourlyFrequencyValues());
            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
            model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
        } else {
            model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
            searchUtil.searchPatientCaseByCharacter(model, patientName);
            //model.addAttribute("searchedPatientCase", searchUtil.returnSearchPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));
        }

	    // Add common attributes;
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    model.addAttribute("doseFormat", generateDoseFrequencyValues());
	    model.addAttribute("frequencyFormat", generateDoseFrequencyValues());
	    model.addAttribute("doseHrFrequency", generateHourlyFrequencyValues());

	    // Format the LocalDateTime objects
	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    return "/CurrentMedications/createCurrentMedicationRecord.jsp";
	}


	@PostMapping("/process/currentMedications/createNewCurrentMedication")
	public String processCreateCurrentMedication(
		@RequestParam(value = "patientCase", required = false) Long patientCaseId,
		@RequestParam(value = "startDate", required = false) LocalDate startDate,
		@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		@Valid @ModelAttribute("currentMedication") CurrentMedication newCurrentMedication,
	    BindingResult result, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (patientId == null){
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    } 

	    if (physicianId == null){
	    } 

	    if (patientCaseId == null){
	    } 

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    String patientName = loggedInPatient.getPatientFirstName();
	    LocalDate patientDateOfBirth = loggedInPatient.getDateOfBirth();

	    // Situation where loggedInPatient or searchedPatientCase is null
	    if (loggedInPatient == null || patientCaseId == null || startDate == null) {
	    }

	    // Validate birth date
	    if (!patientUtil.validatePastDates(startDate)) {
	        result.rejectValue("startDate", "InvalidDate", "Invalid start date");
	    }

        // Necessary model attributes before rendering the form with validation errors
	    model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(patientName));
        model.addAttribute("searchedPatientAge", patientUtil.calculateDateDifference(patientDateOfBirth, LocalDate.now(), ChronoUnit.YEARS));

        // Add necessary model attributes for rendering the form with validation errors
	    if (result.hasErrors()) {
			patientUtil.setPatientAttributes(model);
		    patientUtil.sortLoggedPatientAttributes(model, patientId);
		    model.addAttribute("timeFormat", generateTimeFormatList());
		    model.addAttribute("doseFormat", generateDoseFrequencyValues());
		    model.addAttribute("frequencyFormat", generateDoseFrequencyValues());
		    model.addAttribute("doseHrFrequency", generateHourlyFrequencyValues());
	        searchUtil.searchPatientCaseByCharacter(model, patientName);
            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(patientName));
            model.addAttribute("searchedPatientCase", searchUtil.returnSearchPatientCaseByCharacter(patientName));
            model.addAttribute("searchedPatientAge", patientUtil.calculateDateDifference(patientDateOfBirth, LocalDate.now(), ChronoUnit.YEARS));


		    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
	        return "/CurrentMedications/createCurrentMedicationRecord.jsp";
	    }

	    currentMedicationServ.create(newCurrentMedication);

	    // Flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");
	    return "redirect:" + PATIENT_PATH +"/"+ patientId;
	}

	@GetMapping("/currentMedications/editCurrentMedication/{id}")
	public String editCurrentMedication(@PathVariable("id") Long id,
		@ModelAttribute("inputCollector") ListConverterUtil inputCollector,
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
        model.addAttribute("allPatientCasesWithFilter", patientCaseServ.getAll());
	    CurrentMedication currentMedicationToEdit = currentMedicationServ.getOne(id);
	    model.addAttribute("timeFormat", generateTimeFormatList());
		model.addAttribute("loggedInPatient", loggedInPatient);

	    // Check if the logged-in physician is associated with the currentMedication
	    if (currentMedicationToEdit.getPatient().getId().equals(patientId)) {
			patientUtil.setPatientAttributes(model);
	        model.addAttribute("currentMedication", currentMedicationToEdit);
	        return "CurrentMedications/editOneCurrentMedicationRecord.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/currentMedications";
	    }
	}

	@PatchMapping("/process/currentMedications/editCurrentMedication/{id}")
	public String processEditCurrentMedication(@Valid @ModelAttribute("currentMedication") CurrentMedication currentMedication,
		BindingResult result,Model model, HttpSession session) {
	        // Add necessary model attributes for rendering the form with validation errors
	        Long patientId = (Long) session.getAttribute("patient_id");

		    PatientCase searchedPatientCase = patientCaseServ.getOne(currentMedication.getPatientCase().getId());

	        if (searchedPatientCase == null) {	        	
	        }
		    
	        model.addAttribute("oneSearchedPatientCase",  searchedPatientCase);
	        model.addAttribute("oneSearchedPatientAge", patientUtil.calculateDateDifference(searchedPatientCase.getPatient().getDateOfBirth(), LocalDate.now(), ChronoUnit.YEARS));
	    if (result.hasErrors()) {
	        // Model Attributes
	        if (patientId != null) {
	    		patientUtil.setPatientAttributes(model);
			    model.addAttribute("timeFormat", generateTimeFormatList());
	            model.addAttribute("loggedInPatient", patientServ.getOne(patientId));

	            // Return the view with the model attributes
	            return "CurrentMedications/editOneCurrentMedicationRecord.jsp";
	        } else {
	            // Handle the case where physicianId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PATIENT_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the currentMedication
	        currentMedicationServ.update(currentMedication);
	        return "redirect:/mellowHealth/currentMedications/{id}";
	    }
	}
	
	@DeleteMapping("/currentMedications/delete/{id}")
	public String deleteCurrentMedication(@PathVariable("id") Long id, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (patientId == null){
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    } 

	    if (physicianId == null){
	    } 

	    CurrentMedication currentMedicationToDelete = currentMedicationServ.getOne(id);
	    // Check if the logged-in physician is the owner of the currentMedication
	    if (currentMedicationToDelete != null && currentMedicationToDelete.getPatient().getId().equals(patientId)) {
	        currentMedicationServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the physician is not the owner
	        return "redirect:/mellowHealth/patientsPortal";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


}