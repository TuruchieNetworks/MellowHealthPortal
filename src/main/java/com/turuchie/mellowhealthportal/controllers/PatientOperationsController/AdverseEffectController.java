package com.turuchie.mellowhealthportal.controllers.PatientOperationsController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

import com.turuchie.mellowhealthportal.models.PatientOperations.AdverseEffect;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.AdverseEffectService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
import com.turuchie.mellowhealthportal.utils.PatientUtils;
import com.turuchie.mellowhealthportal.utils.ListConverterUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth")
public class AdverseEffectController {
	private static final String PHYSICIAN_LOGIN_PATH = "/mellowHealth/patients/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/hospitalDashboard/";
	private static final String PHYSICIAN_PATH = "/mellowHealth/patients";
	@Autowired
	private AdverseEffectService adverseEffectServ;
	
	@Autowired
	private PatientService patientServ;
	
	@Autowired
	private PatientUtils patientUtil;

	@Autowired
	public AdverseEffectController(AdverseEffectService adverseEffectServ,
		PatientService patientServ, PatientUtils patientUtil) {
		   this.patientServ = patientServ;
		   this.adverseEffectServ = adverseEffectServ;
		   this.patientUtil = patientUtil;
    }
	
	public AdverseEffectController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/adverseEffects")
	public String adverseEffectIndexPage(Model model, HttpSession session) {
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null){
	    	return "redirect:" + PHYSICIAN_LOGIN_PATH;
	    } 
		Patient loggedInPatient = patientServ.getOne(patientId);

	    if (loggedInPatient == null) {
	        return "redirect:" + PHYSICIAN_LOGIN_PATH;
	    }		

		patientUtil.setPatientAttributes(model);
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    patientUtil.sortLoggedPatientAttributes(model, patientId);
		return "AdverseEffects/viewAllAdverseEffects.jsp";
	}

	@GetMapping("/adverseEffects/{id}")
	public String showOneAdverseEffect(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	    	return "redirect:" + PHYSICIAN_LOGIN_PATH;
	    }
		Patient loggedInPatient = patientServ.getOne(patientId);

	    if (loggedInPatient == null) {
	        return "redirect:" + PHYSICIAN_LOGIN_PATH;
	    }

	    AdverseEffect oneAdverseEffect = adverseEffectServ.getOne(id);
	    oneAdverseEffect.getCreatedAt();

	    // Add formatted dates to the model
		model.addAttribute("loggedInPatient", loggedInPatient);
		model.addAttribute("allAdverseEffects", adverseEffectServ.getAll());
		model.addAttribute("adverseEffect", adverseEffectServ.getOne(id));

		// Add Current Dates and Formatted Created At Dates To Model
		patientUtil.setPatientAttributes(model);
		patientUtil.addCoverageLengthToModel(model, oneAdverseEffect.getCreatedAt());
		
	    // Format dates
		model.addAttribute("dayCreatedAt", adverseEffectServ.getOne(id).getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	    model.addAttribute("createdAt", adverseEffectServ.getOne(id).getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		return "AdverseEffects/viewOneAdverseEffect.jsp";
	}

	@GetMapping("/adverseEffects/newAdverseEffect")
	public String createAdverseEffect(
	        @ModelAttribute("adverseEffect") AdverseEffect adverseEffect,
	        @RequestParam(value = "searchedPatientName", required = false) String searchedPatientName,
	        Model model,
	        HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null ) {
	        return "redirect:" + PHYSICIAN_LOGIN_PATH;
	    }

	    String trimmedSearchTerm = searchedPatientName != null ? searchedPatientName.trim() : null;

	    if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
	        // If a non-empty search value is provided
	    	patientUtil.addSearchedMethods(model, trimmedSearchTerm);
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
		patientUtil.setPatientAttributes(model);
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("timeFormat", generateTimeFormatList());

		// Add today's date to the model
		model.addAttribute("dayCreatedAt",adverseEffect.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		model.addAttribute("createdAt", adverseEffect.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));

	    return "/AdverseEffects/createAdverseEffect.jsp";
	}

	@PostMapping("/process/adverseEffects/createNewAdverseEffect")
	public String processCreateAdverseEffect(Model model, @Valid @ModelAttribute("adverseEffect") AdverseEffect newAdverseEffect,	    
	    BindingResult result, HttpSession session, RedirectAttributes redirectAttributes) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    // Redirect to login if patientId is null
	    if (patientId == null) {
	        return "redirect:" + PHYSICIAN_LOGIN_PATH;
	    }
		    // Add necessary model attributes for rendering the form with validation errors
	    if (result.hasErrors()) {
	        Patient loggedInPatient = patientServ.getOne(patientId);
		    model.addAttribute("loggedInPatient", loggedInPatient);
		    model.addAttribute("allPatients", patientServ.findAll());
		    model.addAttribute("timeFormat", generateTimeFormatList());
		    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
	        return "/AdverseEffects/createAdverseEffect.jsp";
	    }
		    
	    // Add flash attribute for success message
	    adverseEffectServ.create(newAdverseEffect);
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");
	    
	    // Redirect to the patient's page after successful adverseEffect creation
	    return "redirect:" + PHYSICIAN_PATH +"/"+ patientId;
	}

	@GetMapping("/adverseEffects/edit/{id}")
	public String editAdverseEffect(@PathVariable("id") Long id,
		@ModelAttribute("inputCollector") ListConverterUtil inputCollector,
		Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	        return "redirect:" + PHYSICIAN_LOGIN_PATH;
	    }
	    
	    Patient loggedInPatient = patientServ.getOne(patientId);
	    AdverseEffect adverseEffectToEdit = adverseEffectServ.getOne(id);

	    model.addAttribute("timeFormat", generateTimeFormatList());
		model.addAttribute("loggedInPatient", loggedInPatient);
	    // Check if the logged-in patient is associated with the adverseEffect
	    if (adverseEffectToEdit.getPatient().getId().equals(patientId)) {

			// Add formatted dates to the model
			model.addAttribute("dayCreatedAt", adverseEffectToEdit.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
			model.addAttribute("dayCurrentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		    // Add today's date to the model
		    model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
			model.addAttribute("createdAt", adverseEffectToEdit.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	        model.addAttribute("adverseEffect", adverseEffectToEdit);
	        return "AdverseEffects/editAdverseEffect.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/adverseEffects";
	    }
	}

	@PatchMapping("/process/adverseEffects/edit/{id}")
	public String processEditAdverseEffect(@Valid @ModelAttribute("adverseEffect") AdverseEffect adverseEffect,
		Model model, HttpSession session, BindingResult result) {
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
				model.addAttribute("dayCreatedAt", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
				model.addAttribute("dayCurrentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
			    // Add today's date to the model
			    model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
				model.addAttribute("createdAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));

	            // Return the view with the model attributes
	            return "AdverseEffects/editAdverseEffect.jsp";
	        } else {
	            // Handle the case where patientId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PHYSICIAN_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the adverseEffect
	        adverseEffectServ.update(adverseEffect);
	        return "redirect:/mellowHealth/hospitalDashboard/adverseEffects/{id}";
	    }
	}
	
	@DeleteMapping("/adverseEffects/delete/{id}")
	public String deleteAdverseEffect(@PathVariable("id") Long id, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Redirect to login if patientId is null
	    if (patientId == null) {
	        return "redirect:" + PHYSICIAN_LOGIN_PATH;
	    }

	    AdverseEffect adverseEffectToDelete = adverseEffectServ.getOne(id);

	    // Check if the logged-in patient is the owner of the adverseEffect
	    if (adverseEffectToDelete != null && adverseEffectToDelete.getPatient().getId().equals(patientId)) {
	        adverseEffectServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the patient is not the owner
	        return "redirect:/mellowHealth/patients";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PHYSICIAN_PATH + "/" + patientId;
	}


}