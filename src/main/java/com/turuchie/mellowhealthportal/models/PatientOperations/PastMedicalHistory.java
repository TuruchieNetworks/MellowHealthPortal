package com.turuchie.mellowhealthportal.models.PatientOperations;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "pastMedicalRecords")
public class PastMedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;   

    @NotBlank(message = "Medical Condition Cannot Be Blank!")
    @Size(min = 3, max = 155, message = "Medical Condition Must Be Between 3 and 155 Characters!")
    private String medicalCondition;

    @NotBlank(message = "Please Enter Any Medication You Are Currently Taking!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Enter Null If Unknown!")
    private String medication;

    @NotBlank(message = "Please Enter Any Treatment Plan For This Condition!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Enter Null If Unknown!")
    private String treatmentPlan;

    @NotNull(message = "Diagnosis Date cannot be null!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotNull(message = "Diagnosis Date cannot be null!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Column(updatable = false)
    private LocalDate createdAt;

    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public PastMedicalHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicalCondition() {
		return medicalCondition;
	}

    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedicalCondition(String medicalCondition) {
		this.medicalCondition = medicalCondition;
	}

    public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
    }
}
