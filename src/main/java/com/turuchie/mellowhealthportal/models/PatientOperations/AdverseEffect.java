package com.turuchie.mellowhealthportal.models.PatientOperations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.DoseRegimen;
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
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "adverse_effects")
public class AdverseEffect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "drug_name", length = 255)
    private String drugName;

    @Column(name = "symptoms", length = 255)
    @NotBlank(message = "Please Provide New Medical Symptoms!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Enter Null If Unknown!")
    private String symptoms;

    @Column(name = "complications", length = 255)
    @NotBlank(message = "Please Enter Any Known Complication or Enter Null if Unknown!")
    private String complications;
    
    @Column(name = "administration_route", length = 255)
    @NotBlank(message = "Please Enter Route Of Administration!")
    private String administrationRoute;

    @Column(name = "dose", length = 255)
    @DecimalMin(value = "0.0", inclusive = false, message = "Dosage is Required")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Dose Must Be LessThan or equal to 1000!")
    private BigDecimal dose;

    @Column(name = "frequency_and_regimen", length = 255)
    @DecimalMin(value = "0.0", inclusive = false, message = "Frequency Must Be Greater Than 0")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Freqency Must Be LessThan or equal to 1000")
    private Integer frequencyAndRegimen;

    @Column(name = "pharmaceutical_form", length = 255)
    @NotBlank(message = "Please Enter State of Administration!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Please Enter Oral, Subdermal, IV, or Null If Unknown!")
    private String pharmaceuticalForm;

    @NotNull(message = "Please Enter The Date or Approximate Date You Experienced This Symptom!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime startDate;

    @Column(name = "onset_time")
    @NotBlank(message = "Please Enter the Onset Time")
    private String onsetTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Please Enter the Resolved Date")
    private LocalDateTime resolvedDate;

    @Column(name = "resolved_time")
    @NotBlank(message = "Please Enter the Resolved Time")
    private String resolvedTime;

    @NotBlank(message = "Please Select Yes or No")
    @Size(min = 1, max = 1, message = "Select Yes or No")
    private String seriousEvent;

    @NotBlank(message = "Please Select Severity")
    @Size(min = 1, max = 1, message = "Select Severity")
    private String severity;

    @NotBlank(message = "Please Select Relationship to Product")
    @Size(min = 1, max = 1, message = "Select Relationship to Product")
    private String relationshipToProduct;

    @NotBlank(message = "Please Select Action Taken")
    @Size(min = 1, max = 1, message = "Select Action Taken")
    private String actionTaken;

    @NotBlank(message = "Please Select Outcome to Date")
    @Size(min = 1, max = 1, message = "Select Outcome to Date")
    private String outcomeToDate;

    @Column(updatable = false)
    private LocalDate createdAt;

    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient; 

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    private PatientCase patientCase;

    @ManyToOne
    @JoinColumn(name = "currentMedication_id")
    private CurrentMedication currentMedication;

    @ManyToOne
    @JoinColumn(name = "doseRegimen_id")
    private DoseRegimen doseRegimen;

    public AdverseEffect() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	public String getComplications() {
		return complications;
	}

	public void setComplications(String complications) {
		this.complications = complications;
	}

	public String getAdministrationRoute() {
		return administrationRoute;
	}

	public void setAdministrationRoute(String administrationRoute) {
		this.administrationRoute = administrationRoute;
	}

	public BigDecimal getDose() {
		return dose;
	}

	public void setDose(BigDecimal dose) {
		this.dose = dose;
	}

	public Integer getFrequencyAndRegimen() {
		return frequencyAndRegimen;
	}

	public void setFrequencyAndRegimen(Integer frequencyAndRegimen) {
		this.frequencyAndRegimen = frequencyAndRegimen;
	}

	public String getPharmaceuticalForm() {
		return pharmaceuticalForm;
	}

	public void setPharmaceuticalForm(String pharmaceuticalForm) {
		this.pharmaceuticalForm = pharmaceuticalForm;
	}

	public CurrentMedication getCurrentMedication() {
		return currentMedication;
	}

	public void setCurrentMedication(CurrentMedication currentMedication) {
		this.currentMedication = currentMedication;
	}

	public DoseRegimen getDoseRegimen() {
		return doseRegimen;
	}

	public void setDoseRegimen(DoseRegimen doseRegimen) {
		this.doseRegimen = doseRegimen;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
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

	public String getOnsetTime() {
		return onsetTime;
	}

	public void setOnsetTime(String onsetTime) {
		this.onsetTime = onsetTime;
	}

	public LocalDateTime getResolvedDate() {
		return resolvedDate;
	}

	public void setResolvedDate(LocalDateTime resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	public String getResolvedTime() {
		return resolvedTime;
	}

	public void setResolvedTime(String resolvedTime) {
		this.resolvedTime = resolvedTime;
	}

	public String getSeriousEvent() {
		return seriousEvent;
	}

	public void setSeriousEvent(String seriousEvent) {
		this.seriousEvent = seriousEvent;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getRelationshipToProduct() {
		return relationshipToProduct;
	}

	public void setRelationshipToProduct(String relationshipToProduct) {
		this.relationshipToProduct = relationshipToProduct;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	public String getOutcomeToDate() {
		return outcomeToDate;
	}

	public void setOutcomeToDate(String outcomeToDate) {
		this.outcomeToDate = outcomeToDate;
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
