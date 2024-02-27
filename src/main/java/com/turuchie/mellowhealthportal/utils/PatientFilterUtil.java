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

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientVitalRecord;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PhysicalAssessment;
import com.turuchie.mellowhealthportal.models.PatientOperations.IncidentReport;
import com.turuchie.mellowhealthportal.models.PatientOperations.InsuranceInformation;
import com.turuchie.mellowhealthportal.models.PatientOperations.PastMedicalHistory;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PatientService;

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
        sortMostRecentPatientVitalRecordByStartDate(model, patientId);
        sortMostRecentIncidentReportByStartDate(model, patientId);
        setMostRecentInsuranceReportProperty(model, patientId);
        addPhysicalAssessmentInfoToModel(model, patientId);
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
        model.addAttribute("physicalAssessments", allAssessmentRecords);
    }

    // Helper method to sort patient cases by start date
    public void sortMostRecentPatientCaseByStartDate(Model model, Long patientId) {
        Patient loggedInPatient = patientServ.getOne(patientId);
        
        if (loggedInPatient == null) {
            // Handle the case where loggedInPatient is null
            return;
        }

        List<PatientCase> patientCases = loggedInPatient.getPatientCases();

        if (patientCases == null || patientCases.isEmpty()) {
            // Handle the case where patientCases is null or empty
            return;
        }

        patientCases.sort(Comparator.comparing(PatientCase::getCreatedAt).reversed());
        PatientCase mostRecentPatientCase = patientCases.get(0);

        // Date Ranges
        int onsetHistory = calculateLocalDateDifference(mostRecentPatientCase.getOnset(), LocalDate.now() );
        int visitHistory = calculateLocaleDateTimeDiffference(mostRecentPatientCase.getCreatedAt(), LocalDateTime.now());

        // Date Formatting
        model.addAttribute("mostRecentOnsetHistory", onsetHistory);
        model.addAttribute("mostRecentVisitHistory", visitHistory);
        model.addAttribute("mostRecentPatientCase", mostRecentPatientCase);
        model.addAttribute("mostRecentPatientCaseDayCreatedAt", mostRecentPatientCase.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
        model.addAttribute("mostRecentPatientCaseCreatedAt", mostRecentPatientCase.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
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
        int dateMonthDifference = (int) ChronoUnit.MONTHS.between(startDate, endDate);
        return dateMonthDifference;
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

}
