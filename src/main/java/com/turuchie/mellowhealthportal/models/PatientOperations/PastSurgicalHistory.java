package com.turuchie.mellowhealthportal.models.PatientOperations;

import java.time.LocalDateTime;

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
@Table(name = "pastSurgicalRecords")
public class PastSurgicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;   

    @NotBlank(message = "Medical Condition Cannot Be Blank!")
    @Size(min = 3, max = 155, message = "Medical Condition Must Be Between 3 and 155 Characters!")
    private String medicalCondition;

    @NotBlank(message = "Please Enter Procedure That Was Performed!")
    @Size(min = 3, max = 255, message = "Surgical Procedrue Must Be Between 3 and 255 Characters!")
    private String medicalProcedure;

    @NotBlank(message = "Surgical Complications Cannot Be Blank, Please Enter Null If Unknown!")
    private String complications;

    @NotBlank(message = "Please Enter Any Medication You Are Currently Taking!")
    @Size(min = 3, max = 255, message = "Medications Must Be Between 3 and 255 Characters, Enter Null If Unknown!")
    private String medication;

    @NotNull(message = "Procedure Date cannot be null!")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime startDate;

    private String treatmentPlan;
 
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public PastSurgicalHistory() {
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

    public void setMedicalCondition(String medicalCondition) {
		this.medicalCondition = medicalCondition;
	}

    public String getMedicalProcedure() {
		return medicalProcedure;
	}

    public void setMedicalProcedure(String medicalProcedure) {
		this.medicalProcedure = medicalProcedure;
	}

    public String getComplications() {
		return complications;
	}

    public void setComplications(String complications) {
		this.complications = complications;
	}

    public String getMedication() {
		return medication;
	}

    public void setMedication(String medication) {
		this.medication = medication;
	}

    public LocalDateTime getStartDate() {
		return startDate;
	}

    public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
