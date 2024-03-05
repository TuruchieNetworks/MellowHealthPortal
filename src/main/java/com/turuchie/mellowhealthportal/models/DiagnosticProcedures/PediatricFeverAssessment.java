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
@Table(name = "pediatric_fever_assessments")
public class PediatricFeverAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Severity is required")
    @Size(max = 255, message = "Severity must be at most 255 characters")
    private String severity;

    @NotBlank(message = "Duration is required")
    @Size(max = 255, message = "Duration must be at most 255 characters")
    private String duration;

    @NotBlank(message = "Associated symptoms are required")
    @Size(max = 255, message = "Associated symptoms must be at most 255 characters")
    private String associatedSymptoms;

    @NotBlank(message = "Poor appetite is required")
    @Size(max = 255, message = "Poor appetite must be at most 255 characters")
    private String poorAppetite;

    @NotBlank(message = "Convulsions are required")
    @Size(max = 255, message = "Convulsions must be at most 255 characters")
    private String convulsions;

    @NotBlank(message = "Lethargy is required")
    @Size(max = 255, message = "Lethargy must be at most 255 characters")
    private String lethargy;

    @NotBlank(message = "Sleepiness is required")
    @Size(max = 255, message = "Sleepiness must be at most 255 characters")
    private String sleepiness;

    @NotBlank(message = "Sick contacts is required")
    @Size(max = 255, message = "Sick contacts must be at most 255 characters")
    private String sickContacts;

    @NotBlank(message = "Day care information is required")
    @Size(max = 255, message = "Day care information must be at most 255 characters")
    private String dayCare;

    @NotBlank(message = "Immunization details are required")
    @Size(max = 255, message = "Immunization details must be at most 255 characters")
    private String immunizations;

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
    public PediatricFeverAssessment () {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    // Constructors, getters, setters, and lifecycle methods

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getAssociatedSymptoms() {
		return associatedSymptoms;
	}

	public void setAssociatedSymptoms(String associatedSymptoms) {
		this.associatedSymptoms = associatedSymptoms;
	}

	public String getPoorAppetite() {
		return poorAppetite;
	}

	public void setPoorAppetite(String poorAppetite) {
		this.poorAppetite = poorAppetite;
	}

	public String getConvulsions() {
		return convulsions;
	}

	public void setConvulsions(String convulsions) {
		this.convulsions = convulsions;
	}

	public String getLethargy() {
		return lethargy;
	}

	public void setLethargy(String lethargy) {
		this.lethargy = lethargy;
	}

	public String getSleepiness() {
		return sleepiness;
	}

	public void setSleepiness(String sleepiness) {
		this.sleepiness = sleepiness;
	}

	public String getSickContacts() {
		return sickContacts;
	}

	public void setSickContacts(String sickContacts) {
		this.sickContacts = sickContacts;
	}

	public String getDayCare() {
		return dayCare;
	}

	public void setDayCare(String dayCare) {
		this.dayCare = dayCare;
	}

	public String getImmunizations() {
		return immunizations;
	}

	public void setImmunizations(String immunizations) {
		this.immunizations = immunizations;
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

	@PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

	@PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
