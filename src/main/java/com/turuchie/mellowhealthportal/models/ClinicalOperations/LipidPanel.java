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
@Table(name = "lipid_panels")
public class LipidPanel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @DecimalMin(value = "0.0", inclusive = false, message = "Hdl Must Be Greater Than 0")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Hdl Must Be Less Than or equal to 1000")
    @Column(name = "hdl_cholesterol", precision = 38, scale = 2)
    private BigDecimal hdlCholesterol;


    @DecimalMin(value = "0.0", inclusive = false, message = "Ldl Must Be Greater Than 0")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Ldl Must Be Less Than or equal to 1000")
    @Column(name = "ldl_cholesterol", precision = 38, scale = 2)
    private BigDecimal ldlCholesterol;


    @DecimalMin(value = "0.0", inclusive = false, message = "Total Cholesterol Must Be Greater Than 0")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Total Cholesteorl Must Be Less Than or equal to 100")
    @Column(name = "total_cholesterol", precision = 38, scale = 2)
    private BigDecimal totalCholesterol;

    @DecimalMin(value = "0.0", inclusive = false, message = "Triglycerides Must Be Greater Than 0")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Triglycerides Must Be LessThan or equal to 100")
    @Column(name = "triglycerides", precision = 38, scale = 2)
    private BigDecimal triglycerides;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    @NotNull(message = "Please Select Case ID!")
    private PatientCase patientCase;

    public LipidPanel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getHdlCholesterol() {
        return hdlCholesterol;
    }

    public void setHdlCholesterol(BigDecimal hdlCholesterol) {
        this.hdlCholesterol = hdlCholesterol;
    }

    public BigDecimal getLdlCholesterol() {
        return ldlCholesterol;
    }

    public void setLdlCholesterol(BigDecimal ldlCholesterol) {
        this.ldlCholesterol = ldlCholesterol;
    }

    public BigDecimal getTotalCholesterol() {
        return totalCholesterol;
    }

    public void setTotalCholesterol(BigDecimal totalCholesterol) {
        this.totalCholesterol = totalCholesterol;
    }

    public BigDecimal getTriglycerides() {
        return triglycerides;
    }

    public void setTriglycerides(BigDecimal triglycerides) {
        this.triglycerides = triglycerides;
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

	@PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
