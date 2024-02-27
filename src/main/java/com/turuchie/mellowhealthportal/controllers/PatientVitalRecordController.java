package com.turuchie.mellowhealthportal.controllers;

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

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientVitalRecord;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PatientService;
import com.turuchie.mellowhealthportal.services.PatientVitalRecordService;
import com.turuchie.mellowhealthportal.utils.PatientUtils;
import com.turuchie.mellowhealthportal.utils.UtilInputConverter;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth/hospitalDashboard")
public class PatientVitalRecordController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/hospitalDashboard/";
	private static final String PATIENT_PATH = "/mellowHealth/patientPortals";
	@Autowired
	private PatientVitalRecordService patientVitalRecordServ;

	@Autowired
	private PatientUtils patientUtil;

	@Autowired
	private PatientService physicianServ;

	@Autowired
	public PatientVitalRecordController(PatientVitalRecordService patientVitalRecordServ,
		PatientService physicianServ,PatientService patientServ,PatientUtils patientUtil) {
		   this.patientUtil = patientUtil;
		   this.physicianServ = physicianServ;
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
	public String patientVitalRecordIndexPage(Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		if (patientId == null){
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    } 

		if (physicianId == null){
	    } 

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    patientUtil.setLoggedInPatientDateAttributes(patientId, model);
		return "PatientVitalRecords/viewAllPatientVitalRecords.jsp";
	}

	@GetMapping("/patientVitalRecords/{id}")
	public String showOnePatientVitalRecord(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		if (physicianId == null) {
	    }

	    Patient loggedInPatient = physicianServ.getOne(patientId);

	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    PatientVitalRecord onePatientVitalRecord = patientVitalRecordServ.getOne(id);	
		model.addAttribute("patientVitalRecord", patientVitalRecordServ.getOne(id));

		// Add formatted dates to the model
		model.addAttribute("dayCreatedAt", onePatientVitalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		model.addAttribute("createdAt", onePatientVitalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));	

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    patientUtil.setLoggedInPatientDateAttributes(patientId, model);
		return "PatientVitalRecords/viewOnePatientVitalRecord.jsp";
	}

	@GetMapping("/patientVitalRecords/newPatientVitalRecord")
	public String createPatientVitalRecord(
	    @ModelAttribute("patientVitalRecord") PatientVitalRecord patientVitalRecord,
	    @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	    Model model,HttpSession session) {
	    Long physicianId = (Long) session.getAttribute("physician_id");
	    Long patientId = (Long) session.getAttribute("patient_id");

	    if (patientId == null) {
	         return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    if (physicianId == null) {
	    }

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;

	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	patientUtil.addSearchedMethods(model, trimmedSearchTerm);
	    }

	    Patient loggedInPhysician = physicianServ.getOne(physicianId);

	    model.addAttribute("loggedInPhysician", loggedInPhysician);
	    model.addAttribute("timeFormat", generateTimeFormatList());

	    patientUtil.setPatientAttributes(model);
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

		    // Add necessary model attributes for rendering the form with validation errors
		    if (result.hasErrors()) {
			    patientUtil.setPatientAttributes(model);

			    Patient loggedInPatient = physicianServ.getOne(patientId);
			    model.addAttribute("loggedInPatient", loggedInPatient);
			    model.addAttribute("timeFormat", generateTimeFormatList());
			    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
		        return "/PatientVitalRecords/createPatientVitalRecord.jsp";
		    }

	    // Continue processing if there are no validation errors
		//	patientVitalRecord.setPatient(physicianServ.getOne(physicianId));
	    patientVitalRecordServ.create(newPatientVitalRecord);
	    // Add flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");
	    
	    // Redirect to the physician's page after successful patientVitalRecord creation
	    return "redirect:" + PATIENT_PATH +"/"+ physicianId;
	}

	@GetMapping("/patientVitalRecords/edit/{id}")
	public String editPatientVitalRecord(@PathVariable("id") Long id,
		@ModelAttribute("inputCollector") UtilInputConverter inputCollector,
		Model model, HttpSession session) {
	    Long physicianId = (Long) session.getAttribute("physician_id");
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Null Pointers
	    if (physicianId == null) {
	    }

	    // Null Pointers
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    patientUtil.setPatientAttributes(model);
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    
	    Patient loggedInPatient = physicianServ.getOne(patientId);
	    PatientVitalRecord patientVitalRecordToEdit = patientVitalRecordServ.getOne(id);

		model.addAttribute("loggedInPatient", loggedInPatient);
	    // Check if the logged-in physician is associated with the patientVitalRecord
	    if (patientVitalRecordToEdit.getPatient().getId().equals(physicianId)) {

			// Add formatted dates to the model
			model.addAttribute("dayCurrentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		    // Add today's date to the model
		    model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	        model.addAttribute("patientVitalRecord", patientVitalRecordToEdit);
	        return "PatientVitalRecords/editPatientVitalRecord.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/patientVitalRecords";
	    }
	}

	@PatchMapping("/process/patientVitalRecords/edit/{id}")
	public String processEditPatientVitalRecord(@Valid @ModelAttribute("patientVitalRecord") PatientVitalRecord patientVitalRecord,
		Model model, HttpSession session, BindingResult result) {
	    if (result.hasErrors()) {
	        // Add necessary model attributes for rendering the form with validation errors
	        Long patientId = (Long) session.getAttribute("physician_id");
	        if (patientId != null) {
	            List<Integer> timeFormat = new ArrayList<>();
	            for (int i = 1; i <= 12; i++) {
	                timeFormat.add(i);
	            }

	            // Add other necessary attributes to the model
	            model.addAttribute("timeFormat", timeFormat);
				
	            // Add formatted dates to the model
	            patientUtil.setPatientAttributes(model);
	    	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    	    patientUtil.setLoggedInPatientDateAttributes(patientId, model);


	            // Return the view with the model attributes
	            return "PatientVitalRecords/editPatientVitalRecord.jsp";
	        } else {
	            // Handle the case where physicianId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PATIENT_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the patientVitalRecord
	        patientVitalRecordServ.update(patientVitalRecord);
	        return "redirect:/mellowHealth/hospitalDashboard/patientVitalRecords/{id}";
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
	        return "redirect:/mellowHealth/physicians";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


}