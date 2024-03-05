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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "hematology_records")
public class HematologyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "RBC Count Is Required: Normal Count is between 4.35 - 5.65 Million Red Blood Cells Per Microliter (mc/L) For Men And 3.92 to 5.13 Million Red Blood Cells For Women!")
    @DecimalMin(value = "0.0", inclusive = false, message = "RBC Count Must Be Greater Than 0mcL")
    @DecimalMax(value = "1000.0", inclusive = true, message = "RBC Count Must Be Less Than or equal to 1000 x 10^6 mc/L!")
    private BigDecimal rbcCount;

    @NotNull(message = "WBC Count Is Required: Normal Count is between 4.5 - 11.05 Thousand White Blood Cells Per Microliter (mc/L)!")
    @DecimalMin(value = "0.0", inclusive = false, message = "WBC Count Must Be Greater Than 0mcL")
    @DecimalMax(value = "1000.0", inclusive = true, message = "WBC Count Must Be Less Than or equal to 1000 x 10^3 mc/L!")
    private BigDecimal wbcCount;

    @NotNull(message = "Platelet Count Is Required: Normal Count is between 4.35 to 5.65 Hundred Thousand Platelets Per Microliter (mcL) For Men And 3.92 to 5.13 Million Red Blood Cells For Women!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Platelet Count Must Be Greater Than 0 mc/L")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Platelet Count Must Be Less Than or equal to 1000 x 10^9/L!")
    private BigDecimal plateletCount;

    @NotNull(message = "Hemoglobin Level Is Required: Normal Level is between 14 to 18 g/dl!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Hemoglobin Levels Must Be Greater Than 0mcL Low Levels Signify Anemia!")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Hemoglobin Levels Must Be Less Than or equal to 1000 x 10^6 mc/L!")
    private BigDecimal hemoglobin;

    @NotNull(message = "Hematocrit Levels Is Required: Normal Count is between 40 to 54% In Men And 36 to 48% In Women!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Hematocrit Must Be Greater Than 0%")
    @DecimalMax(value = "1.0", inclusive = true, message = "Hematocrit Must Be Less Than or equal to 100%")
    private BigDecimal hematocrit;

    @NotNull(message = "RBC Count Is Required: Normal Count is between 2500 to 7000 Neutrophils Per Microliter (mc/L)!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Neutrophils Must Be Greater Than 0mcL")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Neutrophils Must Be Less Than or equal to 1000 x 10^6 mc/L!")
    private BigDecimal neutrophils;

    @NotNull(message = "Lymphocyte Is Required: Normal Count is between 1 to 4 x10^9/L")
    @DecimalMin(value = "0.0", inclusive = false, message = "Lymphocyte Must Be Greater Than 0/L")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Lymphocyte Must Be Less Than or equal to 1000 x 10^9 /L!")
    private BigDecimal lymphocytes;

    @NotNull(message = "Monocyte Levels Is Required: Normal Count is between 200 - 800 monocytes per microliter of blood!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Monocyte Levels Must Be Greater Than 0mc/L")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Monocyte Levels Must Be Less Than or equal to 1000 x 10^6mc/L!")
    private BigDecimal monocytes;

    @NotNull(message = "RBC Count Is Required: Normal Count is between 0.00 - 300 Basophils Per Microliter (mcL)!")
    @DecimalMax(value = "1000.0", inclusive = true, message = "RBC Count Must Be Less Than or equal to 1000 x 10^6mcL!")
    private BigDecimal basophils;

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

    public HematologyRecord() {
    }

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRbcCount() {
		return rbcCount;
	}

	public void setRbcCount(BigDecimal rbcCount) {
		this.rbcCount = rbcCount;
	}

	public BigDecimal getWbcCount() {
		return wbcCount;
	}

	public void setWbcCount(BigDecimal wbcCount) {
		this.wbcCount = wbcCount;
	}

	public BigDecimal getPlateletCount() {
		return plateletCount;
	}

	public void setPlateletCount(BigDecimal plateletCount) {
		this.plateletCount = plateletCount;
	}

	public BigDecimal getHemoglobin() {
		return hemoglobin;
	}

	public void setHemoglobin(BigDecimal hemoglobin) {
		this.hemoglobin = hemoglobin;
	}

	public BigDecimal getHematocrit() {
		return hematocrit;
	}

	public void setHematocrit(BigDecimal hematocrit) {
		this.hematocrit = hematocrit;
	}

	public BigDecimal getNeutrophils() {
		return neutrophils;
	}

	public void setNeutrophils(BigDecimal neutrophils) {
		this.neutrophils = neutrophils;
	}

	public BigDecimal getLymphocytes() {
		return lymphocytes;
	}

	public void setLymphocytes(BigDecimal lymphocytes) {
		this.lymphocytes = lymphocytes;
	}

	public BigDecimal getMonocytes() {
		return monocytes;
	}

	public void setMonocytes(BigDecimal monocytes) {
		this.monocytes = monocytes;
	}

	public BigDecimal getBasophils() {
		return basophils;
	}

	public void setBasophils(BigDecimal basophils) {
		this.basophils = basophils;
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