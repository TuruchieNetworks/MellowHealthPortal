package com.turuchie.mellowhealthportal.models.ClinicalOperations;

import java.time.LocalDateTime;

import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;

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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "exclusion_criteria")
public class ExclusionCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please Confirm Heart Health Status!")
    private Boolean clinicallySignificantHeartDisease;

    @NotNull(message = "Please Confirm QT Interval Status!")
    private Boolean concomitantConditionProlongQTInterval;

    @NotNull(message = "Please Confirm Gastrointestinal Status!")
    private Boolean gastrointestinalDiseaseOrImpairedFunction;

    @NotNull(message = "Please Confirm Liver or Renal Status!")
    private Boolean historyOfChronicLiverInjuryOrDisease;

    @NotNull(message = "Please Confirm Hepatitis B or C Status!")
    private Boolean positiveSerologicMarkersForHepatitisBorC;

    @NotNull(message = "Please Confirm HIV Status!")
    private Boolean positiveHIVTest;

    @NotNull(message = "Please Confirm ECG Status!")
    private Boolean abnormalECG;

    @NotNull(message = "Please Confirm Cardiac Function Status!")
    private Boolean impairedCardiacFunction;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    private PatientCase patientCase;

    public ExclusionCriteria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getClinicallySignificantHeartDisease() {
        return clinicallySignificantHeartDisease;
    }

    public void setClinicallySignificantHeartDisease(Boolean clinicallySignificantHeartDisease) {
        this.clinicallySignificantHeartDisease = clinicallySignificantHeartDisease;
    }

    public Boolean getConcomitantConditionProlongQTInterval() {
        return concomitantConditionProlongQTInterval;
    }

    public void setConcomitantConditionProlongQTInterval(Boolean concomitantConditionProlongQTInterval) {
        this.concomitantConditionProlongQTInterval = concomitantConditionProlongQTInterval;
    }

    public Boolean getGastrointestinalDiseaseOrImpairedFunction() {
        return gastrointestinalDiseaseOrImpairedFunction;
    }

    public void setGastrointestinalDiseaseOrImpairedFunction(Boolean gastrointestinalDiseaseOrImpairedFunction) {
        this.gastrointestinalDiseaseOrImpairedFunction = gastrointestinalDiseaseOrImpairedFunction;
    }

    public Boolean getHistoryOfChronicLiverInjuryOrDisease() {
        return historyOfChronicLiverInjuryOrDisease;
    }

    public void setHistoryOfChronicLiverInjuryOrDisease(Boolean historyOfChronicLiverInjuryOrDisease) {
        this.historyOfChronicLiverInjuryOrDisease = historyOfChronicLiverInjuryOrDisease;
    }

    public Boolean getPositiveSerologicMarkersForHepatitisBorC() {
        return positiveSerologicMarkersForHepatitisBorC;
    }

    public void setPositiveSerologicMarkersForHepatitisBorC(Boolean positiveSerologicMarkersForHepatitisBorC) {
        this.positiveSerologicMarkersForHepatitisBorC = positiveSerologicMarkersForHepatitisBorC;
    }

    public Boolean getPositiveHIVTest() {
        return positiveHIVTest;
    }

    public void setPositiveHIVTest(Boolean positiveHIVTest) {
        this.positiveHIVTest = positiveHIVTest;
    }

    public Boolean getAbnormalECG() {
        return abnormalECG;
    }

    public void setAbnormalECG(Boolean abnormalECG) {
        this.abnormalECG = abnormalECG;
    }

    public Boolean getImpairedCardiacFunction() {
        return impairedCardiacFunction;
    }

    public void setImpairedCardiacFunction(Boolean impairedCardiacFunction) {
        this.impairedCardiacFunction = impairedCardiacFunction;
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