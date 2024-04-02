package com.turuchie.mellowhealthportal.models.PatientOperations;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.Physicians.Physician;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "incident_reports")
public class IncidentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please Describe New Event!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Enter Null If Unknown!")
    private String event;

    @NotNull(message = "Please Enter Date Of New Event!")
    private LocalDate onset;

    @NotBlank(message = "Please Provide Time Of New Event!")
    @Size(min = 3, max = 25, message = "Invalid Entry, Please Select Time!")
    private String timeOfOccurrence;

    @NotBlank(message = "Please Enter Any Relieving Agent Taken!")
    @Size(min = 3, max = 250, message = "Invalid Entry, Enter Null If Unknown!")
    private String relievingAgentTaken;

    @NotBlank(message = "Please Enter Any Aggravating Agent Taken!")
    @Size(min = 3, max = 250, message = "Invalid Entry, Enter Null If Unknown!")
    private String aggravatingAgentTaken;
    
    @NotBlank(message = "Please Describe Current Condition!")
    private String conditionStatus;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @NotNull(message = "Patient ID Is Required!")
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    @NotNull(message = "Patient Case Is Required!")
    private PatientCase patientCase;

    public IncidentReport (){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public LocalDate getOnset() {
		return onset;
	}

	public void setOnset(LocalDate onset) {
		this.onset = onset;
	}

	public String getTimeOfOccurrence() {
		return timeOfOccurrence;
	}

	public void setTimeOfOccurrence(String timeOfOccurrence) {
		this.timeOfOccurrence = timeOfOccurrence;
	}

	public String getRelievingAgentTaken() {
		return relievingAgentTaken;
	}

	public void setRelievingAgentTaken(String relievingAgentTaken) {
		this.relievingAgentTaken = relievingAgentTaken;
	}

	public String getAggravatingAgentTaken() {
		return aggravatingAgentTaken;
	}

	public void setAggravatingAgentTaken(String aggravatingAgentTaken) {
		this.aggravatingAgentTaken = aggravatingAgentTaken;
	}

	public String getConditionStatus() {
		return conditionStatus;
	}

	public void setConditionStatus(String conditionStatus) {
		this.conditionStatus = conditionStatus;
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
