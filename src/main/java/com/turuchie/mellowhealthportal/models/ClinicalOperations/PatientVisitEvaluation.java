package com.turuchie.mellowhealthportal.models.ClinicalOperations;

import java.time.LocalDate;

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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "patient_visit_evaluations")
public class PatientVisitEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Chief complaint is required")
    @Size(max = 255, message = "Chief complaint must be at most 255 characters")
    private String chiefComplaint;

    @NotNull(message = "Onset date is required")
    private LocalDate onsetDate;

    @NotBlank(message = "Precipitating Events is required")
    @Size(max = 255, message = "Precipitating Events must be at most 255 characters")
    private String precipitatingEvents;

    @NotBlank(message = "Progression is required")
    @Size(max = 255, message = "Progression must be at most 255 characters")
    private String progression;

    @NotNull(message = "Please Enter Severity On A Scale Of 1-10")
    @Min(value = 0, message = "Severity must be at least 0")
    @Max(value = 11, message = "Severity must be at most 10")
    private Integer severityOnAScale;

    @NotBlank(message = "Location is required")
    @Size(max = 255, message = "Location must be at most 255 characters")
    private String location;

    @NotBlank(message = "Radiation is required")
    @Size(max = 255, message = "Radiation must be at most 255 characters")
    private String radiation;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must be at most 255 characters")
    private String quality;

    @NotBlank(message = "Please Enter Alleviating Factors")
    @Size(max = 255, message = "Alleviatin Factors must be at most 255 characters")
    private String alleviatingFactors;

    @NotBlank(message = "Please Enter Alleviatin Factors")
    @Size(max = 255, message = "Description must be at most 255 characters")
    private String exacerbatingFactors;

    @NotNull(message = "Please Verify Shortness Of Breath")
    private Boolean shortnessOfBreath;

    @NotNull(message = "Please Verify Presence of Nausea")
    private Boolean nausea;

    @NotNull(message = "Please Verify Vomit Episodes")
    private Boolean vomiting;
 
    @NotNull(message = "Please Verify Presence Of Excessive Sweat")
    private Boolean sweating;

    @NotBlank(message = "Please Provide Any Associated Symptom")
    @Size(max = 255, message = "Associated Symptoms must be at most 255 characters")
    private String associatedSymptoms;

    @NotNull(message = "History Of Previous Episode is required")
    private Boolean previousEpisodes;

    @NotNull(message = "Onset of previous episode is required")
    private LocalDate onsetOfPreviousEpisode;
    
    @NotNull(message = "Severity of previous episode is required")
    @Min(value = 0, message = "Severity of previous episode must be at least 0")
    @Max(value = 1000, message = "Severity must be at most 1000")
    private Integer severityOfPreviousEpisode;
 
    @NotNull(message = "Please Enter Frequency Of Previous Episode")
    @Min(value = 0, message = "Frequency must be at least 0")
    @Max(value = 1000, message = "Frequency must be at most 1000")
    private Integer frequency;

    @NotBlank(message = "Current Medications is required")
    @Size(max = 255, message = "Current Medications must be at most 255 characters")
    private String currentMedications;

    @NotBlank(message = "Past Medical History is required")
    @Size(max = 255, message = "Past Medical History must be at most 255 characters")
    private String pastMedicalHistory;

    @NotBlank(message = "Past Surgical History is required")
    @Size(max = 255, message = "Past Surgical History must be at most 255 characters")
    private String pastSurgicalHistory;

    @NotBlank(message = "Family History is required")
    @Size(max = 255, message = "Family History must be at most 255 characters")
    private String familyHistory;

    @NotBlank(message = "occupation is required")
    @Size(max = 255, message = "occupation must be at most 255 characters")
    private String occupation;

    @NotNull(message = "Please Verify Alcohol Consumption")
    private Boolean alcoholUse;

    @NotNull(message = "Please Verify Illicit Drug Consumption")
    private Boolean illicitDrugUse;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must be at most 255 characters")

    private String sexualActivity;
    @NotBlank(message = "Sexual Activity is required")
    @Size(max = 255, message = "Sexual Activity must be at most 255 characters")

    @NotBlank(message = "Exercise Information is required")
    @Size(max = 255, message = "Exercise Information must be at most 255 characters")
    private String exercise;

    @NotBlank(message = "Diet Information is required")
    @Size(max = 255, message = "Diet Information must be at most 255 characters")
    private String diet;

    @NotNull(message = "History Of Drug Allergy is required")
    private Boolean drugAllergies;

    @Column(updatable = false)
    private LocalDate createdAt;

    private LocalDate updatedAt;

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
    public PatientVisitEvaluation () {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }
}
