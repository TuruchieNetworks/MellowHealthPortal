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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "hematology_records")
public class HematologyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Red Blood Cell (RBC) Cannot Be Blank!")
    private BigDecimal rbcCount;

    @NotBlank(message = "White Blood Cell (WBC) Count Cannot Be Blank!")
    private BigDecimal wbcCount;

    @NotBlank(message = "Platelet Count Cannot Be Blank!")
    private BigDecimal plateletCount;

    @NotBlank(message = "Hemoglobin (Hgb) Cannot Be Blank!")
    private BigDecimal hemoglobin;

    @NotBlank(message = "Hematocrit (Hct) Cannot Be Blank!")
    private BigDecimal hematocrit;

    @NotBlank(message = "Neutrophil Count Cannot Be Blank!")
    private BigDecimal neutrophils;

    @NotBlank(message = "Lymphocyte Count Cannot Be Blank!")
    private BigDecimal lymphocytes;

    @NotBlank(message = "Monocyte Count Cannot Be Blank!")
    private BigDecimal monocytes;

    @NotBlank(message = "Basophil Cannot Be Blank!")
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