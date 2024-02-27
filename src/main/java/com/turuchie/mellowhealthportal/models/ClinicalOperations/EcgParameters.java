package com.turuchie.mellowhealthportal.models.ClinicalOperations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ecg_parameters")
public class EcgParameters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please Enter PR Interval Value!")
    private BigDecimal prInterval;
    @NotNull(message = "Please Enter QRS Value!")
    private BigDecimal qrsComplex;
    @NotNull(message = "Please Confirm Cardiac QT Status!")
    private BigDecimal qtcFMale;
    @NotNull(message = "Please Confirm Cardiac QT Status!")
    private BigDecimal qtcFFemale;
    @NotNull(message = "Please Confirm Cardiac ST Status!")
    private boolean stTAbnormalities;
    @NotNull(message = "Please Confirm Arrythmia Status!")
    private boolean arrhythmias;
    @NotNull(message = "Please Confirm Cardiac Conductio Status!")
    private boolean cardiacConductionAbnormalities;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ecg_parameters_id")
    private EcgParameters ecgParameters;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    @NotNull(message = "Please Enter Case Id!")
    private PatientCase patientCase;

    public EcgParameters() {
    }
 
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public BigDecimal getPrInterval() {
		return prInterval;
	}

	public void setPrInterval(BigDecimal prInterval) {
		this.prInterval = prInterval;
	}

	public BigDecimal getQrsComplex() {
		return qrsComplex;
	}

	public void setQrsComplex(BigDecimal qrsComplex) {
		this.qrsComplex = qrsComplex;
	}

	public BigDecimal getQtcFMale() {
		return qtcFMale;
	}

	public void setQtcFMale(BigDecimal qtcFMale) {
		this.qtcFMale = qtcFMale;
	}

	public BigDecimal getQtcFFemale() {
		return qtcFFemale;
	}

	public void setQtcFFemale(BigDecimal qtcFFemale) {
		this.qtcFFemale = qtcFFemale;
	}

	public boolean isStTAbnormalities() {
		return stTAbnormalities;
	}

	public void setStTAbnormalities(boolean stTAbnormalities) {
		this.stTAbnormalities = stTAbnormalities;
	}

	public boolean isArrhythmias() {
		return arrhythmias;
	}

	public void setArrhythmias(boolean arrhythmias) {
		this.arrhythmias = arrhythmias;
	}

	public boolean isCardiacConductionAbnormalities() {
		return cardiacConductionAbnormalities;
	}

	public void setCardiacConductionAbnormalities(boolean cardiacConductionAbnormalities) {
		this.cardiacConductionAbnormalities = cardiacConductionAbnormalities;
	}

	public EcgParameters getEcgParameters() {
		return ecgParameters;
	}

	public void setEcgParameters(EcgParameters ecgParameters) {
		this.ecgParameters = ecgParameters;
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