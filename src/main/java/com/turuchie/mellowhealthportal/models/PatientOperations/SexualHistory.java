package com.turuchie.mellowhealthportal.models.PatientOperations;

import java.time.LocalDate;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "sexualHistories")
public class SexualHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Sexual activity
    @Column(name = "sexually_active")
    @NotNull(message = "Please select sexual activity status")
    private Boolean sexuallyActive;

    @Column(name = "condom_use")
    @NotBlank(message = "Please describe condom use (always, occasionally, none)")
    private String condomUse;

    @Column(name = "sexual_partners")
    @NotBlank(message = "Please describe sexual partners (men, women, both)")
    private String sexualPartners;

    @Column(name = "partner_details")
    @NotBlank(message = "Please provide details about sexual partner or partners")
    private String partnerDetails;

    @Column(name = "partners_last_year")
    @Min(value = 0, message = "Number of sexual partners in the past year must be greater than or equal to 0")
    private Integer partnersLastYear;

    @Column(name = "current_partners")
    @Min(value = 0, message = "Number of current sexual partners must be greater than or equal to 0")
    private Integer currentPartners;

    @Column(name = "std_history")
    @NotBlank(message = "Please describe STD history")
    private String stdHistory;

    @Column(name = "sexual_function_problems")
    @NotBlank(message = "Please describe problems with sexual function")
    private String sexualFunctionProblems;

    @Column(name = "erection_problems")
    @NotBlank(message = "Please describe problems with erections")
    private String erectionProblems;

    @Column(name = "contraception_use")
    @NotBlank(message = "Please describe contraception use")
    private String contraceptionUse;

    @Column(name = "hiv_tested")
    @NotNull(message = "Please select HIV tested status")
    private Boolean hivTested;

    // ObGyn history
    @Column(name = "first_menstrual_period_age")
    @Min(value = 0, message = "Age of first menstrual period must be greater than or equal to 0")
    private Integer firstMenstrualPeriodAge;

    @Column(updatable = false)
    private LocalDate createdAt;
    
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id", nullable = false)
    private PatientCase patientCase;

    public SexualHistory() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getSexuallyActive() {
		return sexuallyActive;
	}

	public void setSexuallyActive(Boolean sexuallyActive) {
		this.sexuallyActive = sexuallyActive;
	}

	public String getCondomUse() {
		return condomUse;
	}

	public void setCondomUse(String condomUse) {
		this.condomUse = condomUse;
	}

	public String getSexualPartners() {
		return sexualPartners;
	}

	public void setSexualPartners(String sexualPartners) {
		this.sexualPartners = sexualPartners;
	}

	public String getPartnerDetails() {
		return partnerDetails;
	}

	public void setPartnerDetails(String partnerDetails) {
		this.partnerDetails = partnerDetails;
	}

	public Integer getPartnersLastYear() {
		return partnersLastYear;
	}

	public void setPartnersLastYear(Integer partnersLastYear) {
		this.partnersLastYear = partnersLastYear;
	}

	public Integer getCurrentPartners() {
		return currentPartners;
	}

	public void setCurrentPartners(Integer currentPartners) {
		this.currentPartners = currentPartners;
	}

	public String getStdHistory() {
		return stdHistory;
	}

	public void setStdHistory(String stdHistory) {
		this.stdHistory = stdHistory;
	}

	public String getSexualFunctionProblems() {
		return sexualFunctionProblems;
	}

	public void setSexualFunctionProblems(String sexualFunctionProblems) {
		this.sexualFunctionProblems = sexualFunctionProblems;
	}

	public String getErectionProblems() {
		return erectionProblems;
	}

	public void setErectionProblems(String erectionProblems) {
		this.erectionProblems = erectionProblems;
	}

	public String getContraceptionUse() {
		return contraceptionUse;
	}

	public void setContraceptionUse(String contraceptionUse) {
		this.contraceptionUse = contraceptionUse;
	}

	public Boolean getHivTested() {
		return hivTested;
	}

	public void setHivTested(Boolean hivTested) {
		this.hivTested = hivTested;
	}

	public Integer getFirstMenstrualPeriodAge() {
		return firstMenstrualPeriodAge;
	}

	public void setFirstMenstrualPeriodAge(Integer firstMenstrualPeriodAge) {
		this.firstMenstrualPeriodAge = firstMenstrualPeriodAge;
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

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }}
