package com.turuchie.mellowhealthportal.controllers.PatientOperationsController;

import java.time.LocalDateTime;
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

import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.models.PatientOperations.PatientAddress;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.InsuranceInformationService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientAddressService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
import com.turuchie.mellowhealthportal.utils.ApacheApiHttpClientUtils;
import com.turuchie.mellowhealthportal.utils.ListConverterUtil;
import com.turuchie.mellowhealthportal.utils.PatientFilterUtil;
import com.turuchie.mellowhealthportal.utils.PatientUtils;
import com.turuchie.mellowhealthportal.utils.SearchUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth")
public class PatientAddressController {
	private static final String PATIENT_LOGIN_PATH = "/mellowHealth/patientsPortal/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/hospitalDashboard";
	private static final String PATIENT_PATH = "/mellowHealth/patientsPortal/patients";
	
	@Autowired
	private SearchUtil searchUtil;
	
	@Autowired
	private PatientUtils patientUtil;
	
	@Autowired
	private PatientFilterUtil filterUtil;

	@Autowired
	private PatientService patientServ;

	@Autowired
	private PatientAddressService patientAddressServ;

	@Autowired
	private ApacheApiHttpClientUtils apacheApiHttpClientUtils;

	@Autowired
	public PatientAddressController(PatientAddressService patientAddressServ, ApacheApiHttpClientUtils apacheApiHttpClientUtils,
		   PatientUtils patientUtil, PhysicianService physicianServ, PatientService patientServ,
		   SearchUtil searchUtil, InsuranceInformationService insuranceInformationServ) {
		        this.patientUtil = patientUtil;
		        this.searchUtil = searchUtil;
		        this.patientServ = patientServ;
		        this.patientAddressServ = patientAddressServ;
		        this.apacheApiHttpClientUtils = apacheApiHttpClientUtils;
    }
	
	public PatientAddressController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/patientAddresses")
	public String patientAddressIndexPage(@RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
		@RequestParam(value = "patientAddressId", required = false) Long patientAddressId,
	    Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Null Pointers
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }
	    // Null Pointers
	    if (patientAddressId == null) {
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);

	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

        model.addAttribute("allPatientAddressesWithFilter", patientAddressServ.getAll());
	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
	        model.addAttribute("searchedPatient",searchUtil.searchByCharacterMethod(model, trimmedSearchTerm));
	        model.addAttribute("allPatientsWithFilter", searchUtil.searchPatientsByCharacters(trimmedSearchTerm));
	        model.addAttribute("searchedPatientAddress", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allPatientAddressesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display patient cases
	        model.addAttribute("allPatientAddressesWithFilter", Collections.emptyList());
	    }

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);

	    model.addAttribute("loggedInPatient", loggedInPatient);
	    return "PatientAddresses/viewAllPatientAddresses.jsp";
	}

	@GetMapping("/patientAddresses/{id}")
	public String showOnePatientAddress(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    if (physicianId == null) {
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    PatientAddress onePatientAddress = patientAddressServ.getOne(id);
	    String patientName = onePatientAddress.getPatient().getPatientFirstName();

	    if (patientName == null || onePatientAddress == null) {
	    }

	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    // Feed Search Before Modification
        model.addAttribute("allPatientAddressesWithFilter",searchUtil.returnSearchPatientCaseByCharacter(patientName));
	    String trimmedSearchTerm = patientName != null ? patientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	searchUtil.searchPatientCaseByCharacter(model, trimmedSearchTerm);
	        model.addAttribute("searchedPatient",searchUtil.searchByCharacterMethod(model, trimmedSearchTerm));
	        model.addAttribute("allPatientsWithFilter", searchUtil.searchPatientsByCharacters(trimmedSearchTerm));
	        model.addAttribute("searchedPatientAddress", searchUtil.returnFirstPatientCaseByCharacter(trimmedSearchTerm));
	        model.addAttribute("allPatientAddressesWithFilter", searchUtil.returnSearchPatientCaseByCharacter(trimmedSearchTerm));
	    } else {
	        // If the search bar is empty, do not display patient cases
	        model.addAttribute("allPatientAddressesWithFilter", Collections.emptyList());
	    }
		// Add formatted dates to the model
		patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);

	    // Get the converted LocalDateTime using the utility method
	    LocalDateTime convertedLocalDateTime = patientUtil.convertDateToLocalDateTime(onePatientAddress.getCreatedAt());
        int accountHistory = filterUtil.calculateDaysLocaleDateTimeDiffference(convertedLocalDateTime, LocalDateTime.now());

        // Date Formatting
        model.addAttribute("oneAccountHistory", accountHistory);
		model.addAttribute("onePatientAddress", onePatientAddress);
		model.addAttribute("dayCreatedAt", convertedLocalDateTime.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		model.addAttribute("createdAt", convertedLocalDateTime.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));	
		return "PatientAddresses/viewOnePatientAddress.jsp";
	}

	@GetMapping("/patientAddresses/newPatientAddress")
	public String createPatientAddress(@ModelAttribute("patientAddress") PatientAddress patientAddress,
	    @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	    Model model, HttpSession session) {
	    Long physicianId = (Long) session.getAttribute("physician_id");
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Null Pointers
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    if (physicianId == null) {
	        // Handle physicianId being null, if needed
	    }

	    if (searchedPatientName == null) {
	        // Handle searchedPatientName being null, if needed
	    }

	    // Fetch country codes using ApacheApiHttpClientUtils
	    List<String> countryCodes = apacheApiHttpClientUtils.getCountryCodes();

	    // Add country codes to the model
	    model.addAttribute("countryCodes", countryCodes);

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;
	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	patientUtil.addMatchedPatientCommonAttributeLists(model, trimmedSearchTerm);
	    	patientUtil.addOneMatchedPatientCommonSearchAttribute(model, trimmedSearchTerm);
	        List<Patient> matchedPatients = searchUtil.searchByCharacterMethod(model, trimmedSearchTerm);
	        // Check if no patient is found
	        if (matchedPatients.isEmpty()) {
	            // Handle the case where no patient is found, e.g., redirect or show a message
	            return "/PatientAddresses/createPatientAddress.jsp"; // Replace with your logic
	        }		

		    Patient loggedInPatient = patientServ.getOne(patientId);
		    model.addAttribute("loggedInPatient", loggedInPatient);
			patientUtil.setPatientAttributes(model);
		    patientUtil.setLoggedInPatientDateAttributes(patientId, model);
		    patientUtil.setLoggedPatientCommonAttributes(model, loggedInPatient, null);
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    if (loggedInPatient == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    // Format the LocalDateTime objects
	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    return "/PatientAddresses/createPatientAddress.jsp";
	}

	@PostMapping("/process/patientAddresses/createNewPatientAddress")
	public String processCreatePatientAddress(
        @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
        @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
        @RequestParam(value = "patient", required = false) Long onePatientId,
        @Valid @ModelAttribute("patientAddress") PatientAddress newPatientAddress,
        BindingResult result, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Null Pointer check for patientId
	    if (patientId == null) {
	        return "redirect:" + PATIENT_LOGIN_PATH;
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    Patient searchedPatient = newPatientAddress.getPatient();


	    // Null checkers
	    if (loggedInPatient == null || searchedPatient == null) {
	    }

	    patientUtil.setPatientAttributes(model);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
    	patientUtil.addOneMatchedPatientCommonSearchAttribute(model, searchedPatient.getPatientFirstName());
    	patientUtil.addMatchedPatientCommonAttributeLists(model, searchedPatient.getPatientFirstName());

	    // Combine null checks plus errors
	    if (result.hasErrors()) {
		    patientUtil.setPatientAttributes(model);
		    patientUtil.sortLoggedPatientAttributes(model, patientId);
	        // Add necessary model attributes for rendering the form with validation errors
	        model.addAttribute("loggedInPatient", loggedInPatient);
	        model.addAttribute("timeFormat", generateTimeFormatList());
	    	patientUtil.addMatchedPatientCommonAttributeLists(model, loggedInPatient.getPatientFirstName());
	        return "/PatientAddresses/createPatientAddress.jsp";
	    }

	    newPatientAddress.setPhoneNumber("+1-" + patientUtil.formatContactNumber(phoneNumber));
	    patientAddressServ.create(newPatientAddress);

	    // Flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient Address created successfully!");

	    // Redirect to the physician's page after successful patientAddress creation
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


	@GetMapping("/patientAddresses/editPatientAddress/{id}")
	public String editPatientAddress(@PathVariable("id") Long id,
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
	    PatientAddress patientAddressToEdit = patientAddressServ.getOne(id);

	    model.addAttribute("timeFormat", generateTimeFormatList());
		model.addAttribute("loggedInPatient", loggedInPatient);
	    searchUtil.searchByCharacterMethod(model, patientAddressToEdit.getPatient().getPatientFirstName());
	    searchUtil.searchPatientInsuranceByCharacter(model, patientAddressToEdit.getPatient().getPatientFirstName());

	    // Check if the logged-in physician is associated with the patientAddress
	    if (patientAddressToEdit.getPatient().getId().equals(patientId)) {
			patientUtil.setPatientAttributes(model);
	        model.addAttribute("patientAddress", patientAddressToEdit);
	        return "PatientAddresses/editOnePatientAddress.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/patientAddresses";
	    }
	}

	@PatchMapping("/process/patientAddresses/editPatientAddress/{id}")
	public String processEditPatientAddress(@Valid @ModelAttribute("patientAddress") PatientAddress patientAddress,
		BindingResult result, Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
		
	    if (result.hasErrors()) {
			patientUtil.setPatientAttributes(model);
	        model.addAttribute("loggedInPatient", patientServ.getOne(patientId));
	        // Add necessary model attributes for rendering the form with validation errors

	        // Model Attributes
	        if (patientId != null) {
			    model.addAttribute("timeFormat", generateTimeFormatList());
	            searchUtil.searchByCharacterMethod(model, patientAddress.getPatient().getPatientFirstName());
	            searchUtil.searchPatientInsuranceByCharacter(model, patientAddress.getPatient().getPatientFirstName());
	            // Direct Patient id way: searchUtil.searchPatientInsuranceByCharacter(model, patientServ.getOne(patientId).getPatientFirstName());

	            // Return the view with the model attributes
	            return "PatientAddresses/editOnePatientAddress.jsp";
	        } else {
	            // Handle the case where physicianId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PATIENT_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the patientAddress
	        patientAddressServ.update(patientAddress);
	        return "redirect:/mellowHealth/patientAddresses/{id}";
	    }
	}
	
	@DeleteMapping("/patientAddresses/deletePatientAddress/{id}")
	public String deletePatientAddress(@PathVariable("id") Long id, HttpSession session) {
		Long physicianId = (Long) session.getAttribute("physician_id");
		Long patientId = (Long) session.getAttribute("patient_id");

		// Null Pointers
		if (patientId == null){
	    	return "redirect:" + PATIENT_LOGIN_PATH;
	    } 

	    if (physicianId == null){
	    } 

	    PatientAddress patientAddressToDelete = patientAddressServ.getOne(id);
	    // Check if the logged-in physician is the owner of the patientAddress
	    if (patientAddressToDelete != null && patientAddressToDelete.getPatient().getId().equals(patientId)) {
	        patientAddressServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the physician is not the owner
	        return "redirect:/mellowHealth/patientsPortal/patients";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PATIENT_PATH + "/" + patientId;
	}


}