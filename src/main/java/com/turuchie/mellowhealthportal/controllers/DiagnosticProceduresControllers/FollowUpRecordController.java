package com.turuchie.mellowhealthportal.controllers.DiagnosticProceduresControllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.FollowUpRecord;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientCaseService;
import com.turuchie.mellowhealthportal.services.DiagnosticProceduresServices.FollowUpRecordService;
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
public class FollowUpRecordController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth";
	private static final String PATIENT_PATH = "/mellowHealth/patientsPortal/patients";
	@Autowired
	private FollowUpRecordService followUpRecordServ;

	@Autowired
	private PatientService patientServ;

	@Autowired
	private PatientCaseService patientCaseServ;

	@Autowired
	private SearchUtil searchUtil;
	
	@Autowired
	private PatientUtils patientUtil;
	
	@Autowired
	private DiagnosticUtils diagnosticUtil;
	
	@Autowired
	private PatientFilterUtil filterUtil;

	@Autowired
	public FollowUpRecordController(FollowUpRecordService followUpRecordServ,
		PatientService patientServ, DiagnosticUtils diagnosticUtil, PatientCaseService patientCaseServ,
		PatientUtils patientUtil,PhysicianService physicianServ, SearchUtil searchUtil) {
   	        this.patientUtil = patientUtil;
	        this.searchUtil = searchUtil;
	        this.patientServ = patientServ;
	        this.patientCaseServ = patientCaseServ;
	        this.diagnosticUtil = diagnosticUtil;
	        this.followUpRecordServ = followUpRecordServ;
    }
	
	public FollowUpRecordController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/followUpRecords")
	public String followUpRecordIndexPage(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
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

	    //String patientName = loggedInPatient.getPatientFirstName();
	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
	        diagnosticUtil.searchFollowUpRecordByCharacter(model,trimmedSearchTerm);
	        diagnosticUtil.searchSingleFollowUpRecordByCharacter(model, trimmedSearchTerm);

	        model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display patient cases
	        model.addAttribute("allFollowUpRecordsWithFilter", Collections.emptyList());
	        model.addAttribute("allFollowUpRecordsWithFilter", Collections.emptyList());
	    }

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    return "FollowUpRecords/viewAllFollowUpRecords.jsp";
	}

	@GetMapping("/followUpRecords/{id}")
	public String showOneFollowUpRecord(
	        @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	        @PathVariable("id") Long id, Model model, HttpSession session) {
	    
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    FollowUpRecord oneFollowUpRecord = followUpRecordServ.getOne(id);

	    if (loggedInPatient == null || oneFollowUpRecord == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }   

	    model.addAttribute("oneFollowUpRecord", oneFollowUpRecord);
	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	diagnosticUtil.setAllSearchTrimmedMethods(model, trimmedSearchTerm);
	        diagnosticUtil.searchFollowUpRecordByCharacter(model,trimmedSearchTerm);
	        diagnosticUtil.searchSingleFollowUpRecordByCharacter(model, trimmedSearchTerm);

	        model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display physical oneCoagulationRecordHistory. one of the JSP is looping over filtered patient case cases
	        model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
	    	model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));
	    }

	    // Add formatted dates to the model
	    patientUtil.setPatientAttributes(model);
	    filterUtil.addPhysicalAssessmentInfoToModel(model, id);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);

	    // Date Ranges
	    int recordHistory = filterUtil.calculateDaysLocaleDateTimeDiffference(oneFollowUpRecord.getCreatedAt().atStartOfDay(), LocalDateTime.now());

	    // Date Formatting
	    model.addAttribute("oneFollowUpRecordHistory", recordHistory);
	    model.addAttribute("dayCreatedAt", oneFollowUpRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	    model.addAttribute("createdAt", oneFollowUpRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));    
	    return "FollowUpRecords/viewOneFollowUpRecord.jsp";
	}


	@GetMapping("/followUpRecords/newFollowUpRecord")
	public String createFollowUpRecord(
	   @ModelAttribute("followUpRecord") FollowUpRecord followUpRecord,
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

        // Add attributes related to patient case search
        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(patientServ.getOne(patientId).getPatientFirstName()));
        String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
        if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
            searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
            model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
        } else {
            model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
        }

	    // Format the LocalDateTime objects
	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
        model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));
        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));

	    // Add common attributes
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("timeFormat", generateTimeFormatList());

	    return "/FollowUpRecords/createFollowUpRecord.jsp";
	}


	@PostMapping("/process/followUpRecords/createNewFollowUpRecord")
	public String processCreateFollowUpRecord(
		@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName, 
		@RequestParam(value = "patientCase", required = false) Long patientCaseId, 
		@Valid @ModelAttribute("followUpRecord") FollowUpRecord newFollowUpRecord,	    
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
	    PatientCase searchedPatientCase = patientCaseServ.getOne(newFollowUpRecord.getPatientCase().getId());

	    // Add necessary model attributes for rendering the form with validation errors
	    if (result.hasErrors()) {
			patientUtil.setPatientAttributes(model);
		    patientUtil.sortLoggedPatientAttributes(model, patientId);
		    model.addAttribute("timeFormat", generateTimeFormatList());
            model.addAttribute("loggedInPatient", loggedInPatient);
            model.addAttribute("oneSearchedPatientCase",  searchedPatientCase);
            model.addAttribute("oneSearchedPatientCaseCreatedAt",  searchedPatientCase.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
            model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(searchedPatientCase.getPatient().getPatientFirstName()));
            model.addAttribute("oneSearchedPatientAge", patientUtil.calculateDateDifference(searchedPatientCase.getPatient().getDateOfBirth(), LocalDate.now(), ChronoUnit.YEARS));

		    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
	        return "/FollowUpRecords/createFollowUpRecord.jsp";
	    }

	    followUpRecordServ.create(newFollowUpRecord);

	    // Flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");
	    return "redirect:" + PATIENT_PATH +"/"+ patientId;
	}

	@GetMapping("/followUpRecords/editFollowUpRecord/{id}")
	public String editFollowUpRecord(@PathVariable("id") Long id,
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

	    FollowUpRecord followUpRecordToEdit = followUpRecordServ.getOne(id);
		// Check if the logged-in physician is associated with the followUpRecord
	    if (followUpRecordToEdit.getPatient().getId().equals(patientId)) {
		    patientUtil.setPatientAttributes(model);
		    PatientCase searchedPatientCase = patientCaseServ.getOne(followUpRecordServ.getOne(id).getPatientCase().getId());
	
		    Patient loggedInPatient = patientServ.getOne(patientId);
		    model.addAttribute("timeFormat", generateTimeFormatList());
			model.addAttribute("loggedInPatient", loggedInPatient);
	        model.addAttribute("oneSearchedPatientCase",  searchedPatientCase);
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(searchedPatientCase.getPatient().getPatientFirstName()));
	        model.addAttribute("oneSearchedPatientAge", patientUtil.calculateDateDifference(searchedPatientCase.getPatient().getDateOfBirth(), LocalDate.now(), ChronoUnit.YEARS));
	        model.addAttribute("oneSearchedPatientCaseCreatedAt",  searchedPatientCase.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));

			patientUtil.setPatientAttributes(model);
	        model.addAttribute("followUpRecord", followUpRecordToEdit);
	        return "FollowUpRecords/editOneFollowUpRecord.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/followUpRecords";
	    }
	}

	@PatchMapping("/process/followUpRecords/editFollowUpRecord/{id}")
	public String processEditFollowUpRecord(@Valid @ModelAttribute("followUpRecord") FollowUpRecord followUpRecord,
		BindingResult result,Model model, HttpSession session) {
	        // Add necessary model attributes for rendering the form with validation errors
	        Long patientId = (Long) session.getAttribute("patient_id");
		    PatientCase searchedPatientCase = patientCaseServ.getOne(followUpRecord.getPatientCase().getId());

	        if (searchedPatientCase == null) {	        	
	        }
		    
	        model.addAttribute("oneSearchedPatientAge", patientUtil.calculateDateDifference(searchedPatientCase.getPatient().getDateOfBirth(), LocalDate.now(), ChronoUnit.YEARS));
	    if (result.hasErrors()) {
	        // Model Attributes
	        if (patientId != null) {
	    		patientUtil.setPatientAttributes(model);
			    model.addAttribute("timeFormat", generateTimeFormatList());
	            model.addAttribute("loggedInPatient", patientServ.getOne(patientId));
	            model.addAttribute("oneSearchedPatientCase",  searchedPatientCase);
	            model.addAttribute("oneSearchedPatientCase",  searchedPatientCase);
	            model.addAttribute("oneSearchedPatientCaseCreatedAt",  searchedPatientCase.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	            model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(searchedPatientCase.getPatient().getPatientFirstName()));
	            model.addAttribute("oneSearchedPatientAge", patientUtil.calculateDateDifference(searchedPatientCase.getPatient().getDateOfBirth(), LocalDate.now(), ChronoUnit.YEARS));
	            // Return the view with the model attributes
	            return "FollowUpRecords/editOneFollowUpRecord.jsp";
	        } else {
	            // Handle the case where physicianId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PATIENT_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the followUpRecord
	        followUpRecordServ.update(followUpRecord);
	        return "redirect:/mellowHealth/followUpRecords/{id}";
	    }
	}
	
	@DeleteMapping("/followUpRecords/delete/{id}")
	public String deleteFollowUpRecord(@PathVariable("id") Long id, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (patientId == null){
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    } 

	    if (physicianId == null){
	    } 

	    FollowUpRecord followUpRecordToDelete = followUpRecordServ.getOne(id);
	    // Check if the logged-in physician is the owner of the followUpRecord
	    if (followUpRecordToDelete != null && followUpRecordToDelete.getPatient().getId().equals(patientId)) {
	        followUpRecordServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the physician is not the owner
	        return "redirect:/mellowHealth/patientsPortal";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


}