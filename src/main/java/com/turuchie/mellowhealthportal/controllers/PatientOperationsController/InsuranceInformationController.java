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
import com.turuchie.mellowhealthportal.models.PatientOperations.InsuranceInformation;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.InsuranceInformationService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.InsuredPatientsService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
import com.turuchie.mellowhealthportal.utils.InsuranceUtil;
import com.turuchie.mellowhealthportal.utils.PatientUtils;
import com.turuchie.mellowhealthportal.utils.ListConverterUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth")
public class InsuranceInformationController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/patientsPortal/patients";
	private static final String PATIENT_PATH = "/mellowHealth/patientsPortal/patients";
	@Autowired
	private InsuranceInformationService insuranceInformationServ;

	@Autowired
	private InsuredPatientsService insuredPatientServ;

	@Autowired
	private PatientService patientServ;
	
	@Autowired
	private PatientUtils patientUtil;
	
	@Autowired
	private InsuranceUtil insuranceUtil;

	@Autowired
	public InsuranceInformationController(InsuranceInformationService insuranceInformationServ,
		InsuredPatientsService insuredPatientServ,InsuranceUtil insuranceUtil,
		PatientUtils patientUtil, PatientService patientServ) {
		this.patientServ = patientServ;
		this.patientUtil = patientUtil;
		this.insuranceUtil = insuranceUtil;
		this.insuredPatientServ = insuredPatientServ;
		this.insuranceInformationServ = insuranceInformationServ;
    }
	
	public InsuranceInformationController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/insuranceRecords")
	public String insuranceInformationIndexPage(Model model, HttpSession session) {
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

		return "InsuranceRecords/viewAllInsuranceRecords.jsp";
	}

	@GetMapping("/insuranceRecords/{id}")
	public String showOneInsuranceInformation(@PathVariable("id") Long id, Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    InsuranceInformation oneInsuranceInformation = insuranceInformationServ.getOne(id);

	    // Format dates
	    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	    String createdAtDate = oneInsuranceInformation.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	    model.addAttribute("currentDate", currentDate);
	    model.addAttribute("dayCurrentDate", createdAtDate);
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("oneInsuranceInformation", oneInsuranceInformation);

	    // Add formatted dates to the model 
	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    patientUtil.addCoverageLengthToModel(model, oneInsuranceInformation.getExpirationDate());
	    patientUtil.addSpecificCoverageLengthToModel(model, oneInsuranceInformation.getExpirationDate());
	    patientUtil.addCoverageLengthToModel(model, insuranceInformationServ.getOne(id).getExpirationDate());
		patientUtil.addSpecificCoverageLengthToModel(model, insuranceInformationServ.getOne(id).getExpirationDate());
		insuranceUtil.setMostRecentInsuranceReportProperty(model, id);

	    // Format dates
		model.addAttribute("dayCreatedAt", insuranceInformationServ.getOne(id).getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	    model.addAttribute("createdAt", insuranceInformationServ.getOne(id).getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	    return "InsuranceRecords/viewOneInsuranceRecord.jsp";
	}

	@GetMapping("/insuranceRecords/newInsuranceRecord")
	public String createInsuranceInformation(
	        @ModelAttribute("insuranceInformation") InsuranceInformation insuranceInformation,
	        @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	        Model model,
	        HttpSession session) {
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

	    return "/InsuranceRecords/createInsuranceRecord.jsp";
	}

	@PostMapping("/process/insuranceRecords/createNewInsuranceRecord")
	public String processCreateInsuranceInformation(@RequestParam(value = "startDate", required = false) LocalDate startDate,
		@RequestParam(value = "expirationDate", required = false) LocalDate expirationDate, 
		@Valid @ModelAttribute("insuranceInformation") InsuranceInformation newInsuranceInformation,	    
	    BindingResult result, HttpSession session, Model model,RedirectAttributes redirectAttributes) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    	// Redirect to login if patientId is null
		    if (patientId == null) {
		        return "redirect:" + PATIENT_LOGIN_PATH;
		    }

		    Patient loggedInPatient = patientServ.getOne(patientId);

		    // Validate Insurance Start Date
	        if (!patientUtil.isValidStartDate(startDate)) {
	            result.rejectValue("startDate", "error.startDate", "Invalid start date for insurance");
	        }

	        // Validate Insurance Expiration Date
	        if (!patientUtil.isValidExpirationDate(expirationDate)) {
	            result.rejectValue("expirationDate", "error.expirationDate", "Invalid expiration date for insurance");
	        }

		    // Add necessary model attributes for rendering the form with validation errors
		    if (result.hasErrors()) {
			    model.addAttribute("loggedInPatient", loggedInPatient);
			    model.addAttribute("timeFormat", generateTimeFormatList());

				patientUtil.setPatientAttributes(model);
			    patientUtil.sortLoggedPatientAttributes(model, patientId);
				patientUtil.setLoggedInPatientAgeAttributes(model, loggedInPatient);
				patientUtil.setLoggedPatientCommonAttributes(model, loggedInPatient, result);
			    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
		        return "/InsuranceRecords/createInsuranceRecord.jsp";
		    }

	    // Add flash attribute for success message
		InsuranceInformation newInsurance = insuranceInformationServ.create(newInsuranceInformation);
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");

	    // Continue processing if there are no validation errors
	    InsuredPatients newInsuredPatient = new InsuredPatients();

	    if (newInsuredPatient != null) {
		    newInsuredPatient.setPatient(loggedInPatient);
		    newInsuredPatient.setInsuranceInformation(newInsurance);
		    newInsuredPatient.setPatientName(newInsurance.getPatient().getPatientFirstName() + " " + newInsurance.getPatient().getPatientLastName());
		    
	    // Redirect to the patient's page after successful insuranceInformation creation
	    insuredPatientServ.create(newInsuredPatient);
	    }
	    return "redirect:" + PATIENT_PATH +"/"+ patientId;
	}

	@GetMapping("/insuranceRecords/editInsuranceRecord/{id}")
	public String editInsuranceInformation(@PathVariable("id") Long id,
		@ModelAttribute("inputCollector") ListConverterUtil inputCollector,
		Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    
	    Patient loggedInPatient = patientServ.getOne(patientId);
	    InsuranceInformation insuranceInformationToEdit = insuranceInformationServ.getOne(id);

		patientUtil.setPatientAttributes(model);
		patientUtil.setLoggedInPatientAgeAttributes(model, loggedInPatient);
		patientUtil.setLoggedPatientCommonAttributes(model, loggedInPatient, null);
		patientUtil.setLoggedInPatientDateAttributes(patientId, model);
	    // Check if the logged-in patient is associated with the insuranceInformation
	    if (insuranceInformationToEdit.getPatient().getId().equals(patientId)) {
			// Add formatted dates to the model
			patientUtil.setPatientAttributes(model);
		    patientUtil.sortLoggedPatientAttributes(model, patientId);
			patientUtil.setLoggedInPatientAgeAttributes(model, loggedInPatient);
			patientUtil.setLoggedPatientCommonAttributes(model, loggedInPatient, null);
	        model.addAttribute("insuranceInformation", insuranceInformationToEdit);
	        return "InsuranceRecords/editOneInsuranceRecord.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/insuranceRecords";
	    }
	}

	@PatchMapping("/process/insuranceRecords/editInsuranceRecord/{id}")
	public String processEditInsuranceInformation(@Valid @ModelAttribute("insuranceInformation") InsuranceInformation insuranceInformation,
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
	            return "InsuranceRecords/editOneInsuranceRecord.jsp";
	        } else {
	            // Handle the case where patientId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PATIENT_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the insuranceInformation
	        insuranceInformationServ.update(insuranceInformation);
	        return "redirect:/mellowHealth/insuranceRecords/{id}";
	    }
	}
	
	@DeleteMapping("/insuranceRecords/deleteInsuranceRecord/{id}")
	public String deleteInsuranceInformation(@PathVariable("id") Long id, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Redirect to login if patientId is null
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    InsuranceInformation insuranceInformationToDelete = insuranceInformationServ.getOne(id);

	    // Check if the logged-in patient is the owner of the insuranceInformation
	    if (insuranceInformationToDelete != null && insuranceInformationToDelete.getPatient().getId().equals(patientId)) {
	        insuranceInformationServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the patient is not the owner
	        return "redirect:/mellowHealth/patientsPortal/patients";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


}