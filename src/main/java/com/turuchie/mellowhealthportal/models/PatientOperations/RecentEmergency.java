package com.turuchie.mellowhealthportal.models.PatientOperations;

import java.time.LocalDateTime;
import java.util.Date;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "recent_emergencies")
public class RecentEmergency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Medical Condition Cannot Be Blank!")
    private String medicalCondition;

    @NotBlank(message = "Please Enter Any Complications Or Enter None If Unknown!")
    private String complication;

    @NotBlank(message = "Please Provide Admission Date!")
    private Date startDate;

    @NotNull(message = "Please Enter How Long You Were Admitted!")
    @Min(value = 1, message = "Please Enter Number Of Days or Select Less Than A Day!")
    private int durationOfCare;

    @NotBlank(message = "Please Provide The Name Of The Facility Where You Were Treated!")
    private String facility;

    @NotBlank(message = "Please Enter Ongoing Treatment or Past Treatment!")
    private String treatmentPlan;

    @NotBlank(message = "Please Provide The Physicians First Name!")
    private String physicianFirstName;

    @NotBlank(message = "Please Provide The Physicians Last Name!")
    private String physicianLastName;

    @NotBlank(message = "Please Provide The Physicians Email or Valid Contact Information!")
    private String physicianEmail;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public RecentEmergency() {
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

	public void setMedicalCondition(String medicalCondition) {
		this.medicalCondition = medicalCondition;
	}

	public String getTreatmentPlan() {
		return treatmentPlan;
	}

	public void setTreatmentPlan(String treatmentPlan) {
		this.treatmentPlan = treatmentPlan;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public String getPhysicianFirstName() {
		return physicianFirstName;
	}

	public void setPhysicianFirstName(String physicianFirstName) {
		this.physicianFirstName = physicianFirstName;
	}

	public String getPhysicianLastName() {
		return physicianLastName;
	}

	public void setPhysicianLastName(String physicianLastName) {
		this.physicianLastName = physicianLastName;
	}

	public String getPhysicianEmail() {
		return physicianEmail;
	}

	public void setPhysicianEmail(String physicianEmail) {
		this.physicianEmail = physicianEmail;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getComplication() {
		return complication;
	}

	public void setComplication(String complication) {
		this.complication = complication;
	}

	public int getDurationOfCare() {
		return durationOfCare;
	}

	public void setDurationOfCare(int durationOfCare) {
		this.durationOfCare = durationOfCare;
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