package com.turuchie.mellowhealthportal.models.DiagnosticProcedures;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientVitalRecord;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.models.Physicians.Physician;

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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pediatric_gi_assessments")
public class PediatricGIAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;

    @NotNull(message = "Onset is required")
    private LocalDate onset;

    @NotBlank(message = "Location is required")
    @Size(max = 255, message = "Location must be at most 255 characters")
    private String location;

    @NotBlank(message = "Quality is required")
    @Size(max = 255, message = "Quality must be at most 255 characters")
    private String quality;

    @DecimalMin(value = "0.0", inclusive = false, message = "Weight change amount must be greater than 0")
    @Digits(integer = 5, fraction = 2, message = "Weight change amount must have at most 5 digits in total with 2 decimal places")
    private BigDecimal weightChangeAmount;

    @NotBlank(message = "Please Enter Exacerbating Factors")
    @Size(max = 255, message = "Exacerbating Factorsmust be at most 255 characters")
    private String exacerbatingFactors;

    @NotBlank(message = "Please Enter Alleviating Factors")
    @Size(max = 255, message = "Alleviating Factors must be at most 255 characters")
    private String alleviatingFactors;

    @NotBlank(message = "History Of Similar Symptoms is required")
    @Size(max = 255, message = "History Of Similar Symptoms must be at most 255 characters")
    private String historyOfSimilarSymptoms;

    @NotBlank(message = "History Of Abdominal Surgeries is required")
    @Size(max = 255, message = "History Of Abdominal Surgeries must be at most 255 characters")
    private String historyOfAbdominalSurgeries;

    // Add boolean attributes
    @NotNull(message = "Changes in weight is required")
    private Boolean changesInWeight;

    @NotNull(message = "SkinRash Status is required")
    private Boolean skinRash;

    @NotNull(message = "Bloody Mucoid Stool Status is required")
    private Boolean bloodyMucoidStools;

    @NotNull(message = "Changes in stool color is required")
    private Boolean changeInStoolColor;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    @NotNull(message = "Please Select Case ID!")
    private PatientCase patientCase;

    @ManyToOne
    @JoinColumn(name = "physician_id")
    @NotNull(message = "Please Select Physician!")
    private Physician physician;

    @ManyToOne
    @JoinColumn(name = "patientVital_id")
    private PatientVitalRecord patientVital;

    // Constructors, getters, and setters
    public PediatricGIAssessment () {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getOnset() {
		return onset;
	}

	public void setOnset(LocalDate onset) {
		this.onset = onset;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public BigDecimal getWeightChangeAmount() {
		return weightChangeAmount;
	}

	public void setWeightChangeAmount(BigDecimal weightChangeAmount) {
		this.weightChangeAmount = weightChangeAmount;
	}

	public String getExacerbatingFactors() {
		return exacerbatingFactors;
	}

	public void setExacerbatingFactors(String exacerbatingFactors) {
		this.exacerbatingFactors = exacerbatingFactors;
	}

	public String getAlleviatingFactors() {
		return alleviatingFactors;
	}

	public void setAlleviatingFactors(String alleviatingFactors) {
		this.alleviatingFactors = alleviatingFactors;
	}

	public String getHistoryOfSimilarSymptoms() {
		return historyOfSimilarSymptoms;
	}

	public void setHistoryOfSimilarSymptoms(String historyOfSimilarSymptoms) {
		this.historyOfSimilarSymptoms = historyOfSimilarSymptoms;
	}

	public String getHistoryOfAbdominalSurgeries() {
		return historyOfAbdominalSurgeries;
	}

	public void setHistoryOfAbdominalSurgeries(String historyOfAbdominalSurgeries) {
		this.historyOfAbdominalSurgeries = historyOfAbdominalSurgeries;
	}

	public Boolean getChangesInWeight() {
		return changesInWeight;
	}

	public void setChangesInWeight(Boolean changesInWeight) {
		this.changesInWeight = changesInWeight;
	}

	public Boolean getSkinRash() {
		return skinRash;
	}

	public void setSkinRash(Boolean skinRash) {
		this.skinRash = skinRash;
	}

	public Boolean getBloodyMucoidStools() {
		return bloodyMucoidStools;
	}

	public void setBloodyMucoidStools(Boolean bloodyMucoidStools) {
		this.bloodyMucoidStools = bloodyMucoidStools;
	}

	public Boolean getChangeInStoolColor() {
		return changeInStoolColor;
	}

	public void setChangeInStoolColor(Boolean changeInStoolColor) {
		this.changeInStoolColor = changeInStoolColor;
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

	public Physician getPhysician() {
		return physician;
	}

	public void setPhysician(Physician physician) {
		this.physician = physician;
	}

	public PatientVitalRecord getPatientVital() {
		return patientVital;
	}

	public void setPatientVital(PatientVitalRecord patientVital) {
		this.patientVital = patientVital;
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
