package com.turuchie.mellowhealthportal.models.ClinicalOperations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.DiagnosticRecord;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.FollowUpRecord;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PainAssessment;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PhysicalAssessment;
import com.turuchie.mellowhealthportal.models.PatientOperations.AdverseEffect;
import com.turuchie.mellowhealthportal.models.PatientOperations.CurrentMedication;
import com.turuchie.mellowhealthportal.models.PatientOperations.IncidentReport;
import com.turuchie.mellowhealthportal.models.PatientOperations.InsuranceInformation;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.models.PatientOperations.PediatricHistory;
import com.turuchie.mellowhealthportal.models.PatientOperations.SexualHistory;
import com.turuchie.mellowhealthportal.models.PatientOperations.SocialHistory;
import com.turuchie.mellowhealthportal.models.Physicians.Physician;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "patient_cases")
public class PatientCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please Enter Patients Name!")
    private String patientName;

    @NotBlank(message = "Chief Complaint Cannot Be Blank!")
    private String chiefComplaint;

    @NotBlank(message = "Please Describe Your Current Symptoms!")
    @Size(min = 3, max = 500, message = "Subjective Symptoms Must Be Between 3 and 500 Characters!")
    private String subjectiveSymptoms;

    @NotNull(message = "Please Enter When You Observed This Condition!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate onset;

    @NotBlank(message = "Please Enter Any Known Drug Allergy!")
    @Size(min = 3, max = 500, message = "Drug Allergies Must Be Between 3 and 500 Characters!")
    private String drugAllergies;

    @NotNull(message = "Please Select Treatment Status!")
    private Boolean currentlyMedicating;

    @NotBlank(message = "Patients Medications must be between 3 and 500 characters!")
    @Size(min = 3, max = 500, message = "Please provide current medication for this condion, or enter null if unavailable!")
    private String patientMedication;

    @NotBlank(message = "Please Enter Past Medical History!")
    @Size(min = 3, max = 500, message = "Past Medical History Must Be Between 3 and 500 characters, Enter Null if unavailable!")
    private String patientPastMedicalHistory;

    @NotBlank(message = "Please Enter Past Surgical History!")
    @Size(min = 3, max = 500, message = "Invalid Past Medical History, Enter Null If Unavailable!")
    private String patientPastSurgicalHistory;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "insuranceInformation_id")
    @NotNull(message = "Please Enter Valid Insurance Information!")
    private InsuranceInformation insuranceInformation;

    @ManyToOne
    @JoinColumn(name = "physician_id")
    private Physician physician;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<FollowUpRecord> followUpRecords;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<PhysicalAssessment> physicalAssessments;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<PainAssessment> painAssessments;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<DiagnosticRecord> diagnosticRecords;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<AdverseEffect> adverseEffects;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<BioequivalenceAnalysis> bioequivalenceAnalysis;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<CurrentMedication> currentMedications;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<DoseRegimen> doseRegimenRecords;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<DrugAlcoholCotinineScreenRecord> drugAlcoholCotinineScreenRecords;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<EcgParameters> ecgParameters;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<FoodRecord> foodRecords;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<HematologyRecord> hematologyRecords;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<HepatitisScreenRecord> hepatitisScreenRecords;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<IncidentReport> incidentReports;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<InclusionCriteria> inclusionCriteria;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<ExclusionCriteria> exclusionCriteria;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<LipidPanel> lipidPanels;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<PatientVitalRecord> patientVitalRecords;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<PatientClinicalChemistry> patientClinicalChemistryRecords;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<PregnancyFertilityAssessment> pregnancyFertilityAssesments;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<PharmacoKineticParameter> pharmacoKineticParameters;
 
    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<ThyroidFunction> thyroidFunctions;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<UrineAnalysis> urineAnalysis;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<SocialHistory> socialHistories;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<SexualHistory> sexualHistories;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<PediatricHistory> pediatricHistories;
    

    public PatientCase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getChiefComplaint() {
		return chiefComplaint;
	}

	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}

	public String getPatientMedication() {
		return patientMedication;
	}
	public String getDrugAllergies() {
		return drugAllergies;
	}

	public void setDrugAllergies(String drugAllergies) {
		this.drugAllergies = drugAllergies;
	}

	public Boolean getCurrentlyMedicating() {
		return currentlyMedicating;
	}

	public void setCurrentlyMedicating(Boolean currentlyMedicating) {
		this.currentlyMedicating = currentlyMedicating;
	}

	public LocalDate getOnset() {
		return onset;
	}

	public void setOnset(LocalDate onset) {
		this.onset = onset;
	}

	public String getSubjectiveSymptoms() {
		return subjectiveSymptoms;
	}

	public void setSubjectiveSymptoms(String subjectiveSymptoms) {
		this.subjectiveSymptoms = subjectiveSymptoms;
	}

	public List<PainAssessment> getPainAssessments() {
		return painAssessments;
	}

	public void setPainAssessments(List<PainAssessment> painAssessments) {
		this.painAssessments = painAssessments;
	}

	public List<DiagnosticRecord> getDiagnosticRecords() {
		return diagnosticRecords;
	}

	public void setDiagnosticRecords(List<DiagnosticRecord> diagnosticRecords) {
		this.diagnosticRecords = diagnosticRecords;
	}

	public void setPatientMedication(String patientMedication) {
		this.patientMedication = patientMedication;
	}

	public String getPatientPastMedicalHistory() {
		return patientPastMedicalHistory;
	}

	public void setPatientPastMedicalHistory(String patientPastMedicalHistory) {
		this.patientPastMedicalHistory = patientPastMedicalHistory;
	}

	public String getPatientPastSurgicalHistory() {
		return patientPastSurgicalHistory;
	}

	public void setPatientPastSurgicalHistory(String patientPastSurgicalHistory) {
		this.patientPastSurgicalHistory = patientPastSurgicalHistory;
	}

	public List<PhysicalAssessment> getPhysicalAssessments() {
		return physicalAssessments;
	}

	public void setPhysicalAssessments(List<PhysicalAssessment> physicalAssessments) {
		this.physicalAssessments = physicalAssessments;
	}

	public List<FollowUpRecord> getFollowUpRecords() {
		return followUpRecords;
	}

	public void setFollowUpRecords(List<FollowUpRecord> followUpRecords) {
		this.followUpRecords = followUpRecords;
	}

	public List<AdverseEffect> getAdverseEffects() {
		return adverseEffects;
	}

	public void setAdverseEffects(List<AdverseEffect> adverseEffects) {
		this.adverseEffects = adverseEffects;
	}

	public List<BioequivalenceAnalysis> getBioequivalenceAnalysis() {
		return bioequivalenceAnalysis;
	}

	public void setBioequivalenceAnalysis(List<BioequivalenceAnalysis> bioequivalenceAnalysis) {
		this.bioequivalenceAnalysis = bioequivalenceAnalysis;
	}

	public List<CurrentMedication> getCurrentMedications() {
		return currentMedications;
	}

	public void setCurrentMedications(List<CurrentMedication> currentMedications) {
		this.currentMedications = currentMedications;
	}

	public List<DoseRegimen> getDoseRegimenRecords() {
		return doseRegimenRecords;
	}

	public void setDoseRegimenRecords(List<DoseRegimen> doseRegimenRecords) {
		this.doseRegimenRecords = doseRegimenRecords;
	}

	public List<DrugAlcoholCotinineScreenRecord> getDrugAlcoholCotinineScreenRecords() {
		return drugAlcoholCotinineScreenRecords;
	}

	public void setDrugAlcoholCotinineScreenRecords(
			List<DrugAlcoholCotinineScreenRecord> drugAlcoholCotinineScreenRecords) {
		this.drugAlcoholCotinineScreenRecords = drugAlcoholCotinineScreenRecords;
	}

	public List<EcgParameters> getEcgParameters() {
		return ecgParameters;
	}

	public void setEcgParameters(List<EcgParameters> ecgParameters) {
		this.ecgParameters = ecgParameters;
	}

	public List<FoodRecord> getFoodRecords() {
		return foodRecords;
	}

	public void setFoodRecords(List<FoodRecord> foodRecords) {
		this.foodRecords = foodRecords;
	}

	public List<HematologyRecord> getHematologyRecords() {
		return hematologyRecords;
	}

	public void setHematologyRecords(List<HematologyRecord> hematologyRecords) {
		this.hematologyRecords = hematologyRecords;
	}

	public List<HepatitisScreenRecord> getHepatitisScreenRecords() {
		return hepatitisScreenRecords;
	}

	public void setHepatitisScreenRecords(List<HepatitisScreenRecord> hepatitisScreenRecords) {
		this.hepatitisScreenRecords = hepatitisScreenRecords;
	}

	public List<IncidentReport> getIncidentReports() {
		return incidentReports;
	}

	public void setIncidentReports(List<IncidentReport> incidentReports) {
		this.incidentReports = incidentReports;
	}

	public List<InclusionCriteria> getInclusionCriteria() {
		return inclusionCriteria;
	}

	public void setInclusionCriteria(List<InclusionCriteria> inclusionCriteria) {
		this.inclusionCriteria = inclusionCriteria;
	}

	public List<ExclusionCriteria> getExclusionCriteria() {
		return exclusionCriteria;
	}

	public void setExclusionCriteria(List<ExclusionCriteria> exclusionCriteria) {
		this.exclusionCriteria = exclusionCriteria;
	}

	public List<LipidPanel> getLipidPanels() {
		return lipidPanels;
	}

	public void setLipidPanels(List<LipidPanel> lipidPanels) {
		this.lipidPanels = lipidPanels;
	}

	public List<PatientVitalRecord> getPatientVitalRecords() {
		return patientVitalRecords;
	}

	public void setPatientVitalRecords(List<PatientVitalRecord> patientVitalRecords) {
		this.patientVitalRecords = patientVitalRecords;
	}

	public List<PatientClinicalChemistry> getPatientClinicalChemistryRecords() {
		return patientClinicalChemistryRecords;
	}

	public void setPatientClinicalChemistryRecords(List<PatientClinicalChemistry> patientClinicalChemistryRecords) {
		this.patientClinicalChemistryRecords = patientClinicalChemistryRecords;
	}

	public List<PregnancyFertilityAssessment> getPregnancyFertilityAssesments() {
		return pregnancyFertilityAssesments;
	}

	public void setPregnancyFertilityAssesments(List<PregnancyFertilityAssessment> pregnancyFertilityAssesments) {
		this.pregnancyFertilityAssesments = pregnancyFertilityAssesments;
	}

	public List<PharmacoKineticParameter> getPharmacoKineticParameters() {
		return pharmacoKineticParameters;
	}

	public void setPharmacoKineticParameters(List<PharmacoKineticParameter> pharmacoKineticParameters) {
		this.pharmacoKineticParameters = pharmacoKineticParameters;
	}

	public List<ThyroidFunction> getThyroidFunctions() {
		return thyroidFunctions;
	}

	public void setThyroidFunctions(List<ThyroidFunction> thyroidFunctions) {
		this.thyroidFunctions = thyroidFunctions;
	}

	public List<UrineAnalysis> getUrineAnalysis() {
		return urineAnalysis;
	}

	public void setUrineAnalysis(List<UrineAnalysis> urineAnalysis) {
		this.urineAnalysis = urineAnalysis;
	}

	public List<SocialHistory> getSocialHistories() {
		return socialHistories;
	}

	public void setSocialHistories(List<SocialHistory> socialHistories) {
		this.socialHistories = socialHistories;
	}

	public List<SexualHistory> getSexualHistories() {
		return sexualHistories;
	}

	public void setSexualHistories(List<SexualHistory> sexualHistories) {
		this.sexualHistories = sexualHistories;
	}

	public List<PediatricHistory> getPediatricHistories() {
		return pediatricHistories;
	}

	public void setPediatricHistories(List<PediatricHistory> pediatricHistories) {
		this.pediatricHistories = pediatricHistories;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Physician getPhysician() {
		return physician;
	}

	public void setPhysician(Physician physician) {
		this.physician = physician;
	}

	public InsuranceInformation getInsuranceInformation() {
		return insuranceInformation;
	}

	public void setInsuranceInformation(InsuranceInformation insuranceInformation) {
		this.insuranceInformation = insuranceInformation;
	}

	public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

