package com.turuchie.mellowhealthportal.controllers.DiagnosticProceduresControllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PregnancyFertilityAssessment;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.DiagnosticProceduresServices.PregnancyFertilityAssessmentService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
import com.turuchie.mellowhealthportal.utils.PatientUtils;
import com.turuchie.mellowhealthportal.utils.ListConverterUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth/hospitalDashboard")
public class PregnancyFertilityAssessmentController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/physicians/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/hospitalDashboard/";
	private static final String PATIENT_PATH = "/mellowHealth/physicians";
	@Autowired
	private PregnancyFertilityAssessmentService pregnancyFertilityAssessmentServ;

	@Autowired
	private PatientService physicianServ;
	
	@Autowired
	private PatientService patientServ;
	
	@Autowired
	private PatientUtils patientUtil;

	@Autowired
	public PregnancyFertilityAssessmentController(PregnancyFertilityAssessmentService pregnancyFertilityAssessmentServ,
			PatientUtils patientUtil,PatientService physicianServ,PatientService patientServ) {
		        this.patientServ = patientServ;
		        this.pregnancyFertilityAssessmentServ = pregnancyFertilityAssessmentServ;
		        this.physicianServ = physicianServ;
		        this.patientUtil = patientUtil;
    }
	
	public PregnancyFertilityAssessmentController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/pregnancyFertilityAssessments")
	public String pregnancyFertilityAssessmentIndexPage(Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (physicianId == null){
	    } 

	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

		Patient loggedInPatient = physicianServ.getOne(physicianId);		

	    model.addAttribute("loggedInPatient", loggedInPatient);
		model.addAttribute("allPregnancyFertilityAssessments", pregnancyFertilityAssessmentServ.getAll());

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    patientUtil.setLoggedInPatientDateAttributes(patientId, model);
		return "PregnancyFertilityAssessments/viewAllPregnancyFertilityAssessments.jsp";
	}

	@GetMapping("/pregnancyFertilityAssessments/{id}")
	public String showOnePregnancyFertilityAssessment(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (physicianId == null){
	    } 

	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    Patient loggedInPatient = physicianServ.getOne(physicianId);

	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }
	    PregnancyFertilityAssessment onePregnancyFertilityAssessment = pregnancyFertilityAssessmentServ.getOne(id);
	    model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	    onePregnancyFertilityAssessment.getCreatedAt();
		model.addAttribute("createdAt", onePregnancyFertilityAssessment.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));		
		model.addAttribute("loggedInPatient", loggedInPatient);
		model.addAttribute("allPregnancyFertilityAssessments", pregnancyFertilityAssessmentServ.getAll());
		model.addAttribute("pregnancyFertilityAssessment", pregnancyFertilityAssessmentServ.getOne(id));

		// Add formatted dates to the model
	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    patientUtil.setLoggedInPatientDateAttributes(patientId, model);
		return "PregnancyFertilityAssessments/viewOnePregnancyFertilityAssessment.jsp";
	}

	@GetMapping("/pregnancyFertilityAssessments/newPregnancyFertilityAssessment")
	public String createPregnancyFertilityAssessment(
	    @ModelAttribute("pregnancyFertilityAssessment") PregnancyFertilityAssessment pregnancyFertilityAssessment,
	    @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	    Model model, HttpSession session) {
	    Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (physicianId == null){
	    } 

	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;

	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	        Patient matchedPatientFirstName = patientServ.getOnePatientFirstName(trimmedSearchTerm.toLowerCase());
	        Patient matchedPatientFullName = patientServ.getOnePatientByFullName(trimmedSearchTerm.toLowerCase());
	        Patient matchedPatients = patientServ.getPatientsByPartialName(trimmedSearchTerm.toLowerCase());
	        List<Patient> matchedSearchedPatients = patientServ.getAllPatientsMatchingSearchTerm(trimmedSearchTerm.toLowerCase());
	        model.addAttribute("matchedPatients", matchedPatients);
	        model.addAttribute("matchedSearchedPatients", matchedSearchedPatients);
	        model.addAttribute("matchedPatientFirstName", matchedPatientFirstName);
	        model.addAttribute("matchedPatientFullName", matchedPatientFullName);
	    }

	    Patient loggedInPatient = physicianServ.getOne(patientId);
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    patientUtil.setLoggedInPatientDateAttributes(patientId, model);

	    return "/PregnancyFertilityAssessments/createPregnancyFertilityAssessment.jsp";
	}

	@PostMapping("/process/pregnancyFertilityAssessments/createNewPregnancyFertilityAssessment")
	public String processCreatePregnancyFertilityAssessment(Model model, @Valid @ModelAttribute("pregnancyFertilityAssessment") PregnancyFertilityAssessment newPregnancyFertilityAssessment,	    
	    BindingResult result, HttpSession session, RedirectAttributes redirectAttributes) {
	    Long physicianId = (Long) session.getAttribute("physician_id");
		    // Redirect to login if physicianId is null
		    if (physicianId == null) {
		        return "redirect:" + PATIENT_LOGIN_PATH;
		    }

		    // Add necessary model attributes for rendering the form with validation errors
		    if (result.hasErrors()) {
		        Patient loggedInPatient = physicianServ.getOne(physicianId);
			    model.addAttribute("loggedInPatient", loggedInPatient);
			    model.addAttribute("timeFormat", generateTimeFormatList());
			    model.addAttribute("allPatients", patientServ.findAll());
			    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
		        return "/PregnancyFertilityAssessments/createPregnancyFertilityAssessment.jsp";
		    }

	    // Continue processing if there are no validation errors
		//	pregnancyFertilityAssessment.setPatient(physicianServ.getOne(physicianId));
	    pregnancyFertilityAssessmentServ.create(newPregnancyFertilityAssessment);
	    // Add flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");
	    
	    // Redirect to the physician's page after successful pregnancyFertilityAssessment creation
	    return "redirect:" + PATIENT_PATH +"/"+ physicianId;
	}

	@GetMapping("/pregnancyFertilityAssessments/edit/{id}")
	public String editPregnancyFertilityAssessment(@PathVariable("id") Long id,
		@ModelAttribute("inputCollector") ListConverterUtil inputCollector,
		Model model, HttpSession session) {
	    Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (physicianId == null){
	    } 

	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    model.addAttribute("allPatients", patientServ.findAll());;
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    
	    Patient loggedInPatient = patientServ.getOne(patientId);
	    PregnancyFertilityAssessment pregnancyFertilityAssessmentToEdit = pregnancyFertilityAssessmentServ.getOne(id);

		model.addAttribute("loggedInPatient", loggedInPatient);
	    // Check if the logged-in physician is associated with the pregnancyFertilityAssessment
	    if (pregnancyFertilityAssessmentToEdit.getPatient().getId().equals(physicianId)) {

			// Add formatted dates to the model
			model.addAttribute("dayCreatedAt", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
			model.addAttribute("dayCurrentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		    // Add today's date to the model
		    model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
			model.addAttribute("createdAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	        model.addAttribute("pregnancyFertilityAssessment", pregnancyFertilityAssessmentToEdit);
	        return "PregnancyFertilityAssessments/editPregnancyFertilityAssessment.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/pregnancyFertilityAssessments";
	    }
	}

	@PatchMapping("/process/pregnancyFertilityAssessments/edit/{id}")
	public String processEditPregnancyFertilityAssessment(@Valid @ModelAttribute("pregnancyFertilityAssessment") PregnancyFertilityAssessment pregnancyFertilityAssessment,
		Model model, HttpSession session, BindingResult result) {
	    if (result.hasErrors()) {
	        // Add necessary model attributes for rendering the form with validation errors
	        Long physicianId = (Long) session.getAttribute("physician_id");
			Long patientId = (Long) session.getAttribute("patient_id");

			// Null Pointers
			if (physicianId == null){
		    } 

		    if (patientId == null) {
		        return "redirect:" + PATIENT_LOGIN_PATH;
		    }
		    
	        List<Integer> timeFormat = new ArrayList<>();
	          	for (int i = 1; i <= 12; i++) {
	            		timeFormat.add(i);
	           	}

	            // Add other necessary attributes to the model
	        model.addAttribute("timeFormat", timeFormat);
            model.addAttribute("loggedInPatient", physicianServ.getOne(physicianId));

			// Add formatted dates to the model
    	    patientUtil.setPatientAttributes(model);
    	    patientUtil.sortLoggedPatientAttributes(model, patientId);
    	    patientUtil.setLoggedInPatientDateAttributes(patientId, model);

            // Return the view with the model attributes
            return "PregnancyFertilityAssessments/editPregnancyFertilityAssessment.jsp";
	       
	    } else {
	        // Validation passed, update the pregnancyFertilityAssessment
	        pregnancyFertilityAssessmentServ.update(pregnancyFertilityAssessment);
	        return "redirect:/mellowHealth/hospitalDashboard/pregnancyFertilityAssessments/{id}";
	    }
	}
	
	@DeleteMapping("/pregnancyFertilityAssessments/delete/{id}")
	public String deletePregnancyFertilityAssessment(@PathVariable("id") Long id, HttpSession session) {
	    Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (physicianId == null){
	    } 

	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    PregnancyFertilityAssessment pregnancyFertilityAssessmentToDelete = pregnancyFertilityAssessmentServ.getOne(id);

	    // Check if the logged-in physician is the owner of the pregnancyFertilityAssessment
	    if (pregnancyFertilityAssessmentToDelete != null && pregnancyFertilityAssessmentToDelete.getPatient().getId().equals(physicianId)) {
	        pregnancyFertilityAssessmentServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the physician is not the owner
	        return "redirect:/mellowHealth/physicians";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


}