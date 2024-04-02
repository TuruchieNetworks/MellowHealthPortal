 package com.turuchie.mellowhealthportal.controllers.DiagnosticProceduresControllers;

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

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientVitalRecord;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientVitalRecordService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
import com.turuchie.mellowhealthportal.utils.PatientFilterUtil;
import com.turuchie.mellowhealthportal.utils.PatientUtils;
import com.turuchie.mellowhealthportal.utils.SearchUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth")
public class PatientVitalRecordController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String VITALS_DASHBOARD_PATH = "/mellowHealth/patientVitalRecords";
	private static final String PATIENT_PATH = "/mellowHealth/patientsPortal/patients";
	@Autowired
	private PatientVitalRecordService patientVitalRecordServ;

	@Autowired
	private PatientUtils patientUtil;
	
	@Autowired
	private PatientFilterUtil filterUtil;

	@Autowired
	private SearchUtil searchUtil;

	@Autowired
	private PatientService patientServ;

	@Autowired
	public PatientVitalRecordController(PatientVitalRecordService patientVitalRecordServ, SearchUtil searchUtil,
		PhysicianService physicianServ, PatientService patientServ, PatientUtils patientUtil, PatientFilterUtil filterUtil) {
		   this.searchUtil = searchUtil;
		   this.filterUtil = filterUtil;
		   this.patientUtil = patientUtil;
		   this.patientServ = patientServ;
		   this.patientVitalRecordServ = patientVitalRecordServ;
    }
	
	public PatientVitalRecordController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/patientVitalRecords")
	public String patientVitalRecordIndexPage(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		if (patientId == null) {
	        // Redirect to login page if either physicianId or patientId is null
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

		Patient loggedInPatient = patientServ.getOne(patientId);

	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    String patientName = loggedInPatient.getPatientFirstName();

	    // Null Checks
	    if (patientName == null || physicianId == null) {
	    }

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
        if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	    	patientUtil.addSearchedMethods(model, trimmedSearchTerm);
	        searchUtil.setSearchUtilMethods(model, trimmedSearchTerm);
            searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allPatientCasesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
        } else {
	        patientUtil.addSearchedMethods(model, patientName);
	        searchUtil.searchSinglePatientCaseByCharacter(model, patientName);
            model.addAttribute("allPatientCasesWithFilter", Collections.emptyList());
            model.addAttribute("searchedPatientCase", Collections.emptyList());
        }

	    patientUtil.setPatientAttributes(model);
		filterUtil.sortAllByStartDate(model, patientId);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    patientUtil.setLoggedInPatientDateAttributes(patientId, model);
		return "PatientVitalRecords/viewAllPatientVitalRecords.jsp";
	}

	@GetMapping("/patientVitalRecords/{id}")
	public String showOnePatientVitalRecord(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		@PathVariable("id") Long id, Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		if (physicianId == null) {
	    }

		if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);

	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    PatientVitalRecord onePatientVitalRecord = patientVitalRecordServ.getOne(id);
	    if (onePatientVitalRecord == null) {
	    }	

	    String patientName = onePatientVitalRecord.getPatient().getPatientFirstName();
	    String dayCreatedAt = onePatientVitalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	    String createdAt = onePatientVitalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

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
            //model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(patientName));
        }

	    patientUtil.setPatientAttributes(model);
		filterUtil.sortAllByStartDate(model, patientId);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    patientUtil.setLoggedInPatientDateAttributes(patientId, model);

	    // Add formatted dates to the model
		model.addAttribute("createdAt", createdAt);
		model.addAttribute("dayCreatedAt", dayCreatedAt);
		model.addAttribute("onePatientVitalRecord", patientVitalRecordServ.getOne(id));
		return "PatientVitalRecords/viewOnePatientVitalRecord.jsp";
	}

	@GetMapping("/patientVitalRecords/newPatientVitalRecord")
	public String createPatientVitalRecord(@ModelAttribute("patientVitalRecord") PatientVitalRecord patientVitalRecord,
	    @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	    Model model,HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    Long physicianId = (Long) session.getAttribute("physician_id");

	    // Null Check For Patients
	    if (patientId == null) {
	         return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    String loggedInPatientName = patientServ.getOne(patientId).getPatientFirstName();

	    // Null Checks
	    if (physicianId == null || loggedInPatient == null || loggedInPatientName == null) {
	    }

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
        if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	    	patientUtil.addSearchedMethods(model, loggedInPatientName);
            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(loggedInPatientName));
        } else {
            model.addAttribute("searchedPatientCase", Collections.emptyList());
        }

	    patientUtil.setPatientAttributes(model);
		filterUtil.sortAllByStartDate(model, patientId);
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    return "/PatientVitalRecords/createPatientVitalRecord.jsp";
	}

	@PostMapping("/process/patientVitalRecords/createNewPatientVitalRecord")
	public String processCreatePatientVitalRecord(Model model, @Valid @ModelAttribute("patientVitalRecord") PatientVitalRecord newPatientVitalRecord,	    
	    BindingResult result, HttpSession session, RedirectAttributes redirectAttributes) {
	    Long physicianId = (Long) session.getAttribute("physician_id");
	    Long patientId = (Long) session.getAttribute("patient_id");
	    
		// Redirect to login if patientId is null
	    if (patientId == null) {
		        return "redirect:" + PATIENT_LOGIN_PATH;
		    }

		    if (physicianId == null) {
		    }

		    Patient loggedInPatient = patientServ.getOne(patientId);
		    String loggedInPatientName = patientServ.getOne(patientId).getPatientFirstName();

		    // Null Checks
		    if (loggedInPatient == null || loggedInPatientName == null) {
		    }

		    // Add necessary model attributes for rendering the form with validation errors
		    if (result.hasErrors()) {
			    patientUtil.sortLoggedPatientAttributes(model, patientId);
			    model.addAttribute("timeFormat", generateTimeFormatList());
		        searchUtil.setSearchUtilMethods(model, loggedInPatientName);
	            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(loggedInPatientName));
			    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
		        return "/PatientVitalRecords/createPatientVitalRecord.jsp";
		    }

	    // Continue processing if there are no validation errors
		//	patientVitalRecord.setPatient(physicianServ.getOne(physicianId));
	    patientVitalRecordServ.create(newPatientVitalRecord);
	    // Add flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");
	    
	    // Redirect to the physician's page after successful patientVitalRecord creation
	    return "redirect:" + PATIENT_PATH +"/"+ patientId;
	}

	@GetMapping("/patientVitalRecords/editPatientVitalRecord/{id}")
	public String editPatientVitalRecord(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		@PathVariable("id") Long id,Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	        return "redirect:/mellowHealth/patientsPortal/login";
	    }

	    // Fetch the patientVitalRecord by id
	    PatientVitalRecord patientVitalRecordToEdit = patientVitalRecordServ.getOne(id); 
	    if (patientVitalRecordToEdit == null) {
	        // Handle the case where the patientVitalRecord with the given id is not found
	        return "redirect:" + VITALS_DASHBOARD_PATH + "/" + id;
	    }

	    patientUtil.setPatientAttributes(model);
	    Patient loggedInPatient = patientServ.getOne(patientId);
	    String loggedInPatientName = patientServ.getOne(patientId).getPatientFirstName();
	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;

	    // Check if the logged-in patient is associated with the patientVitalRecord
	    if (patientVitalRecordToEdit.getPatient().getId().equals(patientId)) {
	        if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
		        //searchUtil.setSearchUtilMethods(model, trimmedSearchTerm);
	            //searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
	            patientUtil.addSearchedMethods(model, loggedInPatientName);
	            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(loggedInPatientName));
	        } else {
	        	patientUtil.addSearchedMethods(model, loggedInPatientName);
	        }

		    model.addAttribute("loggedInPatient", loggedInPatient);
	        model.addAttribute("timeFormat", generateTimeFormatList());
	        model.addAttribute("patientVitalRecord", patientVitalRecordToEdit);
	        return "/PatientVitalRecords/editOnePatientVitalRecord.jsp";
	    } else {
	        return "redirect:/mellowHealth/patientVitalRecords/{id}";
	    }
	}


	@PatchMapping("/process/patientVitalRecords/editPatientVitalRecord/{id}")
	public String processEditPatientVitalRecord(@Valid @ModelAttribute("patientVitalRecord") PatientVitalRecord patientVitalRecord,
		BindingResult result, Model model, HttpSession session) {  
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientVitalRecord == null) {
	        // Handle the case where the patientVitalRecord with the given id is not found
	        return "redirect:" + PATIENT_PATH + patientId;
	    }
	    if (result.hasErrors()) {
	        // Add necessary model attributes for rendering the form with validation errors
	        Patient loggedInPatient = patientServ.getOne(patientId);
		    String loggedInPatientName = patientServ.getOne(patientId).getPatientFirstName();

	        if (patientId != null) {
	            // Add other necessary attributes to the model
			    patientUtil.setPatientAttributes(model);
	            model.addAttribute("loggedInPatient", loggedInPatient);
	        	patientUtil.addSearchedMethods(model, loggedInPatientName);
	            model.addAttribute("searchedPatientCase", searchUtil.returnFirstPatientCaseByCharacter(loggedInPatientName));

	            // Return the view with the model attributes
	            return "PatientVitalRecords/editOnePatientVitalRecord.jsp";
	        } else {
	            // Handle the case where patientId is null (redirect to login, show an error, etc.)
	            return "redirect:/mellowHealth/patientsPortal/login";
	        }
	    } else {
	        // Validation passed, update the patientVitalRecord
	        patientVitalRecordServ.update(patientVitalRecord);
	        return "redirect:/mellowHealth/patientVitalRecords/{id}";
	    }
	}
	
	@DeleteMapping("/patientVitalRecords/delete/{id}")
	public String deletePatientVitalRecord(@PathVariable("id") Long id, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Redirect to login if physicianId is null
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    PatientVitalRecord patientVitalRecordToDelete = patientVitalRecordServ.getOne(id);

	    // Check if the logged-in physician is the owner of the patientVitalRecord
	    if (patientVitalRecordToDelete != null && patientVitalRecordToDelete.getPatient().getId().equals(patientId)) {
	        patientVitalRecordServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the physician is not the owner
	        return "redirect:" + PATIENT_PATH;
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


}