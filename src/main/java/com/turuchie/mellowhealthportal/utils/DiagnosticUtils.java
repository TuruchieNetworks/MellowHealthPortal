package com.turuchie.mellowhealthportal.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.CoagulationRecord;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.CurrentMedication;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.DoseRegimen;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.DiagnosticRecord;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PainAssessment;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PhysicalAssessment;
import com.turuchie.mellowhealthportal.models.PatientOperations.PastMedicalHistory;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.services.PatientOperationsServices.PatientService;
@Component
public class DiagnosticUtils {
	@Autowired
	private PatientService patientServ;

	@Autowired
	private PatientFilterUtil filterUtil;

	@Autowired
	private SearchUtil searchUtil;

    public void SearchUtils(
            PatientService patientServ,
            PatientFilterUtil filterUtil,
            SearchUtil searchUtil) {
        this.patientServ = patientServ;
        this.filterUtil = filterUtil;
        this.searchUtil = searchUtil;
    }

    public void setAllSearchTrimmedMethods(Model model, String trimmedSearchTerm) {
    	searchDiagnosticRecordByCharacter(model, trimmedSearchTerm);
    	searchSingleDiagnosticRecordByCharacter(model, trimmedSearchTerm);
    	searchPhysicalAssessmentByCharacter(model, trimmedSearchTerm);
    	searchSinglePhysicalAssessmentByCharacter(model, trimmedSearchTerm);
    	searchSinglePhysicalAssessmentByCharacter(model, trimmedSearchTerm);
    	alternativeSearchDiagnosticRecordByCharacter(model, trimmedSearchTerm);
    	searchDoseRegimenRecordByCharacter(model, trimmedSearchTerm);
    	searchSingleDoseRegimenRecordByCharacter(model, trimmedSearchTerm);
    	searchCoagulationRecordByCharacter(model, trimmedSearchTerm);
    	searchSingleCoagulationRecordByCharacter(model, trimmedSearchTerm);
    	searchPatientCaseDiagnosticRecordByCharacter(model, trimmedSearchTerm);
    	searchSinglePatientCaseDiagnosticRecordByCharacter(model, trimmedSearchTerm);
    	searchPatientCaseCoagulationRecordByCharacter(model, trimmedSearchTerm);
    	searchSinglePatientCaseCoagulationRecordByCharacter(model, trimmedSearchTerm);
    }

    public void setAllPatientAttributes (Model model, Long id) {
    	findPatientPainAssessments(model, id);
    	findPatientCaseAndPainAssessments(model, id);
    	
    }
    // Method to find Pain Assessments for a specific Patient
    public void findPatientPainAssessments(Model model, Long patientId) {
        // Retrieve the patient by ID
        Patient patient = patientServ.getOne(patientId);

        if (patient != null) {
            // Add patient details to the model
            model.addAttribute("patientDetails", patient);

            // Retrieve the pain assessments for the patient
            List<PainAssessment> patientPainAssessments = patient.getPainAssessments();

            // Add pain assessments to the model
            model.addAttribute("patientPainAssessments", patientPainAssessments);
        } else {
            // Handle the case where the patient is not found
            // You can add an error message to the model or perform other actions
        }
    }

    // Method to find Pain Assessments for a specific Patient and calculate account lengths
    public void findPatientCaseAndPainAssessments(Model model, Long patientId) {
        // Retrieve the patient by ID
        Patient patient = patientServ.getOne(patientId);

        if (patient != null) {
            // Add patient details to the model
            model.addAttribute("patientDetails", patient);

            // Retrieve the patient cases for the patient
            List<PatientCase> patientCases = patient.getPatientCases();

            // List to store pain assessments for all patient cases
            List<PainAssessment> allPatientPainAssessments = new ArrayList<>();

            // List to store account lengths for each patient case
            List<Long> patientCaseAccountLengths = new ArrayList<>();
            List<Long> painAssessmentAccountLengths = new ArrayList<>();

            // Check if patientCases and allPatientPainAssessments are not null before iterating
            if (patientCases != null && allPatientPainAssessments != null) {
                // Iterate through each patient case to gather pain assessments and calculate account length
                for (PatientCase patientCase : patientCases) {
                    List<PainAssessment> patientCasePainAssessments = patientCase.getPainAssessments();
                    allPatientPainAssessments.addAll(patientCasePainAssessments);

                    // Calculate account length for each patient case
                    LocalDate createdAt = patientCase.getCreatedAt().toLocalDate();
                    LocalDate painAssessmentCreatedAt = patientCase.getCreatedAt().toLocalDate();

                    long patientCaseAccountDaysHistories = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.DAYS);
                    long painAssessmentAccountHistories = searchUtil.calculateDateDifference(painAssessmentCreatedAt, LocalDate.now(), ChronoUnit.DAYS);

                    patientCaseAccountLengths.add(patientCaseAccountDaysHistories);
                    painAssessmentAccountLengths.add(painAssessmentAccountHistories);
                }
            }

            // Add pain assessments and account lengths to the model
            model.addAttribute("allPatientPainAssessmentLists", allPatientPainAssessments);
            model.addAttribute("allPainAssessmentAccountHistories", painAssessmentAccountLengths);

            // Add patientCase pain assessments attributes and account lengths to the model
            model.addAttribute("allPatientCasesLists", patientCases);
            model.addAttribute("allPatientCaseAccountHistories", patientCaseAccountLengths);
        } else {
            // Handle the case where the patient is not found
            // You can add an error message to the model or perform other actions
        }
    }

 	//Method to search for patients and return their first pain assessment by characters
 	public List<PainAssessment> returnFirstPainAssessmentByCharacter(String trimmedSearchTerm) {
 	if (trimmedSearchTerm != null) {
 		List<Patient> matchedPatients = searchUtil.searchPatientsByCharacters(trimmedSearchTerm);
 		List<PainAssessment> firstPainAssessments = new ArrayList<>();
        //List<DiagnosticRecord> firstPatientCaseDiagnosticRecordLists = new ArrayList<>();

	      for (Patient patient : matchedPatients) {
	          if (patient.getPatientCases() != null && !patient.getPatientCases().isEmpty()) {
	              PatientCase firstPatientCase = patient.getPatientCases().get(0);
	
	              if (firstPatientCase.getPainAssessments() != null && !firstPatientCase.getPainAssessments().isEmpty()) {
	                  // Add only the first pain assessment
	                  firstPainAssessments.add(firstPatientCase.getPainAssessments().get(0));
	                  //firstPatientCaseDiagnosticRecordLists.add(firstPatientCase.getDiagnosticRecords().get(0));
	              }
	          }
	      }
	
	      return firstPainAssessments;
	  }
	  return Collections.emptyList(); // Return an empty list if the search term is null
	}

 	// Method to find Pain Assessments for a specific PatientCase
 	public void findPatientCasePainAssessments(Model model, Long patientId) {
    // Retrieve the patient case by ID
    List<PatientCase> patientCase = patientServ.getOne(patientId).getPatientCases();

	    if (patientCase != null) {
	         // Add patient case details to the model
	         model.addAttribute("patientCaseDetails", patientCase);
	
	         // Retrieve the pain assessments for the patient case
	         List<PainAssessment> patientCasePainAssessments = ((PatientCase) patientCase).getPainAssessments();
	         List<DiagnosticRecord> patientCaseDiagnosticRecordLists = ((PatientCase) patientCase).getDiagnosticRecords();
	
	         // Add pain assessments to the model
	         model.addAttribute("patientCasePainAssessments", patientCasePainAssessments);
	         model.addAttribute("allPatientCaseDiagnosticRecordLists", patientCaseDiagnosticRecordLists);
	     } else {
	         // Handle the case where the patient case is not found
	         // You can add an error message to the model or perform other actions
	     }
	 }

 	// Find Single Diagnostic Record 
 	public void searchSingleDiagnosticRecordByCharacter(Model model, String trimmedSearchTerm) {
       // If a non-empty search value is provided
       List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

       if (!matchedPatients.isEmpty()) {
           // Single or multiple matches found, set the flag and add to the model
           model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
           model.addAttribute("matchedSearchPatientCharacterList", matchedPatients);

           // Populate DiagnosticRecord list for each patient
           List<DiagnosticRecord> searchedDiagnosticRecordLists = new ArrayList<>();
           for (Patient patient : matchedPatients) {
               // Check for null to avoid potential NullPointerException
               if (patient.getDiagnosticRecords() != null && !patient.getDiagnosticRecords().isEmpty()) {
                   // Only add the first patient case
                   DiagnosticRecord oneDiagnosticRecord = patient.getDiagnosticRecords().get(0);

                   // Calculate date differences
                   LocalDate searchedPatientBirthDay = oneDiagnosticRecord.getPatient().getDateOfBirth();
                   LocalDate searchedDiagnosticRecordHistory = oneDiagnosticRecord.getPatient().getDateOfBirth();

                   long searchedPatientAge = searchUtil.calculateDateDifference(searchedPatientBirthDay, LocalDate.now(), ChronoUnit.YEARS);
                   long diagnosticRecordHistory = searchUtil.calculateDateDifference(searchedDiagnosticRecordHistory, LocalDate.now(), ChronoUnit.YEARS);

                   // Use PatientFilterUtil to get the most recent PastMedicalHistory
                   PastMedicalHistory mostRecentPastMedicalRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patient.getId());

                   // Calculate length of medical condition
                   if (mostRecentPastMedicalRecord != null) {
                       LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
                       long searchedPatientLengthOfMedicalCondition = searchUtil.calculateDateDifference(medicalConditionStartDate, LocalDate.now(), ChronoUnit.DAYS);

                       // Add to the model
                       model.addAttribute("searchedPatientAge", searchedPatientAge);
                       model.addAttribute("searchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
                       model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
                   }

                   model.addAttribute("singleRecordHistory", diagnosticRecordHistory);
                   model.addAttribute("searchedPatientAge", searchedPatientAge);

                   searchedDiagnosticRecordLists.add(oneDiagnosticRecord);
               }
           }

           model.addAttribute("searchedSingleDiagnosticRecordsList", searchedDiagnosticRecordLists);
       } else {
           // No match found, set the flag and add an empty list to the model
           model.addAttribute("isSingleMatch", false);
           model.addAttribute("matchedSingleDiagnosticRecordCharacterList", Collections.emptyList());
           model.addAttribute("searchedSingleDiagnosticRecordsLists", Collections.emptyList());
       }
   }

   // Method To Search For All Patient Diagnostic Record
   public void searchDiagnosticRecordByCharacter(Model model, String trimmedSearchTerm) {
       // If a non-empty search value is provided
       List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

       if (!matchedPatients.isEmpty()) {
           // Single or multiple matches found, set the flag and add to the model
           model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
           model.addAttribute("matchedSearchPatientCharacterList", matchedPatients);

           // Populate DiagnosticRecord list for each patient
           List<DiagnosticRecord> searchedDiagnosticRecordLists = new ArrayList<>();
           for (Patient patient : matchedPatients) {
               List<DiagnosticRecord> diagnosticRecordList = new ArrayList<>();

               // Check for null to avoid potential NullPointerException
               if (patient.getDiagnosticRecords() != null) {
                   for (DiagnosticRecord oneDiagnosticRecord : patient.getDiagnosticRecords()) {
                       // Handle null DiagnosticRecord or Patient
                       if (oneDiagnosticRecord == null || oneDiagnosticRecord.getPatient() == null) {
                           continue;
                       }

                       // Calculate date differences
                       LocalDate createdAt = oneDiagnosticRecord.getCreatedAt().toLocalDate();
                       LocalDate onsetOfPatientCase = oneDiagnosticRecord.getPatientCase().getOnset();
                       LocalDate searchedPatientBirthDay = oneDiagnosticRecord.getPatient().getDateOfBirth();
                       LocalDate diagnosticRecordPatientCaseCreatedAt = oneDiagnosticRecord.getPatientCase().getCreatedAt().toLocalDate();

                       long searchedPatientAge = searchUtil.calculateDateDifference(searchedPatientBirthDay, LocalDate.now(), ChronoUnit.YEARS);
                       long accountLengthYears = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.YEARS);
                       long accountLengthDays = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.DAYS);
                       long accountLengthMonths = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.MONTHS);

                       long conditionLengthYears = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.YEARS);
                       long conditionLengthDays = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.DAYS);
                       long conditionLengthMonths = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.MONTHS);

                       filterUtil.addPhysicalAssessmentInfoToModel(model, patient.getId());

                       // Use PatientFilterUtil to get the most recent PastMedicalHistory
                       PastMedicalHistory mostRecentPastMedicalRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patient.getId());

                       if (mostRecentPastMedicalRecord != null) {
                           LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
                           long searchedPatientLengthOfMedicalCondition = searchUtil.calculateDateDifference(medicalConditionStartDate, LocalDate.now(), ChronoUnit.YEARS);

                           // Add to the model
                           model.addAttribute("diagnosticRecordPatientAge", searchedPatientAge);
                           model.addAttribute("diagnosticRecordAccountHistoryYears", accountLengthYears);
                           model.addAttribute("diagnosticRecordAccountHistoryDays", accountLengthDays);
                           model.addAttribute("diagnosticRecordAccountHistoryMonths", accountLengthMonths);
                           model.addAttribute("lengthOfPatientCaseConditionYears", conditionLengthYears);
                           model.addAttribute("lengthOfPatientCaseConditionDays", conditionLengthDays);
                           model.addAttribute("lengthOfPatientCaseConditionMonths", conditionLengthMonths);
                           model.addAttribute("diagnosticSearchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
                           model.addAttribute("diagnosticSearchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
                           model.addAttribute("diagnosticRecordPatientCaseDayCreatedAt", diagnosticRecordPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
                           model.addAttribute("diagnosticRecordPatientCaseCreatedAt", diagnosticRecordPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
                           model.addAttribute("diagnosticSearchedMostRecentPastMedicalRecordDayCreatedAt", mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
                           model.addAttribute("diagnosticSearchedMostRecentPastMedicalRecordCreatedAt", mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
                           model.addAttribute("searchedDiagnosticRecordDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
                           model.addAttribute("searchedDiagnosticRecordCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
                           searchUtil.addDateAttributesToModel(model, oneDiagnosticRecord.getCreatedAt().toLocalDate(), "diagnosticRecordAccountLength");
                           searchUtil.addDateAttributesToModel(model, oneDiagnosticRecord.getPatientCase().getOnset(), "lengthOfPatientCondition");
                       } else {
                           // Add to the model without PastMedicalHistory details
                           model.addAttribute("searchedPatientAge", searchedPatientAge);
                           model.addAttribute("diagnosticRecordAccountLength", accountLengthYears);
                           model.addAttribute("searchedDiagnosticRecordDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
                           model.addAttribute("searchedDiagnosticRecordCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
                           searchUtil.addDateAttributesToModel(model, oneDiagnosticRecord.getCreatedAt().toLocalDate(), "diagnosticRecordAccountLength");
                           searchUtil.addDateAttributesToModel(model, oneDiagnosticRecord.getPatientCase().getOnset(), "lengthOfPatientCasesCondition");
                       }

                       // Add DiagnosticRecord to the list
                       diagnosticRecordList.add(oneDiagnosticRecord);
                   }
               }

               searchedDiagnosticRecordLists.addAll(diagnosticRecordList);
           }

           model.addAttribute("searchedDiagnosticRecordsList", searchedDiagnosticRecordLists);
       } else {
           // No match found, set the flag and add an empty list to the model
           model.addAttribute("isSingleMatch", false);
           model.addAttribute("matchedDiagnosticRecordCharacterList", Collections.emptyList());
           model.addAttribute("searchedDiagnosticRecordsLists", Collections.emptyList());
       }
   }

   // Search Physical Assessment by Character
   public void searchPhysicalAssessmentByCharacter(Model model, String trimmedSearchTerm) {
       List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());
	    // Iterate through patient cases for each patient
	    for (Patient patient : matchedPatients) {
	        List<PhysicalAssessment> physicalAssessmentList = new ArrayList<>();

	        // Check for null to avoid potential NullPointerException
	        if (patient.getPatientCases() != null) {
	            for (PatientCase patientCase : patient.getPatientCases()) {
	                // Check for null to avoid potential NullPointerException
	                if (patientCase.getPhysicalAssessments() != null) {
	                    for (PhysicalAssessment assessment : patientCase.getPhysicalAssessments()) {
	                        // Use the utility method to calculate account history in days
	                        long accountHistoryDays = searchUtil.calculateDateDifference(assessment.getCreatedAt(), LocalDate.now(), ChronoUnit.DAYS);
	                        LocalDate physicalAssessmentCreatedAt = assessment.getCreatedAt();
	                        String formattedPhysicalAssessmentCreatedAtDate = physicalAssessmentCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	                        String formattedPhysicalAssessmentDayCreatedAtDate = physicalAssessmentCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	                        // Set the calculated account history and created at date in the assessment
	                        model.addAttribute("physicalAssessmentHistory", accountHistoryDays);
	                        model.addAttribute("physicalAssessmentCreatedAtList", formattedPhysicalAssessmentCreatedAtDate);
	                        model.addAttribute("physicalAssessmentDayCreatedAtList", formattedPhysicalAssessmentDayCreatedAtDate);
	                        // Add the physical assessment to the list
	                        physicalAssessmentList.add(assessment);
	                    }
	                }
	            }
	        }

	        // Add the physical assessments to the model
	        model.addAttribute("physicalAssessmentList", physicalAssessmentList);
	    	}
	    }

   // Method To Find Single Physical Assessment List 
	public void searchSinglePhysicalAssessmentByCharacter(Model model, String trimmedSearchTerm) {
      // If a non-empty search value is provided
      List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

      if (!matchedPatients.isEmpty()) {
          // Single or multiple matches found, set the flag and add to the model
          model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
          model.addAttribute("matchedSearchPatientCharacterList", matchedPatients);

          // Populate PhysicalAssessment list for each patient
          List<PhysicalAssessment> searchedPhysicalAssessmentLists = new ArrayList<>();
          for (Patient patient : matchedPatients) {
              // Check for null to avoid potential NullPointerException
              if (patient.getPhysicalAssessments() != null && !patient.getPhysicalAssessments().isEmpty()) {
                  // Only add the first patient case
                  PhysicalAssessment onePhysicalAssessment = patient.getPhysicalAssessments().get(0);

                  // Calculate date differences
                  LocalDate searchedPatientBirthDay = onePhysicalAssessment.getPatient().getDateOfBirth();
                  LocalDate searchedPhysicalAssessmentHistory = onePhysicalAssessment.getPatient().getDateOfBirth();

                  long searchedPatientAge = searchUtil.calculateDateDifference(searchedPatientBirthDay, LocalDate.now(), ChronoUnit.YEARS);
                  long physicalAssessmentHistory = searchUtil.calculateDateDifference(searchedPhysicalAssessmentHistory, LocalDate.now(), ChronoUnit.YEARS);

                  // Use PatientFilterUtil to get the most recent PastMedicalHistory
                  PastMedicalHistory mostRecentPastMedicalRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patient.getId());

                  // Calculate length of medical condition
                  if (mostRecentPastMedicalRecord != null) {
                      LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
                      long searchedPatientLengthOfMedicalCondition = searchUtil.calculateDateDifference(medicalConditionStartDate, LocalDate.now(), ChronoUnit.DAYS);

                      // Add to the model
                      model.addAttribute("searchedPatientAge", searchedPatientAge);
                      model.addAttribute("searchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
                      model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
                  }

                  model.addAttribute("singleRecordHistory", physicalAssessmentHistory);
                  model.addAttribute("searchedPatientAge", searchedPatientAge);
                  model.addAttribute("searchedSinglePhysicalAssessmentsList", searchedPhysicalAssessmentLists);

                  searchedPhysicalAssessmentLists.add(onePhysicalAssessment);
              }
          }

          model.addAttribute("searchedSinglePhysicalAssessmentsList", searchedPhysicalAssessmentLists);
      } else {
          // No match found, set the flag and add an empty list to the model
          model.addAttribute("isSingleMatch", false);
          model.addAttribute("matchedSinglePhysicalAssessmentCharacterList", Collections.emptyList());
          model.addAttribute("searchedSinglePhysicalAssessmentsLists", Collections.emptyList());
      }
  }

   // Method To Search For Patient Diagnostic Record
   public void alternativeSearchDiagnosticRecordByCharacter(Model model, String trimmedSearchTerm) {
       // If a non-empty search value is provided
       List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

       if (!matchedPatients.isEmpty()) {
           // Single or multiple matches found, set the flag and add to the model
           model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
           model.addAttribute("matchedSearchPatientCharacterList", matchedPatients);

           // Populate DiagnosticRecord list for each patient
           List<DiagnosticRecord> searchedDiagnosticRecordLists = new ArrayList<>();
           for (Patient patient : matchedPatients) {
               List<DiagnosticRecord> diagnosticRecordList = new ArrayList<>();

               // Check for null to avoid potential NullPointerException
               if (patient.getDiagnosticRecords() != null) {
                   for (DiagnosticRecord oneDiagnosticRecord : patient.getDiagnosticRecords()) {
                       // Handle null DiagnosticRecord or Patient
                       if (oneDiagnosticRecord == null || oneDiagnosticRecord.getPatient() == null) {
                           continue;
                       }

                       // Calculate date differences
                       LocalDate createdAt = oneDiagnosticRecord.getCreatedAt().toLocalDate();
                       LocalDate onsetOfPatientCase = oneDiagnosticRecord.getPatientCase().getOnset();
                       LocalDate searchedPatientBirthDay = oneDiagnosticRecord.getPatient().getDateOfBirth();
                       LocalDate diagnosticRecordPatientCaseCreatedAt = oneDiagnosticRecord.getPatientCase().getCreatedAt().toLocalDate();

                       long searchedPatientAge = searchUtil.calculateDateDifference(searchedPatientBirthDay, LocalDate.now(), ChronoUnit.YEARS);
                       long accountLengthYears = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.YEARS);
                       long accountLengthDays = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.DAYS);
                       long accountLengthMonths = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.MONTHS);

                       long conditionLengthYears = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.YEARS);
                       long conditionLengthDays = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.DAYS);
                       long conditionLengthMonths = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.MONTHS);

                       filterUtil.addPhysicalAssessmentInfoToModel(model, patient.getId());

                       // Use PatientFilterUtil to get the most recent PastMedicalHistory
                       PastMedicalHistory mostRecentPastMedicalRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patient.getId());

                       // Calculate length of medical condition if PastMedicalHistory is present
                       if (mostRecentPastMedicalRecord != null) {
                           LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
                           long searchedPatientLengthOfMedicalCondition = searchUtil.calculateDateDifference(medicalConditionStartDate, LocalDate.now(), ChronoUnit.YEARS);

                           // Add to the model
                           model.addAttribute("diagnosticRecordPatientAge", searchedPatientAge);
                           model.addAttribute("diagnosticRecordAccountHistoryYears", accountLengthYears);
                           model.addAttribute("diagnosticRecordAccountHistoryDays", accountLengthDays);
                           model.addAttribute("diagnosticRecordAccountHistoryMonths", accountLengthMonths);
                           model.addAttribute("lengthOfPatientCaseConditionYears", conditionLengthYears);
                           model.addAttribute("lengthOfPatientCaseConditionDays", conditionLengthDays);
                           model.addAttribute("lengthOfPatientCaseConditionMonths", conditionLengthMonths);
                           model.addAttribute("diagnosticSearchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
                           model.addAttribute("diagnosticSearchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
                           model.addAttribute("diagnosticRecordPatientCaseDayCreatedAt", diagnosticRecordPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
                           model.addAttribute("diagnosticRecordPatientCaseCreatedAt", diagnosticRecordPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
                           model.addAttribute("diagnosticSearchedMostRecentPastMedicalRecordDayCreatedAt", mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, yyyy")));
                           model.addAttribute("diagnosticSearchedMostRecentPastMedicalRecordCreatedAt", mostRecentPastMedicalRecord.getCreatedAt().format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
                           model.addAttribute("searchedDiagnosticRecordDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
                           model.addAttribute("searchedDiagnosticRecordCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
                           searchUtil.addDateAttributesToModel(model, oneDiagnosticRecord.getCreatedAt().toLocalDate(), "diagnosticRecordAccountLength");
                           searchUtil.addDateAttributesToModel(model, oneDiagnosticRecord.getPatientCase().getOnset(), "lengthOfPatientCondition");
                       } else {
                           // Add to the model without PastMedicalHistory details
                           model.addAttribute("searchedPatientAge", searchedPatientAge);
                           model.addAttribute("diagnosticRecordAccountLength", accountLengthYears);
                           model.addAttribute("searchedDiagnosticRecordDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
                           model.addAttribute("searchedDiagnosticRecordCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
                           searchUtil.addDateAttributesToModel(model, oneDiagnosticRecord.getCreatedAt().toLocalDate(), "diagnosticRecordAccountLength");
                           searchUtil.addDateAttributesToModel(model, oneDiagnosticRecord.getPatientCase().getOnset(), "lengthOfPatientCasesCondition");
                       }

                       // Add DiagnosticRecord to the list
                       diagnosticRecordList.add(oneDiagnosticRecord);
                   }
               }

               searchedDiagnosticRecordLists.addAll(diagnosticRecordList);
           }

           model.addAttribute("searchedDiagnosticRecordsList", searchedDiagnosticRecordLists);
       } else {
           // No match found, set the flag and add an empty list to the model
           model.addAttribute("isSingleMatch", false);
           model.addAttribute("matchedDiagnosticRecordCharacterList", Collections.emptyList());
           model.addAttribute("searchedDiagnosticRecordsLists", Collections.emptyList());
       }
   }

   // Method to search for patients and their patient cases by characters
   public List<DiagnosticRecord> returnSearchDiagnosticRecordByCharacter(String trimmedSearchTerm) {
       List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());
       List<DiagnosticRecord> searchedDiagnosticRecordList = new ArrayList<>();

       for (Patient patient : matchedPatients) {
           List<DiagnosticRecord> diagnosticRecords = patient.getDiagnosticRecords();
           if (diagnosticRecords != null) {
               searchedDiagnosticRecordList.addAll(diagnosticRecords);
           }
       }

       return searchedDiagnosticRecordList;
   }

   // Method to search for patients and return their first patient case by characters
   public List<DiagnosticRecord> returnFirstDiagnosticRecordByCharacter(String trimmedSearchTerm) {
       List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());
       List<DiagnosticRecord> firstDiagnosticRecords = new ArrayList<>();

       for (Patient patient : matchedPatients) {
           List<DiagnosticRecord> diagnosticRecords = patient.getDiagnosticRecords();
           if (diagnosticRecords != null && !diagnosticRecords.isEmpty()) {
               // Add only the first patient case
               firstDiagnosticRecords.add(diagnosticRecords.get(0));
           }
       }

       return firstDiagnosticRecords;
   }
   
   // Combined Method To Search For All Patient Current Medication Records
   public void searchCurrentMedicationByCharacter(Model model, String trimmedSearchTerm) {
       // If a non-empty search value is provided
       List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

       if (!matchedPatients.isEmpty()) {
           // Single or multiple matches found, set the flag and add to the model
           model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
           model.addAttribute("matchedSearchPatientCharacterList", matchedPatients);

           // Populate CurrentMedication list for each patient
           List<CurrentMedication> searchedCurrentMedicationLists = new ArrayList<>();
           for (Patient patient : matchedPatients) {
               List<CurrentMedication> currentMedicationList = new ArrayList<>();

               // Check for null to avoid potential NullPointerException
               if (patient.getCurrentMedications() != null) {
                   for (CurrentMedication oneCurrentMedication : patient.getCurrentMedications()) {
                       // Handle null CurrentMedication or Patient
                       if (oneCurrentMedication == null || oneCurrentMedication.getPatient() == null) {
                           continue;
                       }

                       // Calculate date differences
                       LocalDate createdAt = oneCurrentMedication.getCreatedAt().toLocalDate();
                       LocalDate onsetOfPatientCase = oneCurrentMedication.getPatientCase().getOnset();
                       LocalDate searchedPatientBirthDay = oneCurrentMedication.getPatient().getDateOfBirth();
                       LocalDate currentMedicationCreatedAt = oneCurrentMedication.getCreatedAt().toLocalDate();
                       LocalDate currentMedicationPatientCaseCreatedAt = oneCurrentMedication.getPatientCase().getCreatedAt().toLocalDate();

                       String formattedDayCurrentMedicationCreatedAtDate = currentMedicationCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
                       String formattedCurrentMedicationCreatedAtDate = currentMedicationCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
                       String formattedDayCurrentMedicationPatientCaseCreatedAt = currentMedicationPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
                       String formattedCurrentMedicationPatientCaseCreatedAt = currentMedicationPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

                       // Current Medication Account Histories
                       long currentMedicationAccountDaysHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.DAYS);
                       long currentMedicationAccountMonthsHistory= searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.MONTHS);
                       long currentMedicationAccountYearsHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.YEARS);

                       // Patient Case Condition Histories
                       long conditionLengthYears = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.YEARS);
                       long conditionLengthDays = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.DAYS);
                       long conditionLengthMonths = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.MONTHS);
                       long searchedPatientAge = searchUtil.calculateDateDifference(searchedPatientBirthDay, LocalDate.now(), ChronoUnit.YEARS);

                       filterUtil.addPhysicalAssessmentInfoToModel(model, patient.getId());

                       // Use PatientFilterUtil to get the most recent PastMedicalHistory
                       PastMedicalHistory mostRecentPastMedicalRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patient.getId());
                       if (mostRecentPastMedicalRecord != null) {
                           LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
                           LocalDate medicalConditionCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt();
                           long searchedPatientLengthOfMedicalCondition = searchUtil.calculateDateDifference(medicalConditionStartDate, LocalDate.now(), ChronoUnit.YEARS);

                           String formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
                           String formattedCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
                           // Add to the model
                           model.addAttribute("currentMedicationPatientAge", searchedPatientAge);
                           model.addAttribute("lengthOfPatientCaseConditionDays", conditionLengthDays);
                           model.addAttribute("lengthOfPatientCaseConditionMonths", conditionLengthMonths);
                           model.addAttribute("lengthOfPatientCaseConditionYears", conditionLengthYears);
                           model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
                           model.addAttribute("currentMedicationCreatedAt", formattedCurrentMedicationCreatedAtDate);
                           model.addAttribute("currentMedicationDayCreatedAt", formattedDayCurrentMedicationCreatedAtDate);
                           model.addAttribute("currentMedicationAccountDaysHistory", currentMedicationAccountDaysHistory);
                           model.addAttribute("currentMedicationAccountMonthsHistory", currentMedicationAccountMonthsHistory);
                           model.addAttribute("currentMedicationAccountYearsHistory", currentMedicationAccountYearsHistory);
                           model.addAttribute("searchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
                           model.addAttribute("currentMedicationPatientCaseDayCreatedAt", formattedDayCurrentMedicationPatientCaseCreatedAt);
                           model.addAttribute("currentMedicationPatientCaseCreatedAt", formattedCurrentMedicationPatientCaseCreatedAt);
                           model.addAttribute("searchedCurrentMedicatingPatientMostRecentPastMedicalRecordDayCreatedAt", formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate);
                           model.addAttribute("searchedCurrentMedicatingPatientMostRecentPastMedicalRecordCreatedAt",formattedCurrentMedicatingMedicaHistoryCreatedAtDate);
                           //model.addAttribute("searchedCurrentMedicationDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
                           //model.addAttribute("searchedCurrentMedicationCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
                           searchUtil.addDateAttributesToModel(model, oneCurrentMedication.getCreatedAt().toLocalDate(), "currentMedicationAccountLength");
                           searchUtil.addDateAttributesToModel(model, oneCurrentMedication.getPatientCase().getOnset(), "lengthOfPatientCondition");
                       } else {
                           // Add to the model without PastMedicalHistory details
                           model.addAttribute("searchedPatientAge", searchedPatientAge);
                           model.addAttribute("currentMedicationPatientAge", searchedPatientAge);
                           model.addAttribute("lengthOfPatientCaseConditionDays", conditionLengthDays);
                           model.addAttribute("lengthOfPatientCaseConditionMonths", conditionLengthMonths);
                           model.addAttribute("lengthOfPatientCaseConditionYears", conditionLengthYears);
                           model.addAttribute("currentMedicationAccountDaysHistory", currentMedicationAccountDaysHistory);
                           model.addAttribute("currentMedicationAccountMonthsHistory", currentMedicationAccountMonthsHistory);
                           model.addAttribute("currentMedicationAccountYearsHistory", currentMedicationAccountYearsHistory);
                           model.addAttribute("searchedCurrentMedicationDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
                           model.addAttribute("searchedCurrentMedicationCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
                           searchUtil.addDateAttributesToModel(model, oneCurrentMedication.getCreatedAt().toLocalDate(), "currentMedicationAccountLength");
                           searchUtil.addDateAttributesToModel(model, oneCurrentMedication.getPatientCase().getOnset(), "lengthOfPatientCasesCondition");
                       }

                       // Add CurrentMedication to the list
                       currentMedicationList.add(oneCurrentMedication);
                   }
               }

               searchedCurrentMedicationLists.addAll(currentMedicationList);
           }

           model.addAttribute("searchedCurrentMedicationsList", searchedCurrentMedicationLists);
       } else {
           // No match found, set the flag and add an empty list to the model
           model.addAttribute("isSingleMatch", false);
           model.addAttribute("matchedCurrentMedicationCharacterList", Collections.emptyList());
           model.addAttribute("searchedCurrentMedicationsLists", Collections.emptyList());
       }
   } 

	// Find Single CurrentMedication 
	public void searchSingleCurrentMedicationByCharacter(Model model, String trimmedSearchTerm) {
      // If a non-empty search value is provided
      List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

      if (!matchedPatients.isEmpty()) {
          // Single or multiple matches found, set the flag and add to the model
          model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
          model.addAttribute("matchedSearchPatientCharacterList", matchedPatients);

          // Populate CurrentMedication list for each patient
          List<CurrentMedication> searchedCurrentMedicationLists = new ArrayList<>();
          for (Patient patient : matchedPatients) {
              // Check for null to avoid potential NullPointerException
              if (patient.getCurrentMedications() != null && !patient.getCurrentMedications().isEmpty()) {
                  // Only add the first patient case
                  CurrentMedication oneCurrentMedication = patient.getCurrentMedications().get(0);

                  // Handle For Null CurrentMedication or Patient
                  if (oneCurrentMedication == null || oneCurrentMedication.getPatient() == null) {
                      continue;
                  }

                  // Calculate date differences
                  LocalDate createdAt = oneCurrentMedication.getCreatedAt().toLocalDate();
                  LocalDate onsetOfPatientCase = oneCurrentMedication.getPatientCase().getOnset();
                  LocalDate searchedPatientBirthDay = oneCurrentMedication.getPatient().getDateOfBirth();
                  LocalDate currentMedicationCreatedAt = oneCurrentMedication.getCreatedAt().toLocalDate();
                  LocalDate currentMedicationPatientCaseCreatedAt = oneCurrentMedication.getPatientCase().getCreatedAt().toLocalDate();

                  String formattedDayCurrentMedicationCreatedAtDate = currentMedicationCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
                  String formattedCurrentMedicationCreatedAtDate = currentMedicationCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
                  String formattedDayCurrentMedicationPatientCaseCreatedAt = currentMedicationPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
                  String formattedCurrentMedicationPatientCaseCreatedAt = currentMedicationPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

                  // Current Medication Account Histories
                  long currentMedicationAccountDaysHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.DAYS);
                  long currentMedicationAccountMonthsHistory= searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.MONTHS);
                  long currentMedicationAccountYearsHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.YEARS);

                  // Patient Case Condition Histories
                  long conditionLengthYears = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.YEARS);
                  long conditionLengthDays = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.DAYS);
                  long conditionLengthMonths = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.MONTHS);
                  long searchedPatientAge = searchUtil.calculateDateDifference(searchedPatientBirthDay, LocalDate.now(), ChronoUnit.YEARS);

                  filterUtil.addPhysicalAssessmentInfoToModel(model, patient.getId());

                  // Use PatientFilterUtil to get the most recent PastMedicalHistory
                  PastMedicalHistory mostRecentPastMedicalRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patient.getId());
                  if (mostRecentPastMedicalRecord != null) {
                      LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
                      LocalDate medicalConditionCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt();
                      long searchedPatientLengthOfMedicalCondition = searchUtil.calculateDateDifference(medicalConditionStartDate, LocalDate.now(), ChronoUnit.YEARS);

                      String formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
                      String formattedCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
                      // Add to the model
                      model.addAttribute("oneCurrentMedicationPatientAge", searchedPatientAge);
                      model.addAttribute("oneLengthOfPatientCaseConditionDays", conditionLengthDays);
                      model.addAttribute("oneLengthOfPatientCaseConditionMonths", conditionLengthMonths);
                      model.addAttribute("oneLengthOfPatientCaseConditionYears", conditionLengthYears);
                      model.addAttribute("oneSearchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
                      model.addAttribute("oneCurrentMedicationCreatedAt", formattedCurrentMedicationCreatedAtDate);
                      model.addAttribute("oneCurrentMedicationDayCreatedAt", formattedDayCurrentMedicationCreatedAtDate);
                      model.addAttribute("oneCurrentMedicationAccountDaysHistory", currentMedicationAccountDaysHistory);
                      model.addAttribute("oneCurrentMedicationAccountMonthsHistory", currentMedicationAccountMonthsHistory);
                      model.addAttribute("oneCurrentMedicationAccountYearsHistory", currentMedicationAccountYearsHistory);
                      model.addAttribute("oneSearchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
                      model.addAttribute("oneCurrentMedicationPatientCaseDayCreatedAt", formattedDayCurrentMedicationPatientCaseCreatedAt);
                      model.addAttribute("oneCurrentMedicationPatientCaseCreatedAt", formattedCurrentMedicationPatientCaseCreatedAt);
                      model.addAttribute("oneSearchedCurrentMedicatingPatientMostRecentPastMedicalRecordDayCreatedAt", formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate);
                      model.addAttribute("oneSearchedCurrentMedicatingPatientMostRecentPastMedicalRecordCreatedAt",formattedCurrentMedicatingMedicaHistoryCreatedAtDate);
                      //model.addAttribute("searchedCurrentMedicationDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
                      //model.addAttribute("searchedCurrentMedicationCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
                      searchUtil.addDateAttributesToModel(model, oneCurrentMedication.getCreatedAt().toLocalDate(), "currentMedicationAccountLength");
                      searchUtil.addDateAttributesToModel(model, oneCurrentMedication.getPatientCase().getOnset(), "lengthOfPatientCondition");
                  } else {
                      // Add to the model without PastMedicalHistory details
                      model.addAttribute("oneSearchedPatientAge", searchedPatientAge);
                      model.addAttribute("oneCurrentMedicationPatientAge", searchedPatientAge);
                      model.addAttribute("oneLengthOfPatientCaseConditionDays", conditionLengthDays);
                      model.addAttribute("oneLengthOfPatientCaseConditionMonths", conditionLengthMonths);
                      model.addAttribute("oneLengthOfPatientCaseConditionYears", conditionLengthYears);
                      model.addAttribute("oneCurrentMedicationAccountDaysHistory", currentMedicationAccountDaysHistory);
                      model.addAttribute("oneCurrentMedicationAccountMonthsHistory", currentMedicationAccountMonthsHistory);
                      model.addAttribute("oneCurrentMedicationAccountYearsHistory", currentMedicationAccountYearsHistory);
                      model.addAttribute("oneSearchedCurrentMedicationDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
                      model.addAttribute("oneSearchedCurrentMedicationCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
                      searchUtil.addDateAttributesToModel(model, oneCurrentMedication.getCreatedAt().toLocalDate(), "currentMedicationAccountLength");
                      searchUtil.addDateAttributesToModel(model, oneCurrentMedication.getPatientCase().getOnset(), "lengthOfPatientCasesCondition");
                  }

                  searchedCurrentMedicationLists.add(oneCurrentMedication);
              }
          }

          model.addAttribute("oneSearchedSingleCurrentMedicationsList", searchedCurrentMedicationLists);
      } else {
          // No match found, set the flag and add an empty list to the model
          model.addAttribute("isSingleMatch", false);
          model.addAttribute("matchedSingleCurrentMedicationCharacterList", Collections.emptyList());
          model.addAttribute("searchedSingleCurrentMedicationsLists", Collections.emptyList());
      }
  }

   // Method to search for patients and return their first current medication by characters
	public List<CurrentMedication> searchFirstCurrentMedicationByCharacter(String trimmedSearchTerm) {
	    if (trimmedSearchTerm != null) {
	        List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());
	        List<CurrentMedication> firstCurrentMedications = new ArrayList<>();
	
	        for (Patient patient : matchedPatients) {
	            if (patient.getCurrentMedications() != null && !patient.getCurrentMedications().isEmpty()) {
	                // Add only the first current medication
	                firstCurrentMedications.add(patient.getCurrentMedications().get(0));
	            }
	        }
	
	        return firstCurrentMedications;
	    }
	    return Collections.emptyList(); // Return an empty list if the search term is null
	}
	
	// Method to retrieve additional details for a specific current medication
	private void addCurrentMedicationDetailsToModel(Model model, CurrentMedication currentMedication) {
	    if (currentMedication != null) {
	        // Extract necessary details and add to the model
	        LocalDate createdAt = currentMedication.getCreatedAt().toLocalDate();
	        LocalDate onsetOfPatientCase = currentMedication.getPatientCase().getOnset();	
	
	        // Formatting createdAt date
	        String formattedDayCurrentMedicationCreatedAtDate = createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	        String formattedCurrentMedicationCreatedAtDate = createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	
	        model.addAttribute("currentMedicationCreatedAt", formattedCurrentMedicationCreatedAtDate);
	        model.addAttribute("currentMedicationDayCreatedAt", formattedDayCurrentMedicationCreatedAtDate);
	
	        // ... (other details added to the model)
	
	        // Use other utility methods for date attributes, if applicable
	        searchUtil.addDateAttributesToModel(model, createdAt, "currentMedicationAccountLength");
	        searchUtil.addDateAttributesToModel(model, onsetOfPatientCase, "lengthOfPatientCondition");
	    }
	}
	
	// Method to search for current medications and return additional details for a specific patient
	public void searchCurrentMedicationAndDetailsByCharacter(Model model, String trimmedSearchTerm) {
	    List<CurrentMedication> firstCurrentMedications = searchFirstCurrentMedicationByCharacter(trimmedSearchTerm);
	
	    if (!firstCurrentMedications.isEmpty()) {
	        model.addAttribute("isSingleMatch", firstCurrentMedications.size() == 1);
	        model.addAttribute("matchedSearchCurrentMedicationCharacterList", firstCurrentMedications);
	
	        // Populate CurrentMedication details for each patient
	        for (CurrentMedication currentMedication : firstCurrentMedications) {
	            addCurrentMedicationDetailsToModel(model, currentMedication);
	        }
	    } else {
	        model.addAttribute("isSingleMatch", false);
	        model.addAttribute("matchedSearchCurrentMedicationCharacterList", Collections.emptyList());
	    }
	}
	   
	   // Combined Method To Search For All Patient Dose Regimen Records
	   public void searchDoseRegimenRecordByCharacter(Model model, String trimmedSearchTerm) {
	       // If a non-empty search value is provided
	       List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

	       if (!matchedPatients.isEmpty()) {
	           // Single or multiple matches found, set the flag and add to the model
	           model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
	           model.addAttribute("matchedSearchPatientCharacterList", matchedPatients);

	           // Populate DoseRegimen list for each patient
	           List<DoseRegimen> searchedDoseRegimenLists = new ArrayList<>();
	           for (Patient patient : matchedPatients) {
	               List<DoseRegimen> doseRegimenList = new ArrayList<>();

	               // Check for null to avoid potential NullPointerException
	               if (patient.getDoseRegimenRecords() != null) {
	                   for (DoseRegimen oneDoseRegimen : patient.getDoseRegimenRecords()) {
	                       // Handle null DoseRegimen or Patient
	                       if (oneDoseRegimen == null || oneDoseRegimen.getPatient() == null) {
	                           continue;
	                       }

	                       // Calculate date differences
	                       LocalDate createdAt = oneDoseRegimen.getCreatedAt().toLocalDate();
	                       LocalDate onsetOfPatientCase = oneDoseRegimen.getPatientCase().getOnset();
	                       LocalDate searchedPatientBirthDay = oneDoseRegimen.getPatient().getDateOfBirth();
	                       LocalDate doseRegimenCreatedAt = oneDoseRegimen.getCreatedAt().toLocalDate();
	                       LocalDate doseRegimenPatientCaseCreatedAt = oneDoseRegimen.getPatientCase().getCreatedAt().toLocalDate();

	                       String formattedDayDoseRegimenCreatedAtDate = doseRegimenCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                       String formattedDoseRegimenCreatedAtDate = doseRegimenCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	                       String formattedDayDoseRegimenPatientCaseCreatedAt = doseRegimenPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                       String formattedDoseRegimenPatientCaseCreatedAt = doseRegimenPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	                       // Current Medication Account Histories
	                       long doseRegimenAccountDaysHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.DAYS);
	                       long doseRegimenAccountMonthsHistory= searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.MONTHS);
	                       long doseRegimenAccountYearsHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.YEARS);

	                       // Patient Case Condition Histories
	                       long conditionLengthYears = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.YEARS);
	                       long conditionLengthDays = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.DAYS);
	                       long conditionLengthMonths = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.MONTHS);
	                       long searchedPatientAge = searchUtil.calculateDateDifference(searchedPatientBirthDay, LocalDate.now(), ChronoUnit.YEARS);

	                       filterUtil.addPhysicalAssessmentInfoToModel(model, patient.getId());

	                       // Use PatientFilterUtil to get the most recent PastMedicalHistory
	                       PastMedicalHistory mostRecentPastMedicalRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patient.getId());
	                       if (mostRecentPastMedicalRecord != null) {
	                           LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	                           LocalDate medicalConditionCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt();
	                           long searchedPatientLengthOfMedicalCondition = searchUtil.calculateDateDifference(medicalConditionStartDate, LocalDate.now(), ChronoUnit.YEARS);

	                           String formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                           String formattedCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	                           // Add to the model
	                           model.addAttribute("doseRegimenPatientAge", searchedPatientAge);
	                           model.addAttribute("searchedPatientDoseRegimenRecords", oneDoseRegimen);
	                           model.addAttribute("lengthOfPatientCaseConditionDays", conditionLengthDays);
	                           model.addAttribute("lengthOfPatientCaseConditionMonths", conditionLengthMonths);
	                           model.addAttribute("lengthOfPatientCaseConditionYears", conditionLengthYears);
	                           model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
	                           model.addAttribute("doseRegimenCreatedAt", formattedDoseRegimenCreatedAtDate);
	                           model.addAttribute("doseRegimenDayCreatedAt", formattedDayDoseRegimenCreatedAtDate);
	                           model.addAttribute("doseRegimenAccountDaysHistory", doseRegimenAccountDaysHistory);
	                           model.addAttribute("doseRegimenAccountMonthsHistory", doseRegimenAccountMonthsHistory);
	                           model.addAttribute("doseRegimenAccountYearsHistory", doseRegimenAccountYearsHistory);
	                           model.addAttribute("searchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
	                           model.addAttribute("doseRegimenPatientCaseDayCreatedAt", formattedDayDoseRegimenPatientCaseCreatedAt);
	                           model.addAttribute("doseRegimenPatientCaseCreatedAt", formattedDoseRegimenPatientCaseCreatedAt);
	                           model.addAttribute("searchedCurrentMedicatingPatientMostRecentPastMedicalRecordDayCreatedAt", formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate);
	                           model.addAttribute("searchedCurrentMedicatingPatientMostRecentPastMedicalRecordCreatedAt",formattedCurrentMedicatingMedicaHistoryCreatedAtDate);
	                           //model.addAttribute("searchedDoseRegimenDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	                           //model.addAttribute("searchedDoseRegimenCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	                           searchUtil.addDateAttributesToModel(model, oneDoseRegimen.getCreatedAt().toLocalDate(), "doseRegimenAccountLength");
	                           searchUtil.addDateAttributesToModel(model, oneDoseRegimen.getPatientCase().getOnset(), "lengthOfPatientCondition");
	                       } else {
	                           // Add to the model without PastMedicalHistory details
	                           model.addAttribute("searchedPatientAge", searchedPatientAge);
	                           model.addAttribute("doseRegimenPatientAge", searchedPatientAge);
	                           model.addAttribute("searchedPatientDoseRegimenRecords", oneDoseRegimen);
	                           model.addAttribute("lengthOfPatientCaseConditionDays", conditionLengthDays);
	                           model.addAttribute("lengthOfPatientCaseConditionMonths", conditionLengthMonths);
	                           model.addAttribute("lengthOfPatientCaseConditionYears", conditionLengthYears);
	                           model.addAttribute("doseRegimenAccountDaysHistory", doseRegimenAccountDaysHistory);
	                           model.addAttribute("doseRegimenAccountMonthsHistory", doseRegimenAccountMonthsHistory);
	                           model.addAttribute("doseRegimenAccountYearsHistory", doseRegimenAccountYearsHistory);
	                           model.addAttribute("searchedDoseRegimenDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	                           model.addAttribute("searchedDoseRegimenCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	                           searchUtil.addDateAttributesToModel(model, oneDoseRegimen.getCreatedAt().toLocalDate(), "doseRegimenAccountLength");
	                           searchUtil.addDateAttributesToModel(model, oneDoseRegimen.getPatientCase().getOnset(), "lengthOfPatientCasesCondition");
	                       }

	                       // Add DoseRegimen to the list
	                       doseRegimenList.add(oneDoseRegimen);
	                   }
	               }

	               searchedDoseRegimenLists.addAll(doseRegimenList);
	           }

	           model.addAttribute("searchedDoseRegimensList", searchedDoseRegimenLists);
	       } else {
	           // No match found, set the flag and add an empty list to the model
	           model.addAttribute("isSingleMatch", false);
	           model.addAttribute("matchedDoseRegimenCharacterList", Collections.emptyList());
	           model.addAttribute("searchedDoseRegimensLists", Collections.emptyList());
	       }
	   } 

		// Find Single DoseRegimen 
		public void searchSingleDoseRegimenRecordByCharacter(Model model, String trimmedSearchTerm) {
	      // If a non-empty search value is provided
	      List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

	      if (!matchedPatients.isEmpty()) {
	          // Single or multiple matches found, set the flag and add to the model
	          model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
	          model.addAttribute("matchedSearchPatientCharacterList", matchedPatients);

	          // Populate DoseRegimen list for each patient
	          List<DoseRegimen> searchedDoseRegimenLists = new ArrayList<>();
	          for (Patient patient : matchedPatients) {
	              // Check for null to avoid potential NullPointerException
	              if (patient.getDoseRegimenRecords() != null && !patient.getDoseRegimenRecords().isEmpty()) {
	                  // Only add the first patient case
	                  DoseRegimen oneDoseRegimen = patient.getDoseRegimenRecords().get(0);

	                  // Handle For Null DoseRegimen or Patient
	                  if (oneDoseRegimen == null || oneDoseRegimen.getPatient() == null) {
	                      continue;
	                  }

	                  // Calculate date differences
	                  LocalDate createdAt = oneDoseRegimen.getCreatedAt().toLocalDate();
	                  LocalDate onsetOfPatientCase = oneDoseRegimen.getPatientCase().getOnset();
	                  LocalDate searchedPatientBirthDay = oneDoseRegimen.getPatient().getDateOfBirth();
	                  LocalDate doseRegimenCreatedAt = oneDoseRegimen.getCreatedAt().toLocalDate();
	                  LocalDate doseRegimenPatientCaseCreatedAt = oneDoseRegimen.getPatientCase().getCreatedAt().toLocalDate();

	                  String formattedDayDoseRegimenCreatedAtDate = doseRegimenCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                  String formattedDoseRegimenCreatedAtDate = doseRegimenCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	                  String formattedDayDoseRegimenPatientCaseCreatedAt = doseRegimenPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                  String formattedDoseRegimenPatientCaseCreatedAt = doseRegimenPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	                  // Current Medication Account Histories
	                  long doseRegimenAccountDaysHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.DAYS);
	                  long doseRegimenAccountMonthsHistory= searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.MONTHS);
	                  long doseRegimenAccountYearsHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.YEARS);

	                  // Patient Case Condition Histories
	                  long conditionLengthYears = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.YEARS);
	                  long conditionLengthDays = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.DAYS);
	                  long conditionLengthMonths = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.MONTHS);
	                  long searchedPatientAge = searchUtil.calculateDateDifference(searchedPatientBirthDay, LocalDate.now(), ChronoUnit.YEARS);

	                  filterUtil.addPhysicalAssessmentInfoToModel(model, patient.getId());

	                  // Use PatientFilterUtil to get the most recent PastMedicalHistory
	                  PastMedicalHistory mostRecentPastMedicalRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patient.getId());
	                  if (mostRecentPastMedicalRecord != null) {
	                      LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	                      LocalDate medicalConditionCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt();
	                      long searchedPatientLengthOfMedicalCondition = searchUtil.calculateDateDifference(medicalConditionStartDate, LocalDate.now(), ChronoUnit.YEARS);

	                      String formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                      String formattedCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	                      // Add to the model
	                      model.addAttribute("oneDoseRegimenPatientAge", searchedPatientAge);
                          model.addAttribute("oneSearchedPatientDoseRegimenRecords", oneDoseRegimen);
	                      model.addAttribute("oneLengthOfPatientCaseConditionDays", conditionLengthDays);
	                      model.addAttribute("oneLengthOfPatientCaseConditionMonths", conditionLengthMonths);
	                      model.addAttribute("oneLengthOfPatientCaseConditionYears", conditionLengthYears);
	                      model.addAttribute("oneSearchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
	                      model.addAttribute("oneDoseRegimenCreatedAt", formattedDoseRegimenCreatedAtDate);
	                      model.addAttribute("oneDoseRegimenDayCreatedAt", formattedDayDoseRegimenCreatedAtDate);
	                      model.addAttribute("oneDoseRegimenAccountDaysHistory", doseRegimenAccountDaysHistory);
	                      model.addAttribute("oneDoseRegimenAccountMonthsHistory", doseRegimenAccountMonthsHistory);
	                      model.addAttribute("oneDoseRegimenAccountYearsHistory", doseRegimenAccountYearsHistory);
	                      model.addAttribute("oneSearchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
	                      model.addAttribute("oneDoseRegimenPatientCaseDayCreatedAt", formattedDayDoseRegimenPatientCaseCreatedAt);
	                      model.addAttribute("oneDoseRegimenPatientCaseCreatedAt", formattedDoseRegimenPatientCaseCreatedAt);
	                      model.addAttribute("oneSearchedCurrentMedicatingPatientMostRecentPastMedicalRecordDayCreatedAt", formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate);
	                      model.addAttribute("oneSearchedCurrentMedicatingPatientMostRecentPastMedicalRecordCreatedAt",formattedCurrentMedicatingMedicaHistoryCreatedAtDate);
	                      //model.addAttribute("searchedDoseRegimenDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	                      //model.addAttribute("searchedDoseRegimenCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	                      searchUtil.addDateAttributesToModel(model, oneDoseRegimen.getCreatedAt().toLocalDate(), "doseRegimenAccountLength");
	                      searchUtil.addDateAttributesToModel(model, oneDoseRegimen.getPatientCase().getOnset(), "lengthOfPatientCondition");
	                  } else {
	                      // Add to the model without PastMedicalHistory details
	                      model.addAttribute("oneSearchedPatientAge", searchedPatientAge);
	                      model.addAttribute("oneDoseRegimenPatientAge", searchedPatientAge);
                          model.addAttribute("oneSearchedPatientDoseRegimenRecords", oneDoseRegimen);
	                      model.addAttribute("oneLengthOfPatientCaseConditionDays", conditionLengthDays);
	                      model.addAttribute("oneLengthOfPatientCaseConditionMonths", conditionLengthMonths);
	                      model.addAttribute("oneLengthOfPatientCaseConditionYears", conditionLengthYears);
	                      model.addAttribute("oneDoseRegimenAccountDaysHistory", doseRegimenAccountDaysHistory);
	                      model.addAttribute("oneDoseRegimenAccountMonthsHistory", doseRegimenAccountMonthsHistory);
	                      model.addAttribute("oneDoseRegimenAccountYearsHistory", doseRegimenAccountYearsHistory);
	                      model.addAttribute("oneSearchedDoseRegimenDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	                      model.addAttribute("oneSearchedDoseRegimenCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	                      searchUtil.addDateAttributesToModel(model, oneDoseRegimen.getCreatedAt().toLocalDate(), "doseRegimenAccountLength");
	                      searchUtil.addDateAttributesToModel(model, oneDoseRegimen.getPatientCase().getOnset(), "lengthOfPatientCasesCondition");
	                  }

	                  searchedDoseRegimenLists.add(oneDoseRegimen);
	              }
	          }

	          model.addAttribute("oneSearchedSingleDoseRegimensList", searchedDoseRegimenLists);
	      } else {
	          // No match found, set the flag and add an empty list to the model
	          model.addAttribute("isSingleMatch", false);
	          model.addAttribute("matchedSingleDoseRegimenCharacterList", Collections.emptyList());
	          model.addAttribute("searchedSingleDoseRegimensLists", Collections.emptyList());
	      }
	  }

	   // Method to search for patients and return their first current medication by characters
		public List<DoseRegimen> searchFirstDoseRegimenByCharacter(String trimmedSearchTerm) {
		    if (trimmedSearchTerm != null) {
		        List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());
		        List<DoseRegimen> firstDoseRegimens = new ArrayList<>();
		
		        for (Patient patient : matchedPatients) {
		            if (patient.getDoseRegimenRecords() != null && !patient.getDoseRegimenRecords().isEmpty()) {
		                // Add only the first current medication
		                firstDoseRegimens.add(patient.getDoseRegimenRecords().get(0));
		            }
		        }
		
		        return firstDoseRegimens;
		    }
		    return Collections.emptyList(); // Return an empty list if the search term is null
		}

		// Combined Method To Search For All Patient Coagulation Records
		   public void searchCoagulationRecordByCharacter(Model model, String trimmedSearchTerm) {
		       // If a non-empty search value is provided
		       List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

		       if (!matchedPatients.isEmpty()) {
		           // Single or multiple matches found, set the flag and add to the model
		           model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
		           model.addAttribute("matchedSearchPatientCharacterList", matchedPatients);

		           // Populate CoagulationRecord list for each patient
		           List<CoagulationRecord> searchedCoagulationRecordLists = new ArrayList<>();
		           for (Patient patient : matchedPatients) {
		               List<CoagulationRecord> coagulationRecordList = new ArrayList<>();

		               // Check for null to avoid potential NullPointerException
		               if (patient.getCoagulationRecords() != null) {
		                   for (CoagulationRecord oneCoagulationRecord : patient.getCoagulationRecords()) {
		                       // Handle null CoagulationRecord or Patient
		                       if (oneCoagulationRecord == null || oneCoagulationRecord.getPatient() == null) {
		                           continue;
		                       }

		                       // Calculate date differences
		                       LocalDate createdAt = oneCoagulationRecord.getCreatedAt().toLocalDate();
		                       LocalDate onsetOfPatientCase = oneCoagulationRecord.getPatientCase().getOnset();
		                       LocalDate searchedPatientBirthDay = oneCoagulationRecord.getPatient().getDateOfBirth();
		                       LocalDate coagulationRecordCreatedAt = oneCoagulationRecord.getCreatedAt().toLocalDate();
		                       LocalDate coagulationRecordPatientCaseCreatedAt = oneCoagulationRecord.getPatientCase().getCreatedAt().toLocalDate();

		                       String formattedDayCoagulationRecordCreatedAtDate = coagulationRecordCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
		                       String formattedCoagulationRecordCreatedAtDate = coagulationRecordCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
		                       String formattedDayCoagulationRecordPatientCaseCreatedAt = coagulationRecordPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
		                       String formattedCoagulationRecordPatientCaseCreatedAt = coagulationRecordPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

		                       // Current Medication Account Histories
		                       long coagulationRecordAccountDaysHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.DAYS);
		                       long coagulationRecordAccountMonthsHistory= searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.MONTHS);
		                       long coagulationRecordAccountYearsHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.YEARS);

		                       // Patient Case Condition Histories
		                       long conditionLengthYears = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.YEARS);
		                       long conditionLengthDays = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.DAYS);
		                       long conditionLengthMonths = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.MONTHS);
		                       long searchedPatientAge = searchUtil.calculateDateDifference(searchedPatientBirthDay, LocalDate.now(), ChronoUnit.YEARS);

		                       filterUtil.addPhysicalAssessmentInfoToModel(model, patient.getId());

		                       // Use PatientFilterUtil to get the most recent PastMedicalHistory
		                       PastMedicalHistory mostRecentPastMedicalRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patient.getId());
		                       if (mostRecentPastMedicalRecord != null) {
		                           LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
		                           LocalDate medicalConditionCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt();
		                           long searchedPatientLengthOfMedicalCondition = searchUtil.calculateDateDifference(medicalConditionStartDate, LocalDate.now(), ChronoUnit.YEARS);

		                           String formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
		                           String formattedCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
		                           // Add to the model
		                           model.addAttribute("coagulationRecordPatientAge", searchedPatientAge);
		                           model.addAttribute("lengthOfPatientCaseConditionDays", conditionLengthDays);
		                           model.addAttribute("lengthOfPatientCaseConditionMonths", conditionLengthMonths);
		                           model.addAttribute("lengthOfPatientCaseConditionYears", conditionLengthYears);
		                           model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
		                           model.addAttribute("coagulationRecordCreatedAt", formattedCoagulationRecordCreatedAtDate);
		                           model.addAttribute("coagulationRecordDayCreatedAt", formattedDayCoagulationRecordCreatedAtDate);
		                           model.addAttribute("coagulationRecordAccountDaysHistory", coagulationRecordAccountDaysHistory);
		                           model.addAttribute("coagulationRecordAccountMonthsHistory", coagulationRecordAccountMonthsHistory);
		                           model.addAttribute("coagulationRecordAccountYearsHistory", coagulationRecordAccountYearsHistory);
		                           model.addAttribute("searchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
		                           model.addAttribute("coagulationRecordPatientCaseDayCreatedAt", formattedDayCoagulationRecordPatientCaseCreatedAt);
		                           model.addAttribute("coagulationRecordPatientCaseCreatedAt", formattedCoagulationRecordPatientCaseCreatedAt);
		                           model.addAttribute("searchedCurrentMedicatingPatientMostRecentPastMedicalRecordDayCreatedAt", formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate);
		                           model.addAttribute("searchedCurrentMedicatingPatientMostRecentPastMedicalRecordCreatedAt",formattedCurrentMedicatingMedicaHistoryCreatedAtDate);
		                           //model.addAttribute("searchedCoagulationRecordDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		                           //model.addAttribute("searchedCoagulationRecordCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		                           searchUtil.addDateAttributesToModel(model, oneCoagulationRecord.getCreatedAt().toLocalDate(), "coagulationRecordAccountLength");
		                           searchUtil.addDateAttributesToModel(model, oneCoagulationRecord.getPatientCase().getOnset(), "lengthOfPatientCondition");
		                       } else {
		                           // Add to the model without PastMedicalHistory details
		                           model.addAttribute("searchedPatientAge", searchedPatientAge);
		                           model.addAttribute("coagulationRecordPatientAge", searchedPatientAge);
		                           model.addAttribute("lengthOfPatientCaseConditionDays", conditionLengthDays);
		                           model.addAttribute("lengthOfPatientCaseConditionMonths", conditionLengthMonths);
		                           model.addAttribute("lengthOfPatientCaseConditionYears", conditionLengthYears);
		                           model.addAttribute("coagulationRecordAccountDaysHistory", coagulationRecordAccountDaysHistory);
		                           model.addAttribute("coagulationRecordAccountMonthsHistory", coagulationRecordAccountMonthsHistory);
		                           model.addAttribute("coagulationRecordAccountYearsHistory", coagulationRecordAccountYearsHistory);
		                           model.addAttribute("searchedCoagulationRecordDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		                           model.addAttribute("searchedCoagulationRecordCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		                           searchUtil.addDateAttributesToModel(model, oneCoagulationRecord.getCreatedAt().toLocalDate(), "coagulationRecordAccountLength");
		                           searchUtil.addDateAttributesToModel(model, oneCoagulationRecord.getPatientCase().getOnset(), "lengthOfPatientCasesCondition");
		                       }

		                       // Add CoagulationRecord to the list
		                       coagulationRecordList.add(oneCoagulationRecord);
		                   }
		               }

		               searchedCoagulationRecordLists.addAll(coagulationRecordList);
		           }

		           model.addAttribute("searchedCoagulationRecordsList", searchedCoagulationRecordLists);
		       } else {
		           // No match found, set the flag and add an empty list to the model
		           model.addAttribute("isSingleMatch", false);
		           model.addAttribute("matchedCoagulationRecordCharacterList", Collections.emptyList());
		           model.addAttribute("searchedCoagulationRecordsLists", Collections.emptyList());
		       }
		   } 

			// Find Single CoagulationRecord 
			public void searchSingleCoagulationRecordByCharacter(Model model, String trimmedSearchTerm) {
		      // If a non-empty search value is provided
		      List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

		      if (!matchedPatients.isEmpty()) {
		          // Single or multiple matches found, set the flag and add to the model
		          model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
		          model.addAttribute("matchedSearchPatientCharacterList", matchedPatients);

		          // Populate CoagulationRecord list for each patient
		          List<CoagulationRecord> searchedCoagulationRecordLists = new ArrayList<>();
		          for (Patient patient : matchedPatients) {
		              // Check for null to avoid potential NullPointerException
		              if (patient.getCoagulationRecords() != null && !patient.getCoagulationRecords().isEmpty()) {
		                  // Only add the first patient case
		                  CoagulationRecord oneCoagulationRecord = patient.getCoagulationRecords().get(0);

		                  // Handle For Null CoagulationRecord or Patient
		                  if (oneCoagulationRecord == null || oneCoagulationRecord.getPatient() == null) {
		                      continue;
		                  }

		                  // Calculate date differences
		                  LocalDate createdAt = oneCoagulationRecord.getCreatedAt().toLocalDate();
		                  LocalDate onsetOfPatientCase = oneCoagulationRecord.getPatientCase().getOnset();
		                  LocalDate searchedPatientBirthDay = oneCoagulationRecord.getPatient().getDateOfBirth();
		                  LocalDate coagulationRecordCreatedAt = oneCoagulationRecord.getCreatedAt().toLocalDate();
		                  LocalDate coagulationRecordPatientCaseCreatedAt = oneCoagulationRecord.getPatientCase().getCreatedAt().toLocalDate();

		                  String formattedDayCoagulationRecordCreatedAtDate = coagulationRecordCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
		                  String formattedCoagulationRecordCreatedAtDate = coagulationRecordCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
		                  String formattedDayCoagulationRecordPatientCaseCreatedAt = coagulationRecordPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
		                  String formattedCoagulationRecordPatientCaseCreatedAt = coagulationRecordPatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

		                  // Current Medication Account Histories
		                  long coagulationRecordAccountDaysHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.DAYS);
		                  long coagulationRecordAccountMonthsHistory= searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.MONTHS);
		                  long coagulationRecordAccountYearsHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.YEARS);

		                  // Patient Case Condition Histories
		                  long conditionLengthYears = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.YEARS);
		                  long conditionLengthDays = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.DAYS);
		                  long conditionLengthMonths = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.MONTHS);
		                  long searchedPatientAge = searchUtil.calculateDateDifference(searchedPatientBirthDay, LocalDate.now(), ChronoUnit.YEARS);

		                  filterUtil.addPhysicalAssessmentInfoToModel(model, patient.getId());

		                  // Use PatientFilterUtil to get the most recent PastMedicalHistory
		                  PastMedicalHistory mostRecentPastMedicalRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patient.getId());
		                  if (mostRecentPastMedicalRecord != null) {
		                      LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
		                      LocalDate medicalConditionCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt();
		                      long searchedPatientLengthOfMedicalCondition = searchUtil.calculateDateDifference(medicalConditionStartDate, LocalDate.now(), ChronoUnit.YEARS);

		                      String formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
		                      String formattedCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
		                      // Add to the model
		                      model.addAttribute("oneCoagulationRecordPatientAge", searchedPatientAge);
		                      model.addAttribute("oneLengthOfPatientCaseConditionDays", conditionLengthDays);
		                      model.addAttribute("oneLengthOfPatientCaseConditionMonths", conditionLengthMonths);
		                      model.addAttribute("oneLengthOfPatientCaseConditionYears", conditionLengthYears);
		                      model.addAttribute("oneSearchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
		                      model.addAttribute("oneCoagulationRecordCreatedAt", formattedCoagulationRecordCreatedAtDate);
		                      model.addAttribute("oneCoagulationRecordDayCreatedAt", formattedDayCoagulationRecordCreatedAtDate);
		                      model.addAttribute("oneCoagulationRecordAccountDaysHistory", coagulationRecordAccountDaysHistory);
		                      model.addAttribute("oneCoagulationRecordAccountMonthsHistory", coagulationRecordAccountMonthsHistory);
		                      model.addAttribute("oneCoagulationRecordAccountYearsHistory", coagulationRecordAccountYearsHistory);
		                      model.addAttribute("oneSearchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
		                      model.addAttribute("oneCoagulationRecordPatientCaseDayCreatedAt", formattedDayCoagulationRecordPatientCaseCreatedAt);
		                      model.addAttribute("oneCoagulationRecordPatientCaseCreatedAt", formattedCoagulationRecordPatientCaseCreatedAt);
		                      model.addAttribute("oneSearchedCurrentMedicatingPatientMostRecentPastMedicalRecordDayCreatedAt", formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate);
		                      model.addAttribute("oneSearchedCurrentMedicatingPatientMostRecentPastMedicalRecordCreatedAt",formattedCurrentMedicatingMedicaHistoryCreatedAtDate);
		                      //model.addAttribute("searchedCoagulationRecordDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		                      //model.addAttribute("searchedCoagulationRecordCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		                      searchUtil.addDateAttributesToModel(model, oneCoagulationRecord.getCreatedAt().toLocalDate(), "coagulationRecordAccountLength");
		                      searchUtil.addDateAttributesToModel(model, oneCoagulationRecord.getPatientCase().getOnset(), "lengthOfPatientCondition");
		                  } else {
		                      // Add to the model without PastMedicalHistory details
		                      model.addAttribute("oneSearchedPatientAge", searchedPatientAge);
		                      model.addAttribute("oneCoagulationRecordPatientAge", searchedPatientAge);
		                      model.addAttribute("oneLengthOfPatientCaseConditionDays", conditionLengthDays);
		                      model.addAttribute("oneLengthOfPatientCaseConditionMonths", conditionLengthMonths);
		                      model.addAttribute("oneLengthOfPatientCaseConditionYears", conditionLengthYears);
		                      model.addAttribute("oneCoagulationRecordAccountDaysHistory", coagulationRecordAccountDaysHistory);
		                      model.addAttribute("oneCoagulationRecordAccountMonthsHistory", coagulationRecordAccountMonthsHistory);
		                      model.addAttribute("oneCoagulationRecordAccountYearsHistory", coagulationRecordAccountYearsHistory);
		                      model.addAttribute("oneSearchedCoagulationRecordDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
		                      model.addAttribute("oneSearchedCoagulationRecordCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
		                      searchUtil.addDateAttributesToModel(model, oneCoagulationRecord.getCreatedAt().toLocalDate(), "coagulationRecordAccountLength");
		                      searchUtil.addDateAttributesToModel(model, oneCoagulationRecord.getPatientCase().getOnset(), "lengthOfPatientCasesCondition");
		                  }

		                  searchedCoagulationRecordLists.add(oneCoagulationRecord);
		              }
		          }

		          model.addAttribute("oneSearchedSingleCoagulationRecordsList", searchedCoagulationRecordLists);
		      } else {
		          // No match found, set the flag and add an empty list to the model
		          model.addAttribute("isSingleMatch", false);
		          model.addAttribute("matchedSingleCoagulationRecordCharacterList", Collections.emptyList());
		          model.addAttribute("searchedSingleCoagulationRecordsLists", Collections.emptyList());
		      }
		  }
			   
		  // Combined Method To Search For All Patient Case Diagnostic Records
		   public void searchPatientCaseDiagnosticRecordByCharacter(Model model, String trimmedSearchTerm) {
	       // If a non-empty search value is provided
	       List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

	       if (!matchedPatients.isEmpty()) {
	           // Single or multiple matches found, set the flag and add to the model
	           model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
	           model.addAttribute("matchedSearchPatientCharacterList", matchedPatients);

	           // Populate PatientCase list for each patient
	           List<PatientCase> searchedPatientCaseLists = new ArrayList<>();
	           for (Patient patient : matchedPatients) {
	               List<PatientCase> patientCaseList = new ArrayList<>();

	               // Check for null to avoid potential NullPointerException
	               if (patient.getPatientCases() != null) {
	                   for (PatientCase onePatientCase : patient.getPatientCases()) {
	                       // Handle null PatientCase or Patient
	                       if (onePatientCase == null || onePatientCase.getPatient() == null) {
	                           continue;
	                       }

			               if (onePatientCase.getDiagnosticRecords() == null) {
			            	   continue;
			               }
	
			               for (DiagnosticRecord oneDiagnosticRecord : onePatientCase.getDiagnosticRecords()) {
				           List<DiagnosticRecord> patientCaseDiagnosticList = new ArrayList<>();

	                       // Calculate date differences
	                       LocalDate createdAt = onePatientCase.getCreatedAt().toLocalDate();
	                       LocalDate onsetOfPatientCase = onePatientCase.getOnset();
	                       LocalDate searchedPatientBirthDay = onePatientCase.getPatient().getDateOfBirth();
	                       LocalDate patientCaseCreatedAt = onePatientCase.getCreatedAt().toLocalDate();
	                       LocalDate diagnosticReportCreatedAt = oneDiagnosticRecord.getCreatedAt().toLocalDate();
	                       LocalDate patientCasePatientCaseCreatedAt = onePatientCase.getCreatedAt().toLocalDate();

	                       String formattedDayPatientCaseCreatedAtDate = patientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                       String formattedPatientCaseCreatedAtDate = patientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	                       String formattedDayPatientCasePatientCaseCreatedAt = patientCasePatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                       String formattedPatientCasePatientCaseCreatedAt = patientCasePatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	                       String formattedDayPatientCaseDiagnosticReportCreatedAtDate = diagnosticReportCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                       String formattedPatientCaseDiagnosticReportCreatedAtDate = diagnosticReportCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	                       // Current Medication Account Histories
	                       long patientCaseAccountDaysHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.DAYS);
	                       long patientCaseAccountMonthsHistory= searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.MONTHS);
	                       long patientCaseAccountYearsHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.YEARS);

	                       // Patient Case Condition Histories
	                       long conditionLengthYears = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.YEARS);
	                       long conditionLengthDays = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.DAYS);
	                       long conditionLengthMonths = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.MONTHS);
	                       long searchedPatientAge = searchUtil.calculateDateDifference(searchedPatientBirthDay, LocalDate.now(), ChronoUnit.YEARS);

	                       filterUtil.addPhysicalAssessmentInfoToModel(model, patient.getId());

	                       // Use PatientFilterUtil to get the most recent PastMedicalHistory
	                       PastMedicalHistory mostRecentPastMedicalRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patient.getId());
	                       if (mostRecentPastMedicalRecord != null) {
	                           LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	                           LocalDate medicalConditionCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt();
	                           long searchedPatientLengthOfMedicalCondition = searchUtil.calculateDateDifference(medicalConditionStartDate, LocalDate.now(), ChronoUnit.YEARS);

	                           String formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                           String formattedCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	                           // Add to the model
	                           model.addAttribute("patientCasePatientAge", searchedPatientAge);
	                           model.addAttribute("searchedPatientCaseDiagnosticRecord", oneDiagnosticRecord);
	                           model.addAttribute("lengthOfPatientCaseConditionDays", conditionLengthDays);
	                           model.addAttribute("lengthOfPatientCaseConditionMonths", conditionLengthMonths);
	                           model.addAttribute("lengthOfPatientCaseConditionYears", conditionLengthYears);
	                           model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
	                           model.addAttribute("patientCaseCreatedAt", formattedPatientCaseCreatedAtDate);
	                           model.addAttribute("patientCaseDayCreatedAt", formattedDayPatientCaseCreatedAtDate);
	                           model.addAttribute("patientCaseAccountDaysHistory", patientCaseAccountDaysHistory);
	                           model.addAttribute("patientCaseAccountMonthsHistory", patientCaseAccountMonthsHistory);
	                           model.addAttribute("patientCaseAccountYearsHistory", patientCaseAccountYearsHistory);
	                           model.addAttribute("searchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
	                           model.addAttribute("patientCasePatientCaseCreatedAt", formattedPatientCasePatientCaseCreatedAt);
	                           model.addAttribute("patientCasePatientCaseDayCreatedAt", formattedDayPatientCasePatientCaseCreatedAt);
	                           model.addAttribute("searchedPatientCaseDiagnosticReportCreatedAt", formattedPatientCaseDiagnosticReportCreatedAtDate);
	                           model.addAttribute("searchedPatientCaseDiagnosticReportDayCreatedAt", formattedDayPatientCaseDiagnosticReportCreatedAtDate);
	                           model.addAttribute("searchedCurrentMedicatingPatientMostRecentPastMedicalRecordDayCreatedAt", formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate);
	                           model.addAttribute("searchedCurrentMedicatingPatientMostRecentPastMedicalRecordCreatedAt",formattedCurrentMedicatingMedicaHistoryCreatedAtDate);
	                           //model.addAttribute("searchedPatientCaseDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	                           //model.addAttribute("searchedPatientCaseCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	                           searchUtil.addDateAttributesToModel(model, onePatientCase.getCreatedAt().toLocalDate(), "patientCaseAccountLength");
	                           searchUtil.addDateAttributesToModel(model, onePatientCase.getOnset(), "lengthOfPatientCondition");
	                       } else {
	                           // Add to the model without PastMedicalHistory details
	                           model.addAttribute("searchedPatientAge", searchedPatientAge);
	                           model.addAttribute("patientCasePatientAge", searchedPatientAge);
	                           model.addAttribute("searchedPatientCaseDiagnosticRecord", oneDiagnosticRecord);
	                           model.addAttribute("lengthOfPatientCaseConditionDays", conditionLengthDays);
	                           model.addAttribute("lengthOfPatientCaseConditionMonths", conditionLengthMonths);
	                           model.addAttribute("lengthOfPatientCaseConditionYears", conditionLengthYears);
	                           model.addAttribute("searchedPatientCaseDiagnosticRecord", oneDiagnosticRecord);
	                           model.addAttribute("patientCaseAccountDaysHistory", patientCaseAccountDaysHistory);
	                           model.addAttribute("patientCaseAccountMonthsHistory", patientCaseAccountMonthsHistory);
		                       model.addAttribute("patientCaseAccountYearsHistory", patientCaseAccountYearsHistory);
		                       model.addAttribute("searchedPatientCaseDiagnosticReportCreatedAt", formattedPatientCaseDiagnosticReportCreatedAtDate);
		                       model.addAttribute("searchedPatientCaseDiagnosticReportDayCreatedAt", formattedDayPatientCaseDiagnosticReportCreatedAtDate);
	                           model.addAttribute("searchedPatientCaseDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	                           model.addAttribute("searchedPatientCaseCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	                           searchUtil.addDateAttributesToModel(model, onePatientCase.getCreatedAt().toLocalDate(), "patientCaseAccountLength");
	                           searchUtil.addDateAttributesToModel(model, onePatientCase.getOnset(), "lengthOfPatientCasesCondition");
	                       }

	                       // Add PatientCase to the list
	                       patientCaseList.add(onePatientCase);
	                       patientCaseDiagnosticList.add(oneDiagnosticRecord);
	                   }
	               }
		               searchedPatientCaseLists.addAll(patientCaseList);
	           }
	       }

	           model.addAttribute("searchedPatientCasesList", searchedPatientCaseLists);
	       } else {
	           // No match found, set the flag and add an empty list to the model
	           model.addAttribute("isSingleMatch", false);
	           model.addAttribute("matchedPatientCaseCharacterList", Collections.emptyList());
	           model.addAttribute("searchedPatientCasesLists", Collections.emptyList());
	       }
	   } 

	   // Find Single PatientCase Diagnostic Report
	   public void searchSinglePatientCaseDiagnosticRecordByCharacter(Model model, String trimmedSearchTerm) {
	      // If a non-empty search value is provided
	      List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

	      if (!matchedPatients.isEmpty()) {
	          // Single or multiple matches found, set the flag and add to the model
	          model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
	          model.addAttribute("matchedSearchPatientCharacterList", matchedPatients);

	          // Populate PatientCase list for each patient
	          List<PatientCase> searchedPatientCaseLists = new ArrayList<>();
	          List<DiagnosticRecord> searchedDiagnosticReportLists = new ArrayList<>();
	          for (Patient patient : matchedPatients) {
	              // Check for null to avoid potential NullPointerException
	              if (patient.getPatientCases() != null && !patient.getPatientCases().isEmpty()) {
	                  // Only add the first patient case
	                  PatientCase onePatientCase = patient.getPatientCases().get(0);

	                  // Handle For Null PatientCase or Patient
	                  if (onePatientCase == null || onePatientCase.getPatient() == null) {
	                      continue;
	                  }

		               if (onePatientCase.getDiagnosticRecords() == null) {
		            	   continue;
		               }

		               DiagnosticRecord oneDiagnosticRecord = onePatientCase.getDiagnosticRecords().get(0);

	                  // Calculate date differences
	                  LocalDate createdAt = onePatientCase.getCreatedAt().toLocalDate();
	                  LocalDate onsetOfPatientCase = onePatientCase.getOnset();
	                  LocalDate searchedPatientBirthDay = onePatientCase.getPatient().getDateOfBirth();
	                  LocalDate patientCaseCreatedAt = onePatientCase.getCreatedAt().toLocalDate();
                      LocalDate diagnosticReportCreatedAt = oneDiagnosticRecord.getCreatedAt().toLocalDate();
	                  LocalDate patientCasePatientCaseCreatedAt = onePatientCase.getCreatedAt().toLocalDate();

	                  String formattedDayPatientCaseCreatedAtDate = patientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                  String formattedPatientCaseCreatedAtDate = patientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	                  String formattedDayPatientCasePatientCaseCreatedAt = patientCasePatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                  String formattedPatientCasePatientCaseCreatedAt = patientCasePatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
                      String formattedDayPatientCaseDiagnosticReportCreatedAtDate = diagnosticReportCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
                      String formattedPatientCaseDiagnosticReportCreatedAtDate = diagnosticReportCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	                  // Current Medication Account Histories
	                  long patientCaseAccountDaysHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.DAYS);
	                  long patientCaseAccountMonthsHistory= searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.MONTHS);
	                  long patientCaseAccountYearsHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.YEARS);

	                  // Patient Case Condition Histories
	                  long conditionLengthYears = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.YEARS);
	                  long conditionLengthDays = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.DAYS);
	                  long conditionLengthMonths = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.MONTHS);
	                  long searchedPatientAge = searchUtil.calculateDateDifference(searchedPatientBirthDay, LocalDate.now(), ChronoUnit.YEARS);

	                  filterUtil.addPhysicalAssessmentInfoToModel(model, patient.getId());

	                  // Use PatientFilterUtil to get the most recent PastMedicalHistory
	                  PastMedicalHistory mostRecentPastMedicalRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patient.getId());

	                  if (mostRecentPastMedicalRecord != null) {
	                      LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	                      LocalDate medicalConditionCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt();
	                      long searchedPatientLengthOfMedicalCondition = searchUtil.calculateDateDifference(medicalConditionStartDate, LocalDate.now(), ChronoUnit.YEARS);

	                      String formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                      String formattedCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	                      // Add to the model
	                      model.addAttribute("onePatientCasePatientAge", searchedPatientAge);
                          model.addAttribute("oneSearchedPatientCaseDiagnosticRecord", oneDiagnosticRecord);
	                      model.addAttribute("oneLengthOfPatientCaseConditionDays", conditionLengthDays);
	                      model.addAttribute("oneLengthOfPatientCaseConditionMonths", conditionLengthMonths);
	                      model.addAttribute("oneLengthOfPatientCaseConditionYears", conditionLengthYears);
	                      model.addAttribute("onePatientCaseCreatedAt", formattedPatientCaseCreatedAtDate);
	                      model.addAttribute("onePatientCaseDayCreatedAt", formattedDayPatientCaseCreatedAtDate);
	                      model.addAttribute("onePatientCaseAccountDaysHistory", patientCaseAccountDaysHistory);
	                      model.addAttribute("onePatientCaseAccountYearsHistory", patientCaseAccountYearsHistory);
	                      model.addAttribute("onePatientCaseAccountMonthsHistory", patientCaseAccountMonthsHistory);
	                      model.addAttribute("oneSearchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
	                      model.addAttribute("onePatientCasePatientCaseCreatedAt", formattedPatientCasePatientCaseCreatedAt);
	                      model.addAttribute("onePatientCasePatientCaseDayCreatedAt", formattedDayPatientCasePatientCaseCreatedAt);
	                      model.addAttribute("oneSearchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
                          model.addAttribute("oneSearchedPatientCaseDiagnosticReportCreatedAt", formattedPatientCaseDiagnosticReportCreatedAtDate);
                          model.addAttribute("oneSearchedPatientCaseDiagnosticReportDayCreatedAt", formattedDayPatientCaseDiagnosticReportCreatedAtDate);
	                      model.addAttribute("oneSearchedCurrentMedicatingPatientMostRecentPastMedicalRecordDayCreatedAt", formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate);
	                      model.addAttribute("oneSearchedCurrentMedicatingPatientMostRecentPastMedicalRecordCreatedAt",formattedCurrentMedicatingMedicaHistoryCreatedAtDate);
	                      //model.addAttribute("searchedPatientCaseDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	                      //model.addAttribute("searchedPatientCaseCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	                      searchUtil.addDateAttributesToModel(model, onePatientCase.getCreatedAt().toLocalDate(), "patientCaseAccountLength");
	                      searchUtil.addDateAttributesToModel(model, onePatientCase.getOnset(), "lengthOfPatientCondition");
	                  } else {
	                      // Add to the model without PastMedicalHistory details
	                      model.addAttribute("oneSearchedPatientAge", searchedPatientAge);
	                      model.addAttribute("onePatientCasePatientAge", searchedPatientAge);
                          model.addAttribute("oneSearchedPatientCaseDiagnosticRecord", oneDiagnosticRecord);
                          model.addAttribute("oneLengthOfPatientCaseConditionDays", conditionLengthDays);
	                      model.addAttribute("oneLengthOfPatientCaseConditionMonths", conditionLengthMonths);
	                      model.addAttribute("oneLengthOfPatientCaseConditionYears", conditionLengthYears);
	                      model.addAttribute("onePatientCaseAccountDaysHistory", patientCaseAccountDaysHistory);
	                      model.addAttribute("onePatientCaseAccountMonthsHistory", patientCaseAccountMonthsHistory);
	                      model.addAttribute("onePatientCaseAccountYearsHistory", patientCaseAccountYearsHistory);
                          model.addAttribute("oneSearchedPatientCaseDiagnosticReportCreatedAt", formattedPatientCaseDiagnosticReportCreatedAtDate);
                          model.addAttribute("oneSearchedPatientCaseDiagnosticReportDayCreatedAt", formattedDayPatientCaseDiagnosticReportCreatedAtDate);
	                      model.addAttribute("oneSearchedPatientCaseDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	                      model.addAttribute("oneSearchedPatientCaseCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	                      searchUtil.addDateAttributesToModel(model, onePatientCase.getCreatedAt().toLocalDate(), "patientCaseAccountLength");
	                      searchUtil.addDateAttributesToModel(model, onePatientCase.getOnset(), "lengthOfPatientCasesCondition");
	                  }

	                  searchedPatientCaseLists.add(onePatientCase);
	                  searchedDiagnosticReportLists.add(oneDiagnosticRecord);
	              }
	          }

	          model.addAttribute("oneSearchedSinglePatientCasesList", searchedPatientCaseLists);
	      } else {
	          // No match found, set the flag and add an empty list to the model
	          model.addAttribute("isSingleMatch", false);
	          model.addAttribute("matchedSinglePatientCaseCharacterList", Collections.emptyList());
	          model.addAttribute("searchedSinglePatientCasesLists", Collections.emptyList());
	      }
	  }

	   
		  // Combined Method To Search For All Patient Case CoagulationRecord Records
		   public void searchPatientCaseCoagulationRecordByCharacter(Model model, String trimmedSearchTerm) {
	       // If a non-empty search value is provided
	       List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

	       if (!matchedPatients.isEmpty()) {
	           // Single or multiple matches found, set the flag and add to the model
	           model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
	           model.addAttribute("matchedSearchPatientCharacterList", matchedPatients);

	           // Populate PatientCase list for each patient
	           List<PatientCase> searchedPatientCaseLists = new ArrayList<>();
	           for (Patient patient : matchedPatients) {
	               List<PatientCase> patientCaseList = new ArrayList<>();

	               // Check for null to avoid potential NullPointerException
	               if (patient.getPatientCases() != null) {
	                   for (PatientCase onePatientCase : patient.getPatientCases()) {
	                       // Handle null PatientCase or Patient
	                       if (onePatientCase == null || onePatientCase.getPatient() == null) {
	                           continue;
	                       }

			               if (onePatientCase.getCoagulationRecord() == null) {
			            	   continue;
			               }
	
			               for (CoagulationRecord oneCoagulationRecord : onePatientCase.getCoagulationRecord()) {
				           List<CoagulationRecord> patientCaseCoagulationRecordList = new ArrayList<>();

	                       // Calculate date differences
	                       LocalDate createdAt = onePatientCase.getCreatedAt().toLocalDate();
	                       LocalDate onsetOfPatientCase = onePatientCase.getOnset();
	                       LocalDate searchedPatientBirthDay = onePatientCase.getPatient().getDateOfBirth();
	                       LocalDate patientCaseCreatedAt = onePatientCase.getCreatedAt().toLocalDate();
	                       LocalDate coagulationRecordCreatedAt = oneCoagulationRecord.getCreatedAt().toLocalDate();
	                       LocalDate patientCasePatientCaseCreatedAt = onePatientCase.getCreatedAt().toLocalDate();

	                       String formattedDayPatientCaseCreatedAtDate = patientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                       String formattedPatientCaseCreatedAtDate = patientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	                       String formattedDayPatientCasePatientCaseCreatedAt = patientCasePatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                       String formattedPatientCasePatientCaseCreatedAt = patientCasePatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	                       String formattedDayPatientCaseCoagulationRecordCreatedAtDate = coagulationRecordCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                       String formattedPatientCaseCoagulationRecordCreatedAtDate = coagulationRecordCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	                       // Current Medication Account Histories
	                       long patientCaseAccountDaysHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.DAYS);
	                       long patientCaseAccountMonthsHistory= searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.MONTHS);
	                       long patientCaseAccountYearsHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.YEARS);

	                       // Patient Case Condition Histories
	                       long conditionLengthYears = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.YEARS);
	                       long conditionLengthDays = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.DAYS);
	                       long conditionLengthMonths = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.MONTHS);
	                       long searchedPatientAge = searchUtil.calculateDateDifference(searchedPatientBirthDay, LocalDate.now(), ChronoUnit.YEARS);

	                       filterUtil.addPhysicalAssessmentInfoToModel(model, patient.getId());

	                       // Use PatientFilterUtil to get the most recent PastMedicalHistory
	                       PastMedicalHistory mostRecentPastMedicalRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patient.getId());
	                       if (mostRecentPastMedicalRecord != null) {
	                           LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	                           LocalDate medicalConditionCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt();
	                           long searchedPatientLengthOfMedicalCondition = searchUtil.calculateDateDifference(medicalConditionStartDate, LocalDate.now(), ChronoUnit.YEARS);

	                           String formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                           String formattedCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	                           // Add to the model
	                           model.addAttribute("patientCasePatientAge", searchedPatientAge);
	                           model.addAttribute("searchedPatientCaseCoagulationRecord", oneCoagulationRecord);
	                           model.addAttribute("lengthOfPatientCaseConditionDays", conditionLengthDays);
	                           model.addAttribute("lengthOfPatientCaseConditionMonths", conditionLengthMonths);
	                           model.addAttribute("lengthOfPatientCaseConditionYears", conditionLengthYears);
	                           model.addAttribute("searchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
	                           model.addAttribute("patientCaseCreatedAt", formattedPatientCaseCreatedAtDate);
	                           model.addAttribute("patientCaseDayCreatedAt", formattedDayPatientCaseCreatedAtDate);
	                           model.addAttribute("patientCaseAccountDaysHistory", patientCaseAccountDaysHistory);
	                           model.addAttribute("patientCaseAccountMonthsHistory", patientCaseAccountMonthsHistory);
	                           model.addAttribute("patientCaseAccountYearsHistory", patientCaseAccountYearsHistory);
	                           model.addAttribute("searchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
	                           model.addAttribute("patientCasePatientCaseCreatedAt", formattedPatientCasePatientCaseCreatedAt);
	                           model.addAttribute("patientCasePatientCaseDayCreatedAt", formattedDayPatientCasePatientCaseCreatedAt);
	                           model.addAttribute("searchedPatientCaseCoagulationRecordCreatedAt", formattedPatientCaseCoagulationRecordCreatedAtDate);
	                           model.addAttribute("searchedPatientCaseCoagulationRecordDayCreatedAt", formattedDayPatientCaseCoagulationRecordCreatedAtDate);
	                           model.addAttribute("searchedCurrentMedicatingPatientMostRecentPastMedicalRecordDayCreatedAt", formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate);
	                           model.addAttribute("searchedCurrentMedicatingPatientMostRecentPastMedicalRecordCreatedAt",formattedCurrentMedicatingMedicaHistoryCreatedAtDate);
	                           //model.addAttribute("searchedPatientCaseDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	                           //model.addAttribute("searchedPatientCaseCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	                           searchUtil.addDateAttributesToModel(model, onePatientCase.getCreatedAt().toLocalDate(), "patientCaseAccountLength");
	                           searchUtil.addDateAttributesToModel(model, onePatientCase.getOnset(), "lengthOfPatientCondition");
	                       } else {
	                           // Add to the model without PastMedicalHistory details
	                           model.addAttribute("searchedPatientAge", searchedPatientAge);
	                           model.addAttribute("patientCasePatientAge", searchedPatientAge);
	                           model.addAttribute("searchedPatientCaseCoagulationRecord", oneCoagulationRecord);
	                           model.addAttribute("lengthOfPatientCaseConditionDays", conditionLengthDays);
	                           model.addAttribute("lengthOfPatientCaseConditionMonths", conditionLengthMonths);
	                           model.addAttribute("lengthOfPatientCaseConditionYears", conditionLengthYears);
	                           model.addAttribute("searchedPatientCaseCoagulationRecord", oneCoagulationRecord);
	                           model.addAttribute("patientCaseAccountDaysHistory", patientCaseAccountDaysHistory);
	                           model.addAttribute("patientCaseAccountMonthsHistory", patientCaseAccountMonthsHistory);
		                       model.addAttribute("patientCaseAccountYearsHistory", patientCaseAccountYearsHistory);
		                       model.addAttribute("searchedPatientCaseCoagulationRecordCreatedAt", formattedPatientCaseCoagulationRecordCreatedAtDate);
		                       model.addAttribute("searchedPatientCaseCoagulationRecordDayCreatedAt", formattedDayPatientCaseCoagulationRecordCreatedAtDate);
	                           model.addAttribute("searchedPatientCaseDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	                           model.addAttribute("searchedPatientCaseCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	                           searchUtil.addDateAttributesToModel(model, onePatientCase.getCreatedAt().toLocalDate(), "patientCaseAccountLength");
	                           searchUtil.addDateAttributesToModel(model, onePatientCase.getOnset(), "lengthOfPatientCasesCondition");
	                       }
	                       model.addAttribute("searchedPatientCaseCoagulationList", patientCaseCoagulationRecordList);

	                       // Add PatientCase to the list
	                       patientCaseList.add(onePatientCase);
	                       patientCaseCoagulationRecordList.add(oneCoagulationRecord);
	                   }
	               }
		           searchedPatientCaseLists.addAll(patientCaseList);
	           }
	       }

	           model.addAttribute("searchedPatientCasesList", searchedPatientCaseLists);
	       } else {
	           // No match found, set the flag and add an empty list to the model
	           model.addAttribute("isSingleMatch", false);
	           model.addAttribute("matchedPatientCaseCharacterList", Collections.emptyList());
	           model.addAttribute("searchedPatientCasesLists", Collections.emptyList());
	       }
	   } 

	   // Find Single PatientCase CoagulationRecord Report
	   public void searchSinglePatientCaseCoagulationRecordByCharacter(Model model, String trimmedSearchTerm) {
	      // If a non-empty search value is provided
	      List<Patient> matchedPatients = patientServ.searchPatientsByCharacters(trimmedSearchTerm.toLowerCase());

	      if (!matchedPatients.isEmpty()) {
	          // Single or multiple matches found, set the flag and add to the model
	          model.addAttribute("isSingleMatch", matchedPatients.size() == 1);
	          model.addAttribute("matchedSearchPatientCharacterList", matchedPatients);

	          // Populate PatientCase list for each patient
	          List<PatientCase> searchedPatientCaseLists = new ArrayList<>();
	          List<CoagulationRecord> searchedCoagulationRecordLists = new ArrayList<>();
	          for (Patient patient : matchedPatients) {
	              // Check for null to avoid potential NullPointerException
	              if (patient.getPatientCases() != null && !patient.getPatientCases().isEmpty()) {
	                  // Only add the first patient case
	                  PatientCase onePatientCase = patient.getPatientCases().get(0);

	                  // Handle For Null PatientCase or Patient
	                  if (onePatientCase == null || onePatientCase.getPatient() == null) {
	                      continue;
	                  }

		               if (onePatientCase.getCoagulationRecord() == null) {
		            	   continue;
		               }

		               CoagulationRecord oneCoagulationRecord = onePatientCase.getCoagulationRecord().get(0);

	                  // Calculate date differences
	                  LocalDate createdAt = onePatientCase.getCreatedAt().toLocalDate();
	                  LocalDate onsetOfPatientCase = onePatientCase.getOnset();
	                  LocalDate searchedPatientBirthDay = onePatientCase.getPatient().getDateOfBirth();
	                  LocalDate patientCaseCreatedAt = onePatientCase.getCreatedAt().toLocalDate();
                   LocalDate coagulationRecordCreatedAt = oneCoagulationRecord.getCreatedAt().toLocalDate();
	                  LocalDate patientCasePatientCaseCreatedAt = onePatientCase.getCreatedAt().toLocalDate();

	                  String formattedDayPatientCaseCreatedAtDate = patientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                  String formattedPatientCaseCreatedAtDate = patientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
	                  String formattedDayPatientCasePatientCaseCreatedAt = patientCasePatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                  String formattedPatientCasePatientCaseCreatedAt = patientCasePatientCaseCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
                   String formattedDayPatientCaseCoagulationRecordCreatedAtDate = coagulationRecordCreatedAt.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
                   String formattedPatientCaseCoagulationRecordCreatedAtDate = coagulationRecordCreatedAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	                  // Current Medication Account Histories
	                  long patientCaseAccountDaysHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.DAYS);
	                  long patientCaseAccountMonthsHistory= searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.MONTHS);
	                  long patientCaseAccountYearsHistory = searchUtil.calculateDateDifference(createdAt, LocalDate.now(), ChronoUnit.YEARS);

	                  // Patient Case Condition Histories
	                  long conditionLengthYears = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.YEARS);
	                  long conditionLengthDays = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.DAYS);
	                  long conditionLengthMonths = searchUtil.calculateDateDifference(onsetOfPatientCase, LocalDate.now(), ChronoUnit.MONTHS);
	                  long searchedPatientAge = searchUtil.calculateDateDifference(searchedPatientBirthDay, LocalDate.now(), ChronoUnit.YEARS);

	                  filterUtil.addPhysicalAssessmentInfoToModel(model, patient.getId());

	                  // Use PatientFilterUtil to get the most recent PastMedicalHistory
	                  PastMedicalHistory mostRecentPastMedicalRecord = filterUtil.sortMostRecentPastMedicalRecord(model, patient.getId());

	                  if (mostRecentPastMedicalRecord != null) {
	                      LocalDate medicalConditionStartDate = mostRecentPastMedicalRecord.getStartDate();
	                      LocalDate medicalConditionCreatedAtDate = mostRecentPastMedicalRecord.getCreatedAt();
	                      long searchedPatientLengthOfMedicalCondition = searchUtil.calculateDateDifference(medicalConditionStartDate, LocalDate.now(), ChronoUnit.YEARS);

	                      String formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, yyyy"));
	                      String formattedCurrentMedicatingMedicaHistoryCreatedAtDate = medicalConditionCreatedAtDate.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));

	                      // Add to the model
	                      model.addAttribute("onePatientCasePatientAge", searchedPatientAge);
                       model.addAttribute("oneSearchedPatientCaseCoagulationRecord", oneCoagulationRecord);
	                      model.addAttribute("oneLengthOfPatientCaseConditionDays", conditionLengthDays);
	                      model.addAttribute("oneLengthOfPatientCaseConditionMonths", conditionLengthMonths);
	                      model.addAttribute("oneLengthOfPatientCaseConditionYears", conditionLengthYears);
	                      model.addAttribute("onePatientCaseCreatedAt", formattedPatientCaseCreatedAtDate);
	                      model.addAttribute("onePatientCaseDayCreatedAt", formattedDayPatientCaseCreatedAtDate);
	                      model.addAttribute("onePatientCaseAccountDaysHistory", patientCaseAccountDaysHistory);
	                      model.addAttribute("onePatientCaseAccountYearsHistory", patientCaseAccountYearsHistory);
	                      model.addAttribute("onePatientCaseAccountMonthsHistory", patientCaseAccountMonthsHistory);
	                      model.addAttribute("oneSearchedMostRecentPastMedicalRecord", mostRecentPastMedicalRecord);
	                      model.addAttribute("onePatientCasePatientCaseCreatedAt", formattedPatientCasePatientCaseCreatedAt);
	                      model.addAttribute("onePatientCasePatientCaseDayCreatedAt", formattedDayPatientCasePatientCaseCreatedAt);
	                      model.addAttribute("oneSearchedPatientLengthOfMedicalCondition", searchedPatientLengthOfMedicalCondition);
                       model.addAttribute("oneSearchedPatientCaseCoagulationRecordCreatedAt", formattedPatientCaseCoagulationRecordCreatedAtDate);
                       model.addAttribute("oneSearchedPatientCaseCoagulationRecordDayCreatedAt", formattedDayPatientCaseCoagulationRecordCreatedAtDate);
	                      model.addAttribute("oneSearchedCurrentMedicatingPatientMostRecentPastMedicalRecordDayCreatedAt", formattedDayCurrentMedicatingMedicaHistoryCreatedAtDate);
	                      model.addAttribute("oneSearchedCurrentMedicatingPatientMostRecentPastMedicalRecordCreatedAt",formattedCurrentMedicatingMedicaHistoryCreatedAtDate);
	                      //model.addAttribute("searchedPatientCaseDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	                      //model.addAttribute("searchedPatientCaseCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	                      searchUtil.addDateAttributesToModel(model, onePatientCase.getCreatedAt().toLocalDate(), "patientCaseAccountLength");
	                      searchUtil.addDateAttributesToModel(model, onePatientCase.getOnset(), "lengthOfPatientCondition");
	                  } else {
	                      // Add to the model without PastMedicalHistory details
	                      model.addAttribute("oneSearchedPatientAge", searchedPatientAge);
	                      model.addAttribute("onePatientCasePatientAge", searchedPatientAge);
                       model.addAttribute("oneSearchedPatientCaseCoagulationRecord", oneCoagulationRecord);
                       model.addAttribute("oneLengthOfPatientCaseConditionDays", conditionLengthDays);
	                      model.addAttribute("oneLengthOfPatientCaseConditionMonths", conditionLengthMonths);
	                      model.addAttribute("oneLengthOfPatientCaseConditionYears", conditionLengthYears);
	                      model.addAttribute("onePatientCaseAccountDaysHistory", patientCaseAccountDaysHistory);
	                      model.addAttribute("onePatientCaseAccountMonthsHistory", patientCaseAccountMonthsHistory);
	                      model.addAttribute("onePatientCaseAccountYearsHistory", patientCaseAccountYearsHistory);
                       model.addAttribute("oneSearchedPatientCaseCoagulationRecordCreatedAt", formattedPatientCaseCoagulationRecordCreatedAtDate);
                       model.addAttribute("oneSearchedPatientCaseCoagulationRecordDayCreatedAt", formattedDayPatientCaseCoagulationRecordCreatedAtDate);
	                      model.addAttribute("oneSearchedPatientCaseDayCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, yyyy")));
	                      model.addAttribute("oneSearchedPatientCaseCreatedAt", createdAt.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")));
	                      searchUtil.addDateAttributesToModel(model, onePatientCase.getCreatedAt().toLocalDate(), "patientCaseAccountLength");
	                      searchUtil.addDateAttributesToModel(model, onePatientCase.getOnset(), "lengthOfPatientCasesCondition");
	                  }

	                  searchedPatientCaseLists.add(onePatientCase);
	                  searchedCoagulationRecordLists.add(oneCoagulationRecord);
	              }
	          }

	          model.addAttribute("oneSearchedSinglePatientCasesList", searchedPatientCaseLists);
	      } else {
	          // No match found, set the flag and add an empty list to the model
	          model.addAttribute("isSingleMatch", false);
	          model.addAttribute("matchedSinglePatientCaseCharacterList", Collections.emptyList());
	          model.addAttribute("searchedSinglePatientCasesLists", Collections.emptyList());
	      }
	  }
}
