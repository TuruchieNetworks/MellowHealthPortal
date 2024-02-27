package com.turuchie.mellowhealthportal.models.PatientOperations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.turuchie.mellowhealthportal.models.Administrations.InsuredPatients;
import com.turuchie.mellowhealthportal.models.Administrations.PhysiciansPatient;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.CoagulationRecord;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.DoseRegimen;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.DrugAlcoholCotinineScreenRecord;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.ExclusionCriteria;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.FoodRecord;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.HematologyRecord;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.HepatitisScreenRecord;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.InclusionCriteria;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.LipidPanel;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientClinicalChemistry;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientVitalRecord;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PharmacoKineticParameter;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PregnancyFertilityAssessment;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.ThyroidFunction;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.UrineAnalysis;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.DiagnosticRecord;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.FollowUpRecord;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PainAssessment;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PhysicalAssessment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "patients")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;   

	@NotBlank(message="Patients First Name Is Required!")
	@Size(min=2, max= 150, message="Patient First Name Must Be Between 2 and 150 characters!")
	private String patientFirstName;

	@NotBlank(message="Patients Last Name Is Required!")
	@Size(min=2, max= 150, message="Patient Last Name Must Be Between 2 and 150 Characters!")
	private String patientLastName;
    
    @NotEmpty(message = "Email Is Required!")
    @Email(message = "Please Enter A Valid Email!")
    private String email;

    @NotEmpty(message = "Password Is Required!")
    @Size(min = 8, max = 80, message = "Password Must Be Between 8 and 80 Characters")
    private String password;

    @Transient
    @NotEmpty(message = "Confirm Passwords Must Match!")
    @Size(min = 8, max = 80, message = "Confirm Passwords Must Be Between 8 and 80 Characters!")
    private String confirmPassword;

    @NotNull(message = "Please Your Date Of Birth!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Use this annotation for binding LocalDate
    private LocalDate dateOfBirth;

    @NotBlank(message = "Please Select Gender!")
    @Size(min = 3, max = 20, message = "Gender Is Required!")
    private String gender;
 
    @NotBlank(message = "Please Choose Ethnicity!")
    @Size(min = 3, max = 20, message = "Invalid Race!")
    private String race;
    

    @NotBlank(message = "Please Enter Any Recently Used Recreational Substance!")
    @Size(min = 3, max = 50, message = "Invalid Entry, Enter Null If Unknown!")
    private String recreationalSubstance;

    @NotNull(message = "Please Enter Travel History!")
    private Boolean hasTravelledOutsideTheUnitedStatesForMoreThan30Days;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PatientCase> patientCases;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<FollowUpRecord> followUpRecords;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PhysicalAssessment> physicalAssessments;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PainAssessment> painAssessments;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<DiagnosticRecord> diagnosticRecords;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<CoagulationRecord> coagulationRecords;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<HematologyRecord> hematologyRecords;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<HepatitisScreenRecord> hepatitisScreenRecords;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<LipidPanel> lipidPanels;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PastMedicalHistory> pastMedicalHistories;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PastSurgicalHistory> pastSurgicalHistories;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PatientVitalRecord> patientVitalRecords;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PatientClinicalChemistry> patientClinicalChemistryRecords;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<DrugAlcoholCotinineScreenRecord> drugAlcoholCotinineScreenRecords;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PregnancyFertilityAssessment> pregnancyFertilityAssesments;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<RecentAdmission> recentAdmissions;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<RecentEmergency> recentEmergencies;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<ThyroidFunction> thyroidFunctions;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<UrineAnalysis> urineAnalysis;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<InclusionCriteria> inclusionCriteria;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<ExclusionCriteria> exclusionCriteria;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<FoodRecord> foodRecords;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<DoseRegimen> doseRegimenRecords;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<CurrentMedication> currentMedications;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<AdverseEffect> adverseEffects;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PhysiciansPatient> physiciansPatients;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PatientAddresses> patientAddresses;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<IncidentReport> incidentReports;
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<InsuranceInformation> insuranceRecords;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<InsuredPatients> insuredPatients;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PharmacoKineticParameter> pharmacoKineticParameters;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<SocialHistory> socialHistories;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<SexualHistory> sexualHistories;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PediatricHistory> pediatricHistories;
    
	public Patient() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }
    
    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getRecreationalSubstance() {
		return recreationalSubstance;
	}

	public void setRecreationalSubstance(String recreationalSubstance) {
		this.recreationalSubstance = recreationalSubstance;
	}

	public Boolean getHasTravelledOutsideTheUnitedStatesForMoreThan30Days() {
		return hasTravelledOutsideTheUnitedStatesForMoreThan30Days;
	}

	public void setHasTravelledOutsideTheUnitedStatesForMoreThan30Days(
			Boolean hasTravelledOutsideTheUnitedStatesForMoreThan30Days) {
		this.hasTravelledOutsideTheUnitedStatesForMoreThan30Days = hasTravelledOutsideTheUnitedStatesForMoreThan30Days;
	}
	
	public List<PatientCase> getPatientCases() {
		return patientCases;
	}

	public void setPatientCases(List<PatientCase> patientCases) {
		this.patientCases = patientCases;
	}

	public List<InsuredPatients> getInsuredPatients() {
		return insuredPatients;
	}

	public void setInsuredPatients(List<InsuredPatients> insuredPatients) {
		this.insuredPatients = insuredPatients;
	}

	public List<PhysiciansPatient> getPhysiciansPatients() {
		return physiciansPatients;
	}

	public void setPhysiciansPatients(List<PhysiciansPatient> physiciansPatients) {
		this.physiciansPatients = physiciansPatients;
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

	public List<FollowUpRecord> getFollowUpRecords() {
		return followUpRecords;
	}

	public void setFollowUpRecords(List<FollowUpRecord> followUpRecords) {
		this.followUpRecords = followUpRecords;
	}

	public List<PhysicalAssessment> getPhysicalAssessments() {
		return physicalAssessments;
	}

	public void setPhysicalAssessments(List<PhysicalAssessment> physicalAssessments) {
		this.physicalAssessments = physicalAssessments;
	}

	public List<IncidentReport> getIncidentReports() {
		return incidentReports;
	}

	public void setIncidentReports(List<IncidentReport> incidentReports) {
		this.incidentReports = incidentReports;
	}
	
	public List<PatientAddresses> getPatientAddresses() {
		return patientAddresses;
	}

	public void setPatientAddresses(List<PatientAddresses> patientAddresses) {
		this.patientAddresses = patientAddresses;
	}

	public List<CoagulationRecord> getCoagulationRecords() {
		return coagulationRecords;
	}

	public void setCoagulationRecords(List<CoagulationRecord> coagulationRecords) {
		this.coagulationRecords = coagulationRecords;
	}

	public List<DrugAlcoholCotinineScreenRecord> getDrugAlcoholCotinineScreenRecords() {
		return drugAlcoholCotinineScreenRecords;
	}

	public void setDrugAlcoholCotinineScreenRecords(
			List<DrugAlcoholCotinineScreenRecord> drugAlcoholCotinineScreenRecords) {
		this.drugAlcoholCotinineScreenRecords = drugAlcoholCotinineScreenRecords;
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

	public List<LipidPanel> getLipidPanels() {
		return lipidPanels;
	}

	public void setLipidPanels(List<LipidPanel> lipidPanels) {
		this.lipidPanels = lipidPanels;
	}

	public List<PastMedicalHistory> getPastMedicalHistories() {
		return pastMedicalHistories;
	}

	public void setPastMedicalHistories(List<PastMedicalHistory> pastMedicalHistories) {
		this.pastMedicalHistories = pastMedicalHistories;
	}

	public List<PastSurgicalHistory> getPastSurgicalHistories() {
		return pastSurgicalHistories;
	}

	public void setPastSurgicalHistories(List<PastSurgicalHistory> pastSurgicalHistories) {
		this.pastSurgicalHistories = pastSurgicalHistories;
	}

	public List<PatientClinicalChemistry> getPatientClinicalChemistryRecords() {
		return patientClinicalChemistryRecords;
	}

	public void setPatientClinicalChemistryRecords(List<PatientClinicalChemistry> patientClinicalChemistryRecords) {
		this.patientClinicalChemistryRecords = patientClinicalChemistryRecords;
	}

	public List<PatientVitalRecord> getPatientVitalRecords() {
		return patientVitalRecords;
	}

	public void setPatientVitalRecords(List<PatientVitalRecord> patientVitalRecords) {
		this.patientVitalRecords = patientVitalRecords;
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

	public List<RecentAdmission> getRecentAdmissions() {
		return recentAdmissions;
	}

	public void setRecentAdmissions(List<RecentAdmission> recentAdmissions) {
		this.recentAdmissions = recentAdmissions;
	}

	public List<RecentEmergency> getRecentEmergencies() {
		return recentEmergencies;
	}

	public void setRecentEmergencies(List<RecentEmergency> recentEmergencies) {
		this.recentEmergencies = recentEmergencies;
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

	public List<FoodRecord> getFoodRecords() {
		return foodRecords;
	}

	public void setFoodRecords(List<FoodRecord> foodRecords) {
		this.foodRecords = foodRecords;
	}

	public List<DoseRegimen> getDoseRegimenRecords() {
		return doseRegimenRecords;
	}

	public void setDoseRegimenRecords(List<DoseRegimen> doseRegimenRecords) {
		this.doseRegimenRecords = doseRegimenRecords;
	}

	public List<CurrentMedication> getCurrentMedications() {
		return currentMedications;
	}

	public void setCurrentMedications(List<CurrentMedication> currentMedications) {
		this.currentMedications = currentMedications;
	}

	public List<AdverseEffect> getAdverseEffects() {
		return adverseEffects;
	}

	public void setAdverseEffects(List<AdverseEffect> adverseEffects) {
		this.adverseEffects = adverseEffects;
	}

	public List<InsuranceInformation> getInsuranceRecords() {
		return insuranceRecords;
	}

	public void setInsuranceRecords(List<InsuranceInformation> insuranceRecords) {
		this.insuranceRecords = insuranceRecords;
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
