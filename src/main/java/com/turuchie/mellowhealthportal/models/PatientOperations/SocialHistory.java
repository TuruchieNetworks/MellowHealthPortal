package com.turuchie.mellowhealthportal.models.PatientOperations;

import java.time.LocalDateTime;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "socialHistories")
public class SocialHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Smoking history
    @Column(name = "smoker")
    @NotNull(message = "Please select smoker status")
    private Boolean smoker;

    @Column(name = "packs_per_day")
    @Min(value = 0, message = "Packs per day must be greater than or equal to 0")
    private Integer packsPerDay;

    @Column(name = "smoking_duration")
    @NotBlank(message = "Smoking duration cannot be blank")
    private String smokingDuration;

    // Alcohol history
    @Column(name = "drinker")
    @NotNull(message = "Please select drinker status")
    private Boolean drinker;

    @Column(name = "alcohol_type")
    @NotBlank(message = "Please enter alcoholic type (wine or liquor)")
    private String alcoholType;

    @Column(name = "drinks_per_week")
    @Min(value = 0, message = "Drinks per week must be greater than or equal to 0")
    private Integer drinksPerWeek;

    // Recreational drug use
    @Column(name = "recreational_drug_user")
    @NotBlank(message = "Please select recreational drug user status")
    private Boolean recreationalDrugUser;

    @Column(name = "drugs_used")
    @NotBlank(message = "Please enter drugs used or enter none if unavailable")
    private String drugsUsed;

    @Column(name = "drug_use_frequency")
    @Min(value = 0, message = "Drug use frequency must be greater than or equal to 0")
    private Integer drugUseFrequency;

    // Occupation and living situation
    @Column(name = "occupation")
    @NotBlank(message = "Please enter occupation")
    private String occupation;

    @Column(name = "living_situation")
    @NotBlank(message = "Please enter living situation")
    private String livingSituation;

    // Marital and family status
    @Column(name = "married")
    @NotNull(message = "Please select marital status")
    private Boolean married;

    @Column(name = "children")
    @NotBlank(message = "Please enter number of children or enter none if unavailable")
    private Integer numberOfChildren;

    // Job-related stress and environmental hazards
    @Column(name = "job_related_stress")
    @NotNull(message = "Please select job-related stress status")
    private Boolean jobRelatedStress;

    @Column(name = "environmental_hazards")
    @NotNull(message = "Please enter work conditions")
    private Boolean environmentalHazards;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id", nullable = false)
    private PatientCase patientCase;

	public SocialHistory() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getSmoker() {
		return smoker;
	}

	public void setSmoker(Boolean smoker) {
		this.smoker = smoker;
	}

	public Integer getPacksPerDay() {
		return packsPerDay;
	}

	public void setPacksPerDay(Integer packsPerDay) {
		this.packsPerDay = packsPerDay;
	}

	public String getSmokingDuration() {
		return smokingDuration;
	}

	public void setSmokingDuration(String smokingDuration) {
		this.smokingDuration = smokingDuration;
	}

	public Boolean getDrinker() {
		return drinker;
	}

	public void setDrinker(Boolean drinker) {
		this.drinker = drinker;
	}

	public String getAlcoholType() {
		return alcoholType;
	}

	public void setAlcoholType(String alcoholType) {
		this.alcoholType = alcoholType;
	}

	public Integer getDrinksPerWeek() {
		return drinksPerWeek;
	}

	public void setDrinksPerWeek(Integer drinksPerWeek) {
		this.drinksPerWeek = drinksPerWeek;
	}

	public Boolean getRecreationalDrugUser() {
		return recreationalDrugUser;
	}

	public void setRecreationalDrugUser(Boolean recreationalDrugUser) {
		this.recreationalDrugUser = recreationalDrugUser;
	}

	public String getDrugsUsed() {
		return drugsUsed;
	}

	public void setDrugsUsed(String drugsUsed) {
		this.drugsUsed = drugsUsed;
	}

	public Integer getDrugUseFrequency() {
		return drugUseFrequency;
	}

	public void setDrugUseFrequency(Integer drugUseFrequency) {
		this.drugUseFrequency = drugUseFrequency;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getLivingSituation() {
		return livingSituation;
	}

	public void setLivingSituation(String livingSituation) {
		this.livingSituation = livingSituation;
	}

	public Boolean getMarried() {
		return married;
	}

	public void setMarried(Boolean married) {
		this.married = married;
	}

	public Integer getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(Integer numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public Boolean getJobRelatedStress() {
		return jobRelatedStress;
	}

	public void setJobRelatedStress(Boolean jobRelatedStress) {
		this.jobRelatedStress = jobRelatedStress;
	}

	public Boolean getEnvironmentalHazards() {
		return environmentalHazards;
	}

	public void setEnvironmentalHazards(Boolean environmentalHazards) {
		this.environmentalHazards = environmentalHazards;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public PatientCase getPatientCase() {
		return patientCase;
	}

	public void setPatientCase(PatientCase patientCase) {
		this.patientCase = patientCase;
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
