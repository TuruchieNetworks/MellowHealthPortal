package com.turuchie.mellowhealthportal.models.DiagnosticProcedures;

import java.time.LocalDateTime;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.models.Physicians.Physician;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "painAssessments")
public class PainAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "has_pain")
    @NotNull(message = "Please Enter Pain Status!")
    private Boolean hasPain;

    @Column(name = "duration")
    @NotBlank(message = "Please Enter Pain Duration!")
    @Size(min = 1, max = 255, message = "Invalid Entry, Duration Must Be Between 1 and 255 Characters!")
    private String duration;

    @Column(name = "frequency")
    @NotBlank(message = "Please Enter Pain Frequency!")
    @Size(min = 1, max = 255, message = "Invalid Entry, Frequency Must Be Between 1 and 255 Characters!")
    private String frequency;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "location")
    @NotBlank(message = "Please Enter Pain Location!")
    @Size(min = 1, max = 255, message = "Invalid Entry, Location Must Be Between 1 and 255 Characters!")
    private String location;

    @Column(name = "travel_location")
    private String travelLocation;

    @Column(name = "description")
    @NotBlank(message = "Please Enter Pain Description!")
    @Size(min = 1, max = 255, message = "Invalid Entry, Description Must Be Between 1 and 255 Characters!")
    private String description;

    @Column(name = "characteristics")
    @NotBlank(message = "Please Enter Pain Characteristics!")
    @Size(min = 1, max = 255, message = "Invalid Entry, Characteristics Must Be Between 1 and 255 Characters!")
    private String characteristics;

    @Column(name = "pain_scale")
    @NotNull(message = "Please Tell Us How You Feel On A Scale Of 1 - 10")
    @Min(value = 0, message = "Pain Scale Must Be Greater Than or Equal to 0")
    @Max(value = 10, message = "Pain Scale Must Be Less Than or Equal to 10")
    private Integer painScale;

    @Column(name = "trigger_factor")
    @NotBlank(message = "Please Enter Any Pain Triggering Effect, Enter Null If Unavailable!")
    @Size(min = 1, max = 255, message = "Invalid Entry, Characteristics Must Be Between 1 and 255 Characters!")
    private String triggerFactor;

    @Column(name = "relieving_factor")
    @NotBlank(message = "Please Enter Any Relieving Factor, Enter Null If Unavailable!")
    @Size(min = 1, max = 255, message = "Invalid Entry, Relieving Factors Must Be Between 1 and 255 Characters!")
    private String relievingFactor;

    @Column(name = "aggravating_factor")
    @NotBlank(message = "Please Enter Any Aggravating Factor, Enter Null If Unavailable!")
    @Size(min = 1, max = 255, message = "Invalid Entry, Aggravating Factors Must Be Between 1 and 255 Characters!")
    private String aggravatingFactor;

    @Column(name = "previous_episode")
    @NotNull(message = "Please Verify Previous episode!")
    private Boolean previousEpisode;

    @Column(name = "related_symptoms")
    @NotBlank(message = "Please Enter Any Related Symptoms, Enter Null If Unavailable!")
    @Size(min = 1, max = 255, message = "Invalid Entry, Related Symptoms Must Be Between 1 and 255 Characters!")
    private String relatedSymptoms;

    @Column(name = "difficulty_urinating")
    @NotNull(message = "Please specify if there is difficulty urinating.")
    private Boolean difficultyUrinating;

    @Column(name = "urinary_incontinence")
    @NotNull(message = "Please specify if there is urinary incontinence.")
    private Boolean urinaryIncontinence;

    @Column(name = "fecal_incontinence")
    @NotNull(message = "Please specify if there is fecal incontinence.")
    private Boolean fecalIncontinence;

    @Column(name = "fever_night_sweats_weight_loss")
    @NotNull(message = "Please specify if there are fever, night sweats, or weight loss.")
    private Boolean feverNightSweatsWeightLoss;

    @Column(name = "history_of_back_pain")
    @NotNull(message = "Please specify if there is a history of back pain.")
    private Boolean historyOfBackPain;

    @Column(name = "illicit_drug_use")
    @NotNull(message = "Please specify if there is illicit drug use.")
    private Boolean illicitDrugUse;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "physicalAssessment_id")
    @NotNull(message = "Please Enter Referred Physical Assessement!")
    private PhysicalAssessment physicalAssessment;

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
    

    private PainAssessment() {}

    // Constructors, getters, setters, etc.
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getHasPain() {
		return hasPain;
	}

	public void setHasPain(Boolean hasPain) {
		this.hasPain = hasPain;
	}

	public Boolean getPreviousEpisode() {
		return previousEpisode;
	}

	public void setPreviousEpisode(Boolean previousEpisode) {
		this.previousEpisode = previousEpisode;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTravelLocation() {
		return travelLocation;
	}

	public void setTravelLocation(String travelLocation) {
		this.travelLocation = travelLocation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public Integer getPainScale() {
		return painScale;
	}

	public void setPainScale(Integer painScale) {
		this.painScale = painScale;
	}

	public String getTriggerFactor() {
		return triggerFactor;
	}

	public void setTriggerFactor(String triggerFactor) {
		this.triggerFactor = triggerFactor;
	}

	public String getRelievingFactor() {
		return relievingFactor;
	}

	public void setRelievingFactor(String relievingFactor) {
		this.relievingFactor = relievingFactor;
	}

	public String getAggravatingFactor() {
		return aggravatingFactor;
	}

	public void setAggravatingFactor(String aggravatingFactor) {
		this.aggravatingFactor = aggravatingFactor;
	}

	public boolean isPreviousEpisode() {
		return previousEpisode;
	}

	public void setPreviousEpisode(boolean previousEpisode) {
		this.previousEpisode = previousEpisode;
	}

	public String getRelatedSymptoms() {
		return relatedSymptoms;
	}

	public void setRelatedSymptoms(String relatedSymptoms) {
		this.relatedSymptoms = relatedSymptoms;
	}

    public Boolean getDifficultyUrinating() {
		return difficultyUrinating;
	}

	public void setDifficultyUrinating(Boolean difficultyUrinating) {
		this.difficultyUrinating = difficultyUrinating;
	}

	public Boolean getUrinaryIncontinence() {
		return urinaryIncontinence;
	}

	public void setUrinaryIncontinence(Boolean urinaryIncontinence) {
		this.urinaryIncontinence = urinaryIncontinence;
	}

	public Boolean getFecalIncontinence() {
		return fecalIncontinence;
	}

	public void setFecalIncontinence(Boolean fecalIncontinence) {
		this.fecalIncontinence = fecalIncontinence;
	}

	public Boolean getFeverNightSweatsWeightLoss() {
		return feverNightSweatsWeightLoss;
	}

	public void setFeverNightSweatsWeightLoss(Boolean feverNightSweatsWeightLoss) {
		this.feverNightSweatsWeightLoss = feverNightSweatsWeightLoss;
	}

	public Boolean getHistoryOfBackPain() {
		return historyOfBackPain;
	}

	public void setHistoryOfBackPain(Boolean historyOfBackPain) {
		this.historyOfBackPain = historyOfBackPain;
	}

	public Boolean getIllicitDrugUse() {
		return illicitDrugUse;
	}

	public void setIllicitDrugUse(Boolean illicitDrugUse) {
		this.illicitDrugUse = illicitDrugUse;
	}

	public PhysicalAssessment getPhysicalAssessment() {
		return physicalAssessment;
	}

	public void setPhysicalAssessment(PhysicalAssessment physicalAssessment) {
		this.physicalAssessment = physicalAssessment;
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

