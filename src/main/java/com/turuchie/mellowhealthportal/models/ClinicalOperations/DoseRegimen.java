package com.turuchie.mellowhealthportal.models.ClinicalOperations;

import java.time.LocalDateTime;
import java.util.List;

import com.turuchie.mellowhealthportal.models.PatientOperations.AdverseEffect;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "dose_regimen")
public class DoseRegimen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please enter the drug name!")
    @Size(min = 3, max = 150, message = "Drug Name Must Be Between 3 and 150 Characters!")
    private String drugName;

    @NotBlank(message = "Please enter the pharmaceutical form!")
    @Size(min = 3, max = 150, message = "Pharmaceutical Form Must Be Between 3 and 150 Characters!")
    private String pharmaceuticalForm;

    @NotBlank(message = "Please enter the dose!")
    @Size(min = 3, max = 150, message = "Dose Must Be Between 3 and 150 Characters!")
    private String dose;

    @NotBlank(message = "Please enter the frequency and/or regimen!")
    @Size(min = 3, max = 150, message = "Freqency Must Be Between 3 and 150 Characters!")
    private String frequencyAndRegimen;

    @NotNull(message = "Please select the capsule type!")
    private Boolean isOralCapsule;

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

    @ManyToOne
    @JoinColumn(name = "physician_id")
    @NotNull(message = "Please Select Physician ID!")
    private Physician physician;

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

	public Boolean getIsOralCapsule() {
		return isOralCapsule;
	}

	public void setIsOralCapsule(Boolean isOralCapsule) {
		this.isOralCapsule = isOralCapsule;
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

	public Physician getPhysician() {
		return physician;
	}

	public void setPhysician(Physician physician) {
		this.physician = physician;
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