package com.turuchie.mellowhealthportal.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.FollowUpRecord;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PainAssessment;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PhysicalAssessment;
import com.turuchie.mellowhealthportal.models.PatientOperations.AdverseEffect;
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
        sortMostRecentAdverseEffect(model, patientId);
        sortMostRecentFollowUpRecord(model, patientId);
        sortMostRecentPainAssessment(model, patientId);
        sortMostRecentCurrentMedication(model, patientId);
    	sortMostRecentPastMedicalRecord(model, patientId);
        sortMostRecentPhysicalAssessment(model, patientId);
        addPhysicalAssessmentInfoToModel(model, patientId);
        setMostRecentInsuranceReportProperty(model, patientId);
        sortMostRecentDoseRegimenByStartDate(model, patientId);
        sortMostRecentPatientCaseByStartDate(model, patientId);
        setMostRecentDiagnosticReportProperty(model, patientId);
        sortMostRecentPainAssessmentByStartDate(model, patientId);
        sortMostRecentIncidentReportByStartDate(model, patientId);
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
        // Add model attributes related to the most recent PastMedicalRecord Record
        if (mostRecentPastMedicalRecord != null) {
            LocalDate createdAt = mostRecentPastMedicalRecord.getCreatedAt();
            LocalDate onsetOfPastMedicalRecord = mostRecentPastMedicalRecord.getStartDate();

            long accountLengthDays = calculateDaysLocalDateDifference(createdAt, LocalDate.now());
            long accountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
            long accountLengthMonths = calculateMonthsLocalDateDifference(createdAt, LocalDate.now());

            long accountYearsHistory = calculateLocalDateDifference(onsetOfPastMedicalRecord, LocalDate.now());
            long accountDaysHistory = calculateDaysLocalDateDifference(onsetOfPastMedicalRecord, LocalDate.now());
            long accountMonthsHistory = calculateMonthsLocalDateDifference(onsetOfPastMedicalRecord, LocalDate.now());

            String dayFormattedCreatedAt = createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
            String formattedCreatedAt = createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

            model.addAttribute("mostRecentPastMedicalRecordDaysHistory", accountDaysHistory);
            model.addAttribute("mostRecentPastMedicalRecordYearsHistory", accountYearsHistory);
            model.addAttribute("mostRecentPastMedicalRecordMonthsHistory", accountMonthsHistory);

            model.addAttribute("mostRecentPastMedicalRecordAccountDaysHistory", accountLengthDays);
            model.addAttribute("mostRecentPastMedicalRecordAccountMonthsHistory", accountLengthMonths);
            model.addAttribute("mostRecentPastMedicalRecordAccountYearsHistory", accountLengthYears);

            model.addAttribute("mostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
            model.addAttribute("mostRecentPastMedicalRecordCreatedAt", formattedCreatedAt);
            model.addAttribute("mostRecentPastMedicalRecordDaysCreatedAt", dayFormattedCreatedAt);
        }

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

    // Helper method to sort past medical history by start date and populate model attributes
    public FollowUpRecord sortMostRecentFollowUpRecord(Model model, Long patientId) {
        // Fetch the logged-in patient
        Patient loggedInPatient = patientServ.getOne(patientId);

        // Get the follow-up records for the logged-in patient
        List<FollowUpRecord> followUpRecords = loggedInPatient.getFollowUpRecords();

        // Sort the follow-up records by start date in descending order
        followUpRecords.sort(Comparator.comparing(FollowUpRecord::getCreatedAt).reversed());

        // Get the most recent FollowUp Record (if any)
        FollowUpRecord mostRecentFollowUpRecord = followUpRecords.isEmpty() ? null : followUpRecords.get(0);

        // Add model attributes related to the most recent FollowUp Record
        if (mostRecentFollowUpRecord != null) {
            LocalDate createdAt = mostRecentFollowUpRecord.getCreatedAt();
            long accountLengthDays = calculateDaysLocalDateDifference(createdAt, LocalDate.now());
            long accountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
            long accountLengthMonths = calculateMonthsLocalDateDifference(createdAt, LocalDate.now());

            String dayFormattedCreatedAt = createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
            String formattedCreatedAt = createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

            model.addAttribute("mostRecentFollowUpCreatedAt", formattedCreatedAt);
            model.addAttribute("mostRecentFollowUpDaysCreatedAt", dayFormattedCreatedAt);
            model.addAttribute("mostRecentFollowUpAccountDaysHistory", accountLengthDays);
            model.addAttribute("mostRecentFollowUpAccountMonthsHistory", accountLengthMonths);
            model.addAttribute("mostRecentFollowUpAccountYearsHistory", accountLengthYears);
            model.addAttribute("mostRecentFollowUpRecord", mostRecentFollowUpRecord);
        }

        return mostRecentFollowUpRecord;
    }
    

 // Helper method to sort adverse effects by start date
    public AdverseEffect sortMostRecentAdverseEffect(Model model, Long patientId) {
        // Fetch the logged-in patient
        Patient loggedInPatient = patientServ.getOne(patientId);

        // Get the patient cases for the logged-in patient
        List<PatientCase> patientCases = loggedInPatient.getPatientCases();
        
        // Retrieve the most recent patient case
        PatientCase mostRecentPatientCase = getMostRecentPatientCase(patientCases);

        // Retrieve the most recent adverse effect for the most recent patient case
        AdverseEffect mostRecentAdverseEffect = getMostRecentAdverseEffect(mostRecentPatientCase);

        // Populate model attributes related to the most recent adverse effect
        populateAdverseEffectModelAttributes(model, mostRecentAdverseEffect);

        return mostRecentAdverseEffect;
    }

    // Helper method to retrieve the most recent patient case
    PatientCase getMostRecentPatientCase(List<PatientCase> patientCases) {
        if (patientCases == null || patientCases.isEmpty()) {
            return null;
        }

        // Sort patient cases by creation date in descending order to get the most recent one
        patientCases.sort(Comparator.comparing(PatientCase::getCreatedAt).reversed());
        return patientCases.get(0);
    }

    // Helper method to retrieve the most recent adverse effect for a given patient case
    private AdverseEffect getMostRecentAdverseEffect(PatientCase mostRecentPatientCase) {
        if (mostRecentPatientCase == null) {
            return null;
        }

        // Retrieve adverse effects associated with the most recent patient case
        List<AdverseEffect> adverseEffects = mostRecentPatientCase.getAdverseEffects();
        if (adverseEffects == null || adverseEffects.isEmpty()) {
            return null;
        }

        // Sort adverse effects by creation date in descending order to get the most recent one
        adverseEffects.sort(Comparator.comparing(AdverseEffect::getCreatedAt).reversed());
        return adverseEffects.get(0);
    }

    // Helper method to populate model attributes related to the most recent adverse effect
    private void populateAdverseEffectModelAttributes(Model model, AdverseEffect mostRecentAdverseEffect) {
        if (mostRecentAdverseEffect == null) {
            return;
        }

        LocalDate createdAt = mostRecentAdverseEffect.getCreatedAt();
        long accountLengthDays = calculateDaysLocalDateDifference(createdAt, LocalDate.now());
        long accountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
        long accountLengthMonths = calculateMonthsLocalDateDifference(createdAt, LocalDate.now());

        String dayFormattedCreatedAt = createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
        String formattedCreatedAt = createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

        // Add attributes related to the most recent adverse effect to the model
        model.addAttribute("mostRecentAdverseEffectCreatedAt", formattedCreatedAt);
        model.addAttribute("mostRecentAdverseEffectDaysCreatedAt", dayFormattedCreatedAt);
        model.addAttribute("mostRecentAdverseEffectAccountDaysHistory", accountLengthDays);
        model.addAttribute("mostRecentAdverseEffectAccountMonthsHistory", accountLengthMonths);
        model.addAttribute("mostRecentAdverseEffectAccountYearsHistory", accountLengthYears);
        model.addAttribute("mostRecentAdverseEffect", mostRecentAdverseEffect);
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
    
    // Calculate Assessment History and create a list for patients' physical assessments
    public long calculateTotalLengthOfPhysicalAssessments(List<PhysicalAssessment> physicalAssessmentList) {
        long totalLength = 0;

        for (PhysicalAssessment assessmentHistory : physicalAssessmentList) {
           int lengthOfAssessmentRecord = calculateDaysLocalDateDifference(assessmentHistory.getCreatedAt(), LocalDate.now());
           totalLength += lengthOfAssessmentRecord;
        }

        return totalLength;
    }

    // In your service or controller class
       public void addPhysicalAssessmentInfoToModel(Model model, Long patientId) {
           // Fetch the logged-in patient
           Patient loggedInPatient = patientServ.getOne(patientId);

	   	    // Get all patient cases for the logged-in patient
	   	    List<PatientCase> patientCases = loggedInPatient.getPatientCases();
	   	    
	   	    // Retrieve the most recent patient case
	   	    PatientCase mostRecentPatientCase = getMostRecentPatientCase(patientCases);
	   	    
	   	    // If there are no patient cases, return null
	   	    if (mostRecentPatientCase == null) {
	   	        return;
	   	    }

           // Get the physical assessments for the logged-in patient
           List<PhysicalAssessment> allAssessmentRecords = mostRecentPatientCase.getPhysicalAssessments();

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
    	    if (loggedInPatient == null) {
    	        return;
    	    }

    	    List<PatientCase> patientCases = loggedInPatient.getPatientCases();
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
    	    int onsetHistory = calculateLocalDateDifference(mostRecentPatientCase.getOnset(), LocalDate.now());
    	    int visitHistory = calculateDaysLocalDateDifference(createdAt, LocalDate.now());

    	    long accountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
    	    long accountLengthDays = calculateDaysLocalDateDifference(createdAt, LocalDate.now());
    	    long accountLengthMonths = calculateMonthsLocalDateDifference(createdAt, LocalDate.now());

    	    long conditionLengthYears = calculateLocalDateDifference(onset, LocalDate.now());
    	    long conditionLengthDays = calculateDaysLocalDateDifference(onset, LocalDate.now());
    	    long conditionLengthMonths = calculateMonthsLocalDateDifference(onset, LocalDate.now());
    	    long searchedPatientAge = calculateLocalDateDifference(searchedPatientBirthDay, LocalDate.now());

    	    // Use PatientFilterUtil to get the most recent PastMedicalHistory
    	    PastMedicalHistory mostRecentPastMedicalRecord = sortMostRecentPastMedicalRecord(model, patientId);

    	    populatePatientCaseModelAttributes(model, mostRecentPatientCase, onsetHistory, visitHistory, searchedPatientAge,
   	            accountLengthYears, accountLengthDays, accountLengthMonths, conditionLengthYears, conditionLengthDays,
   	            conditionLengthMonths, mostRecentPastMedicalRecord, createdAt);
    	}

    	private void populatePatientCaseModelAttributes(Model model, PatientCase mostRecentPatientCase, int onsetHistory,
           int visitHistory, long searchedPatientAge, long accountLengthYears,
    	   long accountLengthDays, long accountLengthMonths, long conditionLengthYears,
    	   long conditionLengthDays, long conditionLengthMonths,
    	   PastMedicalHistory mostRecentPastMedicalRecord, LocalDate createdAt) {
    	    // Date Formatting
    	    model.addAttribute("mostRecentOnsetHistory", onsetHistory);
    	    model.addAttribute("mostRecentVisitHistory", visitHistory);
    	    model.addAttribute("searchedPatientAge", searchedPatientAge);
    	    model.addAttribute("mostRecentPatientCase", mostRecentPatientCase);
    	    model.addAttribute("searchedPatientCaseAccountLength", accountLengthYears);
    	    model.addAttribute("patientCaseAccountLengthDays", accountLengthDays);
    	    model.addAttribute("patientCaseAccountLengthMonths", accountLengthMonths);
    	    model.addAttribute("lengthOfPatientConditionYears", conditionLengthYears);
    	    model.addAttribute("lengthOfPatientConditionDays", conditionLengthDays);
    	    model.addAttribute("lengthOfPatientConditionMonths", conditionLengthMonths);
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
    	    
    	    // Get all patient cases for the logged-in patient
    	    List<PatientCase> patientCases = loggedInPatient.getPatientCases();
    	    
    	    // Retrieve the most recent patient case
    	    PatientCase mostRecentPatientCase = getMostRecentPatientCase(patientCases);
    	    
    	    // If there are no patient cases, return null
    	    if (mostRecentPatientCase == null) {
    	        return null;
    	    }

    	    // Get the current medications for the most recent patient case
    	    List<CurrentMedication> currentMedications = mostRecentPatientCase.getCurrentMedications();
    	    
    	    // Sort current medications by start date in descending order to get the most recent one
    	    currentMedications.sort(Comparator.comparing(CurrentMedication::getStartDate).reversed());
    	    
    	    // Retrieve the most recent current medication
    	    CurrentMedication mostRecentCurrentMedication = currentMedications.isEmpty() ? null : currentMedications.get(0);

    	    // Add the most recent current medication to the model
    	    model.addAttribute("mostRecentCurrentMedication", mostRecentCurrentMedication);
    	    
    	    return mostRecentCurrentMedication;
    	}

    	// Sort Pain Assessment By Created At
    	public PainAssessment sortMostRecentPainAssessment(Model model, Long patientId) {
    	    // Fetch the logged-in patient
    	    Patient loggedInPatient = patientServ.getOne(patientId);
    	    
    	    // Get all patient cases for the logged-in patient
    	    List<PatientCase> patientCases = loggedInPatient.getPatientCases();
    	    
    	    // Retrieve the most recent patient case
    	    PatientCase mostRecentPatientCase = getMostRecentPatientCase(patientCases);
    	    
    	    // If there are no patient cases, return null
    	    if (mostRecentPatientCase == null) {
    	        return null;
    	    }

    	    // Get the pain assessments for the most recent patient case
    	    List<PainAssessment> painAssessments = mostRecentPatientCase.getPainAssessments();
    	    
    	    // Sort pain assessments by creation date in descending order to get the most recent one
    	    painAssessments.sort(Comparator.comparing(PainAssessment::getCreatedAt).reversed());
    	    
    	    // Retrieve the most recent pain assessment
    	    PainAssessment mostRecentPainAssessment = painAssessments.isEmpty() ? null : painAssessments.get(0);

    	    // Add the most recent pain assessment to the model
    	    model.addAttribute("mostRecentPainAssessment", mostRecentPainAssessment);
    	    
    	    return mostRecentPainAssessment;
    	}

    // Helper method to sort patient vital records by start date
    public void sortMostRecentPatientVitalRecordByStartDate(Model model, Long patientId) {
        // Fetch the logged-in patient
        Patient loggedInPatient = patientServ.getOne(patientId);
        
        // Get all patient cases for the logged-in patient
        List<PatientCase> patientCases = loggedInPatient.getPatientCases();
        
        // Retrieve the most recent patient case
        PatientCase mostRecentPatientCase = sortMostRecentPatientCase(patientCases);
        
        // If there are no patient cases, return
        if (mostRecentPatientCase == null) {
            return;
        }

        // Get all patient vital records for the most recent patient case
        List<PatientVitalRecord> patientVitalRecords = mostRecentPatientCase.getPatientVitalRecords();
        
        // Sort patient vital records by creation date in descending order to get the most recent one
        patientVitalRecords.sort(Comparator.comparing(PatientVitalRecord::getCreatedAt).reversed());
        
        // Retrieve the most recent patient vital record
        PatientVitalRecord mostRecentPatientVitalRecord = patientVitalRecords.isEmpty() ? null : patientVitalRecords.get(0);

        // Add the most recent patient vital record to the model
        populatePatientVitalRecordAccountHistory(model, mostRecentPatientVitalRecord);
        model.addAttribute("mostRecentPatientVitalRecord", mostRecentPatientVitalRecord);
    }

 // Helper method to calculate account history for the most recent patient vital record
    private void populatePatientVitalRecordAccountHistory(Model model, PatientVitalRecord mostRecentPatientVitalRecord) {
        if (mostRecentPatientVitalRecord == null) {
            return;
        }
        
        // Retrieve weight (in pounds) and height (in centimeters) from the most recent patient vital record
        BigDecimal weightInPounds = mostRecentPatientVitalRecord.getWeight();
        BigDecimal heightInCentimeters = mostRecentPatientVitalRecord.getHeight();
        
        // Convert weight from pounds to kilograms
        BigDecimal weightInKilograms = poundsToKilograms(weightInPounds);
        
        // Calculate BMI
        BigDecimal bmi = calculateBMI(weightInKilograms, heightInCentimeters);
        
        // Calculate account history for the most recent patient vital record (similar to adverse effects and physical assessments)
        LocalDate createdAt = mostRecentPatientVitalRecord.getCreatedAt().toLocalDate();
        long accountLengthDays = calculateDaysLocalDateDifference(createdAt, LocalDate.now());
        long accountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
        long accountLengthMonths = calculateMonthsLocalDateDifference(createdAt, LocalDate.now());

        String dayFormattedCreatedAt = createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
        String formattedCreatedAt = createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

        // Convert BMI from BigDecimal to double
        double bmiDouble = bmi.doubleValue();

        // Determine BMI status with Bootstrap styling
        String bmiStatusWithBootstrap = determineBMIStatusWithBootstrap(bmiDouble);

        // Add BMI status with Bootstrap styling to the model
        model.addAttribute("bmiStatusWithBootstrap", bmiStatusWithBootstrap);


        // Add account history attributes for the most recent patient vital record to the model
        model.addAttribute("mostRecentPatientVitalRecord", mostRecentPatientVitalRecord);
        model.addAttribute("mostRecentPatientVitalRecordCreatedAt", formattedCreatedAt);
        model.addAttribute("mostRecentPatientVitalRecordDaysCreatedAt", dayFormattedCreatedAt);
        model.addAttribute("mostRecentPatientVitalRecordAccountDaysHistory", accountLengthDays);
        model.addAttribute("mostRecentPatientVitalRecordAccountMonthsHistory", accountLengthMonths);
        model.addAttribute("mostRecentPatientVitalRecordAccountYearsHistory", accountLengthYears);
        
        // Add BMI to the model
        model.addAttribute("bmi", bmi);
    }


	// Helper method to convert pounds to kilograms
	private BigDecimal poundsToKilograms(BigDecimal weightInPounds) {
	    // 1 pound is approximately 0.453592 kilograms
	    BigDecimal conversionFactor = new BigDecimal("0.453592");
	    return weightInPounds.multiply(conversionFactor);
	}

	// Helper method to calculate BMI
	private BigDecimal calculateBMI(BigDecimal weightInKilograms, BigDecimal heightInCentimeters) {
	    // Convert height from centimeters to meters
	    BigDecimal heightInMeters = heightInCentimeters.divide(new BigDecimal("100"));

	    // Calculate BMI
	    BigDecimal bmi = weightInKilograms.divide(heightInMeters.multiply(heightInMeters), 2, RoundingMode.HALF_UP);
	    return bmi;
	}
	// Helper method to determine BMI status with Bootstrap styling
	private String determineBMIStatusWithBootstrap(double bmi) {
	    if (bmi <= 18.4) {
	        return "<div class=\"alert alert-outline-primary\" role=\"alert\">Underweight</div>";
	    } else if (bmi >= 18.5 && bmi <= 24.9) {
	        return "<div class=\"alert alert-outline-success\" role=\"alert\">Normal</div>";
	    } else if (bmi >= 25.0 && bmi <= 39.9) {
	        return "<div class=\"alert alert-outline-warning\" role=\"alert\">Overweight</div>";
	    } else {
	        return "<div class=\"alert alert-outline-danger\" role=\"alert\">Obese</div>";
	    }
	}

	// Helper method to sort incident reports by start date
    public void sortMostRecentIncidentReportByStartDate(Model model, Long patientId) {
        // Fetch the logged-in patient
        Patient loggedInPatient = patientServ.getOne(patientId);
        
        // Get all patient cases for the logged-in patient
        List<PatientCase> patientCases = loggedInPatient.getPatientCases();
        
        // Retrieve the most recent patient case
        PatientCase mostRecentPatientCase = sortMostRecentPatientCase(patientCases);
        
        // If there are no patient cases, return
        if (mostRecentPatientCase == null) {
            return;
        }

        // Get all incident reports for the most recent patient case
        List<IncidentReport> incidentReports = mostRecentPatientCase.getIncidentReports();
        
        // Sort incident reports by creation date in descending order to get the most recent one
        incidentReports.sort(Comparator.comparing(IncidentReport::getCreatedAt).reversed());
        
        // Retrieve the most recent incident report
        IncidentReport mostRecentIncidentReport = incidentReports.isEmpty() ? null : incidentReports.get(0);

        // Add the most recent incident report to the model
        model.addAttribute("mostRecentIncidentReport", mostRecentIncidentReport);
        
        // Calculate the account history for the most recent incident report
        populateIncidentReportAccountHistory(model, mostRecentIncidentReport);
    }

    // Helper method to retrieve the most recent patient case
    private PatientCase sortMostRecentPatientCase(List<PatientCase> patientCases) {
        if (patientCases == null || patientCases.isEmpty()) {
            return null;
        }

        // Sort patient cases by creation date in descending order to get the most recent one
        patientCases.sort(Comparator.comparing(PatientCase::getCreatedAt).reversed());
        return patientCases.get(0);
    }

    // Helper method to calculate account history for the most recent incident report
    private void populateIncidentReportAccountHistory(Model model, IncidentReport mostRecentIncidentReport) {
        if (mostRecentIncidentReport == null) {
            return;
        }
        
        // Calculate account history for the most recent incident report (similar to adverse effects and physical assessments)
        LocalDate createdAt = mostRecentIncidentReport.getCreatedAt().toLocalDate();
        long accountLengthDays = calculateDaysLocalDateDifference(createdAt, LocalDate.now());
        long accountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
        long accountLengthMonths = calculateMonthsLocalDateDifference(createdAt, LocalDate.now());

        String dayFormattedCreatedAt = createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
        String formattedCreatedAt = createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

        // Add account history attributes for the most recent incident report to the model
        model.addAttribute("mostRecentIncidentReport", mostRecentIncidentReport);
        model.addAttribute("mostRecentIncidentReportCreatedAt", formattedCreatedAt);
        model.addAttribute("mostRecentIncidentReportDaysCreatedAt", dayFormattedCreatedAt);
        model.addAttribute("mostRecentIncidentReportAccountDaysHistory", accountLengthDays);
        model.addAttribute("mostRecentIncidentReportAccountMonthsHistory", accountLengthMonths);
        model.addAttribute("mostRecentIncidentReportAccountYearsHistory", accountLengthYears);
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
        
        // Get all patient cases for the logged-in patient
        List<PatientCase> patientCases = loggedInPatient.getPatientCases();
        
        // Retrieve the most recent patient case
        PatientCase mostRecentPatientCase = getMostRecentPatientCase(patientCases);
        
        // If there are no patient cases, return null
        if (mostRecentPatientCase == null) {
            return null;
        }

        // Get the diagnostic records for the most recent patient case
        List<DiagnosticRecord> diagnosticRecords = mostRecentPatientCase.getDiagnosticRecords();
        
        // Sort diagnostic records by creation date in descending order to get the most recent one
        diagnosticRecords.sort(Comparator.comparing(DiagnosticRecord::getCreatedAt).reversed());
        
        // Retrieve the most recent diagnostic report
        DiagnosticRecord mostRecentDiagnosticRecord = diagnosticRecords.isEmpty() ? null : diagnosticRecords.get(0);

        if (mostRecentDiagnosticRecord != null) { // Add null check
            // Calculate the diagnostic history
            LocalDate diagnosticCreatedAt = mostRecentDiagnosticRecord.getCreatedAt().toLocalDate();
            int mostRecentDiagnosticHistory = calculateDaysLocalDateDifference(LocalDate.now(), diagnosticCreatedAt);
            String formattedDiagnosticDayCreatedAtDate = diagnosticCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
            String formattedDiagnosticCreatedAtDate = diagnosticCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

            // Add diagnostic report attributes to the model
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
    public int calculateMonthsLocalDateDifference(LocalDate startDate, LocalDate endDate) {  
        int dateMonthDifference = (int) ChronoUnit.MONTHS.between(startDate, endDate);
        return dateMonthDifference;
    }

    // Method To Calculate Length Of Local Date Time
    public int calculateMonthsLocaleDateTimeDiffference(LocalDateTime startDate, LocalDateTime endDate) {
        int dateDifference = (int) ChronoUnit.MONTHS.between(startDate, endDate);
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
        //Get all patient cases for the logged-in patient
   	    List<PatientCase> patientCases = loggedInPatient.getPatientCases();
   	    
   	    // Retrieve the most recent patient case
   	    PatientCase mostRecentPatientCase = getMostRecentPatientCase(patientCases);
   	    
   	    // If there are no patient cases, return null
   	    if (mostRecentPatientCase == null) {
   	        return;
   	    }
        List<DoseRegimen> doseRegimenRecords = mostRecentPatientCase.getDoseRegimenRecords();

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
        long regimenAccountLengthDays = calculateDaysLocalDateDifference(createdAt, LocalDate.now());  
        long regimenAccountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
        long regimenAccountLengthMonths = calculateMonthsLocalDateDifference(createdAt, LocalDate.now());
        long searchedPatientAge = calculateLocalDateDifference(searchedPatientBirthDay, LocalDate.now());

        // Use PatientFilterUtil to get the most recent PastMedicalHistory
        PastMedicalHistory mostRecentPastMedicalRecord = sortMostRecentPastMedicalRecord(model, patientId);

        // Calculate length of medical condition
        if (mostRecentPastMedicalRecord != null) {
	            LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	            long searchedPatientLengthOfMedicalCondition = calculateDaysLocalDateDifference(medicalConditionStartDate, LocalDate.now());
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

        // Get all patient cases for the logged-in patient
        List<PatientCase> patientCases = loggedInPatient.getPatientCases();
        
        // Retrieve the most recent patient case
        PatientCase mostRecentPatientCase = getMostRecentPatientCase(patientCases);
        
        // If there are no patient cases, return null
        if (mostRecentPatientCase == null) {
            return;
        }

        List<CurrentMedication> currentMedicationRecords = mostRecentPatientCase.getCurrentMedications();

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
        long currentMedicationAccountLengthDays = calculateDaysLocalDateDifference(createdAt, LocalDate.now());  
        long currentMedicationAccountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
        long currentMedicationAccountLengthMonths = calculateMonthsLocalDateDifference(createdAt, LocalDate.now());
        long searchedPatientAge = calculateLocalDateDifference(searchedPatientBirthDay, LocalDate.now());

        // Use PatientFilterUtil to get the most recent PastMedicalHistory
        PastMedicalHistory mostRecentPastMedicalRecord = sortMostRecentPastMedicalRecord(model, patientId);

        // Calculate length of medical condition
        if (mostRecentPastMedicalRecord != null) {
	            LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	            long searchedPatientLengthOfMedicalCondition = calculateDaysLocalDateDifference(medicalConditionStartDate, LocalDate.now());

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

        // Get all patient cases for the logged-in patient
        List<PatientCase> patientCases = loggedInPatient.getPatientCases();
        
        // Retrieve the most recent patient case
        PatientCase mostRecentPatientCase = getMostRecentPatientCase(patientCases);
        
        // If there are no patient cases, return null
        if (mostRecentPatientCase == null) {
            return;
        }
        
        List<PhysicalAssessment> physicalAssessmentRecords = mostRecentPatientCase.getPhysicalAssessments();

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
        long physicalAssessmentAccountLengthDays = calculateDaysLocalDateDifference(createdAt, LocalDate.now());  
        long physicalAssessmentAccountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
        long physicalAssessmentAccountLengthMonths = calculateMonthsLocalDateDifference(createdAt, LocalDate.now());
        long searchedPatientAge = calculateLocalDateDifference(searchedPatientBirthDay, LocalDate.now());

        // Use PatientFilterUtil to get the most recent PastMedicalHistory
        PastMedicalHistory mostRecentPastMedicalRecord = sortMostRecentPastMedicalRecord(model, patientId);

        // Calculate length of medical condition
        if (mostRecentPastMedicalRecord != null) {
	            LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	            long searchedPatientLengthOfMedicalCondition = calculateDaysLocalDateDifference(medicalConditionStartDate, LocalDate.now());
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

        // Get all patient cases for the logged-in patient
        List<PatientCase> patientCases = loggedInPatient.getPatientCases();
        
        // Retrieve the most recent patient case
        PatientCase mostRecentPatientCase = getMostRecentPatientCase(patientCases);
        
        // If there are no patient cases, return null
        if (mostRecentPatientCase == null) {
            return;
        }
 
        List<PainAssessment> painAssessmentRecords = mostRecentPatientCase.getPainAssessments();

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
        long painAssessmentAccountLengthDays = calculateDaysLocalDateDifference(createdAt, LocalDate.now());  
        long painAssessmentAccountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
        long painAssessmentAccountLengthMonths = calculateMonthsLocalDateDifference(createdAt, LocalDate.now());
        long searchedPatientAge = calculateLocalDateDifference(searchedPatientBirthDay, LocalDate.now());

        // Use PatientFilterUtil to get the most recent PastMedicalHistory
        PastMedicalHistory mostRecentPastMedicalRecord = sortMostRecentPastMedicalRecord(model, patientId);

        // Calculate length of medical condition
        if (mostRecentPastMedicalRecord != null) {
	            LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	            long searchedPatientLengthOfMedicalCondition = calculateDaysLocalDateDifference(medicalConditionStartDate, LocalDate.now());
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

        // Get all patient cases for the logged-in patient
        List<PatientCase> patientCases = loggedInPatient.getPatientCases();
        
        // Retrieve the most recent patient case
        PatientCase mostRecentPatientCase = getMostRecentPatientCase(patientCases);
        
        // If there are no patient cases, return null
        if (mostRecentPatientCase == null) {
            return;
        }

        List<IncidentReport> incidentReportRecords = mostRecentPatientCase.getIncidentReports();

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
        long incidentReportAccountLengthDays = calculateDaysLocalDateDifference(createdAt, LocalDate.now());  
        long incidentReportAccountLengthYears = calculateLocalDateDifference(createdAt, LocalDate.now());
        long incidentReportAccountLengthMonths = calculateMonthsLocalDateDifference(createdAt, LocalDate.now());
        long searchedPatientAge = calculateLocalDateDifference(searchedPatientBirthDay, LocalDate.now());

        // Use PatientFilterUtil to get the most recent PastMedicalHistory
        PastMedicalHistory mostRecentPastMedicalRecord = sortMostRecentPastMedicalRecord(model, patientId);

        // Calculate length of medical condition
        if (mostRecentPastMedicalRecord != null) {
	            LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	            long searchedPatientLengthOfMedicalCondition = calculateDaysLocalDateDifference(medicalConditionStartDate, LocalDate.now());
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

