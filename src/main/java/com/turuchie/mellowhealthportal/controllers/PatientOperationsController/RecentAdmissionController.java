package com.turuchie.mellowhealthportal.controllers.PatientOperationsController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.models.PatientOperations.RecentAdmission;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.RecentAdmissionService;
import com.turuchie.mellowhealthportal.utils.ListConverterUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mellowHealth/hospitalDashboard")
public class RecentAdmissionController {
	private static final String PHYSICIAN_LOGIN_PATH = "/mellowHealth/patients/login";
	private static final String HOSPITAL_DASHBOARD_PATH = "/mellowHealth/hospitalDashboard/";
	private static final String PHYSICIAN_PATH = "/mellowHealth/patients";
	@Autowired
	private RecentAdmissionService recentAdmissionServ;

	@Autowired
	private PatientService patientServ;

	@Autowired
	public RecentAdmissionController(RecentAdmissionService recentAdmissionServ,PatientService patientServ) {
		        this.patientServ = patientServ;
		        this.recentAdmissionServ = recentAdmissionServ;
		        this.patientServ = patientServ;
    }
	
	public RecentAdmissionController() {}

	private List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	@GetMapping("/recentAdmissions")
	public String recentAdmissionIndexPage(Model model, HttpSession session) {
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null){
	    	return "redirect:" + PHYSICIAN_LOGIN_PATH;
	    } 
		Patient loggedInPatient = patientServ.getOne(patientId);

	    if (loggedInPatient == null) {
	        return "redirect:" + PHYSICIAN_LOGIN_PATH;
	    }		
		model.addAttribute("loggedInPatient", loggedInPatient);
		model.addAttribute("allRecentAdmissions", recentAdmissionServ.getAll());
		model.addAttribute("allPatients", patientServ.findAll());

		// Add formatted dates to the model
		model.addAttribute("dayCreatedAt", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		model.addAttribute("dayCurrentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	    // Add today's date to the model
	    model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		model.addAttribute("createdAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		return "RecentAdmissions/viewAllRecentAdmissions.jsp";
	}

	@GetMapping("/recentAdmissions/{id}")
	public String showOneRecentAdmission(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	    	return "redirect:" + PHYSICIAN_LOGIN_PATH;
	    }
		Patient loggedInPatient = patientServ.getOne(patientId);

	    if (loggedInPatient == null) {
	        return "redirect:" + PHYSICIAN_LOGIN_PATH;
	    }
	    RecentAdmission oneRecentAdmission = recentAdmissionServ.getOne(id);
	    model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	    oneRecentAdmission.getCreatedAt();
		model.addAttribute("createdAt", oneRecentAdmission.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));		
		model.addAttribute("loggedInPatient", loggedInPatient);
		model.addAttribute("allRecentAdmissions", recentAdmissionServ.getAll());
		model.addAttribute("recentAdmission", recentAdmissionServ.getOne(id));

		// Add formatted dates to the model
		model.addAttribute("dayCreatedAt", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		model.addAttribute("dayCurrentDateTime", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	    // Add today's date to the model
	    model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		model.addAttribute("createdAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		// Additional attributes for seconds, minutes, hours, and days
		model.addAttribute("currentSecond", LocalTime.now().getSecond());
		model.addAttribute("currentMinute", LocalTime.now().getMinute());
		model.addAttribute("currentHour", LocalTime.now().getHour());
		model.addAttribute("currentDayOfYear", LocalDate.now().getDayOfYear());
		return "RecentAdmissions/viewOneRecentAdmission.jsp";
	}

	@GetMapping("/recentAdmissions/newRecentAdmission")
	public String createRecentAdmission(
	        @ModelAttribute("recentAdmission") RecentAdmission recentAdmission,
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
	        Patient matchedPatientFirstName = patientServ.getOnePatientFirstName(trimmedSearchTerm.toLowerCase());
	        Patient matchedPatientFullName = patientServ.getOnePatientByFullName(trimmedSearchTerm.toLowerCase());
	        Patient matchedPatients = patientServ.getPatientsByPartialName(trimmedSearchTerm.toLowerCase());
	        List<Patient> matchedSearchedPatients = patientServ.getAllPatientsMatchingSearchTerm(trimmedSearchTerm.toLowerCase());
	        model.addAttribute("matchedPatients", matchedPatients);
	        model.addAttribute("matchedSearchedPatients", matchedSearchedPatients);
	        model.addAttribute("matchedPatientFirstName", matchedPatientFirstName);
	        model.addAttribute("matchedPatientFullName", matchedPatientFullName);
	    }

	    Patient loggedInPatient = patientServ.getOne(patientId);
	    model.addAttribute("loggedInPatient", loggedInPatient);
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    model.addAttribute("allPatients", patientServ.findAll());
	    model.addAttribute("allPatients", patientServ.findAll());

		LocalDateTime createdAtDate = loggedInPatient.getCreatedAt();

		// Convert Date to LocalDateTime
		Instant instant = createdAtDate.toInstant(null);
		LocalDateTime createdAtLocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

		// Use now() to get the current date and time
		LocalDateTime currentDateTime = LocalDateTime.now();

		// Format the LocalDateTime objects
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy");
		DateTimeFormatter dayformatter = DateTimeFormatter.ofPattern("EEE, yyyy");
		String createdAtFormatted = createdAtLocalDateTime.format(formatter);
		String currentDateTimeFormatted = currentDateTime.format(formatter);
		String dayCreatedAtFormatted = createdAtLocalDateTime.format(dayformatter);
		String dayCurrentDateTimeFormatted = currentDateTime.format(dayformatter);

		// Add formatted dates to the model
		model.addAttribute("createdAt", createdAtFormatted);
		model.addAttribute("currentDateTime", currentDateTimeFormatted);
		model.addAttribute("dayCreatedAt", dayCreatedAtFormatted);
		model.addAttribute("dayCurrentDateTime", dayCurrentDateTimeFormatted);

		// Add formatted dates to the model
		model.addAttribute("dayCreatedAt", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	    // Add today's date to the model
		model.addAttribute("dayCurrentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	    // Add today's date to the model
	    model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	    recentAdmission.getCreatedAt();
		model.addAttribute("createdAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));

	    return "/RecentAdmissions/createRecentAdmission.jsp";
	}

	@PostMapping("/process/recentAdmissions/createNewRecentAdmission")
	public String processCreateRecentAdmission(Model model, @Valid @ModelAttribute("recentAdmission") RecentAdmission newRecentAdmission,	    
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
			    model.addAttribute("timeFormat", generateTimeFormatList());
			    model.addAttribute("allPatients", patientServ.findAll());
			    model.addAttribute("allPatients", patientServ.findAll());
			    redirectAttributes.addFlashAttribute("failureMessage", "Validation Failed While Creating Patient's Case!");
		        return "/RecentAdmissions/createRecentAdmission.jsp";
		    }

	    // Continue processing if there are no validation errors
		//	recentAdmission.setPatient(patientServ.getOne(patientId));
	    recentAdmissionServ.create(newRecentAdmission);
	    // Add flash attribute for success message
	    redirectAttributes.addFlashAttribute("successMessage", "Patient case created successfully!");
	    
	    // Redirect to the patient's page after successful recentAdmission creation
	    return "redirect:" + PHYSICIAN_PATH +"/"+ patientId;
	}

	@GetMapping("/recentAdmissions/edit/{id}")
	public String editRecentAdmission(@PathVariable("id") Long id,
		@ModelAttribute("inputCollector") ListConverterUtil inputCollector,
		Model model, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");
	    if (patientId == null) {
	        return "redirect:" + PHYSICIAN_LOGIN_PATH;
	    }
	    model.addAttribute("allPatients", patientServ.findAll());
	    model.addAttribute("allPatients", patientServ.findAll());
	    model.addAttribute("timeFormat", generateTimeFormatList());
	    
	    Patient loggedInPatient = patientServ.getOne(patientId);
	    RecentAdmission recentAdmissionToEdit = recentAdmissionServ.getOne(id);

		model.addAttribute("loggedInPatient", loggedInPatient);
	    // Check if the logged-in patient is associated with the recentAdmission
	    if (recentAdmissionToEdit.getPatient().getId().equals(patientId)) {

			// Add formatted dates to the model
			model.addAttribute("dayCreatedAt", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
			model.addAttribute("dayCurrentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		    // Add today's date to the model
		    model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
			model.addAttribute("createdAt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	        model.addAttribute("recentAdmission", recentAdmissionToEdit);
	        return "RecentAdmissions/editRecentAdmission.jsp";
	    } else {
	        return "redirect:" + HOSPITAL_DASHBOARD_PATH + "/recentAdmissions";
	    }
	}

	@PatchMapping("/process/recentAdmissions/edit/{id}")
	public String processEditRecentAdmission(@Valid @ModelAttribute("recentAdmission") RecentAdmission recentAdmission,
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
	            return "RecentAdmissions/editRecentAdmission.jsp";
	        } else {
	            // Handle the case where patientId is null (redirect to login, show an error, etc.)
	            return "redirect:" + PHYSICIAN_LOGIN_PATH;
	        }
	    } else {
	        // Validation passed, update the recentAdmission
	        recentAdmissionServ.update(recentAdmission);
	        return "redirect:/mellowHealth/hospitalDashboard/recentAdmissions/{id}";
	    }
	}
	
	@DeleteMapping("/recentAdmissions/delete/{id}")
	public String deleteRecentAdmission(@PathVariable("id") Long id, HttpSession session) {
	    Long patientId = (Long) session.getAttribute("patient_id");

	    // Redirect to login if patientId is null
	    if (patientId == null) {
	        return "redirect:" + PHYSICIAN_LOGIN_PATH;
	    }

	    RecentAdmission recentAdmissionToDelete = recentAdmissionServ.getOne(id);

	    // Check if the logged-in patient is the owner of the recentAdmission
	    if (recentAdmissionToDelete != null && recentAdmissionToDelete.getPatient().getId().equals(patientId)) {
	        recentAdmissionServ.delete(id);
	    } else {
	        // Redirect to the pmellowHealth/hysician'spage if the patient is not the owner
	        return "redirect:/mellowHealth/patients";
	    }

	    // Redirect to the pmellowHealth/hysician'spage after successful deletion
	    return "redirect:" + PHYSICIAN_PATH + "/" + patientId;
	}


}