package com.turuchie.mellowhealthportal.models.DiagnosticProcedures;

import java.time.LocalDate;

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
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "follow_up_records")
public class FollowUpRecord {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 	private Long id;

	@NotBlank(message = "Please Enter Chief Complaint!")
	@Size(min = 1, max = 255, message = "Invalid Entry, Duration Must Be Between 1 and 255 Characters!")
 	private String chiefComplaint;

    @NotBlank(message = "Please Enter Recommended Procedure!")
    @Size(min = 1, max = 255, message = "Invalid Entry, Duration Must Be Between 1 and 255 Characters!")
 	private String recommendedProcedure;
 	

    @Column(updatable = false)
    private LocalDate createdAt;

    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient; 

    @ManyToOne
    @JoinColumn(name = "physician_id")
    private Physician physician;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    private PatientCase patientCase;

    public FollowUpRecord() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChiefComplaint() {
		return chiefComplaint;
	}

	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}

	public String getRecommendedProcedure() {
		return recommendedProcedure;
	}

	public void setRecommendedProcedure(String recommendedProcedure) {
		this.recommendedProcedure = recommendedProcedure;
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
//	 @OneToMany(mappedBy = "followUp", cascade = CascadeType.ALL)
//	 private List<HematologyFollowUp> hematologyFollowUps;
//	
//	 @OneToMany(mappedBy = "followUp", cascade = CascadeType.ALL)
//	 private List<RenalFollowUp> renalFollowUps;
//	
//	 @OneToMany(mappedBy = "followUp", cascade = CascadeType.ALL)
//	 private List<HepaticFollowUp> hepaticFollowUps;
//	
//	 @OneToMany(mappedBy = "followUp", cascade = CascadeType.ALL)
//	 private List<CardiacFollowUp> cardiacFollowUps;
//	
//	 @OneToMany(mappedBy = "followUp", cascade = CascadeType.ALL)
//	 private List<DermatologyFollowUp> dermatologyFollowUps;
//	
//	 @OneToMany(mappedBy = "followUp", cascade = CascadeType.ALL)
//	 private List<RespiratoryFollowUp> respiratoryFollowUps;
//	
//	 @OneToMany(mappedBy = "followUp", cascade = CascadeType.ALL)
//	 private List<GastrointestinalFollowUp> gastrointestinalFollowUps;

}
