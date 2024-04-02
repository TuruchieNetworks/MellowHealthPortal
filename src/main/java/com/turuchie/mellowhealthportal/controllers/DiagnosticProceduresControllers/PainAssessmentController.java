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

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PainAssessment;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientCaseService;
import com.turuchie.mellowhealthportal.services.DiagnosticProceduresServices.PainAssessmentService;
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
public class PainAssessmentController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/hospitalDashboard";
	private static final String PATIENT_PATH = "/mellowHealth/patientsPortal/patients";
	@Autowired
	private PainAssessmentService painAssessmentServ;

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
	public PainAssessmentController(PainAssessmentService painAssessmentServ,PatientService patientServ,DiagnosticUtils diagnosticUtil,
	   PatientCaseService patientCaseServ,PatientUtils patientUtil,PhysicianService physicianServ, SearchUtil searchUtil) {
	        this.patientUtil = patientUtil;
	        this.searchUtil = searchUtil;
	        this.patientServ = patientServ;
	        this.patientCaseServ = patientCaseServ;
	        this.painAssessmentServ = painAssessmentServ;
	        this.diagnosticUtil = diagnosticUtil;
    }
	
	public PainAssessmentController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i) ;
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

	@GetMapping("/painAssessments")
	public String painAssessmentIndexPage(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
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

        model.addAttribute("allPatientCasesWithFilter", patientCaseServ.getAll());
        model.addAttribute("allPainAssessmentsWithFilter", painAssessmentServ.getAll());
	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
	        model.addAttribute("searchedPainAssessment", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allPainAssessmentsWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display physical assessment. one of the JSP is looping over filtered patient case cases
	        //model.addAttribute("allPainAssessmentsWithFilter", Collections.emptyList());
	        model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
	    }

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
        //model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));
	    return "PainAssessmentRecords/viewAllPainAssessmentRecords.jsp";
	}

	@GetMapping("/painAssessments/{id}")
	public String showOnePainAssessment(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		@PathVariable("id") Long id, Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    if (physicianId == null) {
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    PainAssessment onePainAssessment = painAssessmentServ.getOne(id);
	    String formattedDayCreatedAt = onePainAssessment.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	    String formattedCreatedAt = onePainAssessment.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));	
	    long onePainAssessmentPatientId = painAssessmentServ.getOne(id).getPatient().getId();

	    if (loggedInPatient == null || onePainAssessment == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }	

		// Add formatted dates to the model
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
        filterUtil.sortAllByStartDate(model, onePainAssessmentPatientId);
	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display physical assessment. one of the JSP is looping over filtered patient case cases
	        //model.addAttribute("allPainAssessmentsWithFilter", Collections.emptyList());
	        model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
	    }

        // Date Ranges
        int assessmentHistory = filterUtil.calculateDaysLocalDateDifference(onePainAssessment.getCreatedAt().toLocalDate(), LocalDate.now());

        // Date Formatting
		model.addAttribute("createdAt", formattedCreatedAt);	
		model.addAttribute("dayCreatedAt", formattedDayCreatedAt);
		model.addAttribute("onePainAssessment", onePainAssessment);
        model.addAttribute("oneAssessmentHistory", assessmentHistory);
		return "PainAssessmentRecords/viewOnePainAssessmentRecord.jsp";
	}

	@GetMapping("/painAssessments/newPainAssessment")
	public String createPainAssessment(
	   @ModelAttribute("painAssessment") PainAssessment painAssessment,
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
	    String loggedPatientName = loggedInPatient.getPatientFirstName();

	    if (loggedInPatient == null || loggedPatientName == null || patientCaseId == null) {
	        // Handle the situation where loggedInPatient or searchedPatientCase is null
	    }
  
	    // Add attributes related to patient case search
        model.addAttribute("searchedPatientAge", patientUtil.calculateDateDifference(loggedInPatient.getDateOfBirth(), LocalDate.now(), ChronoUnit.YEARS));
        String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
        if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
            searchUtil.searchPatientCaseByCharacter(model, loggedPatientName);
            diagnosticUtil.searchPhysicalAssessmentByCharacter(model, loggedPatientName);
            diagnosticUtil.searchSinglePhysicalAssessmentByCharacter(model, loggedPatientName);
            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(loggedPatientName));
            model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(loggedPatientName));
        } else {
            model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
            diagnosticUtil.searchPhysicalAssessmentByCharacter(model, loggedPatientName);
            diagnosticUtil.searchSinglePhysicalAssessmentByCharacter(model, loggedPatientName);
        }

	    // Format the LocalDateTime objects
	    patientUtil.sortLoggedPatientAttributes(model, patientId);

	    // Add common attributes
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    model.addAttribute("painScaleValue", generatePainScaleValues());
	    return "/PainAssessmentRecords/createPainAssessmentRecord.jsp";
	}


	@PostMapping("/process/painAssessments/createNewPainAssessment")
	public String processCreatePainAssessment(
		@RequestParam(value = "patientCase", required = false) Long patientCaseId,
		@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		@Valid @ModelAttribute("painAssessment") PainAssessment newPainAssessment,
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
	    String loggedPatientName = loggedInPatient.getPatientFirstName();

	    if (loggedInPatient == null || loggedPatientName == null || patientCaseId == null) {
	    }
        // Add attributes related to patient case search
        diagnosticUtil.searchPhysicalAssessmentByCharacter(model, loggedPatientName);
        diagnosticUtil.searchSinglePhysicalAssessmentByCharacter(model, loggedPatientName);
        // Necessary model attributes before rendering the form with validation errors
	    model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));
        model.addAttribute("searchedPatientAge", patientUtil.calculateDateDifference(loggedInPatient.getDateOfBirth(), LocalDate.now(), ChronoUnit.YEARS));

        // Add necessary model attributes for rendering the form with validation errors
	    if (result.hasErrors()) {
			patientUtil.setPatientAttributes(model);
		    patientUtil.sortLoggedPatientAttributes(model, patientId);
		    model.addAttribute("timeFormat", generateTimeFormatList());
		    model.addAttribute("painScaleValue", generatePainScaleValues());
	        diagnosticUtil.searchPhysicalAssessmentByCharacter(model, loggedPatientName);
	        diagnosticUtil.searchSinglePhysicalAssessmentByCharacter(model, loggedPatientName);
            model.addAttribute("searchedPatientCase", searchUtil.returnSearchPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));
            model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));
            model.addAttribute("oneSearchedPatientAge", patientUtil.calculateDateDifference(loggedInPatient.getDateOfBirth(), LocalDate.now(), ChronoUnit.YEARS));

		    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
	        return "/PainAssessmentRecords/createPainAssessmentRecord.jsp";
	    }

	    painAssessmentServ.create(newPainAssessment);

	    // Flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");
	    return "redirect:" + PATIENT_PATH +"/"+ patientId;
	}

	@GetMapping("/painAssessments/editPainAssessment/{id}")
	public String editPainAssessment(@PathVariable("id") Long id,
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
	    PainAssessment painAssessmentToEdit = painAssessmentServ.getOne(id);
	    model.addAttribute("timeFormat", generateTimeFormatList());
		model.addAttribute("loggedInPatient", loggedInPatient);

	    // Check if the logged-in physician is associated with the painAssessment
	    if (painAssessmentToEdit.getPatient().getId().equals(patientId)) {
			patientUtil.setPatientAttributes(model);
	        model.addAttribute("painAssessment", painAssessmentToEdit);
	        return "PainAssessmentRecords/editOnePainAssessmentRecord.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/painAssessments";
	    }
	}

	@PatchMapping("/process/painAssessments/editPainAssessment/{id}")
	public String processEditPainAssessment(@Valid @ModelAttribute("painAssessment") PainAssessment painAssessment,
		BindingResult result,Model model, HttpSession session) {
	        // Add necessary model attributes for rendering the form with validation errors
	        Long patientId = (Long) session.getAttribute("patient_id");

		    PatientCase searchedPatientCase = patientCaseServ.getOne(painAssessment.getPatientCase().getId());

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
	            return "PainAssessmentRecords/editOnePainAssessmentRecord.jsp";
	        } else {
	            // Handle the case where physicianId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PATIENT_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the painAssessment
	        painAssessmentServ.update(painAssessment);
	        return "redirect:/mellowHealth/diagnosis/painAssessments/{id}";
	    }
	}
	
	@DeleteMapping("/painAssessments/delete/{id}")
	public String deletePainAssessment(@PathVariable("id") Long id, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (patientId == null){
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    } 

	    if (physicianId == null){
	    } 

	    PainAssessment painAssessmentToDelete = painAssessmentServ.getOne(id);
	    // Check if the logged-in physician is the owner of the painAssessment
	    if (painAssessmentToDelete != null && painAssessmentToDelete.getPatient().getId().equals(patientId)) {
	        painAssessmentServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the physician is not the owner
	        return "redirect:/mellowHealth/patientsPortal";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


}