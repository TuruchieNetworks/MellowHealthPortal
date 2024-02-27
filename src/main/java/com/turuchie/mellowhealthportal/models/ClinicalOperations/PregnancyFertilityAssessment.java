package com.turuchie.mellowhealthportal.models.ClinicalOperations;

import java.math.BigDecimal;
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
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pregnancy_fertility_assessments")
public class PregnancyFertilityAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "betahcglevels", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Beta hCG Levels Must Be Greater Than 0 mIU/mL")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Beta hCG Levels Must Be Less Than or equal to 1000 mIU/mL")
    private BigDecimal betaHCGLevels;

    @Column(name = "estradiol", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Estradiol Levels Must Be Greater Than 0 pg/mL")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Estradiol Levels Must Be Less Than or equal to 1000 pg/mL")
    private BigDecimal estradiol;

    @Column(name = "fsh", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "FSH Levels Must Be Greater Than 0 mIU/mL")
    @DecimalMax(value = "1000.0", inclusive = true, message = "FSH Levels Must Be Less Than or equal to 1000 mIU/mL")
    private BigDecimal fsh;
    
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

    public PregnancyFertilityAssessment() {
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBetaHCGLevels() {
		return betaHCGLevels;
	}

	public void setBetaHCGLevels(BigDecimal betaHCGLevels) {
		this.betaHCGLevels = betaHCGLevels;
	}

	public BigDecimal getEstradiol() {
		return estradiol;
	}

	public void setEstradiol(BigDecimal estradiol) {
		this.estradiol = estradiol;
	}

	public BigDecimal getFsh() {
		return fsh;
	}

	public void setFsh(BigDecimal fsh) {
		this.fsh = fsh;
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

	// Getters and setters
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}