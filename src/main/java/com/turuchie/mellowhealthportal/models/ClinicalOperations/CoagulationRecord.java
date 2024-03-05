package com.turuchie.mellowhealthportal.models.ClinicalOperations;

import java.time.LocalDateTime;

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
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "coagulation_records")
public class CoagulationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Coagulation Time Is Required Normal Time is between 70 - 120s")
    @DecimalMin(value = "0.0", inclusive = false, message = "Coagulation Time Must Be Greater Than 0s")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Coagulation Time Must Be Less Than or equal to 1000s")
    private Double coagulation;

    @NotNull(message = "Albumin Level Is Required, normal levels is between 3.5 - 5.5 g/dL. Levels less than 50 mg/dL Signify Danger Of Bleeding After Surgery while Levels Above 700mg/dL signify higher chances of forming clots!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Albumin Levels Must Be Greater Than 0 grams per deciliter (g/dL)")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Albumin Levels Must Be Less Than or equal to 1000 grams per deciliter!")
    private Double albumin;

    @NotNull(message = "Please Enter Fibrinogen Level, normal levels is between 200 - 400 mg/dL!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Fibrinogen Levels Must Be Greater Than 0 grams per deciliter (g/dL)")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Fibrinogen Levels Must Be Less Than or equal to 1000 grams per deciliter!")
    private Double fibrinogen;

    @NotNull(message = "Partial Thromboplastin Time Is Required, normal levels is between 25 - 35 seconds")
    @DecimalMin(value = "0.0", inclusive = false, message = "Partial Thromboplastin Time Must Be Greater Than 0 sec")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Partial Thromboplastin Time Must Be Less Than or equal to 1000 seconds!")
    private Double partialThromboplastinTime;

    @NotNull(message = "Please Enter Prothrombin Time, normal levels is between 60 - 70 seconds")
    @DecimalMin(value = "0.0", inclusive = false, message = "Prothrombin Time Must Be Greater Than 0s")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Prothrombin Time Must Be Less Than or equal to 1000s!")
    private Double prothrombinTime;

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
    
    public CoagulationRecord () {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCoagulation() {
		return coagulation;
	}

	public void setCoagulation(Double coagulation) {
		this.coagulation = coagulation;
	}

	public Double getAlbumin() {
		return albumin;
	}

	public void setAlbumin(Double albumin) {
		this.albumin = albumin;
	}

	public Double getFibrinogen() {
		return fibrinogen;
	}

	public void setFibrinogen(Double fibrinogen) {
		this.fibrinogen = fibrinogen;
	}

	public Double getPartialThromboplastinTime() {
		return partialThromboplastinTime;
	}

	public void setPartialThromboplastinTime(Double partialThromboplastinTime) {
		this.partialThromboplastinTime = partialThromboplastinTime;
	}

	public Double getProthrombinTime() {
		return prothrombinTime;
	}

	public void setProthrombinTime(Double prothrombinTime) {
		this.prothrombinTime = prothrombinTime;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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
