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

import com.turuchie.mellowhealthportal.models.ClinicalOperations.CoagulationRecord;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.CoagulationRecordService;
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
public class CoagulationRecordsController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/hospitalDashboard";
	private static final String PATIENT_PATH = "/mellowHealth/patientsPortal/patients";
	@Autowired
	private CoagulationRecordService coagulationRecordServ;

	@Autowired
	private PatientService patientServ;

	
	@Autowired
	private PatientUtils patientUtil;
	
	@Autowired
	private PatientFilterUtil filterUtil;

	@Autowired
	private SearchUtil searchUtil;

	@Autowired
	private DiagnosticUtils diagnosticUtil;

	@Autowired
	public CoagulationRecordsController(CoagulationRecordService coagulationRecordServ,
		PatientService patientServ, PatientUtils patientUtil, PhysicianService physicianServ,
		SearchUtil searchUtil, DiagnosticUtils diagnosticUtil) {
	        this.patientUtil = patientUtil;
	        this.searchUtil = searchUtil;
	        this.diagnosticUtil = diagnosticUtil;
	        this.coagulationRecordServ = coagulationRecordServ;
    }
	
	public CoagulationRecordsController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/coagulationRecords")
	public String coagulationRecordIndexPage(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	    Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Null Pointers
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    String patientName = patientServ.getOne(patientId).getPatientFirstName();

	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	diagnosticUtil.setAllSearchTrimmedMethods(model, trimmedSearchTerm);
	        model.addAttribute("searchedCoagulationRecord", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allCoagulationRecordsWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display physical oneCoagulationRecordHistory. one of the JSP is looping over filtered patient case cases
	        //model.addAttribute("allCoagulationRecordsWithFilter", Collections.emptyList());
	        diagnosticUtil.setAllSearchTrimmedMethods(model, patientName);
	        model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
	    }

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
        //model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));
	    return "CoagulationRecords/viewAllCoagulationRecords.jsp";
	}

	@GetMapping("/coagulationRecords/{id}")
	public String showOneCoagulationRecord(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		@PathVariable("id") Long id, Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    if (physicianId == null) {
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    String patientName = patientServ.getOne(patientId).getPatientFirstName();
	    CoagulationRecord oneCoagulationRecord = coagulationRecordServ.getOne(id);

	    if (loggedInPatient == null || oneCoagulationRecord == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }	

		// Add formatted dates to the model
		patientUtil.setPatientAttributes(model);
	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	searchUtil.setSearchUtilMethods(model, trimmedSearchTerm);
	    	diagnosticUtil.setAllSearchTrimmedMethods(model, trimmedSearchTerm);
	    } else {
	        // If the search bar is empty, do not display physical oneCoagulationRecordHistory. one of the JSP is looping over filtered patient case cases
	        //model.addAttribute("allCoagulationRecordsWithFilter", Collections.emptyList());
	    	searchUtil.setSearchUtilMethods(model, patientName);
	        diagnosticUtil.setAllSearchTrimmedMethods(model, patientName);
	        model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
	    }

        // Date Ranges
        int oneCoagulationRecordHistory = filterUtil.calculateDaysLocalDateDifference(oneCoagulationRecord.getCreatedAt().toLocalDate(), LocalDate.now());

        // Date Formatting
		patientUtil.sortLoggedPatientAttributes(model, patientId);
		model.addAttribute("oneCoagulationRecord", coagulationRecordServ.getOne(id));
        model.addAttribute("oneCoagulationRecordHistory", oneCoagulationRecordHistory);
		model.addAttribute("dayCreatedAt", oneCoagulationRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		model.addAttribute("createdAt", oneCoagulationRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));	
		return "CoagulationRecords/viewOneCoagulationRecord.jsp";
	}

	@GetMapping("/coagulationRecords/newCoagulationRecord")
	public String createCoagulationRecord(
	   @ModelAttribute("coagulationRecord") CoagulationRecord coagulationRecord,
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

	    if (loggedInPatient == null || patientCaseId == null) {
	        // Handle the situation where loggedInPatient or searchedPatientCase is null
	    }
        // Add attributes related to patient case search
	    
        //searchUtil.searchPatientCaseByCharacter(model, loggedInPatient.getPatientFirstName());
        //model.addAttribute("searchedPatientCase", searchUtil.returnSearchPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));
        String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
        if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
            searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
            model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
        } else {
            model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
            searchUtil.searchPatientCaseByCharacter(model, loggedInPatient.getPatientFirstName());
            model.addAttribute("searchedPatientAge", patientUtil.calculateDateDifference(loggedInPatient.getDateOfBirth(), LocalDate.now(), ChronoUnit.YEARS));
            //model.addAttribute("searchedPatientCase", searchUtil.returnSearchPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));
        }

	    // Add common attributes
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("timeFormat", generateTimeFormatList());

	    // Format the LocalDateTime objects
	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    return "/CoagulationRecords/createCoagulationRecord.jsp";
	}


	@PostMapping("/process/coagulationRecords/createNewCoagulationRecord")
	public String processCreateCoagulationRecord(
		@RequestParam(value = "patientCase", required = false) Long patientCaseId,
		@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		@Valid @ModelAttribute("coagulationRecord") CoagulationRecord newCoagulationRecord,
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

	    // Situation where loggedInPatient or searchedPatientCase is null
	    if (loggedInPatient == null || patientCaseId == null) {
	    }

        // Necessary model attributes before rendering the form with validation errors
	    model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));
        model.addAttribute("searchedPatientAge", patientUtil.calculateDateDifference(loggedInPatient.getDateOfBirth(), LocalDate.now(), ChronoUnit.YEARS));

        // Add necessary model attributes for rendering the form with validation errors
	    if (result.hasErrors()) {
			patientUtil.setPatientAttributes(model);
		    patientUtil.sortLoggedPatientAttributes(model, patientId);
		    model.addAttribute("timeFormat", generateTimeFormatList());
	        searchUtil.searchPatientCaseByCharacter(model, loggedInPatient.getPatientFirstName());
            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));
            model.addAttribute("searchedPatientCase", searchUtil.returnSearchPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));
            model.addAttribute("searchedPatientAge", patientUtil.calculateDateDifference(loggedInPatient.getDateOfBirth(), LocalDate.now(), ChronoUnit.YEARS));


		    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
	        return "/CoagulationRecords/createCoagulationRecord.jsp";
	    }

	    coagulationRecordServ.create(newCoagulationRecord);

	    // Flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");
	    return "redirect:" + PATIENT_PATH +"/"+ patientId;
	}

	@GetMapping("/coagulationRecords/editCoagulationRecord/{id}")
	public String editCoagulationRecord(@PathVariable("id") Long id,
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

	    CoagulationRecord coagulationRecordToEdit = coagulationRecordServ.getOne(id);
	    Patient loggedInPatient = patientServ.getOne(patientId);
	    String patientName = loggedInPatient.getPatientFirstName();
 
	    patientUtil.setPatientAttributes(model);
	    diagnosticUtil.setAllSearchTrimmedMethods(model, patientName);
	    model.addAttribute("timeFormat", generateTimeFormatList());
		model.addAttribute("loggedInPatient", loggedInPatient);
        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(patientName));

	    // Check if the logged-in physician is associated with the coagulationRecord
	    if (coagulationRecordToEdit.getPatient().getId().equals(patientId)) {
			patientUtil.setPatientAttributes(model);
	        model.addAttribute("coagulationRecord", coagulationRecordToEdit);
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(patientName));
	        return "CoagulationRecords/editOneCoagulationRecord.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/coagulationRecords";
	    }
	}

	@PatchMapping("/process/coagulationRecords/editCoagulationRecord/{id}")
	public String processEditCoagulationRecord(@PathVariable("id") Long id, @Valid @ModelAttribute("coagulationRecord") CoagulationRecord coagulationRecord,
		BindingResult result,Model model, HttpSession session) {
	        // Add necessary model attributes for rendering the form with validation errors
	        Long patientId = (Long) session.getAttribute("patient_id");

		    Patient loggedInPatient = patientServ.getOne(patientId);
		    String patientName = loggedInPatient.getPatientFirstName();

	        if (loggedInPatient == null || patientName == null) {	        	
	        }
	        
		    patientUtil.setPatientAttributes(model);
		    diagnosticUtil.setAllSearchTrimmedMethods(model, patientName);

		    if (result.hasErrors()) {
	        // Model Attributes
	        if (patientId != null) {
			    patientUtil.setPatientAttributes(model);
			    model.addAttribute("timeFormat", generateTimeFormatList());
			    diagnosticUtil.setAllSearchTrimmedMethods(model, patientName);
	            model.addAttribute("loggedInPatient", patientServ.getOne(patientId));
		        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(patientName));

	            // Return the view with the model attributes
	            return "CoagulationRecords/editOneCoagulationRecord.jsp";
	        } else {
	            // Handle the case where physicianId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PATIENT_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the coagulationRecord
	        coagulationRecordServ.update(coagulationRecord);
	        return "redirect:/mellowHealth/coagulationRecords/{id}";
	    }
	}
	
	@DeleteMapping("/coagulationRecords/deleteCoagulationRecord/{id}")
	public String deleteCoagulationRecord(@PathVariable("id") Long id, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (patientId == null){
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    } 

	    if (physicianId == null){
	    } 

	    CoagulationRecord coagulationRecordToDelete = coagulationRecordServ.getOne(id);
	    // Check if the logged-in physician is the owner of the coagulationRecord
	    if (coagulationRecordToDelete != null && coagulationRecordToDelete.getPatient().getId().equals(patientId)) {
	        coagulationRecordServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the physician is not the owner
	        return "redirect:/mellowHealth/patientsPortal";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


}