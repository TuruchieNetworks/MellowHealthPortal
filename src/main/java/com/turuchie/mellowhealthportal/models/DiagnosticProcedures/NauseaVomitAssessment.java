package com.turuchie.mellowhealthportal.models.DiagnosticProcedures;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "nausea_vomit_assessments")
public class NauseaVomitAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Acuity of onset is required")
    @Size(max = 255, message = "Acuity of onset must be at most 255 characters")
    private String acuityOfOnset;

    @NotBlank(message = "Abdominal pain is required")
    @Size(max = 255, message = "Abdominal pain must be at most 255 characters")
    private String abdominalPain;

    @NotBlank(message = "Relation to meals is required")
    @Size(max = 255, message = "Relation to meals must be at most 255 characters")
    private String relationToMeals;

    @NotBlank(message = "Sick contacts is required")
    @Size(max = 255, message = "Sick contacts must be at most 255 characters")
    private String sickContacts;

    @NotBlank(message = "Possible food poisoning is required")
    @Size(max = 255, message = "Possible food poisoning must be at most 255 characters")
    private String possibleFoodPoisoning;

    @NotBlank(message = "Possible pregnancy is required")
    @Size(max = 255, message = "Possible pregnancy must be at most 255 characters")
    private String possiblePregnancy;

    @NotBlank(message = "Neurologic symptoms are required")
    @Size(max = 255, message = "Neurologic symptoms must be at most 255 characters")
    private String neurologicSymptoms;

    @NotBlank(message = "Urinary symptoms are required")
    @Size(max = 255, message = "Urinary symptoms must be at most 255 characters")
    private String urinarySymptoms;

    @NotBlank(message = "Other associated symptoms are required")
    @Size(max = 255, message = "Other associated symptoms must be at most 255 characters")
    private String otherAssociatedSymptoms;

    @NotBlank(message = "Exacerbating factors are required")
    @Size(max = 255, message = "Exacerbating factors must be at most 255 characters")
    private String exacerbatingFactors;

    @NotBlank(message = "Alleviating factors are required")
    @Size(max = 255, message = "Alleviating factors must be at most 255 characters")
    private String alleviatingFactors;

    @NotBlank(message = "Medications are required")
    @Size(max = 255, message = "Medications must be at most 255 characters")
    private String medications;

    @NotBlank(message = "History of abdominal surgery is required")
    @Size(max = 255, message = "History of abdominal surgery must be at most 255 characters")
    private String historyOfAbdominalSurgery;

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
    public NauseaVomitAssessment () {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAcuityOfOnset() {
		return acuityOfOnset;
	}

	public void setAcuityOfOnset(String acuityOfOnset) {
		this.acuityOfOnset = acuityOfOnset;
	}

	public String getAbdominalPain() {
		return abdominalPain;
	}

	public void setAbdominalPain(String abdominalPain) {
		this.abdominalPain = abdominalPain;
	}

	public String getRelationToMeals() {
		return relationToMeals;
	}

	public void setRelationToMeals(String relationToMeals) {
		this.relationToMeals = relationToMeals;
	}

	public String getSickContacts() {
		return sickContacts;
	}

	public void setSickContacts(String sickContacts) {
		this.sickContacts = sickContacts;
	}

	public String getPossibleFoodPoisoning() {
		return possibleFoodPoisoning;
	}

	public void setPossibleFoodPoisoning(String possibleFoodPoisoning) {
		this.possibleFoodPoisoning = possibleFoodPoisoning;
	}

	public String getPossiblePregnancy() {
		return possiblePregnancy;
	}

	public void setPossiblePregnancy(String possiblePregnancy) {
		this.possiblePregnancy = possiblePregnancy;
	}

	public String getNeurologicSymptoms() {
		return neurologicSymptoms;
	}

	public void setNeurologicSymptoms(String neurologicSymptoms) {
		this.neurologicSymptoms = neurologicSymptoms;
	}

	public String getUrinarySymptoms() {
		return urinarySymptoms;
	}

	public void setUrinarySymptoms(String urinarySymptoms) {
		this.urinarySymptoms = urinarySymptoms;
	}

	public String getOtherAssociatedSymptoms() {
		return otherAssociatedSymptoms;
	}

	public void setOtherAssociatedSymptoms(String otherAssociatedSymptoms) {
		this.otherAssociatedSymptoms = otherAssociatedSymptoms;
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

	public String getMedications() {
		return medications;
	}

	public void setMedications(String medications) {
		this.medications = medications;
	}

	public String getHistoryOfAbdominalSurgery() {
		return historyOfAbdominalSurgery;
	}

	public void setHistoryOfAbdominalSurgery(String historyOfAbdominalSurgery) {
		this.historyOfAbdominalSurgery = historyOfAbdominalSurgery;
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