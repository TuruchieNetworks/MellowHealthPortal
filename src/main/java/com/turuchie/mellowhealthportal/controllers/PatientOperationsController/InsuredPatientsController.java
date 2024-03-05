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

import com.turuchie.mellowhealthportal.models.Administrations.InsuredPatients;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.InsuredPatientsService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
import com.turuchie.mellowhealthportal.utils.InsuranceUtil;
import com.turuchie.mellowhealthportal.utils.PatientUtils;
import com.turuchie.mellowhealthportal.utils.ListConverterUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth")
public class InsuredPatientsController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/patientsPortal/patients";
	private static final String PATIENT_PATH = "/mellowHealth/patientsPortal/patients";
	@Autowired
	private InsuredPatientsService insuredPatientsServ;

	@Autowired
	private PatientService patientServ;
	
	@Autowired
	private PatientUtils patientUtil;
	
	@Autowired
	private InsuranceUtil insuranceUtil;

	@Autowired
	public InsuredPatientsController(InsuredPatientsService insuredPatientsServ,
		InsuranceUtil insuranceUtil,PatientUtils patientUtil, PatientService patientServ) {
		this.patientUtil = patientUtil;
		this.patientServ = patientServ;
		this.insuranceUtil = insuranceUtil;
		this.insuredPatientsServ = insuredPatientsServ;
    }
	
	public InsuredPatientsController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/insuredPatients")
	public String insuredPatientsIndexPage(Model model, HttpSession session) {
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null){
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);

	    // Null Pointer
	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

		// Add formatted dates to the model
		patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
		patientUtil.setLoggedInPatientAgeAttributes(model, loggedInPatient);
		patientUtil.setLoggedPatientCommonAttributes(model, loggedInPatient, null);

		return "InsuranceRecords/viewAllInsuredPatients.jsp";
	}

	@GetMapping("/insuredPatients/{id}")
	public String showOneInsuredPatients(@PathVariable("id") Long id, Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    InsuredPatients oneInsuredPatients = insuredPatientsServ.getOne(id);
	    Patient loggedInPatient = patientServ.getOne(patientId);

	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    // Format dates
	    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	    String createdAtDate = oneInsuredPatients.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	    model.addAttribute("createdAt", createdAtDate);
	    model.addAttribute("currentDate", currentDate);
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("oneInsuredPatients", oneInsuredPatients);

	    // Add formatted dates to the model 
	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    patientUtil.addCoverageLengthToModel(model, oneInsuredPatients.getInsuranceInformation().getExpirationDate());
	    patientUtil.addSpecificCoverageLengthToModel(model, oneInsuredPatients.getInsuranceInformation().getExpirationDate());
	    patientUtil.addCoverageLengthToModel(model, insuredPatientsServ.getOne(id).getInsuranceInformation().getExpirationDate());
		patientUtil.addSpecificCoverageLengthToModel(model, insuredPatientsServ.getOne(id).getInsuranceInformation().getExpirationDate());
		insuranceUtil.setMostRecentInsuranceReportProperty(model, id);

	    // Format dates
		model.addAttribute("dayCreatedAt", insuredPatientsServ.getOne(id).getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	    model.addAttribute("createdAt", insuredPatientsServ.getOne(id).getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	    return "InsuranceRecords/viewOneInsuredPatient.jsp";
	}

	@GetMapping("/insuredPatients/newInsuredPatient")
	public String createInsuredPatients(@ModelAttribute("insuredPatients") InsuredPatients insuredPatients,
	    @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	    Model model,HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null ) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;

	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	patientUtil.addOneMatchedPatientCommonSearchAttribute(model, trimmedSearchTerm);
	    	patientUtil.addMatchedPatientCommonAttributeLists(model, trimmedSearchTerm);
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("timeFormat", generateTimeFormatList());

	    patientUtil.setLoggedPatientCommonAttributes(model, loggedInPatient, null);
		patientUtil.setPatientAttributes(model);
	    patientUtil.setLoggedInPatientDateAttributes(patientId, model);

	    return "/InsuranceRecords/createNewInsuredPatient.jsp";
	}

	@PostMapping("/process/insuredPatients/createNewInsuredPatient")
	public String processCreateInsuredPatients(@RequestParam(value = "startDate", required = false) LocalDate startDate,
		@RequestParam(value = "expirationDate", required = false) LocalDate expirationDate, 
		@Valid @ModelAttribute("insuredPatients") InsuredPatients newInsuredPatients,	    
	    BindingResult result, HttpSession session, Model model,RedirectAttributes redirectAttributes) {
	    Long patientId = (Long) session.getAttribute("patient_id");
		    // Redirect to login if patientId is null
		    if (patientId == null) {
		        return "redirect:" + PATIENT_LOGIN_PATH;
		    }

		    // Add necessary model attributes for rendering the form with validation errors
		    if (result.hasErrors()) {
		        Patient loggedInPatient = patientServ.getOne(patientId);
			    model.addAttribute("loggedInPatient", loggedInPatient);
			    model.addAttribute("timeFormat", generateTimeFormatList());

				patientUtil.setPatientAttributes(model);
				patientUtil.setLoggedInPatientAgeAttributes(model, loggedInPatient);
				patientUtil.setLoggedPatientCommonAttributes(model, loggedInPatient, result);
				patientUtil.setLoggedInPatientDateAttributes(patientId, model);
			    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
		        return "/InsuranceRecords/createNewInsuredPatient.jsp";
		    }

	    // Continue processing if there are no validation errors
	    insuredPatientsServ.create(newInsuredPatients);
	    // Add flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");
	    
	    // Redirect to the patient's page after successful insuredPatients creation
	    return "redirect:" + PATIENT_PATH +"/"+ patientId;
	}

	@GetMapping("/insuredPatients/editInsuredPatient/{id}")
	public String editInsuredPatients(@PathVariable("id") Long id,
		@ModelAttribute("inputCollector") ListConverterUtil inputCollector,
		Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    
	    Patient loggedInPatient = patientServ.getOne(patientId);
	    InsuredPatients insuredPatientsToEdit = insuredPatientsServ.getOne(id);

		patientUtil.setPatientAttributes(model);
		patientUtil.setLoggedInPatientAgeAttributes(model, loggedInPatient);
		patientUtil.setLoggedPatientCommonAttributes(model, loggedInPatient, null);
		patientUtil.setLoggedInPatientDateAttributes(patientId, model);
	    // Check if the logged-in patient is associated with the insuredPatients
	    if (insuredPatientsToEdit.getPatient().getId().equals(patientId)) {

			// Add formatted dates to the model

			patientUtil.setPatientAttributes(model);
			patientUtil.setLoggedInPatientAgeAttributes(model, loggedInPatient);
			patientUtil.setLoggedPatientCommonAttributes(model, loggedInPatient, null);
			patientUtil.setLoggedInPatientDateAttributes(patientId, model);
	        model.addAttribute("insuredPatients", insuredPatientsToEdit);
	        return "InsuranceRecords/editOneInsuredPatient.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/insuredPatients";
	    }
	}

	@PatchMapping("/process/insuredPatients/editInsuredPatient/{id}")
	public String processEditInsuredPatients(@Valid @ModelAttribute("insuredPatients") InsuredPatients insuredPatients,
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
	            return "InsuranceRecords/editOneInsuredPatient.jsp";
	        } else {
	            // Handle the case where patientId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PATIENT_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the insuredPatients
	        insuredPatientsServ.update(insuredPatients);
	        return "redirect:/mellowHealth/insuredPatients/{id}";
	    }
	}
	
	@DeleteMapping("/insuredPatients/deleteInsuredPatient/{id}")
	public String deleteInsuredPatients(@PathVariable("id") Long id, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Redirect to login if patientId is null
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    InsuredPatients insuredPatientsToDelete = insuredPatientsServ.getOne(id);

	    // Check if the logged-in patient is the owner of the insuredPatients
	    if (insuredPatientsToDelete != null && insuredPatientsToDelete.getPatient().getId().equals(patientId)) {
	        insuredPatientsServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the patient is not the owner
	        return "redirect:/mellowHealth/patientsPortal/patients";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


}