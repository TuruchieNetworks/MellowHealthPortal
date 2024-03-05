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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "diagnostic_records")
public class DiagnosticRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please Describe Patients Current Symptoms!")
    @Size(min = 3, max = 500, message = "Subjective Symptoms Must Be Between 3 and 500 Characters!")
    private String subjectiveSymptoms;

    @NotBlank(message = "Please Describe Observed Symptoms!")
    @Size(min = 3, max = 500, message = "Subjective Symptoms Must Be Between 3 and 500 Characters!")
    private String objectiveFindings;

    @Column(name = "history_findings")
    @NotBlank(message = "Please Enter History Findings!")
    @Size(min = 1, max = 1000, message = "Invalid Entry, History Findings Must Be Between 1 and 1000 Characters!")
    private String historyFindings;

    @Column(name = "physical_exam_findings")
    @NotBlank(message = "Please Enter Physical Exam Findings!")
    @Size(min = 1, max = 1000, message = "Invalid Entry, Physical Exam Findings Must Be Between 1 and 1000 Characters!")
    private String physicalExamFindings;

    @Column(name = "differential_diagnosis")
    @NotBlank(message = "Please Differential Diagnosis!")
    @Size(min = 1, max = 1000, message = "Invalid Entry, Differential Diagnosis Must Be Between 1 and 1000 Characters!")
    private String differentialDiagnosis;

    @Column(name = "diagnostic_workup")
    @NotBlank(message = "Please Enter Diagnostic Workup!")
    @Size(min = 1, max = 1000, message = "Invalid Entry, Diagnostic Workup Must Be Between 1 and 1000 Characters!")
    private String diagnosticWorkUp;

    @Column(name = "patient_note")
    @NotBlank(message = "Please Enter Patient Note!")
    @Size(min = 1, max = 1000, message = "Invalid Entry, Patient Note Must Be Between 1 and 1000 Characters!")
    private String patientNote;

    @NotBlank(message = "Patients Treatment Plan must be between 3 and 500 characters!")
    @Size(min = 3, max = 500, message = "Please provide treatment plan for this patient's visit, or Enter Pending!")
    private String treatmentPlan;

    @NotBlank(message = "Follow ups must be between 3 and 500 characters!")
    @Size(min = 3, max = 500, message = "Please provide follow-up consultations for this patient's visit, Enter Null if Pending!")
    private String followUpConsultation;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    @NotNull(message = "Please Enter Patient Case ID!")
    private PatientCase patientCase;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @NotNull(message = "Please Select Patient ID!")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "physician_id")
    @NotNull(message = "Please Select Physician ID!")
    private Physician physician;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public DiagnosticRecord () {
    }

    // Constructors, getters, setters, etc.
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubjectiveSymptoms() {
		return subjectiveSymptoms;
	}

	public void setSubjectiveSymptoms(String subjectiveSymptoms) {
		this.subjectiveSymptoms = subjectiveSymptoms;
	}

	public String getObjectiveFindings() {
		return objectiveFindings;
	}

	public void setObjectiveFindings(String objectiveFindings) {
		this.objectiveFindings = objectiveFindings;
	}

	public String getFollowUpConsultation() {
		return followUpConsultation;
	}

	public void setFollowUpConsultation(String followUpConsultation) {
		this.followUpConsultation = followUpConsultation;
	}

	public String getHistoryFindings() {
		return historyFindings;
	}

	public void setHistoryFindings(String historyFindings) {
		this.historyFindings = historyFindings;
	}

	public String getPhysicalExamFindings() {
		return physicalExamFindings;
	}

	public void setPhysicalExamFindings(String physicalExamFindings) {
		this.physicalExamFindings = physicalExamFindings;
	}

	public String getDifferentialDiagnosis() {
		return differentialDiagnosis;
	}

	public void setDifferentialDiagnosis(String differentialDiagnosis) {
		this.differentialDiagnosis = differentialDiagnosis;
	}

	public String getDiagnosticWorkUp() {
		return diagnosticWorkUp;
	}

	public void setDiagnosticWorkUp(String diagnosticWorkUp) {
		this.diagnosticWorkUp = diagnosticWorkUp;
	}

	public String getPatientNote() {
		return patientNote;
	}

	public void setPatientNote(String patientNote) {
		this.patientNote = patientNote;
	}

	public String getTreatmentPlan() {
		return treatmentPlan;
	}

	public void setTreatmentPlan(String treatmentPlan) {
		this.treatmentPlan = treatmentPlan;
	}

	public PatientCase getPatientCase() {
		return patientCase;
	}

	public void setPatientCase(PatientCase patientCase) {
		this.patientCase = patientCase;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
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