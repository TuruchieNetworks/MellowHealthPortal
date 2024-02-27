package com.turuchie.mellowhealthportal.models.ClinicalOperations;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "bioequivalence_analysis")
public class BioequivalenceAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please Treatment Period!") 
    @DecimalMin(value = "0.0", inclusive = false, message = "Treatment Period must be greater than 0")
    @Column(name = "treatment_period")
    private int treatmentPeriod;


    @NotNull(message = "Please Enter Day Count!") 
    @DecimalMin(value = "0.0", inclusive = false, message = "Day Count Must be greater than 0")
    @Column(name = "day")
    private int day;


    @NotNull(message = "Please Enter Scheduled Time Point!") 
    @DecimalMin(value = "0.0", inclusive = false, message = "Scheduled Time Point must be greater than 0")
    @Column(name = "scheduled_time_point")
    private String scheduledTimePoint;

    @NotNull(message = "Please Enter PK Collection Number!") 
    @DecimalMin(value = "0.0", inclusive = false, message = "PK Collection Number must be greater than 0")
    @Column(name = "pk_collection_no")
    private int pkCollectionNo;

    @NotNull(message = "Please Enter PK Sample Number!") 
    @DecimalMin(value = "0.0", inclusive = false, message = "PK Sample Number must be greater than 0")
    @Column(name = "pk_sample_no")
    private int pkSampleNo;

    @NotNull(message = "Please Enter Blood Volume!") 
    @DecimalMin(value = "0.0", inclusive = false, message = "Blood Volume must be greater than 0 mL")
    @Column(name = "blood_volume_ml")
    private int bloodVolumeMl;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patientCase_id")
    @NotNull(message = "Please Select Visit!")
    private PatientCase patientCase;

    public BioequivalenceAnalysis() {
    }
 
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public int getTreatmentPeriod() {
		return treatmentPeriod;
	}

	public void setTreatmentPeriod(int treatmentPeriod) {
		this.treatmentPeriod = treatmentPeriod;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getScheduledTimePoint() {
		return scheduledTimePoint;
	}

	public void setScheduledTimePoint(String scheduledTimePoint) {
		this.scheduledTimePoint = scheduledTimePoint;
	}

	public int getPkCollectionNo() {
		return pkCollectionNo;
	}

	public void setPkCollectionNo(int pkCollectionNo) {
		this.pkCollectionNo = pkCollectionNo;
	}

	public int getPkSampleNo() {
		return pkSampleNo;
	}

	public void setPkSampleNo(int pkSampleNo) {
		this.pkSampleNo = pkSampleNo;
	}

	public int getBloodVolumeMl() {
		return bloodVolumeMl;
	}

	public void setBloodVolumeMl(int bloodVolumeMl) {
		this.bloodVolumeMl = bloodVolumeMl;
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