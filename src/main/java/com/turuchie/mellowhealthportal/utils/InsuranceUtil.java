package com.turuchie.mellowhealthportal.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.CurrentMedication;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientVitalRecord;
import com.turuchie.mellowhealthportal.models.PatientOperations.InsuranceInformation;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.models.PatientOperations.RecentAdmission;
import com.turuchie.mellowhealthportal.models.PatientOperations.RecentEmergency;
import com.turuchie.mellowhealthportal.services.PhysicianService;
import com.turuchie.mellowhealthportal.services.PhysiciansPatientService;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientCaseService;
import com.turuchie.mellowhealthportal.services.ClinicalOperationsServices.PatientVitalRecordService;
import com.turuchie.mellowhealthportal.services.DiagnosticProceduresServices.CurrentMedicationService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.AdverseEffectService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.IncidentReportService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.InsuranceInformationService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.RecentAdmissionService;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.RecentEmergencyService;

@Component
public class InsuranceUtil {
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
	private InsuranceInformationService insuranceInformationServ;
	
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
    	formatAndSetAllPatientsDateAttributes(model);
    	setCurrentDateTimeAttributes(model);
    	formatAndSetAllPatientsDateAttributes(model);
    	setInsuranceReportProperty(model);
    	setMostRecentInsuranceReportProperty(model);
    	setMostRecentRecords(model);
    	formatAndSetInsuranceDateAttributes(model);
    	mapAllInsuranceInformationDayMonthYearAttributes(model);
    	setCommonModelInsuranceAttributes(model);
    	mapAllInsuranceInformationDayMonthYearAttributes(model);
    	calculateTotalCoverageLengthForAllInsuranceRecords(model, null);
    	formatAndSetInsuranceCoverageAttributes(model);
    	calculateDaysMonthsYearsTotalCoverageLengthForAllInsuranceRecords(model);
    }    

    // Helper method to add common model attributes
    public void addCommonModelAttributes(Model model) {
        model.addAttribute("allPatients", patientServ.findAll());
        model.addAttribute("allPhysicians", physicianServ.findAll());
        model.addAttribute("allPatientCases", patientCaseServ.getAll());
        model.addAttribute("allIncidentReports", incidentReportServ.getAll());
        model.addAttribute("allPhysiciansPatients", physiciansPatientServ.getAll());
        model.addAttribute("allPatientVitalRecords", patientVitalRecordServ.getAll());
        model.addAttribute("allInsuranceRecords", insuranceInformationServ.getAll());
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
        InsuranceInformation insuranceInformation = insuranceInformationServ.getMostRecentInsuranceInformation();

        // Add most recent records to the model
        model.addAttribute("mostRecentPatientCase", mostRecentPatientCase);
        model.addAttribute("mostRecentPatientVitalRecord", mostRecentPatientVitalRecord);
        model.addAttribute("mostRecentCurrentMedication", mostRecentCurrentMedication);
        model.addAttribute("mostRecentRecentEmergency", mostRecentRecentEmergency);
        model.addAttribute("mostRecentRecentAdmission", mostRecentRecentAdmission);
        model.addAttribute("mostRecentInsuranceInformation", insuranceInformation);
    }

    // Helper method to convert Date to LocalDateTime and format dates for InsuranceInformation
    public void formatAndSetInsuranceDateAttributes(Model model) {
        // Fetch all Patients
        Iterable<Patient> allPatients = patientServ.findAll();

        // Extract createdAt dates from PatientCases
        List<LocalDateTime> insuranceInformationCreatedAtDates = new ArrayList<>();

        for (Patient patient : allPatients) {
            for (InsuranceInformation insuranceInformation : patient.getInsuranceRecords()) {
                LocalDateTime createdAtDate = insuranceInformation.getCreatedAt();

                // Check for null before converting
                if (createdAtDate != null) {
                    // Corrected: Convert LocalDate to LocalDateTime
                    LocalDateTime dateTime = createdAtDate;
                    insuranceInformationCreatedAtDates.add(dateTime);
                }
            }
        }

        // List Population
        List<String> formattedInsuranceRecordCreatedAtList = insuranceInformationCreatedAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")))
                .collect(Collectors.toList());

        List<String> formattedInsuranceRecordDayCreatedAtList = insuranceInformationCreatedAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, yyyy")))
                .collect(Collectors.toList());

        model.addAttribute("insuranceRecordCreatedAtList", formattedInsuranceRecordCreatedAtList);
        model.addAttribute("insuranceRecordDayCreatedAtList", formattedInsuranceRecordDayCreatedAtList);

        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy");
        DateTimeFormatter dayformatter = DateTimeFormatter.ofPattern("EEE, yyyy");

        String currentDateTimeFormatted = currentDateTime.format(formatter);
        String dayCurrentDateTimeFormatted = currentDateTime.format(dayformatter);

        // Set formatted date attributes in the model
        model.addAttribute("currentDateTime", currentDateTimeFormatted);
        model.addAttribute("dayCurrentDateTime", dayCurrentDateTimeFormatted);
    }    
    
    // Helper method to Convert All  Date to LocalDateTime and format dates for InsuranceInformation
    public void formatAndSetInsuranceCoverageAttributes(Model model) {
        // Fetch all Patients
        Iterable<Patient> allPatients = patientServ.findAll();

        // Extract createdAt dates from PatientCases
        List<LocalDateTime> insuranceInformationCreatedAtDates = new ArrayList<>();
        List<Integer> lengthOfInsuranceCoverages = new ArrayList<>();

        for (Patient patient : allPatients) {
            for (InsuranceInformation insuranceInformation : patient.getInsuranceRecords()) {
                LocalDateTime createdAtDate = insuranceInformation.getCreatedAt();
                int insuranceLength = calculateCoverageInMonths(insuranceInformation.getStartDate(), insuranceInformation.getExpirationDate());

                // Check for null before converting
                if (createdAtDate != null) {
                    // Corrected: Convert LocalDate to LocalDateTime
                    LocalDateTime dateTime = createdAtDate;
                    insuranceInformationCreatedAtDates.add(dateTime);
                }

                int coverage = insuranceLength;
                lengthOfInsuranceCoverages.add(coverage);
            }
        }

        // List Population
        List<Integer> formattedCoverageLengthList = lengthOfInsuranceCoverages.stream()
                .map(insuranceLength -> insuranceLength)
                .collect(Collectors.toList());
        
        List<String> formattedInsuranceRecordCreatedAtList = insuranceInformationCreatedAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")))
                .collect(Collectors.toList());

        List<String> formattedInsuranceRecordDayCreatedAtList = insuranceInformationCreatedAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, yyyy")))
                .collect(Collectors.toList());

        model.addAttribute("insuranceLengthOfCoverages", formattedCoverageLengthList);
        model.addAttribute("insuranceCreatedAtList", formattedInsuranceRecordCreatedAtList);
        model.addAttribute("insuranceDayCreatedAtList", formattedInsuranceRecordDayCreatedAtList);
    }

    // Method To Convert Patients Insurance Date Ranges
    public void formatAndSetPatientInsuranceCoverageAttributes(Model model, Long patientId) {
        // Fetch the specific patient
        Patient patient = patientServ.getOne(patientId);

        // Extract createdAt dates from PatientCases
        List<Integer> lengthOfTotalInsuranceCoverages = new ArrayList<>();
        List<Integer> lengthOfRemainigInsuranceCoverages = new ArrayList<>();
        List<Integer> lengthOfInsuranceRecordHistory = new ArrayList<>();
        List<LocalDateTime> insuranceInformationCreatedAtDates = new ArrayList<>();

        for (InsuranceInformation insuranceInformation : patient.getInsuranceRecords()) {
            LocalDateTime createdAtDate = insuranceInformation.getCreatedAt();
            int insuranceAccountHistory = calculateLocaleDateTimeDiffference(LocalDateTime.now(), insuranceInformation.getCreatedAt());
            int remainingCoverageLength = calculateCoverageInMonths(LocalDate.now(), insuranceInformation.getExpirationDate());
            int totalCoverageLength = calculateCoverageInMonths(insuranceInformation.getStartDate(), insuranceInformation.getExpirationDate());

            // Check for null before converting
            if (createdAtDate != null) {
                // Corrected: Convert LocalDate to LocalDateTime
                LocalDateTime dateTime = createdAtDate;
                insuranceInformationCreatedAtDates.add(dateTime);
            }

            // Populate Remaining Valid Coverage
            int accountHistory = insuranceAccountHistory;
            lengthOfInsuranceRecordHistory.add(accountHistory);

            // Populate Total Coverage Length
            int totalCoverage = totalCoverageLength;
            lengthOfTotalInsuranceCoverages.add(totalCoverage);

            // Populate Remaining Valid Coverage
            int remainingCoverage = remainingCoverageLength;
            lengthOfRemainigInsuranceCoverages.add(remainingCoverage);

            
        }

        // List Population
        List<Integer> formattedInsuranceHistoryList = lengthOfInsuranceRecordHistory.stream()
                .map(accountHistory -> accountHistory)
                .collect(Collectors.toList());

        List<Integer> formattedTotalCoverageLengthList = lengthOfTotalInsuranceCoverages.stream()
                .map(insuranceLength -> insuranceLength)
                .collect(Collectors.toList());

        List<Integer> formattedRemainingCoverageLengthList = lengthOfRemainigInsuranceCoverages.stream()
                .map(remainingInsuranceLength -> remainingInsuranceLength)
                .collect(Collectors.toList());

        List<String> formattedInsuranceRecordCreatedAtList = insuranceInformationCreatedAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")))
                .collect(Collectors.toList());

        List<String> formattedInsuranceRecordDayCreatedAtList = insuranceInformationCreatedAtDates.stream()
                .map(dateTime -> dateTime.format(DateTimeFormatter.ofPattern("EEE, yyyy")))
                .collect(Collectors.toList());

        // Use a prefix like "patient" to avoid clashes in the model
        if (!formattedInsuranceHistoryList.isEmpty()) {
            model.addAttribute("patientInsuranceRecordAccountHistory", formattedInsuranceHistoryList);
        }

        if (!formattedInsuranceRecordCreatedAtList.isEmpty()) {
            model.addAttribute("patientInsuranceCreatedAtList", formattedInsuranceRecordCreatedAtList);
        }

        if (!formattedInsuranceRecordDayCreatedAtList.isEmpty()) {
            model.addAttribute("patientInsuranceDayCreatedAtList", formattedInsuranceRecordDayCreatedAtList);
        }

        if (!formattedRemainingCoverageLengthList.isEmpty()) {
            model.addAttribute("patientRemainingInsuranceLengthOfCoverages", formattedRemainingCoverageLengthList);
        }

        if (!formattedTotalCoverageLengthList.isEmpty()) {
            model.addAttribute("patientTotalInsuranceLengthOfCoverages", formattedTotalCoverageLengthList);
        }
    }

    public int calculateCoverageInMonths(LocalDate startDate, LocalDate expirationDate) {
        if (startDate != null && expirationDate != null) {
            int result = (int) Period.between(startDate, expirationDate).toTotalMonths();
            return result;
        } else {
            return -1; // or handle it in a way that makes sense for your application
        }
    }

    // Formatted Insurance Records And Length Of Coverage
    public void mapAllInsuranceInformationDayMonthYearAttributes(Model model) {
        // Fetch all Patient Insurance Record
        List<InsuranceInformation> allInsuranceRecords = insuranceInformationServ.getAll();

        // Filter out InsuranceRecords with null start dates or null expiration dates
        List<InsuranceInformation> validInsuranceRecords = allInsuranceRecords.stream()
                .filter(oneRecord -> oneRecord.getStartDate() != null && oneRecord.getExpirationDate() != null)
                .collect(Collectors.toList());

        // Create a java.util.Map to store the association between each InsuranceInformation and its length
        java.util.Map<InsuranceInformation, Integer> coverageLengthMap = new HashMap<>();

        // Calculate lengths of medical conditions and populate the map
        for (InsuranceInformation oneRecord : validInsuranceRecords) {
            LocalDate startDate = oneRecord.getStartDate();
            LocalDate expirationDate = oneRecord.getExpirationDate();
            int lengthOfCoverage = (int) ChronoUnit.MONTHS.between(startDate, expirationDate);
            coverageLengthMap.put(oneRecord, lengthOfCoverage);
        }

        // Additional attributes for individual components of the created-at date based on expiration date
        model.addAttribute("mappedFormattedTotalMonthCoverages", new HashMap<>(coverageLengthMap));
        model.addAttribute("formattedTotalMonthCoverages", coverageLengthMap);
        model.addAttribute("insuranceRecordDays", validInsuranceRecords.stream()
                .map(oneRecord -> oneRecord.getExpirationDate().getDayOfMonth())
                .collect(Collectors.toList()));
        model.addAttribute("insuranceRecordMonths", validInsuranceRecords.stream()
                .map(oneRecord -> oneRecord.getExpirationDate().getMonth())
                .collect(Collectors.toList()));
        model.addAttribute("insuranceRecordYears", validInsuranceRecords.stream()
                .map(oneRecord -> oneRecord.getExpirationDate().getYear())
                .collect(Collectors.toList()));
    }

    // Helper method to create a list of total length of coverage for each insurance record
    public List<Integer> calculateTotalDaysCoverageLengthForAllInsuranceRecords(Model model) {
        List<InsuranceInformation> allInsuranceRecords = insuranceInformationServ.getAll();

        return allInsuranceRecords.stream()
                .filter(insuranceRecord -> insuranceRecord.getStartDate() != null && insuranceRecord.getExpirationDate() != null)
                .map(insuranceRecord -> {
                    int totalCoverageInMonths = (int) calculateDateDifference(insuranceRecord.getStartDate(), insuranceRecord.getExpirationDate(), ChronoUnit.MONTHS);
                    int totalCoverageInDays = (int) calculateDateDifference(insuranceRecord.getStartDate(), insuranceRecord.getExpirationDate(), ChronoUnit.DAYS);
                    int totalCoverageInYears = (int) calculateDateDifference(insuranceRecord.getStartDate(), insuranceRecord.getExpirationDate(), ChronoUnit.YEARS);

                    // Add individual values to the model
                    model.addAttribute("totalMonthCoverageList", totalCoverageInMonths);
                    model.addAttribute("totalDaysCoverageList", totalCoverageInDays);
                    model.addAttribute("totalYearsCoverageList", totalCoverageInYears);

                    // Return the total coverage in years to be collected in the list
                    return totalCoverageInDays;
                })
                .collect(Collectors.toList());
    }

    // Helper method to create a list of total length of coverage for each insurance record
    public List<Integer> calculateTotalMonthCoverageLengthForAllInsuranceRecords(Model model) {
        List<InsuranceInformation> allInsuranceRecords = insuranceInformationServ.getAll();

        return allInsuranceRecords.stream()
                .filter(insuranceRecord -> insuranceRecord.getStartDate() != null && insuranceRecord.getExpirationDate() != null)
                .map(insuranceRecord -> {
                    int totalCoverageInMonths = (int) calculateDateDifference(insuranceRecord.getStartDate(), insuranceRecord.getExpirationDate(), ChronoUnit.MONTHS);
                    int totalCoverageInDays = (int) calculateDateDifference(insuranceRecord.getStartDate(), insuranceRecord.getExpirationDate(), ChronoUnit.DAYS);
                    int totalCoverageInYears = (int) calculateDateDifference(insuranceRecord.getStartDate(), insuranceRecord.getExpirationDate(), ChronoUnit.YEARS);
                    model.addAttribute("insuranceLengthList", calculateCoverageInMonths(insuranceRecord.getStartDate(), insuranceRecord.getExpirationDate()));
                    // Add individual values to the model
                    model.addAttribute("totalMonthCoverageList", totalCoverageInMonths);
                    model.addAttribute("totalDaysCoverageList", totalCoverageInDays);
                    model.addAttribute("totalYearsCoverageList", totalCoverageInYears);

                    // Return the total coverage in years to be collected in the list
                    return totalCoverageInMonths;
                })
                .collect(Collectors.toList());
    }

    // Helper method to create a list of total length of coverage for each insurance record
    public List<Integer> calculateTotalYearCoverageLengthForAllInsuranceRecords(Model model) {
        List<InsuranceInformation> allInsuranceRecords = insuranceInformationServ.getAll();

        return allInsuranceRecords.stream()
                .filter(insuranceRecord -> insuranceRecord.getStartDate() != null && insuranceRecord.getExpirationDate() != null)
                .map(insuranceRecord -> {
                    int totalCoverageInMonths = (int) calculateDateDifference(insuranceRecord.getStartDate(), insuranceRecord.getExpirationDate(), ChronoUnit.MONTHS);
                    int totalCoverageInDays = (int) calculateDateDifference(insuranceRecord.getStartDate(), insuranceRecord.getExpirationDate(), ChronoUnit.DAYS);
                    int totalCoverageInYears = (int) calculateDateDifference(insuranceRecord.getStartDate(), insuranceRecord.getExpirationDate(), ChronoUnit.YEARS);

                    // Add individual values to the model
                    model.addAttribute("totalMonthCoverageList", totalCoverageInMonths);
                    model.addAttribute("totalDaysCoverageList", totalCoverageInDays);
                    model.addAttribute("totalYearsCoverageList", totalCoverageInYears);

                    // Return the total coverage in years to be collected in the list
                    return totalCoverageInYears;
                })
                .collect(Collectors.toList());
    }

    
 // One Single Helper method to create a list of total length of coverage for each insurance record
    public List<Integer> calculateTotalCoverageLengthForAllInsuranceRecords(Model model, ChronoUnit unit) {
        if (unit != null) {
        List<InsuranceInformation> allInsuranceRecords = insuranceInformationServ.getAll();

        return allInsuranceRecords.stream()
                .filter(insuranceRecord -> insuranceRecord.getStartDate() != null && insuranceRecord.getExpirationDate() != null)
                .map(insuranceRecord -> {
                    int totalCoverage = (int) calculateDateDifference(insuranceRecord.getStartDate(), insuranceRecord.getExpirationDate(), unit);
                    return totalCoverage;
                })
                .collect(Collectors.toList());
        }
		return null;
    }

    public void setDateLength(Model model, ChronoUnit unit) {
        if (unit != null) {
            List<Integer> totalMonthCoverages = calculateTotalCoverageLengthForAllInsuranceRecords(model, ChronoUnit.MONTHS);
            List<Integer> totalDayCoverages = calculateTotalCoverageLengthForAllInsuranceRecords(model, ChronoUnit.DAYS);
            List<Integer> totalYearCoverages = calculateTotalCoverageLengthForAllInsuranceRecords(model, ChronoUnit.YEARS);

            // Add the lists to the model if needed
            model.addAttribute("totalFormattedMonthCoverages", totalMonthCoverages);
            model.addAttribute("totalFormattedDayCoverages", totalDayCoverages);
            model.addAttribute("totalFormattedYearCoverages", totalYearCoverages);
        } else {
            // Handle the case where the unit is null, perhaps log an error or throw an exception
            // Example: throw new IllegalArgumentException("Unit cannot be null");
        }
    }

    // Helper method to set date-related attributes
    public void setCommonModelInsuranceAttributes(Model model) {
        List<Integer> totalCoverageLengthList = calculateTotalMonthCoverageLengthForAllInsuranceRecords(model);
        model.addAttribute("totalCoverageLength", totalCoverageLengthList);
    }

    // Calculate Date Difference method (similar to the one in your code)
    public int calculateDateDifference(LocalDate startDate, LocalDate endDate, ChronoUnit unit) {
        return (int) unit.between(startDate, endDate);
    }

    // Date Differences..............................................

    public long calculateCoverageInDays(LocalDate startDate, LocalDate expirationDate) {
        if (startDate != null && expirationDate != null) {
            return ChronoUnit.DAYS.between(startDate, expirationDate);
        } else {
            return -1; // or handle it in a way that makes sense for your application
        }
    }

    public long calculateCoverageInYears(LocalDate startDate, LocalDate expirationDate) {
        if (startDate != null && expirationDate != null) {
            return ChronoUnit.YEARS.between(startDate, expirationDate);
        } else {
            return -1; // or handle it in a way that makes sense for your application
        }
    }

    // Helper method to set date-related attributes
    public void calculateDaysMonthsYearsTotalCoverageLengthForAllInsuranceRecords(Model model) {
        List<InsuranceInformation> allInsuranceRecords = insuranceInformationServ.getAll();

        allInsuranceRecords.forEach(insuranceRecord -> {
            if (insuranceRecord.getStartDate() != null && insuranceRecord.getExpirationDate() != null) {
                int totalCoverageInMonths = (int) ChronoUnit.MONTHS.between(insuranceRecord.getStartDate(), insuranceRecord.getExpirationDate());
                int totalCoverageInDays = (int) ChronoUnit.DAYS.between(insuranceRecord.getStartDate(), insuranceRecord.getExpirationDate());
                int totalCoverageInYears = (int) ChronoUnit.YEARS.between(insuranceRecord.getStartDate(), insuranceRecord.getExpirationDate());

                // Add individual values to the model
                model.addAttribute("totalCoverageInMonths", totalCoverageInMonths);
                model.addAttribute("totalCoverageInDays", totalCoverageInDays);
                model.addAttribute("totalCoverageInYears", totalCoverageInYears);
            }
        });
    }

    public void setMostRecentInsuranceReportProperty(Model model, Long id) {
        // Fetch the logged-in patient
        InsuranceInformation oneInsuranceRecord = insuranceInformationServ.getOne(id);

        // Date Ranges
        int coveragePeriod = calculateLocalDateDifference(LocalDate.now(), oneInsuranceRecord.getExpirationDate());
        int totalInsuredPeriod = calculateLocalDateDifference(oneInsuranceRecord.getStartDate(), oneInsuranceRecord.getExpirationDate());

        model.addAttribute("oneCoveragePeriod", coveragePeriod);
        model.addAttribute("oneTotalInsuredPeriod", totalInsuredPeriod);
        model.addAttribute("oneInsuranceReport", oneInsuranceRecord);
    }

    // Set Date Ranges
    public int setDateRange(Model model, LocalDate dateObj) {
        // Null check for date obj
        if (dateObj == null) {
            // Handle the case where date of birth is null
        }

        // Check if the date year is within the specified range
        if (!validateYear(dateObj.getYear())) {
            // Handle the case where the birth year is not valid
        }

        // Calculate patient age
        int dateDifference = Period.between(dateObj, LocalDate.now()).getYears();
        
        return dateDifference;
    }

    // Method To Calculate Length Of Local Date
    public int calculateLocalDateDifference(LocalDate startDate, LocalDate endDate) {  
        int dateMonthDifference = (int) ChronoUnit.MONTHS.between(startDate, endDate);
        return dateMonthDifference;
    }

    // Method To Calculate Length Of Local Date
    public int calculateLocaleDateTimeDiffference(LocalDateTime startDate, LocalDateTime endDate) {
        int dateDifference = (int) ChronoUnit.MONTHS.between(startDate, endDate);
        return dateDifference;
    }

    // Method To Calculate and Validate Length Of Local Date
    public int calculateAndValidateLocaleDateDifference(LocalDate startDate, LocalDate endDate) {
        // Null check for date object
        if (startDate == null || endDate == null) {
            // Handle the case where date of birth is null
            return 0; // or throw an exception based on your requirements
        }

        // Check if the date year is within the specified range
        if (!validateFutueDates(startDate)) {
            // Handle the case where the birth year is not valid
            return 0; // or throw an exception based on your requirements
        }

        int dateYearDifference = (int) ChronoUnit.YEARS.between(startDate, endDate);
        return dateYearDifference;
    }

    // Year Validation
    public boolean validateYear(int yearInteger) {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int minValidYear = currentYear - 150; // 150 years ago
        int maxValidYear = currentYear; // Current year

        return yearInteger >= minValidYear && yearInteger <= maxValidYear && yearInteger <= currentYear;
    }

    // Validate Against Future Years
    public boolean validateFutueDates(LocalDate dateObj) {
        if (dateObj == null) {
            return false;
        }

        LocalDate currentDate = LocalDate.now();

        // Validate Against Future Years
        int minimumValidYear = currentDate.getYear() - 150;

        // Check if birth date is in the past, not in the future
        if (dateObj.isAfter(currentDate)) {
            return false;
        }

        // Check if date year is within the specified range
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
}