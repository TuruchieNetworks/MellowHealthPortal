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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "hepatitis_screen_records")
public class HepatitisScreenRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "antihcv", precision = 38, scale = 2)
    private BigDecimal antihcv;

    @Column(name = "hbs_ag", precision = 38, scale = 2)
    private BigDecimal hbsAg;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    @NotNull(message = "Please Select Case ID!")
    private PatientCase patientCase;

    public HepatitisScreenRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAntihcv() {
        return antihcv;
    }

    public void setAntihcv(BigDecimal antihcv) {
        this.antihcv = antihcv;
    }

    public BigDecimal getHbsAg() {
        return hbsAg;
    }

    public void setHbsAg(BigDecimal hbsAg) {
        this.hbsAg = hbsAg;
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
