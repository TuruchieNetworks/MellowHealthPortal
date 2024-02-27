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
@Table(name = "urinalysis")
public class UrineAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bilirubin", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Bilirubin Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Bilirubin Must Be Less Than or equal to 100 mg/dl")
    private BigDecimal bilirubin;

    @Column(name = "blood", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Blood Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Blood Must Be Less Than or equal to 100 mg/dl")
    private BigDecimal blood;

    @Column(name = "glucose", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Glucose Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Glucose Must Be Less Than or equal to 100 mg/dl")
    private BigDecimal glucose;

    @Column(name = "hematocytes", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Hematocytes Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Hematocytes Must Be Less Than or equal to 100 mg/dl")
    private BigDecimal hematocytes;

    @Column(name = "ketones", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Ketones Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Ketones Must Be Less Than or equal to 100 mg/dl")
    private BigDecimal ketones;

    @Column(name = "leukocytes", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Leukocytes Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Leukocytes Must Be Less Than or equal to 100 mg/dl")
    private BigDecimal leukocytes;

    @Column(name = "phspecific_gravity", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "pH Specific Gravity Must Be Greater Than 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "pH Specific Gravity Must Be Less Than or equal to 100")
    private BigDecimal phSpecificGravity;

    @Column(name = "protein", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Protein Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Protein Must Be Less Than or equal to 100 mg/dl")
    private BigDecimal protein;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    @NotNull(message = "Patient Case Is Required!")
    private PatientCase patientCase;

    public UrineAnalysis() {
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBilirubin() {
		return bilirubin;
	}

	public void setBilirubin(BigDecimal bilirubin) {
		this.bilirubin = bilirubin;
	}

	public BigDecimal getBlood() {
		return blood;
	}

	public void setBlood(BigDecimal blood) {
		this.blood = blood;
	}
	public BigDecimal getGlucose() {
		return glucose;
	}

	public void setGlucose(BigDecimal glucose) {
		this.glucose = glucose;
	}

	public BigDecimal getHematocytes() {
		return hematocytes;
	}

	public void setHematocytes(BigDecimal hematocytes) {
		this.hematocytes = hematocytes;
	}

	public BigDecimal getKetones() {
		return ketones;
	}

	public void setKetones(BigDecimal ketones) {
		this.ketones = ketones;
	}

	public BigDecimal getLeukocytes() {
		return leukocytes;
	}

	public void setLeukocytes(BigDecimal leukocytes) {
		this.leukocytes = leukocytes;
	}

	public BigDecimal getPhSpecificGravity() {
		return phSpecificGravity;
	}

	public void setPhSpecificGravity(BigDecimal phSpecificGravity) {
		this.phSpecificGravity = phSpecificGravity;
	}

	public BigDecimal getProtein() {
		return protein;
	}

	public void setProtein(BigDecimal protein) {
		this.protein = protein;
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