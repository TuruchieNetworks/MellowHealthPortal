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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "thyroid_function_records")
public class ThyroidFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please Enter Thyroid Stimulating Hormone")
    @DecimalMin(value = "0.0", inclusive = false, message = "Thyroid Stimulating Hormone Must Be Greater Than 0 microunits per mililiter (mIU/L)!")
    private BigDecimal thyroidStimulatingHormone;

    @NotNull(message = "Please Enter Free T4")
    @DecimalMin(value = "0.0", inclusive = false, message = "Free T4 Must Be Greater Than 0ng/dL!")
    private BigDecimal freeT4;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    @NotNull(message = "Please Select Case Id!")
    private PatientCase patientCase;

    public ThyroidFunction() {
    }
 
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public BigDecimal getThyroidStimulatingHormone() {
		return thyroidStimulatingHormone;
	}

	public void setThyroidStimulatingHormone(BigDecimal thyroidStimulatingHormone) {
		this.thyroidStimulatingHormone = thyroidStimulatingHormone;
	}

	public BigDecimal getFreeT4() {
		return freeT4;
	}

	public void setFreeT4(BigDecimal freeT4) {
		this.freeT4 = freeT4;
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