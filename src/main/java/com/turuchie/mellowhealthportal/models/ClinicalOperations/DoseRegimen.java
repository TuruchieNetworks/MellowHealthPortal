package com.turuchie.mellowhealthportal.models.ClinicalOperations;

import java.time.LocalDateTime;
import java.util.List;

import com.turuchie.mellowhealthportal.models.PatientOperations.AdverseEffect;
import com.turuchie.mellowhealthportal.models.PatientOperations.CurrentMedication;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;

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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "dose_regimen")
public class DoseRegimen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please enter the drug name!")
    private String drugName;

    @NotNull(message = "Please enter the pharmaceutical form!")
    private String pharmaceuticalForm;

    @NotNull(message = "Please enter the dose!")
    private String dose;

    @NotNull(message = "Please enter the frequency and/or regimen!")
    private String frequencyAndRegimen;

    @NotNull(message = "Please select the capsule type!")
    private boolean isOralCapsule;

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

    @ManyToOne
    @JoinColumn(name = "currentMedication_id")
    private CurrentMedication currentMedication;

    @OneToMany(mappedBy = "doseRegimen", cascade = CascadeType.ALL)
    private List<AdverseEffect> adverseEffects;

    public DoseRegimen() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getPharmaceuticalForm() {
        return pharmaceuticalForm;
    }

    public void setPharmaceuticalForm(String pharmaceuticalForm) {
        this.pharmaceuticalForm = pharmaceuticalForm;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getFrequencyAndRegimen() {
        return frequencyAndRegimen;
    }

    public void setFrequencyAndRegimen(String frequencyAndRegimen) {
        this.frequencyAndRegimen = frequencyAndRegimen;
    }

    public boolean isOralCapsule() {
        return isOralCapsule;
    }

    public void setOralCapsule(boolean oralCapsule) {
        isOralCapsule = oralCapsule;
    }

	public CurrentMedication getCurrentMedication() {
		return currentMedication;
	}

	public void setCurrentMedication(CurrentMedication currentMedication) {
		this.currentMedication = currentMedication;
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

	public List<AdverseEffect> getAdverseEffects() {
		return adverseEffects;
	}

	public void setAdverseEffects(List<AdverseEffect> adverseEffects) {
		this.adverseEffects = adverseEffects;
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