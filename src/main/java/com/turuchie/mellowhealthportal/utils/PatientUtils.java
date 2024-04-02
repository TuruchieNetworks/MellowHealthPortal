package com.turuchie.mellowhealthportal.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.turuchie.mellowhealthportal.models.PatientOperations.InsuranceInformation;
import com.turuchie.mellowhealthportal.models.PatientOperations.PastMedicalHistory;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.PhysiciansPatientService;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientCaseService;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientVitalRecordService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.IncidentReportService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PastMedicalHistoryService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;

@Component
public class PatientUtils {
	@Autowired
	private PatientService patientServ;

	@Autowired
	private PhysicianService physicianServ;
	
	@Autowired
	private PhysiciansPatientService physiciansPatientServ;
	
	@Autowired
	private PatientCaseService patientCaseServ;
	
	@Autowired
	private IncidentReportService incidentReportServ;
	
	@Autowired
	private PatientVitalRecordService patientVitalRecordServ;

	@Autowired
	private PastMedicalHistoryService pastMedicalHistoryServ;

	@Autowired
	private ModelAttributeUtil modelUtil;

	@Autowired
	private PatientFilterUtil filterUtil;

	@Autowired
	private InsuranceUtil insuranceUtil;
	
	@Autowired
	private SearchUtil searchUtil;
	
	@Autowired
	private DiagnosticUtils diagnosticUtil;

    public PatientUtils(
            PatientService patientServ,
            PhysicianService physicianServ,
            PhysiciansPatientService physiciansPatientServ,
            PatientCaseService patientCaseServ,
            IncidentReportService incidentReportServ,
            PatientVitalRecordService patientVitalRecordServ,
            PastMedicalHistoryService pastMedicalHistoryServ,
            ModelAttributeUtil modelUtil,
            PatientFilterUtil filterUtil,
            DiagnosticUtils diagnosticUtil,
            InsuranceUtil insuranceUtil,
            SearchUtil searchUtil) {
        this.patientServ = patientServ;
        this.physicianServ = physicianServ;
        this.physiciansPatientServ = physiciansPatientServ;
        this.patientCaseServ = patientCaseServ;
        this.incidentReportServ = incidentReportServ;
        this.patientVitalRecordServ = patientVitalRecordServ;
        this.pastMedicalHistoryServ = pastMedicalHistoryServ;
        this.modelUtil = modelUtil;
        this.filterUtil = filterUtil;
        this.diagnosticUtil = diagnosticUtil;
        this.insuranceUtil = insuranceUtil;
        this.searchUtil =searchUtil;
    }

    // Helper method to set common patient attributes
    public void setCommonPatientAttributes(Patient existingPatient, Patient patientToEdit) {
        patientToEdit.setPatientFirstName(existingPatient.getPatientFirstName());
        patientToEdit.setPatientLastName(existingPatient.getPatientLastName());
        patientToEdit.setEmail(existingPatient.getEmail());
        patientToEdit.setPassword(existingPatient.getPassword());
        patientToEdit.setConfirmPassword(existingPatient.getConfirmPassword());
        patientToEdit.setDateOfBirth(existingPatient.getDateOfBirth());
        patientToEdit.setGender(existingPatient.getGender());
        patientToEdit.setRace(existingPatient.getRace());
        patientToEdit.setRecreationalSubstance(existingPatient.getRecreationalSubstance());
        patientToEdit.setHasTravelledOutsideTheUnitedStatesForMoreThan30Days(existingPatient.getHasTravelledOutsideTheUnitedStatesForMoreThan30Days());
    }

    //  Helper method to set date-related attributes
    public void setPatientAttributes(Model model) {
    	addModelAttributes(model);
    	modelUtil.setCommonModelAttributes(model);
    	insuranceUtil.setCommonModelAttributes(model);
    }

    // Helper Method to set patient id attributes
    public void sortLoggedPatientAttributes(Model model, Long patientId) {
	    Patient loggedInPatient = patientServ.getOne(patientId);
	    if (loggedInPatient != null) {
		    String loggedInPatientName =loggedInPatient.getPatientFirstName();

		    setDateDifferences(patientId, model);
		    addSearchedMethods(model, loggedInPatientName);
	    	filterUtil.sortAllByStartDate(model, patientId);
	    	setLoggedInPatientDateAttributes(patientId, model);
	    	modelUtil.setPatientModelAttributes(model, patientId);
		    model.addAttribute("loggedInPatient", loggedInPatient);
	    	searchUtil.sortLoggedPatientAttributes(model, patientId);
	    	formatAndSetOnePastMedicalHistoryStartDateAttributes(model, patientId);
	    	formatAndSetAllPastMedicalHistoryStartDateAttributes(model, patientId);
    	}
    }
 
    public void addModelAttributes(Model model) {
        model.addAttribute("allPatients", patientServ.findAll());
        model.addAttribute("allPhysicians", physicianServ.findAll());
        model.addAttribute("allPatientCases", patientCaseServ.getAll());
        model.addAttribute("allIncidentReports", incidentReportServ.getAll());
        model.addAttribute("allPhysiciansPatients", physiciansPatientServ.getAll());
        model.addAttribute("allPatientVitalRecords", patientVitalRecordServ.getAll());
        model.addAttribute("allPastMedicalHistories", pastMedicalHistoryServ.getAll());
    }

    public void addSearchedMethods(Model model, String trimmedSearchTerm) {
    	searchByCharacterMethod(model, trimmedSearchTerm);
    	addSearchedFilteredPatients(model, trimmedSearchTerm);
    	searchUtil.setSearchUtilMethods(model, trimmedSearchTerm);
    	searchPatientInsuranceByCharacter(model, trimmedSearchTerm);
    	addOneMatchedPatientCommonAttribute(model, trimmedSearchTerm);
    	addMatchedPatientContainingSearchTerm(model, trimmedSearchTerm);
    	addMatchedPatientCommonAttributeLists(model, trimmedSearchTerm);
    	addOneMatchedPatientCommonSearchAttribute(model, trimmedSearchTerm);
    	diagnosticUtil.setAllSearchTrimmedMethods(model, trimmedSearchTerm);
    }

    // Method to search for patients and their insurance information by characters
    public void searchPatientInsuranceByCharacter(Model model, String trimmedSearchTerm) {
        // If a non-empty search value is provided
        List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

        if (!matchedPatients.isEmpty()) {
            // Single or multiple matches found, set the flag and add to the model
            model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
            model.addAttribute("matchedPatientCharacterList", matchedPatients);

            // Populate InsuranceInformation list for each patient
            List<List<InsuranceInformation>> searchedPatientInsuranceRecordLists = new ArrayList<>();
            for (Patient patient : matchedPatients) {
                List<InsuranceInformation> insuranceInformationList = new ArrayList<>();

                // Check for null to avoid potential NullPointerException
                if (patient.getInsuranceRecords() != null) {
                    for (InsuranceInformation oneInsuranceRecord : patient.getInsuranceRecords()) {
                        // You can directly add the existing InsuranceInformation objects to the list
                        insuranceInformationList.add(oneInsuranceRecord);
                    }
                }

                searchedPatientInsuranceRecordLists.add(insuranceInformationList);
            }

            model.addAttribute("searchedPatientInsuranceRecordLists", searchedPatientInsuranceRecordLists);
        } else {
            // No match found, set the flag and add an empty list to the model
            model.addAttribute("isSingleMatch", false);
            model.addAttribute("matchedPatientCharacterList", Collections.emptyList());
            model.addAttribute("searchedPatientInsuranceRecordLists", Collections.emptyList());
        }
    }


    // Calculate the total length of medical conditions for a list of past medical history records
    public int calculateTotalLengthOfMedicalConditions(List<PastMedicalHistory> medicalHistoryList) {
        int totalLength = 0;

        for (PastMedicalHistory history : medicalHistoryList) {
            int lengthOfMedicalCondition = calculateLengthOfMedicalCondition(history.getStartDate());
            totalLength += lengthOfMedicalCondition;
        }

        return totalLength;
    }
    
    // Helper method to convert Past Medical History Created Dates
    public void formatAndSetAllPastMedicalHistoryStartDateAttributes(Model model, Long patientId) {
        // Fetch all PastMedicalHistory records for the patient
        List<PastMedicalHistory> allPastMedicalHistories = patientServ.getOne(patientId).getPastMedicalHistories();

        // Calculate the total length of medical conditions
        int totalLengthOfMedicalConditions = calculateTotalLengthOfMedicalConditions(allPastMedicalHistories);

        // Add the total length to the model
        model.addAttribute("totalLengthOfMedicalConditions", totalLengthOfMedicalConditions);
    }


 // Helper method to convert Past Medical History Created Dates
    public void formatAndSetOnePastMedicalHistoryStartDateAttributes(Model model, Long id) {
        // Fetch the specific PastMedicalHistory record by id
        PastMedicalHistory pastMedicalHistory = pastMedicalHistoryServ.getOne(id);

        // Check if the retrieved record is not null
        if (pastMedicalHistory != null) {
            // Calculate the length of the medical condition
            int lengthOfMedicalCondition = calculateLengthOfMedicalCondition(pastMedicalHistory.getStartDate());

            // Add the length to the model
            model.addAttribute("lengthOfOneMedicalCondition", lengthOfMedicalCondition);
        } else {
            // Handle the case where the record is not found
            model.addAttribute("lengthOfOneMedicalCondition", 0);
        }
    }

    // Calculate the length of a medical condition based on its start date
    public int calculateLengthOfMedicalCondition(LocalDate dateObj) {
        if (dateObj == null) {
            return 0; // If start date is null, the length is zero
        }

        LocalDate currentDate = LocalDate.now();
        int lengthInYears = (int) ChronoUnit.YEARS.between(dateObj, currentDate);

        // Adjust the length if the current date is after the start date
        return currentDate.isAfter(dateObj) ? lengthInYears : lengthInYears + 1;
    }

    // Calculate the length of a medical condition based on its start date
    public int calculateDescLengthOfMedicalCondition(LocalDate dateObj) {
        if (dateObj == null) {
            return 0; // If start date is null, the length is zero
        }

        LocalDate currentDate = LocalDate.now();
        int lengthInYears = currentDate.getYear() - dateObj.getYear();

        // Adjust the length if the current date is before the birthday this year
        if (currentDate.getMonthValue() < dateObj.getMonthValue() || 
            (currentDate.getMonthValue() == dateObj.getMonthValue() && currentDate.getDayOfMonth() < dateObj.getDayOfMonth())) {
            lengthInYears--;
        }

        return lengthInYears;
    }

    // Calculate Length of Entity
    public int calculateLengthOfCreatedDate(LocalDateTime createdAt) {
        if (createdAt == null) {
            return 0; // If start date is null, the length is zero
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        return (int) ChronoUnit.DAYS.between(createdAt, currentDateTime);
    }

 // Helper method to set filtered patients
    public List<Patient> getFilteredPatientList(String searchedPatientName) {
        // For partial matches
        List<Patient> partialMatches = patientServ.getAllPatientsMatchingSearchTerm(searchedPatientName);

        // For exact matches
        List<Patient> exactMatches = patientServ.getPatientsByContainingLetters(searchedPatientName);

        // Combine the results as needed
        List<Patient> matchedPatients = new ArrayList<>();
        matchedPatients.addAll(partialMatches);
        matchedPatients.addAll(exactMatches);

        return matchedPatients;
    }

    //  Add Matched Searched Patients
    public void addSearchedFilteredPatients (Model model, String trimmedSearchTerm) {
        model.addAttribute("searchMatchedPatientList", getFilteredPatientList(trimmedSearchTerm));
    }

    public List<Patient> getAllPatientsMatchingSearchTerm(Model model, String trimmedSearchTerm) {
        Iterable<Patient> patients = patientServ.findAll();
        List<String> searchTerms = Arrays.asList(trimmedSearchTerm.toLowerCase().split("\\s+"));

        List<Patient> matchingPatients = ((Collection<Patient>) patients).stream()
                .filter(patient ->
                        searchTerms.stream()
                                .anyMatch(term ->
                                        patient.getPatientFirstName().toLowerCase().contains(term) ||
                                        patient.getPatientLastName().toLowerCase().contains(term)
                                )
                )
                .collect(Collectors.toList());

        // Add logging statement
        System.out.println("Search term: " + trimmedSearchTerm + ", Matching Patients: " + matchingPatients);
		return matchingPatients;
    }

    // Flexible Search Method
    public List<Patient> getAllPatientsContainingSearchTerm(String trimmedSearchTerm) {
        Iterable<Patient> patients = patientServ.findAll();
        List<String> searchTerms = Arrays.asList(trimmedSearchTerm.toLowerCase().split("\\s+"));

        return ((Collection<Patient>) patients).stream()
                .filter(patient ->
                        searchTerms.stream()
                                .anyMatch(term ->
                                        patient.getPatientFirstName().toLowerCase().contains(term) ||
                                        patient.getPatientLastName().toLowerCase().contains(term)
                                )
                )
                .collect(Collectors.toList());
    }

    public void addMatchedPatientContainingSearchTerm(Model model, String trimmedSearchTerm) {
        // If a non-empty search value is provided
        List<Patient> matchedPatients = patientServ.getAllPatientsMatchingSearchTerm(trimmedSearchTerm.toLowerCase());

        if (!matchedPatients.isEmpty()) {
            // Matches found, set the flag and add the list to the model
            model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
            model.addAttribute("matchedPatientsContainingSearchList", matchedPatients);
        } else {
            // No match found, set the flag and add an empty list to the model
            model.addAttribute("isSingleMatch", true);
            model.addAttribute("matchedPatientsContainingSearchList", Collections.emptyList());
        }
    }

    // Search for patients using strict criteria
    public void searchPatientsStrictAndAddToModel(Model model, String firstName, String lastName, LocalDate dateOfBirth) {
        List<Patient> strictSearchResults = patientServ.strictSearchPatientsByNameAndDOB(firstName, lastName, dateOfBirth);
        model.addAttribute("strictSearchResults", strictSearchResults);
    }

    // Search for patients using flexible criteria
    public void searchPatientsFlexibleAndAddToModel(Model model, String firstName, String lastName, LocalDate dateOfBirth) {
        List<Patient> flexibleSearchResults = patientServ.flexibleSearchPatientsByNameAndDOB(firstName, lastName, dateOfBirth);
        model.addAttribute("flexibleSearchResults", flexibleSearchResults);
    }

    // Method to add Searched Patients to One New Incident
    public void addMatchedPatientCommonAttributeLists(Model model, String trimmedSearchTerm) {
        if (trimmedSearchTerm != null) {
        // If a non-empty search value is provided
        List<Patient> matchedPatients = Collections.singletonList(patientServ.getOnePatientByFullName(trimmedSearchTerm.toLowerCase()));

        if (!matchedPatients.isEmpty()) {
            // Matches found, set the flag and add the list to the model
            model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
            model.addAttribute("matchedPatientSearchedList", matchedPatients);
        } else {
            // No match found, set the flag and add an empty list to the model
            model.addAttribute("isSingleMatch", true);
            model.addAttribute("matchedPatientSearchedList", Collections.emptyList());
        }
        }
    }
 
    // Method to add Common Attributes to One New Incident
    public void addOneMatchedPatientCommonSearchAttribute(Model model, String trimmedSearchTerm) {
        // If a non-empty search value is provided
        if (trimmedSearchTerm != null) {
        Patient matchedPatientFullName = patientServ.getOnePatientByFullName(trimmedSearchTerm.toLowerCase());

	        if (matchedPatientFullName != null) {
	            // Single match found, set the flag and add to the model
	            model.addAttribute("isSingleMatch", true);
	            model.addAttribute("matchedPatientFullName", matchedPatientFullName);
	        } else {
	            // No match found, set the flag and add an empty list to the model
	            model.addAttribute("isSingleMatch", false);
	            model.addAttribute("matchedPatientsList", Collections.emptyList());
	        }
        } else {
        	return;
        }
    }

    //Method To Search By Characters
    public void searchByCharacterMethod(Model model, String trimmedSearchTerm) {
        // If a non-empty search value is provided
        List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

        if (!matchedPatients.isEmpty()) {
            // Single or multiple matches found, set the flag and add to the model
            model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
            model.addAttribute("matchedPatientCharacterList", matchedPatients);
        } else {
            // No match found, set the flag and add an empty list to the model
            model.addAttribute("isSingleMatch", false);
            model.addAttribute("matchedPatientCharacterList", Collections.emptyList());
        }
    }


    // Using BirthDay Utility
    public static void calculateBirthAge(Model model, Patient patient) {
        LocalDate dateOfBirth = patient.getDateOfBirth();

        // Null Pointer
        if (dateOfBirth == null) {
            throw new IllegalArgumentException("Date of birth cannot be null");
        }

        // Set attribute for the age
        BirthdayUtils.calculateAge(dateOfBirth);
    }

    // Helper method to convert single created at dates to LocalDateTime and format dates
    public void formatAndSetFormatedCurrentDateAttribute(Model model, Date currentDate) {
//        Instant instant = currentDate.toInstant();
//        LocalDateTime createdAtLocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter dayformatter = DateTimeFormatter.ofPattern("EEE, yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy");

        String currentDateTimeFormatted = currentDateTime.format(formatter);
        String dayCurrentDateTimeFormatted = currentDateTime.format(dayformatter);

        // Set formatted date attributes in the model
        model.addAttribute("currentDateTimeFormatted", currentDateTimeFormatted);
        model.addAttribute("dayCurrentDateTimeFormatted", dayCurrentDateTimeFormatted);
    }

	// Method to add Common Attributes to One New Incident
	public void addOneMatchedPatientCommonAttribute(Model model, String trimmedSearchTerm ) {
        // If a non-empty search value is provided
        Patient matchedPatientFirstName = patientServ.getOnePatientFirstName(trimmedSearchTerm.toLowerCase());
        Patient matchedPatientFullName = patientServ.getOnePatientByFullName(trimmedSearchTerm.toLowerCase());
        List<Patient> matchedPatients = patientServ.getAllPatientsMatchingSearchTerm(trimmedSearchTerm.toLowerCase());
        List<Patient> matchedSearchedPatients = patientServ.getAllPatientsMatchingSearchTerm(trimmedSearchTerm.toLowerCase());
        model.addAttribute("matchedPatients", matchedPatients);
        model.addAttribute("matchedSearchedPatients", matchedSearchedPatients);
        model.addAttribute("matchedPatientFirstName", matchedPatientFirstName);
        model.addAttribute("matchedPatientFullName", matchedPatientFullName);
	} 
	
	// Calculate Date Difference
    public long calculateDateDifference(LocalDate dateObject1, LocalDate dateObject2, ChronoUnit unit) {
        return unit.between(dateObject1, dateObject2);
    }

    // Helper Method For Date Ranges
    public void setDateDifferences(Long patientId, Model model) {
        Patient loggedInPatient = patientServ.getOne(patientId);

        // Calculate date difference in years
        long accountLengthYears = calculateDateDifference(loggedInPatient.getCreatedAt().toLocalDate(), LocalDate.now(), ChronoUnit.YEARS);
        model.addAttribute("accountLength", accountLengthYears);

        // Calculate date difference in days and months
        LocalDate startDate = loggedInPatient.getCreatedAt().toLocalDate();
        LocalDate currentDate = LocalDate.now();

        long accountLengthDays = calculateDateDifference(startDate, currentDate, ChronoUnit.DAYS);
        long accountLengthMonths = calculateDateDifference(startDate, currentDate, ChronoUnit.MONTHS);

        model.addAttribute("accountLengthDays", accountLengthDays);
        model.addAttribute("accountLengthMonths", accountLengthMonths);

        // Calculate date difference in years for most recent past medical record
        PastMedicalHistory mostRecentRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patientId);

        if (mostRecentRecord != null) {
            long conditionLengthYears = calculateDateDifference(mostRecentRecord.getStartDate(), LocalDate.now(), ChronoUnit.YEARS);
            model.addAttribute("loggedInPatientLengthOfConditionYears", conditionLengthYears);

            // Calculate date difference in days and months
            LocalDate conditionStartDate = mostRecentRecord.getStartDate();
            long conditionLengthDays = calculateDateDifference(conditionStartDate, currentDate, ChronoUnit.DAYS);
            long conditionLengthMonths = calculateDateDifference(conditionStartDate, currentDate, ChronoUnit.MONTHS);

            model.addAttribute("loggedInPatientLengthOfConditionDays", conditionLengthDays);
            model.addAttribute("loggedInPatientLengthOfConditionMonths", conditionLengthMonths);
        } else {
            // Handle the case where the most recent record is null
            model.addAttribute("loggedInPatientLengthOfConditionYears", 0);
            model.addAttribute("loggedInPatientLengthOfConditionDays", 0);
            model.addAttribute("loggedInPatientLengthOfConditionMonths", 0);
        }
    }

    // Helper Method For User Dates
    public void setLoggedInPatientDateAttributes(Long patientId, Model model) {
	    Patient loggedInPatient = patientServ.getOne(patientId);
        LocalDate dateOfBirth = loggedInPatient.getDateOfBirth();        
        if (dateOfBirth != null) {
        }

        // Calculate patient age
        int patientAge = Period.between(dateOfBirth, LocalDate.now()).getYears();

        // Add formatted dates to the model
		model.addAttribute("loggedInPatient", loggedInPatient);
        model.addAttribute("patientAge", patientAge);
		model.addAttribute("loggedInPatientDayCreatedAt", loggedInPatient.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		model.addAttribute("loggedInPatientCreatedAt", loggedInPatient.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
    }

    public void setPatientDateAttributes(Model model, Patient loggedInPatient, Patient onePatient) {
		// Add formatted dates to the model
		model.addAttribute("onePatientDayCreatedAt", onePatient.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		model.addAttribute("onePatientCreatedAt", onePatient.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		model.addAttribute("loggedInPatientDayCreatedAt", loggedInPatient.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		model.addAttribute("loggedInPatientCreatedAt", loggedInPatient.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		setLoggedPatientCommonAttributes(model, onePatient, null);
    }

    // Your existing method
    public void setLoggedPatientCommonAttributes(Model model, Patient loggedInPatient, BindingResult bindingResult) {
        // Null check for date of birth
        LocalDate dateOfBirth = loggedInPatient.getDateOfBirth();
        if (dateOfBirth == null) {
            bindingResult.rejectValue("dateOfBirth", "NotNull", "Date of birth cannot be null");
            // Handle the case where date of birth is null
        }

        // Check if the birth year is within the specified range
        if (!isValidBirthYear(dateOfBirth.getYear())) {
            bindingResult.rejectValue("dateOfBirth", "InvalidYear", "Invalid birth year");
            // Handle the case where the birth year is not valid
        }

        // Calculate patient age
        int patientAge = Period.between(dateOfBirth, LocalDate.now()).getYears();

        // Set attributes
        model.addAttribute("loggedInPatient", loggedInPatient);
        model.addAttribute("loggedInPatientAge", patientAge);
        model.addAttribute("age", patientAge);
        model.addAttribute("loggedPatientDayCreatedAt", loggedInPatient.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
        model.addAttribute("loggedPatientCreatedAt", loggedInPatient.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
    }

    // Updated method to check if the birth year is within the specified range and not in the future
    public boolean isValidBirthYear(int birthYear) {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int minValidYear = currentYear - 150; // 150 years ago
        int maxValidYear = currentYear; // Current year

        return birthYear >= minValidYear && birthYear <= maxValidYear && birthYear <= currentYear;
    }

    public void setRegPatientCommonAttributes(Model model, Patient patient, BindingResult bindingResult) {
        LocalDate dateOfBirth = patient.getDateOfBirth();

        // Handle the case where date of birth is null
        if (dateOfBirth == null) {
            bindingResult.rejectValue("dateOfBirth", "NotNull", "Date of birth cannot be null");
        }

        // Handle the case where the birth year is  valid and within the specified birth range year
        if (!isValidBirthYear(dateOfBirth.getYear())) {
            bindingResult.rejectValue("dateOfBirth", "InvalidYear", "Invalid birth year");
        }

        // Calculate patient age
        int patientAge = Period.between(dateOfBirth, LocalDate.now()).getYears();

        // Set attributes
        model.addAttribute("loggedInPatient", patient);
        model.addAttribute("loggedInPatientAge", patientAge);
        model.addAttribute("patientAge", patientAge);
        model.addAttribute("loggedPatientDayCreatedAt", patient.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
        model.addAttribute("loggedPatientCreatedAt", patient.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
    }

    // Validate Registered Patient Birth Year
    public boolean isValidRegisteredPatientBirthYear(int birthYear) {
        int currentYear = Year.now().getValue();
        int minimumValidYear = currentYear - 150;

        return birthYear >= minimumValidYear && birthYear <= currentYear;
    }

    // Date Range Validator Against ONLY Future Years
    public boolean isValidDateRange(LocalDate dateObj) {
        if (dateObj == null) {
            return false; // Null birth date is invalid
        }

        LocalDate currentDate = LocalDate.now();
        int minimumValidYear = currentDate.getYear() - 150;

        // Check if birth date is in the past, not in the future
        if (dateObj.isAfter(currentDate)) {
            return false;
        }

        // Check if the birth year is within the specified range
        if (dateObj.getYear() < minimumValidYear) {
            return false;
        }

        // Check if the birth date is in the future month of the current year
        if (dateObj.getYear() == currentDate.getYear() &&
                (dateObj.getMonthValue() > currentDate.getMonthValue() ||
                        (dateObj.getMonthValue() == currentDate.getMonthValue() &&
                                dateObj.getDayOfMonth() > currentDate.getDayOfMonth()))) {
            return false;
        }

        return true;
    }

    // Validate Against Future Years
    public boolean isValidBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            return false; // Null birth date is invalid
        }

        LocalDate currentDate = LocalDate.now();
        int minimumValidYear = currentDate.getYear() - 150;

        // Check if birth date is in the past, not in the future
        if (birthDate.isAfter(currentDate)) {
            return false;
        }

        // Check if the birth year is within the specified range
        if (birthDate.getYear() < minimumValidYear) {
            return false;
        }

        // Check if the birth date is in the future month of the current year
        if (birthDate.getYear() == currentDate.getYear() &&
                (birthDate.getMonthValue() > currentDate.getMonthValue() ||
                        (birthDate.getMonthValue() == currentDate.getMonthValue() &&
                                birthDate.getDayOfMonth() > currentDate.getDayOfMonth()))) {
            return false;
        }

        return true;
    }
    
    // Validate Start Date
    public boolean isValidStartDate(LocalDate startDate) {
        if (startDate == null) {
            return false; // Null start date is invalid
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate maxStartDate = currentDate.minusYears(5);

        // Check if start date is in the future or not in the past 5 years
        if (startDate.isAfter(currentDate) || startDate.isBefore(maxStartDate)) {
            return false;
        }

        return true;
    }

    // Validate Expiration Date
    public boolean isValidExpirationDate(LocalDate expirationDate) {
        if (expirationDate == null) {
            return false; // Null expiration date is invalid
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate yesterday = currentDate.minusDays(1);

        // Check if expiration date is today or yesterday
        if (expirationDate.isEqual(currentDate) || expirationDate.isEqual(yesterday)) {
            return false;
        }

        return true;
    }

    // Calculate Coverage Length and Add to Model
    public void addCoverageLengthToModel(Model model, LocalDate expirationDate) {
        long coverageLength = calculateCoverageLength(expirationDate);
        long coverageDays = calculateCoverageLengthInDays(expirationDate);
        model.addAttribute("coverageLength", coverageLength);
        model.addAttribute("dayCoverageLength", coverageDays);
    }

    // Calculate Coverage Length
    public long calculateDatePeriodInDays(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0; // If expiration date is null, coverage length is zero
        }
        
        Period period = Period.between(startDate, endDate);

        return period.getDays(); // You can modify this based on your specific requirements
    }

    // Calculate Coverage Length
    public long calculateCoverageLength(LocalDate expirationDate) {
        if (expirationDate == null) {
            return 0; // If expiration date is null, coverage length is zero
        }

        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(currentDate, expirationDate);

        return period.getDays(); // You can modify this based on your specific requirements
    }

    // Calculate Coverage Length in Days
    public long calculateCoverageLengthInDays(LocalDate expirationDate) {
        if (expirationDate == null) {
            return 0; // If expiration date is null, coverage length is zero
        }

        LocalDate currentDate = LocalDate.now();
        return ChronoUnit.DAYS.between(currentDate, expirationDate);
    }

    // Calculate Coverage Length in Days
    public long calculateCoverageLengthInMonths(LocalDate expirationDate) {
        if (expirationDate == null) {
            return 0; // If expiration date is null, coverage length is zero
        }

        LocalDate currentDate = LocalDate.now();
        return ChronoUnit.MONTHS.between(currentDate, expirationDate);
    }

    // Calculate Coverage Length in Months and Years
    public Period calculateCoverageLengthInPeriod(LocalDate expirationDate) {
        if (expirationDate == null) {
            return Period.ZERO; // If expiration date is null, coverage length is zero
        }

        LocalDate currentDate = LocalDate.now();
        return Period.between(currentDate, expirationDate);
    }
	
	 // Calculate Coverage Length and Add to Model
	 public void addSpecificCoverageLengthToModel(Model model, LocalDate expirationDate) {
	     long coverageLengthDays = calculateCoverageLengthInDays(expirationDate);
	     long coverageLengthMonths = calculateCoverageLengthInMonths(expirationDate);
	     model.addAttribute("lengthOfCoverageDays", coverageLengthDays);
	     model.addAttribute("lengthOfCoverageMonths", coverageLengthMonths);
	
	     // Calculate coverage length in months and years
	     Period coverageLengthPeriod = calculateCoverageLengthInPeriod(expirationDate);
	
	     // Convert the Period to a custom format
	     String coverageLengthFormatted = formatCoverageLength(coverageLengthPeriod);
	     model.addAttribute("coverageLengthFormatted", coverageLengthFormatted);
	 }
	
	 // Helper method to format coverage length
	 private String formatCoverageLength(Period coverageLengthPeriod) {
	     if (coverageLengthPeriod == null) {
	         return ""; // Handle the case where coverage length period is null
	     }
	
	     int years = coverageLengthPeriod.getYears();
	     int months = coverageLengthPeriod.getMonths();
	     int days = coverageLengthPeriod.getDays();
	
	     // Customize the format as needed
	     String formattedCoverageLength = String.format("Golden Covers P%dY%dM%dD", years, months, days);
	     
	     return formattedCoverageLength;
	 }

	    // Validate Regular Patients
	    public boolean isValidBirthDateRegularPatient(LocalDate birthDate) {
	        if (birthDate == null) {
	            return false; // Null birth date is invalid
	        }

	        LocalDate currentDate = LocalDate.now();
	        int minimumValidYear = currentDate.getYear() - 150;

	        // Check if birth date is in the past, not in the future
	        if (birthDate.isAfter(currentDate)) {
	            return false;
	        }

	        // Check if the birth year is within the specified range
	        if (birthDate.getYear() < minimumValidYear) {
	            return false;
	        }

	        // Check if the birth date is in the future month of the current year
	        if (birthDate.getYear() == currentDate.getYear() &&
	                (birthDate.getMonthValue() > currentDate.getMonthValue() ||
	                        (birthDate.getMonthValue() == currentDate.getMonthValue() &&
	                                birthDate.getDayOfMonth() > currentDate.getDayOfMonth()))) {
	            return false;
	        }

	        return true;
	    }

	    // Validate Past Dates
	    public boolean validatePastDates(LocalDate dateObj) {
	        if (dateObj == null) {
	            return false; // Null birth date is invalid
	        }

	        LocalDate currentDate = LocalDate.now();
	        int minimumValidYear = currentDate.getYear() - 150;

	        // Check if the birth year is within the specified range
	        if (dateObj.getYear() < minimumValidYear) {
	            return false;
	        }

	        return true;
	    }

    // Validate ObyGyn Patients
    public boolean isValidBirthDateObstetricPatient(LocalDate birthDate) {
        // Reuse the validation logic for regular patients
        return isValidBirthDateRegularPatient(birthDate);
    }

    public void setLoggedInPatientAgeAttributes(Model model, Object loggedInPatient) {
        LocalDate birthDate;

        if (loggedInPatient instanceof Patient) {
            // Case 1: If you have the loggedInPatient object directly
            birthDate = ((Patient) loggedInPatient).getDateOfBirth();
        } else if (loggedInPatient instanceof Long) {
            // Case 2: If you have the patientId, retrieve the patient from the service
            Patient patient = patientServ.getOne((Long) loggedInPatient);
            birthDate = patient.getDateOfBirth();
        } else {
            // Handle other cases or throw an exception if needed
            throw new IllegalArgumentException("Invalid parameter type for loggedInPatient");
        }

        if (birthDate != null) {
            int loggedInPatientAge = (int) ChronoUnit.YEARS.between(birthDate, LocalDate.now());
            model.addAttribute("loggedInPatientAge", loggedInPatientAge);
        } else {
            // Handle the case where the birthDate is null
            throw new IllegalArgumentException("Birth date is null for the logged-in patient");
        }
    }

    public LocalDate convertDateToLocalDate(Date date) {
        Instant instantDateObj = date.toInstant();
        return instantDateObj.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDateTime convertDateToLocalDateTime(Date date) {
        Instant instantDateObj = date.toInstant();
        return instantDateObj.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public String formatContactNumber(String rawContactNumber) {
	    // Remove any non-digit characters
	    String cleanedNumber = rawContactNumber.replaceAll("[^\\d]", "");

	    // Format the number as "123-123-1234"
	    if (cleanedNumber.length() == 10) {
	        return cleanedNumber.replaceFirst("(\\d{3})(\\d{3})(\\d{4})", "$1-$2-$3");
	    } else {
	        // Handle invalid or unexpected number lengths
	        return cleanedNumber;
	    }
	}
}