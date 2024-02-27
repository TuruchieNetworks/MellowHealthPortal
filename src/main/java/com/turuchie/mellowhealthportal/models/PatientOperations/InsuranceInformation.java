package com.turuchie.mellowhealthportal.models.PatientOperations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.turuchie.mellowhealthportal.models.Administrations.InsuredPatients;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "insurance_information")
public class InsuranceInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please Enter Insurance Provider Name!")
    @Size(min = 3, max = 150, message = "Insurance Provider's Name must be between 3 and 150 Characters!")
    private String providerName;

    @NotBlank(message = "Please Enter Insurance Id!")
    @Size(min = 3, max = 50, message = "Invalid Insurance Id Entry!")
    private String insuranceId;

    @NotNull(message = "Please Enter Start Date!")
    private LocalDate startDate;

    @NotNull(message = "Please Enter Expiration Date!")
    private LocalDate expirationDate;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(mappedBy = "insuranceInformation")
    private List<PatientCase> patientCases;

    @OneToMany(mappedBy = "insuranceInformation")
    private List<InsuredPatients> insuredPatients;

    // Constructors, getters, setters, etc.
    public InsuranceInformation() {
    }

    public InsuranceInformation(Patient patient, String insuranceId, LocalDate startDate, LocalDate expirationDate, String providerName) {
        this.patient = patient;
        this.insuranceId = insuranceId;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.providerName = providerName;
    }

    public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	// Getters and setters
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

    public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public List<PatientCase> getPatientCases() {
		return patientCases;
	}

	public void setPatientCases(List<PatientCase> patientCases) {
		this.patientCases = patientCases;
	}

	public List<InsuredPatients> getInsuredPatients() {
		return insuredPatients;
	}

	public void setInsuredPatients(List<InsuredPatients> insuredPatients) {
		this.insuredPatients = insuredPatients;
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
