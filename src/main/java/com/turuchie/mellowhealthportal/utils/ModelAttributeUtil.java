package com.turuchie.mellowhealthportal.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientVitalRecord;
import com.turuchie.mellowhealthportal.models.PatientOperations.CurrentMedication;
import com.turuchie.mellowhealthportal.models.PatientOperations.IncidentReport;
import com.turuchie.mellowhealthportal.models.PatientOperations.InsuranceInformation;
import com.turuchie.mellowhealthportal.models.PatientOperations.PastMedicalHistory;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.models.PatientOperations.RecentAdmission;
import com.turuchie.mellowhealthportal.models.PatientOperations.RecentEmergency;
import com.turuchie.mellowhealthportal.models.Physicians.Physician;
import com.turuchie.mellowhealthportal.services.AdverseEffectService;
import com.turuchie.mellowhealthportal.services.CurrentMedicationService;
import com.turuchie.mellowhealthportal.services.IncidentReportService;
import com.turuchie.mellowhealthportal.services.InsuranceInformationService;
import com.turuchie.mellowhealthportal.services.PastMedicalHistoryService;
import com.turuchie.mellowhealthportal.services.PatientCaseService;
import com.turuchie.mellowhealthportal.services.PatientService;
import com.turuchie.mellowhealthportal.services.PatientVitalRecordService;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.PhysiciansPatientService;
import com.turuchie.mellowhealthportal.services.RecentAdmissionService;
import com.turuchie.mellowhealthportal.services.RecentEmergencyService;

@Component
public class ModelAttributeUtil {
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
	private InsuranceInformationService insuranceRecordServ;
	
	@Autowired
	private CurrentMedicationService currentMedicationServ;
	
	@Autowired
	private RecentAdmissionService recentAdmissionServ;
	
	@Autowired
	private RecentEmergencyService recentEmergencyServ;

	@Autowired
	private AdverseEffectService adverseEffectServ;

	// Helper method to convert Time Format
	public List<Integer> generateTimeFormatList() {
	    List<Integer> timeFormat = new ArrayList<>();
	    for (int i = 1; i <= 12; i++) {
	        timeFormat.add(i);
	    }
	    return timeFormat;
	}

	//  Helper method to set date-related attributes
    public void setCommonModelAttributes(Model model) {
    	addCommonModelAttributes(model);
    	addCommonIncidentReportDateAttributes(model);
    	addCommonPatientVitalRecordsAttributes(model);
    	formatListAndSetAllPastMedicalHistoryStartDateAttributes(model);
    	mapAndSetAllPatientCaseDateAttributes(model);
    	formatAndSetPatientCaseDateAttributes(model);
    	formatAndSetAllPatientsDateAttributes(model);
    	formatListAndSetAllPatientAge(model);
    	formatRepoPatientAge(model);
    	setCurrentDateTimeAttributes(model);
    	addFormattedPhysicianDateAttributes(model);
    	formatAndSetAllPatientsDateAttributes(model);
    	setInsuranceReportProperty(model);
    	setMostRecentInsuranceReportProperty(model);
    	setMostRecentRecords(model);
    	formatAndSetPastMedicalDateAttributes(model);
    	mapAllPastMedicalHistoryDayMonthYearAttributes(model);
    }

    // Helper method to add common model attributes
    public void addCommonModelAttributes(Model model) {
        model.addAttribute("allPatients", patientServ.findAll());
        model.addAttribute("allPhysicians", physicianServ.findAll());
        model.addAttribute("allPatientCases", patientCaseServ.getAll());
        model.addAttribute("allIncidentReports", incidentReportServ.getAll());
        model.addAttribute("allPhysiciansPatients", physiciansPatientServ.getAll());
        model.addAttribute("allPatientVitalRecords", patientVitalRecordServ.getAll());
        model.addAttribute("allPastMedicalHistories", pastMedicalHistoryServ.getAll());
        model.addAttribute("allCurrentMedications", currentMedicationServ.getAll());
        model.addAttribute("allRecentEmergencies", recentEmergencyServ.getAll());
        model.addAttribute("allRecentAdmissions", recentAdmissionServ.getAll());
        model.addAttribute("allAdverseEffects", adverseEffectServ.getAll());
	    model.addAttribute("allInsuranceRecords", insuranceRecordServ.getAll());
    }

    // Helper method to set current date and time attributes
    public void setCurrentDateTimeAttributes(Model model) {
    	// Formatted Current Dates
        model.addAttribute("dayCurrentDateTime", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
        model.addAttribute("currentDateTime", LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));

        // Additional attributes for seconds, minutes, hours, and days
        model.addAttribute("currentSecond", LocalTime.now().getSecond());
        model.addAttribute("currentMinute", LocalTime.now().getMinute());
        model.addAttribute("currentHour", LocalTime.now().getHour());
        model.addAttribute("currentDayOfYear", LocalDate.now().getDayOfYear());
        model.addAttribute("timeFormat",generateTimeFormatList());
    }

	// Helper method to convert Date to LocalDateTime and format dates for Patients
    public void formatAndSetAllPatientsDateAttributes(Model model) {
        // Fetch all Patients
        List<Patient> allPatients = (List<Patient>) patientServ.findAll();

        // Extract createdAt dates from Patients
        List<LocalDateTime> createdAtDates = allPatients.stream()
                .map(Patient::getCreatedAt)
                .collect(Collectors.toList());

        // Format all LocalDateTime objects
        List<String> formattedPatientCreatedAtList = createdAtDates.stream()
                .map(date -> date.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")))
                .collect(Collectors.toList());

        List<String> formattedPatientDayCreatedAtList = createdAtDates.stream()
                .map(date -> date.format(DateTimeFormatter.ofPattern("EEE, yyyy")))
                .collect(Collectors.toList());

        // Set formatted date attributes in the model
        model.addAttribute("allPatientCreatedAtList", formattedPatientCreatedAtList);
        model.addAttribute("allPatientDayCreatedAtList", formattedPatientDayCreatedAtList);

        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy");
        DateTimeFormatter dayformatter = DateTimeFormatter.ofPattern("EEE, yyyy");

        String currentDateTimeFormatted = currentDateTime.format(formatter);
        String dayCurrentDateTimeFormatted = currentDateTime.format(dayformatter);

        // Set current date attributes in the model
        model.addAttribute("currentDateTime", currentDateTimeFormatted);
        model.addAttribute("dayCurrentDateTime", dayCurrentDateTimeFormatted);
    }

    // Sort By Create Date
    public void setInsuranceReportProperty(Model model) {
        InsuranceInformation mostRecentInsuranceInformation = insuranceRecordServ.getMostRecentInsuranceInformation();

        // Assuming InsuranceReport has a property named "reportValue"
        String reportValue = mostRecentInsuranceInformation != null ? mostRecentInsuranceInformation.getInsuranceId() : "Default Value";

        model.addAttribute("insuranceReportValue", reportValue);
    }

    public void setMostRecentInsuranceReportProperty(Model model) {
        InsuranceInformation mostRecentInsuranceInformation = insuranceRecordServ.getMostRecentInsuranceInformation();

        model.addAttribute("insuranceReport", mostRecentInsuranceInformation);
    }

    // Set Recent Records
    public void setMostRecentRecords(Model model) {
        PatientCase mostRecentPatientCase = patientCaseServ.getMostRecentPatientCase();
        PatientVitalRecord mostRecentPatientVitalRecord = patientVitalRecordServ.getMostRecentPatientVitalRecord();
        CurrentMedication mostRecentCurrentMedication = currentMedicationServ.getMostRecentCurrentMedication();
        RecentEmergency mostRecentRecentEmergency = recentEmergencyServ.getMostRecentRecentEmergency();
        RecentAdmission mostRecentRecentAdmission = recentAdmissionServ.getMostRecentRecentAdmission();
        PastMedicalHistory pastMedicalHistory = pastMedicalHistoryServ.getMostRecentPastMedicalHistory();

        // Add most recent records to the model
        model.addAttribute("mostRecentPatientCase", mostRecentPatientCase);
        model.addAttribute("mostRecentPatientVitalRecord", mostRecentPatientVitalRecord);
        model.addAttribute("mostRecentCurrentMedication", mostRecentCurrentMedication);
        model.addAttribute("mostRecentRecentEmergency", mostRecentRecentEmergency);
        model.addAttribute("mostRecentRecentAdmission", mostRecentRecentAdmission);
        model.addAttribute("mostRecentPastMedicalHistory", pastMedicalHistory);
    }

    // Helper method to convert Date to LocalDateTime and format dates for PastMedicalHistory
    public void formatAndSetPastMedicalDateAttributes(Model model) {
        // Fetch all Patients
        Iterable<Patient> allPatients = patientServ.findAll();

        // Extract createdAt dates from PatientCases
        List<LocalDateTime> pastMedicalHistoryCreatedAtDates = new ArrayList<>();

        for (Patient patient : allPatients) {
            for (PastMedicalHistory pastMedicalHistory : patient.getPastMedicalHistories()) {
                LocalDate createdAtDate = pastMedicalHistory.getCreatedAt();

                // Check for null before converting
                if (createdAtDate != null) {
                    // Corrected: Convert LocalDate to LocalDateTime
                    LocalDateTime dateTime = createdAtDate.atStartOfDay();
                    pastMedicalHistoryCreatedAtDates.add(dateTime);
                }
            }
        }

        // List Population
        List<String> formattedPastMedicalHistoryCreatedAtList = pastMedicalHistoryCreatedAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")))
                .collect(Collectors.toList());

        List<String> formattedPastMedicalHistoryDayCreatedAtList = pastMedicalHistoryCreatedAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, yyyy")))
                .collect(Collectors.toList());

        model.addAttribute("pastMedicalHistoryCreatedAtList", formattedPastMedicalHistoryCreatedAtList);
        model.addAttribute("pastMedicalHistoryDayCreatedAtList", formattedPastMedicalHistoryDayCreatedAtList);

        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy");
        DateTimeFormatter dayformatter = DateTimeFormatter.ofPattern("EEE, yyyy");

        String currentDateTimeFormatted = currentDateTime.format(formatter);
        String dayCurrentDateTimeFormatted = currentDateTime.format(dayformatter);

        // Set formatted date attributes in the model
        model.addAttribute("currentDateTime", currentDateTimeFormatted);
        model.addAttribute("dayCurrentDateTime", dayCurrentDateTimeFormatted);
    }

    // Formatted Past Medical Records And Length Of Medical Condition
    public void mapAllPastMedicalHistoryDayMonthYearAttributes(Model model) {
        // Fetch all PatientCases
        List<PastMedicalHistory> allPastMedicalHistories = pastMedicalHistoryServ.getAll();

        // Filter out PastMedicalHistories with null start dates
        List<PastMedicalHistory> validPastMedicalHistories = allPastMedicalHistories.stream()
                .filter(history -> history.getStartDate() != null)
                .collect(Collectors.toList());

        // Create a java.util.Map to store the association between each PastMedicalHistory and its length
        java.util.Map<PastMedicalHistory, Integer> medicalConditionLengthMap = new HashMap<>();

        // Calculate lengths of medical conditions and populate the map
        for (PastMedicalHistory history : validPastMedicalHistories) {
            LocalDate startDate = history.getStartDate();
            int lengthOfMedicalCondition = (int) ChronoUnit.YEARS.between(startDate, LocalDate.now());
            medicalConditionLengthMap.put(history, lengthOfMedicalCondition);
        }

        model.addAttribute("formattedLengthOfMedicalConditionMap", medicalConditionLengthMap);

        // Additional attributes for individual components of the created-at date
        model.addAttribute("pastMedicalHistoryDay", validPastMedicalHistories.stream()
                .map(history -> history.getStartDate().getDayOfMonth())
                .collect(Collectors.toList()));

        model.addAttribute("pastMedicalHistoryMonth", validPastMedicalHistories.stream()
                .map(history -> history.getStartDate().getMonth())
                .collect(Collectors.toList()));

        model.addAttribute("pastMedicalHistoryYear", validPastMedicalHistories.stream()
                .map(history -> history.getStartDate().getYear())
                .collect(Collectors.toList()));
    }

    // Helper method to convert Date to LocalDateTime and format dates for PatientCases
    public void formatAndSetPatientCaseDateAttributes(Model model) {
        // Fetch all Patients
        Iterable<Patient> allPatients = patientServ.findAll();

        // Extract createdAt dates from PatientCases
        List<LocalDateTime> patientCaseCreatedAtDates = new ArrayList<>();

        for (Patient patient : allPatients) {
            for (PatientCase patientCase : patient.getPatientCases()) {
                LocalDateTime createdAtDate = patientCase.getCreatedAt();

                // Check for null before converting
                if (createdAtDate != null) {
                    LocalDateTime dateTime = createdAtDate;
                    patientCaseCreatedAtDates.add(dateTime);
                }
            }
        }

        // Convert the patientCaseCreatedAtDates to formatted date strings
        List<String> formattedPatientCaseCreatedAtList = patientCaseCreatedAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")))
                .collect(Collectors.toList());

        List<String> formattedPatientCaseDayCreatedAtList = patientCaseCreatedAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, yyyy")))
                .collect(Collectors.toList());

        model.addAttribute("patientCaseCreatedAtList", formattedPatientCaseCreatedAtList);
        model.addAttribute("patientCaseDayCreatedAtList", formattedPatientCaseDayCreatedAtList);

        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy");
        DateTimeFormatter dayformatter = DateTimeFormatter.ofPattern("EEE, yyyy");

        String currentDateTimeFormatted = currentDateTime.format(formatter);
        String dayCurrentDateTimeFormatted = currentDateTime.format(dayformatter);

        // Set formatted date attributes in the model
        model.addAttribute("currentDateTime", currentDateTimeFormatted);
        model.addAttribute("dayCurrentDateTime", dayCurrentDateTimeFormatted);
    }

    // Helper method to convert PatientCase Created Dates
    public void mapAndSetAllPatientCaseDateAttributes(Model model) {
        // Fetch all PatientCases
        List<PatientCase> allPatientCases = patientCaseServ.getAll();

        // Extract createdAt dates from PatientCases
        List<LocalDateTime> createdAtDates = allPatientCases.stream()
                .map(PatientCase::getCreatedAt)
                .collect(Collectors.toList());

        // Convert createdAtDates to formatted date strings
        List<String> formattedPatientCaseCreatedAtList = createdAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")))
                .collect(Collectors.toList());

        List<String> formattedPatientCaseDayCreatedAtList = createdAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, yyyy")))
                .collect(Collectors.toList());

        model.addAttribute("patientCaseCreatedAtDates", formattedPatientCaseCreatedAtList);
        model.addAttribute("patientCaseDayCreatedAtDates", formattedPatientCaseDayCreatedAtList);

        // Set formatted date attributes for the current date in the model
        String currentDateTimeFormatted = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
        model.addAttribute("currentDateTime", currentDateTimeFormatted);
        model.addAttribute("dayCurrentDateTime", currentDateTimeFormatted);  // Reusing the formatted current date
    }

    // Helper Method To Calculate Length Of Medical Condition
    public void formatListAndSetAllPastMedicalHistoryStartDateAttributes(Model model) {
        // Fetch all PatientCases
        List<PastMedicalHistory> allPastMedicalHistories = pastMedicalHistoryServ.getAll();

        // Extract start dates from PastMedicalHistories
        List<LocalDate> startDates = allPastMedicalHistories.stream()
                .map(PastMedicalHistory::getStartDate)
                .filter(Objects::nonNull) // Filter out null start dates if any
                .collect(Collectors.toList());

        // Create a java.util.Map to store the association between each PastMedicalHistory and its length
        java.util.Map<PastMedicalHistory, Integer> medicalConditionLengthMap = new HashMap<>();

        // Check if startDates is not empty
        if (!startDates.isEmpty()) {
            // Calculate lengths of medical conditions and populate the map
            for (PastMedicalHistory history : allPastMedicalHistories) {
                LocalDate startDate = history.getStartDate();
                if (startDate != null) {
                    int lengthOfMedicalCondition = (int) ChronoUnit.YEARS.between(startDate, LocalDate.now());
                    medicalConditionLengthMap.put(history, lengthOfMedicalCondition);
                }
            }
        }

        model.addAttribute("lengthOfMedicalConditionMap", medicalConditionLengthMap);
    }

    // Alternative Helper Method To Calculate Length Of Medical Condition
    public void mapAllPastMedicalHistoryStartDateAttributes(Model model) {
        // Fetch all PatientCases
        List<PastMedicalHistory> allPastMedicalHistories = pastMedicalHistoryServ.getAll();

        // Filter out PastMedicalHistories with null start dates
        List<PastMedicalHistory> validPastMedicalHistories = allPastMedicalHistories.stream()
                .filter(history -> history.getStartDate() != null)
                .collect(Collectors.toList());

        // Create a java.util.Map to store the association between each PastMedicalHistory and its length
        java.util.Map<PastMedicalHistory, Integer> medicalConditionLengthMap = new HashMap<>();

        // Calculate lengths of medical conditions and populate the map
        for (PastMedicalHistory history : validPastMedicalHistories) {
            LocalDate startDate = history.getStartDate();
            int lengthOfMedicalCondition = (int) ChronoUnit.YEARS.between(startDate, LocalDate.now());
            medicalConditionLengthMap.put(history, lengthOfMedicalCondition);
        }

        model.addAttribute("lengthOfMedicalConditionMapList", medicalConditionLengthMap);
    }


    // Helper method to convert Patients Date and AGE
    public void formatListAndSetAllPatientAge(Model model) {
        // Fetch all PatientCases
        Iterable<Patient> allPatients = patientServ.findAll();

        // Extract start dates from PastMedicalHistories
        List<LocalDate> dateOfBirth = ((Collection<Patient>) allPatients).stream()
                .map(Patient::getDateOfBirth)
                .filter(Objects::nonNull) // Filter out null start dates if any
                .collect(Collectors.toList());

        // Create a java.util.Map to store the association between each PastMedicalHistory and its length
        java.util.Map<Patient, Integer> ageMap = new HashMap<>();

        // Check if startDates is not empty
        if (!dateOfBirth.isEmpty()) {
            // Calculate lengths of medical conditions and populate the map
            for (Patient birthday : allPatients) {
                LocalDate birthDate = birthday.getDateOfBirth();
                if (birthDate != null) {
                    int age = (int) ChronoUnit.YEARS.between(birthDate, LocalDate.now());
                    ageMap.put(birthday, age);
                }
            }
        }

        model.addAttribute("patientAgeMap", ageMap);
    }

    public void formatRepoPatientAge(Model model) {
        // Fetch patients with non-null date of birth
        List<Patient> patientsWithDateOfBirth = patientServ.findAllPatientsWithDateOfBirth();

        // Create a list to store ages
        List<Integer> ages = new ArrayList<>();

        // Calculate ages and populate the list
        for (Patient patient : patientsWithDateOfBirth) {
            LocalDate birthDate = patient.getDateOfBirth();

            // Check for null birth date
            if (birthDate != null) {
                int age = (int) ChronoUnit.YEARS.between(birthDate, LocalDate.now());
                ages.add(age);
            }
        }

        model.addAttribute("patientAges", ages);
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
            model.addAttribute("lengthOfPatientMedicalCondition", lengthOfMedicalCondition);
        } else {
            // Handle the case where the record is not found
            model.addAttribute("lengthOfPatientMedicalCondition", 0);
        }
    }

    // Calculate the length of a medical condition based on its start date
    public int calculateLengthOfMedicalCondition(LocalDate localDate) {
        if (localDate == null) {
            return 0; // If start date is null, the length is zero
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        return (int) ChronoUnit.YEARS.between(localDate, currentDateTime);
    }

	// Helper method to convert Physician Created Dates
	public void addFormattedPhysicianDateAttributes(Model model) {
	     Iterable<Physician> allPhysicians = physicianServ.findAll();
	
	     // Convert Iterable to List
	     List<Physician> physicianList = new ArrayList<>();
	     allPhysicians.forEach(physicianList::add);
	
	     // Extract createdAt dates from Physicians
	     List<Date> createdAtDates = physicianList.stream()
	             .map(Physician::getCreatedAt)
	             .collect(Collectors.toList());
	
	     // Convert createdAtDates to formatted date strings
	     List<String> formattedPhysicianCreatedAtList = createdAtDates.stream()
	             .map(dateTime -> {
	                 LocalDateTime localDateTime = dateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	                 return localDateTime.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	             })
	             .collect(Collectors.toList());
	
	     List<String> formattedPhysicianDayCreatedAtList = createdAtDates.stream()
	             .map(dateTime -> {
	                 LocalDateTime localDateTime = dateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	                 return localDateTime.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	             })
	             .collect(Collectors.toList());
	
	     model.addAttribute("allPhysicianCreatedAtDates", formattedPhysicianCreatedAtList);
	     model.addAttribute("allPhysicianDayCreatedAtDates", formattedPhysicianDayCreatedAtList);
	
	     // Set formatted date attributes for the current date in the model
	     String currentDateTimeFormatted = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	     String dayCurrentDateTimeFormatted = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	     model.addAttribute("currentDateTime", currentDateTimeFormatted);
	     model.addAttribute("dayCurrentDateTime", dayCurrentDateTimeFormatted);
	}

    // Helper method to convert Patient Vital Record Created Dates
    public void addCommonPatientVitalRecordsAttributes(Model model) {
        // Fetch all PatientCases
        List<PatientVitalRecord> allPatientVitalRecords = patientVitalRecordServ.getAll();

        // Extract createdAt dates from PatientCases
        List<LocalDateTime> createdAtDates = allPatientVitalRecords.stream()
                .map(PatientVitalRecord::getCreatedAt)
                .collect(Collectors.toList());

        // Convert createdAtDates to formatted date strings
        List<String> formattedPatientVitalRecordCreatedAtList = createdAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")))
                .collect(Collectors.toList());

        List<String> formattedPatientVitalRecordDayCreatedAtList = createdAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, yyyy")))
                .collect(Collectors.toList());

        model.addAttribute("patientVitalRecordCreatedAt", formattedPatientVitalRecordCreatedAtList);
        model.addAttribute("patientVitalRecordDayCreatedAt", formattedPatientVitalRecordDayCreatedAtList);

        // Set formatted date attributes for the current date in the model
        String currentDateTimeFormatted = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
        model.addAttribute("currentDateTime", currentDateTimeFormatted);
        model.addAttribute("dayCurrentDateTime", currentDateTimeFormatted);
    }

    // Helper method to convert Patient Vital Record Created Dates
    public void addCommonIncidentReportDateAttributes(Model model) {
        // Fetch all PatientCases
        List<IncidentReport> allIncidentReports = incidentReportServ.getAll();

        // Extract createdAt dates from PatientCases
        List<LocalDateTime> createdAtDates = allIncidentReports.stream()
                .map(IncidentReport::getCreatedAt)
                .collect(Collectors.toList());

        // Convert createdAtDates to formatted date strings
        List<String> formattedPatientVitalRecordCreatedAtList = createdAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")))
                .collect(Collectors.toList());

        List<String> formattedPatientVitalRecordDayCreatedAtList = createdAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, yyyy")))
                .collect(Collectors.toList());

        model.addAttribute("allIncidentReportCreatedAt", formattedPatientVitalRecordCreatedAtList);
        model.addAttribute("allIncidentReportDayCreatedAt", formattedPatientVitalRecordDayCreatedAtList);

        // Set formatted date attributes for the current date in the model
        String currentDateTimeFormatted = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
        model.addAttribute("currentDateTime", currentDateTimeFormatted);
        model.addAttribute("dayCurrentDateTime", currentDateTimeFormatted);
    }
}
