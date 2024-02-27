package com.turuchie.mellowhealthportal.models.ClinicalOperations;

import java.time.LocalDateTime;

import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;

import jakarta.persistence.CascadeType;
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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pharmaco_kinetic_parameters")
public class PharmacoKineticParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Please enter the maximum (or peak) serum concentration") 
    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum (or peak) serum concentration must be greater than 0 ng/mL")
    private double cMax;

    @NotNull(message = "Please enter the value of area under the curve")
    @DecimalMin(value = "0.0", inclusive = false, message = "The value of area under the curve must be greater than 0 h*ng/mL")         
    private double auc0_24h;

    @NotNull(message = "Please enter the ratio of accumulation of the drug under steady-state condition")
    @DecimalMin(value = "0.0", inclusive = false, message = "The mean accumulation ratio (Racc) calculated from AUC0-24h at steady-state and AUC0-24h after a single dose must be greater than 0 h*ng/mL") 
    private double rAcc;

    @NotNull(message = "Please enter the ratio of accumulation of the drug under steady-state condition")
    @DecimalMin(value = "0.0", inclusive = false, message = "The ratio of accumulation of the drug under steady-state must be greater than 0 h!") 
    private double tHalfAcc;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull(message = "Please Select Case ID!")
    @JoinColumn(name = "patientCase_id")
    private PatientCase patientCase;

    public PharmacoKineticParameter() {
    }
 
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public double getcMax() {
		return cMax;
	}

	public void setcMax(double cMax) {
		this.cMax = cMax;
	}

	public double getAuc0_24h() {
		return auc0_24h;
	}

	public void setAuc0_24h(double auc0_24h) {
		this.auc0_24h = auc0_24h;
	}

	public double getrAcc() {
		return rAcc;
	}

	public void setrAcc(double rAcc) {
		this.rAcc = rAcc;
	}

	public double gettHalfAcc() {
		return tHalfAcc;
	}

	public void settHalfAcc(double tHalfAcc) {
		this.tHalfAcc = tHalfAcc;
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