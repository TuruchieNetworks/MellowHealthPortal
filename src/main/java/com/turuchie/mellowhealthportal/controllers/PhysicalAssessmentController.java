package com.turuchie.mellowhealthportal.controllers;

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
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PhysicalAssessment;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PatientCaseService;
import com.turuchie.mellowhealthportal.services.PatientService;
import com.turuchie.mellowhealthportal.services.PhysicalAssessmentService;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.utils.PatientFilterUtil;
import com.turuchie.mellowhealthportal.utils.PatientUtils;
import com.turuchie.mellowhealthportal.utils.SearchUtil;
import com.turuchie.mellowhealthportal.utils.UtilInputConverter;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth/diagnosis")
public class PhysicalAssessmentController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/hospitalDashboard";
	private static final String PATIENT_PATH = "/mellowHealth/patientsPortal/patients";
	@Autowired
	private PhysicalAssessmentService physicalAssessmentServ;

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
	public PhysicalAssessmentController(PhysicalAssessmentService physicalAssessmentServ,PatientService patientServ,
		   PatientCaseService patientCaseServ,PatientUtils patientUtil,PhysicianService physicianServ, SearchUtil searchUtil) {
		        this.patientUtil = patientUtil;
		        this.searchUtil = searchUtil;
		        this.patientServ = patientServ;
		        this.patientCaseServ = patientCaseServ;
		        this.physicalAssessmentServ = physicalAssessmentServ;
    }
	
	public PhysicalAssessmentController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/physicalAssessments")
	public String physicalAssessmentIndexPage(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
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
        model.addAttribute("allPhysicalAssessmentsWithFilter", physicalAssessmentServ.getAll());
	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
	        model.addAttribute("searchedPhysicalAssessment", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allPhysicalAssessmentsWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display patient cases
	        model.addAttribute("allPhysicalAssessmentsWithFilter", Collections.emptyList());
	    }

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
        model.addAttribute("allPhysicalAssessments", physicalAssessmentServ.getAll());
	    return "PhysicalAssessmentRecords/viewAllPhysicalAssessmentRecords.jsp";
	}

	@GetMapping("/physicalAssessments/{id}")
	public String showOnePhysicalAssessment(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    if (physicianId == null) {
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    PhysicalAssessment onePhysicalAssessment = physicalAssessmentServ.getOne(id);

	    if (loggedInPatient == null || onePhysicalAssessment == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }	

		// Add formatted dates to the model
		patientUtil.setPatientAttributes(model);
        filterUtil.addPhysicalAssessmentInfoToModel(model, id);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);

        // Date Ranges
        int assessmentHistory = filterUtil.calculateDaysLocalDateDifference(onePhysicalAssessment.getCreatedAt(), LocalDate.now());

        // Date Formatting
        model.addAttribute("oneVisitHistory", assessmentHistory);
		model.addAttribute("onePhysicalAssessment", physicalAssessmentServ.getOne(id));
		model.addAttribute("dayCreatedAt", onePhysicalAssessment.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		model.addAttribute("createdAt", onePhysicalAssessment.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));	
		return "PhysicalAssessmentRecords/viewOnePhysicalAssessmentRecord.jsp";
	}

	@GetMapping("/physicalAssessments/newPhysicalAssessment")
	public String createPhysicalAssessment(
	   @ModelAttribute("physicalAssessment") PhysicalAssessment physicalAssessment,
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
        model.addAttribute("allPatientCasesWithFilter", patientCaseServ.getAll());
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

	    // Add common attributes
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("timeFormat", generateTimeFormatList());

	    return "/PhysicalAssessmentRecords/createPhysicalAssessment.jsp";
	}


	@PostMapping("/process/physicalAssessments/createNewPhysicalAssessment")
	public String processCreatePhysicalAssessment(
		@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName, 
		@RequestParam(value = "patientCase", required = false) Long patientCaseId, 
		@Valid @ModelAttribute("physicalAssessment") PhysicalAssessment newPhysicalAssessment,	    
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
	    PatientCase searchedPatientCase = patientCaseServ.getOne(newPhysicalAssessment.getPatientCase().getId());

	    // Add necessary model attributes for rendering the form with validation errors
	    if (result.hasErrors()) {
			patientUtil.setPatientAttributes(model);
		    patientUtil.sortLoggedPatientAttributes(model, patientId);
		    model.addAttribute("timeFormat", generateTimeFormatList());
            model.addAttribute("loggedInPatient", loggedInPatient);
            model.addAttribute("oneSearchedPatientCase",  searchedPatientCase);
            model.addAttribute("oneSearchedPatientAge", patientUtil.calculateDateDifference(searchedPatientCase.getPatient().getDateOfBirth(), LocalDate.now(), ChronoUnit.YEARS));
            model.addAttribute("oneSearchedPatientCaseCreatedAt",  searchedPatientCase.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));

		    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
	        return "/PhysicalAssessmentRecords/createPhysicalAssessment.jsp";
	    }

	    physicalAssessmentServ.create(newPhysicalAssessment);

	    // Flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");
	    return "redirect:" + PATIENT_PATH +"/"+ patientId;
	}

	@GetMapping("/physicalAssessments/editPhysicalAssessment/{id}")
	public String editPhysicalAssessment(@PathVariable("id") Long id,
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
        model.addAttribute("allPatientCasesWithFilter", patientCaseServ.getAll());
	    PhysicalAssessment physicalAssessmentToEdit = physicalAssessmentServ.getOne(id);
	    model.addAttribute("timeFormat", generateTimeFormatList());
		model.addAttribute("loggedInPatient", loggedInPatient);

	    // Check if the logged-in physician is associated with the physicalAssessment
	    if (physicalAssessmentToEdit.getPatient().getId().equals(patientId)) {
			patientUtil.setPatientAttributes(model);
	        model.addAttribute("physicalAssessment", physicalAssessmentToEdit);
	        return "PhysicalAssessmentRecords/editOnePhysicalAssessmentRecord.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/physicalAssessments";
	    }
	}

	@PatchMapping("/process/physicalAssessments/editPhysicalAssessment/{id}")
	public String processEditPhysicalAssessment(@Valid @ModelAttribute("physicalAssessment") PhysicalAssessment physicalAssessment,
		BindingResult result,Model model, HttpSession session) {
	        // Add necessary model attributes for rendering the form with validation errors
	        Long patientId = (Long) session.getAttribute("patient_id");

		    PatientCase searchedPatientCase = patientCaseServ.getOne(physicalAssessment.getPatientCase().getId());

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
	            return "PhysicalAssessmentRecords/editOnePhysicalAssessmentRecord.jsp";
	        } else {
	            // Handle the case where physicianId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PATIENT_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the physicalAssessment
	        physicalAssessmentServ.update(physicalAssessment);
	        return "redirect:/mellowHealth/diagnosis/physicalAssessments/{id}";
	    }
	}
	
	@DeleteMapping("/physicalAssessments/delete/{id}")
	public String deletePhysicalAssessment(@PathVariable("id") Long id, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (patientId == null){
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    } 

	    if (physicianId == null){
	    } 

	    PhysicalAssessment physicalAssessmentToDelete = physicalAssessmentServ.getOne(id);
	    // Check if the logged-in physician is the owner of the physicalAssessment
	    if (physicalAssessmentToDelete != null && physicalAssessmentToDelete.getPatient().getId().equals(patientId)) {
	        physicalAssessmentServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the physician is not the owner
	        return "redirect:/mellowHealth/patientsPortal";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


}