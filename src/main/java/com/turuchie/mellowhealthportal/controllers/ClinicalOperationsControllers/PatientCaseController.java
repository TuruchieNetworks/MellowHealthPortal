package com.turuchie.mellowhealthportal.controllers.ClinicalOperationsControllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientCaseService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.InsuranceInformationService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
import com.turuchie.mellowhealthportal.utils.DiagnosticUtils;
import com.turuchie.mellowhealthportal.utils.ListConverterUtil;
import com.turuchie.mellowhealthportal.utils.PatientFilterUtil;
import com.turuchie.mellowhealthportal.utils.PatientUtils;
import com.turuchie.mellowhealthportal.utils.SearchUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth/hospitalDashboard")
public class PatientCaseController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/hospitalDashboard";
	private static final String PATIENT_PATH = "/mellowHealth/patientsPortal/patients";

	@Autowired
	private PatientService patientServ;

	@Autowired
	private PatientCaseService patientCaseServ;
	
	@Autowired
	private SearchUtil searchUtil;
	
	@Autowired
	private PatientUtils patientUtil;
	
	@Autowired
	private PatientFilterUtil filterUtil;

	@Autowired
	private DiagnosticUtils diagnosticUtil;

	@Autowired
	public PatientCaseController(PatientCaseService patientCaseServ,PatientService patientServ,
	   PatientUtils patientUtil,PhysicianService physicianServ,DiagnosticUtils diagnosticUtil,
	   SearchUtil searchUtil,InsuranceInformationService insuranceInformationServ) {
	        this.searchUtil = searchUtil;
	        this.patientUtil = patientUtil;
	        this.diagnosticUtil = diagnosticUtil;
	        this.patientServ = patientServ;
	        this.patientCaseServ = patientCaseServ;
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
	    String patientName = patientServ.getOne(patientId).getPatientFirstName();

	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	        searchUtil.setSearchUtilMethods(model, trimmedSearchTerm);
	        diagnosticUtil.setAllSearchTrimmedMethods(model, trimmedSearchTerm);
	        model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display patient cases
	        searchUtil.setSearchUtilMethods(model, patientName);
	        diagnosticUtil.setAllSearchTrimmedMethods(model, patientName);
	        //model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
	    }

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);

	    model.addAttribute("loggedInPatient", loggedInPatient);
	    return "PatientCases/viewAllPatientCases.jsp";
	}

	@GetMapping("/patientCases/{id}")
	public String showOnePatientCase(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		@PathVariable("id") Long id, Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    if (physicianId == null) {
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    PatientCase onePatientCase = patientCaseServ.getOne(id);
	    String patientName = onePatientCase.getPatient().getPatientFirstName();
	    String onePatientCaseDayCreatedAtDate = onePatientCase.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	    String onePatientCaseCreatedAtDate = onePatientCase.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	    if (loggedInPatient == null || onePatientCase == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    } 
	    
	    if ( patientId != id) {

        // Date Ranges
        int onsetHistory = filterUtil.calculateDaysLocalDateDifference(onePatientCase.getOnset(), LocalDate.now());
        long visitHistory = searchUtil.calculateDateTimeDifference(onePatientCase.getCreatedAt(), LocalDateTime.now(), ChronoUnit.DAYS);

	    if (loggedInPatient == null || onePatientCase == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }	

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	        diagnosticUtil.searchDiagnosticRecordByCharacter(model, trimmedSearchTerm);
	        diagnosticUtil.searchSingleDiagnosticRecordByCharacter(model, trimmedSearchTerm);
	        diagnosticUtil.searchSinglePatientCaseDiagnosticRecordByCharacter(model, trimmedSearchTerm);
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display physical oneCurrentMedicationHistory. one of the JSP is looping over filtered patient case cases
	        diagnosticUtil.searchDiagnosticRecordByCharacter(model, patientName);
	        //model.addAttribute("allCurrentMedicationsWithFilter", Collections.emptyList());
	        //diagnosticUtil.searchSingleDiagnosticRecordByCharacter(model, patientName);
	        //diagnosticUtil.searchSinglePatientCaseDiagnosticRecordByCharacter(model, patientName);
	        model.addAttribute("allPatientCasesWithFilter",searchUtil.returnSearchPatientCaseByCharacter(patientName));
	    }

		// Add formatted dates to the model
		//patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);

        // Date Formatting
		model.addAttribute("patientCase", onePatientCase);
        model.addAttribute("oneOnsetHistory", onsetHistory);
        model.addAttribute("onePatientVisitHistory", visitHistory);
		model.addAttribute("createdAt",onePatientCaseCreatedAtDate);	
		model.addAttribute("dayCreatedAt", onePatientCaseDayCreatedAtDate);
        diagnosticUtil.searchSinglePatientCaseDiagnosticRecordByCharacter(model, patientName);
	    }	
		return "PatientCases/viewOnePatientCase.jsp";
	}

	@GetMapping("/patientCases/newPatientCase")
	public String createPatientCase(@ModelAttribute("patientCase") PatientCase patientCase,
	    @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	    Model model, HttpSession session) {
	    Long physicianId = (Long) session.getAttribute("physician_id");
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Null Pointers
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    if (physicianId == null) {
	        // Handle physicianId being null, if needed
	    }

	    if (searchedPatientName == null) {
	        // Handle searchedPatientName being null, if needed
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    String patientName = loggedInPatient.getPatientFirstName();
	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	        List<Patient> matchedPatients = searchUtil.searchByCharacterMethod(model, trimmedSearchTerm);
	        
	        // Check if no patient is found
	        if (matchedPatients.isEmpty()) {
		        diagnosticUtil.searchDiagnosticRecordByCharacter(model, patientName);
		        diagnosticUtil.searchSingleDiagnosticRecordByCharacter(model, patientName);
	            return "/PatientCases/createPatientCase.jsp";
	        }
	        model.addAttribute("searchedPatients", matchedPatients);
	        searchUtil.searchPatientInsuranceByCharacter(model, trimmedSearchTerm);
	    }

	    // Format the LocalDateTime objects
	    patientUtil.setPatientAttributes(model);
	    patientUtil.addSearchedMethods(model, patientName);
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
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

	    // Validate PatientCase Start Date
	    if (!patientUtil.validatePastDates(onset)) {
	        result.rejectValue("onset", "error.onset", "Invalid Date Of Onset!");
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);


	    // Null checkers
	    if (loggedInPatient == null) {
	    }

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
        searchUtil.searchPatientInsuranceByCharacter(model, loggedInPatient.getPatientFirstName());

	    patientUtil.addSearchedMethods(model, loggedInPatient.getPatientFirstName());
        model.addAttribute("oneSearchedPatientAge", patientUtil.calculateDateDifference(loggedInPatient.getDateOfBirth(), LocalDate.now(), ChronoUnit.YEARS));

	    // Combine null checks plus errors
	    if (result.hasErrors()) {
	        // Add necessary model attributes for rendering the form with validation errors
	        model.addAttribute("loggedInPatient", loggedInPatient);
	        model.addAttribute("timeFormat", generateTimeFormatList());
		    patientUtil.addSearchedMethods(model, loggedInPatient.getPatientFirstName());
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
	    PatientCase patientCaseToEdit = patientCaseServ.getOne(id);

	    model.addAttribute("timeFormat", generateTimeFormatList());
		model.addAttribute("loggedInPatient", loggedInPatient);
	    searchUtil.searchByCharacterMethod(model, loggedInPatient.getPatientFirstName());
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