package com.turuchie.mellowhealthportal.controllers.PatientOperationsController;

import java.time.LocalDate;
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

import com.turuchie.mellowhealthportal.models.PatientOperations.PastMedicalHistory;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PastMedicalHistoryService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
import com.turuchie.mellowhealthportal.utils.ModelAttributeUtil;
import com.turuchie.mellowhealthportal.utils.PatientUtils;
import com.turuchie.mellowhealthportal.utils.ListConverterUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth")
public class PastMedicalHistoryController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/patientsPortal/patients";
	private static final String PATIENT_PATH = "/mellowHealth/patientsPortal/patients";

	@Autowired
	private PastMedicalHistoryService pastMedicalHistoryServ;

	@Autowired
	private PatientService patientServ;
	
	@Autowired
	private PatientUtils patientUtil;

	@Autowired
	private ModelAttributeUtil modelUtil;


	@Autowired
	public PastMedicalHistoryController(PastMedicalHistoryService pastMedicalHistoryServ,
		PatientUtils patientUtil, PatientService patientServ,ModelAttributeUtil modelUtil) {
		this.patientServ = patientServ;
		this.patientUtil = patientUtil;
		this.modelUtil = modelUtil;
		this.pastMedicalHistoryServ = pastMedicalHistoryServ;
    }
	
	public PastMedicalHistoryController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/pastMedicalRecords")
	public String pastMedicalHistoryIndexPage(Model model, HttpSession session) {
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null){
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);

	    // Null Pointer
	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    patientUtil.setPatientAttributes(model);
		modelUtil.setCommonModelAttributes(model);
	    modelUtil.mapAllPastMedicalHistoryStartDateAttributes(model);
		patientUtil.sortLoggedPatientAttributes(model, patientId);
		patientUtil.setLoggedInPatientDateAttributes(patientId, model);
		patientUtil.setLoggedInPatientAgeAttributes(model, loggedInPatient);

		// Add Logged In Attributes to the model
		model.addAttribute("loggedInPatient", loggedInPatient);
		return "PastMedicalRecords/viewAllPastMedicalRecords.jsp";
	}

	@GetMapping("/pastMedicalRecords/{id}")
	public String showOnePastMedicalHistory(@PathVariable("id") Long id, Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);

	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    PastMedicalHistory onePastMedicalHistory = pastMedicalHistoryServ.getOne(id);

	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("medicalRecord", onePastMedicalHistory);
       
	    // Calculate the length of the medical condition
        int lengthOfMedicalRecord = patientUtil.calculateLengthOfMedicalCondition(onePastMedicalHistory.getCreatedAt());
	    model.addAttribute("onePastMedicalRecordLength", lengthOfMedicalRecord);

	    // Add formatted dates to the model
	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    patientUtil.setLoggedInPatientDateAttributes(patientId, model);
	    patientUtil.formatAndSetOnePastMedicalHistoryStartDateAttributes(model, id);
	    patientUtil.calculateLengthOfMedicalCondition(pastMedicalHistoryServ.getOne(id).getCreatedAt());

	    // Format dates
		model.addAttribute("dayCreatedAt", pastMedicalHistoryServ.getOne(id).getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	    model.addAttribute("createdAt", pastMedicalHistoryServ.getOne(id).getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	    return "PastMedicalRecords/viewOnePastMedicalRecord.jsp";
	}

	@GetMapping("/pastMedicalRecords/newPastMedicalRecord")
	public String createPastMedicalHistory(@ModelAttribute("pastMedicalHistory") PastMedicalHistory pastMedicalHistory,
        @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
        Model model,HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null ) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    Patient searchedPatient = patientServ.getOne(patientId);
 
	    patientUtil.addSearchedMethods(model, searchedPatient.getPatientFirstName());
	    
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	patientUtil.addSearchedMethods(model, trimmedSearchTerm);
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("timeFormat", generateTimeFormatList());

		patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    patientUtil.setLoggedInPatientDateAttributes(patientId, model);
	    patientUtil.setLoggedPatientCommonAttributes(model, loggedInPatient, null);

	    return "/PastMedicalRecords/createPastMedicalRecord.jsp";
	}

	@PostMapping("/process/pastMedicalRecords/createNewPastMedicalRecord")
	public String processCreatePastMedicalHistory(@RequestParam(value = "startDate", required = false) LocalDate startDate,
		@Valid @ModelAttribute("pastMedicalHistory") PastMedicalHistory newPastMedicalHistory,	    
	    BindingResult result, HttpSession session, Model model,RedirectAttributes redirectAttributes) {
	    Long patientId = (Long) session.getAttribute("patient_id");
		    // Redirect to login if patientId is null
		    if (patientId == null) {
		        return "redirect:" + PATIENT_LOGIN_PATH;
		    }
	        // Validate Insurance Start Date
		    if (!patientUtil.isValidBirthDateRegularPatient(startDate)){
	            result.rejectValue("startDate", "error.startDate", "Invalid Start Date For Medical Condition!");
	        }

		    // Add necessary model attributes for rendering the form with validation errors
		    if (result.hasErrors()) {
		        Patient loggedInPatient = patientServ.getOne(patientId);
			    model.addAttribute("loggedInPatient", loggedInPatient);
			    model.addAttribute("timeFormat", generateTimeFormatList());
			    
				patientUtil.setPatientAttributes(model);
			    patientUtil.sortLoggedPatientAttributes(model, patientId);
				patientUtil.setLoggedInPatientDateAttributes(patientId, model);
				patientUtil.setLoggedInPatientAgeAttributes(model, loggedInPatient);
				patientUtil.setLoggedPatientCommonAttributes(model, loggedInPatient, result);
			    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
		        return "/PastMedicalRecords/createPastMedicalRecord.jsp";
		    }

	    // Continue processing if there are no validation errors
	    pastMedicalHistoryServ.create(newPastMedicalHistory);
	    // Add flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");
	    
	    // Redirect to the patient's page after successful pastMedicalHistory creation
	    return "redirect:" + PATIENT_PATH +"/"+ patientId;
	}

	@GetMapping("/pastMedicalRecords/editPastMedicalRecord/{id}")
	public String editPastMedicalHistory(@PathVariable("id") Long id,
		@ModelAttribute("inputCollector") ListConverterUtil inputCollector,
		Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    
	    Patient loggedInPatient = patientServ.getOne(patientId);
	    PastMedicalHistory pastMedicalHistoryToEdit = pastMedicalHistoryServ.getOne(id);

		patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
		patientUtil.setLoggedInPatientDateAttributes(patientId, model);
		patientUtil.setLoggedInPatientAgeAttributes(model, loggedInPatient);
		patientUtil.setLoggedPatientCommonAttributes(model, loggedInPatient, null);

		// Check if the logged-in patient is associated with the pastMedicalHistory
	    if (pastMedicalHistoryToEdit.getPatient().getId().equals(patientId)) {

			// Add formatted dates to the model

			patientUtil.setPatientAttributes(model);
			patientUtil.setLoggedInPatientAgeAttributes(model, loggedInPatient);
			patientUtil.setLoggedPatientCommonAttributes(model, loggedInPatient, null);
			patientUtil.setLoggedInPatientDateAttributes(patientId, model);
	        model.addAttribute("pastMedicalHistory", pastMedicalHistoryToEdit);
	        return "PastMedicalRecords/editOnePastMedicalRecord.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/pastMedicalRecords";
	    }
	}

	@PatchMapping("/process/pastMedicalRecords/editPastMedicalRecord/{id}")
	public String processEditPastMedicalHistory(@Valid @ModelAttribute("pastMedicalHistory") PastMedicalHistory pastMedicalHistory,
		BindingResult result,Model model, HttpSession session) {
	    if (result.hasErrors()) {
	        // Add necessary model attributes for rendering the form with validation errors
	        Long patientId = (Long) session.getAttribute("patient_id");
	        if (patientId != null) {
	            List<Integer> timeFormat = new ArrayList<>();
	            for (int i = 1; i <= 12; i++) {
	                timeFormat.add(i);
	            }

	            // Add other necessary attributes to the model
	            model.addAttribute("timeFormat", timeFormat);
	            model.addAttribute("loggedInPatient", patientServ.getOne(patientId));

				// Add formatted dates to the model
				model.addAttribute("dayCurrentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
			    // Add today's date to the model
			    model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));

	            // Return the view with the model attributes
	            return "PastMedicalRecords/editOnePastMedicalRecord.jsp";
	        } else {
	            // Handle the case where patientId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PATIENT_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the pastMedicalHistory
	        pastMedicalHistoryServ.update(pastMedicalHistory);
	        return "redirect:/mellowHealth/pastMedicalRecords/{id}";
	    }
	}
	
	@DeleteMapping("/pastMedicalRecords/deletePastMedicalRecord/{id}")
	public String deletePastMedicalHistory(@PathVariable("id") Long id, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Redirect to login if patientId is null
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    PastMedicalHistory pastMedicalHistoryToDelete = pastMedicalHistoryServ.getOne(id);

	    // Check if the logged-in patient is the owner of the pastMedicalHistory
	    if (pastMedicalHistoryToDelete != null && pastMedicalHistoryToDelete.getPatient().getId().equals(patientId)) {
	        pastMedicalHistoryServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the patient is not the owner
	        return "redirect:/mellowHealth/patientsPortal/patients";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}

}