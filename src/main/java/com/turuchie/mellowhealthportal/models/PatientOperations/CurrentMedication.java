package com.turuchie.mellowhealthportal.models.PatientOperations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.DoseRegimen;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "current_medications")
public class CurrentMedication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "drug_name", length = 255)
    @NotBlank(message = "Please Enter The Name Of Pharmaceutical Agent!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Drug Name Must Be In Pharmaceutical Form, Enter Conventional Market Name If Unknown!")
    private String drugName;

	@Column(name = "active_ingredient", length = 255)
	@NotBlank(message = "Please Enter the Active Ingredient Eg: Acetaminophine for Tylenol!")
	private String activeIngredient;

	// Using String for detailed dosage information
	@Column(name = "dosage", length = 255)    
	@Min(value = 0, message = "Dosage Amount Must Be Greater Than 0")
	@NotBlank(message = "Please Enter the Dosage Amount!")
	private String dosage;

	// Using Integer for a simpler representation
	@Column(name = "dosage_quantity")
	@NotNull(message = "Please Enter the Dosage Quantity!")
	private Integer dosageQuantity;

	// You can have additional columns to represent dosage frequency, e.g., per day, per 6 hours, etc.
	@Column(name = "dosage_frequency")
    @NotBlank(message = "Please Enter Frequency Information!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Enter Null If Unknown!")
	private String dosageFrequency;

	@Column(name = "dosage_frequency_per_hour")
    @NotBlank(message = "Please Enter Hourly Frequency Information!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Enter Null If Unknown!")
	private Integer dosageFrequencyPerHour;  

    @Column(name = "administration_route", length = 255)
    @NotNull(message = "Please Enter Route Of Administration!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Please Enter Oral, Subdermal, IV, or Null If Unknown!")
    private String administrationRoute;

    @Column(name = "frequency", length = 255)
    @NotNull(message = "Please Provide Dosage Frequency!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Please Enter Frequency Of Dose!")
    private String frequency;

    @Column(name = "regimen", length = 255)
    @NotNull(message = "Please Enter Regimen!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Please Enter Correct Regimen!")
    private String regimen;

    @Column(name = "pharmaceutical_form", length = 255)
    @Size(min = 3, max = 255, message = "Invalid Entry, Please Enter Tablet, Subdermal, IV, or Null If Unknown!")
    private String pharmaceuticalForm;
    
    @NotNull(message = "Please Enter Start Date!")
    private LocalDate startDate;
    
    @NotNull(message = "Please Enter Current Medication Status!")
    private Boolean currentlyTakingMedicine;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    private PatientCase patientCase;

    @OneToMany(mappedBy = "currentMedication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoseRegimen> doseRegimenRecords;

    @OneToMany(mappedBy = "currentMedication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdverseEffect> adverseEffects;

    public CurrentMedication() {
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdministrationRoute() {
		return administrationRoute;
	}

	public void setAdministrationRoute(String administrationRoute) {
		this.administrationRoute = administrationRoute;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getActiveIngredient() {
		return activeIngredient;
	}

	public void setActiveIngredient(String activeIngredient) {
		this.activeIngredient = activeIngredient;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public Integer getDosageQuantity() {
		return dosageQuantity;
	}

	public void setDosageQuantity(Integer dosageQuantity) {
		this.dosageQuantity = dosageQuantity;
	}

	public String getDosageFrequency() {
		return dosageFrequency;
	}

	public void setDosageFrequency(String dosageFrequency) {
		this.dosageFrequency = dosageFrequency;
	}

	public Integer getDosageFrequencyPerHour() {
		return dosageFrequencyPerHour;
	}

	public void setDosageFrequencyPerHour(Integer dosageFrequencyPerHour) {
		this.dosageFrequencyPerHour = dosageFrequencyPerHour;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getRegimen() {
		return regimen;
	}

	public void setRegimen(String regimen) {
		this.regimen = regimen;
	}

	public String getPharmaceuticalForm() {
		return pharmaceuticalForm;
	}

	public void setPharmaceuticalForm(String pharmaceuticalForm) {
		this.pharmaceuticalForm = pharmaceuticalForm;
	}

	public Boolean getCurrentlyTakingMedicine() {
		return currentlyTakingMedicine;
	}

	public void setCurrentlyTakingMedicine(Boolean currentlyTakingMedicine) {
		this.currentlyTakingMedicine = currentlyTakingMedicine;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public List<DoseRegimen> getDoseRegimenRecords() {
		return doseRegimenRecords;
	}

	public void setDoseRegimenRecords(List<DoseRegimen> doseRegimenRecords) {
		this.doseRegimenRecords = doseRegimenRecords;
	}

	public List<AdverseEffect> getAdverseEffects() {
		return adverseEffects;
	}

	public void setAdverseEffects(List<AdverseEffect> adverseEffects) {
		this.adverseEffects = adverseEffects;
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

	// Getters and setters
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
