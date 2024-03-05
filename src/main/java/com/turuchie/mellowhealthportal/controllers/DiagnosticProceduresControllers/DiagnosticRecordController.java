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
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.DiagnosticRecord;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientCaseService;
import com.turuchie.mellowhealthportal.services.DiagnosticProceduresServices.DiagnosticRecordService;
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
public class DiagnosticRecordController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/hospitalDashboard";
	private static final String PATIENT_PATH = "/mellowHealth/patientsPortal/patients";
	@Autowired
	private DiagnosticRecordService diagnosticRecordServ;

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
	public DiagnosticRecordController(DiagnosticRecordService diagnosticRecordServ,
		PatientService patientServ, DiagnosticUtils diagnosticUtil, PatientCaseService patientCaseServ,
		PatientUtils patientUtil,PhysicianService physicianServ, SearchUtil searchUtil) {
   	        this.patientUtil = patientUtil;
	        this.searchUtil = searchUtil;
	        this.patientServ = patientServ;
	        this.patientCaseServ = patientCaseServ;
	        this.diagnosticRecordServ = diagnosticRecordServ;
    }
	
	public DiagnosticRecordController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/diagnosticRecords")
	public String diagnosticRecordIndexPage(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
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
	        diagnosticUtil.searchDiagnosticRecordByCharacter(model,trimmedSearchTerm);
	        diagnosticUtil.searchSingleDiagnosticRecordByCharacter(model, trimmedSearchTerm);
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("searchedDiagnosticRecord", diagnosticUtil.returnFirstDiagnosticRecordByCharacter(trimmedSearchTerm));
	        model.addAttribute("allDiagnosticRecordsWithFilter", diagnosticUtil.returnSearchDiagnosticRecordByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display patient cases
	        //model.addAttribute("allDiagnosticRecordsWithFilter", Collections.emptyList());
	        //model.addAttribute("allDiagnosticRecordsWithFilter",diagnosticUtil.returnFirstDiagnosticRecordByCharacter(patientName));
	    }

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    return "DiagnosticRecords/viewAllDiagnosticRecords.jsp";
	}

	@GetMapping("/diagnosticRecords/{id}")
	public String showOneDiagnosticRecord(
	        @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	        @PathVariable("id") Long id, Model model, HttpSession session) {
	    
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    DiagnosticRecord oneDiagnosticRecord = diagnosticRecordServ.getOne(id);

	    if (loggedInPatient == null || oneDiagnosticRecord == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }   

	    model.addAttribute("oneDiagnosticRecord", oneDiagnosticRecord);

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	diagnosticUtil.searchDiagnosticRecordByCharacter(model, trimmedSearchTerm);
	        List<DiagnosticRecord> searchedRecords = diagnosticUtil.returnSearchDiagnosticRecordByCharacter(trimmedSearchTerm);

	        if (!searchedRecords.isEmpty()) {
	            // Get the first searched record
	            DiagnosticRecord firstSearchedRecord = searchedRecords.get(0);
	            
	            // Add necessary attributes to the model
	            model.addAttribute("oneSearchedDiagnosticRecord", firstSearchedRecord);
	            model.addAttribute("allDiagnosticRecordsWithFilter", searchedRecords);
	        } else {
	            // If no matching records found, add an empty list to the model
	            model.addAttribute("allDiagnosticRecordsWithFilter", Collections.emptyList());
	        }
	    } else {
	        // If the search bar is empty, do not display patient cases
	        model.addAttribute("allDiagnosticRecordsWithFilter", Collections.emptyList());
	    }

	    // Add formatted dates to the model
	    patientUtil.setPatientAttributes(model);
	    filterUtil.addPhysicalAssessmentInfoToModel(model, id);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);

	    // Date Ranges
	    int recordHistory = filterUtil.calculateDaysLocaleDateTimeDiffference(oneDiagnosticRecord.getCreatedAt(), LocalDateTime.now());

	    // Date Formatting
	    model.addAttribute("oneDiagnosticRecordHistory", recordHistory);
	    model.addAttribute("dayCreatedAt", oneDiagnosticRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	    model.addAttribute("createdAt", oneDiagnosticRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));    
	    return "DiagnosticRecords/viewOneDiagnosticRecord.jsp";
	}


	@GetMapping("/diagnosticRecords/newDiagnosticRecord")
	public String createDiagnosticRecord(
	   @ModelAttribute("diagnosticRecord") DiagnosticRecord diagnosticRecord,
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

	    return "/DiagnosticRecords/createDiagnosticRecord.jsp";
	}


	@PostMapping("/process/diagnosticRecords/createNewDiagnosticRecord")
	public String processCreateDiagnosticRecord(
		@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName, 
		@RequestParam(value = "patientCase", required = false) Long patientCaseId, 
		@Valid @ModelAttribute("diagnosticRecord") DiagnosticRecord newDiagnosticRecord,	    
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
	    PatientCase searchedPatientCase = patientCaseServ.getOne(newDiagnosticRecord.getPatientCase().getId());

	    // Add necessary model attributes for rendering the form with validation errors
	    if (result.hasErrors()) {
			patientUtil.setPatientAttributes(model);
		    patientUtil.sortLoggedPatientAttributes(model, patientId);
		    model.addAttribute("timeFormat", generateTimeFormatList());
            model.addAttribute("loggedInPatient", loggedInPatient);
            model.addAttribute("oneSearchedPatientCase",  searchedPatientCase);
            model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(searchedPatientCase.getPatient().getPatientFirstName()));
            model.addAttribute("oneSearchedPatientAge", patientUtil.calculateDateDifference(searchedPatientCase.getPatient().getDateOfBirth(), LocalDate.now(), ChronoUnit.YEARS));
            model.addAttribute("oneSearchedPatientCaseCreatedAt",  searchedPatientCase.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));

		    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
	        return "/DiagnosticRecords/createDiagnosticRecord.jsp";
	    }

	    diagnosticRecordServ.create(newDiagnosticRecord);

	    // Flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");
	    return "redirect:" + PATIENT_PATH +"/"+ patientId;
	}

	@GetMapping("/diagnosticRecords/editDiagnosticRecord/{id}")
	public String editDiagnosticRecord(@PathVariable("id") Long id,
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
	    DiagnosticRecord diagnosticRecordToEdit = diagnosticRecordServ.getOne(id);
	    model.addAttribute("timeFormat", generateTimeFormatList());
		model.addAttribute("loggedInPatient", loggedInPatient);

	    // Check if the logged-in physician is associated with the diagnosticRecord
	    if (diagnosticRecordToEdit.getPatient().getId().equals(patientId)) {
			patientUtil.setPatientAttributes(model);
	        model.addAttribute("diagnosticRecord", diagnosticRecordToEdit);
	        return "DiagnosticRecords/editOneDiagnosticRecord.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/diagnosticRecords";
	    }
	}

	@PatchMapping("/process/diagnosticRecords/editDiagnosticRecord/{id}")
	public String processEditDiagnosticRecord(@Valid @ModelAttribute("diagnosticRecord") DiagnosticRecord diagnosticRecord,
		BindingResult result,Model model, HttpSession session) {
	        // Add necessary model attributes for rendering the form with validation errors
	        Long patientId = (Long) session.getAttribute("patient_id");

		    PatientCase searchedPatientCase = patientCaseServ.getOne(diagnosticRecord.getPatientCase().getId());

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
	            return "DiagnosticRecords/editOneDiagnosticRecord.jsp";
	        } else {
	            // Handle the case where physicianId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PATIENT_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the diagnosticRecord
	        diagnosticRecordServ.update(diagnosticRecord);
	        return "redirect:/mellowHealth/diagnosis/diagnosticRecords/{id}";
	    }
	}
	
	@DeleteMapping("/diagnosticRecords/delete/{id}")
	public String deleteDiagnosticRecord(@PathVariable("id") Long id, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (patientId == null){
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    } 

	    if (physicianId == null){
	    } 

	    DiagnosticRecord diagnosticRecordToDelete = diagnosticRecordServ.getOne(id);
	    // Check if the logged-in physician is the owner of the diagnosticRecord
	    if (diagnosticRecordToDelete != null && diagnosticRecordToDelete.getPatient().getId().equals(patientId)) {
	        diagnosticRecordServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the physician is not the owner
	        return "redirect:/mellowHealth/patientsPortal";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


}