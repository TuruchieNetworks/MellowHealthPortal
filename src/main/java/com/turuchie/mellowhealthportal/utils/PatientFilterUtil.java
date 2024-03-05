package com.turuchie.mellowhealthportal.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.CurrentMedication;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.DoseRegimen;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientVitalRecord;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.DiagnosticRecord;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PainAssessment;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PhysicalAssessment;
import com.turuchie.mellowhealthportal.models.PatientOperations.IncidentReport;
import com.turuchie.mellowhealthportal.models.PatientOperations.InsuranceInformation;
import com.turuchie.mellowhealthportal.models.PatientOperations.PastMedicalHistory;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;

@Component
public class PatientFilterUtil {
	@Autowired
	private PatientService patientServ;

	@Autowired
	private InsuranceUtil insuranceUtil;

	// Sort All Attributes
    public void sortAllByStartDate(Model model, Long patientId) {
    	sortMostRecentPastMedicalRecord(model, patientId);
        sortMostRecentPatientCaseByStartDate(model, patientId);
        sortMostRecentIncidentReportByStartDate(model, patientId);
        setMostRecentInsuranceReportProperty(model, patientId);
        sortMostRecentDoseRegimenByStartDate(model, patientId);
        sortMostRecentCurrentMedication(model, patientId);
        sortMostRecentPainAssessment(model, patientId);
        sortMostRecentPhysicalAssessment(model, patientId);
        addPhysicalAssessmentInfoToModel(model, patientId);
        setMostRecentDiagnosticReportProperty(model, patientId);
        sortMostRecentPainAssessmentByStartDate(model, patientId);
        sortMostRecentCurrentMedicationByStartDate(model, patientId);
        sortMostRecentPhysicalAssessmentByStartDate(model, patientId);
        sortMostRecentPatientVitalRecordByStartDate(model, patientId);
        insuranceUtil.formatAndSetPatientInsuranceCoverageAttributes(model, patientId);
    }

    // Helper method to sort past medical history by start date
    public PastMedicalHistory sortMostRecentPastMedicalRecord(Model model, Long patientId) {
        // Fetch the logged-in patient
        Patient loggedInPatient = patientServ.getOne(patientId);

        // Get the past medical records for the logged-in patient
        List<PastMedicalHistory> pastMedicalRecords = loggedInPatient.getPastMedicalHistories();

        // Sort the past medical records by start date in descending order
        pastMedicalRecords.sort(Comparator.comparing(PastMedicalHistory::getStartDate).reversed());

        // Get the most recent past medical record (if any)
        PastMedicalHistory mostRecentPastMedicalRecord = pastMedicalRecords.isEmpty() ? null : pastMedicalRecords.get(0);

        model.addAttribute("mostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
        return mostRecentPastMedicalRecord;
    }

    // Helper method to sort past medical history by start date
    public PhysicalAssessment sortMostRecentPhysicalAssessment(Model model, Long patientId) {
        // Fetch the logged-in patient
        Patient loggedInPatient = patientServ.getOne(patientId);

        // Get the physical assessments for the logged-in patient
        List<PhysicalAssessment> assessmentRecords = loggedInPatient.getPhysicalAssessments();

        // Sort the physical assessments by start date in descending order
        assessmentRecords.sort(Comparator.comparing(PhysicalAssessment::getCreatedAt).reversed());

        // Get the most recent physical assessment (if any)
        PhysicalAssessment mostRecentAssessmentRecords = assessmentRecords.isEmpty() ? null : assessmentRecords.get(0);

        model.addAttribute("mostRecentAssessmentRecord", mostRecentAssessmentRecords);
        return mostRecentAssessmentRecords;
    }

    
 // Calculate Assessment History and create a list for patients' physical assessments
    public long calculateTotalLengthOfPhysicalAssessments(List<PhysicalAssessment> physicalAssessmentList) {
        long totalLength = 0;

        for (PhysicalAssessment assessmentHistory : physicalAssessmentList) {
            int lengthOfAssessmentRecord = calculateDaysLocalDateDifference(assessmentHistory.getCreatedAt(), LocalDate.now());
            totalLength += lengthOfAssessmentRecord;
        }

        return totalLength;
    }

    // In PatientFilterUtil or your service class
    public List<PhysicalAssessment> getPhysicalAssessmentsSortedByDate(Model model, Long patientId) {
        // Fetch the logged-in patient
        Patient loggedInPatient = patientServ.getOne(patientId);

        // Get the physical assessments for the logged-in patient
        List<PhysicalAssessment> allAssessmentRecords = loggedInPatient.getPhysicalAssessments();

        // Calculate the total length of medical conditions
        long allAssessmentHistories = calculateTotalLengthOfPhysicalAssessments(allAssessmentRecords);

        // Add the total length to the model
        model.addAttribute("assessmentHistories", allAssessmentHistories);

        // Sort the physical assessments by created date in descending order
        allAssessmentRecords.sort(Comparator.comparing(PhysicalAssessment::getCreatedAt).reversed());

        return allAssessmentRecords;
    }

 // In your service or controller class
    public void addPhysicalAssessmentInfoToModel(Model model, Long patientId) {
        // Fetch the logged-in patient
        Patient loggedInPatient = patientServ.getOne(patientId);

        // Get the physical assessments for the logged-in patient
        List<PhysicalAssessment> allAssessmentRecords = loggedInPatient.getPhysicalAssessments();

        // Calculate the total length of physical assessments
        long allAssessmentHistories = calculateTotalLengthOfPhysicalAssessments(allAssessmentRecords);

        // Add the total length to the model
        model.addAttribute("assessmentHistories", allAssessmentHistories);

        // You can also add the list of assessments to the model if needed
        model.addAttribute("allPhysicalAssessmentRecords", allAssessmentRecords);
    }

    // Helper method to sort patient cases by start date
    public void sortMostRecentPatientCaseByStartDate(Model model, Long patientId) {
        Patient loggedInPatient = patientServ.getOne(patientId);
        // Case where loggedInPatient is null
        if (loggedInPatient == null) {
            return;
        }

        List<PatientCase> patientCases = loggedInPatient.getPatientCases();
        //Case where patientCases is null or empty
        if (patientCases == null || patientCases.isEmpty()) {
            return;
        }

        patientCases.sort(Comparator.comparing(PatientCase::getCreatedAt).reversed());
        PatientCase mostRecentPatientCase = patientCases.get(0);

        // Calculate date differences
        LocalDate onset = mostRecentPatientCase.getOnset();
        LocalDate createdAt = mostRecentPatientCase.getCreatedAt().toLocalDate();
        LocalDate searchedPatientBirthDay = mostRecentPatientCase.getPatient().getDateOfBirth();
 
        // Date Ranges
        int onsetHistory = calculateLocalDateDifference(mostRecentPatientCase.getOnset(), LocalDate.now() );
        int visitHistory = calculateDaysLocalDateDifference(createdAt, LocalDate.now());
        
        long accountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
        long accountLengthDays = calculateLocalDateDifference(createdAt, LocalDate.now());
        long accountLengthMonths = calculateLocalDateDifference(createdAt, LocalDate.now());

        long conditionLengthYears = calculateLocalDateDifference(onset, LocalDate.now());
        long conditionLengthDays = calculateLocalDateDifference(onset, LocalDate.now());
        long conditionLengthMonths = calculateLocalDateDifference(onset, LocalDate.now());
        long searchedPatientAge = calculateLocalDateDifference(searchedPatientBirthDay, LocalDate.now());

        // Use PatientFilterUtil to get the most recent PastMedicalHistory
        PastMedicalHistory mostRecentPastMedicalRecord = sortMostRecentPastMedicalRecord(model, patientId);

        // Calculate length of medical condition
        if (mostRecentPastMedicalRecord != null) {
            LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
            long searchedPatientLengthOfMedicalCondition = calculateLocalDateDifference(medicalConditionStartDate, LocalDate.now());
	        model.addAttribute("searchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
	        model.addAttribute("searchedPatientMostRecentLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
	        model.addAttribute("searchedMostRecentPastMedicalRecordDayCreatedAt", mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	        model.addAttribute("oneSearchedMostRecentPastMedicalRecordCreatedAt", mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		 }
        	// Date Formatting
	        model.addAttribute("mostRecentOnsetHistory", onsetHistory);
	        model.addAttribute("mostRecentVisitHistory", visitHistory);
	        model.addAttribute("mostRecentPatientCase", mostRecentPatientCase);
	        model.addAttribute("searchedPatientAge", searchedPatientAge);
	        model.addAttribute("searchedPatientCaseAccountLength", accountLengthYears);
	        model.addAttribute("patientCaseAccountLengthDays", accountLengthDays);
	        model.addAttribute("patientCaseAccountLengthMonths", accountLengthMonths);
	        model.addAttribute("lengthOfPatientConditionYears", conditionLengthYears);
	        model.addAttribute("lengthOfPatientConditionDays", conditionLengthDays);
	        model.addAttribute("lengthOfPatientConditionMonths", conditionLengthMonths);
	        model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
	        model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
	        model.addAttribute("oneSearchedPatientCaseDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	        model.addAttribute("oneSearchedPatientCaseCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	        model.addAttribute("mostRecentPatientCaseDayCreatedAt", mostRecentPatientCase.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	        model.addAttribute("mostRecentPatientCaseCreatedAt", mostRecentPatientCase.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
   
   	}

    // Sort Current Medication By Start Date
    public CurrentMedication sortMostRecentCurrentMedication(Model model, Long patientId) {
        // Fetch the logged-in patient
        Patient loggedInPatient = patientServ.getOne(patientId);

        // Get the past medical records for the logged-in patient
        List<CurrentMedication> currentMedications = loggedInPatient.getCurrentMedications();

        // Sort the past medical records by start date in descending order
        currentMedications.sort(Comparator.comparing(CurrentMedication::getStartDate).reversed());

        // Get the most recent past medical record (if any)
        CurrentMedication mostRecentCurrentMedication = currentMedications.isEmpty() ? null : currentMedications.get(0);

        model.addAttribute("mostRecentCurrentMedication", mostRecentCurrentMedication);
        return mostRecentCurrentMedication;
    } 

    // Sort Pain Assessment By Created At
    public PainAssessment sortMostRecentPainAssessment(Model model, Long patientId) {
        // Fetch the logged-in patient
        Patient loggedInPatient = patientServ.getOne(patientId);

        // Get the past medical records for the logged-in patient
        List<PainAssessment> painAssessments = loggedInPatient.getPainAssessments();

        // Sort the past medical records by start date in descending order
        painAssessments.sort(Comparator.comparing(PainAssessment::getCreatedAt).reversed());

        // Get the most recent past medical record (if any)
        PainAssessment mostRecentPainAssessment = painAssessments.isEmpty() ? null : painAssessments.get(0);

        model.addAttribute("mostRecentPainAssessment", mostRecentPainAssessment);
        return mostRecentPainAssessment;
    }

    // Helper method to sort patient vital records by start date
    public void sortMostRecentPatientVitalRecordByStartDate(Model model, Long patientId) {
        Patient loggedInPatient = patientServ.getOne(patientId);
        List<PatientVitalRecord> patientVitalRecords = loggedInPatient.getPatientVitalRecords();
        patientVitalRecords.sort(Comparator.comparing(PatientVitalRecord::getCreatedAt).reversed());
        PatientVitalRecord mostRecentPatientVitalRecord = patientVitalRecords.isEmpty() ? null : patientVitalRecords.get(0);
        model.addAttribute("mostRecentPatientVitalRecord", mostRecentPatientVitalRecord);
    }

    // Helper method to sort incident reports by start date
    public void sortMostRecentIncidentReportByStartDate(Model model, Long patientId) {
        Patient loggedInPatient = patientServ.getOne(patientId);
        List<IncidentReport> incidentReports = loggedInPatient.getIncidentReports();
        incidentReports.sort(Comparator.comparing(IncidentReport::getCreatedAt).reversed());
        IncidentReport mostRecentIncidentReport = incidentReports.isEmpty() ? null : incidentReports.get(0);
        model.addAttribute("mostRecentIncidentReport", mostRecentIncidentReport);
    }

    public InsuranceInformation setMostRecentInsuranceReportProperty(Model model, Long patientId) {
        // Fetch the logged-in patient
        Patient loggedInPatient = patientServ.getOne(patientId);

        // Get the insurance records for the logged-in patient
        List<InsuranceInformation> insuranceRecords = loggedInPatient.getInsuranceRecords();

        // Sort the insurance records by start date in descending order
        insuranceRecords.sort(Comparator.comparing(InsuranceInformation::getStartDate).reversed());

        // Get the most recent insurance report (if any)
        InsuranceInformation mostRecentInsuranceInformation = insuranceRecords.isEmpty() ? null : insuranceRecords.get(0);

        if (mostRecentInsuranceInformation != null) { // Add null check
            // Date Ranges
            int coveragePeriod = calculateLocalDateDifference(LocalDate.now(), mostRecentInsuranceInformation.getExpirationDate());
            int totalInsuredPeriod = calculateAndValidateLocaleDateDifference(mostRecentInsuranceInformation.getStartDate(), mostRecentInsuranceInformation.getExpirationDate());

            model.addAttribute("mostRecentCoveragePeriod", coveragePeriod);
            model.addAttribute("mostRecentTotalInsuredPeriod", totalInsuredPeriod);
            model.addAttribute("mostRecentInsuranceReport", mostRecentInsuranceInformation);
            model.addAttribute("mostRecentRecordDayCreatedAt", mostRecentInsuranceInformation.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
            model.addAttribute("mostRecentRecordCreatedAt", mostRecentInsuranceInformation.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
        }

        return mostRecentInsuranceInformation;
    }

    // Most Recent Diagnostic Report
    public DiagnosticRecord setMostRecentDiagnosticReportProperty(Model model, Long patientId) {
        // Fetch the logged-in patient
        Patient loggedInPatient = patientServ.getOne(patientId);

        // Get the diagnostic records for the logged-in patient
        List<DiagnosticRecord> diagnosticRecords = loggedInPatient.getDiagnosticRecords();

        // Sort the diagnostic records by start date in descending order
        diagnosticRecords.sort(Comparator.comparing(DiagnosticRecord::getCreatedAt).reversed());

        // Get the most recent diagnostic report (if any)
        DiagnosticRecord mostRecentDiagnosticRecord = diagnosticRecords.isEmpty() ? null : diagnosticRecords.get(0);

        if (mostRecentDiagnosticRecord != null) { // Add null check
            // Date Ranges
        	LocalDate diagnosticCreatedAt =  mostRecentDiagnosticRecord.getCreatedAt().toLocalDate();
            int mostRecentDiagnosticHistory = calculateLocalDateDifference(LocalDate.now(), diagnosticCreatedAt);
            String formattedDiagnosticDayCreatedAtDate = diagnosticCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
            String formattedDiagnosticCreatedAtDate = diagnosticCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

            model.addAttribute("mostRecentDiagnosticReport", mostRecentDiagnosticRecord);
            model.addAttribute("mostRecentDiagnosticHistory", mostRecentDiagnosticHistory);
            model.addAttribute("mostRecentDiagnosticRecordCreatedAt", formattedDiagnosticCreatedAtDate);
            model.addAttribute("mostRecentDiagnosticRecordDayCreatedAt", formattedDiagnosticDayCreatedAtDate);
        }

        return mostRecentDiagnosticRecord;
    }

    // Set Date Ranges
    public int setDateRange(Model model, LocalDate dateObj) {
        // Null check for date object
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
        int dateDifference = (int) ChronoUnit.YEARS.between(startDate, endDate);
        return dateDifference;
    }

    // Method To Calculate Length Of Local Date Time
    public int calculateLocaleDateTimeDiffference(LocalDateTime startDate, LocalDateTime endDate) {
        int dateDifference = (int) ChronoUnit.YEARS.between(startDate, endDate);
        return dateDifference;
    }

    // Method To Calculate Length Of Local Date
    public int calculateDaysLocalDateDifference(LocalDate startDate, LocalDate endDate) {  
        int dateMonthDifference = (int) ChronoUnit.DAYS.between(startDate, endDate);
        return dateMonthDifference;
    }

    // Method To Calculate Length Of Local Date Time
    public int calculateDaysLocaleDateTimeDiffference(LocalDateTime startDate, LocalDateTime endDate) {
        int dateDifference = (int) ChronoUnit.DAYS.between(startDate, endDate);
        return dateDifference;
    }

    // Method To Calculate Length Of Local Date
    public int calculateWeeksLocalDateDifference(LocalDate startDate, LocalDate endDate) {  
        int dateMonthDifference = (int) ChronoUnit.WEEKS.between(startDate, endDate);
        return dateMonthDifference;
    }

    // Method To Calculate Length Of Local Date Time
    public int calculateWeeksLocaleDateTimeDiffference(LocalDateTime startDate, LocalDateTime endDate) {
        int dateDifference = (int) ChronoUnit.WEEKS.between(startDate, endDate);
        return dateDifference;
    }

    // Method To Calculate and Validate Length Of Local Date
    public int calculateAndValidateLocaleDateDifference(LocalDate startDate, LocalDate endDate) {
        // Null check for date obj
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

    // Method to validate if previous admission is within the entire coverage period
    public boolean isPreviousAdmissionWithinCoverage(InsuranceInformation insuranceInformation, LocalDate previousAdmissionDate) {
        if (insuranceInformation != null && insuranceInformation.getStartDate() != null &&
                insuranceInformation.getExpirationDate() != null && previousAdmissionDate != null) {
            return (previousAdmissionDate.isEqual(insuranceInformation.getStartDate()) ||
                    (previousAdmissionDate.isAfter(insuranceInformation.getStartDate()) &&
                     previousAdmissionDate.isBefore(insuranceInformation.getExpirationDate().plusDays(1))));
        } else {
            return false; // or handle it in a way that makes sense for your application
        }
    }

    // Helper method to sort patient cases by start date
    public void sortMostRecentDoseRegimenByStartDate(Model model, Long patientId) {
        Patient loggedInPatient = patientServ.getOne(patientId);
        
        if (loggedInPatient == null) {
            // Handle the case where loggedInPatient is null
            return;
        }

        List<DoseRegimen> doseRegimenRecords = loggedInPatient.getDoseRegimenRecords();

        if (doseRegimenRecords == null || doseRegimenRecords.isEmpty()) {
            // Handle the case where doseRegimenRecords is null or empty
            return;
        }

        doseRegimenRecords.sort(Comparator.comparing(DoseRegimen::getCreatedAt).reversed());
        DoseRegimen mostRecentDoseRegimen = doseRegimenRecords.get(0);

        // Calculate date differences
        LocalDate createdAt = mostRecentDoseRegimen.getCreatedAt().toLocalDate();
        LocalDate searchedPatientBirthDay = mostRecentDoseRegimen.getPatient().getDateOfBirth();
 
        // Date Ranges
        int regimenHistory = calculateDaysLocalDateDifference(createdAt, LocalDate.now()); 
        long regimenAccountLengthDays = calculateLocalDateDifference(createdAt, LocalDate.now());  
        long regimenAccountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
        long regimenAccountLengthMonths = calculateLocalDateDifference(createdAt, LocalDate.now());
        long searchedPatientAge = calculateLocalDateDifference(searchedPatientBirthDay, LocalDate.now());

        // Use PatientFilterUtil to get the most recent PastMedicalHistory
        PastMedicalHistory mostRecentPastMedicalRecord = sortMostRecentPastMedicalRecord(model, patientId);

        // Calculate length of medical condition
        if (mostRecentPastMedicalRecord != null) {
	            LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	            long searchedPatientLengthOfMedicalCondition = calculateLocalDateDifference(medicalConditionStartDate, LocalDate.now());
	            String formattedPastMedicalCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	            String formattedDayPastMedicalCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	            model.addAttribute("lengthOfPatientConditionYears", regimenAccountLengthYears);
		        model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
		        model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
		        model.addAttribute("searchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
		        model.addAttribute("searchedPatientMostRecentLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
		        model.addAttribute("searchedMostRecentPastMedicalRecordDayCreatedAt", formattedPastMedicalCreatedAtDate);
		        model.addAttribute("oneSearchedMostRecentPastMedicalRecordCreatedAt", formattedDayPastMedicalCreatedAtDate);
		 }
		        // Date Formatting
		        model.addAttribute("searchedPatientAge", searchedPatientAge);
		        model.addAttribute("mostRecentDoseRegimen", mostRecentDoseRegimen);
		        model.addAttribute("searchedDoseRegimenDaysHistory", regimenAccountLengthDays);
		        model.addAttribute("searchedDoseRegimenMonthsHistory", regimenAccountLengthMonths);
		        model.addAttribute("searchedDoseRegimenYearsHistory", regimenAccountLengthYears);
		        model.addAttribute("searchedDoseRegimenHistoryList", regimenHistory);
		        model.addAttribute("oneSearchedDoseRegimenDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		        model.addAttribute("oneSearchedDoseRegimenCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		        model.addAttribute("mostRecentDoseRegimenDayCreatedAt", mostRecentDoseRegimen.getCreatedAt());
		        model.addAttribute("mostRecentDoseRegimenCreatedAt", mostRecentDoseRegimen.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		   
    	}
  
    // Helper method to sort patient current medications by start date
    public void sortMostRecentCurrentMedicationByStartDate(Model model, Long patientId) {
        Patient loggedInPatient = patientServ.getOne(patientId);
        
        if (loggedInPatient == null) {
            // Handle the case where loggedInPatient is null
            return;
        }

        List<CurrentMedication> currentMedicationRecords = loggedInPatient.getCurrentMedications();

        if (currentMedicationRecords == null || currentMedicationRecords.isEmpty()) {
            // Handle the case where currentMedicationRecords is null or empty
            return;
        }

        currentMedicationRecords.sort(Comparator.comparing(CurrentMedication::getCreatedAt).reversed());
        CurrentMedication mostRecentCurrentMedication = currentMedicationRecords.get(0);

        // Calculate date differences
        LocalDate createdAt = mostRecentCurrentMedication.getCreatedAt().toLocalDate();
        LocalDate searchedPatientBirthDay = mostRecentCurrentMedication.getPatient().getDateOfBirth();

        String formattedCreatedAtDate = mostRecentCurrentMedication.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy"));
        String formattedDayCreatedAtDate = mostRecentCurrentMedication.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
 
        // Date Ranges
        int currentMedicationHistory = calculateDaysLocalDateDifference(createdAt, LocalDate.now()); 
        long currentMedicationAccountLengthDays = calculateLocalDateDifference(createdAt, LocalDate.now());  
        long currentMedicationAccountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
        long currentMedicationAccountLengthMonths = calculateLocalDateDifference(createdAt, LocalDate.now());
        long searchedPatientAge = calculateLocalDateDifference(searchedPatientBirthDay, LocalDate.now());

        // Use PatientFilterUtil to get the most recent PastMedicalHistory
        PastMedicalHistory mostRecentPastMedicalRecord = sortMostRecentPastMedicalRecord(model, patientId);

        // Calculate length of medical condition
        if (mostRecentPastMedicalRecord != null) {
	            LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	            long searchedPatientLengthOfMedicalCondition = calculateLocalDateDifference(medicalConditionStartDate, LocalDate.now());

	            String formattedPastMedicalCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	            String formattedDayPastMedicalCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	            model.addAttribute("lengthOfPatientConditionYears", currentMedicationAccountLengthYears);
		        model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
		        model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
		        model.addAttribute("searchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
		        model.addAttribute("searchedPatientMostRecentLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
		        model.addAttribute("searchedMostRecentPastMedicalRecordDayCreatedAt", formattedPastMedicalCreatedAtDate);
		        model.addAttribute("oneSearchedMostRecentPastMedicalRecordCreatedAt", formattedDayPastMedicalCreatedAtDate);
		 }
		        // Date Formatting
		        model.addAttribute("searchedPatientAge", searchedPatientAge);
		        model.addAttribute("mostRecentCurrentMedication", mostRecentCurrentMedication);
		        model.addAttribute("searchedCurrentMedicationDaysHistory", currentMedicationAccountLengthDays);
		        model.addAttribute("searchedCurrentMedicationMonthsHistory", currentMedicationAccountLengthMonths);
		        model.addAttribute("searchedCurrentMedicationYearsHistory", currentMedicationAccountLengthYears);
		        model.addAttribute("searchedCurrentMedicationHistoryList", currentMedicationHistory);
		        model.addAttribute("oneSearchedCurrentMedicationDayCreatedAt",formattedCreatedAtDate);
		        model.addAttribute("oneSearchedCurrentMedicationCreatedAt", formattedDayCreatedAtDate);
		        model.addAttribute("mostRecentCurrentMedicationDayCreatedAt", mostRecentCurrentMedication.getCreatedAt());
		        model.addAttribute("mostRecentCurrentMedicationCreatedAt", mostRecentCurrentMedication.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		   
    	}
    
    // Helper method to sort patient cases by start date
    public void sortMostRecentPhysicalAssessmentByStartDate(Model model, Long patientId) {
        Patient loggedInPatient = patientServ.getOne(patientId);
        
        if (loggedInPatient == null) {
            // Handle the case where loggedInPatient is null
            return;
        }

        List<PhysicalAssessment> physicalAssessmentRecords = loggedInPatient.getPhysicalAssessments();

        if (physicalAssessmentRecords == null || physicalAssessmentRecords.isEmpty()) {
            // Handle the case where physicalAssessmentRecords is null or empty
            return;
        }

        physicalAssessmentRecords.sort(Comparator.comparing(PhysicalAssessment::getCreatedAt).reversed());
        PhysicalAssessment mostRecentPhysicalAssessment = physicalAssessmentRecords.get(0);

        // Calculate date differences
        LocalDate createdAt = mostRecentPhysicalAssessment.getCreatedAt();
        LocalDate searchedPatientBirthDay = mostRecentPhysicalAssessment.getPatient().getDateOfBirth();
 
        // Date Ranges
        int physicalAssessmentHistory = calculateDaysLocalDateDifference(createdAt, LocalDate.now()); 
        long physicalAssessmentAccountLengthDays = calculateLocalDateDifference(createdAt, LocalDate.now());  
        long physicalAssessmentAccountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
        long physicalAssessmentAccountLengthMonths = calculateLocalDateDifference(createdAt, LocalDate.now());
        long searchedPatientAge = calculateLocalDateDifference(searchedPatientBirthDay, LocalDate.now());

        // Use PatientFilterUtil to get the most recent PastMedicalHistory
        PastMedicalHistory mostRecentPastMedicalRecord = sortMostRecentPastMedicalRecord(model, patientId);

        // Calculate length of medical condition
        if (mostRecentPastMedicalRecord != null) {
	            LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	            long searchedPatientLengthOfMedicalCondition = calculateLocalDateDifference(medicalConditionStartDate, LocalDate.now());
	            String formattedPastMedicalCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE,  MMM dd, yyyy"));
	            String formattedDayPastMedicalCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy"));

	            model.addAttribute("lengthOfPatientConditionYears", physicalAssessmentAccountLengthYears);
		        model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
		        model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
		        model.addAttribute("searchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
		        model.addAttribute("searchedPatientMostRecentLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
		        model.addAttribute("searchedMostRecentPastMedicalRecordDayCreatedAt", formattedPastMedicalCreatedAtDate);
		        model.addAttribute("oneSearchedMostRecentPastMedicalRecordCreatedAt", formattedDayPastMedicalCreatedAtDate);
		 }
		        // Date Formatting
		        model.addAttribute("searchedPatientAge", searchedPatientAge);
		        model.addAttribute("mostRecentPhysicalAssessment", mostRecentPhysicalAssessment);
		        model.addAttribute("searchedPhysicalAssessmentDaysHistory", physicalAssessmentAccountLengthDays);
		        model.addAttribute("searchedPhysicalAssessmentMonthsHistory", physicalAssessmentAccountLengthMonths);
		        model.addAttribute("searchedPhysicalAssessmentYearsHistory", physicalAssessmentAccountLengthYears);
		        model.addAttribute("searchedPhysicalAssessmentHistoryList", physicalAssessmentHistory);
		        model.addAttribute("oneSearchedPhysicalAssessmentDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		        model.addAttribute("oneSearchedPhysicalAssessmentCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		        model.addAttribute("mostRecentPhysicalAssessmentDayCreatedAt", mostRecentPhysicalAssessment.getCreatedAt());
		        model.addAttribute("mostRecentPhysicalAssessmentCreatedAt", mostRecentPhysicalAssessment.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		   
    	}

    // Helper method to sort patient cases by start date
    public void sortMostRecentPainAssessmentByStartDate(Model model, Long patientId) {
        Patient loggedInPatient = patientServ.getOne(patientId);
        
        if (loggedInPatient == null) {
            // Handle the case where loggedInPatient is null
            return;
        }

        List<PainAssessment> painAssessmentRecords = loggedInPatient.getPainAssessments();

        if (painAssessmentRecords == null || painAssessmentRecords.isEmpty()) {
            // Handle the case where painAssessmentRecords is null or empty
            return;
        }

        painAssessmentRecords.sort(Comparator.comparing(PainAssessment::getCreatedAt).reversed());
        PainAssessment mostRecentPainAssessment = painAssessmentRecords.get(0);

        // Calculate date differences
        LocalDate createdAt = mostRecentPainAssessment.getCreatedAt().toLocalDate();
        LocalDate searchedPatientBirthDay = mostRecentPainAssessment.getPatient().getDateOfBirth();
 
        // Date Ranges
        int painAssessmentHistory = calculateDaysLocalDateDifference(createdAt, LocalDate.now()); 
        long painAssessmentAccountLengthDays = calculateLocalDateDifference(createdAt, LocalDate.now());  
        long painAssessmentAccountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
        long painAssessmentAccountLengthMonths = calculateLocalDateDifference(createdAt, LocalDate.now());
        long searchedPatientAge = calculateLocalDateDifference(searchedPatientBirthDay, LocalDate.now());

        // Use PatientFilterUtil to get the most recent PastMedicalHistory
        PastMedicalHistory mostRecentPastMedicalRecord = sortMostRecentPastMedicalRecord(model, patientId);

        // Calculate length of medical condition
        if (mostRecentPastMedicalRecord != null) {
	            LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	            long searchedPatientLengthOfMedicalCondition = calculateLocalDateDifference(medicalConditionStartDate, LocalDate.now());
	            String formattedPastMedicalCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	            String formattedDayPastMedicalCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	            model.addAttribute("lengthOfPatientConditionYears", painAssessmentAccountLengthYears);
		        model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
		        model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
		        model.addAttribute("searchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
		        model.addAttribute("searchedPatientMostRecentLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
		        model.addAttribute("searchedMostRecentPastMedicalRecordDayCreatedAt", formattedPastMedicalCreatedAtDate);
		        model.addAttribute("oneSearchedMostRecentPastMedicalRecordCreatedAt", formattedDayPastMedicalCreatedAtDate);
		 }
		        // Date Formatting
		        model.addAttribute("searchedPatientAge", searchedPatientAge);
		        model.addAttribute("mostRecentPainAssessment", mostRecentPainAssessment);
		        model.addAttribute("searchedPainAssessmentDaysHistory", painAssessmentAccountLengthDays);
		        model.addAttribute("searchedPainAssessmentMonthsHistory", painAssessmentAccountLengthMonths);
		        model.addAttribute("searchedPainAssessmentYearsHistory", painAssessmentAccountLengthYears);
		        model.addAttribute("searchedPainAssessmentHistoryList", painAssessmentHistory);
		        model.addAttribute("oneSearchedPainAssessmentDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		        model.addAttribute("oneSearchedPainAssessmentCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		        model.addAttribute("mostRecentPainAssessmentDayCreatedAt", mostRecentPainAssessment.getCreatedAt());
		        model.addAttribute("mostRecentPainAssessmentCreatedAt", mostRecentPainAssessment.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		   
    	}
    
    // Helper method to sort patient cases by start date
    public void sortMostRecentIncidentReportByCreatedAtDate(Model model, Long patientId) {
        Patient loggedInPatient = patientServ.getOne(patientId);
        
        if (loggedInPatient == null) {
            // Handle the case where loggedInPatient is null
            return;
        }

        List<IncidentReport> incidentReportRecords = loggedInPatient.getIncidentReports();

        if (incidentReportRecords == null || incidentReportRecords.isEmpty()) {
            // Handle the case where incidentReportRecords is null or empty
            return;
        }

        incidentReportRecords.sort(Comparator.comparing(IncidentReport::getCreatedAt).reversed());
        IncidentReport mostRecentIncidentReport = incidentReportRecords.get(0);

        // Calculate date differences
        LocalDate createdAt = mostRecentIncidentReport.getCreatedAt().toLocalDate();
        LocalDate searchedPatientBirthDay = mostRecentIncidentReport.getPatient().getDateOfBirth();
 
        // Date Ranges
        int incidentReportHistory = calculateDaysLocalDateDifference(createdAt, LocalDate.now()); 
        long incidentReportAccountLengthDays = calculateLocalDateDifference(createdAt, LocalDate.now());  
        long incidentReportAccountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
        long incidentReportAccountLengthMonths = calculateLocalDateDifference(createdAt, LocalDate.now());
        long searchedPatientAge = calculateLocalDateDifference(searchedPatientBirthDay, LocalDate.now());

        // Use PatientFilterUtil to get the most recent PastMedicalHistory
        PastMedicalHistory mostRecentPastMedicalRecord = sortMostRecentPastMedicalRecord(model, patientId);

        // Calculate length of medical condition
        if (mostRecentPastMedicalRecord != null) {
	            LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	            long searchedPatientLengthOfMedicalCondition = calculateLocalDateDifference(medicalConditionStartDate, LocalDate.now());
	            String formattedPastMedicalCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	            String formattedDayPastMedicalCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	            model.addAttribute("lengthOfPatientConditionYears", incidentReportAccountLengthYears);
		        model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
		        model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
		        model.addAttribute("searchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
		        model.addAttribute("searchedPatientMostRecentLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
		        model.addAttribute("searchedMostRecentPastMedicalRecordDayCreatedAt", formattedPastMedicalCreatedAtDate);
		        model.addAttribute("oneSearchedMostRecentPastMedicalRecordCreatedAt", formattedDayPastMedicalCreatedAtDate);
		 }
		        // Date Formatting
		        model.addAttribute("searchedPatientAge", searchedPatientAge);
		        model.addAttribute("mostRecentIncidentReport", mostRecentIncidentReport);
		        model.addAttribute("searchedIncidentReportDaysHistory", incidentReportAccountLengthDays);
		        model.addAttribute("searchedIncidentReportMonthsHistory", incidentReportAccountLengthMonths);
		        model.addAttribute("searchedIncidentReportYearsHistory", incidentReportAccountLengthYears);
		        model.addAttribute("searchedIncidentReportHistoryList", incidentReportHistory);
		        model.addAttribute("oneSearchedIncidentReportDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		        model.addAttribute("oneSearchedIncidentReportCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		        model.addAttribute("mostRecentIncidentReportDayCreatedAt", mostRecentIncidentReport.getCreatedAt());
		        model.addAttribute("mostRecentIncidentReportCreatedAt", mostRecentIncidentReport.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		   
    	}
}

