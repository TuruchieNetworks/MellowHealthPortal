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
@Table(name = "InclusionCriteria")
public class InclusionCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please Consent To Research Protocol!")
    private Boolean writtenInformedConsent;

    @NotNull(message = "Age Is Required!")
    @DecimalMin(value = "0", message = "Age must be a positive number")
    private Integer age;


    @NotNull(message = "Please Select Status!")
    private Boolean postmenopausal;

    @NotNull(message = "Please Confirm Status!")
    private Boolean vitalSignsWithinNormalRanges;

    @DecimalMin(value = "0.0", message = "Weight must be greater than 0")
    private BigDecimal weight;

    @DecimalMin(value = "0.0", message = "BMI must be greater than 0")
    @DecimalMax(value = "100.0", message = "BMI must be less than or equal to 100")
    private BigDecimal bmi;

    @NotNull(message = "Please Confirm Oral Status!")
    private Boolean ableToSwallowCapsulesAndTablets;

    @NotNull(message = "Please Confirm Auditory Status!")
    private Boolean ableToCommunicateWell;

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

    public InclusionCriteria() {
    }
 
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
 
    public Boolean getWrittenInformedConsent() {
		return writtenInformedConsent;
	}

	public void setWrittenInformedConsent(Boolean writtenInformedConsent) {
		this.writtenInformedConsent = writtenInformedConsent;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Boolean getPostmenopausal() {
		return postmenopausal;
	}

	public void setPostmenopausal(Boolean postmenopausal) {
		this.postmenopausal = postmenopausal;
	}

	public Boolean getVitalSignsWithinNormalRanges() {
		return vitalSignsWithinNormalRanges;
	}

	public void setVitalSignsWithinNormalRanges(Boolean vitalSignsWithinNormalRanges) {
		this.vitalSignsWithinNormalRanges = vitalSignsWithinNormalRanges;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getBmi() {
		return bmi;
	}

	public void setBmi(BigDecimal bmi) {
		this.bmi = bmi;
	}

	public Boolean getAbleToSwallowCapsulesAndTablets() {
		return ableToSwallowCapsulesAndTablets;
	}

	public void setAbleToSwallowCapsulesAndTablets(Boolean ableToSwallowCapsulesAndTablets) {
		this.ableToSwallowCapsulesAndTablets = ableToSwallowCapsulesAndTablets;
	}

	public Boolean getAbleToCommunicateWell() {
		return ableToCommunicateWell;
	}

	public void setAbleToCommunicateWell(Boolean ableToCommunicateWell) {
		this.ableToCommunicateWell = ableToCommunicateWell;
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