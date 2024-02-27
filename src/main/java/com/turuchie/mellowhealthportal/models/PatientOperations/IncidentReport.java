package com.turuchie.mellowhealthportal.models.PatientOperations;

import java.time.LocalDateTime;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "incident_reports")
public class IncidentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Chief Complaint Cannot Be Blank!")
    private String event;

    @NotBlank(message = "Please Enter Patients Name!")
    private String timeOfOccurrence;

    @NotBlank(message = "Please Enter Patients Name!")
    private String relievingAgentTaken;
    
    @NotBlank(message = "Please Enter Patients Name!")
    private String conditionStatus;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
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

	public String getConditionStatus() {
		return conditionStatus;
	}

	public void setConditionStatus(String conditionStatus) {
		this.conditionStatus = conditionStatus;
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
   
}
