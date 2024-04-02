package com.turuchie.mellowhealthportal.models.ClinicalOperations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.AbdominalAssessment;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.NauseaVomitAssessment;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PatientVisitEvaluation;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PediatricFeverAssessment;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PediatricGIAssessment;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.models.Physicians.Physician;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @JoinColumn(name = "physician_id")
    @NotNull(message = "Please Select Physician!")
    private Physician physician;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    @NotNull(message = "Patient Case Is Required!")
    private PatientCase patientCase;

    @OneToMany(mappedBy = "patientVital", cascade = CascadeType.ALL)
    private List<AbdominalAssessment> abdominalPainAssessments;

    @OneToMany(mappedBy = "patientVital", cascade = CascadeType.ALL)
    private List<NauseaVomitAssessment> nauseaVomitAssessments;

    @OneToMany(mappedBy = "patientVital", cascade = CascadeType.ALL)
    private List<PediatricFeverAssessment> pediatricFeverAssessments;    

    @OneToMany(mappedBy = "patientVital", cascade = CascadeType.ALL)
    private List<PediatricGIAssessment> pediatricGIAssessments;   

    @OneToMany(mappedBy = "patientVital", cascade = CascadeType.ALL)
    private List<PatientVisitEvaluation> patientVisitEvaluations; 
 
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

	public Physician getPhysician() {
		return physician;
	}

	public void setPhysician(Physician physician) {
		this.physician = physician;
	}

	public void setPatientCase(PatientCase patientCase) {
		this.patientCase = patientCase;
	}

	public List<AbdominalAssessment> getAbdominalPainAssessments() {
		return abdominalPainAssessments;
	}

	public void setAbdominalPainAssessments(List<AbdominalAssessment> abdominalPainAssessments) {
		this.abdominalPainAssessments = abdominalPainAssessments;
	}

	public List<NauseaVomitAssessment> getNauseaVomitAssessments() {
		return nauseaVomitAssessments;
	}

	public List<PediatricFeverAssessment> getPediatricFeverAssessments() {
		return pediatricFeverAssessments;
	}

	public void setPediatricFeverAssessments(List<PediatricFeverAssessment> pediatricFeverAssessments) {
		this.pediatricFeverAssessments = pediatricFeverAssessments;
	}

	public List<PediatricGIAssessment> getPediatricGIAssessments() {
		return pediatricGIAssessments;
	}

	public void setPediatricGIAssessments(List<PediatricGIAssessment> pediatricGIAssessments) {
		this.pediatricGIAssessments = pediatricGIAssessments;
	}

	public void setNauseaVomitAssessments(List<NauseaVomitAssessment> nauseaVomitAssessments) {
		this.nauseaVomitAssessments = nauseaVomitAssessments;
	}

	public List<PatientVisitEvaluation> getPatientVisitEvaluations() {
		return patientVisitEvaluations;
	}

	public void setPatientVisitEvaluations(List<PatientVisitEvaluation> patientVisitEvaluations) {
		this.patientVisitEvaluations = patientVisitEvaluations;
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

