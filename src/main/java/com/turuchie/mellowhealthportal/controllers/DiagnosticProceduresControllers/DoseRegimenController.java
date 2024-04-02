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

import com.turuchie.mellowhealthportal.models.ClinicalOperations.DoseRegimen;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientCaseService;
import com.turuchie.mellowhealthportal.services.DiagnosticProceduresServices.DoseRegimenService;
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
public class DoseRegimenController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/hospitalDashboard";
	private static final String PATIENT_PATH = "/mellowHealth/patientsPortal/patients";
	@Autowired
	private DoseRegimenService doseRegimenServ;

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
	public DoseRegimenController(DoseRegimenService doseRegimenServ,
		PatientService patientServ, DiagnosticUtils diagnosticUtil, PatientCaseService patientCaseServ,
		PatientUtils patientUtil,PhysicianService physicianServ, SearchUtil searchUtil) {
   	        this.patientUtil = patientUtil;
	        this.searchUtil = searchUtil;
	        this.patientServ = patientServ;
	        this.patientCaseServ = patientCaseServ;
	        this.doseRegimenServ = doseRegimenServ;
    }
	
	public DoseRegimenController() {}

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

	@GetMapping("/doseRegimenRecords")
	public String doseRegimenIndexPage(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
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
	        diagnosticUtil.searchDoseRegimenRecordByCharacter(model,trimmedSearchTerm);
	        diagnosticUtil.searchSingleDoseRegimenRecordByCharacter(model, trimmedSearchTerm);
	        model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("searchedDoseRegimen", diagnosticUtil.searchFirstDoseRegimenByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display patient cases
	        model.addAttribute("searchedPatientDoseRegimenRecords", Collections.emptyList());
	        diagnosticUtil.searchDoseRegimenRecordByCharacter(model,loggedInPatient.getPatientFirstName());
	    }

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    return "DoseRegimenRecords/viewAllDoseRegimenRecords.jsp";
	}

	@GetMapping("/doseRegimenRecords/{id}")
	public String showOneDoseRegimen(
	        @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	        @PathVariable("id") Long id, Model model, HttpSession session) {
	    
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    DoseRegimen oneDoseRegimen = doseRegimenServ.getOne(id);

	    if (loggedInPatient == null || oneDoseRegimen == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }   

	    model.addAttribute("oneDoseRegimen", oneDoseRegimen);

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	diagnosticUtil.searchDoseRegimenRecordByCharacter(model, trimmedSearchTerm);
	        List<DoseRegimen> searchedRecords = diagnosticUtil.searchFirstDoseRegimenByCharacter(trimmedSearchTerm);

	        if (!searchedRecords.isEmpty()) {
	            // Get the first searched record
	            DoseRegimen firstSearchedRecord = searchedRecords.get(0);
	            
	            // Add necessary attributes to the model
	            model.addAttribute("oneSearchedDoseRegimen", firstSearchedRecord);
	            model.addAttribute("allDoseRegimenRecordsWithFilter", searchedRecords);
	        } else {
	            // If no matching records found, add an empty list to the model
	            model.addAttribute("allDoseRegimenRecordsWithFilter", Collections.emptyList());
	        }
	    } else {
	        // If the search bar is empty, do not display patient cases
	        model.addAttribute("allDoseRegimenRecordsWithFilter", Collections.emptyList());
	    }

	    // Add formatted dates to the model
	    patientUtil.setPatientAttributes(model);
	    filterUtil.addPhysicalAssessmentInfoToModel(model, id);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);

	    // Date Ranges
	    int recordHistory = filterUtil.calculateDaysLocaleDateTimeDiffference(oneDoseRegimen.getCreatedAt(), LocalDateTime.now());

	    // Date Formatting
	    model.addAttribute("oneDoseRegimenHistory", recordHistory);
	    model.addAttribute("dayCreatedAt", oneDoseRegimen.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	    model.addAttribute("createdAt", oneDoseRegimen.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));    
	    return "DoseRegimenRecords/viewOneDoseRegimenRecord.jsp";
	}


	@GetMapping("/doseRegimenRecords/newDoseRegimen")
	public String createDoseRegimen(
	   @ModelAttribute("doseRegimen") DoseRegimen doseRegimen,
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
        String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
        if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
            searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
            diagnosticUtil.searchSingleCurrentMedicationByCharacter(model, loggedInPatient.getPatientFirstName());
            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
            model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
        } else {
            //model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
            diagnosticUtil.searchSingleCurrentMedicationByCharacter(model, loggedInPatient.getPatientFirstName());
            searchUtil.searchPatientCaseByCharacter(model, loggedInPatient.getPatientFirstName());
        }

	    // Format the LocalDateTime objects
	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);

	    // Add common attributes
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    model.addAttribute("doseHrFrequency", generateHourlyFrequencyValues());
	    model.addAttribute("frequencyFormat", generateDoseFrequencyValues());
	    return "/DoseRegimenRecords/createDoseRegimenRecord.jsp";
	}


	@PostMapping("/process/doseRegimenRecords/createNewDoseRegimen")
	public String processCreateDoseRegimen(
		@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName, 
		@RequestParam(value = "patientCase", required = false) Long patientCaseId, 
		@Valid @ModelAttribute("doseRegimen") DoseRegimen newDoseRegimen,	    
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

	    // Add necessary model attributes for rendering the form with validation errors
	    if (result.hasErrors()) {
            model.addAttribute("loggedInPatient", loggedInPatient);
		    model.addAttribute("timeFormat", generateTimeFormatList());
		    model.addAttribute("frequencyFormat", generateDoseFrequencyValues());
		    model.addAttribute("doseHrFrequency", generateHourlyFrequencyValues());
            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));
            model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(loggedInPatient.getPatientFirstName()));

			patientUtil.setPatientAttributes(model);
		    patientUtil.sortLoggedPatientAttributes(model, patientId);
            searchUtil.searchPatientCaseByCharacter(model, loggedInPatient.getPatientFirstName());
            diagnosticUtil.searchSingleCurrentMedicationByCharacter(model, loggedInPatient.getPatientFirstName());
		    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
	        return "/DoseRegimenRecords/createDoseRegimenRecord.jsp";
	    }

	    doseRegimenServ.create(newDoseRegimen);

	    // Flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");
	    return "redirect:" + PATIENT_PATH +"/"+ patientId;
	}

	@GetMapping("/doseRegimenRecords/editDoseRegimen/{id}")
	public String editDoseRegimen(@PathVariable("id") Long id,
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
	    DoseRegimen doseRegimenToEdit = doseRegimenServ.getOne(id);
	    model.addAttribute("timeFormat", generateTimeFormatList());
		model.addAttribute("loggedInPatient", loggedInPatient);

	    // Check if the logged-in physician is associated with the doseRegimen
	    if (doseRegimenToEdit.getPatient().getId().equals(patientId)) {
			patientUtil.setPatientAttributes(model);
	        model.addAttribute("doseRegimen", doseRegimenToEdit);
	        return "DoseRegimenRecords/editOneDoseRegimenRecord.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/doseRegimenRecords";
	    }
	}

	@PatchMapping("/process/doseRegimenRecords/editDoseRegimen/{id}")
	public String processEditDoseRegimen(@Valid @ModelAttribute("doseRegimen") DoseRegimen doseRegimen,
		BindingResult result,Model model, HttpSession session) {
	        // Add necessary model attributes for rendering the form with validation errors
	        Long patientId = (Long) session.getAttribute("patient_id");

		    PatientCase searchedPatientCase = patientCaseServ.getOne(doseRegimen.getPatientCase().getId());

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
	            return "DoseRegimenRecords/editOneDoseRegimenRecord.jsp";
	        } else {
	            // Handle the case where physicianId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PATIENT_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the doseRegimen
	        doseRegimenServ.update(doseRegimen);
	        return "redirect:/mellowHealth/doseRegimenRecords/{id}";
	    }
	}
	
	@DeleteMapping("/doseRegimenRecords/delete/{id}")
	public String deleteDoseRegimen(@PathVariable("id") Long id, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (patientId == null){
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    } 

	    if (physicianId == null){
	    } 

	    DoseRegimen doseRegimenToDelete = doseRegimenServ.getOne(id);
	    // Check if the logged-in physician is the owner of the doseRegimen
	    if (doseRegimenToDelete != null && doseRegimenToDelete.getPatient().getId().equals(patientId)) {
	        doseRegimenServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the physician is not the owner
	        return "redirect:/mellowHealth/patientsPortal";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


}