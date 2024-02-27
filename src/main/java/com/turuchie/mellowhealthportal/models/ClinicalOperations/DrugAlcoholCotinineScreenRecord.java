package com.turuchie.mellowhealthportal.models.ClinicalOperations;

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
@Table(name = "drug_alcohol_cotinine_screen_records")
public class DrugAlcoholCotinineScreenRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alcohol")
    @NotNull(message = "Please Confirm Status For Alcohol!")
    private Boolean alcohol;

    @NotNull(message = "Please Confirm Status For Amphetamine!")
    @Column(name = "amphetamine")
    private Boolean amphetamine;

    @Column(name = "barbiturates")
    @NotNull(message = "Please Confirm Status For Barbiturates!")
    private Boolean barbiturates;

    @Column(name = "benzodiazepines")
    @NotNull(message = "Please Confirm Status For Benzodiazepines!")
    private Boolean benzodiazepines;

    @Column(name = "cannabinoids")
    @NotNull(message = "Please Confirm Status For Cannabinoids!")
    private Boolean cannabinoids;

    @Column(name = "cocaine")
    @NotNull(message = "Please Confirm Status For Cocaine!")
    private Boolean cocaine;

    @Column(name = "cotinine")
    @NotNull(message = "Please Confirm Status For Cotinine!")
    private Boolean cotinine;

    @Column(name = "opiates")
    @NotNull(message = "Please Confirm Status For Opiates!")
    private Boolean opiates;

    @Column(name = "phencyclidine")
    @NotNull(message = "Please Confirm Status For Phencyclidines!")
    private Boolean phencyclidine;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    @NotNull(message = "Please Select Patient Case!")
    private PatientCase patientCase;

    public DrugAlcoholCotinineScreenRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(Boolean alcohol) {
        this.alcohol = alcohol;
    }

    public Boolean getAmphetamine() {
        return amphetamine;
    }

    public void setAmphetamine(Boolean amphetamine) {
        this.amphetamine = amphetamine;
    }

    public Boolean getBarbiturates() {
        return barbiturates;
    }

    public void setBarbiturates(Boolean barbiturates) {
        this.barbiturates = barbiturates;
    }

    public Boolean getBenzodiazepines() {
        return benzodiazepines;
    }

    public void setBenzodiazepines(Boolean benzodiazepines) {
        this.benzodiazepines = benzodiazepines;
    }

    public Boolean getCannabinoids() {
        return cannabinoids;
    }

    public void setCannabinoids(Boolean cannabinoids) {
        this.cannabinoids = cannabinoids;
    }

    public Boolean getCocaine() {
        return cocaine;
    }

    public void setCocaine(Boolean cocaine) {
        this.cocaine = cocaine;
    }

    public Boolean getCotinine() {
        return cotinine;
    }

    public void setCotinine(Boolean cotinine) {
        this.cotinine = cotinine;
    }

    public Boolean getOpiates() {
        return opiates;
    }

    public void setOpiates(Boolean opiates) {
        this.opiates = opiates;
    }

    public Boolean getPhencyclidine() {
        return phencyclidine;
    }

    public void setPhencyclidine(Boolean phencyclidine) {
        this.phencyclidine = phencyclidine;
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