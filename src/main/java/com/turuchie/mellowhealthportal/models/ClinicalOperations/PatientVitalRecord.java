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
@Table(name = "patient_vital_records")
public class PatientVitalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please Enter Systolic Blood Pressure!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Systolic Blood Pressure Must Be Greater Than 0")
    private Integer systolicBloodPressure;

    @NotNull(message = "Please Enter Diastolic Blood Pressure!")
    private Integer diastolicBloodPressure;

    @NotNull(message = "Heart Rate cannot be null!")
    private Integer heartRate;

    @NotNull(message = "Respiratory Rate cannot be null!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Respiratory Rate Must Be Greater Than 0")
    private BigDecimal respiratoryRate;

    @NotNull(message = "Pulse Rate cannot be null!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Pulse Rate Must Be Greater Than 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Pulse Rate Must Be LessThan or equal to 100")
    private BigDecimal pulseRate;

    @NotNull(message = "Body Temperature cannot be null!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Body Temperature Must Be Greater Than 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Body Temperature Must Be LessThan or equal to 100")
    private BigDecimal bodyTemperature;

    @NotNull(message = "Height cannot be null!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Height Must Be Greater Than 0")
    @DecimalMax(value = "500.0", inclusive = true, message = "Height Must Be LessThan or equal to 500")
    private BigDecimal height;

    @NotNull(message = "Weight cannot be null!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Weight Must Be Greater Than 0")
    @DecimalMax(value = "500.0", inclusive = true, message = "Weight Must Be LessThan or equal to 500")
    private BigDecimal weight;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @NotNull(message = "Patient Is Required!")
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    @NotNull(message = "Patient Case Is Required!")
    private PatientCase patientCase;

    public PatientVitalRecord() {
    }
 
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Integer getSystolicBloodPressure() {
		return systolicBloodPressure;
	}

	public void setSystolicBloodPressure(Integer systolicBloodPressure) {
		this.systolicBloodPressure = systolicBloodPressure;
	}

	public Integer getDiastolicBloodPressure() {
		return diastolicBloodPressure;
	}

	public void setDiastolicBloodPressure(Integer diastolicBloodPressure) {
		this.diastolicBloodPressure = diastolicBloodPressure;
	}

	public Integer getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(Integer heartRate) {
		this.heartRate = heartRate;
	}

	public BigDecimal getRespiratoryRate() {
		return respiratoryRate;
	}

	public void setRespiratoryRate(BigDecimal respiratoryRate) {
		this.respiratoryRate = respiratoryRate;
	}

	public BigDecimal getPulseRate() {
		return pulseRate;
	}

	public void setPulseRate(BigDecimal pulseRate) {
		this.pulseRate = pulseRate;
	}

	public BigDecimal getBodyTemperature() {
		return bodyTemperature;
	}

	public void setBodyTemperature(BigDecimal bodyTemperature) {
		this.bodyTemperature = bodyTemperature;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
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

