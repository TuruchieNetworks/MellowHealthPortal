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
@Table(name = "abdominal_pain_assessments")
public class AbdominalAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Sexual History is required")
    @Size(max = 255, message = "Sexual History must be at most 255 characters")
    private String sexualHistory;

    @NotBlank(message = "Pregnancy History is required")
    @Size(max = 255, message = "Pregnancy History must be at most 255 characters")
    private String pregnancyHistory;

    // Location, quality, intensity, duration, radiation, timing
    @NotBlank(message = "Location is required")
    @Size(max = 255, message = "Location must be at most 255 characters")
    private String location;

    @NotBlank(message = "Quality is required")
    @Size(max = 255, message = "Quality must be at most 255 characters")
    private String quality;

    // Associated Symptoms
    @NotBlank(message = "Associated symptoms are required")
    @Size(max = 255, message = "Associated symptoms must be at most 255 characters")
    private String associatedSymptoms;

    // Exacerbating and Alleviating Factors
    @NotBlank(message = "Exacerbating factors are required")
    @Size(max = 255, message = "Exacerbating factors must be at most 255 characters")
    private String exacerbatingFactors;

    @NotBlank(message = "Alleviating factors are required")
    @Size(max = 255, message = "Alleviating factors must be at most 255 characters")
    private String alleviatingFactors;

    // History and Medical Details
    @NotBlank(message = "History of similar symptoms is required")
    @Size(max = 255, message = "History of similar symptoms must be at most 255 characters")
    private String historyOfSimilarSymptoms;

    // Physical Exam Components
    @NotBlank(message = "CVA percussion is required")
    @Size(max = 255, message = "CVA percussion must be at most 255 characters")
    private String cvaPercussion;

    @NotBlank(message = "Bowel Sounds is required")
    @Size(max = 255, message = "Bowel Sounds must be at most 255 characters")
    private String bowelSounds;

    @NotBlank(message = "Aortic Bruits is required")
    @Size(max = 255, message = "Aortic Bruits must be at most 255 characters")
    private String aorticBruits;

    @NotBlank(message = "Rectal Exam is required")
    @Size(max = 255, message = "Rectal Exam must be at most 255 characters")
    private String rectalExam;

    @NotBlank(message = "Pelvic Exam is required")
    @Size(max = 255, message = "Pelvic Exam must be at most 255 characters")
    private String pelvicExam;
 
    @NotNull(message = "Tenderness is required")
    private Boolean tenderness;

    @NotNull(message = "Guarding is required")
    private Boolean guarding;

    @NotNull(message = "Rebound is required")
    private Boolean rebound;

    @NotNull(message = "Murphy's sign is required")
    private Boolean murphysSign;

    @NotNull(message = "Psoas sign is required")
    private Boolean psoasSign;

    @NotNull(message = "Obturator sign is required")
    private Boolean obturatorSign;

    @NotNull(message = "Please Confirm Alcohol Use")
    private Boolean  alcoholUse;

    @NotNull(message = "Please Confirm Alcohol Use")
    private Boolean drugUse;

    @NotNull(message = "Please Confirm Alcohol Use")
    private Boolean domesticViolenceHistory;

    @NotNull(message = "Please Confirm Alcohol Use")
    private Boolean stressAnxietyHistory;

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
    public AbdominalAssessment () {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSexualHistory() {
		return sexualHistory;
	}

	public void setSexualHistory(String sexualHistory) {
		this.sexualHistory = sexualHistory;
	}

	public String getPregnancyHistory() {
		return pregnancyHistory;
	}

	public void setPregnancyHistory(String pregnancyHistory) {
		this.pregnancyHistory = pregnancyHistory;
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

	public String getAssociatedSymptoms() {
		return associatedSymptoms;
	}

	public void setAssociatedSymptoms(String associatedSymptoms) {
		this.associatedSymptoms = associatedSymptoms;
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

	public String getCvaPercussion() {
		return cvaPercussion;
	}

	public void setCvaPercussion(String cvaPercussion) {
		this.cvaPercussion = cvaPercussion;
	}

	public String getBowelSounds() {
		return bowelSounds;
	}

	public void setBowelSounds(String bowelSounds) {
		this.bowelSounds = bowelSounds;
	}

	public String getAorticBruits() {
		return aorticBruits;
	}

	public void setAorticBruits(String aorticBruits) {
		this.aorticBruits = aorticBruits;
	}

	public String getRectalExam() {
		return rectalExam;
	}

	public void setRectalExam(String rectalExam) {
		this.rectalExam = rectalExam;
	}

	public String getPelvicExam() {
		return pelvicExam;
	}

	public void setPelvicExam(String pelvicExam) {
		this.pelvicExam = pelvicExam;
	}

	public Boolean getTenderness() {
		return tenderness;
	}

	public void setTenderness(Boolean tenderness) {
		this.tenderness = tenderness;
	}

	public Boolean getGuarding() {
		return guarding;
	}

	public void setGuarding(Boolean guarding) {
		this.guarding = guarding;
	}

	public Boolean getRebound() {
		return rebound;
	}

	public void setRebound(Boolean rebound) {
		this.rebound = rebound;
	}

	public Boolean getMurphysSign() {
		return murphysSign;
	}

	public void setMurphysSign(Boolean murphysSign) {
		this.murphysSign = murphysSign;
	}

	public Boolean getPsoasSign() {
		return psoasSign;
	}

	public void setPsoasSign(Boolean psoasSign) {
		this.psoasSign = psoasSign;
	}

	public Boolean getObturatorSign() {
		return obturatorSign;
	}

	public void setObturatorSign(Boolean obturatorSign) {
		this.obturatorSign = obturatorSign;
	}

	public Boolean getAlcoholUse() {
		return alcoholUse;
	}

	public void setAlcoholUse(Boolean alcoholUse) {
		this.alcoholUse = alcoholUse;
	}

	public Boolean getDrugUse() {
		return drugUse;
	}

	public void setDrugUse(Boolean drugUse) {
		this.drugUse = drugUse;
	}

	public Boolean getDomesticViolenceHistory() {
		return domesticViolenceHistory;
	}

	public void setDomesticViolenceHistory(Boolean domesticViolenceHistory) {
		this.domesticViolenceHistory = domesticViolenceHistory;
	}

	public Boolean getStressAnxietyHistory() {
		return stressAnxietyHistory;
	}

	public void setStressAnxietyHistory(Boolean stressAnxietyHistory) {
		this.stressAnxietyHistory = stressAnxietyHistory;
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
